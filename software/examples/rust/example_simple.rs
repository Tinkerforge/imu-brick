use std::{error::Error, io};

use tinkerforge::{imu_brick::*, ipconnection::IpConnection};

const HOST: &str = "127.0.0.1";
const PORT: u16 = 4223;
const UID: &str = "XXYYZZ"; // Change XXYYZZ to the UID of your IMU Brick

fn main() -> Result<(), Box<dyn Error>> {
    let ipcon = IpConnection::new(); // Create IP connection
    let imu_brick = IMUBrick::new(UID, &ipcon); // Create device object

    ipcon.connect(HOST, PORT).recv()??; // Connect to brickd
                                        // Don't use device before ipcon is connected

    // Get current quaternion
    let get_quaternion_result = imu_brick.get_quaternion().recv()?;

    println!("Quaternion [X]: {}", get_quaternion_result.x);
    println!("Quaternion [Y]: {}", get_quaternion_result.y);
    println!("Quaternion [Z]: {}", get_quaternion_result.z);
    println!("Quaternion [W]: {}", get_quaternion_result.w);

    println!("Press enter to exit.");
    let mut _input = String::new();
    io::stdin().read_line(&mut _input)?;
    ipcon.disconnect();
    Ok(())
}
