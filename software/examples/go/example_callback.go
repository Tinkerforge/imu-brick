package main

import (
	"fmt"
	"github.com/Tinkerforge/go-api-bindings/imu_brick"
	"github.com/Tinkerforge/go-api-bindings/ipconnection"
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

	imu.RegisterQuaternionCallback(func(x float32, y float32, z float32, w float32) {
		fmt.Printf("Quaternion [X]: %f\n", x)
		fmt.Printf("Quaternion [Y]: %f\n", y)
		fmt.Printf("Quaternion [Z]: %f\n", z)
		fmt.Printf("Quaternion [W]: %f\n", w)
		fmt.Println()
	})

	// Set period for quaternion receiver to 1s (1000ms).
	imu.SetQuaternionPeriod(1000)

	fmt.Print("Press enter to exit.")
	fmt.Scanln()

}
