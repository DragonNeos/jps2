package com.jps2.core.cpu.ee;

import com.jps2.core.memory.FastMemory;

public class EEHardwareRegisters extends FastMemory {
	public EEHardwareRegisters() {
		super("EE Hardware Registers", 0xFFFF);
	}
}
