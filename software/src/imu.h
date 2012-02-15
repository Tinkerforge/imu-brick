/* imu-brick
 * Copyright (C) 2011-2012 Olaf Lüke <olaf@tinkerforge.com>
 *
 * imu.h: Inertial Measurement Unit readout implementation
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

#ifndef IMU_H
#define IMU_H

#include <stdint.h>
#include "config.h"

#include "bricklib/drivers/twi/twid.h"

#define IMU_DEFAULT_CONVERGENCE_SPEED 30

// sampling period in seconds (2 ms)
#define IMU_FILTER_DELTA_T 0.002f

// sqrtf(3.0f / 4.0f)
#define SQRT3DIV4 0.8660254037844386

#define IMU_PERIOD_TYPE_ACC               0
#define IMU_PERIOD_TYPE_MAG               1
#define IMU_PERIOD_TYPE_ANG               2
#define IMU_PERIOD_TYPE_ALL               3
#define IMU_PERIOD_TYPE_ORI               4
#define IMU_PERIOD_TYPE_QUA               5

#define IMU_PERIOD_NUM                    6

#define IMU_STARTUP_TIME                  70 // 70ms for startup

// LSM Address
#define LSM_I2C_ACC_ADDRESS               24
#define LSM_I2C_MAG_ADDRESS               30

// LSM Register
#define LSM_REGISTER_ACC_CTRL1            0x20
#define LSM_REGISTER_ACC_CTRL2            0x21
#define LSM_REGISTER_ACC_CTRL3            0x22
#define LSM_REGISTER_ACC_CTRL4            0x23
#define LSM_REGISTER_ACC_CTRL5            0x24
#define LSM_REGISTER_ACC_HP_FILTER_RESET  0x25
#define LSM_REGISTER_ACC_REFERENCE        0x26
#define LSM_REGISTER_ACC_STATUS           0x27
#define LSM_REGISTER_ACC_X_L              0x28
#define LSM_REGISTER_ACC_X_H              0x29
#define LSM_REGISTER_ACC_Y_L              0x2A
#define LSM_REGISTER_ACC_Y_H              0x2B
#define LSM_REGISTER_ACC_Z_L              0x2C
#define LSM_REGISTER_ACC_Z_H              0x2D
#define LSM_REGISTER_ACC_INT1_CFG         0x30
#define LSM_REGISTER_ACC_INT1_SOURCE      0x31
#define LSM_REGISTER_ACC_INT1_THS         0x32
#define LSM_REGISTER_ACC_INT1_DURATION    0x33
#define LSM_REGISTER_ACC_INT2_CFG         0x34
#define LSM_REGISTER_ACC_INT2_SOURCE      0x35
#define LSM_REGISTER_ACC_INT2_THS         0x36
#define LSM_REGISTER_ACC_INT2_DURATION    0x37
#define LSM_REGISTER_MAG_CRA              0x00
#define LSM_REGISTER_MAG_CRB              0x01
#define LSM_REGISTER_MAG_MR               0x02
#define LSM_REGISTER_MAG_X_H              0x03
#define LSM_REGISTER_MAG_X_L              0x04
#define LSM_REGISTER_MAG_Y_H              0x05
#define LSM_REGISTER_MAG_Y_L              0x06
#define LSM_REGISTER_MAG_Z_H              0x07
#define LSM_REGISTER_MAG_Z_L              0x08
#define LSM_REGISTER_MAG_SR               0x09
#define LSM_REGISTER_MAG_IRA              0x0A
#define LSM_REGISTER_MAG_IRB              0x0B
#define LSM_REGISTER_MAG_IRC              0x0C

#define LSM_ACC_READ_INCREMENTAL          (1 << 7)

// LSM ACC CTRL1 Register values
#define LSM_ACC_CTRL1_X_EN                (1 << 0)
#define LSM_ACC_CTRL1_Y_EN                (1 << 1)
#define LSM_ACC_CTRL1_Z_EN                (1 << 2)
#define LSM_ACC_CTRL1_FREQ_50             (0 << 3)
#define LSM_ACC_CTRL1_FREQ_100            (1 << 3)
#define LSM_ACC_CTRL1_FREQ_400            (2 << 3)
#define LSM_ACC_CTRL1_FREQ_1000           (3 << 3)
#define LSM_ACC_CTRL1_POWER_DOWN          (0 << 5)
#define LSM_ACC_CTRL1_POWER_NORMAL        (1 << 5)
#define LSM_ACC_CTRL1_POWER_LOW_0_5       (2 << 5)
#define LSM_ACC_CTRL1_POWER_LOW_1         (3 << 5)
#define LSM_ACC_CTRL1_POWER_LOW_2         (4 << 5)
#define LSM_ACC_CTRL1_POWER_LOW_5         (5 << 5)
#define LSM_ACC_CTRL1_POWER_LOW_10        (6 << 5)

// LSM ACC CTRL2 Register values
#define LSM_ACC_CTRL2_HPFC_00             (0)
#define LSM_ACC_CTRL2_HPFC_01             (1)
#define LSM_ACC_CTRL2_HPFC_10             (2)
#define LSM_ACC_CTRL2_HPFC_11             (3)
#define LSM_ACC_CTRL2_HP_EN1              (1 << 2)
#define LSM_ACC_CTRL2_HP_EN2              (1 << 3)
#define LSM_ACC_CTRL2_FDS                 (1 << 4)
#define LSM_ACC_CTRL2_HP_NORMAL           (0 << 5)
#define LSM_ACC_CTRL2_HP_REFERENCE        (1 << 5)
#define LSM_ACC_CTRL2_REBOOT              (1 << 7)

// LSM ACC CTRL3 Register values
#define LSM_ACC_CTRL3_INT_ACTIVE_HIGH     (0 << 0)
#define LSM_ACC_CTRL3_INT_ACTIVE_LOW      (1 << 0)
#define LSM_ACC_CTRL3_INT_PUSH_PULL       (0 << 1)
#define LSM_ACC_CTRL3_INT_OPEN_DRAIN      (1 << 1)
#define LSM_ACC_CTRL3_LIR2                (1 << 3)
#define LSM_ACC_CTRL3_INT2_2_ONLY         (0 << 4)
#define LSM_ACC_CTRL3_INT2_1_AND_2        (1 << 4)
#define LSM_ACC_CTRL3_INT2_DATA_READY     (2 << 4)
#define LSM_ACC_CTRL3_INT2_BOOT_RUNNING   (3 << 4)
#define LSM_ACC_CTRL3_LIR1                (1 << 5)
#define LSM_ACC_CTRL3_INT1_1_ONLY         (0 << 6)
#define LSM_ACC_CTRL3_INT1_1_AND_2        (1 << 6)
#define LSM_ACC_CTRL3_INT1_DATA_READY     (2 << 6)
#define LSM_ACC_CTRL3_INT1_BOOT_RUNNING   (3 << 6)

// LSM ACC CTRL4 Register values
#define LSM_ACC_CTRL4_SELF_TEST_EN        (1 << 1)
#define LSM_ACC_CTRL4_SELF_TEST_SP        (0 << 3)
#define LSM_ACC_CTRL4_SELF_TEST_SM        (1 << 3)
#define LSM_ACC_CTRL4_SCALE_2G            (0 << 4)
#define LSM_ACC_CTRL4_SCALE_4G            (1 << 4)
#define LSM_ACC_CTRL4_SCALE_8G            (3 << 4)
#define LSM_ACC_CTRL4_DATA_LSB            (0 << 6)
#define LSM_ACC_CTRL4_DATA_MSB            (1 << 6)
#define LSM_ACC_CTRL4_DATA_BDU            (1 << 7)

// LSM ACC CTRL5 Register values
#define LSM_ACC_CTRL5_SLEEP_TO_WAKE       (0)
#define LSM_ACC_CTRL5_TURNED_ON           (3)

// LSM MAG CRA Register values
#define LSM_MAG_CRA_FREQ_0_75             (0)
#define LSM_MAG_CRA_FREQ_1_5              (1)
#define LSM_MAG_CRA_FREQ_3                (2)
#define LSM_MAG_CRA_FREQ_7_5              (3)
#define LSM_MAG_CRA_FREQ_15               (4)
#define LSM_MAG_CRA_FREQ_30               (5)
#define LSM_MAG_CRA_FREQ_75               (6)

// LSM MAG CRB Register values
#define LSM_MAG_CRB_RANGE_1_3             (1 << 5)
#define LSM_MAG_CRB_RANGE_1_9             (2 << 5)
#define LSM_MAG_CRB_RANGE_2_5             (3 << 5)
#define LSM_MAG_CRB_RANGE_4_0             (4 << 5)
#define LSM_MAG_CRB_RANGE_4_7             (5 << 5)
#define LSM_MAG_CRB_RANGE_5_6             (6 << 5)
#define LSM_MAG_CRB_RANGE_8_1             (7 << 5)

// LSM MAG MR Register values
#define LSM_MAG_MR_CONTINUOUS_CONVERSION  (0)
#define LSM_MAG_MR_SINGLE_CONVERSION      (1)
#define LSM_MAG_MR_SLEEP_MODE             (3)


// ITG Address
#define ITG_I2C_GYR_ADDRESS               0x68

// ITG Register
#define ITG_REGISTER_WHO_AM_I             0x0
#define ITG_REGISTER_SMPLRT_DIV           0x15
#define ITG_REGISTER_DLPF_FS              0x16
#define ITG_REGISTER_INT_CFG              0x17
#define ITG_REGISTER_INT_STATUS           0x1A
#define ITG_REGISTER_TEMP_H               0x1B
#define ITG_REGISTER_TEMP_L               0x1C
#define ITG_REGISTER_GYRO_X_H             0x1D
#define ITG_REGISTER_GYRO_X_L             0x1E
#define ITG_REGISTER_GYRO_Y_H             0x1F
#define ITG_REGISTER_GYRO_Y_L             0x20
#define ITG_REGISTER_GYRO_Z_H             0x21
#define ITG_REGISTER_GYRO_Z_L             0x22
#define ITG_REGISTER_PWR_MGM              0x3E

// ITG DLPF FS Register values
#define ITG_DLPF_FS_FS_SEL                (3 << 3)
#define ITG_DLPF_FS_DLPF_CFG_256_8        (0 << 0)
#define ITG_DLPF_FS_DLPF_CFG_188_1        (0 << 1)
#define ITG_DLPF_FS_DLPF_CFG_098_1        (0 << 2)
#define ITG_DLPF_FS_DLPF_CFG_042_1        (0 << 3)
#define ITG_DLPF_FS_DLPF_CFG_020_1        (0 << 4)
#define ITG_DLPF_FS_DLPF_CFG_010_1        (0 << 5)
#define ITG_DLPF_FS_DLPF_CFG_005_1        (0 << 6)

// ITG INT CFG Register values
#define ITG_INT_CFG_RAW_RDY_EN            (1 << 0)
#define ITG_INT_CFG_ITG_RDY_EN            (1 << 2)
#define ITG_INT_CFG_INT_ANYRD_2CLEAR      (1 << 4)
#define ITG_INT_CFG_LATCH_INT_EN          (1 << 5)
#define ITG_INT_CFG_OPEN_DRAIN            (1 << 6)
#define ITG_INT_CFG_PUSH_PULL             (0 << 6)
#define ITG_INT_CFG_ACTIVE_LOW            (1 << 7)
#define ITG_INT_CFG_ACTIVE_HIGH           (0 << 7)

// ITG INT STATUS Register values
#define ITG_INT_STATUS_RAW_DATA_RDY       (1 << 0)
#define ITG_INT_STATUS_ITG_RDY            (1 << 2)

// ITG PWER MGN Register values
#define ITG_PWR_MGM_CLK_SEL_INTERNAL      (0 << 0)
#define ITG_PWR_MGM_CLK_SEL_GYRO_X        (0 << 1)
#define ITG_PWR_MGM_CLK_SEL_GYRO_Y        (0 << 2)
#define ITG_PWR_MGM_CLK_SEL_GYRO_Z        (0 << 3)
#define ITG_PWR_MGM_CLK_SEL_EXT_32_KHZ    (0 << 4)
#define ITG_PWR_MGM_CLK_SEL_EXT_19_MHZ    (0 << 5)
#define ITG_PWR_MGM_STBY_ZG               (1 << 3)
#define ITG_PWR_MGM_STBY_YG               (1 << 4)
#define ITG_PWR_MGM_STBY_XG               (1 << 5)
#define ITG_PWR_MGM_SLEEP                 (1 << 6)
#define ITG_PWR_MGM_RESET                 (1 << 7)


#define IMU_CALIBRATION_TYPE_ACC_GAIN 0
#define IMU_CALIBRATION_TYPE_ACC_BIAS 1
#define IMU_CALIBRATION_TYPE_MAG_GAIN 2
#define IMU_CALIBRATION_TYPE_MAG_BIAS 3
#define IMU_CALIBRATION_TYPE_GYR_GAIN 4
#define IMU_CALIBRATION_TYPE_GYR_BIAS 5

#define IMU_CALIBRATION_ADDRESS (END_OF_BRICKLET_MEMORY - 0x400)
typedef struct {
	const int16_t imu_acc_x_gain_multiplier;
	const int16_t imu_acc_y_gain_multiplier;
	const int16_t imu_acc_z_gain_multiplier;
	const int16_t imu_acc_x_gain_divider;
	const int16_t imu_acc_y_gain_divider;
	const int16_t imu_acc_z_gain_divider;

	const int16_t imu_acc_x_bias;
	const int16_t imu_acc_y_bias;
	const int16_t imu_acc_z_bias;

	const int16_t imu_mag_x_gain_multiplier;
	const int16_t imu_mag_y_gain_multiplier;
	const int16_t imu_mag_z_gain_multiplier;
	const int16_t imu_mag_x_gain_divider;
	const int16_t imu_mag_y_gain_divider;
	const int16_t imu_mag_z_gain_divider;

	const int16_t imu_mag_x_bias;
	const int16_t imu_mag_y_bias;
	const int16_t imu_mag_z_bias;

	const int16_t imu_gyr_x_gain_multiplier;
	const int16_t imu_gyr_y_gain_multiplier;
	const int16_t imu_gyr_z_gain_multiplier;
	const int16_t imu_gyr_x_gain_divider;
	const int16_t imu_gyr_y_gain_divider;
	const int16_t imu_gyr_z_gain_divider;

	const int16_t imu_gyr_x_bias;
	const int16_t imu_gyr_y_bias;
	const int16_t imu_gyr_z_bias;
	const int16_t imu_gyr_x_temp_bias;
	const int16_t imu_gyr_y_temp_bias;
	const int16_t imu_gyr_z_temp_bias;

	const int16_t imu_gyr_temp_ref;

	const int32_t imu_gyr_x_temp_m_multiplier;
	const int32_t imu_gyr_y_temp_m_multiplier;
	const int32_t imu_gyr_z_temp_m_multiplier;
	const int32_t imu_gyr_temp_m_divider;
	const int32_t imu_gyr_x_temp_b;
	const int32_t imu_gyr_y_temp_b;
	const int32_t imu_gyr_z_temp_b;
} IMUCalibration;

typedef struct {
	int16_t imu_acc_x_gain_multiplier;
	int16_t imu_acc_y_gain_multiplier;
	int16_t imu_acc_z_gain_multiplier;
	int16_t imu_acc_x_gain_divider;
	int16_t imu_acc_y_gain_divider;
	int16_t imu_acc_z_gain_divider;

	int16_t imu_acc_x_bias;
	int16_t imu_acc_y_bias;
	int16_t imu_acc_z_bias;

	int16_t imu_mag_x_gain_multiplier;
	int16_t imu_mag_y_gain_multiplier;
	int16_t imu_mag_z_gain_multiplier;
	int16_t imu_mag_x_gain_divider;
	int16_t imu_mag_y_gain_divider;
	int16_t imu_mag_z_gain_divider;

	int16_t imu_mag_x_bias;
	int16_t imu_mag_y_bias;
	int16_t imu_mag_z_bias;

	int16_t imu_gyr_x_gain_multiplier;
	int16_t imu_gyr_y_gain_multiplier;
	int16_t imu_gyr_z_gain_multiplier;
	int16_t imu_gyr_x_gain_divider;
	int16_t imu_gyr_y_gain_divider;
	int16_t imu_gyr_z_gain_divider;

	int16_t imu_gyr_x_bias;
	int16_t imu_gyr_y_bias;
	int16_t imu_gyr_z_bias;
	int16_t imu_gyr_x_temp_bias;
	int16_t imu_gyr_y_temp_bias;
	int16_t imu_gyr_z_temp_bias;

	int16_t imu_gyr_temp_ref;

	int32_t imu_gyr_x_temp_m_multiplier;
	int32_t imu_gyr_y_temp_m_multiplier;
	int32_t imu_gyr_z_temp_m_multiplier;
	int32_t imu_gyr_temp_m_divider;
	int32_t imu_gyr_x_temp_b;
	int32_t imu_gyr_y_temp_b;
	int32_t imu_gyr_z_temp_b;
} IMUCalibrationNonConst;

void tick_task(const uint8_t tick_type);
void make_period_signal(const uint8_t type);

void update_sensors_async(void);
void callback_accelerometer(Async *a);
void callback_magnetometer(Async *a);
void callback_gyroscope(Async *a);

void imu_blinkenlights(void);
void imu_leds_on(bool on);
void imu_set_register(uint16_t addr, uint16_t reg, uint8_t value);

void imu_save_calibration(const IMUCalibrationNonConst *icnc);
void imu_fill_calibration(IMUCalibrationNonConst *icnc);

void imu_init(void);
int16_t two_complement_12_to_16(const int16_t value);
float imu_inv_sqrt(const float x);
void imu_update_filter(float w_x, float w_y, float w_z,
                       float a_x, float a_y, float a_z,
                       float m_x, float m_y, float m_z);
void imu_quaternion_to_orientation(void);
#endif
