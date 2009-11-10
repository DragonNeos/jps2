package com.jps2.core.cpu.registers;

public interface Register128bits extends Register64bits {

	long[] read128();

	void write128(final long[] data);
}
