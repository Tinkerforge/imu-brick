using Tinkerforge;

class Example
{
	private static string HOST = "localhost";
	private static int PORT = 4223;
	private static string UID = "ayQskyoNrCW"; // Change to your UID

	// Quaternion callback
	static void QuaternionCB(object sender, float x, float y, float z, float w)
	{
		System.Console.WriteLine("x: " + x + "\ny: " + y + "\nz: " + z + "\nw: " + w + "\n");
	}

	static void Main() 
	{
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.Connect(HOST, PORT); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set period for quaternion callback to 1s
		imu.SetQuaternionPeriod(1000);

		// Register quaternion callback to QuaternionCB
		imu.Quaternion += QuaternionCB;

		System.Console.WriteLine("Press key to exit");
		System.Console.ReadKey();
	}
}
