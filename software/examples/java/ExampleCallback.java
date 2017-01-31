import com.tinkerforge.IPConnection;
import com.tinkerforge.BrickIMU;

public class ExampleCallback {
	private static final String HOST = "localhost";
	private static final int PORT = 4223;

	// Change XXYYZZ to the UID of your IMU Brick
	private static final String UID = "XXYYZZ";

	// Note: To make the example code cleaner we do not handle exceptions. Exceptions
	//       you might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.connect(HOST, PORT); // Connect to brickd
		// Don't use device before ipcon is connected

		// Add quaternion listener
		imu.addQuaternionListener(new BrickIMU.QuaternionListener() {
			public void quaternion(float x, float y, float z, float w) {
				System.out.println("Quaternion[X]: " + x);
				System.out.println("Quaternion[Y]: " + y);
				System.out.println("Quaternion[Z]: " + z);
				System.out.println("Quaternion[W]: " + w);
				System.out.println("");
			}
		});

		// Set period for quaternion callback to 1s (1000ms)
		imu.setQuaternionPeriod(1000);

		System.out.println("Press key to exit"); System.in.read();
		ipcon.disconnect();
	}
}
