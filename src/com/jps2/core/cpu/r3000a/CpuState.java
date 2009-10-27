package com.jps2.core.cpu.r3000a;

public class CpuState extends BcuState {

	static final int	CP0_INDEX	  = 0;
	static final int	CP0_RANDOM	  = 1;
	static final int	CP0_ENTRYLO0	= 2;
	static final int	CP0_ENTRYLO1	= 3;
	static final int	CP0_CONTEXT	  = 4;
	static final int	CP0_PAGEMASK	= 5;
	static final int	CP0_WIRED	  = 6;
	static final int	CP0_RESERVED0	= 7;
	static final int	CP0_BADVADDR	= 8;
	static final int	CP0_COUNT	  = 9;
	static final int	CP0_ENTRYHI	  = 10;
	static final int	CP0_COMPARE	  = 11;
	static final int	CP0_STATUS	  = 12;
	static final int	CP0_CAUSE	  = 13;
	static final int	CP0_EPC	      = 14;
	static final int	CP0_PRID	  = 15;
	static final int	CP0_CONFIG	  = 16;
	static final int	CP0_LLADDR	  = 17;
	static final int	CP0_WATCHLO	  = 18;
	static final int	CP0_WATCHHI	  = 19;
	static final int	CP0_XCONTEXT	= 20;
	static final int	CP0_RESERVED1	= 21;
	static final int	CP0_RESERVED2	= 22;
	static final int	CP0_RESERVED3	= 23;
	static final int	CP0_RESERVED4	= 24;
	static final int	CP0_RESERVED5	= 25;
	static final int	CP0_ECC	      = 26;
	static final int	CP0_CACHEERR	= 27;
	static final int	CP0_TAGLO	  = 28;
	static final int	CP0_TAGHI	  = 29;
	static final int	CP0_ERROREPC	= 30;
	static final int	CP0_RESERVED6	= 31;

	int[]	         cp0;

	Counter[]	     counters;

	int	             cycle	          = 0;

	public int	     interrupt	      = 0;

	@Override
	void reset() {
		resetAll();
		cp0[CP0_STATUS] = 0x10900000; // COP0 enabled | BEV = 1 | TS = 1
		cp0[CP0_PRID] = 0x0000001f;
		for (int i = 0; i < counters.length; i++) {
			counters[i].reset();
		}
	}

	CpuState() {
		cp0 = new int[32];
		counters = new Counter[8];
		for (int i = 0; i < counters.length; i++) {
			counters[i] = new Counter();
		}
	}

	void copy(final CpuState that) {
		cp0 = that.cp0.clone();
		super.copy(that);
	}

	CpuState(final CpuState that) {
		copy(that);
	}

	public int getPc() {
		return pc;
	}

	public Counter getCounter(final int index) {
		return counters[index];
	}

	public void iopTestIntc() {
		if (memory.read32(0x1FB01078) == 0)
			return;
		if ((memory.read32(0x1FB01070) & memory.read32(0x1FB01074)) == 0)
			return;

		// if( !eeEventTestIsActive )
		// {
		// // An iop exception has occurred while the EE is running code.
		// // Inform the EE to branch so the IOP can handle it promptly:
		//
		// cpuSetNextBranchDelta( 16 );
		// iopBranchAction = true;
		// //Console.Error( "** IOP Needs an EE EventText, kthx **  %d", psxCycleEE );
		//
		// // Note: No need to set the iop's branch delta here, since the EE
		// // will run an IOP branch test regardless.
		// }
		// else if( !iopEventTestIsActive )
		// psxSetNextBranchDelta( 2 );
	}

	void psxRcntInit() {
		int i;

		for (i = 0; i < counters.length; i++) {
			counters[i].reset();
		}

		for (i = 0; i < 3; i++) {
			counters[i].rate = 1;
			counters[i].mode |= 0x0400;
			counters[i].target = IOPCNT_FUTURE_TARGET;
		}
		for (i = 3; i < 6; i++) {
			counters[i].rate = 1;
			counters[i].mode |= 0x0400;
			counters[i].target = IOPCNT_FUTURE_TARGET;
		}

		counters[0].interrupt = 0x10;
		counters[1].interrupt = 0x20;
		counters[2].interrupt = 0x40;

		counters[3].interrupt = 0x04000;
		counters[4].interrupt = 0x08000;
		counters[5].interrupt = 0x10000;
		//
		// if (SPU2async != NULL) {
		// counters[6].rate = 768 * 12;
		// counters[6].cycleT = counters[6].rate;
		// counters[6].mode = 0x8;
		// }
		//
		// if (USBasync != NULL) {
		// counters[7].rate = PSXCLK / 1000;
		// counters[7].cycleT = counters[7].rate;
		// counters[7].mode = 0x8;
		// }

		for (i = 0; i < 8; i++) {
			counters[i].cycleT = cycle;
		}
	}

	public short psxRcntRcount16(final int index) {
		final Counter counter = counters[index];
		if ((counter.mode & 0x1000000) != 0) {
			return (short) counter.count;
		}
		return (short) (counter.count + ((cycle - counter.cycleT) / counter.rate));
	}

	public int psxRcntRcount32(final int index) {
		final Counter counter = counters[index];
		if ((counter.mode & 0x1000000) != 0) {
			return (int) counter.count;
		}
		return (int) (counter.count + ((cycle - counter.cycleT) / counter.rate));
	}

	final void doRFE() {
		cp0[CP0_STATUS] = (cp0[CP0_STATUS] & 0xfffffff0) | ((cp0[CP0_STATUS] & 0x3c) >> 2);
	}

	final void doMFC0(final int rt, final int c0dr) {
		if (gpr[rt].isFalse()) {
			gpr[rt].write32(cp0[c0dr]);
		}
	}

	final void doMTC0(final int rt, final int c0dr) {
		cp0[c0dr] = gpr[rt].read32();
	}

	final void doCFC0(final int rt, final int c0dr) {
		if (gpr[rt].isFalse()) {
			gpr[rt].write32(cp0[c0dr]);
		}
	}

	final void doCTC0(final int rt, final int c0dr) {
		cp0[c0dr] = gpr[rt].read32();
	}
}
