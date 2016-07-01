Imports System
Imports Tinkerforge

Module ExampleSimple
    Const HOST As String = "localhost"
    Const PORT As Integer = 4223
    Const UID As String = "XXYYZZ" ' Change XXYYZZ to the UID of your IMU Brick

    Sub Main()
        Dim ipcon As New IPConnection() ' Create IP connection
        Dim imu As New BrickIMU(UID, ipcon) ' Create device object

        ipcon.Connect(HOST, PORT) ' Connect to brickd
        ' Don't use device before ipcon is connected

        ' Get current quaternion
        Dim x As Single
        Dim y As Single
        Dim z As Single
        Dim w As Single

        imu.GetQuaternion(x, y, z, w)

        Console.WriteLine("Quaternion[X]: " + x.ToString())
        Console.WriteLine("Quaternion[Y]: " + y.ToString())
        Console.WriteLine("Quaternion[Z]: " + z.ToString())
        Console.WriteLine("Quaternion[W]: " + w.ToString())

        Console.WriteLine("Press key to exit")
        Console.ReadLine()
        ipcon.Disconnect()
    End Sub
End Module
