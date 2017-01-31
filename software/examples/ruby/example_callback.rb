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

# Register quaternion callback
imu.register_callback(BrickIMU::CALLBACK_QUATERNION) do |x, y, z, w|
  puts "Quaternion[X]: #{x}"
  puts "Quaternion[Y]: #{y}"
  puts "Quaternion[Z]: #{z}"
  puts "Quaternion[W]: #{w}"
  puts ''
end

# Set period for quaternion callback to 1s (1000ms)
imu.set_quaternion_period 1000

puts 'Press key to exit'
$stdin.gets
ipcon.disconnect
