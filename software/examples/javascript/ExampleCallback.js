var Tinkerforge = require('tinkerforge');

var HOST = 'localhost';
var PORT = 4223;
var UID = 'XXYYZZ'; // Change XXYYZZ to the UID of your IMU Brick

var ipcon = new Tinkerforge.IPConnection(); // Create IP connection
var imu = new Tinkerforge.BrickIMU(UID, ipcon); // Create device object

ipcon.connect(HOST, PORT,
    function (error) {
        console.log('Error: ' + error);
    }
); // Connect to brickd
// Don't use device before ipcon is connected

ipcon.on(Tinkerforge.IPConnection.CALLBACK_CONNECTED,
    function (connectReason) {
        // Set period for quaternion callback to 1s (1000ms)
        // Note: The quaternion callback is only called every second
        //       if the quaternion has changed since the last call!
        imu.setQuaternionPeriod(1000);
    }
);

// Register quaternion callback
imu.on(Tinkerforge.BrickIMU.CALLBACK_QUATERNION,
    // Callback function for quaternion callback
    function (x, y, z, w) {
        console.log('Quaternion[X]: ' + x);
        console.log('Quaternion[Y]: ' + y);
        console.log('Quaternion[Z]: ' + z);
        console.log('Quaternion[W]: ' + w);
        console.log();
    }
);

console.log('Press key to exit');
process.stdin.on('data',
    function (data) {
        ipcon.disconnect();
        process.exit(0);
    }
);
