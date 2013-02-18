Imports Tinkerforge

Module ExampleCallback
    Const HOST As String = "localhost"
    Const PORT As Integer = 4223
    Const UID As String = "6qCwA7" ' Change to your UID

    ' Quaternion callback
    Sub QuaternionCB(ByVal sender As BrickIMU, _
                     ByVal x As Single, ByVal y As Single, ByVal z As Single, ByVal w As Single)
        System.Console.WriteLine("x: " + x.ToString())
        System.Console.WriteLine("y: " + y.ToString())
        System.Console.WriteLine("z: " + z.ToString())
        System.Console.WriteLine("w: " + w.ToString())
        System.Console.WriteLine("")
    End Sub

    Sub Main()
        Dim ipcon As New IPConnection() ' Create IP connection
        Dim imu As New BrickIMU(UID, ipcon) ' Create device object

        ipcon.Connect(HOST, PORT) ' Connect to brickd
        ' Don't use device before ipcon is connected

        ' Set period for quaternion callback to 1s
        imu.SetQuaternionPeriod(1000)

        ' Register quaternion callback to QuaternionCB
        AddHandler imu.Quaternion, AddressOf QuaternionCB

        System.Console.WriteLine("Press key to exit")
        System.Console.ReadKey()
        ipcon.Disconnect()
    End Sub
End Module
