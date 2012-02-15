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


public class BrickletPiezoBuzzer extends Device {
	private final static byte TYPE_BEEP = (byte)1;
	private final static byte TYPE_MORSE_CODE = (byte)2;
	private final static byte TYPE_BEEP_FINISHED = (byte)3;
	private final static byte TYPE_MORSE_CODE_FINISHED = (byte)4;

	public interface BeepFinishedListener {
		public void beepFinished();
	}

	public interface MorseCodeFinishedListener {
		public void morseCodeFinished();
	}

	public BrickletPiezoBuzzer(String uid) {
		super(uid);

		callbacks[TYPE_BEEP_FINISHED] = new CallbackListener() {
			public void callback(byte[] data) {
				((BeepFinishedListener)listenerObjects[TYPE_BEEP_FINISHED]).beepFinished();
			}
		};

		callbacks[TYPE_MORSE_CODE_FINISHED] = new CallbackListener() {
			public void callback(byte[] data) {
				((MorseCodeFinishedListener)listenerObjects[TYPE_MORSE_CODE_FINISHED]).morseCodeFinished();
			}
		};
	}

	public void beep(long duration)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_BEEP);
		bb.putShort((short)8);
		bb.putInt((int)duration);

		ipcon.write(this, bb, TYPE_BEEP, false);
	}

	public void morseCode(String morse)  {
		ByteBuffer bb = ByteBuffer.allocate(64);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_MORSE_CODE);
		bb.putShort((short)64);
		for(int i = 0; i < 60; i++) {
			try {
				bb.put((byte)morse.charAt(i));
			} catch(Exception e) {
				bb.put((byte)0);
			}
		}


		ipcon.write(this, bb, TYPE_MORSE_CODE, false);
	}

	public void addListener(Object o) {
		if(o instanceof BeepFinishedListener) {
			listenerObjects[TYPE_BEEP_FINISHED] = o;
		} else if(o instanceof MorseCodeFinishedListener) {
			listenerObjects[TYPE_MORSE_CODE_FINISHED] = o;
		}
	}
}