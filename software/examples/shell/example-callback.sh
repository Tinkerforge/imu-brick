#!/bin/sh
# Connects to localhost:4223 by default, use --host and --port to change this

uid=XXYYZZ # Change XXYYZZ to the UID of your IMU Brick

# Handle incoming quaternion callbacks
tinkerforge dispatch imu-brick $uid quaternion &

# Set period for quaternion callback to 1s (1000ms)
# Note: The quaternion callback is only called every second
#       if the quaternion has changed since the last call!
tinkerforge call imu-brick $uid set-quaternion-period 1000

echo "Press key to exit"; read dummy

kill -- -$$ # Stop callback dispatch in background
