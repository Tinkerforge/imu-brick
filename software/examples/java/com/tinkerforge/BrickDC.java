/*************************************************************
 * This file was automatically generated on 2011-09-20.      *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the generator. You can find a link  *
 * to the generator git on tinkerforge.com                   *
 *************************************************************/

package com.tinkerforge;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;


public class BrickDC extends Device {
	private final static byte TYPE_SET_VELOCITY = (byte)1;
	private final static byte TYPE_GET_VELOCITY = (byte)2;
	private final static byte TYPE_GET_CURRENT_VELOCITY = (byte)3;
	private final static byte TYPE_SET_ACCELERATION = (byte)4;
	private final static byte TYPE_GET_ACCELERATION = (byte)5;
	private final static byte TYPE_SET_PWM_FREQUENCY = (byte)6;
	private final static byte TYPE_GET_PWM_FREQUENCY = (byte)7;
	private final static byte TYPE_FULL_BRAKE = (byte)8;
	private final static byte TYPE_GET_STACK_INPUT_VOLTAGE = (byte)9;
	private final static byte TYPE_GET_EXTERNAL_INPUT_VOLTAGE = (byte)10;
	private final static byte TYPE_GET_CURRENT_CONSUMPTION = (byte)11;
	private final static byte TYPE_ENABLE = (byte)12;
	private final static byte TYPE_DISABLE = (byte)13;
	private final static byte TYPE_IS_ENABLED = (byte)14;
	private final static byte TYPE_SET_MINIMUM_VOLTAGE = (byte)15;
	private final static byte TYPE_GET_MINIMUM_VOLTAGE = (byte)16;
	private final static byte TYPE_SET_DRIVE_MODE = (byte)17;
	private final static byte TYPE_GET_DRIVE_MODE = (byte)18;
	private final static byte TYPE_SET_CURRENT_VELOCITY_PERIOD = (byte)19;
	private final static byte TYPE_GET_CURRENT_VELOCITY_PERIOD = (byte)20;
	private final static byte TYPE_UNDER_VOLTAGE = (byte)21;
	private final static byte TYPE_EMERGENCY_SHUTDOWN = (byte)22;
	private final static byte TYPE_VELOCITY_REACHED = (byte)23;
	private final static byte TYPE_CURRENT_VELOCITY = (byte)24;

	public interface UnderVoltageListener {
		public void underVoltage(int voltage);
	}

	public interface EmergencyShutdownListener {
		public void emergencyShutdown();
	}

	public interface VelocityReachedListener {
		public void velocityReached(short velocity);
	}

	public interface CurrentVelocityListener {
		public void currentVelocity(short velocity);
	}

	public BrickDC(String uid) {
		super(uid);

		callbacks[TYPE_UNDER_VOLTAGE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int voltage = IPConnection.unsignedShort(bb.getShort());

				((UnderVoltageListener)listenerObjects[TYPE_UNDER_VOLTAGE]).underVoltage(voltage);
			}
		};

		callbacks[TYPE_EMERGENCY_SHUTDOWN] = new CallbackListener() {
			public void callback(byte[] data) {
				((EmergencyShutdownListener)listenerObjects[TYPE_EMERGENCY_SHUTDOWN]).emergencyShutdown();
			}
		};

		callbacks[TYPE_VELOCITY_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short velocity = (bb.getShort());

				((VelocityReachedListener)listenerObjects[TYPE_VELOCITY_REACHED]).velocityReached(velocity);
			}
		};

		callbacks[TYPE_CURRENT_VELOCITY] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short velocity = (bb.getShort());

				((CurrentVelocityListener)listenerObjects[TYPE_CURRENT_VELOCITY]).currentVelocity(velocity);
			}
		};
	}

	public void setVelocity(short velocity)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_VELOCITY);
		bb.putShort((short)6);
		bb.putShort((short)velocity);

		ipcon.write(this, bb, TYPE_SET_VELOCITY, false);
	}

	public short getVelocity() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_VELOCITY);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_VELOCITY, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getVelocity in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short velocity = (bb.getShort());

		semaphoreWrite.release();
		return velocity;
	}

	public short getCurrentVelocity() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_CURRENT_VELOCITY);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_CURRENT_VELOCITY, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getCurrentVelocity in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short velocity = (bb.getShort());

		semaphoreWrite.release();
		return velocity;
	}

	public void setAcceleration(int acceleration)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ACCELERATION);
		bb.putShort((short)6);
		bb.putShort((short)acceleration);

		ipcon.write(this, bb, TYPE_SET_ACCELERATION, false);
	}

	public int getAcceleration() throws IPConnection.TimeoutException {
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

		int acceleration = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return acceleration;
	}

	public void setPWMFrequency(int frequency)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_PWM_FREQUENCY);
		bb.putShort((short)6);
		bb.putShort((short)frequency);

		ipcon.write(this, bb, TYPE_SET_PWM_FREQUENCY, false);
	}

	public int getPWMFrequency() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_PWM_FREQUENCY);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_PWM_FREQUENCY, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getPWMFrequency in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int frequency = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return frequency;
	}

	public void fullBrake()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_FULL_BRAKE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_FULL_BRAKE, false);
	}

	public int getStackInputVoltage() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_STACK_INPUT_VOLTAGE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_STACK_INPUT_VOLTAGE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getStackInputVoltage in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return voltage;
	}

	public int getExternalInputVoltage() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_EXTERNAL_INPUT_VOLTAGE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_EXTERNAL_INPUT_VOLTAGE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getExternalInputVoltage in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return voltage;
	}

	public int getCurrentConsumption() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_CURRENT_CONSUMPTION);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_CURRENT_CONSUMPTION, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getCurrentConsumption in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return voltage;
	}

	public void enable()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_ENABLE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_ENABLE, false);
	}

	public void disable()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_DISABLE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_DISABLE, false);
	}

	public boolean isEnabled() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_IS_ENABLED);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_IS_ENABLED, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for isEnabled in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean enabled = (bb.get()) != 0;

		semaphoreWrite.release();
		return enabled;
	}

	public void setMinimumVoltage(int voltage)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_MINIMUM_VOLTAGE);
		bb.putShort((short)6);
		bb.putShort((short)voltage);

		ipcon.write(this, bb, TYPE_SET_MINIMUM_VOLTAGE, false);
	}

	public int getMinimumVoltage() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_MINIMUM_VOLTAGE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_MINIMUM_VOLTAGE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getMinimumVoltage in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return voltage;
	}

	public void setDriveMode(short mode)  {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_DRIVE_MODE);
		bb.putShort((short)5);
		bb.put((byte)mode);

		ipcon.write(this, bb, TYPE_SET_DRIVE_MODE, false);
	}

	public short getDriveMode() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_DRIVE_MODE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_DRIVE_MODE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getDriveMode in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short mode = IPConnection.unsignedByte(bb.get());

		semaphoreWrite.release();
		return mode;
	}

	public void setCurrentVelocityPeriod(int period)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_CURRENT_VELOCITY_PERIOD);
		bb.putShort((short)6);
		bb.putShort((short)period);

		ipcon.write(this, bb, TYPE_SET_CURRENT_VELOCITY_PERIOD, false);
	}

	public int getCurrentVelocityPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_CURRENT_VELOCITY_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_CURRENT_VELOCITY_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getCurrentVelocityPeriod in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int period = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return period;
	}

	public void addListener(Object o) {
		if(o instanceof UnderVoltageListener) {
			listenerObjects[TYPE_UNDER_VOLTAGE] = o;
		} else if(o instanceof EmergencyShutdownListener) {
			listenerObjects[TYPE_EMERGENCY_SHUTDOWN] = o;
		} else if(o instanceof VelocityReachedListener) {
			listenerObjects[TYPE_VELOCITY_REACHED] = o;
		} else if(o instanceof CurrentVelocityListener) {
			listenerObjects[TYPE_CURRENT_VELOCITY] = o;
		}
	}
}