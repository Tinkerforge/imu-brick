#!/usr/bin/perl

use Tinkerforge::IPConnection;
use Tinkerforge::BrickIMU;

use constant HOST => 'localhost';
use constant PORT => 4223;
use constant UID => 'XXYYZZ'; # Change XXYYZZ to the UID of your IMU Brick

# Callback subroutine for quaternion callback
sub cb_quaternion
{
    my ($x, $y, $z, $w) = @_;

    print "Quaternion [X]: $x\n";
    print "Quaternion [Y]: $y\n";
    print "Quaternion [Z]: $z\n";
    print "Quaternion [W]: $w\n";
    print "\n";
}

my $ipcon = Tinkerforge::IPConnection->new(); # Create IP connection
my $imu = Tinkerforge::BrickIMU->new(&UID, $ipcon); # Create device object

$ipcon->connect(&HOST, &PORT); # Connect to brickd
# Don't use device before ipcon is connected

# Register quaternion callback to subroutine cb_quaternion
$imu->register_callback($imu->CALLBACK_QUATERNION, 'cb_quaternion');

# Set period for quaternion callback to 1s (1000ms)
$imu->set_quaternion_period(1000);

print "Press key to exit\n";
<STDIN>;
$ipcon->disconnect();
