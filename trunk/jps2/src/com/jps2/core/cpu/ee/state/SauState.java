package com.jps2.core.cpu.ee.state;

/**
 * Shift amount state
 * 
 * @author dyorgio
 * 
 */
public abstract class SauState extends FpuState {

	private int	SA	= 0;

	public final void doMFSA(int rd) {
		gpr[rd].write32(SA);
	}

	public final void doMTSA(int rd) {
		SA = gpr[rd].read32();
	}

	public final void doMTSAB(int rs, short imm16) {
		SA = ((gpr[rs].read8() & 0xF) ^ (imm16 & 0xF)) << 3;
	}

	public final void doMTSAH(int rs, short imm16) {
		SA = ((gpr[rs].read8() & 0x7) ^ (imm16 & 0x4)) << 3;
	}
}
