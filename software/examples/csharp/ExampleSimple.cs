using System;
using Tinkerforge;

class Example
{
	private static string HOST = "localhost";
	private static int PORT = 4223;
	private static string UID = "XXYYZZ"; // Change XXYYZZ to the UID of your IMU Brick

	static void Main()
	{
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.Connect(HOST, PORT); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get current quaternion
		float x, y, z, w;
		imu.GetQuaternion(out x, out y, out z, out w);

		Console.WriteLine("Quaternion[X]: " + x);
		Console.WriteLine("Quaternion[Y]: " + y);
		Console.WriteLine("Quaternion[Z]: " + z);
		Console.WriteLine("Quaternion[W]: " + w);

		Console.WriteLine("Press enter to exit");
		Console.ReadLine();
		ipcon.Disconnect();
	}
}
