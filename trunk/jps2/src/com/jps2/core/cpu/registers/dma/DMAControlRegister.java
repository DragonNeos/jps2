package com.jps2.core.cpu.registers.dma;

import com.jps2.core.cpu.Memories;
import com.jps2.core.cpu.ee.EEHardwareRegisters;

public final class DMAControlRegister {
	private final int	value;

	public DMAControlRegister() {
		this.value = Memories.hwRegistersEE.read32(EEHardwareRegisters.DMAC_CTRL);
	}

	public final boolean isDMAEnabled() {
		return (value & 0x1) != 0x0;
	}

	public final boolean isReleaseSignalEnabled() {
		return (value & 0x2) != 0x0;
	}

	public final byte getMemoryFIFODrainChannel() {
		return (byte) ((value >> 2) & 0x3);
	}

	public final byte getStallControlSourceChannel() {
		return (byte) ((value >> 4) & 0x3);
	}

	public final byte getStallControlDrainChannel() {
		return (byte) ((value >> 6) & 0x3);
	}

	public final int getReleaseCycle() {
		return (int) Math.pow(2, 3 + ((value >> 6) & 0x7));
	}
}
