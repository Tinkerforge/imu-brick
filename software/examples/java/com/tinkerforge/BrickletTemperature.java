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


public class BrickletTemperature extends Device {
	private final static byte TYPE_GET_TEMPERATURE = (byte)1;
	private final static byte TYPE_SET_TEMPERATURE_CALLBACK_PERIOD = (byte)2;
	private final static byte TYPE_GET_TEMPERATURE_CALLBACK_PERIOD = (byte)3;
	private final static byte TYPE_SET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)4;
	private final static byte TYPE_GET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)5;
	private final static byte TYPE_SET_DEBOUNCE_PERIOD = (byte)6;
	private final static byte TYPE_GET_DEBOUNCE_PERIOD = (byte)7;
	private final static byte TYPE_TEMPERATURE = (byte)8;
	private final static byte TYPE_TEMPERATURE_REACHED = (byte)9;

	public class TemperatureCallbackThreshold {
		public char option;
		public short min;
		public short max;

		public String toString() {
			 return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public interface TemperatureListener {
		public void temperature(short temperature);
	}

	public interface TemperatureReachedListener {
		public void temperatureReached(short temperature);
	}

	public BrickletTemperature(String uid) {
		super(uid);

		callbacks[TYPE_TEMPERATURE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short temperature = (bb.getShort());

				((TemperatureListener)listenerObjects[TYPE_TEMPERATURE]).temperature(temperature);
			}
		};

		callbacks[TYPE_TEMPERATURE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short temperature = (bb.getShort());

				((TemperatureReachedListener)listenerObjects[TYPE_TEMPERATURE_REACHED]).temperatureReached(temperature);
			}
		};
	}

	public short getTemperature() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_TEMPERATURE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_TEMPERATURE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getTemperature in time");
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

	public void setTemperatureCallbackPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_TEMPERATURE_CALLBACK_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_TEMPERATURE_CALLBACK_PERIOD, false);
	}

	public long getTemperatureCallbackPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_TEMPERATURE_CALLBACK_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_TEMPERATURE_CALLBACK_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getTemperatureCallbackPeriod in time");
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

	public void setTemperatureCallbackThreshold(char option, short min, short max)  {
		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_TEMPERATURE_CALLBACK_THRESHOLD);
		bb.putShort((short)9);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		ipcon.write(this, bb, TYPE_SET_TEMPERATURE_CALLBACK_THRESHOLD, false);
	}

	public TemperatureCallbackThreshold getTemperatureCallbackThreshold() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_TEMPERATURE_CALLBACK_THRESHOLD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_TEMPERATURE_CALLBACK_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getTemperatureCallbackThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		TemperatureCallbackThreshold obj = new TemperatureCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getShort());
		obj.max = (bb.getShort());

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
		if(o instanceof TemperatureListener) {
			listenerObjects[TYPE_TEMPERATURE] = o;
		} else if(o instanceof TemperatureReachedListener) {
			listenerObjects[TYPE_TEMPERATURE_REACHED] = o;
		}
	}
}