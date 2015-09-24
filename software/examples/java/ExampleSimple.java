import com.tinkerforge.IPConnection;
import com.tinkerforge.BrickIMU;
import com.tinkerforge.BrickIMU.Quaternion;

public class ExampleSimple {
	private static final String HOST = "localhost";
	private static final int PORT = 4223;
	private static final String UID = "XXYYZZ"; // Change to your UID

	// Note: To make the example code cleaner we do not handle exceptions. Exceptions
	//       you might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.connect(HOST, PORT); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get current quaternion
		Quaternion quaternion = imu.getQuaternion(); // Can throw com.tinkerforge.TimeoutException

		System.out.println("Quaternion[X]: " + quaternion.x);
		System.out.println("Quaternion[Y]: " + quaternion.y);
		System.out.println("Quaternion[Z]: " + quaternion.z);
		System.out.println("Quaternion[W]: " + quaternion.w);

		System.out.println("Press key to exit"); System.in.read();
		ipcon.disconnect();
	}
}
