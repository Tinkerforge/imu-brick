<?php

require_once('Tinkerforge/IPConnection.php');
require_once('Tinkerforge/BrickIMU.php');

use Tinkerforge\IPConnection;
use Tinkerforge\BrickIMU;

const HOST = 'localhost';
const PORT = 4223;
const UID = 'XXYYZZ'; // Change XXYYZZ to the UID of your IMU Brick

$ipcon = new IPConnection(); // Create IP connection
$imu = new BrickIMU(UID, $ipcon); // Create device object

$ipcon->connect(HOST, PORT); // Connect to brickd
// Don't use device before ipcon is connected

// Get current quaternion
$quaternion = $imu->getQuaternion();

echo "Quaternion[X]: " . $quaternion['x'] . "\n";
echo "Quaternion[Y]: " . $quaternion['y'] . "\n";
echo "Quaternion[Z]: " . $quaternion['z'] . "\n";
echo "Quaternion[W]: " . $quaternion['w'] . "\n";

echo "Press key to exit\n";
fgetc(fopen('php://stdin', 'r'));
$ipcon->disconnect();

?>
