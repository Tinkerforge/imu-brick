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


public class BrickletDistanceIR extends Device {
	private final static byte TYPE_GET_DISTANCE = (byte)1;
	private final static byte TYPE_GET_ANALOG_VALUE = (byte)2;
	private final static byte TYPE_SET_SAMPLING_POINT = (byte)3;
	private final static byte TYPE_GET_SAMPLING_POINT = (byte)4;
	private final static byte TYPE_SET_DISTANCE_CALLBACK_PERIOD = (byte)5;
	private final static byte TYPE_GET_DISTANCE_CALLBACK_PERIOD = (byte)6;
	private final static byte TYPE_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)7;
	private final static byte TYPE_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)8;
	private final static byte TYPE_SET_DISTANCE_CALLBACK_THRESHOLD = (byte)9;
	private final static byte TYPE_GET_DISTANCE_CALLBACK_THRESHOLD = (byte)10;
	private final static byte TYPE_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)11;
	private final static byte TYPE_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)12;
	private final static byte TYPE_SET_DEBOUNCE_PERIOD = (byte)13;
	private final static byte TYPE_GET_DEBOUNCE_PERIOD = (byte)14;
	private final static byte TYPE_DISTANCE = (byte)15;
	private final static byte TYPE_ANALOG_VALUE = (byte)16;
	private final static byte TYPE_DISTANCE_REACHED = (byte)17;
	private final static byte TYPE_ANALOG_VALUE_REACHED = (byte)18;

	public class DistanceCallbackThreshold {
		public char option;
		public short min;
		public short max;

		public String toString() {
			 return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public class AnalogValueCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			 return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public interface DistanceListener {
		public void distance(int distance);
	}

	public interface AnalogValueListener {
		public void analogValue(int value);
	}

	public interface DistanceReachedListener {
		public void distanceReached(int distance);
	}

	public interface AnalogValueReachedListener {
		public void analogValueReached(int value);
	}

	public BrickletDistanceIR(String uid) {
		super(uid);

		callbacks[TYPE_DISTANCE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int distance = IPConnection.unsignedShort(bb.getShort());

				((DistanceListener)listenerObjects[TYPE_DISTANCE]).distance(distance);
			}
		};

		callbacks[TYPE_ANALOG_VALUE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int value = IPConnection.unsignedShort(bb.getShort());

				((AnalogValueListener)listenerObjects[TYPE_ANALOG_VALUE]).analogValue(value);
			}
		};

		callbacks[TYPE_DISTANCE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int distance = IPConnection.unsignedShort(bb.getShort());

				((DistanceReachedListener)listenerObjects[TYPE_DISTANCE_REACHED]).distanceReached(distance);
			}
		};

		callbacks[TYPE_ANALOG_VALUE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int value = IPConnection.unsignedShort(bb.getShort());

				((AnalogValueReachedListener)listenerObjects[TYPE_ANALOG_VALUE_REACHED]).analogValueReached(value);
			}
		};
	}

	public int getDistance() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_DISTANCE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_DISTANCE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getDistance in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int distance = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return distance;
	}

	public int getAnalogValue() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ANALOG_VALUE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ANALOG_VALUE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAnalogValue in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int value = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return value;
	}

	public void setSamplingPoint(short position, int distance)  {
		ByteBuffer bb = ByteBuffer.allocate(7);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_SAMPLING_POINT);
		bb.putShort((short)7);
		bb.put((byte)position);
		bb.putShort((short)distance);

		ipcon.write(this, bb, TYPE_SET_SAMPLING_POINT, false);
	}

	public int getSamplingPoint(short position) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_SAMPLING_POINT);
		bb.putShort((short)5);
		bb.put((byte)position);

		ipcon.write(this, bb, TYPE_GET_SAMPLING_POINT, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getSamplingPoint in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int distance = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return distance;
	}

	public void setDistanceCallbackPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_DISTANCE_CALLBACK_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_DISTANCE_CALLBACK_PERIOD, false);
	}

	public long getDistanceCallbackPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_DISTANCE_CALLBACK_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_DISTANCE_CALLBACK_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getDistanceCallbackPeriod in time");
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

	public void setAnalogValueCallbackPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ANALOG_VALUE_CALLBACK_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_ANALOG_VALUE_CALLBACK_PERIOD, false);
	}

	public long getAnalogValueCallbackPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ANALOG_VALUE_CALLBACK_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ANALOG_VALUE_CALLBACK_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAnalogValueCallbackPeriod in time");
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

	public void setDistanceCallbackThreshold(char option, short min, short max)  {
		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_DISTANCE_CALLBACK_THRESHOLD);
		bb.putShort((short)9);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		ipcon.write(this, bb, TYPE_SET_DISTANCE_CALLBACK_THRESHOLD, false);
	}

	public DistanceCallbackThreshold getDistanceCallbackThreshold() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_DISTANCE_CALLBACK_THRESHOLD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_DISTANCE_CALLBACK_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getDistanceCallbackThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		DistanceCallbackThreshold obj = new DistanceCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getShort());
		obj.max = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public void setAnalogValueCallbackThreshold(char option, int min, int max)  {
		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ANALOG_VALUE_CALLBACK_THRESHOLD);
		bb.putShort((short)9);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		ipcon.write(this, bb, TYPE_SET_ANALOG_VALUE_CALLBACK_THRESHOLD, false);
	}

	public AnalogValueCallbackThreshold getAnalogValueCallbackThreshold() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_ANALOG_VALUE_CALLBACK_THRESHOLD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_ANALOG_VALUE_CALLBACK_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAnalogValueCallbackThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AnalogValueCallbackThreshold obj = new AnalogValueCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = IPConnection.unsignedShort(bb.getShort());
		obj.max = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public void setDebouncePeriod(long debounce)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_DEBOUNCE_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)debounce);

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

		long debounce = IPConnection.unsignedInt(bb.getInt());

		semaphoreWrite.release();
		return debounce;
	}

	public void addListener(Object o) {
		if(o instanceof DistanceListener) {
			listenerObjects[TYPE_DISTANCE] = o;
		} else if(o instanceof AnalogValueListener) {
			listenerObjects[TYPE_ANALOG_VALUE] = o;
		} else if(o instanceof DistanceReachedListener) {
			listenerObjects[TYPE_DISTANCE_REACHED] = o;
		} else if(o instanceof AnalogValueReachedListener) {
			listenerObjects[TYPE_ANALOG_VALUE_REACHED] = o;
		}
	}
}