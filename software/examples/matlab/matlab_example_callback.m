function matlab_example_callback()
    import com.tinkerforge.IPConnection;
    import com.tinkerforge.BrickIMU;

    HOST = 'localhost';
    PORT = 4223;
    UID = 'XXYYZZ'; % Change XXYYZZ to the UID of your IMU Brick

    ipcon = IPConnection(); % Create IP connection
    imu = handle(BrickIMU(UID, ipcon), 'CallbackProperties'); % Create device object

    ipcon.connect(HOST, PORT); % Connect to brickd
    % Don't use device before ipcon is connected

    % Register quaternion callback to function cb_quaternion
    set(imu, 'QuaternionCallback', @(h, e) cb_quaternion(e));

    % Set period for quaternion callback to 1s (1000ms)
    imu.setQuaternionPeriod(1000);

    input('Press key to exit\n', 's');
    ipcon.disconnect();
end

% Callback function for quaternion callback
function cb_quaternion(e)
    fprintf('Quaternion [X]: %f\n', e.x);
    fprintf('Quaternion [Y]: %f\n', e.y);
    fprintf('Quaternion [Z]: %f\n', e.z);
    fprintf('Quaternion [W]: %f\n', e.w);
    fprintf('\n');
end
