#!/usr/bin/env ruby
# -*- ruby encoding: utf-8 -*-

require 'tinkerforge/ip_connection'
require 'tinkerforge/brick_imu'

include Tinkerforge

HOST = 'localhost'
PORT = 4223
UID = 'XXYYZZ' # Change XXYYZZ to the UID of your IMU Brick

ipcon = IPConnection.new # Create IP connection
imu = BrickIMU.new UID, ipcon # Create device object

ipcon.connect HOST, PORT # Connect to brickd
# Don't use device before ipcon is connected

# Get current quaternion as [x, y, z, w]
quaternion = imu.get_quaternion

puts "Quaternion [X]: #{quaternion[0]}"
puts "Quaternion [Y]: #{quaternion[1]}"
puts "Quaternion [Z]: #{quaternion[2]}"
puts "Quaternion [W]: #{quaternion[3]}"

puts 'Press key to exit'
$stdin.gets
ipcon.disconnect
