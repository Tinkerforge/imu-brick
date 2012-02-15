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


public class BrickletDualRelay extends Device {
	private final static byte TYPE_SET_STATE = (byte)1;
	private final static byte TYPE_GET_STATE = (byte)2;

	public class State {
		public boolean relay1;
		public boolean relay2;

		public String toString() {
			 return "[" + "relay1 = " + relay1 + ", " + "relay2 = " + relay2 + "]";
		}
	}

	public BrickletDualRelay(String uid) {
		super(uid);
	}

	public void setState(boolean relay1, boolean relay2)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_STATE);
		bb.putShort((short)6);
		bb.put((byte)(relay1 ? 1 : 0));
		bb.put((byte)(relay2 ? 1 : 0));

		ipcon.write(this, bb, TYPE_SET_STATE, false);
	}

	public State getState() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_STATE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_STATE, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getState in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		State obj = new State();
		obj.relay1 = (bb.get()) != 0;
		obj.relay2 = (bb.get()) != 0;

		semaphoreWrite.release();
		return obj;
	}

	public void addListener(Object o) {
		
	}
}