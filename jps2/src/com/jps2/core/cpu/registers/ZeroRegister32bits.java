package com.jps2.core.cpu.registers;

import com.jps2.core.cpu.DataType;

public final class ZeroRegister32bits implements Register32bits {

	DataType	type	= DataType.WORD;

	@Override
	public final DataType getType() {
		return type;
	}

	@Override
	public final byte read8() {
		return 0;
	}

	@Override
	public final short read16() {
		return 0;
	}

	@Override
	public final int read32() {
		return 0;
	}

	@Override
	public final void write8(final byte data) {
	}

	@Override
	public final void write16(final short data) {
	}

	@Override
	public final void write32(final int data) {
	}

	@Override
	public boolean isFalse() {
		return true;
	}

	@Override
	public boolean isTrue() {
		return false;
	}

	@Override
	public void writeBoolean(final boolean data) {
	}
}
