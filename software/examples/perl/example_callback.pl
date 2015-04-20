#!/usr/bin/perl  

use Tinkerforge::IPConnection;
use Tinkerforge::BrickIMU;

use constant HOST => 'localhost';
use constant PORT => 4223;
use constant UID => '6R4m4d'; # Change to your UID

# Quaternion callback
sub cb_quaternion
{
    my ($x, $y, $z, $w) = @_;

    print "x: $x\n";
    print "y: $y\n";
    print "z: $z\n";
    print "w: $w\n";
    print "\n";
}

my $ipcon = Tinkerforge::IPConnection->new(); # Create IP connection
my $imu = Tinkerforge::BrickIMU->new(&UID, $ipcon); # Create device object

$ipcon->connect(&HOST, &PORT); # Connect to brickd
# Don't use device before ipcon is connected

# Set period for quaternion callback to 1s
$imu->set_quaternion_period(1000);

# Register quaternion callback
$imu->register_callback($imu->CALLBACK_QUATERNION, 'cb_quaternion');

print "Press any key to exit...\n";
<STDIN>;
$ipcon->disconnect();
