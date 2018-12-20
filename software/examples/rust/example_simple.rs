use std::{error::Error, io};

use tinkerforge::{imu_brick::*, ip_connection::IpConnection};

const HOST: &str = "localhost";
const PORT: u16 = 4223;
const UID: &str = "XXYYZZ"; // Change XXYYZZ to the UID of your IMU Brick.

fn main() -> Result<(), Box<dyn Error>> {
    let ipcon = IpConnection::new(); // Create IP connection.
    let imu = ImuBrick::new(UID, &ipcon); // Create device object.

    ipcon.connect((HOST, PORT)).recv()??; // Connect to brickd.
                                          // Don't use device before ipcon is connected.

    // Get current quaternion.
    let quaternion = imu.get_quaternion().recv()?;

    println!("Quaternion [X]: {}", quaternion.x);
    println!("Quaternion [Y]: {}", quaternion.y);
    println!("Quaternion [Z]: {}", quaternion.z);
    println!("Quaternion [W]: {}", quaternion.w);

    println!("Press enter to exit.");
    let mut _input = String::new();
    io::stdin().read_line(&mut _input)?;
    ipcon.disconnect();
    Ok(())
}
