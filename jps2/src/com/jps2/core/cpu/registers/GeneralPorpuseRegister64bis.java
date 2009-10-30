package com.jps2.core.cpu.registers;

import com.jps2.core.cpu.DataType;

public class GeneralPorpuseRegister64bis implements Register64bits {
	long	 doubleword	= 0x0l;
	int	     word	    = 0x0;

	DataType	type	= DataType.WORD;

	public final DataType getType() {
		return type;
	}

	public final byte read8() {
		if (type.lenght > 4) {
			return (byte) (doubleword & 0xFF);
		} else {
			return (byte) (word & 0xFF);
		}
	}

	public final short read16() {
		if (type.lenght > 4) {
			return (short) (doubleword & 0xFFFF);
		} else {
			return (short) (word & 0xFFFF);
		}
	}

	public final int read32() {
		if (type.lenght > 4) {
			return (int) (doubleword & 0xFFFFFFFF);
		} else {
			return word;
		}
	}

	public final long read64() {
		if (type.lenght > 4) {
			return doubleword;
		} else {
			return word;
		}
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
	public boolean isFalse() {
		return read64() == 0;
	}

	@Override
	public boolean isTrue() {
		return read64() != 0;
	}

	@Override
	public void writeBoolean(final boolean data) {
		type = DataType.BYTE;
		word = data ? 1 : 0;
	}
}