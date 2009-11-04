package com.jps2.core.cpu.registers;

import com.jps2.core.cpu.DataType;

public class CP0Register implements Register32bits {

	public int value;

	@Override
	public final DataType getType() {
		return DataType.WORD;
	}

	@Override
	public boolean isFalse() {
		return value == 0;
	}

	@Override
	public boolean isTrue() {
		return value != 0;
	}

	@Override
	public short read16() {
		return (short) value;
	}

	@Override
	public int read32() {
		return value;
	}

	@Override
	public byte read8() {
		return (byte) value;
	}

	@Override
	public void write16(final short data) {
		value = data;
	}

	@Override
	public void write32(final int data) {
		value = data;
	}

	@Override
	public void write8(final byte data) {
		value = data;
	}

	@Override
	public void writeBoolean(final boolean data) {
		value = data ? 1 : 0;
	}
}
