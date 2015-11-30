Imports System
Imports Tinkerforge

Module ExampleCallback
    Const HOST As String = "localhost"
    Const PORT As Integer = 4223
    Const UID As String = "XXYYZZ" ' Change to your UID

    ' Callback subroutine for quaternion callback
    Sub QuaternionCB(ByVal sender As BrickIMU, _
                     ByVal x As Single, ByVal y As Single, ByVal z As Single, ByVal w As Single)
        Console.WriteLine("Quaternion[X]: " + x.ToString())
        Console.WriteLine("Quaternion[Y]: " + y.ToString())
        Console.WriteLine("Quaternion[Z]: " + z.ToString())
        Console.WriteLine("Quaternion[W]: " + w.ToString())
        Console.WriteLine("")
    End Sub

    Sub Main()
        Dim ipcon As New IPConnection() ' Create IP connection
        Dim imu As New BrickIMU(UID, ipcon) ' Create device object

        ipcon.Connect(HOST, PORT) ' Connect to brickd
        ' Don't use device before ipcon is connected

        ' Register quaternion callback to subroutine QuaternionCB
        AddHandler imu.Quaternion, AddressOf QuaternionCB

        ' Set period for quaternion callback to 1s (1000ms)
        ' Note: The quaternion callback is only called every second
        '       if the quaternion has changed since the last call!
        imu.SetQuaternionPeriod(1000)

        Console.WriteLine("Press key to exit")
        Console.ReadLine()
        ipcon.Disconnect()
    End Sub
End Module
