package com.jps2.core.cpu.iop.state;

import java.math.BigInteger;

import com.jps2.core.cpu.Cpu;
import com.jps2.core.cpu.ExcCode;
import com.jps2.core.cpu.registers.GeneralPorpuseRegister32bis;
import com.jps2.core.cpu.registers.Register32bits;
import com.jps2.core.cpu.registers.ZeroRegister32bits;

/**
 * General Purpose Registers, handles integer operations like ALU, shifter, etc.
 * Handles control register too
 */
abstract class GprState extends Cpu {

	Register32bits[] gpr;

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

	public final void doSLL(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() << sa);
		}
	}

	public final void doSRL(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >>> sa);
		}
	}

	public final void doSRA(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >> sa);
		}
	}

	public final void doSLLV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write32((gpr[rt].read32() << (gpr[rs].read32() & 31)));
		}
	}

	public final void doSRLV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >>> (gpr[rs].read32() & 31));
		}
	}

	public final void doSRAV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >> (gpr[rs].read32() & 31));
		}
	}

	public final void doADDU(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() + gpr[rt].read32());
		}
	}

	public final void doAND(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() & gpr[rt].read32());
		}
	}

	public final void doSUB(final int rd, final int rs, final int rt,
			final int inst, final boolean delay) {
		final BigInteger sub = BigInteger.valueOf(gpr[rs].read32()).subtract(
				BigInteger.valueOf(gpr[rt].read32()));
		if (sub.bitCount() > 32) {
			processor
					.processException(ExcCode.ARITHMETIC_OVERFLOW, inst, delay);
		} else {
			gpr[rd].write32(sub.intValue());
		}
	}

	public final void doSUBU(final int rd, final int rs, final int rt,
			final int inst, final boolean delay) {
		gpr[rd].write32(gpr[rs].read32() - gpr[rt].read32());
	}

	public final void doOR(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() | gpr[rt].read32());
		}
	}

	public final void doXOR(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() ^ gpr[rt].read32());
		}
	}

	public final void doNOR(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(~(gpr[rs].read32() | gpr[rt].read32()));
		}
	}

	public final void doSLT(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(signedCompare(gpr[rs].read32(), gpr[rt].read32()));
		}
	}

	public final void doSLTU(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd]
					.write32(unsignedCompare(gpr[rs].read32(), gpr[rt].read32()));
		}
	}

	public final void doADDIU(final int rt, final int rs, final int simm16) {
		if (rt != 0) {
			gpr[rt].write32(gpr[rs].read32() + simm16);
		}
	}

	public final void doSLTI(final int rt, final int rs, final int simm16) {
		if (rt != 0) {
			gpr[rt].write32(signedCompare(gpr[rs].read32(), simm16));
		}
	}

	public final void doSLTIU(final int rt, final int rs, final int simm16) {
		if (rt != 0) {
			gpr[rt].write32(unsignedCompare(gpr[rs].read32(), simm16));
		}
	}

	public final void doANDI(final int rt, final int rs, final int uimm16) {
		if (rt != 0) {
			gpr[rt].write32(gpr[rs].read32() & uimm16);
		}
	}

	public final void doORI(final int rt, final int rs, final int uimm16) {
		if (rt != 0) {
			gpr[rt].write32(gpr[rs].read32() | uimm16);
		}
	}

	public final void doXORI(final int rt, final int rs, final int uimm16) {
		if (rt != 0) {
			gpr[rt].write32(gpr[rs].read32() ^ uimm16);
		}
	}

	public final void doLUI(final int rt, final int uimm16) {
		if (rt != 0) {
			gpr[rt].write32(uimm16 << 16);
		}
	}

	public final void doADD(final int rd, final int rs, final int rt,
			final int inst, final boolean delay) {
		final BigInteger sum = BigInteger.valueOf(gpr[rs].read32()).add(
				BigInteger.valueOf(gpr[rt].read32()));
		if (sum.bitCount() > 32) {
			processor
					.processException(ExcCode.ARITHMETIC_OVERFLOW, inst, delay);
		} else {
			gpr[rd].write32(sum.intValue());
		}
	}
}
