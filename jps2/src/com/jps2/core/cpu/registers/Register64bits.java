package com.jps2.core.cpu.registers;

public interface Register64bits extends Register32bits {

	long read64();

	void write64(final long data);
}