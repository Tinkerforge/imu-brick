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


public class BrickletTemperatureIR extends Device {
	private final static byte TYPE_GET_AMBIENT_TEMPERATURE = (byte)1;
	private final static byte TYPE_GET_OBJECT_TEMPERATURE = (byte)2;
	private final static byte TYPE_SET_EMISSIVITY = (byte)3;
	private final static byte TYPE_GET_EMISSIVITY = (byte)4;
	private final static byte TYPE_SET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD = (byte)5;
	private final static byte TYPE_GET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD = (byte)6;
	private final static byte TYPE_SET_OBJECT_TEMPERATURE_CALLBACK_PERIOD = (byte)7;
	private final static byte TYPE_GET_OBJECT_TEMPERATURE_CALLBACK_PERIOD = (byte)8;
	private final static byte TYPE_SET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD = (byte)9;
	private final static byte TYPE_GET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD = (byte)10;
	private final static byte TYPE_SET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD = (byte)11;
	private final static byte TYPE_GET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD = (byte)12;
	private final static byte TYPE_SET_DEBOUNCE_PERIOD = (byte)13;
	private final static byte TYPE_GET_DEBOUNCE_PERIOD = (byte)14;
	private final static byte TYPE_AMBIENT_TEMPERATURE = (byte)15;
	private final static byte TYPE_OBJECT_TEMPERATURE = (byte)16;
	private final static byte TYPE_AMBIENT_TEMPERATURE_REACHED = (byte)17;
	private final static byte TYPE_OBJECT_TEMPERATURE_REACHED = (byte)18;

	public class AmbientTemperatureCallbackThreshold {
		public char option;
		public short min;
		public short max;

		public String toString() {
			 return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public class ObjectTemperatureCallbackThreshold {
		public char option;
		public short min;
		public short max;

		public String toString() {
			 return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public interface AmbientTemperatureListener {
		public void ambientTemperature(short temperature);
	}

	public interface ObjectTemperatureListener {
		public void objectTemperature(short temperature);
	}

	public interface AmbientTemperatureReachedListener {
		public void ambientTemperatureReached(short temperature);
	}

	public interface ObjectTemperatureReachedListener {
		public void objectTemperatureReached(short temperature);
	}

	public BrickletTemperatureIR(String uid) {
		super(uid);

		callbacks[TYPE_AMBIENT_TEMPERATURE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short temperature = (bb.getShort());

				((AmbientTemperatureListener)listenerObjects[TYPE_AMBIENT_TEMPERATURE]).ambientTemperature(temperature);
			}
		};

		callbacks[TYPE_OBJECT_TEMPERATURE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short temperature = (bb.getShort());

				((ObjectTemperatureListener)listenerObjects[TYPE_OBJECT_TEMPERATURE]).objectTemperature(temperature);
			}
		};

		callbacks[TYPE_AMBIENT_TEMPERATURE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short temperature = (bb.getShort());

				((AmbientTemperatureReachedListener)listenerObjects[TYPE_AMBIENT_TEMPERATURE_REACHED]).ambientTemperatureReached(temperature);
			}
		};

		callbacks[TYPE_OBJECT_TEMPERATURE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short temperature = (bb.getShort());

				((ObjectTemperatureReachedListener)listenerObjects[TYPE_OBJECT_TEMPERATURE_REACHED]).objectTemperatureReached(temperature);
			}
		};
	}

	public short getAmbientTemperature() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_AMBIENT_TEMPERATURE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_AMBIENT_TEMPERATURE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAmbientTemperature in time");
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

	public short getObjectTemperature() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_OBJECT_TEMPERATURE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_OBJECT_TEMPERATURE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getObjectTemperature in time");
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

	public void setEmissivity(int emissivity)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_EMISSIVITY);
		bb.putShort((short)6);
		bb.putShort((short)emissivity);

		ipcon.write(this, bb, TYPE_SET_EMISSIVITY, false);
	}

	public int getEmissivity() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_EMISSIVITY);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_EMISSIVITY, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getEmissivity in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int emissivity = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return emissivity;
	}

	public void setAmbientTemperatureCallbackPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD, false);
	}

	public long getAmbientTemperatureCallbackPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAmbientTemperatureCallbackPeriod in time");
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

	public void setObjectTemperatureCallbackPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_OBJECT_TEMPERATURE_CALLBACK_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_OBJECT_TEMPERATURE_CALLBACK_PERIOD, false);
	}

	public long getObjectTemperatureCallbackPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_OBJECT_TEMPERATURE_CALLBACK_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_OBJECT_TEMPERATURE_CALLBACK_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getObjectTemperatureCallbackPeriod in time");
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

	public void setAmbientTemperatureCallbackThreshold(char option, short min, short max)  {
		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD);
		bb.putShort((short)9);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		ipcon.write(this, bb, TYPE_SET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD, false);
	}

	public AmbientTemperatureCallbackThreshold getAmbientTemperatureCallbackThreshold() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getAmbientTemperatureCallbackThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AmbientTemperatureCallbackThreshold obj = new AmbientTemperatureCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getShort());
		obj.max = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public void setObjectTemperatureCallbackThreshold(char option, short min, short max)  {
		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD);
		bb.putShort((short)9);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		ipcon.write(this, bb, TYPE_SET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD, false);
	}

	public ObjectTemperatureCallbackThreshold getObjectTemperatureCallbackThreshold() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getObjectTemperatureCallbackThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		ObjectTemperatureCallbackThreshold obj = new ObjectTemperatureCallbackThreshold();
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
		if(o instanceof AmbientTemperatureListener) {
			listenerObjects[TYPE_AMBIENT_TEMPERATURE] = o;
		} else if(o instanceof ObjectTemperatureListener) {
			listenerObjects[TYPE_OBJECT_TEMPERATURE] = o;
		} else if(o instanceof AmbientTemperatureReachedListener) {
			listenerObjects[TYPE_AMBIENT_TEMPERATURE_REACHED] = o;
		} else if(o instanceof ObjectTemperatureReachedListener) {
			listenerObjects[TYPE_OBJECT_TEMPERATURE_REACHED] = o;
		}
	}
}