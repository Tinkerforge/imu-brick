using System;
using Tinkerforge;

class Example
{
	private static string HOST = "localhost";
	private static int PORT = 4223;
	private static string UID = "XXYYZZ"; // Change to your UID

	// Callback function for quaternion callback
	static void QuaternionCB(BrickIMU sender, float x, float y, float z, float w)
	{
		Console.WriteLine("Quaternion[X]: " + x);
		Console.WriteLine("Quaternion[Y]: " + y);
		Console.WriteLine("Quaternion[Z]: " + z);
		Console.WriteLine("Quaternion[W]: " + w);
		Console.WriteLine("");
	}

	static void Main()
	{
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.Connect(HOST, PORT); // Connect to brickd
		// Don't use device before ipcon is connected

		// Register quaternion callback to function QuaternionCB
		imu.Quaternion += QuaternionCB;

		// Set period for quaternion callback to 1s (1000ms)
		// Note: The quaternion callback is only called every second
		//       if the quaternion has changed since the last call!
		imu.SetQuaternionPeriod(1000);

		Console.WriteLine("Press enter to exit");
		Console.ReadLine();
		ipcon.Disconnect();
	}
}
