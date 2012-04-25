#include <stdio.h>

#include "ip_connection.h"
#include "brick_imu.h"

#define HOST "localhost"
#define PORT 4223
#define UID "ayQskyoNrCW" // Change to your UID

// Quaternion callback
void cb_quaternion(float x, float y, float z, float w) {
	printf("x: %f\ny: %f\nz: %f\nw: %f\n\n", x, y, z, w);
}

int main() {
	// Create IP connection to brickd
	IPConnection ipcon;
	if(ipcon_create(&ipcon, HOST, PORT) < 0) {
		fprintf(stderr, "Could not create connection\n");
		exit(1);
	}

	// Create device object
	IMU imu;
	imu_create(&imu, UID); 

	// Add device to IP connection
	if(ipcon_add_device(&ipcon, &imu) < 0) {
		fprintf(stderr, "Could not connect to Brick\n");
		exit(1);
	}
	// Don't use device before it is added to a connection

	// Set period for quaternion callback to 1s
	imu_set_quaternion_period(&imu, 1000);

	// Register "quaternion callback" to cb_quaternion
	imu_register_callback(&imu, 
	                      IMU_CALLBACK_QUATERNION, 
	                      cb_quaternion);

	printf("Press ctrl+c to close\n");
	ipcon_join_thread(&ipcon); // Join mainloop of IP connection
}
