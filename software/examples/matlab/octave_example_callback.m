function octave_example_callback
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

    % Register position callback to function cb_position
    imu.addQuaternionListener("cb_quaternion");

    input("\nPress any key to exit...\n", "s");
    ipcon.disconnect();
end

% Callback function for quaternion callback
function cb_quaternion(x, y ,z, w)
    fprintf("x: %s \n", x.toString());
    fprintf("y: %s \n", y.toString());
    fprintf("z: %s \n", z.toString());
    fprintf("w: %s \n", w.toString());
end
