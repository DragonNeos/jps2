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

	public static final Instruction	PREF		= new Instruction(NO_FLAGS) {

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
														System.err.println("PREF");
														// nothing to do
													}

												};
	public static final Instruction	NOP			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SYSCALL		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	ERET		= new Instruction(NO_FLAGS) {

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
														System.err.println("ERET");
														// TODO
													}

												};
	public static final Instruction	BREAK		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SYNC		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "SYNC";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														// Nothing to do
													}

												};
	public static final Instruction	HALT		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "HALT";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														System.err.println("HALT");
														// TODO
													}

												};
	public static final Instruction	MFIC		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MFIC";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {

														final int rt = (insn >> 21) & 31;

													}

												};

	public static final Instruction	MFSA		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MFSA";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														cpu.doMFSA(rd);
													}

												};

	public static final Instruction	MTSA		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MTSA";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														cpu.doMTSA(rd);
													}

												};

	public static final Instruction	EI			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "EI";
													}

													@Override
													public final String category() {
														return "MIPS I";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doEI(delay);
													}

												};

	public static final Instruction	DI			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "DI";
													}

													@Override
													public final String category() {
														return "MIPS I";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doDI(delay);
													}

												};

	public static final Instruction	ADD			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DADD		= new Instruction(NO_FLAGS) {

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

														// just ignore overflow
														// exception as it is
														// useless
														cpu.doDADD(rd, rs, rt, insn, delay);
													}

												};
	public static final Instruction	ADDU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DADDU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	ADDI		= new Instruction(NO_FLAGS) {

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

														// just ignore overflow
														// exception as it is
														// useless
														cpu.doADDIU(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	DADDI		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "DADDI";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														// just ignore overflow
														// exception as it is
														// useless
														cpu.doDADDIU(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	ADDIU		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	DADDIU		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "DADDIU";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doDADDIU(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	AND			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	ANDI		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	NOR			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	OR			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	ORI			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	XOR			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	XORI		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SLL			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DSLL		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DSLL32		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SLLV		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DSLLV		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SRA			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DSRA		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DSRA32		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SRAV		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	DSRAV		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SRL			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DSRL		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	DSRL32		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SRLV		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	DSRLV		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	SLT			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SLTI		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SLTU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	TEQ			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	TNE			= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TNEI		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TEQI		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "TEQI";
													}

													@Override
													public final String category() {
														return "MIPS II";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														final int rs = (insn >> 21) & 31;

														cpu.doTEQI(rs, imm16, insn, delay);

													}

												};

	public static final Instruction	TGE			= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TGEI		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TGEIU		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TGEU		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TLT			= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TLTI		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TLTIU		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TLTU		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	SLTIU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SUB			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DSUB		= new Instruction(NO_FLAGS) {

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

														// just ignore overflow
														// exception as it is
														// useless
														cpu.doDSUB(rd, rs, rt, insn, delay);
													}

												};
	public static final Instruction	SUBU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DSUBU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	LUI			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SEB			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "SEB";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doSEB(rd, rt);

													}

												};
	public static final Instruction	SEH			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "SEH";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doSEH(rd, rt);

													}

												};
	public static final Instruction	BITREV		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "BITREV";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doBITREV(rd, rt);

													}

												};
	public static final Instruction	WSBH		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "WSBH";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doWSBH(rd, rt);

													}

												};
	public static final Instruction	WSBW		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "WSBW";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doWSBW(rd, rt);

													}

												};
	public static final Instruction	MOVZ		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MOVZ";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doMOVZ(rd, rs, rt);
													}
												};
	public static final Instruction	MOVT		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MOVT";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														final int cc = (insn >> 18) & 7;
														final int rs = (insn >> 21) & 31;
														cpu.doMOVT(rd, rs, cc);
													}
												};
	public static final Instruction	MOVF		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MOVF";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														final int cc = (insn >> 18) & 7;
														final int rs = (insn >> 21) & 31;
														cpu.doMOVF(rd, rs, cc);
													}
												};
	public static final Instruction	MOVN		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MOVN";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doMOVN(rd, rs, rt);

													}

												};
	public static final Instruction	MAX			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MAX";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doMAX(rd, rs, rt);

													}

												};
	public static final Instruction	MIN			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MIN";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doMIN(rd, rs, rt);

													}

												};
	public static final Instruction	CLZ			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "CLZ";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														final int rs = (insn >> 21) & 31;

														cpu.doCLZ(rd, rs);

													}

												};
	public static final Instruction	CLO			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "CLO";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rd = (insn >> 11) & 31;
														final int rs = (insn >> 21) & 31;

														cpu.doCLO(rd, rs);

													}

												};
	public static final Instruction	EXT			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "EXT";
													}

													@Override
													public final String category() {
														return "CPU";
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
	public static final Instruction	INS			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "INS";
													}

													@Override
													public final String category() {
														return "CPU";
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
	public static final Instruction	MULT		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	DMULT		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DMULTU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MULTU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MADD		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MADD";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														final int rs = (insn >> 21) & 31;

														cpu.doMADD(rs, rt);

													}

												};
	public static final Instruction	MADDU		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MADDU";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														final int rs = (insn >> 21) & 31;

														cpu.doMADDU(rs, rt);

													}

												};
	public static final Instruction	MSUB		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MSUB";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														final int rs = (insn >> 21) & 31;

														cpu.doMSUB(rs, rt);

													}

												};
	public static final Instruction	MSUBU		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MSUBU";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														final int rs = (insn >> 21) & 31;

														cpu.doMSUBU(rs, rt);

													}

												};
	public static final Instruction	DIV			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DIVU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DDIV		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	DDIVU		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MFHI		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MFLO		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MTHI		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MTLO		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	BEQ			= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BEQL		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BGEZ		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BGEZAL		= new Instruction(FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

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
	public static final Instruction	BGEZALL		= new Instruction(FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

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
	public static final Instruction	BGEZL		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BGTZ		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BGTZL		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BLEZ		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BLEZL		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BLTZ		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BLTZAL		= new Instruction(FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

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
	public static final Instruction	BLTZALL		= new Instruction(FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

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
	public static final Instruction	BLTZL		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BNE			= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BNEL		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	J			= new Instruction(FLAG_HAS_DELAY_SLOT | FLAG_IS_JUMPING | FLAG_CANNOT_BE_SPLIT | FLAG_ENDS_BLOCK) {

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
	public static final Instruction	JAL			= new Instruction(FLAGS_LINK_INSTRUCTION | FLAG_IS_JUMPING) {

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
	public static final Instruction	JALR		= new Instruction(FLAG_HAS_DELAY_SLOT) {

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
	public static final Instruction	JR			= new Instruction(FLAG_HAS_DELAY_SLOT | FLAG_CANNOT_BE_SPLIT | FLAG_ENDS_BLOCK) {

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
	public static final Instruction	BC1F		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BC1T		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BC1FL		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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
	public static final Instruction	BC1TL		= new Instruction(FLAGS_BRANCH_INSTRUCTION) {

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

	public static final Instruction	LB			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	LBU			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	LH			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	LHU			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	LW			= new Instruction(NO_FLAGS) {

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

	public static final Instruction	LD			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "LD";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLD(rt, rs, (short) imm16);
													}

												};

	public static final Instruction	LWU			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "LWU";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLWU(rt, rs, (short) imm16);
													}

												};
	public static final Instruction	LWL			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	LWR			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	LDR			= new Instruction(NO_FLAGS) {

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

	public static final Instruction	LDL			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "LDL";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLDL(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	LQ			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "LQ";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLQ(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	SQ			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "SQ";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSQ(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	SB			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SH			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SW			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SD			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "SD";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSD(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SWL			= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SWR			= new Instruction(NO_FLAGS) {

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

	public static final Instruction	SDL			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "SDL";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSDL(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	SDR			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "SDR";
													}

													@Override
													public final String category() {
														return "MIPS IV";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSDR(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	LL			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "LL";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLL(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	LWC1		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	LWXC1		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	SC			= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "SC";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSC(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SWC1		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	ADD_S		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SUB_S		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MUL_S		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DIV_S		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	SQRT_S		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	ABS_S		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MOV_S		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	NEG_S		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	ROUND_W_S	= new Instruction(NO_FLAGS) {

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
	public static final Instruction	TRUNC_W_S	= new Instruction(NO_FLAGS) {

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
	public static final Instruction	CEIL_W_S	= new Instruction(NO_FLAGS) {

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
	public static final Instruction	FLOOR_W_S	= new Instruction(NO_FLAGS) {

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
	public static final Instruction	CVT_S_W		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	CVT_W_S		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	C_COND_S	= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MFC1		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DMFC1		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	CFC1		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MTC1		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DMTC1		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	CTC1		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MFC0		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	DMFC0		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	CFC0		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "CFC0";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c0cr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;
														throw new RuntimeException();
													}

												};
	public static final Instruction	MTC0		= new Instruction(NO_FLAGS) {

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
														cpu.doMTC0((insn >> 11) & 31, (insn >> 16) & 31, insn & 0x3F, delay);
													}

												};
	public static final Instruction	DMTC0		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	CTC0		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "CTC0";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c0cr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;
														throw new RuntimeException();
													}

												};

	public static final Instruction	DERET		= new Instruction(NO_FLAGS) {

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
														throw new RuntimeException();
													}

												};

	public static final Instruction	WAIT		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	CACHE		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "CACHE";
													}

													@Override
													public final String category() {
														return "PROCESSOR";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doCACHE(rt, rs, (short) imm16);
													}

												};

	public static final Instruction	TLBR		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TLBP		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TLBWI		= new Instruction(NO_FLAGS) {

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

	public static final Instruction	TLBWR		= new Instruction(NO_FLAGS) {

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
	public static final Instruction	MTSAB		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MTSAB";
													}

													@Override
													public final String category() {
														return "MIPS EE";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														final int rs = (insn >> 21) & 31;

														cpu.doMTSAB(rs, imm16);
													}

												};

	public static final Instruction	MTSAH		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "MTSAH";
													}

													@Override
													public final String category() {
														return "MIPS EE";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														final int rs = (insn >> 21) & 31;

														cpu.doMTSAH(rs, imm16);
													}

												};

	public static final Instruction	LQC2		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "LQC2";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														// TODO
														// cpu.doLQC2();
													}

												};
	public static final Instruction	SQC2		= new Instruction(NO_FLAGS) {

													@Override
													public final String name() {
														return "SQC2";
													}

													@Override
													public final String category() {
														return "CPU";
													}

													@Override
													public void interpret(final int insn, final boolean delay) {
														// TODO
														// cpu.doSQC2();
													}

												};

	static CpuState					cpu;

	public static final void setCpu(final CpuState cpu) {
		EEInstructions.cpu = cpu;
	}
}
