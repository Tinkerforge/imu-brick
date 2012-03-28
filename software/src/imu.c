/* imu-brick
 * Copyright (C) 2011-2012 Olaf Lüke <olaf@tinkerforge.com>
 *
 * imu.c: Inertial Measurement Unit implementation
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

#include "imu.h"

#include "config.h"
#include "communication.h"
#include "bricklib/com/com_messages.h"
#include "bricklib/com/com_common.h"
#include "bricklib/logging/logging.h"


#include "bricklib/drivers/pio/pio.h"
#include "bricklib/drivers/twi/twi.h"
#include "bricklib/drivers/twi/twid.h"
#include "bricklib/drivers/async/async.h"
#include "bricklib/drivers/pwmc/pwmc.h"
#include "bricklib/drivers/tc/tc.h"
#include "bricklib/drivers/adc/adc.h"
#include "bricklib/utility/util_definitions.h"
#include "bricklib/utility/init.h"
#include "bricklib/utility/led.h"
#include "bricklib/utility/sqrt.h"
#include "bricklib/utility/mutex.h"
#include "bricklib/drivers/flash/flashd.h"

#include <stdio.h>
#include <string.h>
#include <math.h>

uint32_t imu_period[IMU_PERIOD_NUM] = {0};
uint32_t imu_period_counter[IMU_PERIOD_NUM] = {0};

const IMUCalibration *ic = (IMUCalibration*)IMU_CALIBRATION_ADDRESS;

float imu_gyr_x_b = 1.0;
float imu_gyr_y_b = 1.0;
float imu_gyr_z_b = 1.0;

float imu_gyr_x_m = 0.0;
float imu_gyr_y_m = 0.0;
float imu_gyr_z_m = 0.0;

int16_t imu_gyr_x_bias = 0;
int16_t imu_gyr_y_bias = 0;
int16_t imu_gyr_z_bias = 0;

int16_t imu_acc_x = 0;
int16_t imu_acc_y = 0;
int16_t imu_acc_z = 0;

int16_t imu_mag_x = 0;
int16_t imu_mag_y = 0;
int16_t imu_mag_z = 0;

int16_t imu_gyr_x = 0;
int16_t imu_gyr_y = 0;
int16_t imu_gyr_z = 0;

int16_t imu_pitch = 0;
int16_t imu_roll = 0;
int16_t imu_yaw = 0;

int16_t imu_gyr_temperature = 0;
int32_t imu_gyr_temperature_sum = 0;
uint32_t imu_gyr_temperature_counter = 0;

float imu_qua_x = 1.0;
float imu_qua_y = 0.0;
float imu_qua_z = 0.0;
float imu_qua_w = 0.0;

// estimated orientation quaternion elements with initial conditions
float imu_filter_seq_1 = 1.0;
float imu_filter_seq_2 = 0.0;
float imu_filter_seq_3 = 0.0;
float imu_filter_seq_4 = 0.0;

// reference direction of flux in earth frame
float imu_filter_b_x = 1.0;
float imu_filter_b_z = 0.0;

// estimate gyroscope biases error
float imu_filter_w_bx = 0;
float imu_filter_w_by = 0;
float imu_filter_w_bz = 0;

// gyroscope measurement error in rad/s (shown as 30 deg/s)
float imu_filter_beta = SQRT3DIV4 * M_PI * \
                        (IMU_DEFAULT_CONVERGENCE_SPEED / 180.0f);
// gyroscope measurement error in rad/s/s (shown as 0.5f deg/s/s)
float imu_filter_zeta = SQRT3DIV4 * M_PI * \
                        ((IMU_DEFAULT_CONVERGENCE_SPEED/60.0f) / 180.0f);

bool imu_use_leds = true;
uint32_t imu_tick = 0;
Async imu_async;
uint8_t imu_sensor_data[8] = {0};

extern Twid twid;
extern ComType com_current;
extern uint8_t com_stack_id;
extern Mutex mutex_twi_bricklet;

uint16_t imu_convergence_speed = 30;

Pin pins_imu_led[] = {PINS_IMU_LED};
int8_t pins_led_tc_channel[] = {-1, -1, -1, -1, -1, -1, 1, 2, 0, -1};
int8_t pins_led_pwm_channel[] = {0, 0, 1, 1, 2, 2, -1, -1, -1, 3};
bool pins_led_is_pwm[] = {true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          false,
                          false,
                          false,
                          true};

void tick_task(const uint8_t tick_type) {
	if(tick_type == TICK_TASK_TYPE_CALCULATION) {
		switch(imu_tick % 2) {
			case 0:
				// 400us
				imu_blinkenlights();
				imu_quaternion_to_orientation();
				update_sensors_async();
				break;

			case 1:
				// 450us
				imu_update_filter((float)imu_gyr_x*M_PI/(14.375*180),
				                  (float)imu_gyr_y*M_PI/(14.375*180),
				                  (float)imu_gyr_z*M_PI/(14.375*180),
				                  (float)imu_acc_x,
				                  (float)imu_acc_y,
				                  (float)imu_acc_z,
				                  (float)imu_mag_x,
				                  (float)imu_mag_y,
				                  (float)imu_mag_z);
				break;
		}

		for(uint8_t i = 0; i < IMU_PERIOD_NUM; i++) {
			if(imu_period_counter[i] < UINT32_MAX) {
				imu_period_counter[i]++;
			}
		}
		imu_tick++;
	} else if(tick_type == TICK_TASK_TYPE_MESSAGE) {
		for(uint8_t i = 0; i < IMU_PERIOD_NUM; i++) {
			if((imu_period[i] != 0) &&
			   (imu_period[i] <= imu_period_counter[i])) {
				// Test if we are totally out of time (lost a whole
				// period), in this case we don't send the signal again.
				// This is needed for the wireless extensions
				if(imu_period[i]*2 <= imu_period_counter[i]) {
					imu_period_counter[i] = imu_period[i];
				}
				make_period_signal(i);
			}
		}
	}
}

void make_period_signal(const uint8_t type) {
	imu_period_counter[type] -= imu_period[type];

	switch(type) {
		case IMU_PERIOD_TYPE_ACC: {
			AccelerationSignal as = {
				com_stack_id,
				TYPE_ACCELERATION,
				sizeof(AccelerationSignal),
				imu_acc_x,
				imu_acc_y,
				imu_acc_z
			};

			send_blocking_with_timeout(&as,
			                           sizeof(AccelerationSignal),
			                           com_current);
			break;
		}

		case IMU_PERIOD_TYPE_MAG: {
			MagneticFieldSignal mfs = {
				com_stack_id,
				TYPE_MAGNETIC_FIELD,
				sizeof(MagneticFieldSignal),
				imu_mag_x,
				imu_mag_y,
				imu_mag_z
			};

			send_blocking_with_timeout(&mfs,
			                           sizeof(MagneticFieldSignal),
			                           com_current);
			break;
		}

		case IMU_PERIOD_TYPE_ANG: {
			AngularVelocitySignal avs = {
				com_stack_id,
				TYPE_ANGULAR_VELOCITY,
				sizeof(AngularVelocitySignal),
				imu_gyr_x,
				imu_gyr_y,
				imu_gyr_z,
			};

			send_blocking_with_timeout(&avs,
			                           sizeof(AngularVelocitySignal),
			                           com_current);
			break;
		}

		case IMU_PERIOD_TYPE_ALL: {
			AllDataSignal ads = {
				com_stack_id,
				TYPE_ALL_DATA,
				sizeof(AllDataSignal),
				imu_acc_x,
				imu_acc_y,
				imu_acc_z,
				imu_mag_x,
				imu_mag_y,
				imu_mag_z,
				imu_gyr_x,
				imu_gyr_y,
				imu_gyr_z,
				imu_gyr_temperature
			};

			send_blocking_with_timeout(&ads,
			                           sizeof(AllDataSignal),
			                           com_current);
			break;
		}

		case IMU_PERIOD_TYPE_ORI: {
			OrientationSignal os = {
				com_stack_id,
				TYPE_ORIENTATION,
				sizeof(OrientationSignal),
				imu_roll,
				imu_pitch,
				imu_yaw
			};

			send_blocking_with_timeout(&os,
			                           sizeof(OrientationSignal),
			                           com_current);
			break;
		}

		case IMU_PERIOD_TYPE_QUA: {
			QuaternionSignal qs = {
				com_stack_id,
				TYPE_QUATERNION,
				sizeof(QuaternionSignal),
				imu_qua_x,
				imu_qua_y,
				imu_qua_z,
				imu_qua_w
			};

			send_blocking_with_timeout(&qs,
			                           sizeof(QuaternionSignal),
			                           com_current);
			break;
		}
	}
}

/*
Python script to make the lookup table:

s = "{0"
for i in range(1, 21):
    s += ", " + str(0xffff/2 - ((1000-i**3/((20**3)/1000))*(0xFFFF/2)/1000))
for i in reversed(range(0, 20)):
    s += ", " + str(0xffff/2 + ((1000-i**3/((20**3)/1000))*(0xFFFF/2)/1000))
print s + "}"
*/
const uint16_t blink_lookup[41] = {1, 1, 33, 99, 263, 492, 885, 1377, 2098, 2982, 4096, 5440, 7078, 8979, 11240, 13795, 16777, 20119, 23888, 28082, 32767, 37452, 41646, 45415, 48757, 51739, 54294, 56555, 58456, 60094, 61438, 62552, 63436, 64157, 64649, 65042, 65271, 65435, 65501, 65534, 65534};

void imu_leds_on(bool on) {
	if(on) {
		PMC->PMC_PCER0 = 1 << ID_PWM;
		for(uint8_t i = 0; i < 4; i++) {
			PWMC_ConfigureChannel(PWM, i, PWM_CMR_CPRE_MCK, 0, 0);
			PWMC_SetPeriod(PWM, i, 0xFFFF);
			PWMC_SetDutyCycle(PWM, i, 0);
			PWMC_EnableChannel(PWM, i);
		}

	    PMC->PMC_PCER0 = 1 << ID_TC0;
	    tc_channel_init(&TC0->TC_CHANNEL[0],
		                TC_CMR_WAVE |
		                TC_CMR_TCCLKS_TIMER_CLOCK1 |
		                TC_CMR_ACPA_CLEAR |
		                TC_CMR_ACPC_SET |
		                TC_CMR_WAVSEL_UP_RC);

	    TC0->TC_CHANNEL[0].TC_RA = 0;
	    TC0->TC_CHANNEL[0].TC_RC = 0xFFFF;
	    tc_channel_start(&TC0->TC_CHANNEL[0]);

	    PMC->PMC_PCER0 = 1 << ID_TC1;
		tc_channel_init(&TC0->TC_CHANNEL[1],
		                TC_CMR_WAVE |
		                TC_CMR_TCCLKS_TIMER_CLOCK1 |
		                TC_CMR_ACPA_CLEAR |
		                TC_CMR_ACPC_SET |
		                TC_CMR_WAVSEL_UP_RC);

	    TC0->TC_CHANNEL[1].TC_RA = 0;
	    TC0->TC_CHANNEL[1].TC_RC = 0xFFFF;
	    tc_channel_start(&TC0->TC_CHANNEL[1]);

	    PMC->PMC_PCER0 = 1 << ID_TC2;
		tc_channel_init(&TC0->TC_CHANNEL[2],
		                TC_CMR_WAVE |
		                TC_CMR_TCCLKS_TIMER_CLOCK1 |
		                TC_CMR_BCPB_CLEAR |
		                TC_CMR_BCPC_SET |
		                TC_CMR_EEVT_XC0 |
		                TC_CMR_WAVSEL_UP_RC);

	    TC0->TC_CHANNEL[2].TC_RB = 0;
	    TC0->TC_CHANNEL[2].TC_RC = 0xFFFF;
	    tc_channel_start(&TC0->TC_CHANNEL[2]);


		PIO_Configure(pins_imu_led, PIO_LISTSIZE(pins_imu_led));
	} else {
		PMC->PMC_PCER0 &= ~(1 << ID_PWM);
		PMC->PMC_PCER0 &= ~(1 << ID_TC0);
		PMC->PMC_PCER0 &= ~(1 << ID_TC1);
		PMC->PMC_PCER0 &= ~(1 << ID_TC2);

		Pin pins[] = {PINS_IMU_LED};
		for(uint8_t i = 0; i < PIO_LISTSIZE(pins); i++) {
			pins[i].type = PIO_OUTPUT_1;
		}

		PIO_Configure(pins, PIO_LISTSIZE(pins));
	}

	imu_use_leds = on;
}

void imu_blinkenlights(void) {
	if(!imu_use_leds) {
		return;
	}

	PWMC_SetDutyCycle(PWM, 0,
	                  blink_lookup[(BETWEEN(-1000, imu_acc_x, 1000) +
	                                1000)/50]);
	PWMC_SetDutyCycle(PWM, 1,
	                  blink_lookup[(BETWEEN(-1000, imu_acc_y, 1000) +
	                                1000)/50]);
	PWMC_SetDutyCycle(PWM, 2,
	                  blink_lookup[40 - (BETWEEN(-1000, imu_acc_z, 1000) +
	                               1000)/50]);

	int16_t degree = atan2(-imu_qua_w*imu_qua_y +
	                        imu_qua_x*imu_qua_y +
	                        imu_qua_y*imu_qua_x -
	                        imu_qua_w*imu_qua_w,
	                        imu_qua_w*imu_qua_w +
	                        imu_qua_y*imu_qua_y -
	                        imu_qua_z*imu_qua_y -
	                        imu_qua_x*imu_qua_x)*180/M_PI + 90;
	if(degree < 0) {
		degree += 360;
	}

	if(degree > 315 || degree <= 45) {
		uint8_t index = ((degree + 45) % 360) * 40 / 90;
		TC0->TC_CHANNEL[0].TC_RA = 0;
		TC0->TC_CHANNEL[1].TC_RA = blink_lookup[index];
		TC0->TC_CHANNEL[2].TC_RB = 0;
		PWMC_SetDutyCycle(PWM, 3, blink_lookup[index]);
	} else if(degree < 135) {
		uint8_t index = (degree - 45) * 40 / 90;
		TC0->TC_CHANNEL[0].TC_RA = 0;
		TC0->TC_CHANNEL[1].TC_RA = 0;
		TC0->TC_CHANNEL[2].TC_RB = blink_lookup[40-index];
		PWMC_SetDutyCycle(PWM, 3, blink_lookup[40-index]);
	} else if(degree < 225) {
		uint8_t index = (degree - 135) * 40 / 90;
		TC0->TC_CHANNEL[0].TC_RA = blink_lookup[40-index];
		TC0->TC_CHANNEL[1].TC_RA = 0;
		TC0->TC_CHANNEL[2].TC_RB = blink_lookup[index];
		PWMC_SetDutyCycle(PWM, 3, 0);
	} else {
		uint8_t index = (degree - 225) * 40 / 90;
		TC0->TC_CHANNEL[0].TC_RA = blink_lookup[index];
		TC0->TC_CHANNEL[1].TC_RA = blink_lookup[40-index];
		TC0->TC_CHANNEL[2].TC_RB = 0;
		PWMC_SetDutyCycle(PWM, 3, 0);
	}
}

void imu_quaternion_to_orientation(void) {
	imu_roll = atan2(2*imu_qua_y*imu_qua_w - 2*imu_qua_x*imu_qua_z,
	                 1 - 2*imu_qua_y*imu_qua_y -
	                     2*imu_qua_z*imu_qua_z)*18000/M_PI;
	imu_yaw = asin(2*imu_qua_x*imu_qua_y + 2*imu_qua_z*imu_qua_w)*18000/M_PI;
	imu_pitch = atan2(2*imu_qua_x*imu_qua_w-2*imu_qua_y*imu_qua_z ,
	                  1 - 2*imu_qua_x*imu_qua_x -
	                      2*imu_qua_z*imu_qua_z)*18000/M_PI;
}

void callback_accelerometer(Async *a) {
	int16_t x = ((imu_sensor_data[1] << 8) | imu_sensor_data[0]) >> 4;
	int16_t y = ((imu_sensor_data[3] << 8) | imu_sensor_data[2]) >> 4;
	int16_t z = ((imu_sensor_data[5] << 8) | imu_sensor_data[4]) >> 4;

	imu_acc_x = ((two_complement_12_to_16(y) +
	              ic->imu_acc_x_bias)*
	              ic->imu_acc_x_gain_multiplier)/ic->imu_acc_x_gain_divider;
	imu_acc_y = ((-two_complement_12_to_16(x) +
	              ic->imu_acc_y_bias)*
	              ic->imu_acc_y_gain_multiplier)/ic->imu_acc_y_gain_divider;
	imu_acc_z = ((-two_complement_12_to_16(z) +
	              ic->imu_acc_z_bias)*
	              ic->imu_acc_z_gain_multiplier)/ic->imu_acc_z_gain_divider;

	imu_async.callback = callback_magnetometer;
    TWID_Read(&twid,
    		  LSM_I2C_MAG_ADDRESS,
    		  LSM_REGISTER_MAG_X_H,
              1,
              imu_sensor_data,
              6,
              &imu_async);
}

void callback_magnetometer(Async *a) {
	int16_t x = imu_sensor_data[1] | (imu_sensor_data[0] << 8);
	int16_t y = imu_sensor_data[3] | (imu_sensor_data[2] << 8);
	int16_t z = imu_sensor_data[5] | (imu_sensor_data[4] << 8);


	imu_mag_x = ((-y + ic->imu_mag_x_bias)*ic->imu_mag_x_gain_multiplier)/
	             ic->imu_mag_x_gain_divider;
	imu_mag_y = (( x + ic->imu_mag_y_bias)*ic->imu_mag_y_gain_multiplier)/
	             ic->imu_mag_y_gain_divider;
	imu_mag_z = (( z + ic->imu_mag_z_bias)*ic->imu_mag_z_gain_multiplier)/
	             ic->imu_mag_z_gain_divider;

	imu_async.callback = callback_gyroscope;
    TWID_Read(&twid,
			  ITG_I2C_GYR_ADDRESS,
			  ITG_REGISTER_TEMP_H,
              1,
              imu_sensor_data,
              8,
              &imu_async);
}

void update_gyr_temperature_aprox(void) {
	imu_gyr_x_m = ((float)(ic->imu_gyr_x_bias_high - ic->imu_gyr_x_bias_low))/
                          (ic->imu_gyr_temp_high - ic->imu_gyr_temp_low);

	imu_gyr_y_m = ((float)(ic->imu_gyr_y_bias_high - ic->imu_gyr_y_bias_low))/
                          (ic->imu_gyr_temp_high - ic->imu_gyr_temp_low);

	imu_gyr_z_m = ((float)(ic->imu_gyr_z_bias_high - ic->imu_gyr_z_bias_low))/
                          (ic->imu_gyr_temp_high - ic->imu_gyr_temp_low);

	imu_gyr_x_b = ic->imu_gyr_x_bias_low - imu_gyr_x_m*ic->imu_gyr_temp_low;
	imu_gyr_y_b = ic->imu_gyr_y_bias_low - imu_gyr_y_m*ic->imu_gyr_temp_low;
	imu_gyr_z_b = ic->imu_gyr_z_bias_low - imu_gyr_z_m*ic->imu_gyr_temp_low;
}

void callback_gyroscope(Async *a) {
	mutex_give(mutex_twi_bricklet);

	int16_t t = imu_sensor_data[1] | (imu_sensor_data[0] << 8);
	int16_t x = imu_sensor_data[3] | (imu_sensor_data[2] << 8);
	int16_t y = imu_sensor_data[5] | (imu_sensor_data[4] << 8);
	int16_t z = imu_sensor_data[7] | (imu_sensor_data[6] << 8);


	imu_gyr_temperature_sum += t;
	imu_gyr_temperature_counter++;

	if(imu_gyr_temperature_counter == 1000) {
		imu_gyr_temperature = 3500 + (imu_gyr_temperature_sum/100 + 132000)/28;
		imu_gyr_temperature_counter = 0;
		imu_gyr_temperature_sum = 0;

		imu_gyr_x_bias = imu_gyr_x_m * imu_gyr_temperature + imu_gyr_x_b;
		imu_gyr_y_bias = imu_gyr_y_m * imu_gyr_temperature + imu_gyr_y_b;
		imu_gyr_z_bias = imu_gyr_z_m * imu_gyr_temperature + imu_gyr_z_b;
	}

	imu_gyr_x = (( x + imu_gyr_x_bias)*
	             ic->imu_gyr_x_gain_multiplier)/ic->imu_gyr_x_gain_divider;
	imu_gyr_y = ((-y + imu_gyr_y_bias)*
	             ic->imu_gyr_y_gain_multiplier)/ic->imu_gyr_y_gain_divider;
	imu_gyr_z = ((-z + imu_gyr_z_bias)*
	             ic->imu_gyr_z_gain_multiplier)/ic->imu_gyr_z_gain_divider;
}

void update_sensors_async(void) {
	mutex_take(mutex_twi_bricklet, MUTEX_BLOCKING);
	imu_async.callback = callback_accelerometer;
    TWID_Read(&twid,
    		  LSM_I2C_ACC_ADDRESS,
    		  LSM_REGISTER_ACC_X_L | LSM_ACC_READ_INCREMENTAL,
              1,
              imu_sensor_data,
              6,
              &imu_async);
}

void imu_set_register(uint16_t addr, uint16_t reg, uint8_t value) {
	TWI_IMU->TWI_MMR = 0;
	TWI_IMU->TWI_MMR = (addr << 16);
    while(!TWI_ByteSent(TWI_IMU));
    TWI_WriteByte(TWI_IMU, reg);
    while(!TWI_ByteSent(TWI_IMU));
    TWI_WriteByte(TWI_IMU, value);
    while(!TWI_ByteSent(TWI_IMU));
    TWI_SendSTOPCondition(TWI_IMU);
    while(!TWI_TransferComplete(TWI_IMU));
}

int16_t two_complement_12_to_16(const int16_t value) {
	if(value & (1 << 11)) {
		return value | 0xF000;
	}

	return value;
}

void imu_init(void) {
	logimui("IMU init start\n\r");
	Pin pins_imu[] = {PINS_ITG, PINS_LSM};
	PIO_Configure(pins_imu, PIO_LISTSIZE(pins_imu));

	logimui("IMU init LSM\n\r");
	imu_set_register(LSM_I2C_ACC_ADDRESS,
	                 LSM_REGISTER_ACC_CTRL1,
	                 LSM_ACC_CTRL1_X_EN |
	                 LSM_ACC_CTRL1_Y_EN |
	                 LSM_ACC_CTRL1_Z_EN |
	                 LSM_ACC_CTRL1_POWER_NORMAL |
	                 LSM_ACC_CTRL1_FREQ_1000);

	imu_set_register(LSM_I2C_MAG_ADDRESS,
	                 LSM_REGISTER_MAG_MR,
	                 LSM_MAG_MR_CONTINUOUS_CONVERSION);

	logimui("IMU init ITG\n\r");
	imu_set_register(ITG_I2C_GYR_ADDRESS,
	                 ITG_REGISTER_DLPF_FS,
	                 ITG_DLPF_FS_FS_SEL |
	                 ITG_DLPF_FS_DLPF_CFG_188_1);

	imu_set_register(ITG_I2C_GYR_ADDRESS,
	                 ITG_REGISTER_INT_CFG,
	                 ITG_INT_CFG_RAW_RDY_EN |
	                 ITG_INT_CFG_LATCH_INT_EN |
	                 ITG_INT_CFG_INT_ANYRD_2CLEAR |
	                 ITG_INT_CFG_PUSH_PULL |
	                 ITG_INT_CFG_ACTIVE_LOW);

	// TODO: What is the best here?
	imu_set_register(ITG_I2C_GYR_ADDRESS,
	                 ITG_REGISTER_PWR_MGM,
	                 ITG_PWR_MGM_CLK_SEL_GYRO_X);

	SLEEP_MS(IMU_STARTUP_TIME);

	imu_leds_on(true);

	update_gyr_temperature_aprox();
	logimui("IMU init done\n\r");
}

void imu_save_calibration(const IMUCalibrationNonConst *icnc) {
	DISABLE_RESET_BUTTON();
	__disable_irq();

	// Unlock flash region
	FLASHD_Unlock(IMU_CALIBRATION_ADDRESS,
	              END_OF_BRICKLET_MEMORY,
	              NULL,
				  NULL);

	FLASHD_Write(IMU_CALIBRATION_ADDRESS, icnc, sizeof(IMUCalibration));

	FLASHD_Lock(IMU_CALIBRATION_ADDRESS,
	            END_OF_BRICKLET_MEMORY,
	            NULL,
			    NULL);
	__enable_irq();
    ENABLE_RESET_BUTTON();
}

void imu_fill_calibration(IMUCalibrationNonConst *icnc) {
	memcpy(icnc, ic, sizeof(IMUCalibrationNonConst));
}

float imu_inv_sqrt(const float x) {
	float halfx = 0.5f * x;
	float y = x;
	long i = *(long*)&y;
	i = 0x5f3759df - (i>>1);
	y = *(float*)&i;
	return y * (1.5f - (halfx * y * y));
}


// Based on: S. O. Madgwick, "An efficient orientation filter for inertial and
// inertial/magnetic sensor arrays", University of Bristol, April 2010.
void imu_update_filter(float w_x, float w_y, float w_z,
                       float a_x, float a_y, float a_z,
                       float m_x, float m_y, float m_z) {
	// Normalize accelerometer measurement
	float norm = imu_inv_sqrt(a_x * a_x + a_y * a_y + a_z * a_z);
	a_x *= norm;
	a_y *= norm;
	a_z *= norm;

	// Normalize magnetometer measurement
	norm = imu_inv_sqrt(m_x * m_x + m_y * m_y + m_z * m_z);
	m_x *= norm;
	m_y *= norm;
	m_z *= norm;

	// auxiliary variables to avoid repeated calculations
	const float halfSEq_1 = 0.5f * imu_filter_seq_1;
	const float halfSEq_2 = 0.5f * imu_filter_seq_2;
	const float halfSEq_3 = 0.5f * imu_filter_seq_3;
	const float halfSEq_4 = 0.5f * imu_filter_seq_4;
	const float twoSEq_1 = 2.0f * imu_filter_seq_1;
	const float twoSEq_2 = 2.0f * imu_filter_seq_2;
	const float twoSEq_3 = 2.0f * imu_filter_seq_3;
	const float twoSEq_4 = 2.0f * imu_filter_seq_4;
	const float twob_x = 2.0f * imu_filter_b_x;
	const float twob_z = 2.0f * imu_filter_b_z;
	const float twob_xSEq_1 = 2.0f * imu_filter_b_x * imu_filter_seq_1;
	const float twob_xSEq_2 = 2.0f * imu_filter_b_x * imu_filter_seq_2;
	const float twob_xSEq_3 = 2.0f * imu_filter_b_x * imu_filter_seq_3;
	const float twob_xSEq_4 = 2.0f * imu_filter_b_x * imu_filter_seq_4;
	const float twob_zSEq_1 = 2.0f * imu_filter_b_z * imu_filter_seq_1;
	const float twob_zSEq_2 = 2.0f * imu_filter_b_z * imu_filter_seq_2;
	const float twob_zSEq_3 = 2.0f * imu_filter_b_z * imu_filter_seq_3;
	const float twob_zSEq_4 = 2.0f * imu_filter_b_z * imu_filter_seq_4;
	const float twom_x = 2.0f * m_x;
	const float twom_y = 2.0f * m_y;
	const float twom_z = 2.0f * m_z;

	float SEq_1SEq_2;
	float SEq_1SEq_3 = imu_filter_seq_1 * imu_filter_seq_3;
	float SEq_1SEq_4;
	float SEq_2SEq_3;
	float SEq_2SEq_4 = imu_filter_seq_2 * imu_filter_seq_4;
	float SEq_3SEq_4;

	// compute the objective function and Jacobian
	const float f_1 = twoSEq_2 * imu_filter_seq_4 -
	                  twoSEq_1 * imu_filter_seq_3 - a_x;
	const float f_2 = twoSEq_1 * imu_filter_seq_2 +
	                  twoSEq_3 * imu_filter_seq_4 - a_y;
	const float f_3 = 1.0f -
	                  twoSEq_2 * imu_filter_seq_2 -
	                  twoSEq_3 * imu_filter_seq_3 - a_z;
	const float f_4 = twob_x * (0.5f -
	                            imu_filter_seq_3 * imu_filter_seq_3 -
	                            imu_filter_seq_4 * imu_filter_seq_4) +
	                  twob_z * (SEq_2SEq_4 - SEq_1SEq_3) - m_x;
	const float f_5 = twob_x * (imu_filter_seq_2 * imu_filter_seq_3 -
	                            imu_filter_seq_1 * imu_filter_seq_4) +
	                  twob_z * (imu_filter_seq_1 * imu_filter_seq_2 +
	                            imu_filter_seq_3 * imu_filter_seq_4) - m_y;
	const float f_6 = twob_x * (SEq_1SEq_3 + SEq_2SEq_4) +
	                  twob_z * (0.5f -
	                            imu_filter_seq_2 * imu_filter_seq_2 -
	                            imu_filter_seq_3 * imu_filter_seq_3) - m_z;

	// J_11 negated in matrix multiplication
	const float J_11or24 = twoSEq_3;
	const float J_12or23 = 2.0f * imu_filter_seq_4;
	// J_12 negated in matrix multiplication
	const float J_13or22 = twoSEq_1;
	const float J_14or21 = twoSEq_2;
	// negated in matrix multiplication
	const float J_32 = 2.0f * J_14or21;
	// negated in matrix multiplication
	const float J_33 = 2.0f * J_11or24;
	// negated in matrix multiplication
	const float J_41 = twob_zSEq_3;
	const float J_42 = twob_zSEq_4;
	// negated in matrix multiplication
	const float J_43 = 2.0f * twob_xSEq_3 + twob_zSEq_1;
	// negated in matrix multiplication
	const float J_44 = 2.0f * twob_xSEq_4 - twob_zSEq_2;
	// negated in matrix multiplication
	const float J_51 = twob_xSEq_4 - twob_zSEq_2;
	const float J_52 = twob_xSEq_3 + twob_zSEq_1;
	const float J_53 = twob_xSEq_2 + twob_zSEq_4;
	// negated in matrix multiplication
	const float J_54 = twob_xSEq_1 - twob_zSEq_3;
	const float J_61 = twob_xSEq_3;
	const float J_62 = twob_xSEq_4 - 2.0f * twob_zSEq_2;
	const float J_63 = twob_xSEq_1 - 2.0f * twob_zSEq_3;
	const float J_64 = twob_xSEq_2;

	// compute the gradient (matrix multiplication)
	float SEqHatDot_1 = J_14or21 * f_2 -
	                    J_11or24 * f_1 -
	                    J_41 * f_4 -
	                    J_51 * f_5 +
	                    J_61 * f_6;
	float SEqHatDot_2 = J_12or23 * f_1 +
	                    J_13or22 * f_2 -
	                    J_32 * f_3 +
	                    J_42 * f_4 +
	                    J_52 * f_5 +
	                    J_62 * f_6;
	float SEqHatDot_3 = J_12or23 * f_2 -
	                    J_33 * f_3 -
	                    J_13or22 * f_1 -
	                    J_43 * f_4 +
	                    J_53 * f_5 +
	                    J_63 * f_6;
	float SEqHatDot_4 = J_14or21 * f_1 +
	                    J_11or24 * f_2 -
	                    J_44 * f_4 -
	                    J_54 * f_5 +
	                    J_64 * f_6;

	// normalise the gradient to estimate direction of the gyroscope error
	norm = imu_inv_sqrt(SEqHatDot_1 * SEqHatDot_1 +
	                    SEqHatDot_2 * SEqHatDot_2 +
	                    SEqHatDot_3 * SEqHatDot_3 +
	                    SEqHatDot_4 * SEqHatDot_4);

	SEqHatDot_1 *= norm;
	SEqHatDot_2 *= norm;
	SEqHatDot_3 *= norm;
	SEqHatDot_4 *= norm;

	// compute angular estimated direction of the gyroscope error
	const float w_err_x = twoSEq_1 * SEqHatDot_2 -
	                      twoSEq_2 * SEqHatDot_1 -
	                      twoSEq_3 * SEqHatDot_4 +
	                      twoSEq_4 * SEqHatDot_3;
	const float w_err_y = twoSEq_1 * SEqHatDot_3 +
	                      twoSEq_2 * SEqHatDot_4 -
	                      twoSEq_3 * SEqHatDot_1 -
	                      twoSEq_4 * SEqHatDot_2;
	const float w_err_z = twoSEq_1 * SEqHatDot_4 -
	                      twoSEq_2 * SEqHatDot_3 +
	                      twoSEq_3 * SEqHatDot_2 -
	                      twoSEq_4 * SEqHatDot_1;

	// compute and remove the gyroscope baises
	imu_filter_w_bx += w_err_x * IMU_FILTER_DELTA_T * imu_filter_zeta;
	imu_filter_w_by += w_err_y * IMU_FILTER_DELTA_T * imu_filter_zeta;
	imu_filter_w_bz += w_err_z * IMU_FILTER_DELTA_T * imu_filter_zeta;
	w_x -= imu_filter_w_bx;
	w_y -= imu_filter_w_by;
	w_z -= imu_filter_w_bz;

	// compute the quaternion rate measured by gyroscopes
	const float SEqDot_omega_1 = -halfSEq_2 * w_x -
	                             halfSEq_3 * w_y -
	                             halfSEq_4 * w_z;
	const float SEqDot_omega_2 = halfSEq_1 * w_x +
	                             halfSEq_3 * w_z -
	                             halfSEq_4 * w_y;
	const float SEqDot_omega_3 = halfSEq_1 * w_y -
	                             halfSEq_2 * w_z +
	                             halfSEq_4 * w_x;
	const float SEqDot_omega_4 = halfSEq_1 * w_z +
	                             halfSEq_2 * w_y -
	                             halfSEq_3 * w_x;

	// compute then integrate the estimated quaternion rate
	imu_filter_seq_1 += (SEqDot_omega_1 -
	                     (imu_filter_beta * SEqHatDot_1)) * IMU_FILTER_DELTA_T;
	imu_filter_seq_2 += (SEqDot_omega_2 -
	                     (imu_filter_beta * SEqHatDot_2)) * IMU_FILTER_DELTA_T;
	imu_filter_seq_3 += (SEqDot_omega_3 -
	                     (imu_filter_beta * SEqHatDot_3)) * IMU_FILTER_DELTA_T;
	imu_filter_seq_4 += (SEqDot_omega_4 -
	                     (imu_filter_beta * SEqHatDot_4)) * IMU_FILTER_DELTA_T;

	// normalize quaternion
	norm = imu_inv_sqrt(imu_filter_seq_1 * imu_filter_seq_1 +
	                    imu_filter_seq_2 * imu_filter_seq_2 +
	                    imu_filter_seq_3 * imu_filter_seq_3 +
	                    imu_filter_seq_4 * imu_filter_seq_4);
	imu_filter_seq_1 *= norm;
	imu_filter_seq_2 *= norm;
	imu_filter_seq_3 *= norm;
	imu_filter_seq_4 *= norm;

	// compute flux in the earth frame
	SEq_1SEq_2 = imu_filter_seq_1 * imu_filter_seq_2;
	SEq_1SEq_3 = imu_filter_seq_1 * imu_filter_seq_3;
	SEq_1SEq_4 = imu_filter_seq_1 * imu_filter_seq_4;
	SEq_3SEq_4 = imu_filter_seq_3 * imu_filter_seq_4;
	SEq_2SEq_3 = imu_filter_seq_2 * imu_filter_seq_3;
	SEq_2SEq_4 = imu_filter_seq_2 * imu_filter_seq_4;

	const float h_x = twom_x * (0.5f -
	                            imu_filter_seq_3 * imu_filter_seq_3 -
	                            imu_filter_seq_4 * imu_filter_seq_4) +
	                  twom_y * (SEq_2SEq_3 - SEq_1SEq_4) +
	                  twom_z * (SEq_2SEq_4 + SEq_1SEq_3);

	const float h_y = twom_x * (SEq_2SEq_3 + SEq_1SEq_4) +
	                  twom_y * (0.5f -
	                            imu_filter_seq_2 * imu_filter_seq_2 -
	                            imu_filter_seq_4 * imu_filter_seq_4) +
	                  twom_z * (SEq_3SEq_4 - SEq_1SEq_2);

	const float h_z = twom_x * (SEq_2SEq_4 - SEq_1SEq_3) +
	                  twom_y * (SEq_3SEq_4 + SEq_1SEq_2) +
	                  twom_z * (0.5f -
	                            imu_filter_seq_2 * imu_filter_seq_2 -
	                            imu_filter_seq_3 * imu_filter_seq_3);

	// normalize the flux vector to have only components in the x and z
	imu_filter_b_x = sqrtf((h_x * h_x) + (h_y * h_y));
	imu_filter_b_z = h_z;

	imu_qua_w = imu_filter_seq_1;
	imu_qua_x = imu_filter_seq_2;
	imu_qua_y = imu_filter_seq_3;
	imu_qua_z = imu_filter_seq_4;
}
