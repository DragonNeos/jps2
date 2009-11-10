package com.jps2.core.cpu.registers;

import com.jps2.core.cpu.DataType;

public final class ZeroRegister128bits implements Register128bits {

	private static final byte	ZERO_DOUBLEWORD	= 0;
	private static final long[]	ZERO_QUADWORD	= {
						ZERO_DOUBLEWORD,
						ZERO_DOUBLEWORD
												};

	DataType					type			= DataType.QUADWORD;

	@Override
	public final DataType getType() {
		return type;
	}

	@Override
	public final byte read8() {
		return ZERO_DOUBLEWORD;
	}

	@Override
	public final short read16() {
		return ZERO_DOUBLEWORD;
	}

	@Override
	public final int read32() {
		return ZERO_DOUBLEWORD;
	}

	@Override
	public final long read64() {
		return ZERO_DOUBLEWORD;
	}

	@Override
	public long[] read128() {
		return ZERO_QUADWORD;
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
	public void write128(final long[] data) {
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
