#include <stdio.h>

#include "ip_connection.h"
#include "brick_imu.h"

#define HOST "localhost"
#define PORT 4223
#define UID "XYZ" // Change to your UID

int main(void) {
	// Create IP connection
	IPConnection ipcon;
	ipcon_create(&ipcon);

	// Create device object
	IMU imu;
	imu_create(&imu, UID, &ipcon);

	// Connect to brickd
	if(ipcon_connect(&ipcon, HOST, PORT) < 0) {
		fprintf(stderr, "Could not connect\n");
		return 1;
	}
	// Don't use device before ipcon is connected

	// Get current quaternion
	float x, y, z, w;
	if(imu_get_quaternion(&imu, &x, &y, &z, &w) < 0) {
		fprintf(stderr, "Could not get quaternion, probably timeout\n");
		return 1;
	}

	printf("x: %f\ny: %f\nz: %f\nw: %f\n", x, y, z, w);

	printf("Press key to exit\n");
	getchar();
	ipcon_destroy(&ipcon); // Calls ipcon_disconnect internally
	return 0;
}
