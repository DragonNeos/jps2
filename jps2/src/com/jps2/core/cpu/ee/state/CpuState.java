package com.jps2.core.cpu.ee.state;


public final class CpuState extends VfpuState {

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

	@Override
	public void reset() {
		resetAll();
		cp0[CP0_CONFIG] = 0x440;
		cp0[CP0_STATUS] = 0x70400004; //0x10900000 <-- wrong; // COP0 enabled | BEV = 1 | TS = 1
		cp0[CP0_PRID] = 0x00002e20; // PRevID = Revision ID, same as R5900
	}

	public CpuState() {
		cp0 = new int[32];
		reset();
	}

	public final void doTLBR() {

	}

	public final void doTLBWI() {

	}

	public final void doTLBWR() {

	}

	public final void doTLBP() {

	}

	public final void doWAIT() {

	}

	public final void doDERET() {

	}

	public static final class CP0regs {

	}
}
