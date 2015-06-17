#!/usr/bin/perl

use Tinkerforge::IPConnection;
use Tinkerforge::BrickIMU;

use constant HOST => 'localhost';
use constant PORT => 4223;
use constant UID => '6JpHZL'; # Change to your UID

my $ipcon = Tinkerforge::IPConnection->new(); # Create IP connection
my $imu = Tinkerforge::BrickIMU->new(&UID, $ipcon); # Create device object

$ipcon->connect(&HOST, &PORT); # Connect to brickd
# Don't use device before ipcon is connected

# Get current quaternion
my ($x, $y, $z, $w) = $imu->get_quaternion();

print "x: $x\n";
print "y: $y\n";
print "z: $z\n";
print "w: $w\n";

print "Press any key to exit...\n";
<STDIN>;
$ipcon->disconnect();
