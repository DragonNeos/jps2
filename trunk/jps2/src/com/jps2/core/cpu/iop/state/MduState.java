package com.jps2.core.cpu.iop.state;

public abstract class MduState extends GprState {

	long hilo;

	void setHi(final int value) {
		hilo = (hilo & 0xffffffffL) | (((long) value) << 32);
	}

	int getHi() {
		return (int) (hilo >>> 32);
	}

	void setLo(final int value) {
		hilo = (hilo & ~0xffffffffL) | ((value) & 0xffffffffL);
	}

	int getLo() {
		return (int) (hilo & 0xffffffffL);
	}

	@Override
	void reset() {
		hilo = 0;
	}

	@Override
	void resetAll() {
		super.resetAll();
		hilo = 0;
	}

	MduState() {
		hilo = 0;
	}

	void copy(final MduState that) {
		super.copy(that);
		hilo = that.hilo;
	}

	MduState(final MduState that) {
		super(that);
		hilo = that.hilo;
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

	public final void doMULTU(final int rs, final int rt) {
		hilo = ((long) Math.abs(gpr[rs].read32()))
				* ((long) Math.abs(gpr[rt].read32()));
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
}
