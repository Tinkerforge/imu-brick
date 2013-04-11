#!/usr/bin/env python
# -*- coding: utf-8 -*-  
"""
calibration (IMU Calibration tool) 
Copyright (C) 2012 Olaf Lüke <olaf@tinkerforge.com>

calibration.py: Calibration of Acceleromter, Magnetometer and Gyroscope 

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License 
as published by the Free Software Foundation; either version 2 
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public
License along with this program; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA 02111-1307, USA.
"""


HOST = "localhost"
PORT = 4223

import time
from ip_connection import IPConnection
from brick_servo import Servo
from brick_imu import IMU
from threading import Event

class IMUCal:
    NUM_GYR_CAL_TESTS = 5
    TYPE_ACC_GAIN = 0
    TYPE_ACC_BIAS = 1
    TYPE_MAG_GAIN = 2
    TYPE_MAG_BIAS = 3
    TYPE_GYR_GAIN = 4
    TYPE_GYR_BIAS = 5
    
    GYR_TEMP_LOW = 1600
    GYR_TEMP_HIGH = 2500
    
    MAG_CAL_VELOCITY = 40000
    MAG_CAL_ACCELERATION = 30000
    SERVO_VOLTAGE = 6000
    
    servos = {
        'hs-785hb_bottom': [0, (1265, 1735), (-18000, 18000)],
        'hs-785hb_middle': [1, (1200, 1800), (-18975, 18975)],
        'hs-645mg_top': [2, (900, 1950), (0, 18000)]
    }
        
    bot = servos['hs-785hb_bottom'][0]
    mid = servos['hs-785hb_middle'][0]
    top = servos['hs-645mg_top'][0]
    
    servo_reached = [Event(), Event(), Event()]
    mag_make_average = False
    mag_sum_x = 0
    mag_sum_y = 0
    mag_sum_z = 0
    mag_sum_i = 0
    
    mag_x_last_top = 0
    mag_y_last_top = 0
    mag_z_last_top = 0
    
    mag_x_last_bot = 0
    mag_y_last_bot = 0
    mag_z_last_bot = 0
    
    mag_x_max = 0
    mag_y_max = 0
    mag_z_max = 0
    
    acc_make_average = False
    acc_sum_x = 0
    acc_sum_y = 0
    acc_sum_z = 0
    acc_sum_i = 0
    
    acc_x_min = 0
    acc_x_max = 0
    acc_y_min = 0
    acc_y_max = 0
    acc_z_min = 0
    acc_z_max = 0
    
    acc_x_bias = 0
    acc_y_bias = 0
    acc_z_bias = 0
        
    acc_x_gain_mult = 1
    acc_y_gain_mult = 1
    acc_z_gain_mult = 1
    
    acc_x_gain_div = 1
    acc_y_gain_div = 1
    acc_z_gain_div = 1
    
    gyr_x_gain_mult = 1
    gyr_y_gain_mult = 1
    gyr_z_gain_mult = 1
    
    gyr_x_gain_div = 1
    gyr_y_gain_div = 1
    gyr_z_gain_div = 1
    
    gyr_bias_xl = 0
    gyr_bias_yl = 0
    gyr_bias_zl = 0
    gyr_bias_tl = 0
    
    gyr_bias_xh = 0
    gyr_bias_yh = 0
    gyr_bias_zh = 0
    gyr_bias_th = 1
    
    gyr_make_average = False
    gyr_sum_i = 0
    gyr_sum_x = 0
    gyr_sum_y = 0
    gyr_sum_z = 0
    
    gyr_x_ref1 = 2970.9937199233336
    gyr_x_ref2 = 2997.3047517850005
    gyr_x_ref = int(round(gyr_x_ref1 + gyr_x_ref2))
    
    gyr_y_ref1 = 1339.5480923774999
    gyr_y_ref2 = 1428.6085124024999
    gyr_y_ref = int(round(gyr_y_ref1 + gyr_y_ref2))
    
    gyr_z_ref1 = 1250.442733235
    gyr_z_ref2 = 1200.7509489899999
    gyr_z_ref = int(round(gyr_z_ref1 + gyr_z_ref2))
    
    imu_uid = None
    
    def cb_reached(self, servo_num, position):
        self.servo_reached[servo_num].set()
    
    def cb_mag(self, x, y, z):
        if self.mag_make_average:
            self.mag_sum_i += 1
            self.mag_sum_x += x 
            self.mag_sum_y += y 
            self.mag_sum_z += z 
    
    def make_mag_avg(self):
        self.mag_make_average = True
        time.sleep(1)
        self.mag_make_average = False
        time.sleep(0.05)
        x = self.mag_sum_x/self.mag_sum_i
        y = self.mag_sum_y/self.mag_sum_i
        z = self.mag_sum_z/self.mag_sum_i
        
        self.mag_sum_x, self.mag_sum_y, self.mag_sum_z, self.mag_sum_i = 0, 0, 0, 0
    
        return x, y, z
    
    def cb_acc(self, x, y, z):
        if self.acc_make_average and self.acc_sum_i < 1000:
            self.acc_sum_i += 1
            self.acc_sum_x += x 
            self.acc_sum_y += y 
            self.acc_sum_z += z 
        if self.acc_sum_i == 1000:
            self.acc_make_average = False
            self.servo_reached[0].set()
            
    def make_acc_avg(self, direction):
        print "start calibrating",  direction
        
        self.servo_reached[0].clear()
        self.i.set_acceleration_period(1)
        self.acc_make_average = True
        self.servo_reached[0].wait()
        self.i.set_acceleration_period(0)
        
        if direction == 'X':
            if self.acc_sum_x > 0:
                self.acc_x_max = self.acc_sum_x/self.acc_sum_i
                print "value =", self.acc_x_max
            else:
                self.acc_x_min = self.acc_sum_x/self.acc_sum_i
                print "value =", self.acc_x_min
        elif direction == 'Y':
            if self.acc_sum_y > 0:
                self.acc_y_max = self.acc_sum_y/self.acc_sum_i
                print "value =", self.acc_y_max
            else :
                self.acc_y_min = self.acc_sum_y/self.acc_sum_i
                print "value =", self.acc_y_min
        elif direction == 'Z':
            if self.acc_sum_z > 0:
                self.acc_z_max = self.acc_sum_z/self.acc_sum_i
                print "value =", self.acc_z_max
            else :
                self.acc_z_min = self.acc_sum_z/self.acc_sum_i   
                print "value =", self.acc_z_min
                
        self.acc_sum_i = 0
        self.acc_sum_x = 0
        self.acc_sum_y = 0 
        self.acc_sum_z = 0 
        
    def write_acc(self):
        gain = [self.acc_x_gain_mult, self.acc_y_gain_mult, self.acc_z_gain_mult, self.acc_x_gain_div, self.acc_y_gain_div, self.acc_z_gain_div, 0, 0, 0, 0]
        bias = [self.acc_x_bias, self.acc_y_bias, self.acc_z_bias, 0, 0, 0, 0, 0, 0, 0]
        print 'writing acc (gain, bias):', gain, bias
        self.i.set_calibration(self.TYPE_ACC_GAIN, gain)
        self.i.set_calibration(self.TYPE_ACC_BIAS, bias)
        
    def write_gyr_bias(self):
        bias = [-self.gyr_bias_xl, -self.gyr_bias_yl, -self.gyr_bias_zl, self.gyr_bias_tl, -self.gyr_bias_xh, -self.gyr_bias_yh, -self.gyr_bias_zh, self.gyr_bias_th, 0, 0]
        print 'writing gyr (bias):', bias
        self.i.set_calibration(self.TYPE_GYR_BIAS, bias)
        
    def write_gyr_gain(self):
        gain = [self.gyr_x_gain_mult, self.gyr_y_gain_mult, self.gyr_z_gain_mult, self.gyr_x_gain_div, self.gyr_y_gain_div, self.gyr_z_gain_div, 0, 0, 0, 0]
        print 'writing gyr (gain):', gain
        self.i.set_calibration(self.TYPE_GYR_GAIN, gain)
        
    def calculate_acc(self):
        self.acc_x_bias = ((1000  - self.acc_x_max) + (-1000 - self.acc_x_min))/2
        self.acc_y_bias = ((1000  - self.acc_y_max) + (-1000 - self.acc_y_min))/2
        self.acc_z_bias = ((1000  - self.acc_z_max) + (-1000 - self.acc_z_min))/2
        
        self.acc_x_gain_mult = 1000
        self.acc_y_gain_mult = 1000
        self.acc_z_gain_mult = 1000
        
        self.acc_x_gain_div = self.acc_x_max + self.acc_x_bias
        self.acc_y_gain_div = self.acc_y_max + self.acc_y_bias
        self.acc_z_gain_div = self.acc_z_max + self.acc_z_bias
                            
    def calibrate_acc(self):
        self.s.set_position(self.bot, 0)
        self.i.register_callback(self.i.CALLBACK_ACCELERATION, self.cb_acc)
        # X+
        self.s.set_position(self.top, 50)
        self.s.set_position(self.mid, 7700)
        time.sleep(3)
        self.make_acc_avg('X')
        
        # X-
        self.s.set_position(self.top, 50)
        self.s.set_position(self.mid, -7350)
        time.sleep(3.5)
        self.make_acc_avg('X')
        
        # Y+
        self.s.set_position(self.top, 8800)
        self.s.set_position(self.mid, 180)
        time.sleep(3)
        self.make_acc_avg('Y')
        
        # Y-
        self.s.set_position(self.top, 8800)
        self.s.set_position(self.mid, 14900)
        time.sleep(3.5)
        self.make_acc_avg('Y')
        
        # Z+
        self.s.set_position(self.top, 18000)
        self.s.set_position(self.mid, 14900)
        time.sleep(2)
        self.make_acc_avg('Z')
        
        # Z-
        self.s.set_position(self.top, 50)
        self.s.set_position(self.mid, 14900)
        time.sleep(2)
        self.make_acc_avg('Z')
        
        self.calculate_acc()
        print 'acc calibration:', (self.acc_x_min, self.acc_x_max, self.acc_y_min, self.acc_y_max, self.acc_z_min, self.acc_z_max)
    
    def cb_gyr(self, x, y, z):
        if self.gyr_make_average:
            self.gyr_sum_i += 1
            self.gyr_sum_x += x
            self.gyr_sum_y += y
            self.gyr_sum_z += z
    
    def calibrate_gyr_gain(self):
        def calibrate_x(pos):
            self.gyr_sum_i = 0
            self.gyr_sum_x = 0
            self.gyr_sum_y = 0
            self.gyr_sum_z = 0
            
            self.s.set_position(self.top, pos)
            time.sleep(0.2)
            self.i.set_angular_velocity_period(1)
            self.gyr_make_average = True
            print("start")
            time.sleep(0.5)
            self.gyr_make_average = False
            print("stop")
            self.i.set_angular_velocity_period(0)
            time.sleep(0.2)
            print 'value: ', self.gyr_sum_x/self.gyr_sum_i
            time.sleep(0.5)
        
        def calibrate_y(pos):
            self.gyr_sum_i = 0
            self.gyr_sum_x = 0
            self.gyr_sum_y = 0
            self.gyr_sum_z = 0
            
            self.s.set_position(self.mid, pos)
            time.sleep(1.2)
            self.i.set_angular_velocity_period(1)
            self.gyr_make_average = True
            print("start")
            time.sleep(0.5)
            self.gyr_make_average = False
            print("stop")
            self.i.set_angular_velocity_period(0)
            time.sleep(0.1)
            print 'value: ', self.gyr_sum_y/self.gyr_sum_i
            time.sleep(1.2)
            
        def calibrate_z(pos):
            self.gyr_sum_i = 0
            self.gyr_sum_x = 0
            self.gyr_sum_y = 0
            self.gyr_sum_z = 0
            
            self.s.set_position(self.bot, pos)
            time.sleep(2.5)
            self.i.set_angular_velocity_period(1)
            self.gyr_make_average = True
            print("start")
            time.sleep(1.0)
            self.gyr_make_average = False
            print("stop")
            self.i.set_angular_velocity_period(0)
            time.sleep(0.1)
            print 'value: ', self.gyr_sum_z/self.gyr_sum_i
            time.sleep(0.5)
        
        self.i.register_callback(self.i.CALLBACK_ANGULAR_VELOCITY, self.cb_gyr)
        self.s.set_position(self.top, 18000)
        self.s.set_position(self.mid, 14900)
        self.s.set_velocity(self.top, 20000)
        time.sleep(2)
        val1 = 0
        val2 = 0
        for i in range(self.NUM_GYR_CAL_TESTS):
            calibrate_x(0)
            if i != 0:
                val1 += self.gyr_sum_x/float(self.gyr_sum_i)
            calibrate_x(18000)
            if i != 0:
                val2 += self.gyr_sum_x/float(self.gyr_sum_i)
                
        val1 = abs(val1)
        val2 = abs(val2)
        val_x = int(round(val1/float(self.NUM_GYR_CAL_TESTS-1) + val2/float(self.NUM_GYR_CAL_TESTS-1)))
        
        self.s.set_velocity(self.mid, 8000)
        self.s.set_position(self.mid, 10000)
        val1 = 0
        val2 = 0
        time.sleep(2)
        for i in range(self.NUM_GYR_CAL_TESTS):
            calibrate_y(-10000)
            if i != 0:
                val1 += self.gyr_sum_y/float(self.gyr_sum_i)
            calibrate_y(10000)
            if i != 0:
                val2 += self.gyr_sum_y/float(self.gyr_sum_i)
                
        val1 = abs(val1)
        val2 = abs(val2)
        val_y = int(round(val1/float(self.NUM_GYR_CAL_TESTS-1) + val2/float(self.NUM_GYR_CAL_TESTS-1)))
       
        self.s.set_velocity(self.mid, 0xFFFF)
        self.s.set_velocity(self.bot, 8000)
        self.s.set_position(self.bot, 10000)
        self.s.set_position(self.mid, 0)
        time.sleep(2)
        val1 = 0
        val2 = 0
        for i in range(self.NUM_GYR_CAL_TESTS):
            calibrate_z(-18000)
            if i != 0:
                val1 += self.gyr_sum_z/float(self.gyr_sum_i)
            calibrate_z(18000)
            if i != 0:
                val2 += self.gyr_sum_z/float(self.gyr_sum_i)
                
        val1 = abs(val1)
        val2 = abs(val2)
        val_z = int(round(val1/float(self.NUM_GYR_CAL_TESTS-1) + val2/float(self.NUM_GYR_CAL_TESTS-1)))
        
        self.gyr_x_gain_mult = self.gyr_x_ref
        self.gyr_y_gain_mult = self.gyr_y_ref
        self.gyr_z_gain_mult = self.gyr_z_ref
        
        self.gyr_x_gain_div = val_x
        self.gyr_y_gain_div = val_y
        self.gyr_z_gain_div = val_z
        
        gyr_gain_str = ', '.join([str(self.gyr_x_ref) + '/' + str(val_x),
                                  str(self.gyr_y_ref) + '/' + str(val_y),
                                  str(self.gyr_z_ref) + '/' + str(val_z)])
                        
        print "gyr gain: " + gyr_gain_str
    
    def wait(self, num, pos):
        if self.s.get_current_position(num) != pos:
            self.servo_reached[num].wait()
        self.servo_reached[num].clear()
        time.sleep(0.25)
    
    def calibrate_mag(self):
        # TODO
        bot_pos = 0
        top_pos = -13000
        self.s.set_acceleration(self.bot, 0xFFFF) 
        self.s.set_acceleration(self.top, 0xFFFF) 
        self.s.set_velocity(self.bot, 0xFFFF)
        self.s.set_velocity(self.top, 0xFFFF)
        self.s.set_position(self.bot, bot_pos)
        self.s.set_position(self.top, top_pos)
        self.s.enable(self.bot)
        self.s.enable(self.top)
        direction = 1
       
        for i in range(20):
            time.sleep(1)
            x, y, z = self.make_mag_avg()
            print "bot", x
            if x < self.mag_x_last_bot:
                bot_pos += 1000
            else:
                bot_pos -= 1000
                
            if bot_pos > 18000:
                bot_pos = bot_pos - 36000
            if bot_pos < -18000:
                bot_pos = bot_pos + 36000
                
            print bot_pos 
                
            self.mag_x_last_bot = x
                
            self.s.set_position(self.bot, bot_pos)
            self.wait(self.bot, bot_pos)
            time.sleep(0.1)
                
                
#            time.sleep(1)
#            x, y, z = self.make_mag_avg()
#            print "top", x
#            if x > self.mag_x_last_top:
#                top_pos += 1000
#            else:
#                top_pos -= 1000
#                
#            self.mag_x_last_top = x
#                
#            print 'new_pos', bot_pos
#            self.s.set_position(self.top, top_pos)
#            self.wait(self.top, top_pos)
#            time.sleep(0.1)
                
            
             
        def calibrate_top_mid(s, bot_pos):
            for e in servo_reached:
                e.clear()
                
            self.s.set_acceleration(top, MAG_CAL_ACCELERATION)    
            self.s.set_acceleration(mid, MAG_CAL_ACCELERATION)    
            self.s.set_acceleration(bot, MAG_CAL_ACCELERATION) 
            self.s.set_velocity(top, MAG_CAL_VELOCITY)
            self.s.set_velocity(mid, MAG_CAL_VELOCITY)
            self.s.set_velocity(bot, MAG_CAL_VELOCITY) 
            
            self.s.set_position(top, 18000)
            self.s.enable(top)
            self.s.set_position(mid, 0)
            self.s.enable(mid)
            self.s.set_position(bot, bot_pos)
            self.s.enable(bot)
            wait(bot, bot_pos, s)
            wait(top, 18000, s)
            wait(mid, 0, s)
            
            for i in range(19):
                self.s.set_position(mid, i*1000)
                wait(mid, i*1000, s)
                if i % 2:
                    self.s.set_position(top, 18000)
                    wait(top, 18000, s)
                else:
                    self.s.set_position(top, 0)
                    wait(top, 0, s)
    
        calibrate_top_mid(s, -9000)
        calibrate_top_mid(s, 9000)
    
    def calibrate_gyr_bias(self):
        self.i.register_callback(self.i.CALLBACK_ANGULAR_VELOCITY, self.cb_gyr)
        while True:
            temp = self.i.get_imu_temperature()
            print "Temperature: " + str(temp/100.0) + " °C (waiting to fall below " + str(self.GYR_TEMP_LOW/100.0) + " °C)"
            if temp < self.GYR_TEMP_LOW:
                break
            time.sleep(1)
        
        while True:
            temp = self.i.get_imu_temperature()
            print "Temperature: " + str(temp/100.0) + " °C (waiting to rise above " + str(self.GYR_TEMP_LOW/100.0) + " °C)"
            if temp > self.GYR_TEMP_LOW:
                break
            time.sleep(1)
            
        temp1 = self.i.get_imu_temperature()
        self.i.set_angular_velocity_period(1)
        self.gyr_make_average = True
        print("start")
        time.sleep(3)
        self.gyr_make_average = False
        print("stop")
        self.i.set_angular_velocity_period(0)
        temp2 = self.i.get_imu_temperature()
        self.gyr_bias_tl = int(round((temp1 + temp2)/2.0))
        self.gyr_bias_xl, self.gyr_bias_yl, self.gyr_bias_zl = (self.gyr_sum_x/self.gyr_sum_i, self.gyr_sum_y/self.gyr_sum_i, self.gyr_sum_z/self.gyr_sum_i)
        print 'value: ', (self.gyr_bias_xl, self.gyr_bias_yl, self.gyr_bias_zl, self.gyr_bias_tl)
            
        while True:
            temp = self.i.get_imu_temperature()
            print "Temperature: " + str(temp/100.0) + " °C (waiting to rise above " + str(self.GYR_TEMP_HIGH/100.0) + " °C)"
            if temp > self.GYR_TEMP_HIGH:
                break
            time.sleep(1)
        
        temp1 = self.i.get_imu_temperature()
        self.i.set_angular_velocity_period(1)
        self.gyr_make_average = True
        print("start")
        time.sleep(3)
        self.gyr_make_average = False
        print("stop")
        self.i.set_angular_velocity_period(0)
        temp2 = self.i.get_imu_temperature()
        self.gyr_bias_th = int(round((temp1 + temp2)/2.0))
        self.gyr_bias_xh, self.gyr_bias_yh, self.gyr_bias_zh = (self.gyr_sum_x/self.gyr_sum_i, self.gyr_sum_y/self.gyr_sum_i, self.gyr_sum_z/self.gyr_sum_i)
        print 'value: ', (self.gyr_bias_xh, self.gyr_bias_yh, self.gyr_bias_zh, self.gyr_bias_th)
    
    def cb_enumerate(self, uid, connected_uid, position, hardware_version, firmware_version, device_identifier, enumeration_type):
        if device_identifier == 16:
            self.imu_uid = uid
    
    def __init__(self):
        ipcon = IPConnection()
        ipcon.connect(HOST, PORT)
            
        ipcon.register_callback(IPConnection.CALLBACK_ENUMERATE, self.cb_enumerate)

        ipcon.enumerate()
        i = 0
        while self.imu_uid == None:
            time.sleep(0.1)
            i += 1
            if i == 20:
                print "Didn't find IMU, exiting"
                return
        
        self.s = Servo('9TZyPftcTLE', ipcon)
        self.i = IMU(self.imu_uid, ipcon)
    
        self.s.set_output_voltage(self.SERVO_VOLTAGE)
        for servo in self.servos.values():
            self.s.set_pulse_width(servo[0], servo[1][0], servo[1][1])
            self.s.set_degree(servo[0], servo[2][0], servo[2][1])
            self.s.set_velocity(servo[0], 0xFFFF) 
            self.s.set_acceleration(servo[0], 0xFFFF) 
            self.s.set_position(servo[0], 0)
            self.s.enable(servo[0])
    
        time.sleep(1)
        
        self.write_gyr_bias()
        time.sleep(0.2)
        self.calibrate_gyr_bias()
        self.write_gyr_bias()

        self.write_gyr_gain()
        time.sleep(0.2)
        self.calibrate_gyr_gain()
        self.write_gyr_gain()
        
        self.write_acc() # default
        time.sleep(0.2)
        self.calibrate_acc()
        self.write_acc() # new calibration
        
        text = """# Each line starts with "calibration type:"
# followed by the x, y and z calibration, separated by a comma.
# Multiplier and Divider are written as "mul/div"

"""
        
        c = []
        for num in range(6):
            c.append(self.i.get_calibration(num))
            
            
        text += '0: ' + str(c[0][0]) + '/' + str(c[0][3]) + ', ' + str(c[0][1]) + '/' + str(c[0][4]) + ', ' + str(c[0][2]) + '/' + str(c[0][5])
        text += '\n'
        text += '1: ' + str(c[1][0]) + ', ' + str(c[1][1]) + ', ' + str(c[1][2])
        text += '\n'
        text += '2: ' + str(c[2][0]) + '/' + str(c[2][3]) + ', ' + str(c[2][1]) + '/' + str(c[2][4]) + ', ' + str(c[2][2]) + '/' + str(c[2][5])
        text += '\n'
        text += '3: ' + str(c[3][0]) + ', ' + str(c[3][1]) + ', ' + str(c[3][2])
        text += '\n'
        text += '4: ' + str(c[4][0]) + '/' + str(c[4][3]) + ', ' + str(c[4][1]) + '/' + str(c[4][4]) + ', ' + str(c[4][2]) + '/' + str(c[4][5])
        text += '\n'
        text += '5: ' + str(c[5][0]) + ', ' + str(c[5][1]) + ', ' + str(c[5][2]) + ', ' + str(c[5][3]) + ', ' + str(c[5][4]) + ', ' + str(c[5][5]) + ', ' + str(c[5][6]) + ', ' + str(c[5][7])
        
        f = file(self.imu_uid + '.txt', 'w')
        f.write(text)
        f.close()
        
        #self.calibrate_mag()
    

if __name__ == "__main__":
    IMUCal()
    raw_input('Press key to exit\n')
