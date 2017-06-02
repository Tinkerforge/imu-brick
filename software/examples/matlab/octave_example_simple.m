function octave_example_simple()
    more off;

    HOST = "localhost";
    PORT = 4223;
    UID = "XXYYZZ"; % Change XXYYZZ to the UID of your IMU Brick

    ipcon = javaObject("com.tinkerforge.IPConnection"); % Create IP connection
    imu = javaObject("com.tinkerforge.BrickIMU", UID, ipcon); % Create device object

    ipcon.connect(HOST, PORT); % Connect to brickd
    % Don't use device before ipcon is connected

    % Get current quaternion
    quaternion = imu.getQuaternion();

    fprintf("Quaternion[X]: %f\n", quaternion.x);
    fprintf("Quaternion[Y]: %f\n", quaternion.y);
    fprintf("Quaternion[Z]: %f\n", quaternion.z);
    fprintf("Quaternion[W]: %f\n", quaternion.w);

    input("Press key to exit\n", "s");
    ipcon.disconnect();
end
