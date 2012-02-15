/* imu-brick
 * Copyright (C) 2010-2012 Olaf Lüke <olaf@tinkerforge.com>
 *
 * communication.c: Implementation of IMU-Brick specific messages
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

#include "communication.h"

#include "imu.h"

#include "bricklib/logging/logging.h"
#include "bricklib/com/com_common.h"
#include "bricklib/drivers/pwmc/pwmc.h"
#include "bricklib/drivers/adc/adc.h"

#include "bricklib/utility/util_definitions.h"

#include <stdint.h>
#include <stdio.h>
#include <math.h>

extern uint32_t imu_period[IMU_PERIOD_NUM];

extern int16_t imu_acc_x;
extern int16_t imu_acc_y;
extern int16_t imu_acc_z;

extern int16_t imu_mag_x;
extern int16_t imu_mag_y;
extern int16_t imu_mag_z;

extern int16_t imu_gyr_x;
extern int16_t imu_gyr_y;
extern int16_t imu_gyr_z;

extern int16_t imu_pitch;
extern int16_t imu_roll;
extern int16_t imu_yaw;

extern float imu_qua_x;
extern float imu_qua_y;
extern float imu_qua_z;
extern float imu_qua_w;

extern int16_t imu_gyr_temperature;
extern bool imu_use_leds;

extern uint16_t imu_convergence_speed;
extern float imu_filter_beta;
extern float imu_filter_zeta;

extern const IMUCalibration *ic;

void get_acceleration(uint8_t com, const GetAcceleration *data) {
	GetAccelerationReturn gar;

	gar.stack_id      = data->stack_id;
	gar.type          = data->type;
	gar.length        = sizeof(GetAccelerationReturn);
	gar.x             = imu_acc_x;
	gar.y             = imu_acc_y;
	gar.z             = imu_acc_z;

	send_blocking_with_timeout(&gar, sizeof(GetAccelerationReturn), com);
}

void get_magnetic_field(uint8_t com, const GetMagneticField *data) {
	GetMagneticFieldReturn gmfr;

	gmfr.stack_id      = data->stack_id;
	gmfr.type          = data->type;
	gmfr.length        = sizeof(GetMagneticFieldReturn);
	gmfr.x             = imu_mag_x;
	gmfr.y             = imu_mag_y;
	gmfr.z             = imu_mag_z;

	send_blocking_with_timeout(&gmfr, sizeof(GetMagneticFieldReturn), com);
}

void get_angular_velocity(uint8_t com, const GetAngularVelocity *data) {
	GetAngularVelocityReturn gavr;

	gavr.stack_id      = data->stack_id;
	gavr.type          = data->type;
	gavr.length        = sizeof(GetAngularVelocityReturn);
	gavr.x             = imu_gyr_x;
	gavr.y             = imu_gyr_y;
	gavr.z             = imu_gyr_z;

	send_blocking_with_timeout(&gavr, sizeof(GetAngularVelocityReturn), com);
}

void get_all_data(uint8_t com, const GetAllData *data) {
	GetAllDataReturn gadr;

	gadr.stack_id      = data->stack_id;
	gadr.type          = data->type;
	gadr.length        = sizeof(GetAllDataReturn);
	gadr.acc_x         = imu_acc_x;
	gadr.acc_y         = imu_acc_y;
	gadr.acc_z         = imu_acc_z;
	gadr.mag_x         = imu_mag_x;
	gadr.mag_y         = imu_mag_y;
	gadr.mag_z         = imu_mag_z;
	gadr.ang_x         = imu_gyr_x;
	gadr.ang_y         = imu_gyr_y;
	gadr.ang_z         = imu_gyr_z;
	gadr.temperature   = 3500 + (imu_gyr_temperature*10 + 132000)/28;

	send_blocking_with_timeout(&gadr, sizeof(GetAllDataReturn), com);
}

void get_orientation(uint8_t com, const GetOrientation *data) {
//	imu_quaternion_to_orientation();
	GetOrientationReturn gor;

	gor.stack_id      = data->stack_id;
	gor.type          = data->type;
	gor.length        = sizeof(GetOrientationReturn);
	gor.roll          = imu_roll;
	gor.pitch         = imu_pitch;
	gor.yaw           = imu_yaw;

	send_blocking_with_timeout(&gor, sizeof(GetOrientationReturn), com);
}

void get_quaternion(uint8_t com, const GetQuaternion *data) {
	GetQuaternionReturn gqr;

	gqr.stack_id      = data->stack_id;
	gqr.type          = data->type;
	gqr.length        = sizeof(GetQuaternionReturn);
	gqr.x             = imu_qua_x;
	gqr.y             = imu_qua_y;
	gqr.z             = imu_qua_z;
	gqr.w             = imu_qua_w;

	send_blocking_with_timeout(&gqr, sizeof(GetQuaternionReturn), com);
}

void get_imu_temperature(uint8_t com, const GetIMUTemperature *data) {
	GetIMUTemperatureReturn gtr;

	gtr.stack_id      = data->stack_id;
	gtr.type          = data->type;
	gtr.length        = sizeof(GetIMUTemperatureReturn);
	gtr.temperature   = 3500 + (imu_gyr_temperature*10 + 132000)/28;


	send_blocking_with_timeout(&gtr, sizeof(GetIMUTemperatureReturn), com);
}

void leds_on(uint8_t com, const LedsOn *data) {
	imu_leds_on(true);
}

void leds_off(uint8_t com, const LedsOff *data) {
	imu_leds_on(false);
}

void are_leds_on(uint8_t com, const AreLedsOn *data) {
	AreLedsOnReturn alor;

	alor.stack_id      = data->stack_id;
	alor.type          = data->type;
	alor.length        = sizeof(AreLedsOnReturn);
	alor.leds          = imu_use_leds;

	send_blocking_with_timeout(&alor, sizeof(AreLedsOnReturn), com);
}

void set_acceleration_range(uint8_t com, const SetAccelerationRange *data) {
	// TODO: I am an autogenerated function.
	//       Please implement me.
}

void get_acceleration_range(uint8_t com, const GetAccelerationRange *data) {
	// TODO: I am an autogenerated function.
	//       Please implement me.
	GetAccelerationRangeReturn garr;

	garr.stack_id      = data->stack_id;
	garr.type          = data->type;
	garr.length        = sizeof(GetAccelerationRangeReturn);

	send_blocking_with_timeout(&garr, sizeof(GetAccelerationRangeReturn), com);
}

void set_magnetometer_range(uint8_t com, const SetMagnetometerRange *data) {
	// TODO: I am an autogenerated function.
	//       Please implement me.
}

void get_magnetometer_range(uint8_t com, const GetMagnetometerRange *data) {
	// TODO: I am an autogenerated function.
	//       Please implement me.
	GetMagnetometerRangeReturn gmrr;

	gmrr.stack_id      = data->stack_id;
	gmrr.type          = data->type;
	gmrr.length        = sizeof(GetMagnetometerRangeReturn);

	send_blocking_with_timeout(&gmrr, sizeof(GetMagnetometerRangeReturn), com);
}

void set_convergence_speed(uint8_t com, const SetConvergenceSpeed *data) {
	imu_convergence_speed = data->speed;

	imu_filter_beta = SQRT3DIV4 * M_PI * (data->speed / 180.0f);
	imu_filter_zeta = SQRT3DIV4 * M_PI * ((data->speed/60.0f) / 180.0f);

	logimui("set_convergence_speed: %d\n\r", data->speed);
}

void get_convergence_speed(uint8_t com, const GetConvergenceSpeed *data) {
	GetConvergenceSpeedReturn gcsr;

	gcsr.stack_id      = data->stack_id;
	gcsr.type          = data->type;
	gcsr.length        = sizeof(GetConvergenceSpeedReturn);
	gcsr.speed         = imu_convergence_speed;

	send_blocking_with_timeout(&gcsr, sizeof(GetConvergenceSpeedReturn), com);
	logimui("get_convergence_speed: %d\n\r", gcsr.speed);
}

void set_calibration(uint8_t com, const SetCalibration *data) {
	IMUCalibrationNonConst new_cal;
	imu_fill_calibration(&new_cal);

	switch(data->type_data) {
		case IMU_CALIBRATION_TYPE_ACC_GAIN: {
			new_cal.imu_acc_x_gain_multiplier = data->data[0];
			new_cal.imu_acc_y_gain_multiplier = data->data[1];
			new_cal.imu_acc_z_gain_multiplier = data->data[2];
			new_cal.imu_acc_x_gain_divider = data->data[3];
			new_cal.imu_acc_y_gain_divider = data->data[4];
			new_cal.imu_acc_z_gain_divider = data->data[5];
			break;
		}

		case IMU_CALIBRATION_TYPE_ACC_BIAS: {
			new_cal.imu_acc_x_bias = data->data[0];
			new_cal.imu_acc_y_bias = data->data[1];
			new_cal.imu_acc_z_bias = data->data[2];
			break;
		}

		case IMU_CALIBRATION_TYPE_MAG_GAIN: {
			new_cal.imu_mag_x_gain_multiplier = data->data[0];
			new_cal.imu_mag_y_gain_multiplier = data->data[1];
			new_cal.imu_mag_z_gain_multiplier = data->data[2];
			new_cal.imu_mag_x_gain_divider = data->data[3];
			new_cal.imu_mag_y_gain_divider = data->data[4];
			new_cal.imu_mag_z_gain_divider = data->data[5];
			break;
		}

		case IMU_CALIBRATION_TYPE_MAG_BIAS: {
			new_cal.imu_mag_x_bias = data->data[0];
			new_cal.imu_mag_y_bias = data->data[1];
			new_cal.imu_mag_z_bias = data->data[2];
			break;
		}

		case IMU_CALIBRATION_TYPE_GYR_GAIN: {
			new_cal.imu_gyr_x_gain_multiplier = data->data[0];
			new_cal.imu_gyr_y_gain_multiplier = data->data[1];
			new_cal.imu_gyr_z_gain_multiplier = data->data[2];
			new_cal.imu_gyr_x_gain_divider = data->data[3];
			new_cal.imu_gyr_y_gain_divider = data->data[4];
			new_cal.imu_gyr_z_gain_divider = data->data[5];
			break;
		}

		case IMU_CALIBRATION_TYPE_GYR_BIAS: {
			new_cal.imu_gyr_x_bias = data->data[0];
			new_cal.imu_gyr_y_bias = data->data[1];
			new_cal.imu_gyr_z_bias = data->data[2];
			break;
		}

		default: {
			return;
		}
	}

	imu_save_calibration(&new_cal);
}

void get_calibration(uint8_t com, const GetCalibration *data) {
	GetCalibrationReturn gcr;

	gcr.stack_id      = data->stack_id;
	gcr.type          = data->type;
	gcr.length        = sizeof(GetCalibrationReturn);

	switch(data->type_data) {
		case IMU_CALIBRATION_TYPE_ACC_GAIN: {
			gcr.data[0] = ic->imu_acc_x_gain_multiplier;
			gcr.data[1] = ic->imu_acc_y_gain_multiplier;
			gcr.data[2] = ic->imu_acc_z_gain_multiplier;
			gcr.data[3] = ic->imu_acc_x_gain_divider;
			gcr.data[4] = ic->imu_acc_y_gain_divider;
			gcr.data[5] = ic->imu_acc_z_gain_divider;
			break;
		}

		case IMU_CALIBRATION_TYPE_ACC_BIAS: {
			gcr.data[0] = ic->imu_acc_x_bias;
			gcr.data[1] = ic->imu_acc_y_bias;
			gcr.data[2] = ic->imu_acc_z_bias;
			break;
		}

		case IMU_CALIBRATION_TYPE_MAG_GAIN: {
			gcr.data[0] = ic->imu_mag_x_gain_multiplier;
			gcr.data[1] = ic->imu_mag_y_gain_multiplier;
			gcr.data[2] = ic->imu_mag_z_gain_multiplier;
			gcr.data[3] = ic->imu_mag_x_gain_divider;
			gcr.data[4] = ic->imu_mag_y_gain_divider;
			gcr.data[5] = ic->imu_mag_z_gain_divider;
			break;
		}

		case IMU_CALIBRATION_TYPE_MAG_BIAS: {
			gcr.data[0] = ic->imu_mag_x_bias;
			gcr.data[1] = ic->imu_mag_y_bias;
			gcr.data[2] = ic->imu_mag_z_bias;
			break;
		}

		case IMU_CALIBRATION_TYPE_GYR_GAIN: {
			gcr.data[0] = ic->imu_gyr_x_gain_multiplier;
			gcr.data[1] = ic->imu_gyr_y_gain_multiplier;
			gcr.data[2] = ic->imu_gyr_z_gain_multiplier;
			gcr.data[3] = ic->imu_gyr_x_gain_divider;
			gcr.data[4] = ic->imu_gyr_y_gain_divider;
			gcr.data[5] = ic->imu_gyr_z_gain_divider;
			break;
		}

		case IMU_CALIBRATION_TYPE_GYR_BIAS: {
			gcr.data[0] = ic->imu_gyr_x_bias;
			gcr.data[1] = ic->imu_gyr_y_bias;
			gcr.data[2] = ic->imu_gyr_z_bias;
			break;
		}
	}

	send_blocking_with_timeout(&gcr, sizeof(GetCalibrationReturn), com);
}

void set_acceleration_period(uint8_t com, const SetAccelerationPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_ACC] = data->period;
	logimui("set_acceleration_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ACC]);
}

void get_acceleration_period(uint8_t com, const GetAccelerationPeriod *data) {
	GetAccelerationPeriodReturn gapr;

	gapr.stack_id      = data->stack_id;
	gapr.type          = data->type;
	gapr.length        = sizeof(GetAccelerationPeriodReturn);
	gapr.period        = imu_period[IMU_PERIOD_TYPE_ACC];

	send_blocking_with_timeout(&gapr, sizeof(GetAccelerationPeriodReturn), com);
	logimui("get_acceleration_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ACC]);
}

void set_magnetic_field_period(uint8_t com, const SetMagneticFieldPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_MAG] = data->period;
	logimui("set_magnetic_field_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_MAG]);
}

void get_magnetic_field_period(uint8_t com, const GetMagneticFieldPeriod *data) {
	GetMagneticFieldPeriodReturn gmfpr;

	gmfpr.stack_id      = data->stack_id;
	gmfpr.type          = data->type;
	gmfpr.length        = sizeof(GetMagneticFieldPeriodReturn);
	gmfpr.period        = imu_period[IMU_PERIOD_TYPE_MAG];

	send_blocking_with_timeout(&gmfpr, sizeof(GetMagneticFieldPeriodReturn), com);
	logimui("get_magnetic_field_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_MAG]);
}

void set_angular_velocity_period(uint8_t com, const SetAngularVelocityPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_ANG] = data->period;
	logimui("set_angular_velocity_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ANG]);
}

void get_angular_velocity_period(uint8_t com, const GetAngularVelocityPeriod *data) {
	GetAngularVelocityPeriodReturn gavpr;

	gavpr.stack_id      = data->stack_id;
	gavpr.type          = data->type;
	gavpr.length        = sizeof(GetAngularVelocityPeriodReturn);
	gavpr.period        = imu_period[IMU_PERIOD_TYPE_ANG];

	send_blocking_with_timeout(&gavpr, sizeof(GetAngularVelocityPeriodReturn), com);
	logimui("get_angular_velocity_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ANG]);
}

void set_all_data_period(uint8_t com, const SetAllDataPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_ALL] = data->period;
	logimui("set_all_data_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ALL]);
}

void get_all_data_period(uint8_t com, const GetAllDataPeriod *data) {
	GetAllDataPeriodReturn gadpr;

	gadpr.stack_id      = data->stack_id;
	gadpr.type          = data->type;
	gadpr.length        = sizeof(GetAllDataPeriodReturn);
	gadpr.period        = imu_period[IMU_PERIOD_TYPE_ALL];

	send_blocking_with_timeout(&gadpr, sizeof(GetAllDataPeriodReturn), com);
	logimui("get_all_data_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ALL]);
}

void set_orientation_period(uint8_t com, const SetOrientationPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_ORI] = data->period;
	logimui("set_orientation_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ORI]);
}

void get_orientation_period(uint8_t com, const GetOrientationPeriod *data) {
	GetOrientationPeriodReturn gopr;

	gopr.stack_id      = data->stack_id;
	gopr.type          = data->type;
	gopr.length        = sizeof(GetOrientationPeriodReturn);
	gopr.period        = imu_period[IMU_PERIOD_TYPE_ORI];

	send_blocking_with_timeout(&gopr, sizeof(GetOrientationPeriodReturn), com);
	logimui("get_orientation_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ORI]);
}

void set_quaternion_period(uint8_t com, const SetQuaternionPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_QUA] = data->period;
	logimui("set_quaternion_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_QUA]);
}

void get_quaternion_period(uint8_t com, const GetQuaternionPeriod *data) {
	GetQuaternionPeriodReturn gqpr;

	gqpr.stack_id      = data->stack_id;
	gqpr.type          = data->type;
	gqpr.length        = sizeof(GetQuaternionPeriodReturn);
	gqpr.period        = imu_period[IMU_PERIOD_TYPE_QUA];

	send_blocking_with_timeout(&gqpr, sizeof(GetQuaternionPeriodReturn), com);
	logimui("get_quaternion_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_QUA]);
}

