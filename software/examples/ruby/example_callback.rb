#!/usr/bin/env ruby
# -*- ruby encoding: utf-8 -*-

require 'tinkerforge/ip_connection'
require 'tinkerforge/brick_imu'

include Tinkerforge

HOST = 'localhost'
PORT = 4223
UID = 'a4JriVLwq7E' # Change to your UID

ipcon = IPConnection.new HOST, PORT # Create IP connection to brickd
imu = BrickIMU.new UID # Create device object
ipcon.add_device imu # Add device to IP connection
# Don't use device before it is added to a connection

# Set period for quaternion callback to 1s
imu.set_quaternion_period 1000

# Register quaternion callback
imu.register_callback BrickIMU::CALLBACK_QUATERNION, do |x, y, z, w|
  puts "x: #{x}"
  puts "y: #{y}"
  puts "z: #{z}"
  puts "w: #{w}\n\n"
end

puts 'Press key to exit'
$stdin.gets
ipcon.destroy
