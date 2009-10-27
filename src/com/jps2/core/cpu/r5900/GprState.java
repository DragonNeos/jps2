package com.jps2.core.cpu.r5900;

import java.math.BigInteger;

import com.jps2.core.cpu.ExcCode;
import com.jps2.core.cpu.registers.GeneralPorpuseRegister64bis;
import com.jps2.core.cpu.registers.Register64bits;
import com.jps2.core.cpu.registers.ZeroRegister64bits;


/**
 * General Purpose Registers, handles integer operations like ALU, shifter, etc. Handles control register too
 */
public class GprState {

	public static final int	ZERO	= 0;
	public static final int	AT	 = 1;
	public static final int	V0	 = 2;
	public static final int	V1	 = 3;
	public static final int	A0	 = 4;
	public static final int	A1	 = 5;
	public static final int	A2	 = 6;
	public static final int	A3	 = 7;
	public static final int	T0	 = 8;
	public static final int	T1	 = 9;
	public static final int	T2	 = 10;
	public static final int	T3	 = 11;
	public static final int	T4	 = 12;
	public static final int	T5	 = 13;
	public static final int	T6	 = 14;
	public static final int	T7	 = 15;
	public static final int	S0	 = 16;
	public static final int	S1	 = 17;
	public static final int	S2	 = 18;
	public static final int	S3	 = 19;
	public static final int	S4	 = 20;
	public static final int	S5	 = 21;
	public static final int	S6	 = 22;
	public static final int	S7	 = 23;
	public static final int	T8	 = 24;
	public static final int	T9	 = 25;
	public static final int	K0	 = 26;
	public static final int	K1	 = 27;
	public static final int	GP	 = 28;
	public static final int	SP	 = 29;
	public static final int	FP	 = 30;
	public static final int	RA	 = 31;

	public int	            pc;
	public int	            npc;

	public Register64bits[]	gpr;

	public void reset() {
		gpr[0] = new ZeroRegister64bits();
		for (int i = 1; i < gpr.length; i++) {
			gpr[i] = new GeneralPorpuseRegister64bis();
		}
	}

	public void resetAll() {
		gpr[0] = new ZeroRegister64bits();
		for (int i = 1; i < gpr.length; i++) {
			gpr[i] = new GeneralPorpuseRegister64bis();
		}
	}

	public GprState() {
		gpr = new Register64bits[32];
	}

	public void copy(final GprState that) {
		gpr = that.gpr.clone();
	}

	public GprState(final GprState that) {
		gpr = that.gpr.clone();
	}

	public void doUNK(final String reason) {
		System.err.println("Interpreter : " + reason);
	}

	public static final int extractBits(final int x, final int pos, final int len) {
		return (x >>> pos) & ~(~0 << len);
	}

	public static final int insertBits(final int x, final int y, final int lsb, final int msb) {
		final int mask = ~(~0 << (msb - lsb + 1)) << lsb;
		return (x & ~mask) | ((y << lsb) & mask);
	}

	public static final int signExtend(final int value) {
		return (value << 16) >> 16;
	}

	public static final int signExtend8(final int value) {
		return (value << 24) >> 24;
	}

	public static final int zeroExtend(final int value) {
		return (value & 0xffff);
	}

	public static final int zeroExtend8(final int value) {
		return (value & 0xff);
	}

	public static final int signedCompare(final int i, final int j) {
		return (i < j) ? 1 : 0;
	}

	public static final int unsignedCompare(final long i, final long j) {
		return ((i & 0xffffffffL) < (j & 0xffffffffL)) ? 1 : 0;
	}

	// not sure about it
	// public static final int unsignedCompare(int i, int j) {
	// return (i - j) >>> 31;
	// }

	public final void doSLL(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() << sa);
		}
	}

	public final void doDSLL(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write64(gpr[rt].read64() << sa);
		}
	}

	public final void doDSLL32(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write64(gpr[rt].read64() << (sa + 32));
		}
	}

	public final void doSRL(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >>> sa);
		}
	}

	public final void doDSRL(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write64(gpr[rt].read64() >>> sa);
		}
	}

	public final void doDSRL32(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write64(gpr[rt].read64() >>> (sa + 32));
		}
	}

	public final void doSRA(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >> sa);
		}
	}

	public final void doDSRA(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write64(gpr[rt].read64() >> sa);
		}
	}

	public final void doDSRA32(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write64(gpr[rt].read64() >> (sa + 32));
		}
	}

	public final void doSLLV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write32((gpr[rt].read32() << (gpr[rs].read32() & 31)));
		}
	}

	public final void doDSLLV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write64(gpr[rt].read64() << (gpr[rs].read32() & 31));
		}
	}

	public final void doSRLV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >>> (gpr[rs].read32() & 31));
		}
	}

	public final void doDSRLV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write64(gpr[rt].read64() >>> (gpr[rs].read32() & 31));
		}
	}

	public final void doSRAV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rt].read32() >> (gpr[rs].read32() & 31));
		}
	}

	public final void doDSRAV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			gpr[rd].write64(gpr[rt].read64() >> (gpr[rs].read32() & 31));
		}
	}

	public final void doADDU(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() + gpr[rt].read32());
		}
	}

	public final void doDADDU(final int rd, final int rs, final int rt) {
		gpr[rd].write64(gpr[rs].read64() + gpr[rt].read64());
	}

	public final void doSUBU(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() - gpr[rt].read32());
		}
	}

	public final void doAND(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(gpr[rs].read32() & gpr[rt].read32());
		}
	}

	public final void doSUB(final int rd, final int rs, final int rt, final int inst, final boolean delay) {
		final BigInteger sub = BigInteger.valueOf(gpr[rs].read32()).subtract(BigInteger.valueOf(gpr[rt].read32()));
		if (sub.bitCount() > 32) {
			R5900.getProcessor().psxException(ExcCode.ARITHMETIC_OVERFLOW, inst, delay);
		} else {
			gpr[rd].write32(sub.intValue());
		}
	}

	public final void doDSUB(final int rd, final int rs, final int rt, final int inst, final boolean delay) {
		final BigInteger sub = BigInteger.valueOf(gpr[rs].read64()).subtract(BigInteger.valueOf(gpr[rt].read64()));
		if (sub.bitCount() > 64) {
			R5900.getProcessor().psxException(ExcCode.ARITHMETIC_OVERFLOW, inst, delay);
		} else {
			gpr[rd].write64(sub.longValue());
		}
	}

	public final void doSUBU(final int rd, final int rs, final int rt, final int inst, final boolean delay) {
		gpr[rd].write32(gpr[rs].read32() - gpr[rt].read32());
	}

	public final void doDSUBU(final int rd, final int rs, final int rt) {
		gpr[rd].write64(gpr[rs].read64() - gpr[rt].read64());
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
			gpr[rd].write32(unsignedCompare(gpr[rs].read32(), gpr[rt].read32()));
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

	public final void doROTR(final int rd, final int rt, final int sa) {
		if (rd != 0) {
			gpr[rd].write32(Integer.rotateRight(gpr[rt].read32(), sa));
		}
	}

	public final void doROTRV(final int rd, final int rt, final int rs) {
		if (rd != 0) {
			// no need of "gpr[rs] & 31", rotateRight does it for us
			gpr[rd].write32(Integer.rotateRight(gpr[rt].read32(), gpr[rs].read32()));
		}
	}

	public final void doMOVZ(final int rd, final int rs, final int rt) {
		if ((rd != 0) && (gpr[rt].read32() == 0)) {
			copyRegister(gpr[rd], gpr[rs]);
		}
	}

	public final void doMOVT(final int rd, final int rs, final int cc) {
		if (cc == 1) {
			copyRegister(gpr[rd], gpr[rs]);
		}
	}

	public final void doMOVF(final int rd, final int rs, final int cc) {
		if (cc == 0) {
			copyRegister(gpr[rd], gpr[rs]);
		}
	}

	public final void doMOVN(final int rd, final int rs, final int rt) {
		if ((rd != 0) && (gpr[rt].read32() != 0)) {
			copyRegister(gpr[rd], gpr[rs]);
		}
	}

	public static final void copyRegister(final Register64bits dest, final Register64bits orig) {
		switch (orig.getType()) {
		case DOUBLEWORD:
			dest.write64(orig.read64());
			break;
		case WORD:
			dest.write32(orig.read32());
			break;
		case HALFWORD:
			dest.write16(orig.read16());
			break;
		case BYTE:
			dest.write8(orig.read8());
			break;
		default:
			throw new RuntimeException("Not suported datatype:" + orig.getType());
		}
	}

	public final void doCLZ(final int rd, final int rs) {
		if (rd != 0) {
			gpr[rd].write32(Integer.numberOfLeadingZeros(gpr[rs].read32()));
		}
	}

	public final void doCLO(final int rd, final int rs) {
		if (rd != 0) {
			gpr[rd].write32(Integer.numberOfLeadingZeros(~gpr[rs].read32()));
		}
	}

	public final void doMAX(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(Math.max(gpr[rs].read32(), gpr[rt].read32()));
		}
	}

	public final void doMIN(final int rd, final int rs, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(Math.min(gpr[rs].read32(), gpr[rt].read32()));
		}
	}

	public final void doEXT(final int rt, final int rs, final int lsb, final int msbd) {
		if (rt != 0) {
			gpr[rt].write32(extractBits(gpr[rs].read32(), lsb, (msbd + 1)));
		}
	}

	public final void doINS(final int rt, final int rs, final int lsb, final int msb) {
		if (rt != 0) {
			gpr[rt].write32(insertBits(gpr[rt].read32(), gpr[rs].read32(), lsb, msb));
		}
	}

	public final void doWSBH(final int rd, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(Integer.rotateRight(Integer.reverseBytes(gpr[rt].read32()), 16));
		}
	}

	public final void doWSBW(final int rd, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(Integer.reverseBytes(gpr[rt].read32()));
		}
	}

	public final void doADD(final int rd, final int rs, final int rt, final int inst, final boolean delay) {
		final BigInteger sum = BigInteger.valueOf(gpr[rs].read32()).add(BigInteger.valueOf(gpr[rt].read32()));
		if (sum.bitCount() > 32) {
			R5900.getProcessor().psxException(ExcCode.ARITHMETIC_OVERFLOW, inst, delay);
		} else {
			gpr[rd].write32(sum.intValue());
		}
	}

	public final void doDADD(final int rd, final int rs, final int rt, final int inst, final boolean delay) {
		final BigInteger sum = BigInteger.valueOf(gpr[rs].read64()).add(BigInteger.valueOf(gpr[rt].read64()));
		if (sum.bitCount() > 64) {
			R5900.getProcessor().psxException(ExcCode.ARITHMETIC_OVERFLOW, inst, delay);
		} else {
			gpr[rd].write64(sum.longValue());
		}
	}

	public final void doSEB(final int rd, final int rt) {
		if (rd != 0) {
			gpr[rd].write8(gpr[rt].read8());
		}
	}

	public final void doBITREV(final int rd, final int rt) {
		if (rd != 0) {
			gpr[rd].write32(Integer.reverse(gpr[rt].read32()));
		}
	}

	public final void doSEH(final int rd, final int rt) {
		if (rd != 0) {
			gpr[rd].write16(gpr[rt].read16());
		}
	}

	public final void doTEQ(final int rs, final int rt, final int inst, final boolean delay) {
		if (gpr[rs] == gpr[rt]) {

			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTNE(final int rs, final int rt, final int inst, final boolean delay) {
		if (gpr[rs] != gpr[rt]) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTNEI(final int rs, final short imm16, final int inst, final boolean delay) {
		if (gpr[rs].read64() != imm16) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTGE(final int rs, final int rt, final int inst, final boolean delay) {
		if (gpr[rs].read64() >= gpr[rt].read64()) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTGEI(final int rs, final short imm16, final int inst, final boolean delay) {
		if (gpr[rs].read64() >= imm16) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTGEIU(final int rs, final short imm16, final int inst, final boolean delay) {
		if (Math.abs(gpr[rs].read64()) >= Math.abs(imm16)) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTGEU(final int rs, final int rt, final int inst, final boolean delay) {
		if (Math.abs(gpr[rs].read64()) >= Math.abs(gpr[rt].read64())) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTLT(final int rs, final int rt, final int inst, final boolean delay) {
		if (gpr[rs].read64() < gpr[rt].read64()) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTLTI(final int rs, final short imm16, final int inst, final boolean delay) {
		if (gpr[rs].read64() < imm16) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTLTIU(final int rs, final short imm16, final int inst, final boolean delay) {
		if (Math.abs(gpr[rs].read64()) < Math.abs(imm16)) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}

	public final void doTLTU(final int rs, final int rt, final int inst, final boolean delay) {
		if (Math.abs(gpr[rs].read64()) < Math.abs(gpr[rt].read64())) {
			R5900.getProcessor().psxException(ExcCode.TRAP, inst, delay);
		}
	}
}
