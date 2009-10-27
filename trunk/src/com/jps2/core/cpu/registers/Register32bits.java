package com.jps2.core.cpu.registers;

import com.jps2.core.cpu.DataType;

public interface Register32bits {

	DataType getType();
	
	boolean isTrue();
	
	boolean isFalse();

	byte read8();

	short read16();

	int read32();
	
	void writeBoolean(final boolean data);

	void write8(final byte data);

	void write16(final short data);

	void write32(final int data);
}