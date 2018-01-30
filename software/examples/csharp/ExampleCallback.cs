using System;
using Tinkerforge;

class Example
{
	private static string HOST = "localhost";
	private static int PORT = 4223;
	private static string UID = "XXYYZZ"; // Change XXYYZZ to the UID of your IMU Brick

	// Callback function for quaternion callback
	static void QuaternionCB(BrickIMU sender, float x, float y, float z, float w)
	{
		Console.WriteLine("Quaternion [X]: " + x);
		Console.WriteLine("Quaternion [Y]: " + y);
		Console.WriteLine("Quaternion [Z]: " + z);
		Console.WriteLine("Quaternion [W]: " + w);
		Console.WriteLine("");
	}

	static void Main()
	{
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.Connect(HOST, PORT); // Connect to brickd
		// Don't use device before ipcon is connected

		// Register quaternion callback to function QuaternionCB
		imu.QuaternionCallback += QuaternionCB;

		// Set period for quaternion callback to 1s (1000ms)
		imu.SetQuaternionPeriod(1000);

		Console.WriteLine("Press enter to exit");
		Console.ReadLine();
		ipcon.Disconnect();
	}
}
