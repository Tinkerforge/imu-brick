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


public class BrickletIO16 extends Device {
	private final static byte TYPE_SET_PORT = (byte)1;
	private final static byte TYPE_GET_PORT = (byte)2;
	private final static byte TYPE_SET_PORT_CONFIGURATION = (byte)3;
	private final static byte TYPE_GET_PORT_CONFIGURATION = (byte)4;
	private final static byte TYPE_SET_DEBOUNCE_PERIOD = (byte)5;
	private final static byte TYPE_GET_DEBOUNCE_PERIOD = (byte)6;
	private final static byte TYPE_SET_PORT_INTERRUPT = (byte)7;
	private final static byte TYPE_GET_PORT_INTERRUPT = (byte)8;
	private final static byte TYPE_INTERRUPT = (byte)9;

	public class PortConfiguration {
		public char port;
		public short directionMask;
		public short valueMask;

		public String toString() {
			 return "[" + "port = " + port + ", " + "directionMask = " + directionMask + ", " + "valueMask = " + valueMask + "]";
		}
	}

	public interface InterruptListener {
		public void interrupt(char port, short interruptMask, short valueMask);
	}

	public BrickletIO16(String uid) {
		super(uid);

		callbacks[TYPE_INTERRUPT] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				char port = (char)(bb.get());
				short interruptMask = IPConnection.unsignedByte(bb.get());
				short valueMask = IPConnection.unsignedByte(bb.get());

				((InterruptListener)listenerObjects[TYPE_INTERRUPT]).interrupt(port, interruptMask, valueMask);
			}
		};
	}

	public void setPort(char port, short valueMask)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_PORT);
		bb.putShort((short)6);
		bb.put((byte)port);
		bb.put((byte)valueMask);

		ipcon.write(this, bb, TYPE_SET_PORT, false);
	}

	public short getPort(char port) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_PORT);
		bb.putShort((short)5);
		bb.put((byte)port);

		ipcon.write(this, bb, TYPE_GET_PORT, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getPort in time");
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

	public void setPortConfiguration(char port, short portMask, char direction, boolean value)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_PORT_CONFIGURATION);
		bb.putShort((short)8);
		bb.put((byte)port);
		bb.put((byte)portMask);
		bb.put((byte)direction);
		bb.put((byte)(value ? 1 : 0));

		ipcon.write(this, bb, TYPE_SET_PORT_CONFIGURATION, false);
	}

	public PortConfiguration getPortConfiguration(char port) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_PORT_CONFIGURATION);
		bb.putShort((short)5);
		bb.put((byte)port);

		ipcon.write(this, bb, TYPE_GET_PORT_CONFIGURATION, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getPortConfiguration in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		PortConfiguration obj = new PortConfiguration();
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

	public void setPortInterrupt(char port, short interruptMask)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_PORT_INTERRUPT);
		bb.putShort((short)6);
		bb.put((byte)port);
		bb.put((byte)interruptMask);

		ipcon.write(this, bb, TYPE_SET_PORT_INTERRUPT, false);
	}

	public short getPortInterrupt(char port) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_PORT_INTERRUPT);
		bb.putShort((short)5);
		bb.put((byte)port);

		ipcon.write(this, bb, TYPE_GET_PORT_INTERRUPT, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getPortInterrupt in time");
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