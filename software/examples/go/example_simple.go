package main

import (
	"fmt"
	"tinkerforge/imu_brick"
	"tinkerforge/ipconnection"
)

const ADDR string = "localhost:4223"
const UID string = "XXYYZZ" // Change XXYYZZ to the UID of your IMU Brick.

func main() {
	ipcon := ipconnection.New()
	defer ipcon.Close()
	imu, _ := imu_brick.New(UID, &ipcon) // Create device object.

	ipcon.Connect(ADDR) // Connect to brickd.
	defer ipcon.Disconnect()
	// Don't use device before ipcon is connected.

	// Get current quaternion.
	x, y, z, w, _ := imu.GetQuaternion()

	fmt.Printf("Quaternion [X]: %f\n", x)
	fmt.Printf("Quaternion [Y]: %f\n", y)
	fmt.Printf("Quaternion [Z]: %f\n", z)
	fmt.Printf("Quaternion [W]: %f\n", w)

	fmt.Print("Press enter to exit.")
	fmt.Scanln()

}
