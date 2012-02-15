import com.tinkerforge.BrickIMU;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = new String("localhost");
	private static final int port = 4223;
	private static final String UID = new String("ayQskyoNrCW"); // Change to your UID

	// Note: To make the example code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the commnents below
	public static void main(String args[]) throws Exception {
		// Create connection to brickd
		IPConnection ipcon = new IPConnection(host, port); // Can throw IOException

		BrickIMU imu = new BrickIMU(UID); // Create device object

		// Add device to ip connection
		ipcon.addDevice(imu); // Can throw IPConnection.TimeoutException
		// Don't use device before it is added to a connection
		
		// Set period for quaternion callback to 1s
		imu.setQuaternionPeriod(1000);

		// Add and implement quaternion listener 
		imu.addListener(new BrickIMU.QuaternionListener() {
			public void quaternion(float x, float y, float z, float w) {
				System.out.println("x: " + x + "\ny: " + y + "\nz: " + z + "\nw: " + w + "\n");
			}
		});

		System.out.println("Press ctrl+c to exit");
		ipcon.joinThread();
	}
}
