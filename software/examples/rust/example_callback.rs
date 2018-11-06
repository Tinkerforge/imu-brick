use std::{error::Error, io, thread};
use tinkerforge::{imu_brick::*, ipconnection::IpConnection};

const HOST: &str = "127.0.0.1";
const PORT: u16 = 4223;
const UID: &str = "XXYYZZ"; // Change XXYYZZ to the UID of your IMU Brick

fn main() -> Result<(), Box<dyn Error>> {
    let ipcon = IpConnection::new(); // Create IP connection
    let imu_brick = IMUBrick::new(UID, &ipcon); // Create device object

    ipcon.connect(HOST, PORT).recv()??; // Connect to brickd
                                        // Don't use device before ipcon is connected

    //Create listener for quaternion events.
    let quaternion_listener = imu_brick.get_quaternion_receiver();
    // Spawn thread to handle received events. This thread ends when the imu_brick
    // is dropped, so there is no need for manual cleanup.
    thread::spawn(move || {
        for event in quaternion_listener {
            println!("Quaternion [X]: {}", event.x);
            println!("Quaternion [Y]: {}", event.y);
            println!("Quaternion [Z]: {}", event.z);
            println!("Quaternion [W]: {}", event.w);
            println!();
        }
    });

    // Set period for quaternion listener to 1s (1000ms)
    imu_brick.set_quaternion_period(1000);

    println!("Press enter to exit.");
    let mut _input = String::new();
    io::stdin().read_line(&mut _input)?;
    ipcon.disconnect();
    Ok(())
}
