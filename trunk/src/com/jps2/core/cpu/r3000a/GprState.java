package com.jps2.core.cpu.r3000a;

import java.math.BigInteger;

import com.jps2.core.cpu.ExcCode;
import com.jps2.core.cpu.registers.GeneralPorpuseRegister32bis;
import com.jps2.core.cpu.registers.Register32bits;
import com.jps2.core.cpu.registers.ZeroRegister32bits;

/**
 * General Purpose Registers, handles integer operations like ALU, shifter, etc. Handles control register too
 */
class GprState {

	static final int	ZERO	= 0;
	static final int	AT	 = 1;
	static final int	V0	 = 2;
	static final int	V1	 = 3;
	static final int	A0	 = 4;
	static final int	A1	 = 5;
	static final int	A2	 = 6;
	static final int	A3	 = 7;
	static final int	T0	 = 8;
	static final int	T1	 = 9;
	static final int	T2	 = 10;
	static final int	T3	 = 11;
	static final int	T4	 = 12;
	static final int	T5	 = 13;
	static final int	T6	 = 14;
	static final int	T7	 = 15;
	static final int	S0	 = 16;
	static final int	S1	 = 17;
	static final int	S2	 = 18;
	static final int	S3	 = 19;
	static final int	S4	 = 20;
	static final int	S5	 = 21;
	static final int	S6	 = 22;
	static final int	S7	 = 23;
	static final int	T8	 = 24;
	static final int	T9	 = 25;
	static final int	K0	 = 26;
	static final int	K1	 = 27;
	static final int	GP	 = 28;
	static final int	SP	 = 29;
	static final int	FP	 = 30;
	static final int	RA	 = 31;

	int	             pc;
	int	             npc;

	Register32bits[]	gpr;

	void reset() {
		gpr[0] = new ZeroRegister32bits();
		for (int i = 1; i < gpr.length; i++) {
			gpr[i] = new GeneralPorpuseRegister32bis(i);
		}
	}

	void resetAll() {
		gpr[0] = new ZeroRegister32bits();
		for (int i = 1; i < gpr.length; i++) {
			gpr[i] = new GeneralPorpuseRegister32bis(i);
		}
	}

	GprState() {
		gpr = new Register32bits[32];
	}

	void copy(final GprState that) {
		gpr = that.gpr.clone();
	}

	GprState(final GprState that) {
		gpr = that.gpr.clone();
	}

	void doUNK(final String reason) {
		System.err.println("Interpreter : " + reason);
	}

	private static final int signedCompare(final int i, final int j) {
		return (i < j) ? 1 : 0;
	}

	private static final int unsignedCompare(final long i, final long j) {
		return ((i & 0xffffffffL) < (j & 0xffffffffL)) ? 1 : 0;
	}

	final void doSLL(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() << sa);
		}
	}

	final void doSRL(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >>> sa);
		}
	}

	final void doSRA(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >> sa);
		}
	}

	final void doSLLV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write32((gpr[rt].read32() << (gpr[rs].read32() & 31)));
		}
	}

	final void doSRLV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >>> (gpr[rs].read32() & 31));
		}
	}

	final void doSRAV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >> (gpr[rs].read32() & 31));
		}
	}

	final void doADDU(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() + gpr[rt].read32());
		}
	}

	final void doAND(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() & gpr[rt].read32());
		}
	}

	final void doSUB(final int rd, final int rs, final int rt, final int inst, final boolean delay) {
		final BigInteger sub = BigInteger.valueOf(gpr[rs].read32()).subtract(BigInteger.valueOf(gpr[rt].read32()));
		if (sub.bitCount() > 32) {
			R3000a.getProcessor().psxException(ExcCode.ARITHMETIC_OVERFLOW, inst, delay);
		} else {
			gpr[rd].write32(sub.intValue());
		}
	}

	final void doSUBU(final int rd, final int rs, final int rt, final int inst, final boolean delay) {
		gpr[rd].write32(gpr[rs].read32() - gpr[rt].read32());
	}

	final void doOR(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() | gpr[rt].read32());
		}
	}

	final void doXOR(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() ^ gpr[rt].read32());
		}
	}

	final void doNOR(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(~(gpr[rs].read32() | gpr[rt].read32()));
		}
	}

	final void doSLT(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(signedCompare(gpr[rs].read32(), gpr[rt].read32()));
		}
	}

	final void doSLTU(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(unsignedCompare(gpr[rs].read32(), gpr[rt].read32()));
		}
	}

	final void doADDIU(final int rt, final int rs, final int simm16) {
		if (rt != 0) {
			gpr[rt].write32(gpr[rs].read32() + simm16);
		}
	}

	final void doSLTI(final int rt, final int rs, final int simm16) {
		if (rt != 0) {
			gpr[rt].write32(signedCompare(gpr[rs].read32(), simm16));
		}
	}

	final void doSLTIU(final int rt, final int rs, final int simm16) {
		if (rt != 0) {
			gpr[rt].write32(unsignedCompare(gpr[rs].read32(), simm16));
		}
	}

	final void doANDI(final int rt, final int rs, final int uimm16) {
		if (rt != 0) {
			gpr[rt].write32(gpr[rs].read32() & uimm16);
		}
	}

	final void doORI(final int rt, final int rs, final int uimm16) {
		if (rt != 0) {
			gpr[rt].write32(gpr[rs].read32() | uimm16);
		}
	}

	final void doXORI(final int rt, final int rs, final int uimm16) {
		if (rt != 0) {
			gpr[rt].write32(gpr[rs].read32() ^ uimm16);
		}
	}

	final void doLUI(final int rt, final int uimm16) {
		if (rt != 0) {
			gpr[rt].write32(uimm16 << 16);
		}
	}

	final void doADD(final int rd, final int rs, final int rt, final int inst, final boolean delay) {
		final BigInteger sum = BigInteger.valueOf(gpr[rs].read32()).add(BigInteger.valueOf(gpr[rt].read32()));
		if (sum.bitCount() > 32) {
			R3000a.getProcessor().psxException(ExcCode.ARITHMETIC_OVERFLOW, inst, delay);
		} else {
			gpr[rd].write32(sum.intValue());
		}
	}
}
