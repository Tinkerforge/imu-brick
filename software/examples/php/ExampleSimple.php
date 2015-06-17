<?php

require_once('Tinkerforge/IPConnection.php');
require_once('Tinkerforge/BrickIMU.php');

use Tinkerforge\IPConnection;
use Tinkerforge\BrickIMU;

const HOST = 'localhost';
const PORT = 4223;
const UID = '6JpHZL'; // Change to your UID

$ipcon = new IPConnection(); // Create IP connection
$imu = new BrickIMU(UID, $ipcon); // Create device object

$ipcon->connect(HOST, PORT); // Connect to brickd
// Don't use device before ipcon is connected

// Get current quaternion
$q = $imu->getquaternion();

echo "x: ".$q['x']."\n";
echo "y: ".$q['y']."\n";
echo "z: ".$q['z']."\n";
echo "w: ".$q['w']."\n";

echo "Press key to exit\n";
fgetc(fopen('php://stdin', 'r'));
$ipcon->disconnect();

?>
