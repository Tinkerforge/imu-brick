<?php

require_once('Tinkerforge/IPConnection.php');
require_once('Tinkerforge/BrickIMU.php');

use Tinkerforge\IPConnection;
use Tinkerforge\BrickIMU;

const HOST = 'localhost';
const PORT = 4223;
const UID = 'XXYYZZ'; // Change XXYYZZ to the UID of your IMU Brick

// Callback function for quaternion callback
function cb_quaternion($x, $y, $z, $w)
{
    echo "Quaternion[X]: $x\n";
    echo "Quaternion[Y]: $y\n";
    echo "Quaternion[Z]: $z\n";
    echo "Quaternion[W]: $w\n";
    echo "\n";
}

$ipcon = new IPConnection(); // Create IP connection
$imu = new BrickIMU(UID, $ipcon); // Create device object

$ipcon->connect(HOST, PORT); // Connect to brickd
// Don't use device before ipcon is connected

// Register quaternion callback to function cb_quaternion
$imu->registerCallback(BrickIMU::CALLBACK_QUATERNION, 'cb_quaternion');

// Set period for quaternion callback to 1s (1000ms)
// Note: The quaternion callback is only called every second
//       if the quaternion has changed since the last call!
$imu->setQuaternionPeriod(1000);

echo "Press ctrl+c to exit\n";
$ipcon->dispatchCallbacks(-1); // Dispatch callbacks forever

?>
