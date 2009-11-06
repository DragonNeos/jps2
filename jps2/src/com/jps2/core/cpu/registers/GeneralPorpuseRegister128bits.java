package com.jps2.core.cpu.registers;

import com.jps2.core.cpu.DataType;

public class GeneralPorpuseRegister128bits implements Register128bits {

	long[] quadword = { 0, 0 };
	long doubleword = 0;
	int word = 0;

	DataType type = DataType.WORD;

	public final DataType getType() {
		return type;
	}

	public final byte read8() {
		if (type.lenght > 4) {
			if (type.lenght > 8) {
				return (byte) (quadword[1] & 0xFF);
			} else {
				return (byte) (doubleword & 0xFF);
			}
		} else {
			return (byte) (word & 0xFF);
		}
	}

	public final short read16() {
		if (type.lenght > 4) {
			if (type.lenght > 8) {
				return (short) (quadword[1] & 0xFFFF);
			} else {
				return (short) (doubleword & 0xFFFF);
			}
		} else {
			return (short) (word & 0xFFFF);
		}
	}

	public final int read32() {
		if (type.lenght > 4) {
			if (type.lenght > 8) {
				return (int) (quadword[1] & 0xFFFFFFFF);
			} else {
				return (int) (doubleword & 0xFFFFFFFF);
			}
		} else {
			return word;
		}
	}

	public final long read64() {
		if (type.lenght > 4) {
			if (type.lenght > 8) {
				return quadword[1];
			} else {
				return doubleword;
			}
		} else {
			return word;
		}
	}

	public final long[] read128() {
		final long[] result = new long[2];

		if (type.lenght > 4) {
			if (type.lenght > 8) {
				result[0] = quadword[0];
				result[1] = quadword[1];
			} else {
				result[1] = doubleword;
			}
		} else {
			result[1] = word;
		}

		return result;
	}

	public final void write8(final byte data) {
		type = DataType.BYTE;
		word = data;
	}

	public final void write16(final short data) {
		type = DataType.HALFWORD;
		word = data;
	}

	public final void write32(final int data) {
		type = DataType.WORD;
		word = data;
	}

	public final void write64(final long data) {
		type = DataType.DOUBLEWORD;
		doubleword = data;
	}

	@Override
	public void write128(final long[] data) {
		type = DataType.QUADWORD;
		quadword[0] = data[0];
		quadword[1] = data[1];
	}

	@Override
	public void writeBoolean(final boolean data) {
		type = DataType.BYTE;
		word = data ? 1 : 0;
	}

	@Override
	public boolean isFalse() {
		return isZero(read128());
	}

	@Override
	public boolean isTrue() {
		return !isZero(read128());
	}

	public static final boolean isZero(final long[] data) {
		return data[1] == 0 && data[0] == 0;
	}
}