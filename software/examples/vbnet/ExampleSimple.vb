Imports Tinkerforge

Module ExampleSimple
    Const HOST As String = "localhost"
    Const PORT As Integer = 4223
    Const UID As String = "XYZ" ' Change to your UID

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

        System.Console.WriteLine("x: " + x.ToString())
        System.Console.WriteLine("y: " + y.ToString())
        System.Console.WriteLine("z: " + z.ToString())
        System.Console.WriteLine("w: " + w.ToString())

        System.Console.WriteLine("Press key to exit")
        System.Console.ReadLine()
        ipcon.Disconnect()
    End Sub
End Module
