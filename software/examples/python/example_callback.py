#!/usr/bin/env python
# -*- coding: utf-8 -*-

HOST = "localhost"
PORT = 4223
UID = "XXYYZZ" # Change XXYYZZ to the UID of your IMU Brick

from tinkerforge.ip_connection import IPConnection
from tinkerforge.brick_imu import BrickIMU

# Callback function for quaternion callback
def cb_quaternion(x, y, z, w):
    print("Quaternion [X]: " + str(x))
    print("Quaternion [Y]: " + str(y))
    print("Quaternion [Z]: " + str(z))
    print("Quaternion [W]: " + str(w))
    print("")

if __name__ == "__main__":
    ipcon = IPConnection() # Create IP connection
    imu = BrickIMU(UID, ipcon) # Create device object

    ipcon.connect(HOST, PORT) # Connect to brickd
    # Don't use device before ipcon is connected

    # Register quaternion callback to function cb_quaternion
    imu.register_callback(imu.CALLBACK_QUATERNION, cb_quaternion)

    # Set period for quaternion callback to 1s (1000ms)
    imu.set_quaternion_period(1000)

    raw_input("Press key to exit\n") # Use input() in Python 3
    ipcon.disconnect()
