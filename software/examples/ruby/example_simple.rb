#!/usr/bin/env ruby
# -*- ruby encoding: utf-8 -*-

require 'tinkerforge/ip_connection'
require 'tinkerforge/brick_imu'

include Tinkerforge

HOST = 'localhost'
PORT = 4223
UID = 'XYZ' # Change to your UID

ipcon = IPConnection.new # Create IP connection
imu = BrickIMU.new UID, ipcon # Create device object

ipcon.connect HOST, PORT # Connect to brickd
# Don't use device before ipcon is connected

# Get current quaternion (returned as array [x, y, z, w])
q = imu.get_quaternion
puts "x: #{q[0]}"
puts "y: #{q[1]}"
puts "z: #{q[2]}"
puts "w: #{q[3]}"

puts 'Press key to exit'
$stdin.gets
ipcon.disconnect
