/* imu-brick
 * Copyright (C) 2010-2012 Olaf Lüke <olaf@tinkerforge.com>
 *
 * communication.h: Implementation of IMU-Brick specific messages
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


#ifndef COMMUNICATION_H
#define COMMUNICATION_H

#include <stdint.h>
#include <stdbool.h>

#define TYPE_GET_ACCELERATION 1
#define TYPE_GET_MAGNETIC_FIELD 2
#define TYPE_GET_ANGULAR_VELOCITY 3
#define TYPE_GET_ALL_DATA 4
#define TYPE_GET_ORIENTATION 5
#define TYPE_GET_QUATERNION 6
#define TYPE_GET_IMU_TEMPERATURE 7
#define TYPE_LEDS_ON 8
#define TYPE_LEDS_OFF 9
#define TYPE_ARE_LEDS_ON 10
#define TYPE_SET_ACCELERATION_RANGE 11
#define TYPE_GET_ACCELERATION_RANGE 12
#define TYPE_SET_MAGNETOMETER_RANGE 13
#define TYPE_GET_MAGNETOMETER_RANGE 14
#define TYPE_SET_CONVERGENCE_SPEED 15
#define TYPE_GET_CONVERGENCE_SPEED 16
#define TYPE_SET_CALIBRATION 17
#define TYPE_GET_CALIBRATION 18
#define TYPE_SET_ACCELERATION_PERIOD 19
#define TYPE_GET_ACCELERATION_PERIOD 20
#define TYPE_SET_MAGNETIC_FIELD_PERIOD 21
#define TYPE_GET_MAGNETIC_FIELD_PERIOD 22
#define TYPE_SET_ANGULAR_VELOCITY_PERIOD 23
#define TYPE_GET_ANGULAR_VELOCITY_PERIOD 24
#define TYPE_SET_ALL_DATA_PERIOD 25
#define TYPE_GET_ALL_DATA_PERIOD 26
#define TYPE_SET_ORIENTATION_PERIOD 27
#define TYPE_GET_ORIENTATION_PERIOD 28
#define TYPE_SET_QUATERNION_PERIOD 29
#define TYPE_GET_QUATERNION_PERIOD 30
#define TYPE_ACCELERATION 31
#define TYPE_MAGNETIC_FIELD 32
#define TYPE_ANGULAR_VELOCITY 33
#define TYPE_ALL_DATA 34
#define TYPE_ORIENTATION 35
#define TYPE_QUATERNION 36

#define COM_MESSAGES_USER \
	{TYPE_GET_ACCELERATION, (message_handler_func_t)get_acceleration}, \
	{TYPE_GET_MAGNETIC_FIELD, (message_handler_func_t)get_magnetic_field}, \
	{TYPE_GET_ANGULAR_VELOCITY, (message_handler_func_t)get_angular_velocity}, \
	{TYPE_GET_ALL_DATA, (message_handler_func_t)get_all_data}, \
	{TYPE_GET_ORIENTATION, (message_handler_func_t)get_orientation}, \
	{TYPE_GET_QUATERNION, (message_handler_func_t)get_quaternion}, \
	{TYPE_GET_IMU_TEMPERATURE, (message_handler_func_t)get_imu_temperature}, \
	{TYPE_LEDS_ON, (message_handler_func_t)leds_on}, \
	{TYPE_LEDS_OFF, (message_handler_func_t)leds_off}, \
	{TYPE_ARE_LEDS_ON, (message_handler_func_t)are_leds_on}, \
	{TYPE_SET_ACCELERATION_RANGE, (message_handler_func_t)set_acceleration_range}, \
	{TYPE_GET_ACCELERATION_RANGE, (message_handler_func_t)get_acceleration_range}, \
	{TYPE_SET_MAGNETOMETER_RANGE, (message_handler_func_t)set_magnetometer_range}, \
	{TYPE_GET_MAGNETOMETER_RANGE, (message_handler_func_t)get_magnetometer_range}, \
	{TYPE_SET_CONVERGENCE_SPEED, (message_handler_func_t)set_convergence_speed}, \
	{TYPE_GET_CONVERGENCE_SPEED, (message_handler_func_t)get_convergence_speed}, \
	{TYPE_SET_CALIBRATION, (message_handler_func_t)set_calibration}, \
	{TYPE_GET_CALIBRATION, (message_handler_func_t)get_calibration}, \
	{TYPE_SET_ACCELERATION_PERIOD, (message_handler_func_t)set_acceleration_period}, \
	{TYPE_GET_ACCELERATION_PERIOD, (message_handler_func_t)get_acceleration_period}, \
	{TYPE_SET_MAGNETIC_FIELD_PERIOD, (message_handler_func_t)set_magnetic_field_period}, \
	{TYPE_GET_MAGNETIC_FIELD_PERIOD, (message_handler_func_t)get_magnetic_field_period}, \
	{TYPE_SET_ANGULAR_VELOCITY_PERIOD, (message_handler_func_t)set_angular_velocity_period}, \
	{TYPE_GET_ANGULAR_VELOCITY_PERIOD, (message_handler_func_t)get_angular_velocity_period}, \
	{TYPE_SET_ALL_DATA_PERIOD, (message_handler_func_t)set_all_data_period}, \
	{TYPE_GET_ALL_DATA_PERIOD, (message_handler_func_t)get_all_data_period}, \
	{TYPE_SET_ORIENTATION_PERIOD, (message_handler_func_t)set_orientation_period}, \
	{TYPE_GET_ORIENTATION_PERIOD, (message_handler_func_t)get_orientation_period}, \
	{TYPE_SET_QUATERNION_PERIOD, (message_handler_func_t)set_quaternion_period}, \
	{TYPE_GET_QUATERNION_PERIOD, (message_handler_func_t)get_quaternion_period},


typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetAcceleration;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t x;
	int16_t y;
	int16_t z;
} __attribute__((__packed__)) GetAccelerationReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetMagneticField;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t x;
	int16_t y;
	int16_t z;
} __attribute__((__packed__)) GetMagneticFieldReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetAngularVelocity;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t x;
	int16_t y;
	int16_t z;
} __attribute__((__packed__)) GetAngularVelocityReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetAllData;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t acc_x;
	int16_t acc_y;
	int16_t acc_z;
	int16_t mag_x;
	int16_t mag_y;
	int16_t mag_z;
	int16_t ang_x;
	int16_t ang_y;
	int16_t ang_z;
	int16_t temperature;
} __attribute__((__packed__)) GetAllDataReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetOrientation;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t roll;
	int16_t pitch;
	int16_t yaw;
} __attribute__((__packed__)) GetOrientationReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetQuaternion;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	float x;
	float y;
	float z;
	float w;
} __attribute__((__packed__)) GetQuaternionReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetIMUTemperature;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t temperature;
} __attribute__((__packed__)) GetIMUTemperatureReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) LedsOn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) LedsOff;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) AreLedsOn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	bool leds;
} __attribute__((__packed__)) AreLedsOnReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint8_t range;
} __attribute__((__packed__)) SetAccelerationRange;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetAccelerationRange;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint8_t range;
} __attribute__((__packed__)) GetAccelerationRangeReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint8_t range;
} __attribute__((__packed__)) SetMagnetometerRange;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetMagnetometerRange;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint8_t range;
} __attribute__((__packed__)) GetMagnetometerRangeReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint16_t speed;
} __attribute__((__packed__)) SetConvergenceSpeed;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetConvergenceSpeed;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint16_t speed;
} __attribute__((__packed__)) GetConvergenceSpeedReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint8_t type_data;
	uint16_t data[10];
} __attribute__((__packed__)) SetCalibration;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint8_t type_data;
} __attribute__((__packed__)) GetCalibration;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint16_t data[10];
} __attribute__((__packed__)) GetCalibrationReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) SetAccelerationPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetAccelerationPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) GetAccelerationPeriodReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) SetMagneticFieldPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetMagneticFieldPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) GetMagneticFieldPeriodReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) SetAngularVelocityPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetAngularVelocityPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) GetAngularVelocityPeriodReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) SetAllDataPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetAllDataPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) GetAllDataPeriodReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) SetOrientationPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetOrientationPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) GetOrientationPeriodReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) SetQuaternionPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
} __attribute__((__packed__)) GetQuaternionPeriod;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	uint32_t period;
} __attribute__((__packed__)) GetQuaternionPeriodReturn;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t x;
	int16_t y;
	int16_t z;
} __attribute__((__packed__)) AccelerationSignal;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t x;
	int16_t y;
	int16_t z;
} __attribute__((__packed__)) MagneticFieldSignal;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t x;
	int16_t y;
	int16_t z;
} __attribute__((__packed__)) AngularVelocitySignal;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t acc_x;
	int16_t acc_y;
	int16_t acc_z;
	int16_t mag_x;
	int16_t mag_y;
	int16_t mag_z;
	int16_t ang_x;
	int16_t ang_y;
	int16_t ang_z;
	int16_t temperature;
} __attribute__((__packed__)) AllDataSignal;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	int16_t roll;
	int16_t pitch;
	int16_t yaw;
} __attribute__((__packed__)) OrientationSignal;

typedef struct {
	uint8_t stack_id;
	uint8_t type;
	uint16_t length;
	float x;
	float y;
	float z;
	float w;
} __attribute__((__packed__)) QuaternionSignal;


void get_acceleration(uint8_t com, const GetAcceleration *data);
void get_magnetic_field(uint8_t com, const GetMagneticField *data);
void get_angular_velocity(uint8_t com, const GetAngularVelocity *data);
void get_all_data(uint8_t com, const GetAllData *data);
void get_orientation(uint8_t com, const GetOrientation *data);
void get_quaternion(uint8_t com, const GetQuaternion *data);
void get_imu_temperature(uint8_t com, const GetIMUTemperature *data);
void leds_on(uint8_t com, const LedsOn *data);
void leds_off(uint8_t com, const LedsOff *data);
void are_leds_on(uint8_t com, const AreLedsOn *data);
void set_acceleration_range(uint8_t com, const SetAccelerationRange *data);
void get_acceleration_range(uint8_t com, const GetAccelerationRange *data);
void set_magnetometer_range(uint8_t com, const SetMagnetometerRange *data);
void get_magnetometer_range(uint8_t com, const GetMagnetometerRange *data);
void set_convergence_speed(uint8_t com, const SetConvergenceSpeed *data);
void get_convergence_speed(uint8_t com, const GetConvergenceSpeed *data);
void set_calibration(uint8_t com, const SetCalibration *data);
void get_calibration(uint8_t com, const GetCalibration *data);
void set_acceleration_period(uint8_t com, const SetAccelerationPeriod *data);
void get_acceleration_period(uint8_t com, const GetAccelerationPeriod *data);
void set_magnetic_field_period(uint8_t com, const SetMagneticFieldPeriod *data);
void get_magnetic_field_period(uint8_t com, const GetMagneticFieldPeriod *data);
void set_angular_velocity_period(uint8_t com, const SetAngularVelocityPeriod *data);
void get_angular_velocity_period(uint8_t com, const GetAngularVelocityPeriod *data);
void set_all_data_period(uint8_t com, const SetAllDataPeriod *data);
void get_all_data_period(uint8_t com, const GetAllDataPeriod *data);
void set_orientation_period(uint8_t com, const SetOrientationPeriod *data);
void get_orientation_period(uint8_t com, const GetOrientationPeriod *data);
void set_quaternion_period(uint8_t com, const SetQuaternionPeriod *data);
void get_quaternion_period(uint8_t com, const GetQuaternionPeriod *data);

#endif
