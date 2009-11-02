package com.jps2.core.memory;

import org.apache.log4j.Logger;

public abstract class AbstractMemoryManager {

	public static final Logger logger = Logger
			.getLogger(AbstractMemoryManager.class);

	public abstract Memory getMemoryByAddress(int address, boolean write);

	public int read8(final int address) {
		return getMemoryByAddress(address, false).read8(address);
	}

	public int read16(final int address) {
		return getMemoryByAddress(address, false).read16(address);
	}

	public int read32(final int address) {
		return getMemoryByAddress(address, false).read32(address);
	}

	public long read64(final int address) {
		return getMemoryByAddress(address, false).read64(address);
	}

	public void write8(final int address, final byte data) {
		Memory mem = getMemoryByAddress(address, true);
		if (mem != null) {
			mem.write8(address, data);
		}
	}

	public void write16(final int address, final short data) {
		Memory mem = getMemoryByAddress(address, true);
		if (mem != null) {
			mem.write16(address, data);
		}
	}

	public void write32(final int address, final int data) {
		Memory mem = getMemoryByAddress(address, true);
		if (mem != null) {
			mem.write32(address, data);
		}
	}

	public void write64(final int address, final long data) {
		getMemoryByAddress(address, true).write64(address, data);
	}
}
