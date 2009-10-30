package com.jps2.core.cpu.ee;

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
import com.jps2.core.cpu.ee.state.CpuState;

public class EEInstructions {

	public static final Instruction PREF = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "PREF";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			// nothing to do
		}

	};
	public static final Instruction NOP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "NOP";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			// nothing to do
		}

	};
	public static final Instruction SYSCALL = new Instruction(NO_FLAGS) {

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
	public static final Instruction ERET = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ERET";
		}

		@Override
		public final String category() {
			return "MIPS III";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			// TODO
		}

	};
	public static final Instruction BREAK = new Instruction(NO_FLAGS) {

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
			final int imm20 = (insn >> 6) & 0xFFFFF;
			System.err.println(imm20);
			// TODO

		}

	};
	public static final Instruction SYNC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SYNC";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			System.err.println(insn);
			// TODO
		}

	};
	public static final Instruction HALT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "HALT";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			// TODO
		}

	};
	public static final Instruction MFIC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MFIC";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 21) & 31;

		}

	};
	public static final Instruction MTIC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MTIC";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 21) & 31;
			// TODO
		}

	};
	public static final Instruction MFSA = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MFSA";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 21) & 31;
			// TODO
		}

	};

	public static final Instruction ADD = new Instruction(NO_FLAGS) {

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
			decodeRsRtRd(insn);

			cpu.doADD(rd, rs, rt, insn, delay);
		}

	};
	public static final Instruction DADD = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DADD";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			// just ignore overflow exception as it is useless
			cpu.doDADD(rd, rs, rt, insn, delay);
		}

	};
	public static final Instruction ADDU = new Instruction(NO_FLAGS) {

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
	public static final Instruction DADDU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DADDU";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doDADDU(rd, rs, rt);

		}

	};
	public static final Instruction ADDI = new Instruction(NO_FLAGS) {

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

			// just ignore overflow exception as it is useless
			cpu.doADDIU(rt, rs, (short) imm16);

		}

	};
	public static final Instruction ADDIU = new Instruction(NO_FLAGS) {

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
	public static final Instruction AND = new Instruction(NO_FLAGS) {

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
	public static final Instruction ANDI = new Instruction(NO_FLAGS) {

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
	public static final Instruction NOR = new Instruction(NO_FLAGS) {

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
	public static final Instruction OR = new Instruction(NO_FLAGS) {

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
	public static final Instruction ORI = new Instruction(NO_FLAGS) {

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
	public static final Instruction XOR = new Instruction(NO_FLAGS) {

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
	public static final Instruction XORI = new Instruction(NO_FLAGS) {

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
	public static final Instruction SLL = new Instruction(NO_FLAGS) {

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
			decodeRtRdSa(insn);

			cpu.doSLL(rd, rt, sa);

		}

	};
	public static final Instruction DSLL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSLL";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRtRdSa(insn);

			cpu.doDSLL(rd, rt, sa);

		}

	};
	public static final Instruction DSLL32 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSLL32";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRtRdSa(insn);

			cpu.doDSLL32(rd, rt, sa);

		}

	};
	public static final Instruction SLLV = new Instruction(NO_FLAGS) {

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
	public static final Instruction DSLLV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSLLV";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doDSLLV(rd, rt, rs);

		}

	};
	public static final Instruction SRA = new Instruction(NO_FLAGS) {

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
			decodeRtRdSa(insn);

			cpu.doSRA(rd, rt, sa);

		}

	};
	public static final Instruction DSRA = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSRA";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRtRdSa(insn);

			cpu.doDSRA(rd, rt, sa);

		}

	};
	public static final Instruction DSRA32 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSRA32";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRtRdSa(insn);

			cpu.doDSRA32(rd, rt, sa);

		}

	};
	public static final Instruction SRAV = new Instruction(NO_FLAGS) {

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

	public static final Instruction DSRAV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSRAV";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doDSRAV(rd, rt, rs);

		}

	};
	public static final Instruction SRL = new Instruction(NO_FLAGS) {

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
			decodeRtRdSa(insn);

			cpu.doSRL(rd, rt, sa);

		}

	};
	public static final Instruction DSRL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSRL";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRtRdSa(insn);

			cpu.doDSRL(rd, rt, sa);

		}

	};

	public static final Instruction DSRL32 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSRL32";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRtRdSa(insn);

			cpu.doDSRL32(rd, rt, sa);

		}

	};
	public static final Instruction SRLV = new Instruction(NO_FLAGS) {

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

	public static final Instruction DSRLV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSRLV";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doDSRLV(rd, rt, rs);

		}

	};

	public static final Instruction SLT = new Instruction(NO_FLAGS) {

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
	public static final Instruction SLTI = new Instruction(NO_FLAGS) {

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
	public static final Instruction SLTU = new Instruction(NO_FLAGS) {

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
	public static final Instruction TEQ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TEQ";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doTEQ(rs, rt, insn, delay);

		}

	};
	public static final Instruction TNE = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TNE";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doTNE(rs, rt, insn, delay);

		}

	};

	public static final Instruction TNEI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TNEI";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final short imm16 = (short) ((insn >> 0) & 65535);
			final int rs = (insn >> 21) & 31;

			cpu.doTNEI(rs, imm16, insn, delay);

		}

	};
	public static final Instruction TGE = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TGE";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doTGE(rs, rt, insn, delay);

		}

	};

	public static final Instruction TGEI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TGEI";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final short imm16 = (short) ((insn >> 0) & 65535);
			final int rs = (insn >> 21) & 31;

			cpu.doTGEI(rs, imm16, insn, delay);

		}

	};

	public static final Instruction TGEIU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TGEIU";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final short imm16 = (short) ((insn >> 0) & 65535);
			final int rs = (insn >> 21) & 31;

			cpu.doTGEIU(rs, imm16, insn, delay);

		}

	};

	public static final Instruction TGEU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TGEU";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doTGEU(rs, rt, insn, delay);

		}

	};

	public static final Instruction TLT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TLT";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doTLT(rs, rt, insn, delay);
		}
	};

	public static final Instruction TLTI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TLTI";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final short imm16 = (short) ((insn >> 0) & 65535);
			final int rs = (insn >> 21) & 31;

			cpu.doTLTI(rs, imm16, insn, delay);
		}
	};

	public static final Instruction TLTIU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TLTIU";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final short imm16 = (short) ((insn >> 0) & 65535);
			final int rs = (insn >> 21) & 31;

			cpu.doTLTIU(rs, imm16, insn, delay);
		}
	};

	public static final Instruction TLTU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TLTU";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doTLTU(rs, rt, insn, delay);
		}
	};

	public static final Instruction SLTIU = new Instruction(NO_FLAGS) {

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
	public static final Instruction SUB = new Instruction(NO_FLAGS) {

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
	public static final Instruction DSUB = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSUB";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			// just ignore overflow exception as it is useless
			cpu.doDSUB(rd, rs, rt, insn, delay);
		}

	};
	public static final Instruction SUBU = new Instruction(NO_FLAGS) {

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
	public static final Instruction DSUBU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DSUBU";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doDSUBU(rd, rs, rt);

		}

	};
	public static final Instruction LUI = new Instruction(NO_FLAGS) {

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
	public static final Instruction SEB = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SEB";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doSEB(rd, rt);

		}

	};
	public static final Instruction SEH = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SEH";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doSEH(rd, rt);

		}

	};
	public static final Instruction BITREV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "BITREV";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doBITREV(rd, rt);

		}

	};
	public static final Instruction WSBH = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "WSBH";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doWSBH(rd, rt);

		}

	};
	public static final Instruction WSBW = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "WSBW";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doWSBW(rd, rt);

		}

	};
	public static final Instruction MOVZ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MOVZ";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);
			cpu.doMOVZ(rd, rs, rt);
		}
	};
	public static final Instruction MOVT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MOVT";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int cc = (insn >> 18) & 7;
			final int rs = (insn >> 21) & 31;
			cpu.doMOVT(rd, rs, cc);
		}
	};
	public static final Instruction MOVF = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MOVF";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int cc = (insn >> 18) & 7;
			final int rs = (insn >> 21) & 31;
			cpu.doMOVF(rd, rs, cc);
		}
	};
	public static final Instruction MOVN = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MOVN";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doMOVN(rd, rs, rt);

		}

	};
	public static final Instruction MAX = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MAX";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doMAX(rd, rs, rt);

		}

	};
	public static final Instruction MIN = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MIN";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtRd(insn);

			cpu.doMIN(rd, rs, rt);

		}

	};
	public static final Instruction CLZ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CLZ";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doCLZ(rd, rs);

		}

	};
	public static final Instruction CLO = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CLO";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rd = (insn >> 11) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doCLO(rd, rs);

		}

	};
	public static final Instruction EXT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "EXT";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int lsb = (insn >> 6) & 31;
			final int msb = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doEXT(rt, rs, lsb, msb);

		}

	};
	public static final Instruction INS = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "INS";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int lsb = (insn >> 6) & 31;
			final int msb = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doINS(rt, rs, lsb, msb);

		}

	};
	public static final Instruction MULT = new Instruction(NO_FLAGS) {

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

	public static final Instruction DMULT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DMULT";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doDMULT(rs, rt);

		}

	};
	public static final Instruction DMULTU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DMULTU";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doDMULTU(rs, rt);

		}

	};
	public static final Instruction MULTU = new Instruction(NO_FLAGS) {

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
	public static final Instruction MADD = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MADD";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doMADD(rs, rt);

		}

	};
	public static final Instruction MADDU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MADDU";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doMADDU(rs, rt);

		}

	};
	public static final Instruction MSUB = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MSUB";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doMSUB(rs, rt);

		}

	};
	public static final Instruction MSUBU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MSUBU";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doMSUBU(rs, rt);

		}

	};
	public static final Instruction DIV = new Instruction(NO_FLAGS) {

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
	public static final Instruction DIVU = new Instruction(NO_FLAGS) {

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
	public static final Instruction DDIV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DDIV";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doDDIV(rs, rt);

		}

	};

	public static final Instruction DDIVU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DDIVU";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int rt = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doDDIVU(rs, rt);

		}

	};
	public static final Instruction MFHI = new Instruction(NO_FLAGS) {

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
	public static final Instruction MFLO = new Instruction(NO_FLAGS) {

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
	public static final Instruction MTHI = new Instruction(NO_FLAGS) {

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
	public static final Instruction MTLO = new Instruction(NO_FLAGS) {

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
	public static final Instruction BEQ = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

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
	// TODO MFC2
	public static final Instruction BEQL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BEQL";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			if (cpu.doBEQL(rs, rt, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BGEZ = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction BGEZAL = new Instruction(
			FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

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
	public static final Instruction BGEZALL = new Instruction(
			FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

		@Override
		public final String name() {
			return "BGEZALL";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBGEZALL(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BGEZL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BGEZL";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBGEZL(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BGTZ = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction BGTZL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BGTZL";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBGTZL(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BLEZ = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction BLEZL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BLEZL";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBLEZL(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BLTZ = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction BLTZAL = new Instruction(
			FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

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
	public static final Instruction BLTZALL = new Instruction(
			FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

		@Override
		public final String name() {
			return "BLTZALL";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBLTZALL(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BLTZL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BLTZL";
		}

		@Override
		public final String category() {
			return "MIPS I";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int rs = (insn >> 21) & 31;

			if (cpu.doBLTZL(rs, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BNE = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction BNEL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BNEL";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			if (cpu.doBNEL(rs, rt, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction J = new Instruction(FLAG_HAS_DELAY_SLOT
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
	public static final Instruction JAL = new Instruction(
			FLAGS_LINK_INSTRUCTION | FLAG_IS_JUMPING) {

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
	public static final Instruction JALR = new Instruction(FLAG_HAS_DELAY_SLOT) {

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
	public static final Instruction JR = new Instruction(FLAG_HAS_DELAY_SLOT
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
	public static final Instruction BC1F = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BC1F";
		}

		@Override
		public final String category() {
			return "MIPS I/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;

			if (cpu.doBC1F((short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BC1T = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BC1T";
		}

		@Override
		public final String category() {
			return "MIPS I/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;

			if (cpu.doBC1T((short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BC1FL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BC1FL";
		}

		@Override
		public final String category() {
			return "MIPS II/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;

			if (cpu.doBC1FL((short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BC1TL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BC1TL";
		}

		@Override
		public final String category() {
			return "MIPS II/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;

			if (cpu.doBC1TL((short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BVF = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BVF";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int imm3 = (insn >> 18) & 7;

			if (cpu.doBVF(imm3, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BVT = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BVT";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int imm3 = (insn >> 18) & 7;

			if (cpu.doBVT(imm3, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BVFL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BVFL";
		}

		@Override
		public final String category() {
			return "MIPS II/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int imm3 = (insn >> 18) & 7;

			if (cpu.doBVFL(imm3, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction BVTL = new Instruction(
			FLAGS_BRANCH_INSTRUCTION) {

		@Override
		public final String name() {
			return "BVTL";
		}

		@Override
		public final String category() {
			return "MIPS II/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int imm3 = (insn >> 18) & 7;

			if (cpu.doBVTL(imm3, (short) imm16))
				cpu.processor.interpretDelayslot();

		}

	};
	public static final Instruction LB = new Instruction(NO_FLAGS) {

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
	public static final Instruction LBU = new Instruction(NO_FLAGS) {

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
	public static final Instruction LH = new Instruction(NO_FLAGS) {

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
	public static final Instruction LHU = new Instruction(NO_FLAGS) {

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
	public static final Instruction LW = new Instruction(NO_FLAGS) {

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
	public static final Instruction LWL = new Instruction(NO_FLAGS) {

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
	public static final Instruction LWR = new Instruction(NO_FLAGS) {

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
	public static final Instruction LDR = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LDR";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doLDR(rt, rs, (short) imm16);

		}

	};
	public static final Instruction SB = new Instruction(NO_FLAGS) {

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
	public static final Instruction SH = new Instruction(NO_FLAGS) {

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
	public static final Instruction SW = new Instruction(NO_FLAGS) {

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
	public static final Instruction SWL = new Instruction(NO_FLAGS) {

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
	public static final Instruction SWR = new Instruction(NO_FLAGS) {

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
	public static final Instruction LL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LL";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doLL(rt, rs, (short) imm16);

		}

	};
	public static final Instruction LWC1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LWC1";
		}

		@Override
		public final String category() {
			return "MIPS I/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int ft = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doLWC1(ft, rs, (short) imm16);

		}

	};
	public static final Instruction LWXC1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LWXC1";
		}

		@Override
		public final String category() {
			return "MIPS II/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int base = (insn >> 21) & 31;
			final int index = (insn >> 16) & 31;

			cpu.doLWXC1(base, index, fd);

		}

	};
	public static final Instruction LVS = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LVS";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vt2 = (insn >> 0) & 3;
			final int imm14 = (insn >> 2) & 16383;
			final int vt5 = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doLVS((vt5 + (vt2 << 32)), rs, (short) (imm14 << 2));

		}

	};
	public static final Instruction LVLQ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LVLQ";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vt1 = (insn >> 0) & 1;
			final int imm14 = (insn >> 2) & 16383;
			final int vt5 = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doLVLQ((vt5 + (vt1 << 32)), rs, (short) (imm14 << 2));

		}

	};
	public static final Instruction LVRQ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LVRQ";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vt1 = (insn >> 0) & 1;
			final int imm14 = (insn >> 2) & 16383;
			final int vt5 = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doLVRQ((vt5 + (vt1 << 32)), rs, (short) (imm14 << 2));

		}

	};
	public static final Instruction LVQ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "LVQ";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vt1 = (insn >> 0) & 1;
			final int imm14 = (insn >> 2) & 16383;
			final int vt5 = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doLVQ((vt5 + (vt1 << 32)), rs, (((short) imm14) << 2));

		}

	};
	public static final Instruction SC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SC";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeRsRtImm16(insn);

			cpu.doSC(rt, rs, (short) imm16);

		}

	};
	public static final Instruction SWC1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SWC1";
		}

		@Override
		public final String category() {
			return "MIPS I/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int ft = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doSWC1(ft, rs, (short) imm16);

		}

	};
	public static final Instruction SVS = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SVS";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vt2 = (insn >> 0) & 3;
			final int imm14 = (insn >> 2) & 16383;
			final int vt5 = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doSVS((vt5 + (vt2 << 32)), rs, (((short) imm14) << 2));

		}

	};
	public static final Instruction SVLQ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SVLQ";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vt1 = (insn >> 0) & 1;
			final int imm14 = (insn >> 2) & 16383;
			final int vt5 = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doSVLQ((vt5 + (vt1 << 32)), rs, (((short) imm14) << 2));

		}

	};
	public static final Instruction SVRQ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SVRQ";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vt1 = (insn >> 0) & 1;
			final int imm14 = (insn >> 2) & 16383;
			final int vt5 = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doSVRQ((vt5 + (vt1 << 32)), rs, (((short) imm14) << 2));

		}

	};
	public static final Instruction SVQ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SVQ";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vt1 = (insn >> 0) & 1;
			final int imm14 = (insn >> 2) & 16383;
			final int vt5 = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doSVQ((vt5 + (vt1 << 32)), rs, (((short) imm14) << 2));

		}

	};
	public static final Instruction SWB = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SWB";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vt1 = (insn >> 0) & 1;
			final int imm14 = (insn >> 2) & 16383;
			final int vt5 = (insn >> 16) & 31;
			final int rs = (insn >> 21) & 31;

			cpu.doSVQ((vt5 + (vt1 << 32)), rs, (((short) imm14) << 2));

		}

	};
	public static final Instruction ADD_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ADD.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;
			final int ft = (insn >> 16) & 31;

			cpu.doADDS(fd, fs, ft);

		}

	};
	public static final Instruction SUB_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SUB.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;
			final int ft = (insn >> 16) & 31;

			cpu.doSUBS(fd, fs, ft);

		}

	};
	public static final Instruction MUL_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MUL.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;
			final int ft = (insn >> 16) & 31;

			cpu.doMULS(fd, fs, ft);

		}

	};
	public static final Instruction DIV_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DIV.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;
			final int ft = (insn >> 16) & 31;

			cpu.doDIVS(fd, fs, ft);

		}

	};
	public static final Instruction SQRT_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "SQRT.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doSQRTS(fd, fs);

		}

	};
	public static final Instruction ABS_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ABS.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doABSS(fd, fs);

		}

	};
	public static final Instruction MOV_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MOV.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doMOVS(fd, fs);

		}

	};
	public static final Instruction NEG_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "NEG.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doNEGS(fd, fs);

		}

	};
	public static final Instruction ROUND_W_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "ROUND.W.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doROUNDWS(fd, fs);

		}

	};
	public static final Instruction TRUNC_W_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TRUNC.W.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doTRUNCWS(fd, fs);

		}

	};
	public static final Instruction CEIL_W_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CEIL.W.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doCEILWS(fd, fs);

		}

	};
	public static final Instruction FLOOR_W_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "FLOOR.W.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doFLOORWS(fd, fs);

		}

	};
	public static final Instruction CVT_S_W = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CVT.S.W";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doCVTSW(fd, fs);

		}

	};
	public static final Instruction CVT_W_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CVT.W.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fd = (insn >> 6) & 31;
			final int fs = (insn >> 11) & 31;

			cpu.doCVTWS(fd, fs);

		}

	};
	public static final Instruction C_COND_S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "C.COND.S";
		}

		@Override
		public final String category() {
			return "FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int fcond = (insn >> 0) & 15;
			final int fs = (insn >> 11) & 31;
			final int ft = (insn >> 16) & 31;

			cpu.doCCONDS(fs, ft, fcond);

		}

	};
	public static final Instruction MFC1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MFC1";
		}

		@Override
		public final String category() {
			return "MIPS I/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c1dr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doMFC1(rt, c1dr);

		}

	};
	public static final Instruction DMFC1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DMFC1";
		}

		@Override
		public final String category() {
			return "MIPS II/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c1dr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doDMFC1(rt, c1dr);

		}

	};
	public static final Instruction CFC1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CFC1";
		}

		@Override
		public final String category() {
			return "MIPS I/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c1cr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doCFC1(rt, c1cr);

		}

	};
	public static final Instruction MTC1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MTC1";
		}

		@Override
		public final String category() {
			return "MIPS I/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c1dr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doMTC1(rt, c1dr);

		}

	};
	public static final Instruction DMTC1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DMTC1";
		}

		@Override
		public final String category() {
			return "MIPS II/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c1dr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doDMTC1(rt, c1dr);

		}

	};
	public static final Instruction CTC1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CTC1";
		}

		@Override
		public final String category() {
			return "MIPS I/FPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c1cr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

			cpu.doCFC1(rt, c1cr);

		}

	};
	public static final Instruction MFC0 = new Instruction(NO_FLAGS) {

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

		}

	};
	public static final Instruction DMFC0 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DMFC0";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c0dr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;
			// TODO
			throw new RuntimeException();
		}
	};
	public static final Instruction CFC0 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CFC0";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c0cr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

		}

	};
	public static final Instruction MTC0 = new Instruction(NO_FLAGS) {

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

		}

	};
	public static final Instruction DMTC0 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DMTC0";
		}

		@Override
		public final String category() {
			return "MIPS II";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c0dr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;
			// TODO
			throw new RuntimeException();
		}

	};
	public static final Instruction CTC0 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "CTC0";
		}

		@Override
		public final String category() {
			return "ALLEGREX";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int c0cr = (insn >> 11) & 31;
			final int rt = (insn >> 16) & 31;

		}

	};
	public static final Instruction VADD = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VADD";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVADD(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VSUB = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSUB";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVSUB(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VSBN = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSBN";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVSBN(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VDIV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VDIV";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVDIV(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VMUL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMUL";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVMUL(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VDOT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VDOT";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVDOT(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VSCL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSCL";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVSCL(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VHDP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VHDP";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVHDP(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VDET = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VDET";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVDET(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VCRS = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VCRS";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVCRS(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction MFV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MFV";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm7 = (insn >> 0) & 127;
			final int rt = (insn >> 16) & 31;

			cpu.doMFV(rt, imm7);

		}

	};
	public static final Instruction MFVC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MFVC";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm7 = (insn >> 0) & 127;
			final int rt = (insn >> 16) & 31;

			cpu.doMFVC(rt, imm7);

		}

	};
	public static final Instruction MTV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MTV";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm7 = (insn >> 0) & 127;
			final int rt = (insn >> 16) & 31;

			cpu.doMTV(rt, imm7);

		}

	};
	public static final Instruction MTVC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "MTVC";
		}

		@Override
		public final String category() {
			return "MIPS I/VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm7 = (insn >> 0) & 127;
			final int rt = (insn >> 16) & 31;

			cpu.doMTVC(rt, imm7);

		}

	};
	public static final Instruction VCMP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VCMP";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm3 = (insn >> 0) & 7;
			final int one = (insn >> 7) & 1;
			final int vs = (insn >> 8) & 127;
			final int two = (insn >> 15) & 1;
			final int vt = (insn >> 16) & 127;

			cpu.doVCMP(1 + one + (two << 1), vs, vt, imm3);

		}

	};
	public static final Instruction VMIN = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMIN";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVMIN(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VMAX = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMAX";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVMAX(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VSCMP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSCMP";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVSCMP(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VSGE = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSGE";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVSGE(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VSLT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSLT";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVSLT(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VMOV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMOV";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVMOV(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VABS = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VABS";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVABS(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VNEG = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VNEG";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVNEG(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VIDT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VIDT";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;

			cpu.doVIDT(1 + one + (two << 1), vd);

		}

	};
	public static final Instruction VSAT0 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSAT0";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSAT0(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VSAT1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSAT1";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSAT1(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VZERO = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VZERO";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;

			cpu.doVZERO(1 + one + (two << 1), vd);

		}

	};
	public static final Instruction VONE = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VONE";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;

			cpu.doVONE(1 + one + (two << 1), vd);

		}

	};
	public static final Instruction VRCP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VRCP";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVRCP(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VRSQ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VRSQ";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVRSQ(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VSIN = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSIN";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSIN(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VCOS = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VCOS";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVCOS(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VEXP2 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VEXP2";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVEXP2(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VLOG2 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VLOG2";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVLOG2(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VSQRT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSQRT";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSQRT(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VASIN = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VASIN";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVASIN(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VNRCP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VNRCP";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVNRCP(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VNSIN = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VNSIN";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVNSIN(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VREXP2 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VREXP2";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVREXP2(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VRNDS = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VRNDS";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int one = (insn >> 7) & 1;
			final int vs = (insn >> 8) & 127;
			final int two = (insn >> 15) & 1;

			cpu.doVRNDS(1 + one + (two << 1), vs);

		}

	};
	public static final Instruction VRNDI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VRNDI";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;

			cpu.doVRNDI(1 + one + (two << 1), vd);

		}

	};
	public static final Instruction VRNDF1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VRNDF1";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;

			cpu.doVRNDF1(1 + one + (two << 1), vd);

		}

	};
	public static final Instruction VRNDF2 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VRNDF2";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;

			cpu.doVRNDF2(1 + one + (two << 1), vd);

		}

	};
	public static final Instruction VF2H = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VF2H";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVF2H(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VH2F = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VH2F";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVH2F(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VSBZ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSBZ";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSBZ(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VLGB = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VLGB";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVLGB(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VUC2I = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VUC2I";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVUC2I(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VC2I = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VC2I";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVC2I(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VUS2I = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VUS2I";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVUS2I(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VS2I = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VS2I";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVS2I(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VI2UC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VI2UC";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVI2UC(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VI2C = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VI2C";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVI2C(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VI2US = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VI2US";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVI2US(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VI2S = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VI2S";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVI2S(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VSRT1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSRT1";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSRT1(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VSRT2 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSRT2";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSRT2(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VBFY1 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VBFY1";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVBFY1(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VBFY2 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VBFY2";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVBFY2(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VOCP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VOCP";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVOCP(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VSOCP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSOCP";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSOCP(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VFAD = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VFAD";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVFAD(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VAVG = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VAVG";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVAVG(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VSRT3 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSRT3";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSRT3(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VSRT4 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSRT4";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVSRT4(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VMFVC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMFVC";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int imm7 = (insn >> 8) & 127;

			cpu.doVMFVC(vd, imm7);

		}

	};
	public static final Instruction VMTVC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMTVC";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm7 = (insn >> 0) & 127;
			final int vs = (insn >> 8) & 127;

			cpu.doVMTVC(vs, imm7);

		}

	};
	public static final Instruction VT4444 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VT4444";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVT4444(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VT5551 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VT5551";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVT5551(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VT5650 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VT5650";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVT5650(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VCST = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VCST";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;
			final int imm5 = (insn >> 16) & 31;

			cpu.doVCST(1 + one + (two << 1), vd, imm5);

		}

	};
	public static final Instruction VF2IN = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VF2IN";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int imm5 = (insn >> 16) & 31;

			cpu.doVF2IN(1 + one + (two << 1), vd, vs, imm5);

		}

	};
	public static final Instruction VF2IZ = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VF2IZ";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int imm5 = (insn >> 16) & 31;

			cpu.doVF2IZ(1 + one + (two << 1), vd, vs, imm5);

		}

	};
	public static final Instruction VF2IU = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VF2IU";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int imm5 = (insn >> 16) & 31;

			cpu.doVF2IU(1 + one + (two << 1), vd, vs, imm5);

		}

	};
	public static final Instruction VF2ID = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VF2ID";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int imm5 = (insn >> 16) & 31;

			cpu.doVF2ID(1 + one + (two << 1), vd, vs, imm5);

		}

	};
	public static final Instruction VI2F = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VI2F";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int imm5 = (insn >> 16) & 31;

			cpu.doVI2F(1 + one + (two << 1), vd, vs, imm5);

		}

	};
	public static final Instruction VCMOVT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VCMOVT";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int imm3 = (insn >> 16) & 7;

			cpu.doVCMOVT(1 + one + (two << 1), imm3, vd, vs);

		}

	};
	public static final Instruction VCMOVF = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VCMOVF";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int imm3 = (insn >> 16) & 7;

			cpu.doVCMOVF(1 + one + (two << 1), imm3, vd, vs);

		}

	};
	public static final Instruction VWBN = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VWBN";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int imm8 = (insn >> 16) & 255;

			cpu.doVWBN(1 + one + (two << 1), vd, vs, imm8);

		}

	};
	public static final Instruction VPFXS = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VPFXS";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int swzx = (insn >> 0) & 3;
			final int swzy = (insn >> 2) & 3;
			final int swzz = (insn >> 4) & 3;
			final int swzw = (insn >> 6) & 3;
			final int absx = (insn >> 8) & 1;
			final int absy = (insn >> 9) & 1;
			final int absz = (insn >> 10) & 1;
			final int absw = (insn >> 11) & 1;
			final int cstx = (insn >> 12) & 1;
			final int csty = (insn >> 13) & 1;
			final int cstz = (insn >> 14) & 1;
			final int cstw = (insn >> 15) & 1;
			final int negx = (insn >> 16) & 1;
			final int negy = (insn >> 17) & 1;
			final int negz = (insn >> 18) & 1;
			final int negw = (insn >> 19) & 1;

			cpu.doVPFXS(negw, negz, negy, negx, cstw, cstz, csty, cstx, absw,
					absz, absy, absx, swzw, swzz, swzy, swzx);

		}

	};
	public static final Instruction VPFXT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VPFXT";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int swzx = (insn >> 0) & 3;
			final int swzy = (insn >> 2) & 3;
			final int swzz = (insn >> 4) & 3;
			final int swzw = (insn >> 6) & 3;
			final int absx = (insn >> 8) & 1;
			final int absy = (insn >> 9) & 1;
			final int absz = (insn >> 10) & 1;
			final int absw = (insn >> 11) & 1;
			final int cstx = (insn >> 12) & 1;
			final int csty = (insn >> 13) & 1;
			final int cstz = (insn >> 14) & 1;
			final int cstw = (insn >> 15) & 1;
			final int negx = (insn >> 16) & 1;
			final int negy = (insn >> 17) & 1;
			final int negz = (insn >> 18) & 1;
			final int negw = (insn >> 19) & 1;

			cpu.doVPFXT(negw, negz, negy, negx, cstw, cstz, csty, cstx, absw,
					absz, absy, absx, swzw, swzz, swzy, swzx);

		}

	};
	public static final Instruction VPFXD = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VPFXD";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int satx = (insn >> 0) & 3;
			final int saty = (insn >> 2) & 3;
			final int satz = (insn >> 4) & 3;
			final int satw = (insn >> 6) & 3;
			final int mskx = (insn >> 8) & 1;
			final int msky = (insn >> 9) & 1;
			final int mskz = (insn >> 10) & 1;
			final int mskw = (insn >> 11) & 1;

			cpu.doVPFXD(mskw, mskz, msky, mskx, satw, satz, saty, satx);

		}

	};
	public static final Instruction VIIM = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VIIM";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int vd = (insn >> 16) & 127;

			cpu.doVIIM(vd, imm16);

		}

	};
	public static final Instruction VFIM = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VFIM";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int imm16 = (insn >> 0) & 65535;
			final int vd = (insn >> 16) & 127;

			cpu.doVFIM(vd, imm16);

		}

	};
	public static final Instruction VMMUL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMMUL";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVMMUL(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VHTFM2 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VHTFM2";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int vs = (insn >> 8) & 127;
			final int vt = (insn >> 16) & 127;

			cpu.doVHTFM2(vd, vs, vt);

		}

	};
	public static final Instruction VTFM2 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VTFM2";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int vs = (insn >> 8) & 127;
			final int vt = (insn >> 16) & 127;

			cpu.doVTFM2(vd, vs, vt);

		}

	};
	public static final Instruction VHTFM3 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VHTFM3";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int vs = (insn >> 8) & 127;
			final int vt = (insn >> 16) & 127;

			cpu.doVHTFM3(vd, vs, vt);

		}

	};
	public static final Instruction VTFM3 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VTFM3";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int vs = (insn >> 8) & 127;
			final int vt = (insn >> 16) & 127;

			cpu.doVTFM3(vd, vs, vt);

		}

	};
	public static final Instruction VHTFM4 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VHTFM4";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int vs = (insn >> 8) & 127;
			final int vt = (insn >> 16) & 127;

			cpu.doVHTFM4(vd, vs, vt);

		}

	};
	public static final Instruction VTFM4 = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VTFM4";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int vs = (insn >> 8) & 127;
			final int vt = (insn >> 16) & 127;

			cpu.doVTFM4(vd, vs, vt);

		}

	};
	public static final Instruction VMSCL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMSCL";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int vt = (insn >> 16) & 127;

			cpu.doVMSCL(1 + one + (two << 1), vd, vs, vt);

		}

	};
	public static final Instruction VQMUL = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VQMUL";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int vs = (insn >> 8) & 127;
			final int vt = (insn >> 16) & 127;

			cpu.doVQMUL(vd, vs, vt);

		}

	};
	public static final Instruction VMMOV = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMMOV";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);

			cpu.doVMMOV(1 + one + (two << 1), vd, vs);

		}

	};
	public static final Instruction VMIDT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMIDT";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;

			cpu.doVMIDT(1 + one + (two << 1), vd);

		}

	};
	public static final Instruction VMZERO = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMZERO";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;

			cpu.doVMZERO(1 + one + (two << 1), vd);

		}

	};
	public static final Instruction VMONE = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VMONE";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			final int vd = (insn >> 0) & 127;
			final int one = (insn >> 7) & 1;
			final int two = (insn >> 15) & 1;

			cpu.doVMONE(1 + one + (two << 1), vd);

		}

	};
	public static final Instruction VROT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VROT";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			decodeTwoVsOneVd(insn);
			final int imm5 = (insn >> 16) & 31;

			cpu.doVROT(1 + one + (two << 1), vd, vs, imm5);

		}

	};
	public static final Instruction VNOP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VNOP";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {

		}

	};
	public static final Instruction VFLUSH = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VFLUSH";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {

		}

	};
	public static final Instruction VSYNC = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "VSYNC";
		}

		@Override
		public final String category() {
			return "VFPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			// TODO
		}

	};

	public static final Instruction DERET = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "DERET";
		}

		@Override
		public final String category() {
			return "PROCESSOR";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			cpu.doDERET();
		}

	};

	public static final Instruction WAIT = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "WAIT";
		}

		@Override
		public final String category() {
			return "PROCESSOR";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			cpu.doWAIT();
		}

	};

	public static final Instruction TLBR = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TLBR";
		}

		@Override
		public final String category() {
			return "CPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			cpu.doTLBR();
		}

	};

	public static final Instruction TLBP = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TLBP";
		}

		@Override
		public final String category() {
			return "CPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			cpu.doTLBP();
		}

	};

	public static final Instruction TLBWI = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TLBWI";
		}

		@Override
		public final String category() {
			return "CPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			cpu.doTLBWI();
		}

	};

	public static final Instruction TLBWR = new Instruction(NO_FLAGS) {

		@Override
		public final String name() {
			return "TLBWR";
		}

		@Override
		public final String category() {
			return "CPU";
		}

		@Override
		public void interpret(final int insn, final boolean delay) {
			cpu.doTLBWR();
		}

	};

	static CpuState cpu;

	public static final void setCpu(final CpuState cpu) {
		EEInstructions.cpu = cpu;
	}
}