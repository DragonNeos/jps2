package com.jps2.util;

import java.io.InputStream;

import com.jps2.core.memory.Memory;

public class Bios {

	public static void load(final Memory memory) {
		try {
			final InputStream is = Bios.class.getClassLoader()
					.getResourceAsStream("bios/scph39001.bin");
			// final InputStream is =
			// Bios.class.getClassLoader().getResourceAsStream("bios/SCPH-70012_BIOS_V12_USA_200.bin");
			memory.writeStream(0, is);
			is.close();
		} catch (final Exception e) {
			throw new RuntimeException("Problems on load BIOS.", e);
		}
	}
}
