package com.jps2.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.HashSet;

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

	// supported bios MD5
	private static final HashSet<String> knowBios = new HashSet<String>();
	static {
		// scph39001
		knowBios.add("d5ce2c7d119f563ce04bc04dbc3a323e");
		// SCPH-70012_BIOS_V12_USA_200
		knowBios.add("d333558cc14561c1fdc334c75d5f37b7");
	}

	private static final boolean isValid(final File file) {
		try {
			// valid bios must be at least 4mb.
			if (file.length() < 1024 * 4096) {
				return false;
			}

			final FileInputStream input = new FileInputStream(file);
			int readed = -1;
			byte[] buffer = new byte[512];
			final MessageDigest digest = MessageDigest.getInstance("MD5");
			// read bios file to md5 digest
			while ((readed = input.read(buffer)) != -1) {
				digest.update(buffer, 0, readed);
			}
			buffer = digest.digest();
			final StringBuilder hexMd5 = new StringBuilder();
			String hexPart;
			for (final byte b : buffer) {
				hexPart = "0" + Integer.toHexString(b);
				hexMd5.append(hexPart.substring(hexPart.length() - 2, hexPart
						.length()));
			}

			// for new bios uncomment and add in hashset
			// System.err.println(hexMd5);

			return knowBios.contains(hexMd5.toString());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
