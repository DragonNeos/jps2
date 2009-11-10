package com.jps2.core.cpu.registers;

import com.jps2.core.cpu.DataType;

public final class ZeroRegister64bits implements Register64bits {

	DataType	type	= DataType.DOUBLEWORD;

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
	public final long read64() {
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
	public final void write64(final long data) {
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
