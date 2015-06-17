function matlab_example_simple()
    import com.tinkerforge.IPConnection;
    import com.tinkerforge.BrickIMU;

    HOST = 'localhost';
    PORT = 4223;
    UID = '6JpHZL'; % Change to your UID

    ipcon = IPConnection(); % Create IP connection
    imu = BrickIMU(UID, ipcon); % Create device object

    ipcon.connect(HOST, PORT); % Connect to brickd
    % Don't use device before ipcon is connected

    % Get current quaternion
    q = imu.getQuaternion();
    fprintf("x: %g\n", q.x);
    fprintf("y: %g\n", q.y);
    fprintf("z: %g\n", q.z);
    fprintf("w: %g\n", q.w);

    input('Press any key to exit...\n', 's');
    ipcon.disconnect();
end
