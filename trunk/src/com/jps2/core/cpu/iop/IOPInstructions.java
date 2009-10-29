package com.jps2.core.cpu.iop;

import static com.jps2.core.cpu.Common.Instruction.FLAGS_BRANCH_INSTRUCTION;
import static com.jps2.core.cpu.Common.Instruction.FLAGS_LINK_INSTRUCTION;
import static com.jps2.core.cpu.Common.Instruction.FLAG_CANNOT_BE_SPLIT;
import static com.jps2.core.cpu.Common.Instruction.FLAG_ENDS_BLOCK;
import static com.jps2.core.cpu.Common.Instruction.FLAG_HAS_DELAY_SLOT;
import static com.jps2.core.cpu.Common.Instruction.FLAG_IS_BRANCHING;
import static com.jps2.core.cpu.Common.Instruction.FLAG_IS_CONDITIONAL;
import static com.jps2.core.cpu.Common.Instruction.FLAG_IS_JUMPING;
import static com.jps2.core.cpu.Common.Instruction.NO_FLAGS;

import com.jps2.core.cpu.ExcCode;
import com.jps2.core.cpu.Common.Instruction;
import com.jps2.core.cpu.iop.state.CpuState;

public class IOPInstructions {
	static final Instruction SYSCALL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SYSCALL";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			cpu.pc -= 4;
			cpu.processor.processException(ExcCode.SYSCALL, insn, delay);
		}

	};

	static final Instruction BREAK = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "BREAK";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			cpu.pc -= 4;
			cpu.processor.processException(ExcCode.BREAKPOINT, insn, delay);
		}

	};

	static final Instruction ADD = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ADD";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doADD(rd, rs, rt, insn, delay);
		}

	};

	static final Instruction ADDU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ADDU";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doADDU(rd, rs, rt);

		}

	};

	static final Instruction ADDI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ADDI";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			// TODO
			// just ignore overflow exception as it is useless
			cpu.doADDIU(rt, rs, (short) imm16);

		}

	};
	static final Instruction ADDIU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ADDIU";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doADDIU(rt, rs, (short) imm16);

		}

	};
	static final Instruction AND = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "AND";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doAND(rd, rs, rt);

		}

	};
	static final Instruction ANDI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ANDI";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doANDI(rt, rs, imm16);

		}

	};
	static final Instruction NOR = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "NOR";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doNOR(rd, rs, rt);

		}

	};
	static final Instruction OR = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "OR";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doOR(rd, rs, rt);

		}

	};
	static final Instruction ORI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ORI";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doORI(rt, rs, imm16);

		}

	};
	static final Instruction XOR = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "XOR";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doXOR(rd, rs, rt);

		}

	};
	static final Instruction XORI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "XORI";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doXORI(rt, rs, imm16);

		}

	};
	static final Instruction SLL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SLL";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int sa = (insn >> 6) & 31;
			final int rd = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doSLL(rd, rt, sa);

		}

	};

	static final Instruction SLLV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SLLV";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doSLLV(rd, rt, rs);

		}

	};

	static final Instruction SRA = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SRA";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int sa = (insn >> 6) & 31;
			final int rd = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doSRA(rd, rt, sa);

		}

	};

	static final Instruction SRAV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SRAV";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doSRAV(rd, rt, rs);

		}

	};

	static final Instruction SRL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SRL";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int sa = (insn >> 6) & 31;
			final int rd = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doSRL(rd, rt, sa);

		}

	};

	static final Instruction SRLV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SRLV";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doSRLV(rd, rt, rs);

		}

	};

	static final Instruction SLT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SLT";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doSLT(rd, rs, rt);

		}

	};
	static final Instruction SLTI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SLTI";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doSLTI(rt, rs, (short) imm16);

		}

	};
	static final Instruction SLTU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SLTU";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doSLTU(rd, rs, rt);

		}

	};

	static final Instruction SLTIU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SLTIU";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doSLTIU(rt, rs, (short) imm16);

		}

	};
	static final Instruction SUB = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SUB";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doSUB(rd, rs, rt, insn, delay);

		}

	};

	static final Instruction SUBU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SUBU";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);
			cpu.doSUBU(rd, rs, rt, insn, delay);
		}
	};
	static final Instruction LUI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LUI";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rt = (insn >> 16) & 31;

			cpu.doLUI(rt, imm16);

		}

	};

	static final Instruction MULT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MULT";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doMULT(rs, rt);

		}

	};

	static final Instruction MULTU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MULTU";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doMULTU(rs, rt);

		}

	};

	static final Instruction DIV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DIV";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doDIV(rs, rt);

		}

	};
	static final Instruction DIVU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DIVU";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doDIVU(rs, rt);

		}

	};

	static final Instruction MFHI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MFHI";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;

			cpu.doMFHI(rd);

		}

	};
	static final Instruction MFLO = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MFLO";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;

			cpu.doMFLO(rd);

		}

	};
	static final Instruction MTHI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MTHI";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rs = (insn >> 21) & 31;

			cpu.doMTHI(rs);

		}

	};
	static final Instruction MTLO = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MTLO";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rs = (insn >> 21) & 31;

			cpu.doMTLO(rs);

		}

	};
	static final Instruction BEQ = new Instruction(FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BEQ";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			if (cpu.doBEQ(rs, rt, (short) imm16)) {
				cpu.processor.interpretDelayslot();
			}
		}

	};
	static final Instruction BGEZ = new Instruction(FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BGEZ";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBGEZ(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	static final Instruction BGEZAL = new Instruction(FLAGS_LINK_INSTRUCTION
			| FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

		@Override
		public final String name() {
			return "BGEZAL";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBGEZAL(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};

	static final Instruction BGTZ = new Instruction(FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BGTZ";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBGTZ(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	static final Instruction BLEZ = new Instruction(FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BLEZ";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBLEZ(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	static final Instruction BLTZ = new Instruction(FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BLTZ";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBLTZ(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	static final Instruction BLTZAL = new Instruction(FLAGS_LINK_INSTRUCTION
			| FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

		@Override
		public final String name() {
			return "BLTZAL";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBLTZAL(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};

	static final Instruction BNE = new Instruction(FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BNE";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			if (cpu.doBNE(rs, rt, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	static final Instruction J = new Instruction(FLAG_HAS_DELAY_SLOT
			| FLAG_IS_JUMPING | FLAG_CANNOT_BE_SPLIT | FLAG_ENDS_BLOCK) {

		@Override
		public final String name() {
			return "J";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm26 = (insn >> 0) & 67108863;

			if (cpu.doJ(imm26))
				cpu.processor.interpretDelayslot();

		}

	};
	static final Instruction JAL = new Instruction(FLAGS_LINK_INSTRUCTION
			| FLAG_IS_JUMPING) {

		@Override
		public final String name() {
			return "JAL";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm26 = (insn >> 0) & 67108863;

			if (cpu.doJAL(imm26))
				cpu.processor.interpretDelayslot();

		}

	};
	static final Instruction JALR = new Instruction(FLAG_HAS_DELAY_SLOT) {

		@Override
		public final String name() {
			return "JALR";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int rs = (insn >> 21) & 31;

			if (cpu.doJALR(rd, rs))
				cpu.processor.interpretDelayslot();

		}

	};
	static final Instruction JR = new Instruction(FLAG_HAS_DELAY_SLOT
			| FLAG_CANNOT_BE_SPLIT | FLAG_ENDS_BLOCK) {

		@Override
		public final String name() {
			return "JR";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rs = (insn >> 21) & 31;

			if (cpu.doJR(rs))
				cpu.processor.interpretDelayslot();

		}

	};

	static final Instruction LB = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LB";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doLB(rt, rs, (short) imm16);

		}

	};
	static final Instruction LBU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LBU";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doLBU(rt, rs, (short) imm16);

		}

	};
	static final Instruction LH = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LH";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doLH(rt, rs, (short) imm16);

		}

	};
	static final Instruction LHU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LHU";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doLHU(rt, rs, (short) imm16);

		}

	};
	static final Instruction LW = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LW";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doLW(rt, rs, (short) imm16);
		}

	};
	static final Instruction LWL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LWL";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doLWL(rt, rs, (short) imm16);
		}

	};
	static final Instruction LWR = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LWR";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doLWR(rt, rs, (short) imm16);

		}

	};
	static final Instruction SB = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SB";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doSB(rt, rs, (short) imm16);

		}

	};
	static final Instruction SH = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SH";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doSH(rt, rs, (short) imm16);

		}

	};
	static final Instruction SW = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SW";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doSW(rt, rs, (short) imm16);

		}

	};
	static final Instruction SWL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SWL";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);
			cpu.doSWL(rt, rs, (short) imm16);

		}

	};
	static final Instruction SWR = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SWR";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);
			cpu.doSWR(rt, rs, (short) imm16);
		}
	};

	static final Instruction MFC0 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MFC0";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c0dr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;
			cpu.doMFC0(rt, c0dr);
		}
	};

	static final Instruction CFC0 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CFC0";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c0cr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;
			cpu.doCFC0(rt, c0cr);
		}

	};
	static final Instruction MTC0 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MTC0";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c0dr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;
			cpu.doMTC0(rt, c0dr);
		}

	};

	static final Instruction RFE = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "RFE";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			cpu.doRFE();
		}

	};

	static final Instruction CTC0 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CTC0";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c0cr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;
			cpu.doCTC0(rt, c0cr);
		}

	};

	static CpuState cpu;

	public static final void setCpu( CpuState cpu) {
		IOPInstructions.cpu	 = cpu;
	}
}