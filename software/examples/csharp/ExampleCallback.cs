using Tinkerforge;

class Example
{
	private static string HOST = "localhost";
	private static int PORT = 4223;
	private static string UID = "ayQskyoNrCW"; // Change to your UID

	private static BrickIMU imu;

	// Quaternion callback
	static void QuaternionCB(float x, float y, float z, float w)
	{
		System.Console.WriteLine("x: " + x + "\ny: " + y + "\nz: " + z + "\nw: " + w + "\n");
	}

	static void Main() 
	{
		IPConnection ipcon = new IPConnection(HOST, PORT); // Create connection to brickd
		imu = new BrickIMU(UID); // Create device object
		ipcon.AddDevice(imu); // Add device to IP connection
		// Don't use device before it is added to a connection

		// Set period for quaternion callback to 1s
		imu.SetQuaternionPeriod(1000);

		// Register quaternion callback to QuaternionCB
		imu.RegisterCallback(new BrickIMU.Quaternion(QuaternionCB));

		System.Console.WriteLine("Press key to exit");
		System.Console.ReadKey();
		ipcon.Destroy();
	}
}
