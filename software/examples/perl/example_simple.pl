#!/usr/bin/perl

use Tinkerforge::IPConnection;
use Tinkerforge::BrickIMU;

use constant HOST => 'localhost';
use constant PORT => 4223;
use constant UID => 'XXYYZZ'; # Change XXYYZZ to the UID of your IMU Brick

my $ipcon = Tinkerforge::IPConnection->new(); # Create IP connection
my $imu = Tinkerforge::BrickIMU->new(&UID, $ipcon); # Create device object

$ipcon->connect(&HOST, &PORT); # Connect to brickd
# Don't use device before ipcon is connected

# Get current quaternion
my ($x, $y, $z, $w) = $imu->get_quaternion();

print "Quaternion[X]: $x\n";
print "Quaternion[Y]: $y\n";
print "Quaternion[Z]: $z\n";
print "Quaternion[W]: $w\n";

print "Press key to exit\n";
<STDIN>;
$ipcon->disconnect();
