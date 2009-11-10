package com.jps2.core.cpu.ee.state;

public abstract class MduState extends GprState {

	public long	hilo;

	public void setHi(final int value) {
		hilo = (hilo & 0xffffffffL) | (((long) value) << 32);
	}

	public int getHi() {
		return (int) (hilo >>> 32);
	}

	public void setLo(final int value) {
		hilo = (hilo & ~0xffffffffL) | ((value) & 0xffffffffL);
	}

	public int getLo() {
		return (int) (hilo & 0xffffffffL);
	}

	@Override
	public void reset() {
		hilo = 0;
	}

	@Override
	public void resetAll() {
		super.resetAll();
		hilo = 0;
	}

	public MduState() {
		hilo = 0;
	}

	public void copy(final MduState that) {
		super.copy(that);
		hilo = that.hilo;
	}

	public MduState(final MduState that) {
		super(that);
		hilo = that.hilo;
	}

	public static final long signedDivMod(final int x, final int y) {
		return (((long) (x % y)) << 32) | (((x / y)) & 0xffffffffL);
	}

	public static final long unsignedDivMod(final long x, final long y) {
		return ((x % y) << 32) | ((x / y) & 0xffffffffL);
	}

	public final void doMFHI(final int rd) {
		if (rd != 0) {
			gpr[rd].write32(getHi());
		}
	}

	public final void doMTHI(final int rs) {
		final int hi = gpr[rs].read32();
		hilo = (((long) hi) << 32) | (hilo & 0xffffffffL);
	}

	public final void doMFLO(final int rd) {
		if (rd != 0) {
			gpr[rd].write32(getLo());
		}
	}

	public final void doMTLO(final int rs) {
		final int lo = gpr[rs].read32();
		hilo = (hilo & 0xffffffff00000000L) | ((lo) & 0x00000000ffffffffL);
	}

	public final void doMULT(final int rs, final int rt) {
		hilo = ((long) gpr[rs].read32()) * ((long) gpr[rt].read32());
	}

	public final void doDMULT(final int rs, final int rt) {
		hilo = gpr[rs].read64() * gpr[rt].read64();
	}

	public final void doMULTU(final int rs, final int rt) {
		hilo = ((long) Math.abs(gpr[rs].read32())) * ((long) Math.abs(gpr[rt].read32()));
	}

	public final void doDMULTU(final int rs, final int rt) {
		hilo = Math.abs(gpr[rs].read64()) * Math.abs(gpr[rt].read64());
	}

	public final void doDIV(final int rs, final int rt) {
		// According to MIPS spec., result is unpredictable when dividing by
		// zero.
		if (gpr[rt].read32() != 0) {
			final long x = gpr[rs].read32();
			final long y = gpr[rt].read32();
			hilo = (x % y << 32) | ((x / y) & 0xFFFFFFFFL);
		}
	}

	public final void doDIVU(final int rs, final int rt) {
		// According to MIPS spec., result is unpredictable when dividing by
		// zero.
		if (gpr[rt].read32() != 0) {
			final long x = Math.abs(gpr[rs].read32());
			final long y = Math.abs(gpr[rt].read32());
			hilo = (x % y << 32) | ((x / y) & 0xFFFFFFFFL);
		}
	}

	public final void doDDIV(final int rs, final int rt) {
		// According to MIPS spec., result is unpredictable when dividing by
		// zero.
		if (gpr[rt].read64() != 0) {
			final long x = gpr[rs].read64();
			final long y = gpr[rt].read64();
			hilo = (x % y << 32) | ((x / y) & 0xFFFFFFFFL);
		}
	}

	public final void doDDIVU(final int rs, final int rt) {
		// According to MIPS spec., result is unpredictable when dividing by
		// zero.
		if (gpr[rt].read64() != 0) {
			final long x = Math.abs(gpr[rs].read64());
			final long y = Math.abs(gpr[rt].read64());
			hilo = (x % y << 32) | ((x / y) & 0xFFFFFFFFL);
		}
	}

	public final void doMADD(final int rs, final int rt) {
		hilo += ((long) gpr[rs].read32()) * ((long) gpr[rt].read32());
	}

	public final void doMADDU(final int rs, final int rt) {
		hilo += ((gpr[rs].read32()) & 0xffffffffL) * ((gpr[rt].read32()) & 0xffffffffL);
	}

	public final void doMSUB(final int rs, final int rt) {
		hilo -= ((long) gpr[rs].read32()) * ((long) gpr[rt].read32());
	}

	public final void doMSUBU(final int rs, final int rt) {
		hilo -= ((gpr[rs].read32()) & 0xffffffffL) * ((gpr[rt].read32()) & 0xffffffffL);
	}

}
