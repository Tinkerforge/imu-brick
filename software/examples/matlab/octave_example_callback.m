function octave_example_callback()
    more off;
    
    HOST = "localhost";
    PORT = 4223;
    UID = "6QFQff"; % Change to your UID

    ipcon = java_new("com.tinkerforge.IPConnection"); % Create IP connection
    imu = java_new("com.tinkerforge.BrickIMU", UID, ipcon); % Create device object

    ipcon.connect(HOST, PORT); % Connect to brickd
    % Don't use device before ipcon is connected

    % Set Period for quaternion callback to 1s (1000ms)
    % Note: The callback is only called every second if the 
    %       quaternion has changed since the last call!
    imu.setQuaternionPeriod(1000);

    % Register quaternion callback to function cb_quaternion
    imu.addQuaternionCallback(@cb_quaternion);

    input("Press any key to exit...\n", "s");
    ipcon.disconnect();
end

% Callback function for quaternion callback
function cb_quaternion(e)
    fprintf("x: %s \n", e.x.toString());
    fprintf("y: %s \n", e.y.toString());
    fprintf("z: %s \n", e.z.toString());
    fprintf("w: %s \n", e.w.toString());
end
