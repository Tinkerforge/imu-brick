program ExampleCallback;

{$ifdef MSWINDOWS}{$apptype CONSOLE}{$endif}
{$ifdef FPC}{$mode OBJFPC}{$H+}{$endif}

uses
  SysUtils, IPConnection, BrickIMU;

type
  TExample = class
  private
    ipcon: TIPConnection;
    imu: TBrickIMU;
  public
    procedure QuaternionCB(sender: TBrickIMU; 
                           const x: single; const y: single;
                           const z: single; const w: single);
    procedure Execute;
  end;

const
  HOST = 'localhost';
  PORT = 4223;
  UID = 'a4JriVLwq7E'; { Change to your UID }

var
  e: TExample;

{ Quaternion callback }
procedure TExample.QuaternionCB(sender: TBrickIMU; 
                                const x: single; const y: single;
                                const z: single; const w: single);
begin
  WriteLn(Format('x: %.6f', [x]));
  WriteLn(Format('y: %.6f', [y]));
  WriteLn(Format('z: %.6f', [z]));
  WriteLn(Format('w: %.6f', [w]));
  WriteLn('');
end;

procedure TExample.Execute;
begin
  { Create IP connection }
  ipcon := TIPConnection.Create;

  { Create device object }
  imu := TBrickIMU.Create(UID, ipcon);

  { Connect to brickd }
  ipcon.Connect(HOST, PORT);
  { Don't use device before ipcon is connected }

  { Set period for quaternion callback to 1s }
  imu.SetQuaternionPeriod(1000);

  { Register "quaternion callback" to procedure QuaternionCB }
  imu.OnQuaternion := {$ifdef FPC}@{$endif}QuaternionCB;

  WriteLn('Press key to exit');
  ReadLn;
  ipcon.Destroy; { Calls ipcon.Disconnect internally }
end;

begin
  e := TExample.Create;
  e.Execute;
  e.Destroy;
end.
