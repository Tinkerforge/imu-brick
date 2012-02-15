/*************************************************************
 * This file was automatically generated on 2011-09-13.      *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the generator. You can find a link  *
 * to the generator git on tinkerforge.com                   *
 *************************************************************/

package com.tinkerforge;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;


public class BrickIMU extends Device {
	private final static byte TYPE_GET_ACCELERATION = (byte)1;
	private final static byte TYPE_GET_MAGNETIC_FIELD = (byte)2;
	private final static byte TYPE_GET_ANGULAR_VELOCITY = (byte)3;
	private final static byte TYPE_GET_ALL_DATA = (byte)4;
	private final static byte TYPE_GET_ORIENTATION = (byte)5;
	private final static byte TYPE_GET_QUATERNION = (byte)6;
	private final static byte TYPE_GET_IMU_TEMPERATURE = (byte)7;
	private final static byte TYPE_LEDS_ON = (byte)8;
	private final static byte TYPE_LEDS_OFF = (byte)9;
	private final static byte TYPE_ARE_LEDS_ON = (byte)10;
	private final static byte TYPE_SET_ACCELERATION_RANGE = (byte)11;
	private final static byte TYPE_GET_ACCELERATION_RANGE = (byte)12;
	private final static byte TYPE_SET_MAGNETOMETER_RANGE = (byte)13;
	private final static byte TYPE_GET_MAGNETOMETER_RANGE = (byte)14;
	private final static byte TYPE_SET_ZERO = (byte)15;
	private final static byte TYPE_SET_DEBOUNCE_PERIOD = (byte)16;
	private final static byte TYPE_GET_DEBOUNCE_PERIOD = (byte)17;
	private final static byte TYPE_SET_ACCELERATION_THRESHOLD = (byte)18;
	private final static byte TYPE_GET_ACCELERATION_THRESHOLD = (byte)19;
	private final static byte TYPE_SET_MAGNETIC_FIELD_THRESHOLD = (byte)20;
	private final static byte TYPE_GET_MAGNETIC_FIELD_THRESHOLD = (byte)21;
	private final static byte TYPE_SET_ANGULAR_VELOCITY_THRESHOLD = (byte)22;
	private final static byte TYPE_GET_ANGULAR_VELOCITY_THRESHOLD = (byte)23;
	private final static byte TYPE_SET_ALL_DATA_THRESHOLD = (byte)24;
	private final static byte TYPE_GET_ALL_DATA_THRESHOLD = (byte)25;
	private final static byte TYPE_SET_ORIENTATION_THRESHOLD = (byte)26;
	private final static byte TYPE_GET_ORIENTATION_THRESHOLD = (byte)27;
	private final static byte TYPE_SET_ACCELERATION_PERIOD = (byte)28;
	private final static byte TYPE_GET_ACCELERATION_PERIOD = (byte)29;
	private final static byte TYPE_SET_MAGNETIC_FIELD_PERIOD = (byte)30;
	private final static byte TYPE_GET_MAGNETIC_FIELD_PERIOD = (byte)31;
	private final static byte TYPE_SET_ANGULAR_VELOCITY_PERIOD = (byte)32;
	private final static byte TYPE_GET_ANGULAR_VELOCITY_PERIOD = (byte)33;
	private final static byte TYPE_SET_ALL_DATA_PERIOD = (byte)34;
	private final static byte TYPE_GET_ALL_DATA_PERIOD = (byte)35;
	private final static byte TYPE_SET_ORIENTATION_PERIOD = (byte)36;
	private final static byte TYPE_GET_ORIENTATION_PERIOD = (byte)37;
	private final static byte TYPE_SET_QUATERNION_PERIOD = (byte)38;
	private final static byte TYPE_GET_QUATERNION_PERIOD = (byte)39;
	private final static byte TYPE_ACCELERATION = (byte)40;
	private final static byte TYPE_MAGNETIC_FIELD = (byte)41;
	private final static byte TYPE_ANGULAR_VELOCITY = (byte)42;
	private final static byte TYPE_ALL_DATA = (byte)43;
	private final static byte TYPE_ORIENTATION = (byte)44;
	private final static byte TYPE_QUATERNION = (byte)45;
	private final static byte TYPE_ACCELERATION_REACHED = (byte)46;
	private final static byte TYPE_MAGNETIC_FIELD_REACHED = (byte)47;
	private final static byte TYPE_ANGULAR_VELOCITY_REACHED = (byte)48;
	private final static byte TYPE_ALL_DATA_REACHED = (byte)49;
	private final static byte TYPE_ORIENTATION_REACHED = (byte)50;

	public class Acceleration {
		public short x;
		public short y;
		public short z;

		public String toString() {
			 return "[" + "x = " + x + ", " + "y = " + y + ", " + "z = " + z + "]";
		}
	}

	public class MagneticField {
		public short x;
		public short y;
		public short z;

		public String toString() {
			 return "[" + "x = " + x + ", " + "y = " + y + ", " + "z = " + z + "]";
		}
	}

	public class AngularVelocity {
		public short x;
		public short y;
		public short z;

		public String toString() {
			 return "[" + "x = " + x + ", " + "y = " + y + ", " + "z = " + z + "]";
		}
	}

	public class AllData {
		public short accX;
		public short accY;
		public short accZ;
		public short magX;
		public short magY;
		public short magZ;
		public short angX;
		public short angY;
		public short angZ;
		public short temperature;

		public String toString() {
			 return "[" + "accX = " + accX + ", " + "accY = " + accY + ", " + "accZ = " + accZ + ", " + "magX = " + magX + ", " + "magY = " + magY + ", " + "magZ = " + magZ + ", " + "angX = " + angX + ", " + "angY = " + angY + ", " + "angZ = " + angZ + ", " + "temperature = " + temperature + "]";
		}
	}

	public class Orientation {
		public short roll;
		public short pitch;
		public short yaw;

		public String toString() {
			 return "[" + "roll = " + roll + ", " + "pitch = " + pitch + ", " + "yaw = " + yaw + "]";
		}
	}

	public class Quaternion {
		public float w;
		public float x;
		public float y;
		public float z;

		public String toString() {
			 return "[" + "w = " + w + ", " + "x = " + x + ", " + "y = " + y + ", " + "z = " + z + "]";
		}
	}

	public class AccelerationThreshold {
		public short num;
		public short[] threshold = new short[3];
		public char[] option = new char[3];

		public String toString() {
			 return "[" + "num = " + num + ", " + "threshold = " + threshold + ", " + "option = " + option + "]";
		}
	}

	public class MagneticFieldThreshold {
		public short num;
		public short[] threshold = new short[3];
		public char[] option = new char[3];

		public String toString() {
			 return "[" + "num = " + num + ", " + "threshold = " + threshold + ", " + "option = " + option + "]";
		}
	}

	public class AngularVelocityThreshold {
		public short num;
		public short[] threshold = new short[3];
		public char[] option = new char[3];

		public String toString() {
			 return "[" + "num = " + num + ", " + "threshold = " + threshold + ", " + "option = " + option + "]";
		}
	}

	public class AllDataThreshold {
		public short num;
		public short[] threshold = new short[9];
		public char[] option = new char[9];

		public String toString() {
			 return "[" + "num = " + num + ", " + "threshold = " + threshold + ", " + "option = " + option + "]";
		}
	}

	public class OrientationThreshold {
		public short num;
		public short[] threshold = new short[3];
		public char[] option = new char[3];

		public String toString() {
			 return "[" + "num = " + num + ", " + "threshold = " + threshold + ", " + "option = " + option + "]";
		}
	}

	public interface AccelerationListener {
		public void acceleration(short x, short y, short z);
	}

	public interface MagneticFieldListener {
		public void magneticField(short x, short y, short z);
	}

	public interface AngularVelocityListener {
		public void angularVelocity(short x, short y, short z);
	}

	public interface AllDataListener {
		public void allData(short accX, short accY, short accZ, short magX, short magY, short magZ, short angX, short angY, short angZ, short temperature);
	}

	public interface OrientationListener {
		public void orientation(short roll, short pitch, short yaw);
	}

	public interface QuaternionListener {
		public void quaternion(float w, float x, float y, float z);
	}

	public interface AccelerationReachedListener {
		public void accelerationReached(short x, short y, short z);
	}

	public interface MagneticFieldReachedListener {
		public void magneticFieldReached(short x, short y, short z);
	}

	public interface AngularVelocityReachedListener {
		public void angularVelocityReached(short x, short y, short z);
	}

	public interface AllDataReachedListener {
		public void allDataReached(short accX, short accY, short accZ, short magX, short magY, short magZ, short angX, short angY, short angZ, short temperature);
	}

	public interface OrientationReachedListener {
		public void orientationReached(short roll, short pitch, short yaw);
	}

	public BrickIMU(String uid) {
		super(uid);

		callbacks[TYPE_ACCELERATION] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());
				short z = (bb.getShort());

				((AccelerationListener)listenerObjects[TYPE_ACCELERATION]).acceleration(x, y, z);
			}
		};

		callbacks[TYPE_MAGNETIC_FIELD] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());
				short z = (bb.getShort());

				((MagneticFieldListener)listenerObjects[TYPE_MAGNETIC_FIELD]).magneticField(x, y, z);
			}
		};

		callbacks[TYPE_ANGULAR_VELOCITY] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());
				short z = (bb.getShort());

				((AngularVelocityListener)listenerObjects[TYPE_ANGULAR_VELOCITY]).angularVelocity(x, y, z);
			}
		};

		callbacks[TYPE_ALL_DATA] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short accX = (bb.getShort());
				short accY = (bb.getShort());
				short accZ = (bb.getShort());
				short magX = (bb.getShort());
				short magY = (bb.getShort());
				short magZ = (bb.getShort());
				short angX = (bb.getShort());
				short angY = (bb.getShort());
				short angZ = (bb.getShort());
				short temperature = (bb.getShort());

				((AllDataListener)listenerObjects[TYPE_ALL_DATA]).allData(accX, accY, accZ, magX, magY, magZ, angX, angY, angZ, temperature);
			}
		};

		callbacks[TYPE_ORIENTATION] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short roll = (bb.getShort());
				short pitch = (bb.getShort());
				short yaw = (bb.getShort());

				((OrientationListener)listenerObjects[TYPE_ORIENTATION]).orientation(roll, pitch, yaw);
			}
		};

		callbacks[TYPE_QUATERNION] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				float w = (bb.getFloat());
				float x = (bb.getFloat());
				float y = (bb.getFloat());
				float z = (bb.getFloat());

				((QuaternionListener)listenerObjects[TYPE_QUATERNION]).quaternion(w, x, y, z);
			}
		};

		callbacks[TYPE_ACCELERATION_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());
				short z = (bb.getShort());

				((AccelerationReachedListener)listenerObjects[TYPE_ACCELERATION_REACHED]).accelerationReached(x, y, z);
			}
		};

		callbacks[TYPE_MAGNETIC_FIELD_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());
				short z = (bb.getShort());

				((MagneticFieldReachedListener)listenerObjects[TYPE_MAGNETIC_FIELD_REACHED]).magneticFieldReached(x, y, z);
			}
		};

		callbacks[TYPE_ANGULAR_VELOCITY_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());
				short z = (bb.getShort());

				((AngularVelocityReachedListener)listenerObjects[TYPE_ANGULAR_VELOCITY_REACHED]).angularVelocityReached(x, y, z);
			}
		};

		callbacks[TYPE_ALL_DATA_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short accX = (bb.getShort());
				short accY = (bb.getShort());
				short accZ = (bb.getShort());
				short magX = (bb.getShort());
				short magY = (bb.getShort());
				short magZ = (bb.getShort());
				short angX = (bb.getShort());
				short angY = (bb.getShort());
				short angZ = (bb.getShort());
				short temperature = (bb.getShort());

				((AllDataReachedListener)listenerObjects[TYPE_ALL_DATA_REACHED]).allDataReached(accX, accY, accZ, magX, magY, magZ, angX, angY, angZ, temperature);
			}
		};

		callbacks[TYPE_ORIENTATION_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short roll = (bb.getShort());
				short pitch = (bb.getShort());
				short yaw = (bb.getShort());

				((OrientationReachedListener)listenerObjects[TYPE_ORIENTATION_REACHED]).orientationReached(roll, pitch, yaw);
			}
		};
	}

	public Acceleration getAcceleration() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ACCELERATION);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ACCELERATION, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAcceleration in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Acceleration obj = new Acceleration();
		obj.x = (bb.getShort());
		obj.y = (bb.getShort());
		obj.z = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public MagneticField getMagneticField() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_MAGNETIC_FIELD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_MAGNETIC_FIELD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getMagneticField in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		MagneticField obj = new MagneticField();
		obj.x = (bb.getShort());
		obj.y = (bb.getShort());
		obj.z = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public AngularVelocity getAngularVelocity() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ANGULAR_VELOCITY);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ANGULAR_VELOCITY, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAngularVelocity in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AngularVelocity obj = new AngularVelocity();
		obj.x = (bb.getShort());
		obj.y = (bb.getShort());
		obj.z = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public AllData getAllData() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ALL_DATA);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ALL_DATA, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAllData in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AllData obj = new AllData();
		obj.accX = (bb.getShort());
		obj.accY = (bb.getShort());
		obj.accZ = (bb.getShort());
		obj.magX = (bb.getShort());
		obj.magY = (bb.getShort());
		obj.magZ = (bb.getShort());
		obj.angX = (bb.getShort());
		obj.angY = (bb.getShort());
		obj.angZ = (bb.getShort());
		obj.temperature = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public Orientation getOrientation() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ORIENTATION);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ORIENTATION, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getOrientation in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Orientation obj = new Orientation();
		obj.roll = (bb.getShort());
		obj.pitch = (bb.getShort());
		obj.yaw = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public Quaternion getQuaternion() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_QUATERNION);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_QUATERNION, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getQuaternion in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Quaternion obj = new Quaternion();
		obj.w = (bb.getFloat());
		obj.x = (bb.getFloat());
		obj.y = (bb.getFloat());
		obj.z = (bb.getFloat());

		semaphoreWrite.release();
		return obj;
	}

	public short getIMUTemperature() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_IMU_TEMPERATURE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_IMU_TEMPERATURE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getIMUTemperature in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short temperature = (bb.getShort());

		semaphoreWrite.release();
		return temperature;
	}

	public void ledsOn()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_LEDS_ON);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_LEDS_ON, false);
	}

	public void ledsOff()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_LEDS_OFF);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_LEDS_OFF, false);
	}

	public boolean areLedsOn() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_ARE_LEDS_ON);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_ARE_LEDS_ON, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for areLedsOn in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean leds = (bb.get()) != 0;

		semaphoreWrite.release();
		return leds;
	}

	public void setAccelerationRange(short range)  {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ACCELERATION_RANGE);
		bb.putShort((short)5);
		bb.put((byte)range);

		ipcon.write(this, bb, TYPE_SET_ACCELERATION_RANGE, false);
	}

	public short getAccelerationRange() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ACCELERATION_RANGE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ACCELERATION_RANGE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAccelerationRange in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short range = IPConnection.unsignedByte(bb.get());

		semaphoreWrite.release();
		return range;
	}

	public void setMagnetometerRange(short range)  {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_MAGNETOMETER_RANGE);
		bb.putShort((short)5);
		bb.put((byte)range);

		ipcon.write(this, bb, TYPE_SET_MAGNETOMETER_RANGE, false);
	}

	public short getMagnetometerRange() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_MAGNETOMETER_RANGE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_MAGNETOMETER_RANGE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getMagnetometerRange in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short range = IPConnection.unsignedByte(bb.get());

		semaphoreWrite.release();
		return range;
	}

	public void setZero()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ZERO);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_SET_ZERO, false);
	}

	public void setDebouncePeriod(long debouncePeriod)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_DEBOUNCE_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)debouncePeriod);

		ipcon.write(this, bb, TYPE_SET_DEBOUNCE_PERIOD, false);
	}

	public long getDebouncePeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_DEBOUNCE_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_DEBOUNCE_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getDebouncePeriod in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long debouncePeriod = IPConnection.unsignedInt(bb.getInt());

		semaphoreWrite.release();
		return debouncePeriod;
	}

	public void setAccelerationThreshold(short num, short[] threshold, char[] option)  {
		ByteBuffer bb = ByteBuffer.allocate(14);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ACCELERATION_THRESHOLD);
		bb.putShort((short)14);
		bb.put((byte)num);
		for(int i = 0; i < 3; i++) {
			bb.putShort((short)threshold[i]);
		}

		for(int i = 0; i < 3; i++) {
			bb.put((byte)option[i]);
		}


		ipcon.write(this, bb, TYPE_SET_ACCELERATION_THRESHOLD, false);
	}

	public AccelerationThreshold getAccelerationThreshold(short num) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ACCELERATION_THRESHOLD);
		bb.putShort((short)5);
		bb.put((byte)num);

		ipcon.write(this, bb, TYPE_GET_ACCELERATION_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAccelerationThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AccelerationThreshold obj = new AccelerationThreshold();
		for(int i = 0; i < 3; i++) {
			obj.threshold[i] = (bb.getShort());
		}

		for(int i = 0; i < 3; i++) {
			obj.option[i] = (char)(bb.get());
		}


		semaphoreWrite.release();
		return obj;
	}

	public void setMagneticFieldThreshold(short num, short[] threshold, char[] option)  {
		ByteBuffer bb = ByteBuffer.allocate(14);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_MAGNETIC_FIELD_THRESHOLD);
		bb.putShort((short)14);
		bb.put((byte)num);
		for(int i = 0; i < 3; i++) {
			bb.putShort((short)threshold[i]);
		}

		for(int i = 0; i < 3; i++) {
			bb.put((byte)option[i]);
		}


		ipcon.write(this, bb, TYPE_SET_MAGNETIC_FIELD_THRESHOLD, false);
	}

	public MagneticFieldThreshold getMagneticFieldThreshold(short num) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_MAGNETIC_FIELD_THRESHOLD);
		bb.putShort((short)5);
		bb.put((byte)num);

		ipcon.write(this, bb, TYPE_GET_MAGNETIC_FIELD_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getMagneticFieldThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		MagneticFieldThreshold obj = new MagneticFieldThreshold();
		for(int i = 0; i < 3; i++) {
			obj.threshold[i] = (bb.getShort());
		}

		for(int i = 0; i < 3; i++) {
			obj.option[i] = (char)(bb.get());
		}


		semaphoreWrite.release();
		return obj;
	}

	public void setAngularVelocityThreshold(short num, short[] threshold, char[] option)  {
		ByteBuffer bb = ByteBuffer.allocate(14);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ANGULAR_VELOCITY_THRESHOLD);
		bb.putShort((short)14);
		bb.put((byte)num);
		for(int i = 0; i < 3; i++) {
			bb.putShort((short)threshold[i]);
		}

		for(int i = 0; i < 3; i++) {
			bb.put((byte)option[i]);
		}


		ipcon.write(this, bb, TYPE_SET_ANGULAR_VELOCITY_THRESHOLD, false);
	}

	public AngularVelocityThreshold getAngularVelocityThreshold(short num) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ANGULAR_VELOCITY_THRESHOLD);
		bb.putShort((short)5);
		bb.put((byte)num);

		ipcon.write(this, bb, TYPE_GET_ANGULAR_VELOCITY_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAngularVelocityThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AngularVelocityThreshold obj = new AngularVelocityThreshold();
		for(int i = 0; i < 3; i++) {
			obj.threshold[i] = (bb.getShort());
		}

		for(int i = 0; i < 3; i++) {
			obj.option[i] = (char)(bb.get());
		}


		semaphoreWrite.release();
		return obj;
	}

	public void setAllDataThreshold(short num, short[] threshold, char[] option)  {
		ByteBuffer bb = ByteBuffer.allocate(32);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ALL_DATA_THRESHOLD);
		bb.putShort((short)32);
		bb.put((byte)num);
		for(int i = 0; i < 9; i++) {
			bb.putShort((short)threshold[i]);
		}

		for(int i = 0; i < 9; i++) {
			bb.put((byte)option[i]);
		}


		ipcon.write(this, bb, TYPE_SET_ALL_DATA_THRESHOLD, false);
	}

	public AllDataThreshold getAllDataThreshold(short num) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ALL_DATA_THRESHOLD);
		bb.putShort((short)5);
		bb.put((byte)num);

		ipcon.write(this, bb, TYPE_GET_ALL_DATA_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAllDataThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AllDataThreshold obj = new AllDataThreshold();
		for(int i = 0; i < 9; i++) {
			obj.threshold[i] = (bb.getShort());
		}

		for(int i = 0; i < 9; i++) {
			obj.option[i] = (char)(bb.get());
		}


		semaphoreWrite.release();
		return obj;
	}

	public void setOrientationThreshold(short num, short[] threshold, char[] option)  {
		ByteBuffer bb = ByteBuffer.allocate(14);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ORIENTATION_THRESHOLD);
		bb.putShort((short)14);
		bb.put((byte)num);
		for(int i = 0; i < 3; i++) {
			bb.putShort((short)threshold[i]);
		}

		for(int i = 0; i < 3; i++) {
			bb.put((byte)option[i]);
		}


		ipcon.write(this, bb, TYPE_SET_ORIENTATION_THRESHOLD, false);
	}

	public OrientationThreshold getOrientationThreshold(short num) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ORIENTATION_THRESHOLD);
		bb.putShort((short)5);
		bb.put((byte)num);

		ipcon.write(this, bb, TYPE_GET_ORIENTATION_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getOrientationThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		OrientationThreshold obj = new OrientationThreshold();
		for(int i = 0; i < 3; i++) {
			obj.threshold[i] = (bb.getShort());
		}

		for(int i = 0; i < 3; i++) {
			obj.option[i] = (char)(bb.get());
		}


		semaphoreWrite.release();
		return obj;
	}

	public void setAccelerationPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ACCELERATION_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_ACCELERATION_PERIOD, false);
	}

	public long getAccelerationPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ACCELERATION_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ACCELERATION_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAccelerationPeriod in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		semaphoreWrite.release();
		return period;
	}

	public void setMagneticFieldPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_MAGNETIC_FIELD_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_MAGNETIC_FIELD_PERIOD, false);
	}

	public long getMagneticFieldPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_MAGNETIC_FIELD_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_MAGNETIC_FIELD_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getMagneticFieldPeriod in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		semaphoreWrite.release();
		return period;
	}

	public void setAngularVelocityPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ANGULAR_VELOCITY_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_ANGULAR_VELOCITY_PERIOD, false);
	}

	public long getAngularVelocityPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ANGULAR_VELOCITY_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ANGULAR_VELOCITY_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAngularVelocityPeriod in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		semaphoreWrite.release();
		return period;
	}

	public void setAllDataPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ALL_DATA_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_ALL_DATA_PERIOD, false);
	}

	public long getAllDataPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ALL_DATA_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ALL_DATA_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAllDataPeriod in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		semaphoreWrite.release();
		return period;
	}

	public void setOrientationPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ORIENTATION_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_ORIENTATION_PERIOD, false);
	}

	public long getOrientationPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ORIENTATION_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ORIENTATION_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getOrientationPeriod in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		semaphoreWrite.release();
		return period;
	}

	public void setQuaternionPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_QUATERNION_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_QUATERNION_PERIOD, false);
	}

	public long getQuaternionPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_QUATERNION_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_QUATERNION_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getQuaternionPeriod in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		semaphoreWrite.release();
		return period;
	}

	public void addListener(Object o) {
		if(o instanceof AccelerationListener) {
			listenerObjects[TYPE_ACCELERATION] = o;
		} else if(o instanceof MagneticFieldListener) {
			listenerObjects[TYPE_MAGNETIC_FIELD] = o;
		} else if(o instanceof AngularVelocityListener) {
			listenerObjects[TYPE_ANGULAR_VELOCITY] = o;
		} else if(o instanceof AllDataListener) {
			listenerObjects[TYPE_ALL_DATA] = o;
		} else if(o instanceof OrientationListener) {
			listenerObjects[TYPE_ORIENTATION] = o;
		} else if(o instanceof QuaternionListener) {
			listenerObjects[TYPE_QUATERNION] = o;
		} else if(o instanceof AccelerationReachedListener) {
			listenerObjects[TYPE_ACCELERATION_REACHED] = o;
		} else if(o instanceof MagneticFieldReachedListener) {
			listenerObjects[TYPE_MAGNETIC_FIELD_REACHED] = o;
		} else if(o instanceof AngularVelocityReachedListener) {
			listenerObjects[TYPE_ANGULAR_VELOCITY_REACHED] = o;
		} else if(o instanceof AllDataReachedListener) {
			listenerObjects[TYPE_ALL_DATA_REACHED] = o;
		} else if(o instanceof OrientationReachedListener) {
			listenerObjects[TYPE_ORIENTATION_REACHED] = o;
		}
	}
}