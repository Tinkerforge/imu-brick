#include <stdio.h>

#include "ip_connection.h"
#include "brick_imu.h"

#define HOST "localhost"
#define PORT 4223
#define UID "ayQskyoNrCW" // Change to your UID

// Quaternion callback
void cb_quaternion(float x, float y, float z, float w, void *user_data) {
	(void)user_data; // avoid unused parameter warning

	printf("x: %f\ny: %f\nz: %f\nw: %f\n\n", x, y, z, w);
}

int main() {
	// Create IP connection
	IPConnection ipcon;
	ipcon_create(&ipcon);

	// Create device object
	IMU imu;
	imu_create(&imu, UID, &ipcon); 

	// Connect to brickd
	if(ipcon_connect(&ipcon, HOST, PORT) < 0) {
		fprintf(stderr, "Could not connect\n");
		exit(1);
	}
	// Don't use device before ipcon is connected

	// Set period for quaternion callback to 1s
	imu_set_quaternion_period(&imu, 1000);

	// Register "quaternion callback" to cb_quaternion
	imu_register_callback(&imu,
	                      IMU_CALLBACK_QUATERNION,
	                      (void *)cb_quaternion,
	                      NULL);

	printf("Press key to exit\n");
	getchar();
	ipcon_destroy(&ipcon); // Calls ipcon_disconnect internally
}
