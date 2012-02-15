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


public class BrickletLCD20x4 extends Device {
	private final static byte TYPE_WRITE_LINE = (byte)1;
	private final static byte TYPE_CLEAR_DISPLAY = (byte)2;
	private final static byte TYPE_BACKLIGHT_ON = (byte)3;
	private final static byte TYPE_BACKLIGHT_OFF = (byte)4;
	private final static byte TYPE_IS_BACKLIGHT_ON = (byte)5;
	private final static byte TYPE_SET_CONFIG = (byte)6;
	private final static byte TYPE_GET_CONFIG = (byte)7;
	private final static byte TYPE_IS_BUTTON_PRESSED = (byte)8;
	private final static byte TYPE_BUTTON_PRESSED = (byte)9;
	private final static byte TYPE_BUTTON_RELEASED = (byte)10;

	public class Config {
		public boolean cursor;
		public boolean blinking;

		public String toString() {
			 return "[" + "cursor = " + cursor + ", " + "blinking = " + blinking + "]";
		}
	}

	public interface ButtonPressedListener {
		public void buttonPressed(short button);
	}

	public interface ButtonReleasedListener {
		public void buttonReleased(short button);
	}

	public BrickletLCD20x4(String uid) {
		super(uid);

		callbacks[TYPE_BUTTON_PRESSED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short button = IPConnection.unsignedByte(bb.get());

				((ButtonPressedListener)listenerObjects[TYPE_BUTTON_PRESSED]).buttonPressed(button);
			}
		};

		callbacks[TYPE_BUTTON_RELEASED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short button = IPConnection.unsignedByte(bb.get());

				((ButtonReleasedListener)listenerObjects[TYPE_BUTTON_RELEASED]).buttonReleased(button);
			}
		};
	}

	public void writeLine(short line, short position, String text)  {
		ByteBuffer bb = ByteBuffer.allocate(26);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_WRITE_LINE);
		bb.putShort((short)26);
		bb.put((byte)line);
		bb.put((byte)position);
		for(int i = 0; i < 20; i++) {
			try {
				bb.put((byte)text.charAt(i));
			} catch(Exception e) {
				bb.put((byte)0);
			}
		}


		ipcon.write(this, bb, TYPE_WRITE_LINE, false);
	}

	public void clearDisplay()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_CLEAR_DISPLAY);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_CLEAR_DISPLAY, false);
	}

	public void backlightOn()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_BACKLIGHT_ON);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_BACKLIGHT_ON, false);
	}

	public void backlightOff()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_BACKLIGHT_OFF);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_BACKLIGHT_OFF, false);
	}

	public boolean isBacklightOn() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_IS_BACKLIGHT_ON);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_IS_BACKLIGHT_ON, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for isBacklightOn in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean backlight = (bb.get()) != 0;

		semaphoreWrite.release();
		return backlight;
	}

	public void setConfig(boolean cursor, boolean blinking)  {
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_CONFIG);
		bb.putShort((short)6);
		bb.put((byte)(cursor ? 1 : 0));
		bb.put((byte)(blinking ? 1 : 0));

		ipcon.write(this, bb, TYPE_SET_CONFIG, false);
	}

	public Config getConfig() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_CONFIG);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_CONFIG, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getConfig in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Config obj = new Config();
		obj.cursor = (bb.get()) != 0;
		obj.blinking = (bb.get()) != 0;

		semaphoreWrite.release();
		return obj;
	}

	public boolean isButtonPressed(short button) throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_IS_BUTTON_PRESSED);
		bb.putShort((short)5);
		bb.put((byte)button);

		ipcon.write(this, bb, TYPE_IS_BUTTON_PRESSED, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for isButtonPressed in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean pressed = (bb.get()) != 0;

		semaphoreWrite.release();
		return pressed;
	}

	public void addListener(Object o) {
		if(o instanceof ButtonPressedListener) {
			listenerObjects[TYPE_BUTTON_PRESSED] = o;
		} else if(o instanceof ButtonReleasedListener) {
			listenerObjects[TYPE_BUTTON_RELEASED] = o;
		}
	}
}