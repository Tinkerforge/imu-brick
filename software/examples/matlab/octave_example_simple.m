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
    q = imu.getQuaternion();
    fprintf("x: %f\n", q.x);
    fprintf("y: %f\n", q.y);
    fprintf("z: %f\n", q.z);
    fprintf("w: %f\n", q.w);

    input("Press any key to exit...\n", "s");
    ipcon.disconnect();
end
