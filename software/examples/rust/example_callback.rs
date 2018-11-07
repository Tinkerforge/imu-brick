use std::{error::Error, io, thread};
use tinkerforge::{imu_brick::*, ip_connection::IpConnection};

const HOST: &str = "localhost";
const PORT: u16 = 4223;
const UID: &str = "XXYYZZ"; // Change XXYYZZ to the UID of your IMU Brick.

fn main() -> Result<(), Box<dyn Error>> {
    let ipcon = IpConnection::new(); // Create IP connection.
    let imu = ImuBrick::new(UID, &ipcon); // Create device object.

    ipcon.connect((HOST, PORT)).recv()??; // Connect to brickd.
                                          // Don't use device before ipcon is connected.

    // Create receiver for quaternion events.
    let quaternion_receiver = imu.get_quaternion_receiver();

    // Spawn thread to handle received events. This thread ends when the `imu` object
    // is dropped, so there is no need for manual cleanup.
    thread::spawn(move || {
        for quaternion in quaternion_receiver {
            println!("Quaternion [X]: {}", quaternion.x);
            println!("Quaternion [Y]: {}", quaternion.y);
            println!("Quaternion [Z]: {}", quaternion.z);
            println!("Quaternion [W]: {}", quaternion.w);
            println!();
        }
    });

    // Set period for quaternion receiver to 1s (1000ms).
    imu.set_quaternion_period(1000);

    println!("Press enter to exit.");
    let mut _input = String::new();
    io::stdin().read_line(&mut _input)?;
    ipcon.disconnect();
    Ok(())
}
