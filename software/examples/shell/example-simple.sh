#!/bin/sh
# connects to localhost:4223 by default, use --host and --port to change it

# change to your UID
uid=XYZ

# get current quaternion
tinkerforge call imu-brick $uid get-quaternion
