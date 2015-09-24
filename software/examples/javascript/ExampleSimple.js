var Tinkerforge = require('tinkerforge');

var HOST = 'localhost';
var PORT = 4223;
var UID = 'XXYYZZ'; // Change to your UID

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
        // Get current quaternion
        imu.getQuaternion(
            function (x, y, z, w) {
                console.log('Quaternion[X]: ' + x);
                console.log('Quaternion[Y]: ' + y);
                console.log('Quaternion[Z]: ' + z);
                console.log('Quaternion[W]: ' + w);
            },
            function (error) {
                console.log('Error: ' + error);
            }
        );
    }
);

console.log('Press key to exit');
process.stdin.on('data',
    function (data) {
        ipcon.disconnect();
        process.exit(0);
    }
);
