package com.jps2.core.memory;

public abstract class MemoryManager {
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
		getMemoryByAddress(address, true).write8(address, data);
	}

	public void write16(final int address, final short data) {
		getMemoryByAddress(address, true).write16(address, data);
	}

	public void write32(final int address, final int data) {
		getMemoryByAddress(address, true).write32(address, data);
	}

	public void write64(final int address, final long data) {
		getMemoryByAddress(address, true).write64(address, data);
	}
}
