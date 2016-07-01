#!/bin/sh
# Connects to localhost:4223 by default, use --host and --port to change this

uid=XXYYZZ # Change XXYYZZ to the UID of your IMU Brick

# Get current quaternion
tinkerforge call imu-brick $uid get-quaternion
