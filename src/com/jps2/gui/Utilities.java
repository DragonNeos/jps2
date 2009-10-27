package com.jps2.gui;

public final class Utilities {
	private Utilities() {
	}

	public static final boolean isMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
}
