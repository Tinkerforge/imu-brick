#!/usr/bin/env python
# -*- coding: utf-8 -*-  

HOST = "localhost"
PORT = 4223
UID = "ayQskyoNrCW" # Change to your UID

from tinkerforge.ip_connection import IPConnection
from tinkerforge.brick_imu import IMU

imu = IMU(UID) # Create device object

# Quaternion callback
def quaternion_cb(x, y, z, w):
    print("x: " + str(x) + "\ny: " + str(y) + "\nz: " + str(z) + "\nw: " + str(w) + "\n")

if __name__ == "__main__":
    ipcon = IPConnection(HOST, PORT) # Create IPconnection to brickd
    ipcon.add_device(imu) # Add device to IP connection
    # Don't use device before it is added to a connection

    # Set period for quaternion callback to 1s
    imu.set_quaternion_period(1000)

    # Register quaternion callback
    imu.register_callback(imu.CALLBACK_QUATERNION, quaternion_cb)

    raw_input('Press key to exit\n') # Use input() in Python 3
    ipcon.destroy()
