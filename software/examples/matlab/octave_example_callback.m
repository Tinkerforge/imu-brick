function octave_example_callback()
    more off;

    HOST = "localhost";
    PORT = 4223;
    UID = "XXYYZZ"; % Change XXYYZZ to the UID of your IMU Brick

    ipcon = java_new("com.tinkerforge.IPConnection"); % Create IP connection
    imu = java_new("com.tinkerforge.BrickIMU", UID, ipcon); % Create device object

    ipcon.connect(HOST, PORT); % Connect to brickd
    % Don't use device before ipcon is connected

    % Register quaternion callback to function cb_quaternion
    imu.addQuaternionCallback(@cb_quaternion);

    % Set period for quaternion callback to 1s (1000ms)
    imu.setQuaternionPeriod(1000);

    input("Press key to exit\n", "s");
    ipcon.disconnect();
end

% Callback function for quaternion callback
function cb_quaternion(e)
    fprintf("Quaternion[X]: %f\n", e.x);
    fprintf("Quaternion[Y]: %f\n", e.y);
    fprintf("Quaternion[Z]: %f\n", e.z);
    fprintf("Quaternion[W]: %f\n", e.w);
    fprintf("\n");
end
