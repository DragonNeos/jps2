package com.jps2.core.cpu.ee.state;

import com.jps2.core.cpu.Memories;
import com.jps2.core.cpu.ee.EEHardwareRegisters;

/**
 * Branch Control Unit, handles branching and jumping operations
 * 
 * @author hli
 * 
 */
public abstract class BcuState extends LsuState {
	@Override
	public void reset() {
		pc = 0xBFC00000;
		npc = 0xBFC00000;
	}

	@Override
	public void resetAll() {
		super.resetAll();
		pc = 0xBFC00000;
		npc = 0xBFC00000;
	}

	public BcuState() {
		pc = 0xBFC00000;
		npc = 0xBFC00000;
	}

	public static int branchTarget(final int npc, final int simm16) {
		return npc + (simm16 << 2);
	}

	public static int jumpTarget(final int npc, final int uimm26) {
		return (npc & 0xf0000000) | (uimm26 << 2);
	}

	public final boolean cpcond0() {
		return ((Memories.hwRegistersEE.read16(EEHardwareRegisters.DMAC_STAT) & Memories.hwRegistersEE.read16(EEHardwareRegisters.DMAC_PCR)) & 0x3ff) == (Memories.hwRegistersEE
				.read16(EEHardwareRegisters.DMAC_PCR) & 0x3ff);
	}

	abstract void testEvents(final boolean delay);

	public int fetchOpcode() {
		npc = pc + 4;

		final int opcode = processor.memory.read32(pc);

		// by default, the next instruction to emulate is at the next address
		pc = npc;

		return opcode;
	}

	@Override
	public int nextOpcode() {
		final int opcode = processor.memory.read32(pc);

		// by default, the next instruction to emulate is at the next address
		pc += 4;

		return opcode;
	}

	@Override
	public void nextPc() {
		pc = npc;
		npc = pc + 4;
	}

	public boolean doJR(final int rs, final boolean delay) {
		npc = gpr[rs].read32();
		testEvents(delay);
		return true;
	}

	public boolean doJALR(final int rd, final int rs, final boolean delay) {
		if (rd != 0) {
			gpr[rd].write32(pc + 4);
		}
		npc = gpr[rs].read32();
		testEvents(delay);
		return true;
	}

	public boolean doBLTZ(final int rs, final int simm16, final boolean delay) {
		npc = (gpr[rs].read32() < 0) ? branchTarget(pc, simm16) : (pc + 4);
		testEvents(delay);
		return true;
	}

	public boolean doBGEZ(final int rs, final int simm16, final boolean delay) {
		npc = (gpr[rs].read32() >= 0) ? branchTarget(pc, simm16) : (pc + 4);
		testEvents(delay);
		return true;
	}

	public boolean doBLTZL(final int rs, final int simm16, final boolean delay) {
		if (gpr[rs].read32() < 0) {
			npc = branchTarget(pc, simm16);
			testEvents(delay);
			return true;
		} else {
			pc += 4;
			testEvents(delay);
		}
		return false;
	}

	public boolean doBGEZL(final int rs, final int simm16, final boolean delay) {
		if (gpr[rs].read32() >= 0) {
			npc = branchTarget(pc, simm16);
			testEvents(delay);
			return true;
		} else {
			pc += 4;
			testEvents(delay);
		}
		return false;
	}

	public boolean doBLTZAL(final int rs, final int simm16, final boolean delay) {
		final int target = pc + 4;
		final boolean t = (gpr[rs].read32() < 0);
		gpr[31].write32(target);
		npc = t ? branchTarget(pc, simm16) : target;
		testEvents(delay);
		return true;
	}

	public boolean doBGEZAL(final int rs, final int simm16, final boolean delay) {
		final int target = pc + 4;
		final boolean t = (gpr[rs].read32() >= 0);
		gpr[31].write32(target);
		npc = t ? branchTarget(pc, simm16) : target;
		testEvents(delay);
		return true;
	}

	public boolean doBLTZALL(final int rs, final int simm16, final boolean delay) {
		final boolean t = (gpr[rs].read32() < 0);
		gpr[31].write32(pc + 4);
		if (t) {
			npc = branchTarget(pc, simm16);
		} else {
			pc += 4;
		}
		testEvents(delay);
		return t;
	}

	public boolean doBGEZALL(final int rs, final int simm16, final boolean delay) {
		final boolean t = (gpr[rs].read32() >= 0);
		gpr[31].write32(pc + 4);
		if (t) {
			npc = branchTarget(pc, simm16);
		} else {
			pc += 4;
		}
		testEvents(delay);
		return t;
	}

	public boolean doJ(final int uimm26, final boolean delay) {
		npc = jumpTarget(pc, uimm26);
		if (npc == pc - 4) {
			throw new RuntimeException("Pausing emulator - jump to self (death loop)");
		}
		testEvents(delay);
		return true;
	}

	public boolean doJAL(final int uimm26, final boolean delay) {
		gpr[31].write32(pc + 4);
		npc = jumpTarget(pc, uimm26);
		testEvents(delay);
		return true;
	}

	public boolean doBEQ(final int rs, final int rt, final int simm16, final boolean delay) {
		npc = (gpr[rs].read32() == gpr[rt].read32()) ? branchTarget(pc, simm16) : (pc + 4);
		if (npc == pc - 4 && rs == rt) {
			throw new RuntimeException("Pausing emulator - branch to self (death loop)");
		}
		testEvents(delay);
		return true;
	}

	public boolean doBNE(final int rs, final int rt, final int simm16, final boolean delay) {
		npc = (gpr[rs].read32() != gpr[rt].read32()) ? branchTarget(pc, simm16) : (pc + 4);
		testEvents(delay);
		return true;
	}

	public boolean doBLEZ(final int rs, final int simm16, final boolean delay) {
		npc = (gpr[rs].read32() <= 0) ? branchTarget(pc, simm16) : (pc + 4);
		testEvents(delay);
		return true;
	}

	public boolean doBGTZ(final int rs, final int simm16, final boolean delay) {
		npc = (gpr[rs].read32() > 0) ? branchTarget(pc, simm16) : (pc + 4);
		testEvents(delay);
		return true;
	}

	public boolean doBEQL(final int rs, final int rt, final int simm16, final boolean delay) {
		if (gpr[rs].read32() == gpr[rt].read32()) {
			npc = branchTarget(pc, simm16);
			testEvents(delay);
			return true;
		} else {
			pc += 4;
			testEvents(delay);
		}
		return false;
	}

	public boolean doBNEL(final int rs, final int rt, final int simm16, final boolean delay) {
		if (gpr[rs].read32() != gpr[rt].read32()) {
			npc = branchTarget(pc, simm16);
			testEvents(delay);
			return true;
		} else {
			pc += 4;
			testEvents(delay);
		}
		return false;
	}

	public boolean doBLEZL(final int rs, final int simm16, final boolean delay) {
		if (gpr[rs].read32() <= 0) {
			npc = branchTarget(pc, simm16);
			testEvents(delay);
			return true;
		} else {
			pc += 4;
			testEvents(delay);
		}
		return false;
	}

	public boolean doBGTZL(final int rs, final int simm16, final boolean delay) {
		if (gpr[rs].read32() > 0) {
			npc = branchTarget(pc, simm16);
			testEvents(delay);
			return true;
		} else {
			pc += 4;
			testEvents(delay);
		}
		return false;
	}

	public boolean doBC0F(final int offset, final boolean delay) {
		if (!cpcond0()) {
			npc = branchTarget(pc, offset);
			testEvents(delay);
			return true;
		}
		testEvents(delay);
		return false;
	}

	public boolean doBC0T(final int offset, final boolean delay) {
		if (cpcond0()) {
			npc = branchTarget(pc, offset);
			testEvents(delay);
			return true;
		}
		testEvents(delay);
		return false;
	}

	public boolean doBC0FL(final int offset, final boolean delay) {
		if (!cpcond0()) {
			npc = branchTarget(pc, offset);
			testEvents(delay);
			return true;
		} else {
			pc += 4;
			testEvents(delay);
		}
		return false;
	}

	public boolean doBC0TL(final int offset, final boolean delay) {
		if (cpcond0()) {
			npc = branchTarget(pc, offset);
			testEvents(delay);
			return true;
		} else {
			pc += 4;
			testEvents(delay);
		}
		return false;
	}
}
