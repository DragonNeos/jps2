package com.jps2.core.cpu.registers;

import com.jps2.core.cpu.DataType;

public class GeneralPorpuseRegister32bis implements Register32bits {

	final int index;
	private int word = 0x0;

	private DataType type = DataType.WORD;

	public GeneralPorpuseRegister32bis(final int index) {
		this.index = index;
	}

	@Override
	public final DataType getType() {
		return type;
	}

	public final byte read8() {
		return (byte) (word & 0xFF);
	}

	@Override
	public final short read16() {
		return (short) (word & 0xFFFF);
	}

	@Override
	public final int read32() {
		return word;
	}

	@Override
	public final void write8(final byte data) {
		type = DataType.BYTE;
		word = data;
	}

	@Override
	public final void write16(final short data) {
		type = DataType.HALFWORD;
		word = data;
	}

	@Override
	public final void write32(final int data) {
		type = DataType.WORD;
		word = data;
	}

	@Override
	public final boolean isFalse() {
		return word == 0;
	}

	@Override
	public final boolean isTrue() {
		return word != 0;
	}

	@Override
	public final void writeBoolean(final boolean data) {
		word = data ? 1 : 0;
	}
}