var IPConnection = require('Tinkerforge/IPConnection');
var BrickIMU = require('Tinkerforge/BrickIMU');

var HOST = 'localhost';
var PORT = 4223;
var UID = '6QFQff';// Change to your UID

var ipcon = new IPConnection();// Create IP connection
var imu = new BrickIMU(UID, ipcon);// Create device object

ipcon.connect(HOST, PORT,
    function(error) {
        console.log('Error: '+error);        
    }
);// Connect to brickd

// Don't use device before ipcon is connected
ipcon.on(IPConnection.CALLBACK_CONNECTED,
    function(connectReason) {
        // Set period for quaternion callback to 1s
        imu.setQuaternionPeriod(1000);
    }
);

// Register quaternion callback
imu.on(BrickIMU.CALLBACK_QUATERNION,
    // Quaternion callback
    function(x, y, z, w) {
        console.log('x: '+x);
        console.log('y: '+y);
        console.log('z: '+z);
        console.log('w: '+w);
        console.log();
    }
);

console.log("Press any key to exit ...");
process.stdin.on('data',
    function(data) {
        ipcon.disconnect();
        process.exit(0);
    }
);

