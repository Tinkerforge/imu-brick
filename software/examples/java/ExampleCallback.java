import com.tinkerforge.BrickIMU;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ayQskyoNrCW"; // Change to your UID

	// Note: To make the example code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set period for quaternion callback to 1s
		imu.setQuaternionPeriod(1000);

		// Add and implement quaternion listener 
		imu.addListener(new BrickIMU.QuaternionListener() {
			public void quaternion(float x, float y, float z, float w) {
				System.out.println("x: " + x + "\ny: " + y + "\nz: " + z + "\nw: " + w + "\n");
			}
		});

		System.console().readLine("Press key to exit\n");
	}
}
