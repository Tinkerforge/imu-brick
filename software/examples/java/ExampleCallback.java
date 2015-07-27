import com.tinkerforge.IPConnection;
import com.tinkerforge.BrickIMU;

public class ExampleCallback {
	private static final String HOST = "localhost";
	private static final int PORT = 4223;
	private static final String UID = "XYZ"; // Change to your UID

	// Note: To make the example code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickIMU imu = new BrickIMU(UID, ipcon); // Create device object

		ipcon.connect(HOST, PORT); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set period for quaternion callback to 1s
		imu.setQuaternionPeriod(1000);

		// Add and implement quaternion listener 
		imu.addQuaternionListener(new BrickIMU.QuaternionListener() {
			public void quaternion(float x, float y, float z, float w) {
				System.out.println("x: " + x);
				System.out.println("y: " + y);
				System.out.println("z: " + z);
				System.out.println("w: " + w);
				System.out.println("");
			}
		});

		System.out.println("Press key to exit"); System.in.read();
		ipcon.disconnect();
	}
}
