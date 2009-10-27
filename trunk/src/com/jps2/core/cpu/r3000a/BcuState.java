package com.jps2.core.cpu.r3000a;

import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Branch Control Unit, handles branching and jumping operations
 * 
 * @author hli
 * 
 */
public class BcuState extends LsuState {
	@Override
	void reset() {
		pc = 0xBFC00000;
		npc = 0xBFC00000;
	}

	@Override
	void resetAll() {
		super.resetAll();
		pc = 0xBFC00000;
		npc = 0xBFC00000;
	}

	BcuState() {
		pc = 0xBFC00000;
		npc = 0xBFC00000;
	}

	void copy(final BcuState that) {
		super.copy(that);
		pc = that.pc;
		npc = that.npc;
	}

	BcuState(final BcuState that) {
		super(that);
		pc = that.pc;
		npc = that.npc;
	}

	static final int branchTarget(final int npc, final int simm16) {
		return npc + (simm16 << 2);
	}

	static final int jumpTarget(final int npc, final int uimm26) {
		return (npc & 0xf0000000) | (uimm26 << 2);
	}

	private static final DecimalFormat	format	= new DecimalFormat("00000000000000000000000000000000");

	final int fetchOpcode() {
		npc = pc + 4;

		final int opcode = memory.read32(pc);
		if (R3000a.ENABLE_STEP_TRACE) {
			System.err.println(format.format(new BigInteger(Integer.toBinaryString(opcode))));
		}

		// by default, the next instruction to emulate is at the next address
		pc = npc;

		return opcode;
	}

	final int nextOpcode() {
		final int opcode = memory.read32(pc);

		// by default, the next instruction to emulate is at the next address
		pc += 4;

		return opcode;
	}

	final void nextPc() {
		pc = npc;
		npc = pc + 4;
	}

	final boolean doJR(final int rs) {
		npc = gpr[rs].read32();
		return true;
	}

	final boolean doJALR(final int rd, final int rs) {
		if (rd != 0) {
			gpr[rd].write32(pc + 4);
		}
		npc = gpr[rs].read32();
		return true;
	}

	final boolean doBLTZ(final int rs, final int simm16) {
		npc = (gpr[rs].read32() < 0) ? branchTarget(pc, simm16) : (pc + 4);
		return true;
	}

	final boolean doBGEZ(final int rs, final int simm16) {
		npc = (gpr[rs].read32() >= 0) ? branchTarget(pc, simm16) : (pc + 4);
		return true;
	}

	final boolean doBLTZAL(final int rs, final int simm16) {
		final int target = pc + 4;
		final boolean t = (gpr[rs].read32() < 0);
		gpr[31].write32(target);
		npc = t ? branchTarget(pc, simm16) : target;
		return true;
	}

	final boolean doBGEZAL(final int rs, final int simm16) {
		final int target = pc + 4;
		final boolean t = (gpr[rs].read32() >= 0);
		gpr[31].write32(target);
		npc = t ? branchTarget(pc, simm16) : target;
		return true;
	}

	final boolean doJ(final int uimm26) {
		npc = jumpTarget(pc, uimm26);
		if (npc == pc - 4) {
			throw new RuntimeException("Pausing emulator - jump to self (death loop)");
		}
		return true;
	}

	final boolean doJAL(final int uimm26) {
		gpr[31].write32(pc + 4);
		npc = jumpTarget(pc, uimm26);
		return true;
	}

	final boolean doBEQ(final int rs, final int rt, final int simm16) {
		npc = (gpr[rs].read32() == gpr[rt].read32()) ? branchTarget(pc, simm16) : (pc + 4);
		if (npc == pc - 4 && rs == rt) {
			throw new RuntimeException("Pausing emulator - branch to self (death loop)");
		}
		return true;
	}

	final boolean doBNE(final int rs, final int rt, final int simm16) {
		npc = (gpr[rs].read32() != gpr[rt].read32()) ? branchTarget(pc, simm16) : (pc + 4);
		return true;
	}

	final boolean doBLEZ(final int rs, final int simm16) {
		npc = (gpr[rs].read32() <= 0) ? branchTarget(pc, simm16) : (pc + 4);
		return true;
	}

	final boolean doBGTZ(final int rs, final int simm16) {
		npc = (gpr[rs].read32() > 0) ? branchTarget(pc, simm16) : (pc + 4);
		return true;
	}

}
