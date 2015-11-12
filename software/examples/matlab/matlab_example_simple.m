function matlab_example_simple()
    import com.tinkerforge.IPConnection;
    import com.tinkerforge.BrickIMU;

    HOST = 'localhost';
    PORT = 4223;
    UID = 'XXYYZZ'; % Change to your UID

    ipcon = IPConnection(); % Create IP connection
    imu = handle(BrickIMU(UID, ipcon), 'CallbackProperties'); % Create device object

    ipcon.connect(HOST, PORT); % Connect to brickd
    % Don't use device before ipcon is connected

    % Get current quaternion
    quaternion = imu.getQuaternion();

    fprintf('Quaternion[X]: %f\n', quaternion.x);
    fprintf('Quaternion[Y]: %f\n', quaternion.y);
    fprintf('Quaternion[Z]: %f\n', quaternion.z);
    fprintf('Quaternion[W]: %f\n', quaternion.w);

    input('Press key to exit\n', 's');
    ipcon.disconnect();
end
