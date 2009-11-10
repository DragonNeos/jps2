package com.jps2.plugins.event;

public class KeyEvent {
	private final boolean pressed;
	private final int keyCode;

	public KeyEvent(final int keyCode, final boolean pressed) {
		this.keyCode = keyCode;
		this.pressed = pressed;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public boolean isPressed() {
		return pressed;
	}
}
