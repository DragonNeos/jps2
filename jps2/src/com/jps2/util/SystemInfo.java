package com.jps2.util;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

public final class SystemInfo {

	private static final List<DisplayMode>	validDisplayModes	= new ArrayList<DisplayMode>();

	private SystemInfo() {
	}

	public static final void getSystemInfo() {
		final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		// get valid display modes
		validDisplayModes.clear();
		for (final DisplayMode mode : device.getDisplayModes()) {
			// only 32 bits
			if (mode.getBitDepth() == 32) {
				validDisplayModes.add(mode);
			}
		}
	}

	public static final List<DisplayMode> getValidDisplayModes() {
		return validDisplayModes;
	}

	public static final boolean isValidDisplayMode(final DisplayMode mode) {
		boolean valid = false;

		if (mode != null) {
			for (final DisplayMode validMode : validDisplayModes) {
				if (mode.equals(validMode)) {
					valid = true;
					break;
				}
			}
		}

		return valid;
	}

	public static final DisplayMode getCompatibleDisplayMode(final DisplayMode invalidMode) {
		DisplayMode validMode = null;

		for (final DisplayMode mode : validDisplayModes) {
			if (validMode == null) {
				validMode = mode;
			} else
				if (mode.getWidth() == invalidMode.getWidth() && mode.getWidth() == invalidMode.getHeight()) {
					validMode = mode;
					break;
				} else
					if (mode.getWidth() >= validMode.getWidth() && mode.getWidth() <= invalidMode.getWidth() && mode.getHeight() >= validMode.getHeight()
							&& mode.getHeight() <= invalidMode.getHeight()) {
						validMode = mode;
					}
		}

		return validMode;
	}
	
	public static final boolean is32Bits(){
		return System.getProperty("os.arch").toLowerCase().contains("x86");
	}
	
	public static final boolean isMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
	
	public static final boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}
	
	public static final boolean isSolaris() {
		return System.getProperty("os.name").toLowerCase().contains("solaris");
	}
}
