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
extern uint32_t imu_period_counter[IMU_PERIOD_NUM];

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

void get_acceleration(const ComType com, const GetAcceleration *data) {
	GetAccelerationReturn gar;

	gar.header        = data->header;
	gar.header.length = sizeof(GetAccelerationReturn);
	gar.x             = imu_acc_x;
	gar.y             = imu_acc_y;
	gar.z             = imu_acc_z;

	send_blocking_with_timeout(&gar, sizeof(GetAccelerationReturn), com);
}

void get_magnetic_field(const ComType com, const GetMagneticField *data) {
	GetMagneticFieldReturn gmfr;

	gmfr.header        = data->header;
	gmfr.header.length = sizeof(GetMagneticFieldReturn);
	gmfr.x             = imu_mag_x;
	gmfr.y             = imu_mag_y;
	gmfr.z             = imu_mag_z;

	send_blocking_with_timeout(&gmfr, sizeof(GetMagneticFieldReturn), com);
}

void get_angular_velocity(const ComType com, const GetAngularVelocity *data) {
	GetAngularVelocityReturn gavr;

	gavr.header        = data->header;
	gavr.header.length = sizeof(GetAngularVelocityReturn);
	gavr.x             = imu_gyr_x;
	gavr.y             = imu_gyr_y;
	gavr.z             = imu_gyr_z;

	send_blocking_with_timeout(&gavr, sizeof(GetAngularVelocityReturn), com);
}

void get_all_data(const ComType com, const GetAllData *data) {
	GetAllDataReturn gadr;

	gadr.header        = data->header;
	gadr.header.length = sizeof(GetAllDataReturn);
	gadr.acc_x         = imu_acc_x;
	gadr.acc_y         = imu_acc_y;
	gadr.acc_z         = imu_acc_z;
	gadr.mag_x         = imu_mag_x;
	gadr.mag_y         = imu_mag_y;
	gadr.mag_z         = imu_mag_z;
	gadr.ang_x         = imu_gyr_x;
	gadr.ang_y         = imu_gyr_y;
	gadr.ang_z         = imu_gyr_z;
	gadr.temperature   = imu_gyr_temperature;

	send_blocking_with_timeout(&gadr, sizeof(GetAllDataReturn), com);
}

void get_orientation(const ComType com, const GetOrientation *data) {
	GetOrientationReturn gor;

	gor.header        = data->header;
	gor.header.length = sizeof(GetOrientationReturn);
	gor.roll          = imu_roll;
	gor.pitch         = imu_pitch;
	gor.yaw           = imu_yaw;

	send_blocking_with_timeout(&gor, sizeof(GetOrientationReturn), com);
}

void get_quaternion(const ComType com, const GetQuaternion *data) {
	GetQuaternionReturn gqr;

	gqr.header        = data->header;
	gqr.header.length = sizeof(GetQuaternionReturn);
	gqr.x             = imu_qua_x;
	gqr.y             = imu_qua_y;
	gqr.z             = imu_qua_z;
	gqr.w             = imu_qua_w;

	send_blocking_with_timeout(&gqr, sizeof(GetQuaternionReturn), com);
}

void get_imu_temperature(const ComType com, const GetIMUTemperature *data) {
	GetIMUTemperatureReturn gtr;

	gtr.header        = data->header;
	gtr.header.length = sizeof(GetIMUTemperatureReturn);
	gtr.temperature   = imu_gyr_temperature;


	send_blocking_with_timeout(&gtr, sizeof(GetIMUTemperatureReturn), com);
}

void leds_on(const ComType com, const LedsOn *data) {
	imu_leds_on(true);

	com_return_setter(com, data);
}

void leds_off(const ComType com, const LedsOff *data) {
	imu_leds_on(false);

	com_return_setter(com, data);
}

void are_leds_on(const ComType com, const AreLedsOn *data) {
	AreLedsOnReturn alor;

	alor.header        = data->header;
	alor.header.length = sizeof(AreLedsOnReturn);
	alor.leds          = imu_use_leds;

	send_blocking_with_timeout(&alor, sizeof(AreLedsOnReturn), com);
}

void set_acceleration_range(const ComType com, const SetAccelerationRange *data) {
	// TODO: I am an autogenerated function.
	//       Please implement me.

	com_return_setter(com, data);
}

void get_acceleration_range(const ComType com, const GetAccelerationRange *data) {
	// TODO: I am an autogenerated function.
	//       Please implement me.
	GetAccelerationRangeReturn garr;

	garr.header        = data->header;
	garr.header.length = sizeof(GetAccelerationRangeReturn);

	send_blocking_with_timeout(&garr, sizeof(GetAccelerationRangeReturn), com);
}

void set_magnetometer_range(const ComType com, const SetMagnetometerRange *data) {
	// TODO: I am an autogenerated function.
	//       Please implement me.

	com_return_setter(com, data);
}

void get_magnetometer_range(const ComType com, const GetMagnetometerRange *data) {
	// TODO: I am an autogenerated function.
	//       Please implement me.
	GetMagnetometerRangeReturn gmrr;

	gmrr.header        = data->header;
	gmrr.header.length = sizeof(GetMagnetometerRangeReturn);

	send_blocking_with_timeout(&gmrr, sizeof(GetMagnetometerRangeReturn), com);
}

void set_convergence_speed(const ComType com, const SetConvergenceSpeed *data) {
	imu_convergence_speed = data->speed;

	imu_filter_beta = SQRT3DIV4 * M_PI * (data->speed / 180.0f);
	imu_filter_zeta = SQRT3DIV4 * M_PI * ((data->speed/60.0f) / 180.0f);

	logimui("set_convergence_speed: %d\n\r", data->speed);

	com_return_setter(com, data);
}

void get_convergence_speed(const ComType com, const GetConvergenceSpeed *data) {
	GetConvergenceSpeedReturn gcsr;

	gcsr.header        = data->header;
	gcsr.header.length = sizeof(GetConvergenceSpeedReturn);
	gcsr.speed         = imu_convergence_speed;

	send_blocking_with_timeout(&gcsr, sizeof(GetConvergenceSpeedReturn), com);
	logimui("get_convergence_speed: %d\n\r", gcsr.speed);
}

void set_calibration(const ComType com, const SetCalibration *data) {
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
			int16_t t_high;
			if(data->data[3] < data->data[7]) {
				t_high = data->data[7];
			} else {
				t_high = data->data[3] + 1;
			}

			new_cal.imu_gyr_x_bias_low = data->data[0];
			new_cal.imu_gyr_y_bias_low = data->data[1];
			new_cal.imu_gyr_z_bias_low = data->data[2];
			new_cal.imu_gyr_temp_low = data->data[3];
			new_cal.imu_gyr_x_bias_high = data->data[4];
			new_cal.imu_gyr_y_bias_high = data->data[5];
			new_cal.imu_gyr_z_bias_high = data->data[6];
			new_cal.imu_gyr_temp_high = t_high;
			break;
		}

		default: {
			BA->com_return_error(data, sizeof(MessageHeader), MESSAGE_ERROR_CODE_INVALID_PARAMETER, com);
			return;
		}
	}

	imu_save_calibration(&new_cal);
	update_gyr_temperature_aprox();

	com_return_setter(com, data);
}

void get_calibration(const ComType com, const GetCalibration *data) {
	GetCalibrationReturn gcr;

	gcr.header        = data->header;
	gcr.header.length = sizeof(GetCalibrationReturn);

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
			gcr.data[0] = ic->imu_gyr_x_bias_low;
			gcr.data[1] = ic->imu_gyr_y_bias_low;
			gcr.data[2] = ic->imu_gyr_z_bias_low;
			gcr.data[3] = ic->imu_gyr_temp_low;
			gcr.data[4] = ic->imu_gyr_x_bias_high;
			gcr.data[5] = ic->imu_gyr_y_bias_high;
			gcr.data[6] = ic->imu_gyr_z_bias_high;
			gcr.data[7] = ic->imu_gyr_temp_high;
			break;
		}

		default: {
			BA->com_return_error(data, sizeof(GetCalibrationReturn), MESSAGE_ERROR_CODE_INVALID_PARAMETER, com);
			return;
		}
	}

	send_blocking_with_timeout(&gcr, sizeof(GetCalibrationReturn), com);
}

void set_acceleration_period(const ComType com, const SetAccelerationPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_ACC] = data->period;
	imu_period_counter[IMU_PERIOD_TYPE_ACC] = 0;
	logimui("set_acceleration_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ACC]);

	com_return_setter(com, data);
}

void get_acceleration_period(const ComType com, const GetAccelerationPeriod *data) {
	GetAccelerationPeriodReturn gapr;

	gapr.header        = data->header;
	gapr.header.length = sizeof(GetAccelerationPeriodReturn);
	gapr.period        = imu_period[IMU_PERIOD_TYPE_ACC];

	send_blocking_with_timeout(&gapr, sizeof(GetAccelerationPeriodReturn), com);
	logimui("get_acceleration_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ACC]);
}

void set_magnetic_field_period(const ComType com, const SetMagneticFieldPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_MAG] = data->period;
	imu_period_counter[IMU_PERIOD_TYPE_MAG] = 0;
	logimui("set_magnetic_field_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_MAG]);

	com_return_setter(com, data);
}

void get_magnetic_field_period(const ComType com, const GetMagneticFieldPeriod *data) {
	GetMagneticFieldPeriodReturn gmfpr;

	gmfpr.header        = data->header;
	gmfpr.header.length = sizeof(GetMagneticFieldPeriodReturn);
	gmfpr.period        = imu_period[IMU_PERIOD_TYPE_MAG];

	send_blocking_with_timeout(&gmfpr, sizeof(GetMagneticFieldPeriodReturn), com);
	logimui("get_magnetic_field_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_MAG]);
}

void set_angular_velocity_period(const ComType com, const SetAngularVelocityPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_ANG] = data->period;
	imu_period_counter[IMU_PERIOD_TYPE_ANG] = 0;
	logimui("set_angular_velocity_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ANG]);

	com_return_setter(com, data);
}

void get_angular_velocity_period(const ComType com, const GetAngularVelocityPeriod *data) {
	GetAngularVelocityPeriodReturn gavpr;

	gavpr.header        = data->header;
	gavpr.header.length = sizeof(GetAngularVelocityPeriodReturn);
	gavpr.period        = imu_period[IMU_PERIOD_TYPE_ANG];

	send_blocking_with_timeout(&gavpr, sizeof(GetAngularVelocityPeriodReturn), com);
	logimui("get_angular_velocity_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ANG]);
}

void set_all_data_period(const ComType com, const SetAllDataPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_ALL] = data->period;
	imu_period_counter[IMU_PERIOD_TYPE_ALL] = 0;
	logimui("set_all_data_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ALL]);

	com_return_setter(com, data);
}

void get_all_data_period(const ComType com, const GetAllDataPeriod *data) {
	GetAllDataPeriodReturn gadpr;

	gadpr.header        = data->header;
	gadpr.header.length = sizeof(GetAllDataPeriodReturn);
	gadpr.period        = imu_period[IMU_PERIOD_TYPE_ALL];

	send_blocking_with_timeout(&gadpr, sizeof(GetAllDataPeriodReturn), com);
	logimui("get_all_data_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ALL]);
}

void set_orientation_period(const ComType com, const SetOrientationPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_ORI] = data->period;
	imu_period_counter[IMU_PERIOD_TYPE_ORI] = 0;
	logimui("set_orientation_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ORI]);

	com_return_setter(com, data);
}

void get_orientation_period(const ComType com, const GetOrientationPeriod *data) {
	GetOrientationPeriodReturn gopr;

	gopr.header        = data->header;
	gopr.header.length = sizeof(GetAllDataPeriodReturn);
	gopr.period        = imu_period[IMU_PERIOD_TYPE_ORI];

	send_blocking_with_timeout(&gopr, sizeof(GetOrientationPeriodReturn), com);
	logimui("get_orientation_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_ORI]);
}

void set_quaternion_period(const ComType com, const SetQuaternionPeriod *data) {
	imu_period[IMU_PERIOD_TYPE_QUA] = data->period;
	imu_period_counter[IMU_PERIOD_TYPE_QUA] = 0;
	logimui("set_quaternion_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_QUA]);

	com_return_setter(com, data);
}

void get_quaternion_period(const ComType com, const GetQuaternionPeriod *data) {
	GetQuaternionPeriodReturn gqpr;

	gqpr.header        = data->header;
	gqpr.header.length = sizeof(GetQuaternionPeriodReturn);
	gqpr.period        = imu_period[IMU_PERIOD_TYPE_QUA];

	send_blocking_with_timeout(&gqpr, sizeof(GetQuaternionPeriodReturn), com);
	logimui("get_quaternion_period: %d\n\r", imu_period[IMU_PERIOD_TYPE_QUA]);
}
