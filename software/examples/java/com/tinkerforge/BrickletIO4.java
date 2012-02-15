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


public class BrickletIO4 extends Device {
	private final static byte TYPE_SET_VALUE = (byte)1;
	private final static byte TYPE_GET_VALUE = (byte)2;
	private final static byte TYPE_SET_CONFIGURATION = (byte)3;
	private final static byte TYPE_GET_CONFIGURATION = (byte)4;
	private final static byte TYPE_SET_DEBOUNCE_PERIOD = (byte)5;
	private final static byte TYPE_GET_DEBOUNCE_PERIOD = (byte)6;
	private final static byte TYPE_SET_INTERRUPT = (byte)7;
	private final static byte TYPE_GET_INTERRUPT = (byte)8;
	private final static byte TYPE_INTERRUPT = (byte)9;

	public class Configuration {
		public short directionMask;
		public short valueMask;

		public String toString() {
			 return "[" + "directionMask = " + directionMask + ", " + "valueMask = " + valueMask + "]";
		}
	}

	public interface InterruptListener {
		public void interrupt(short interruptMask, short valueMask);
	}

	public BrickletIO4(String uid) {
		super(uid);

		callbacks[TYPE_INTERRUPT] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short interruptMask = IPConnection.unsignedByte(bb.get());
				short valueMask = IPConnection.unsignedByte(bb.get());

				((InterruptListener)listenerObjects[TYPE_INTERRUPT]).interrupt(interruptMask, valueMask);
			}
		};
	}

	public void setValue(short valueMask)  {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_VALUE);
		bb.putShort((short)5);
		bb.put((byte)valueMask);

		ipcon.write(this, bb, TYPE_SET_VALUE, false);
	}

	public short getValue() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_VALUE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_VALUE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getValue in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short valueMask = IPConnection.unsignedByte(bb.get());

		semaphoreWrite.release();
		return valueMask;
	}

	public void setConfiguration(short pinMask, char direction, boolean value)  {
		ByteBuffer bb = ByteBuffer.allocate(7);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_CONFIGURATION);
		bb.putShort((short)7);
		bb.put((byte)pinMask);
		bb.put((byte)direction);
		bb.put((byte)(value ? 1 : 0));

		ipcon.write(this, bb, TYPE_SET_CONFIGURATION, false);
	}

	public Configuration getConfiguration() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_CONFIGURATION);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_CONFIGURATION, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getConfiguration in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Configuration obj = new Configuration();
		obj.directionMask = IPConnection.unsignedByte(bb.get());
		obj.valueMask = IPConnection.unsignedByte(bb.get());

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

	public void setInterrupt(short interruptMask)  {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_INTERRUPT);
		bb.putShort((short)5);
		bb.put((byte)interruptMask);

		ipcon.write(this, bb, TYPE_SET_INTERRUPT, false);
	}

	public short getInterrupt() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_INTERRUPT);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_INTERRUPT, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getInterrupt in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short interruptMask = IPConnection.unsignedByte(bb.get());

		semaphoreWrite.release();
		return interruptMask;
	}

	public void addListener(Object o) {
		if(o instanceof InterruptListener) {
			listenerObjects[TYPE_INTERRUPT] = o;
		}
	}
}