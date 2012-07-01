<?php

require_once('Tinkerforge/IPConnection.php');
require_once('Tinkerforge/BrickIMU.php');

use Tinkerforge\IPConnection;
use Tinkerforge\BrickIMU;

$host = 'localhost';
$port = 4223;
$uid = 'a4JriVLwq7E'; // Change to your UID

// Quaternion callback
function cb_quaternion($x, $y, $z, $w)
{
    echo "x: $x\ny: $y\nz: $z\nw: $w\n";
}

$ipcon = new IPConnection($host, $port); // Create IP connection to brickd
$imu = new BrickIMU($uid); // Create device object

$ipcon->addDevice($imu); // Add device to IP connection
// Don't use device before it is added to a connection

// Set period for quaternion callback to 1s
$imu->setQuaternionPeriod(1000);

// Register position callback to function cb_position
$imu->registerCallback(BrickIMU::CALLBACK_QUATERNION, 'cb_quaternion');

echo "Press ctrl+c to exit\n";
$ipcon->dispatchCallbacks(-1); // Dispatch callbacks forever

?>