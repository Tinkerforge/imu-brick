var IPConnection = require('Tinkerforge/IPConnection');
var BrickIMU = require('Tinkerforge/BrickIMU');

var HOST = 'localhost';
var PORT = 4223;
var UID = '6QFQff';// Change to your UID

var ipcon = new IPConnection();// Create IP connection
var imu = new BrickIMU(UID, ipcon);// Create device object

ipcon.connect(HOST, PORT,
    function(error) {
        if(error === IPConnection.ERROR_ALREADY_CONNECTED) {
            console.log('Error: Already connected');        
        }
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
    function(x, y, z, w) {
        console.log('x: '+x);
        console.log('y: '+y);
        console.log('z: '+z);
        console.log('w: '+w);
        console.log();
    }
);

console.log("Press any key to exit ...");
process.stdin.on('data', function(data) {
	    ipcon.disconnect(
            function(error) {
                if(error === IPConnection.ERROR_NOT_CONNECTED) {
                    console.log('Error: Not connected');        
                }
            }
        );
process.exit(0);
});

