#!/bin/sh
# connects to localhost:4223 by default, use --host and --port to change it

# change to your UID
uid=XYZ

# set period for quaternion callback to 1s (1000ms)
tinkerforge call imu-brick $uid set-quaternion-period 1000

# handle incoming quaternion callbacks
tinkerforge dispatch imu-brick $uid quaternion
