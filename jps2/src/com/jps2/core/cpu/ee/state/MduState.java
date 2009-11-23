package com.jps2.core.cpu.ee.state;

import com.jps2.core.cpu.registers.GeneralPorpuseRegister128bits;

public abstract class MduState extends GprState {

	public final GeneralPorpuseRegister128bits	HI;
	public final GeneralPorpuseRegister128bits	LO;

	@Override
	public void reset() {
		HI.write64(0);
		LO.write64(0);
	}

	@Override
	public void resetAll() {
		super.resetAll();
		HI.write64(0);
		LO.write64(0);
	}

	public MduState() {
		HI = new GeneralPorpuseRegister128bits();
		LO = new GeneralPorpuseRegister128bits();
	}

	public final void doMFHI(final int rd) {
		if (rd != 0) {
			gpr[rd].write64(HI.read64());
		}
	}

	public final void doMTHI(final int rs) {
		HI.write64(gpr[rs].read64());
	}

	public final void doMFLO(final int rd) {
		if (rd != 0) {
			gpr[rd].write64(LO.read64());
		}
	}

	public final void doMTLO(final int rs) {
		LO.write64(gpr[rs].read64());
	}

	public final void doMULT(final int rs, final int rt) {
		final long value = ((long) gpr[rs].read32()) * ((long) gpr[rt].read32());
		HI.write64(value >> 32);
		LO.write64(value & 0xFFFFFFFF);
	}

	public final void doMULTU(final int rs, final int rt) {
		final long value = ((long) Math.abs(gpr[rs].read32())) * ((long) Math.abs(gpr[rt].read32()));
		HI.write64(value >> 32);
		LO.write64(value & 0xFFFFFFFF);
	}

	public final void doDIV(final int rs, final int rt) {
		final int y = gpr[rt].read32();
		// According to MIPS spec., result is unpredictable when dividing by
		// zero.
		if (y != 0) {
			final int x = gpr[rs].read32();
			HI.write64(x % y);
			LO.write64(x / y);
		}
	}

	public final void doDIVU(final int rs, final int rt) {
		final int y = Math.abs(gpr[rt].read32());
		// According to MIPS spec., result is unpredictable when dividing by
		// zero.
		if (y != 0) {
			final int x = Math.abs(gpr[rs].read32());
			HI.write64(x % y);
			LO.write64(x / y);
		}
	}

	public final void doMADD(final int rs, final int rt, final int rd) {
		final long value = ((((long) HI.read32()) << 32) | LO.read32()) + ((long) gpr[rs].read32() * (long) gpr[rt].read32());

		LO.write64(value & 0xFFFFFFFF);
		HI.write64(value >> 32);

		if (rd != 0) {
			gpr[rd].write64(value & 0xFFFFFFFF);
		}
	}

	public final void doMADDU(final int rs, final int rt, final int rd) {
		final long value = ((((long) HI.read32()) << 32) | LO.read32()) + ((((long) gpr[rs].read32()) & 0xFFFFFFFF) * (((long) gpr[rt].read32()) & 0xFFFFFFFF));

		LO.write64(value & 0xFFFFFFFF);
		HI.write64(value >> 32);

		if (rd != 0) {
			gpr[rd].write64(value & 0xFFFFFFFF);
		}
	}

	public final void doMULT1(final int rs, final int rt, final int rd) {
		final long value = ((long) gpr[rs].read32()) * ((long) gpr[rt].read32());
		HI.write128(value >> 32, 0);
		LO.write128(value & 0xFFFFFFFF, 0);
		if (rd != 0) {
			gpr[rd].write64(value & 0xFFFFFFFF);
		}
	}

	public final void doMULTU1(final int rs, final int rt, final int rd) {
		final long value = (((long) gpr[rs].read32()) & 0xFFFFFFFF) * (((long) gpr[rt].read32()) & 0xFFFFFFFF);
		HI.write128(value >> 32, 0);
		LO.write128(value & 0xFFFFFFFF, 0);
		if (rd != 0) {
			gpr[rd].write64(value & 0xFFFFFFFF);
		}
	}

	public final void doDIV1(final int rs, final int rt) {
		final int y = gpr[rt].read32();
		// According to MIPS spec., result is unpredictable when dividing by
		// zero.
		if (y != 0) {
			final int x = gpr[rs].read32();
			HI.write128(x % y, 0);
			LO.write128(x / y, 0);
		}
	}

	public final void doDIVU1(final int rs, final int rt) {
		final int y = Math.abs(gpr[rt].read32());
		// According to MIPS spec., result is unpredictable when dividing by
		// zero.
		if (y != 0) {
			final int x = Math.abs(gpr[rs].read32());
			HI.write128(x % y, 0);
			LO.write128(x / y, 0);
		}
	}

	public final void doMADD1(final int rs, final int rt, final int rd) {
		final long value = ((((long) HI.read32()) << 32) | LO.read32()) + ((long) gpr[rs].read32() * (long) gpr[rt].read32());

		LO.write128(value & 0xFFFFFFFF, 0);
		HI.write128(value >> 32, 0);

		if (rd != 0) {
			gpr[rd].write64(value & 0xFFFFFFFF);
		}
	}

	public final void doMADDU1(final int rs, final int rt, final int rd) {
		final long value = ((((long) HI.read32()) << 32) | LO.read32()) + ((((long) gpr[rs].read32()) & 0xFFFFFFFF) * (((long) gpr[rt].read32()) & 0xFFFFFFFF));

		LO.write128(value & 0xFFFFFFFF, 0);
		HI.write128(value >> 32, 0);

		if (rd != 0) {
			gpr[rd].write64(value & 0xFFFFFFFF);
		}
	}

	public final void doPMFHLLW(final int rd) {
		final long[] value = new long[2];
		final long[] loValue = LO.read128();
		final long[] hiValue = HI.read128();

		value[1] = ((hiValue[1] & 0xFFFFFFFF) << 32) | (loValue[1] & 0xFFFFFFFF);
		value[0] = ((hiValue[0] & 0xFFFFFFFF) << 32) | (loValue[0] & 0xFFFFFFFF);

		gpr[rd].write128(value);
	}

	public final void doPMFHLUW(final int rd) {
		final long[] value = new long[2];
		final long[] loValue = LO.read128();
		final long[] hiValue = HI.read128();

		value[1] = (hiValue[1] & 0xFFFFFFFF00000000L) | ((loValue[1] >> 32) & 0xFFFFFFFF);
		value[0] = (hiValue[0] & 0xFFFFFFFF00000000L) | ((loValue[0] >> 32) & 0xFFFFFFFF);

		gpr[rd].write128(value);
	}

	public final void doPMFHLSLW(final int rd) {
		final long[] value = new long[2];
		final long[] loValue = LO.read128();
		final long[] hiValue = HI.read128();

		long temp = ((hiValue[1] & 0xFFFFFFFF) << 32) | (loValue[1] & 0xFFFFFFFF);

		if (temp >= 0x000000007FFFFFFFL) {
			value[1] = 0x000000007FFFFFFFL;
		} else
			if (temp <= 0xFFFFFFFF80000000L) {
				value[1] = 0xFFFFFFFF80000000L;
			} else {
				value[1] = loValue[1] & 0xFFFFFFFFL;
			}

		temp = ((hiValue[0] & 0xFFFFFFFF) << 32) | (loValue[0] & 0xFFFFFFFF);

		if (temp >= 0x000000007FFFFFFFL) {
			value[0] = 0x000000007FFFFFFFL;
		} else
			if (temp <= 0xFFFFFFFF80000000L) {
				value[0] = 0xFFFFFFFF80000000L;
			} else {
				value[0] = loValue[0] & 0xFFFFFFFFL;
			}

		gpr[rd].write128(value);
	}

	public final void doPMFHLLH(final int rd) {
		final long[] value = new long[2];
		final long[] loValue = LO.read128();
		final long[] hiValue = HI.read128();

		value[1] = ((((hiValue[1] >> 16) & 0xFF00) | (hiValue[1] & 0xFF)) << 32) | ((loValue[1] >> 16) & 0xFF00) | (loValue[1] & 0xFF);
		value[0] = ((((hiValue[0] >> 16) & 0xFF00) | (hiValue[0] & 0xFF)) << 32) | ((loValue[0] >> 16) & 0xFF00) | (loValue[0] & 0xFF);

		gpr[rd].write128(value);
	}

	public final void doPMFHLSH(final int rd) {
		final long[] value = new long[2];
		final long[] loValue = LO.read128();
		final long[] hiValue = HI.read128();

		value[1] = clampPMFHL((int) (loValue[1] & 0xFFFFFFFF));
		value[1] |= (clampPMFHL((int) ((loValue[1] >> 32) & 0xFFFFFFFF)) << 16);
		value[1] |= (clampPMFHL((int) (hiValue[1] & 0xFFFFFFFF)) << 32);
		value[1] |= (clampPMFHL((int) ((hiValue[1] >> 32) & 0xFFFFFFFF)) << 48);

		value[0] = clampPMFHL((int) (loValue[0] & 0xFFFFFFFF));
		value[0] |= (clampPMFHL((int) ((loValue[0] >> 32) & 0xFFFFFFFF)) << 16);
		value[0] |= (clampPMFHL((int) (hiValue[0] & 0xFFFFFFFF)) << 32);
		value[0] |= (clampPMFHL((int) ((hiValue[0] >> 32) & 0xFFFFFFFF)) << 48);

		gpr[rd].write128(value);
	}

	private static final int clampPMFHL(final int toClamp) {
		if (toClamp > 0x00007FFF) {
			return 0x7FFF;
		} else
			if (toClamp < 0xFFFF8000) {
				return 0x8000;
			} else {
				return toClamp;
			}
	}

	public final void doPMTHL(final int rs) {
		final long[] gprValue = gpr[rs].read128();
		final long[] loValue = LO.read128();
		final long[] hiValue = HI.read128();

		loValue[1] = (loValue[1] & 0xFFFFFFFF00000000L) | (gprValue[1] & 0xFFFFFFFF);
		hiValue[1] = (hiValue[1] & 0xFFFFFFFF00000000L) | ((gprValue[1]  >> 32) & 0xFFFFFFFF);
		
		loValue[0] = (loValue[0] & 0xFFFFFFFF00000000L) | (gprValue[0] & 0xFFFFFFFF);
		hiValue[0] = (hiValue[0] & 0xFFFFFFFF00000000L) | ((gprValue[0]  >> 32) & 0xFFFFFFFF);
	}
}
