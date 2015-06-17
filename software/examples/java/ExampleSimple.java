import com.tinkerforge.BrickIMU;
import com.tinkerforge.BrickIMU.Quaternion;
import com.tinkerforge.IPConnection;

public class ExampleSimple {
	private static final String HOST = "localhost";
	private static final int PORT = 4223;
	private static final String UID = "6JpHZL"; // Change to your UID

	// Note: To make the example code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.connect(HOST, PORT); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get current color
		Quaternion q = imu.getQuaternion(); // Can throw com.tinkerforge.TimeoutException

		System.out.println("x: " + q.x);
		System.out.println("y: " + q.y);
		System.out.println("z: " + q.z);
		System.out.println("w: " + q.w);

		System.out.println("Press key to exit"); System.in.read();
		ipcon.disconnect();
	}
}
