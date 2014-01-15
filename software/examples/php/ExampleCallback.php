<?php

require_once('Tinkerforge/IPConnection.php');
require_once('Tinkerforge/BrickIMU.php');

use Tinkerforge\IPConnection;
use Tinkerforge\BrickIMU;

const HOST = 'localhost';
const PORT = 4223;
const UID = 'a4JriVLwq7E'; // Change to your UID

// Quaternion callback
function cb_quaternion($x, $y, $z, $w)
{
    echo "x: $x\ny: $y\nz: $z\nw: $w\n\n";
}

$ipcon = new IPConnection(); // Create IP connection
$imu = new BrickIMU(UID, $ipcon); // Create device object

$ipcon->connect(HOST, PORT); // Connect to brickd
// Don't use device before ipcon is connected

// Set period for quaternion callback to 1s
$imu->setQuaternionPeriod(1000);

// Register position callback to function cb_position
$imu->registerCallback(BrickIMU::CALLBACK_QUATERNION, 'cb_quaternion');

echo "Press ctrl+c to exit\n";
$ipcon->dispatchCallbacks(-1); // Dispatch callbacks forever

?>
