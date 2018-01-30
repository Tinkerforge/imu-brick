#!/usr/bin/env python
# -*- coding: utf-8 -*-

HOST = "localhost"
PORT = 4223
UID = "XXYYZZ" # Change XXYYZZ to the UID of your IMU Brick

from tinkerforge.ip_connection import IPConnection
from tinkerforge.brick_imu import BrickIMU

if __name__ == "__main__":
    ipcon = IPConnection() # Create IP connection
    imu = BrickIMU(UID, ipcon) # Create device object

    ipcon.connect(HOST, PORT) # Connect to brickd
    # Don't use device before ipcon is connected

    # Get current quaternion
    x, y, z, w = imu.get_quaternion()

    print("Quaternion [X]: " + str(x))
    print("Quaternion [Y]: " + str(y))
    print("Quaternion [Z]: " + str(z))
    print("Quaternion [W]: " + str(w))

    raw_input("Press key to exit\n") # Use input() in Python 3
    ipcon.disconnect()
