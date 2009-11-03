package com.jps2.core.cpu.ee.state;


public final class CpuState extends VfpuState {
	int[] cp0;

	@Override
	public void reset() {
		resetAll();
		cp0[CP0_CONFIG] = 0x440;
		cp0[CP0_STATUS] = 0x70400004; // 0x10900000 <-- wrong; // COP0 enabled |
		// BEV = 1 | TS = 1
		cp0[CP0_PRID] = 0x00002e20; // PRevID = Revision ID, same as R5900
	}

	public CpuState() {
		cp0 = new int[32];
		reset();
	}

	public final boolean isWriteLocked() {
		return (cp0[CP0_STATUS] & 0x10000) != 0;
	}

	public final void doMFC0(final int rt, final int c0dr) {
		if (rt != 0) {
			gpr[rt].write32(cp0[c0dr]);
		}
	}
	
	long lastCp0Cycle = 0;

	public final void doMTC0(final int rd, final int rt, final int imm) {
		switch (rd) {
		case 9:
			lastCp0Cycle = cycle;
			cp0[9] = gpr[rt].read32();
			break;

		case 12:
			WriteCP0Status(gpr[rt].read32());
			break;

		case 24:
			System.err.println(String.format("MTC0 Breakpoint debug Registers code = %x",
					imm));
			break;

		case 25:
			switch (imm) {
			case 0: // MTPS [LSB is clear]
				// Updates PCRs and sets the PCCR.
				COP0_UpdatePCCR();
				cpuRegs.PERF.n.pccr.val = cpuRegs.GPR.r[_Rt_].UL[0];
				COP0_DiagnosticPCCR();
				break;

			case 1: // MTPC [LSB is set] - set PCR0
				cpuRegs.PERF.n.pcr0 = cpuRegs.GPR.r[_Rt_].UL[0];
				s_iLastPERFCycle[0] = cpuRegs.cycle;
				break;

			case 3: // MTPC [LSB is set] - set PCR0
				cpuRegs.PERF.n.pcr1 = cpuRegs.GPR.r[_Rt_].UL[0];
				s_iLastPERFCycle[1] = cpuRegs.cycle;
				break;
			}
			break;

		default:
			cp0[rd] = gpr[rt].read32();
			break;
		}

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
