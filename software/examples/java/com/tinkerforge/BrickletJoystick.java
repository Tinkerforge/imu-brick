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


public class BrickletJoystick extends Device {
	private final static byte TYPE_GET_POSITION = (byte)1;
	private final static byte TYPE_IS_PRESSED = (byte)2;
	private final static byte TYPE_GET_ANALOG_VALUE = (byte)3;
	private final static byte TYPE_CALIBRATE = (byte)4;
	private final static byte TYPE_SET_POSITION_CALLBACK_PERIOD = (byte)5;
	private final static byte TYPE_GET_POSITION_CALLBACK_PERIOD = (byte)6;
	private final static byte TYPE_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)7;
	private final static byte TYPE_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)8;
	private final static byte TYPE_SET_POSITION_CALLBACK_THRESHOLD = (byte)9;
	private final static byte TYPE_GET_POSITION_CALLBACK_THRESHOLD = (byte)10;
	private final static byte TYPE_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)11;
	private final static byte TYPE_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)12;
	private final static byte TYPE_SET_DEBOUNCE_PERIOD = (byte)13;
	private final static byte TYPE_GET_DEBOUNCE_PERIOD = (byte)14;
	private final static byte TYPE_POSITION = (byte)15;
	private final static byte TYPE_ANALOG_VALUE = (byte)16;
	private final static byte TYPE_POSITION_REACHED = (byte)17;
	private final static byte TYPE_ANALOG_VALUE_REACHED = (byte)18;
	private final static byte TYPE_PRESSED = (byte)19;
	private final static byte TYPE_RELEASED = (byte)20;

	public class Position {
		public short x;
		public short y;

		public String toString() {
			 return "[" + "x = " + x + ", " + "y = " + y + "]";
		}
	}

	public class AnalogValue {
		public int x;
		public int y;

		public String toString() {
			 return "[" + "x = " + x + ", " + "y = " + y + "]";
		}
	}

	public class PositionCallbackThreshold {
		public char option;
		public short minX;
		public short maxX;
		public short minY;
		public short maxY;

		public String toString() {
			 return "[" + "option = " + option + ", " + "minX = " + minX + ", " + "maxX = " + maxX + ", " + "minY = " + minY + ", " + "maxY = " + maxY + "]";
		}
	}

	public class AnalogValueCallbackThreshold {
		public char option;
		public int minX;
		public int maxX;
		public int minY;
		public int maxY;

		public String toString() {
			 return "[" + "option = " + option + ", " + "minX = " + minX + ", " + "maxX = " + maxX + ", " + "minY = " + minY + ", " + "maxY = " + maxY + "]";
		}
	}

	public interface PositionListener {
		public void position(short x, short y);
	}

	public interface AnalogValueListener {
		public void analogValue(int x, int y);
	}

	public interface PositionReachedListener {
		public void positionReached(short x, short y);
	}

	public interface AnalogValueReachedListener {
		public void analogValueReached(int x, int y);
	}

	public interface PressedListener {
		public void pressed();
	}

	public interface ReleasedListener {
		public void released();
	}

	public BrickletJoystick(String uid) {
		super(uid);

		callbacks[TYPE_POSITION] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());

				((PositionListener)listenerObjects[TYPE_POSITION]).position(x, y);
			}
		};

		callbacks[TYPE_ANALOG_VALUE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int x = IPConnection.unsignedShort(bb.getShort());
				int y = IPConnection.unsignedShort(bb.getShort());

				((AnalogValueListener)listenerObjects[TYPE_ANALOG_VALUE]).analogValue(x, y);
			}
		};

		callbacks[TYPE_POSITION_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());

				((PositionReachedListener)listenerObjects[TYPE_POSITION_REACHED]).positionReached(x, y);
			}
		};

		callbacks[TYPE_ANALOG_VALUE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 4, data.length - 4);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int x = IPConnection.unsignedShort(bb.getShort());
				int y = IPConnection.unsignedShort(bb.getShort());

				((AnalogValueReachedListener)listenerObjects[TYPE_ANALOG_VALUE_REACHED]).analogValueReached(x, y);
			}
		};

		callbacks[TYPE_PRESSED] = new CallbackListener() {
			public void callback(byte[] data) {
				((PressedListener)listenerObjects[TYPE_PRESSED]).pressed();
			}
		};

		callbacks[TYPE_RELEASED] = new CallbackListener() {
			public void callback(byte[] data) {
				((ReleasedListener)listenerObjects[TYPE_RELEASED]).released();
			}
		};
	}

	public Position getPosition() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_POSITION);
		bb.putShort((short)4);

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

		Position obj = new Position();
		obj.x = (bb.getShort());
		obj.y = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public boolean isPressed() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_IS_PRESSED);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_IS_PRESSED, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for isPressed in time");
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

	public AnalogValue getAnalogValue() throws IPConnection.TimeoutException {
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

		AnalogValue obj = new AnalogValue();
		obj.x = IPConnection.unsignedShort(bb.getShort());
		obj.y = IPConnection.unsignedShort(bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public void calibrate()  {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_CALIBRATE);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_CALIBRATE, false);
	}

	public void setPositionCallbackPeriod(long period)  {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_POSITION_CALLBACK_PERIOD);
		bb.putShort((short)8);
		bb.putInt((int)period);

		ipcon.write(this, bb, TYPE_SET_POSITION_CALLBACK_PERIOD, false);
	}

	public long getPositionCallbackPeriod() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_POSITION_CALLBACK_PERIOD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_POSITION_CALLBACK_PERIOD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getPositionCallbackPeriod in time");
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

	public void setPositionCallbackThreshold(char option, short minX, short maxX, short minY, short maxY)  {
		ByteBuffer bb = ByteBuffer.allocate(13);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_POSITION_CALLBACK_THRESHOLD);
		bb.putShort((short)13);
		bb.put((byte)option);
		bb.putShort((short)minX);
		bb.putShort((short)maxX);
		bb.putShort((short)minY);
		bb.putShort((short)maxY);

		ipcon.write(this, bb, TYPE_SET_POSITION_CALLBACK_THRESHOLD, false);
	}

	public PositionCallbackThreshold getPositionCallbackThreshold() throws IPConnection.TimeoutException {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_GET_POSITION_CALLBACK_THRESHOLD);
		bb.putShort((short)4);

		ipcon.write(this, bb, TYPE_GET_POSITION_CALLBACK_THRESHOLD, true);

		byte[] answer = null;
		try {
			answer = answerQueue.poll(IPConnection.TIMEOUT_ANSWER, TimeUnit.MILLISECONDS);
			if(answer == null) {
				throw new IPConnection.TimeoutException("Did not receive answer for getPositionCallbackThreshold in time");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bb = ByteBuffer.wrap(answer, 4, answer.length - 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		PositionCallbackThreshold obj = new PositionCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.minX = (bb.getShort());
		obj.maxX = (bb.getShort());
		obj.minY = (bb.getShort());
		obj.maxY = (bb.getShort());

		semaphoreWrite.release();
		return obj;
	}

	public void setAnalogValueCallbackThreshold(char option, int minX, int maxX, int minY, int maxY)  {
		ByteBuffer bb = ByteBuffer.allocate(13);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put((byte)stackID);
		bb.put((byte)TYPE_SET_ANALOG_VALUE_CALLBACK_THRESHOLD);
		bb.putShort((short)13);
		bb.put((byte)option);
		bb.putShort((short)minX);
		bb.putShort((short)maxX);
		bb.putShort((short)minY);
		bb.putShort((short)maxY);

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
		obj.minX = IPConnection.unsignedShort(bb.getShort());
		obj.maxX = IPConnection.unsignedShort(bb.getShort());
		obj.minY = IPConnection.unsignedShort(bb.getShort());
		obj.maxY = IPConnection.unsignedShort(bb.getShort());

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
		if(o instanceof PositionListener) {
			listenerObjects[TYPE_POSITION] = o;
		} else if(o instanceof AnalogValueListener) {
			listenerObjects[TYPE_ANALOG_VALUE] = o;
		} else if(o instanceof PositionReachedListener) {
			listenerObjects[TYPE_POSITION_REACHED] = o;
		} else if(o instanceof AnalogValueReachedListener) {
			listenerObjects[TYPE_ANALOG_VALUE_REACHED] = o;
		} else if(o instanceof PressedListener) {
			listenerObjects[TYPE_PRESSED] = o;
		} else if(o instanceof ReleasedListener) {
			listenerObjects[TYPE_RELEASED] = o;
		}
	}
}