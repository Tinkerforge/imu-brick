using Tinkerforge;

class Example
{
	private static string HOST = "localhost";
	private static int PORT = 4223;
	private static string UID = "XYZ"; // Change to your UID

	static void Main()
	{
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.Connect(HOST, PORT); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get current quaternion
		float x, y, z, w;
		imu.GetQuaternion(out x, out y, out z, out w);

		System.Console.WriteLine("x: " + x);
		System.Console.WriteLine("y: " + y);
		System.Console.WriteLine("z: " + z);
		System.Console.WriteLine("w: " + w);

		System.Console.WriteLine("Press enter to exit");
		System.Console.ReadLine();
		ipcon.Disconnect();
	}
}
