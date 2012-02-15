/*************************************************************
 * This file was automatically generated on 2011-10-03.      *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the generator. You can find a link  *
 * to the generator git on tinkerforge.com                   *
 *************************************************************/

namespace Tinkerforge
{
	public class BrickIMU : Device 
	{
		private static byte TYPE_GET_ACCELERATION = 1;
		private static byte TYPE_GET_MAGNETIC_FIELD = 2;
		private static byte TYPE_GET_ANGULAR_VELOCITY = 3;
		private static byte TYPE_GET_ALL_DATA = 4;
		private static byte TYPE_GET_ORIENTATION = 5;
		private static byte TYPE_GET_QUATERNION = 6;
		private static byte TYPE_GET_IMU_TEMPERATURE = 7;
		private static byte TYPE_LEDS_ON = 8;
		private static byte TYPE_LEDS_OFF = 9;
		private static byte TYPE_ARE_LEDS_ON = 10;
		private static byte TYPE_SET_ACCELERATION_RANGE = 11;
		private static byte TYPE_GET_ACCELERATION_RANGE = 12;
		private static byte TYPE_SET_MAGNETOMETER_RANGE = 13;
		private static byte TYPE_GET_MAGNETOMETER_RANGE = 14;
		private static byte TYPE_SET_ZERO = 15;
		private static byte TYPE_SET_DEBOUNCE_PERIOD = 16;
		private static byte TYPE_GET_DEBOUNCE_PERIOD = 17;
		private static byte TYPE_SET_ACCELERATION_THRESHOLD = 18;
		private static byte TYPE_GET_ACCELERATION_THRESHOLD = 19;
		private static byte TYPE_SET_MAGNETIC_FIELD_THRESHOLD = 20;
		private static byte TYPE_GET_MAGNETIC_FIELD_THRESHOLD = 21;
		private static byte TYPE_SET_ANGULAR_VELOCITY_THRESHOLD = 22;
		private static byte TYPE_GET_ANGULAR_VELOCITY_THRESHOLD = 23;
		private static byte TYPE_SET_ALL_DATA_THRESHOLD = 24;
		private static byte TYPE_GET_ALL_DATA_THRESHOLD = 25;
		private static byte TYPE_SET_ORIENTATION_THRESHOLD = 26;
		private static byte TYPE_GET_ORIENTATION_THRESHOLD = 27;
		private static byte TYPE_SET_ACCELERATION_PERIOD = 28;
		private static byte TYPE_GET_ACCELERATION_PERIOD = 29;
		private static byte TYPE_SET_MAGNETIC_FIELD_PERIOD = 30;
		private static byte TYPE_GET_MAGNETIC_FIELD_PERIOD = 31;
		private static byte TYPE_SET_ANGULAR_VELOCITY_PERIOD = 32;
		private static byte TYPE_GET_ANGULAR_VELOCITY_PERIOD = 33;
		private static byte TYPE_SET_ALL_DATA_PERIOD = 34;
		private static byte TYPE_GET_ALL_DATA_PERIOD = 35;
		private static byte TYPE_SET_ORIENTATION_PERIOD = 36;
		private static byte TYPE_GET_ORIENTATION_PERIOD = 37;
		private static byte TYPE_SET_QUATERNION_PERIOD = 38;
		private static byte TYPE_GET_QUATERNION_PERIOD = 39;
		private static byte TYPE_ACCELERATION = 40;
		private static byte TYPE_MAGNETIC_FIELD = 41;
		private static byte TYPE_ANGULAR_VELOCITY = 42;
		private static byte TYPE_ALL_DATA = 43;
		private static byte TYPE_ORIENTATION = 44;
		private static byte TYPE_QUATERNION = 45;
		private static byte TYPE_ACCELERATION_REACHED = 46;
		private static byte TYPE_MAGNETIC_FIELD_REACHED = 47;
		private static byte TYPE_ANGULAR_VELOCITY_REACHED = 48;
		private static byte TYPE_ALL_DATA_REACHED = 49;
		private static byte TYPE_ORIENTATION_REACHED = 50;

		public delegate void Acceleration(short x, short y, short z);
		public delegate void MagneticField(short x, short y, short z);
		public delegate void AngularVelocity(short x, short y, short z);
		public delegate void AllData(short accX, short accY, short accZ, short magX, short magY, short magZ, short angX, short angY, short angZ, short temperature);
		public delegate void Orientation(short roll, short pitch, short yaw);
		public delegate void Quaternion(float w, float x, float y, float z);
		public delegate void AccelerationReached(short x, short y, short z);
		public delegate void MagneticFieldReached(short x, short y, short z);
		public delegate void AngularVelocityReached(short x, short y, short z);
		public delegate void AllDataReached(short accX, short accY, short accZ, short magX, short magY, short magZ, short angX, short angY, short angZ, short temperature);
		public delegate void OrientationReached(short roll, short pitch, short yaw);

		public BrickIMU(string uid) : base(uid) 
		{
			messageCallbacks[TYPE_ACCELERATION] = new MessageCallback(CallbackAcceleration);
			messageCallbacks[TYPE_MAGNETIC_FIELD] = new MessageCallback(CallbackMagneticField);
			messageCallbacks[TYPE_ANGULAR_VELOCITY] = new MessageCallback(CallbackAngularVelocity);
			messageCallbacks[TYPE_ALL_DATA] = new MessageCallback(CallbackAllData);
			messageCallbacks[TYPE_ORIENTATION] = new MessageCallback(CallbackOrientation);
			messageCallbacks[TYPE_QUATERNION] = new MessageCallback(CallbackQuaternion);
			messageCallbacks[TYPE_ACCELERATION_REACHED] = new MessageCallback(CallbackAccelerationReached);
			messageCallbacks[TYPE_MAGNETIC_FIELD_REACHED] = new MessageCallback(CallbackMagneticFieldReached);
			messageCallbacks[TYPE_ANGULAR_VELOCITY_REACHED] = new MessageCallback(CallbackAngularVelocityReached);
			messageCallbacks[TYPE_ALL_DATA_REACHED] = new MessageCallback(CallbackAllDataReached);
			messageCallbacks[TYPE_ORIENTATION_REACHED] = new MessageCallback(CallbackOrientationReached);
		}

		public void GetAcceleration(out short x, out short y, out short z)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ACCELERATION, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_ACCELERATION, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAcceleration in time");
			}

			x = LEConverter.ShortFrom(4, answer);
			y = LEConverter.ShortFrom(6, answer);
			z = LEConverter.ShortFrom(8, answer);

			writeEvent.Set();
		}

		public void GetMagneticField(out short x, out short y, out short z)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_MAGNETIC_FIELD, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_MAGNETIC_FIELD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetMagneticField in time");
			}

			x = LEConverter.ShortFrom(4, answer);
			y = LEConverter.ShortFrom(6, answer);
			z = LEConverter.ShortFrom(8, answer);

			writeEvent.Set();
		}

		public void GetAngularVelocity(out short x, out short y, out short z)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ANGULAR_VELOCITY, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_ANGULAR_VELOCITY, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAngularVelocity in time");
			}

			x = LEConverter.ShortFrom(4, answer);
			y = LEConverter.ShortFrom(6, answer);
			z = LEConverter.ShortFrom(8, answer);

			writeEvent.Set();
		}

		public void GetAllData(out short accX, out short accY, out short accZ, out short magX, out short magY, out short magZ, out short angX, out short angY, out short angZ, out short temperature)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ALL_DATA, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_ALL_DATA, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAllData in time");
			}

			accX = LEConverter.ShortFrom(4, answer);
			accY = LEConverter.ShortFrom(6, answer);
			accZ = LEConverter.ShortFrom(8, answer);
			magX = LEConverter.ShortFrom(10, answer);
			magY = LEConverter.ShortFrom(12, answer);
			magZ = LEConverter.ShortFrom(14, answer);
			angX = LEConverter.ShortFrom(16, answer);
			angY = LEConverter.ShortFrom(18, answer);
			angZ = LEConverter.ShortFrom(20, answer);
			temperature = LEConverter.ShortFrom(22, answer);

			writeEvent.Set();
		}

		public void GetOrientation(out short roll, out short pitch, out short yaw)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ORIENTATION, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_ORIENTATION, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetOrientation in time");
			}

			roll = LEConverter.ShortFrom(4, answer);
			pitch = LEConverter.ShortFrom(6, answer);
			yaw = LEConverter.ShortFrom(8, answer);

			writeEvent.Set();
		}

		public void GetQuaternion(out float w, out float x, out float y, out float z)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_QUATERNION, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_QUATERNION, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetQuaternion in time");
			}

			w = LEConverter.FloatFrom(4, answer);
			x = LEConverter.FloatFrom(8, answer);
			y = LEConverter.FloatFrom(12, answer);
			z = LEConverter.FloatFrom(16, answer);

			writeEvent.Set();
		}

		public void GetIMUTemperature(out short temperature)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_IMU_TEMPERATURE, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_IMU_TEMPERATURE, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetIMUTemperature in time");
			}

			temperature = LEConverter.ShortFrom(4, answer);

			writeEvent.Set();
		}

		public void LedsOn()
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_LEDS_ON, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_LEDS_ON, false);
		}

		public void LedsOff()
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_LEDS_OFF, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_LEDS_OFF, false);
		}

		public void AreLedsOn(out bool leds)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_ARE_LEDS_ON, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_ARE_LEDS_ON, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for AreLedsOn in time");
			}

			leds = LEConverter.BoolFrom(4, answer);

			writeEvent.Set();
		}

		public void SetAccelerationRange(byte range)
		{
			byte[] data = new byte[5];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ACCELERATION_RANGE, 1, data);
			LEConverter.To((ushort)5, 2, data);
			LEConverter.To(range, 4, data);

			ipcon.Write(this, data, TYPE_SET_ACCELERATION_RANGE, false);
		}

		public void GetAccelerationRange(out byte range)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ACCELERATION_RANGE, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_ACCELERATION_RANGE, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAccelerationRange in time");
			}

			range = LEConverter.ByteFrom(4, answer);

			writeEvent.Set();
		}

		public void SetMagnetometerRange(byte range)
		{
			byte[] data = new byte[5];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_MAGNETOMETER_RANGE, 1, data);
			LEConverter.To((ushort)5, 2, data);
			LEConverter.To(range, 4, data);

			ipcon.Write(this, data, TYPE_SET_MAGNETOMETER_RANGE, false);
		}

		public void GetMagnetometerRange(out byte range)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_MAGNETOMETER_RANGE, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_MAGNETOMETER_RANGE, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetMagnetometerRange in time");
			}

			range = LEConverter.ByteFrom(4, answer);

			writeEvent.Set();
		}

		public void SetZero()
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ZERO, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_SET_ZERO, false);
		}

		public void SetDebouncePeriod(uint debouncePeriod)
		{
			byte[] data = new byte[8];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_DEBOUNCE_PERIOD, 1, data);
			LEConverter.To((ushort)8, 2, data);
			LEConverter.To(debouncePeriod, 4, data);

			ipcon.Write(this, data, TYPE_SET_DEBOUNCE_PERIOD, false);
		}

		public void GetDebouncePeriod(out uint debouncePeriod)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_DEBOUNCE_PERIOD, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_DEBOUNCE_PERIOD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetDebouncePeriod in time");
			}

			debouncePeriod = LEConverter.UIntFrom(4, answer);

			writeEvent.Set();
		}

		public void SetAccelerationThreshold(byte num, short[] threshold, char[] option)
		{
			byte[] data = new byte[14];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ACCELERATION_THRESHOLD, 1, data);
			LEConverter.To((ushort)14, 2, data);
			LEConverter.To(num, 4, data);
			LEConverter.To(threshold, 5, data);
			LEConverter.To(option, 11, data);

			ipcon.Write(this, data, TYPE_SET_ACCELERATION_THRESHOLD, false);
		}

		public void GetAccelerationThreshold(byte num, out short[] threshold, out char[] option)
		{
			byte[] data = new byte[5];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ACCELERATION_THRESHOLD, 1, data);
			LEConverter.To((ushort)5, 2, data);
			LEConverter.To(num, 4, data);

			ipcon.Write(this, data, TYPE_GET_ACCELERATION_THRESHOLD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAccelerationThreshold in time");
			}

			threshold = LEConverter.ShortArrayFrom(4, answer, 3);
			option = LEConverter.CharArrayFrom(10, answer, 3);

			writeEvent.Set();
		}

		public void SetMagneticFieldThreshold(byte num, short[] threshold, char[] option)
		{
			byte[] data = new byte[14];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_MAGNETIC_FIELD_THRESHOLD, 1, data);
			LEConverter.To((ushort)14, 2, data);
			LEConverter.To(num, 4, data);
			LEConverter.To(threshold, 5, data);
			LEConverter.To(option, 11, data);

			ipcon.Write(this, data, TYPE_SET_MAGNETIC_FIELD_THRESHOLD, false);
		}

		public void GetMagneticFieldThreshold(byte num, out short[] threshold, out char[] option)
		{
			byte[] data = new byte[5];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_MAGNETIC_FIELD_THRESHOLD, 1, data);
			LEConverter.To((ushort)5, 2, data);
			LEConverter.To(num, 4, data);

			ipcon.Write(this, data, TYPE_GET_MAGNETIC_FIELD_THRESHOLD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetMagneticFieldThreshold in time");
			}

			threshold = LEConverter.ShortArrayFrom(4, answer, 3);
			option = LEConverter.CharArrayFrom(10, answer, 3);

			writeEvent.Set();
		}

		public void SetAngularVelocityThreshold(byte num, short[] threshold, char[] option)
		{
			byte[] data = new byte[14];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ANGULAR_VELOCITY_THRESHOLD, 1, data);
			LEConverter.To((ushort)14, 2, data);
			LEConverter.To(num, 4, data);
			LEConverter.To(threshold, 5, data);
			LEConverter.To(option, 11, data);

			ipcon.Write(this, data, TYPE_SET_ANGULAR_VELOCITY_THRESHOLD, false);
		}

		public void GetAngularVelocityThreshold(byte num, out short[] threshold, out char[] option)
		{
			byte[] data = new byte[5];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ANGULAR_VELOCITY_THRESHOLD, 1, data);
			LEConverter.To((ushort)5, 2, data);
			LEConverter.To(num, 4, data);

			ipcon.Write(this, data, TYPE_GET_ANGULAR_VELOCITY_THRESHOLD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAngularVelocityThreshold in time");
			}

			threshold = LEConverter.ShortArrayFrom(4, answer, 3);
			option = LEConverter.CharArrayFrom(10, answer, 3);

			writeEvent.Set();
		}

		public void SetAllDataThreshold(byte num, short[] threshold, char[] option)
		{
			byte[] data = new byte[32];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ALL_DATA_THRESHOLD, 1, data);
			LEConverter.To((ushort)32, 2, data);
			LEConverter.To(num, 4, data);
			LEConverter.To(threshold, 5, data);
			LEConverter.To(option, 23, data);

			ipcon.Write(this, data, TYPE_SET_ALL_DATA_THRESHOLD, false);
		}

		public void GetAllDataThreshold(byte num, out short[] threshold, out char[] option)
		{
			byte[] data = new byte[5];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ALL_DATA_THRESHOLD, 1, data);
			LEConverter.To((ushort)5, 2, data);
			LEConverter.To(num, 4, data);

			ipcon.Write(this, data, TYPE_GET_ALL_DATA_THRESHOLD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAllDataThreshold in time");
			}

			threshold = LEConverter.ShortArrayFrom(4, answer, 9);
			option = LEConverter.CharArrayFrom(22, answer, 9);

			writeEvent.Set();
		}

		public void SetOrientationThreshold(byte num, short[] threshold, char[] option)
		{
			byte[] data = new byte[14];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ORIENTATION_THRESHOLD, 1, data);
			LEConverter.To((ushort)14, 2, data);
			LEConverter.To(num, 4, data);
			LEConverter.To(threshold, 5, data);
			LEConverter.To(option, 11, data);

			ipcon.Write(this, data, TYPE_SET_ORIENTATION_THRESHOLD, false);
		}

		public void GetOrientationThreshold(byte num, out short[] threshold, out char[] option)
		{
			byte[] data = new byte[5];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ORIENTATION_THRESHOLD, 1, data);
			LEConverter.To((ushort)5, 2, data);
			LEConverter.To(num, 4, data);

			ipcon.Write(this, data, TYPE_GET_ORIENTATION_THRESHOLD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetOrientationThreshold in time");
			}

			threshold = LEConverter.ShortArrayFrom(4, answer, 3);
			option = LEConverter.CharArrayFrom(10, answer, 3);

			writeEvent.Set();
		}

		public void SetAccelerationPeriod(uint period)
		{
			byte[] data = new byte[8];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ACCELERATION_PERIOD, 1, data);
			LEConverter.To((ushort)8, 2, data);
			LEConverter.To(period, 4, data);

			ipcon.Write(this, data, TYPE_SET_ACCELERATION_PERIOD, false);
		}

		public void GetAccelerationPeriod(out uint period)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ACCELERATION_PERIOD, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_ACCELERATION_PERIOD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAccelerationPeriod in time");
			}

			period = LEConverter.UIntFrom(4, answer);

			writeEvent.Set();
		}

		public void SetMagneticFieldPeriod(uint period)
		{
			byte[] data = new byte[8];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_MAGNETIC_FIELD_PERIOD, 1, data);
			LEConverter.To((ushort)8, 2, data);
			LEConverter.To(period, 4, data);

			ipcon.Write(this, data, TYPE_SET_MAGNETIC_FIELD_PERIOD, false);
		}

		public void GetMagneticFieldPeriod(out uint period)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_MAGNETIC_FIELD_PERIOD, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_MAGNETIC_FIELD_PERIOD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetMagneticFieldPeriod in time");
			}

			period = LEConverter.UIntFrom(4, answer);

			writeEvent.Set();
		}

		public void SetAngularVelocityPeriod(uint period)
		{
			byte[] data = new byte[8];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ANGULAR_VELOCITY_PERIOD, 1, data);
			LEConverter.To((ushort)8, 2, data);
			LEConverter.To(period, 4, data);

			ipcon.Write(this, data, TYPE_SET_ANGULAR_VELOCITY_PERIOD, false);
		}

		public void GetAngularVelocityPeriod(out uint period)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ANGULAR_VELOCITY_PERIOD, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_ANGULAR_VELOCITY_PERIOD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAngularVelocityPeriod in time");
			}

			period = LEConverter.UIntFrom(4, answer);

			writeEvent.Set();
		}

		public void SetAllDataPeriod(uint period)
		{
			byte[] data = new byte[8];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ALL_DATA_PERIOD, 1, data);
			LEConverter.To((ushort)8, 2, data);
			LEConverter.To(period, 4, data);

			ipcon.Write(this, data, TYPE_SET_ALL_DATA_PERIOD, false);
		}

		public void GetAllDataPeriod(out uint period)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ALL_DATA_PERIOD, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_ALL_DATA_PERIOD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetAllDataPeriod in time");
			}

			period = LEConverter.UIntFrom(4, answer);

			writeEvent.Set();
		}

		public void SetOrientationPeriod(uint period)
		{
			byte[] data = new byte[8];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_ORIENTATION_PERIOD, 1, data);
			LEConverter.To((ushort)8, 2, data);
			LEConverter.To(period, 4, data);

			ipcon.Write(this, data, TYPE_SET_ORIENTATION_PERIOD, false);
		}

		public void GetOrientationPeriod(out uint period)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_ORIENTATION_PERIOD, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_ORIENTATION_PERIOD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetOrientationPeriod in time");
			}

			period = LEConverter.UIntFrom(4, answer);

			writeEvent.Set();
		}

		public void SetQuaternionPeriod(uint period)
		{
			byte[] data = new byte[8];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_SET_QUATERNION_PERIOD, 1, data);
			LEConverter.To((ushort)8, 2, data);
			LEConverter.To(period, 4, data);

			ipcon.Write(this, data, TYPE_SET_QUATERNION_PERIOD, false);
		}

		public void GetQuaternionPeriod(out uint period)
		{
			byte[] data = new byte[4];
			LEConverter.To(stackID, 0, data);
			LEConverter.To(TYPE_GET_QUATERNION_PERIOD, 1, data);
			LEConverter.To((ushort)4, 2, data);

			ipcon.Write(this, data, TYPE_GET_QUATERNION_PERIOD, true);

			byte[] answer;
			if(!answerQueue.TryDequeue(out answer, IPConnection.TIMEOUT_ANSWER))
			{
				throw new TimeoutException("Did not receive answer for GetQuaternionPeriod in time");
			}

			period = LEConverter.UIntFrom(4, answer);

			writeEvent.Set();
		}

		public int CallbackAcceleration(byte[] data)
		{
			short x = LEConverter.ShortFrom(4, data);
			short y = LEConverter.ShortFrom(6, data);
			short z = LEConverter.ShortFrom(8, data);

			((Acceleration)callbacks[TYPE_ACCELERATION])(x, y, z);
			return 10;
		}

		public int CallbackMagneticField(byte[] data)
		{
			short x = LEConverter.ShortFrom(4, data);
			short y = LEConverter.ShortFrom(6, data);
			short z = LEConverter.ShortFrom(8, data);

			((MagneticField)callbacks[TYPE_MAGNETIC_FIELD])(x, y, z);
			return 10;
		}

		public int CallbackAngularVelocity(byte[] data)
		{
			short x = LEConverter.ShortFrom(4, data);
			short y = LEConverter.ShortFrom(6, data);
			short z = LEConverter.ShortFrom(8, data);

			((AngularVelocity)callbacks[TYPE_ANGULAR_VELOCITY])(x, y, z);
			return 10;
		}

		public int CallbackAllData(byte[] data)
		{
			short accX = LEConverter.ShortFrom(4, data);
			short accY = LEConverter.ShortFrom(6, data);
			short accZ = LEConverter.ShortFrom(8, data);
			short magX = LEConverter.ShortFrom(10, data);
			short magY = LEConverter.ShortFrom(12, data);
			short magZ = LEConverter.ShortFrom(14, data);
			short angX = LEConverter.ShortFrom(16, data);
			short angY = LEConverter.ShortFrom(18, data);
			short angZ = LEConverter.ShortFrom(20, data);
			short temperature = LEConverter.ShortFrom(22, data);

			((AllData)callbacks[TYPE_ALL_DATA])(accX, accY, accZ, magX, magY, magZ, angX, angY, angZ, temperature);
			return 24;
		}

		public int CallbackOrientation(byte[] data)
		{
			short roll = LEConverter.ShortFrom(4, data);
			short pitch = LEConverter.ShortFrom(6, data);
			short yaw = LEConverter.ShortFrom(8, data);

			((Orientation)callbacks[TYPE_ORIENTATION])(roll, pitch, yaw);
			return 10;
		}

		public int CallbackQuaternion(byte[] data)
		{
			float w = LEConverter.FloatFrom(4, data);
			float x = LEConverter.FloatFrom(8, data);
			float y = LEConverter.FloatFrom(12, data);
			float z = LEConverter.FloatFrom(16, data);

			((Quaternion)callbacks[TYPE_QUATERNION])(w, x, y, z);
			return 20;
		}

		public int CallbackAccelerationReached(byte[] data)
		{
			short x = LEConverter.ShortFrom(4, data);
			short y = LEConverter.ShortFrom(6, data);
			short z = LEConverter.ShortFrom(8, data);

			((AccelerationReached)callbacks[TYPE_ACCELERATION_REACHED])(x, y, z);
			return 10;
		}

		public int CallbackMagneticFieldReached(byte[] data)
		{
			short x = LEConverter.ShortFrom(4, data);
			short y = LEConverter.ShortFrom(6, data);
			short z = LEConverter.ShortFrom(8, data);

			((MagneticFieldReached)callbacks[TYPE_MAGNETIC_FIELD_REACHED])(x, y, z);
			return 10;
		}

		public int CallbackAngularVelocityReached(byte[] data)
		{
			short x = LEConverter.ShortFrom(4, data);
			short y = LEConverter.ShortFrom(6, data);
			short z = LEConverter.ShortFrom(8, data);

			((AngularVelocityReached)callbacks[TYPE_ANGULAR_VELOCITY_REACHED])(x, y, z);
			return 10;
		}

		public int CallbackAllDataReached(byte[] data)
		{
			short accX = LEConverter.ShortFrom(4, data);
			short accY = LEConverter.ShortFrom(6, data);
			short accZ = LEConverter.ShortFrom(8, data);
			short magX = LEConverter.ShortFrom(10, data);
			short magY = LEConverter.ShortFrom(12, data);
			short magZ = LEConverter.ShortFrom(14, data);
			short angX = LEConverter.ShortFrom(16, data);
			short angY = LEConverter.ShortFrom(18, data);
			short angZ = LEConverter.ShortFrom(20, data);
			short temperature = LEConverter.ShortFrom(22, data);

			((AllDataReached)callbacks[TYPE_ALL_DATA_REACHED])(accX, accY, accZ, magX, magY, magZ, angX, angY, angZ, temperature);
			return 24;
		}

		public int CallbackOrientationReached(byte[] data)
		{
			short roll = LEConverter.ShortFrom(4, data);
			short pitch = LEConverter.ShortFrom(6, data);
			short yaw = LEConverter.ShortFrom(8, data);

			((OrientationReached)callbacks[TYPE_ORIENTATION_REACHED])(roll, pitch, yaw);
			return 10;
		}

		public void RegisterCallback(System.Delegate d)
		{
			if(d.GetType() == typeof(Acceleration))
			{
				callbacks[TYPE_ACCELERATION] = d;
			}
			else if(d.GetType() == typeof(MagneticField))
			{
				callbacks[TYPE_MAGNETIC_FIELD] = d;
			}
			else if(d.GetType() == typeof(AngularVelocity))
			{
				callbacks[TYPE_ANGULAR_VELOCITY] = d;
			}
			else if(d.GetType() == typeof(AllData))
			{
				callbacks[TYPE_ALL_DATA] = d;
			}
			else if(d.GetType() == typeof(Orientation))
			{
				callbacks[TYPE_ORIENTATION] = d;
			}
			else if(d.GetType() == typeof(Quaternion))
			{
				callbacks[TYPE_QUATERNION] = d;
			}
			else if(d.GetType() == typeof(AccelerationReached))
			{
				callbacks[TYPE_ACCELERATION_REACHED] = d;
			}
			else if(d.GetType() == typeof(MagneticFieldReached))
			{
				callbacks[TYPE_MAGNETIC_FIELD_REACHED] = d;
			}
			else if(d.GetType() == typeof(AngularVelocityReached))
			{
				callbacks[TYPE_ANGULAR_VELOCITY_REACHED] = d;
			}
			else if(d.GetType() == typeof(AllDataReached))
			{
				callbacks[TYPE_ALL_DATA_REACHED] = d;
			}
			else if(d.GetType() == typeof(OrientationReached))
			{
				callbacks[TYPE_ORIENTATION_REACHED] = d;
			}
		}
	}
}
