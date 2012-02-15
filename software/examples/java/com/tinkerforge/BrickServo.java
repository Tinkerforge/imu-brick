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


public class BrickServo extends Device {
	private final static byte TYPE_ENABLE = (byte)1;
	private final static byte TYPE_DISABLE = (byte)2;
	private final static byte TYPE_IS_ENABLED = (byte)3;
	private final static byte TYPE_SET_POSITION = (byte)4;
	private final static byte TYPE_GET_POSITION = (byte)5;
	private final static byte TYPE_GET_CURRENT_POSITION = (byte)6;
	private final static byte TYPE_SET_VELOCITY = (byte)7;
	private final static byte TYPE_GET_VELOCITY = (byte)8;
	private final static byte TYPE_GET_CURRENT_VELOCITY = (byte)9;
	private final static byte TYPE_SET_ACCELERATION = (byte)10;
	private final static byte TYPE_GET_ACCELERATION = (byte)11;
	private final static byte TYPE_SET_OUTPUT_VOLTAGE = (byte)12;
	private final static byte TYPE_GET_OUTPUT_VOLTAGE = (byte)13;
	private final static byte TYPE_SET_PULSE_WIDTH = (byte)14;
	private final static byte TYPE_GET_PULSE_WIDTH = (byte)15;
	private final static byte TYPE_SET_DEGREE = (byte)16;
	private final static byte TYPE_GET_DEGREE = (byte)17;
	private final static byte TYPE_SET_PERIOD = (byte)18;
	private final static byte TYPE_GET_PERIOD = (byte)19;
	private final static byte TYPE_GET_SERVO_CURRENT = (byte)20;
	private final static byte TYPE_GET_OVERALL_CURRENT = (byte)21;
	private final static byte TYPE_GET_STACK_INPUT_VOLTAGE = (byte)22;
	private final static byte TYPE_GET_EXTERNAL_INPUT_VOLTAGE = (byte)23;
	private final static byte TYPE_SET_MINIMUM_VOLTAGE = (byte)24;
	private final static byte TYPE_GET_MINIMUM_VOLTAGE = (byte)25;
	private final static byte TYPE_UNDER_VOLTAGE = (byte)26;
	private final static byte TYPE_POSITION_REACHED = (byte)27;
	private final static byte TYPE_VELOCITY_REACHED = (byte)28;

	public class PulseWidth {
		public short servoNum;
		public int min;
		public int max;

		public String toString() {
			 return "[" + "servoNum = " + servoNum + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public class Degree {
		public short servoNum;
		public short min;
		public short max;

		public String toString() {
			 return "[" + "servoNum = " + servoNum + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public interface UnderVoltageListener {
		public void underVoltage(int voltage);
	}

	public interface PositionReachedListener {
		public void positionReached(short servoNum, short position);
	}

	public interface VelocityReachedListener {
		public void velocityReached(short servoNum, short velocity);
	}

	public BrickServo(String uid) {
		super(uid);

		callbacks[TYPE_UNDER_VOLTAGE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int voltage = IPConnection.unsignedShort(bb.getShort());

				((UnderVoltageListener)listenerObjects[TYPE_UNDER_VOLTAGE]).underVoltage(voltage);
			}
		};

		callbacks[TYPE_POSITION_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short servoNum = IPConnection.unsignedByte(bb.get());
				short position = (bb.getShort());

				((PositionReachedListener)listenerObjects[TYPE_POSITION_REACHED]).positionReached(servoNum, position);
			}
		};

		callbacks[TYPE_VELOCITY_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short servoNum = IPConnection.unsignedByte(bb.get());
				short velocity = (bb.getShort());

				((VelocityReachedListener)listenerObjects[TYPE_VELOCITY_REACHED]).velocityReached(servoNum, velocity);
			}
		};
	}

	public void enable(short servoNum)  {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_ENABLE);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

		ipcon.write(this, bb, TYPE_ENABLE, false);
	}

	public void disable(short servoNum)  {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_DISABLE);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

		ipcon.write(this, bb, TYPE_DISABLE, false);
	}

	public boolean isEnabled(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_IS_ENABLED);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

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

	public void setPosition(short servoNum, short position)  {
		ByteBuffer bb = ByteBuffer.allocate(7);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_POSITION);
		bb.putShort((short)7);
		bb.put((byte)servoNum);
		bb.putShort((short)position);

		ipcon.write(this, bb, TYPE_SET_POSITION, false);
	}

	public short getPosition(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_POSITION);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

		ipcon.write(this, bb, TYPE_GET_POSITION, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getPosition in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short position = (bb.getShort());

		semaphoreWrite.release();
		return position;
	}

	public short getCurrentPosition(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_CURRENT_POSITION);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

		ipcon.write(this, bb, TYPE_GET_CURRENT_POSITION, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getCurrentPosition in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short position = (bb.getShort());

		semaphoreWrite.release();
		return position;
	}

	public void setVelocity(short servoNum, int velocity)  {
		ByteBuffer bb = ByteBuffer.allocate(7);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_VELOCITY);
		bb.putShort((short)7);
		bb.put((byte)servoNum);
		bb.putShort((short)velocity);

		ipcon.write(this, bb, TYPE_SET_VELOCITY, false);
	}

	public int getVelocity(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_VELOCITY);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

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

		int velocity = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return velocity;
	}

	public int getCurrentVelocity(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_CURRENT_VELOCITY);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

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

		int velocity = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return velocity;
	}

	public void setAcceleration(short servoNum, int acceleration)  {
		ByteBuffer bb = ByteBuffer.allocate(7);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ACCELERATION);
		bb.putShort((short)7);
		bb.put((byte)servoNum);
		bb.putShort((short)acceleration);

		ipcon.write(this, bb, TYPE_SET_ACCELERATION, false);
	}

	public int getAcceleration(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ACCELERATION);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

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

	public void setOutputVoltage(int voltage)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_OUTPUT_VOLTAGE);
		bb.putShort((short)6);
		bb.putShort((short)voltage);

		ipcon.write(this, bb, TYPE_SET_OUTPUT_VOLTAGE, false);
	}

	public int getOutputVoltage() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_OUTPUT_VOLTAGE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_OUTPUT_VOLTAGE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getOutputVoltage in time");
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

	public void setPulseWidth(short servoNum, int min, int max)  {
		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_PULSE_WIDTH);
		bb.putShort((short)9);
		bb.put((byte)servoNum);
		bb.putShort((short)min);
		bb.putShort((short)max);

		ipcon.write(this, bb, TYPE_SET_PULSE_WIDTH, false);
	}

	public PulseWidth getPulseWidth(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_PULSE_WIDTH);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

		ipcon.write(this, bb, TYPE_GET_PULSE_WIDTH, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getPulseWidth in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		PulseWidth obj = new PulseWidth();
		obj.min = IPConnection.unsignedShort(bb.getShort());
		obj.max = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public void setDegree(short servoNum, short min, short max)  {
		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_DEGREE);
		bb.putShort((short)9);
		bb.put((byte)servoNum);
		bb.putShort((short)min);
		bb.putShort((short)max);

		ipcon.write(this, bb, TYPE_SET_DEGREE, false);
	}

	public Degree getDegree(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_DEGREE);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

		ipcon.write(this, bb, TYPE_GET_DEGREE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getDegree in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Degree obj = new Degree();
		obj.min = (bb.getShort());
		obj.max = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public void setPeriod(short servoNum, int period)  {
		ByteBuffer bb = ByteBuffer.allocate(7);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_PERIOD);
		bb.putShort((short)7);
		bb.put((byte)servoNum);
		bb.putShort((short)period);

		ipcon.write(this, bb, TYPE_SET_PERIOD, false);
	}

	public int getPeriod(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_PERIOD);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

		ipcon.write(this, bb, TYPE_GET_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getPeriod in time");
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

	public int getServoCurrent(short servoNum) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_SERVO_CURRENT);
		bb.putShort((short)5);
		bb.put((byte)servoNum);

		ipcon.write(this, bb, TYPE_GET_SERVO_CURRENT, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getServoCurrent in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int current = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return current;
	}

	public int getOverallCurrent() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_OVERALL_CURRENT);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_OVERALL_CURRENT, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getOverallCurrent in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int current = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return current;
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

	public void addListener(Object o) {
		if(o instanceof UnderVoltageListener) {
			listenerObjects[TYPE_UNDER_VOLTAGE] = o;
		} else if(o instanceof PositionReachedListener) {
			listenerObjects[TYPE_POSITION_REACHED] = o;
		} else if(o instanceof VelocityReachedListener) {
			listenerObjects[TYPE_VELOCITY_REACHED] = o;
		}
	}
}