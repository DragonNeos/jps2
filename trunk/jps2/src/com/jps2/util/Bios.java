package com.jps2.util;

import java.io.File;
import java.io.FileInputStream;

import com.jps2.core.memory.Memory;

public class Bios {

	public static void load(final Memory memory) {
		try {
			if (isValid(ConfigManager.getBiosFile())) {
				final FileInputStream is = new FileInputStream(ConfigManager
						.getBiosFile());
				memory.writeStream(0, is);
				is.close();
			} else {
				throw new RuntimeException("Inválid Bios file.");
			}
		} catch (final Exception e) {
			throw new RuntimeException("Problems on load BIOS.", e);
		}
	}

	private static final boolean isValid(final File file) {
		try {
			// valid bios must be at least 4mb.
			if (file.length() < 1024 * 4096) {
				return false;
			}

			final FileInputStream input = new FileInputStream(file);
			int readed = -1;
			final byte[] buffer = new byte[512];
			final StringBuilder biosStr = new StringBuilder();
			// read bios file as String
			while ((readed = input.read(buffer)) != -1) {
				biosStr.append(new String(buffer, 0, readed));
			}
			int index = biosStr.indexOf("RESET");
			if (index != -1) {
				index = biosStr.indexOf("ROMVER");
				// TODO - Dyorgio Process bios version
				// System.err.println(biosStr.substring(index,index+200));
				return true;
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}
}
