function octave_example_simple()
    more off;

    HOST = "localhost";
    PORT = 4223;
    UID = "6rJFq7"; % Change to your UID

    ipcon = java_new("com.tinkerforge.IPConnection"); % Create IP connection
    imu = java_new("com.tinkerforge.BrickIMU", UID, ipcon); % Create device object

    ipcon.connect(HOST, PORT); % Connect to brickd
    % Don't use device before ipcon is connected

    % Get current quaternion
    quaternion = imu.getQuaternion();
    fprintf("x: %f\n", quaternion.x);
    fprintf("y: %f\n", quaternion.y);
    fprintf("z: %f\n", quaternion.z);
    fprintf("w: %f\n", quaternion.w);

    input("Press any key to exit...\n", "s");
    ipcon.disconnect();
end
