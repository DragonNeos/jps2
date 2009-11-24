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

	public static final Instruction	PREF		= new Instruction("PREF", NO_FLAGS) {
													@Override
													public void interpret(final int insn, final boolean delay) {
														System.err.println("PREF");
														// nothing to do
													}

												};
	public static final Instruction	SYSCALL		= new Instruction("SYSCALL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.pc -= 4;
														cpu.processor.processException(ExcCode.SYSCALL, insn, delay);
													}

												};
	public static final Instruction	ERET		= new Instruction("ERET", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														System.err.println("ERET");
														// TODO
													}

												};
	public static final Instruction	BREAK		= new Instruction("BREAK", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.pc -= 4;
														cpu.processor.processException(ExcCode.BREAKPOINT, insn, delay);
													}

												};
	public static final Instruction	SYNC		= new Instruction("SYNC", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														// Nothing to do
													}

												};
	public static final Instruction	HALT		= new Instruction("HALT", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														System.err.println("HALT");
														// TODO
													}

												};
	public static final Instruction	MFIC		= new Instruction("MFIC", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {

														final int rt = (insn >> 21) & 31;
														// TODO
													}

												};

	public static final Instruction	MFSA		= new Instruction("MFSA", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														cpu.doMFSA(rd);
													}

												};

	public static final Instruction	MTSA		= new Instruction("MTSA", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														cpu.doMTSA(rd);
													}

												};

	public static final Instruction	EI			= new Instruction("EI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doEI(delay);
													}

												};

	public static final Instruction	DI			= new Instruction("DI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doDI(delay);
													}

												};

	public static final Instruction	ADD			= new Instruction("ADD", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doADD(rd, rs, rt, insn, delay);
													}

												};
	public static final Instruction	DADD		= new Instruction("DADD", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														// just ignore overflow
														// exception as it is
														// useless
														cpu.doDADD(rd, rs, rt, insn, delay);
													}

												};
	public static final Instruction	ADDU		= new Instruction("ADDU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doADDU(rd, rs, rt);

													}

												};
	public static final Instruction	DADDU		= new Instruction("DADDU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doDADDU(rd, rs, rt);

													}

												};
	public static final Instruction	ADDI		= new Instruction("ADDI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														// just ignore overflow
														// exception as it is
														// useless
														cpu.doADDIU(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	DADDI		= new Instruction("DADDI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														// just ignore overflow
														// exception as it is
														// useless
														cpu.doDADDIU(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	ADDIU		= new Instruction("ADDIU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doADDIU(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	DADDIU		= new Instruction("DADDIU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doDADDIU(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	AND			= new Instruction("AND", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doAND(rd, rs, rt);

													}

												};
	public static final Instruction	ANDI		= new Instruction("ANDI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doANDI(rt, rs, imm16);

													}

												};
	public static final Instruction	NOR			= new Instruction("NOR", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doNOR(rd, rs, rt);

													}

												};
	public static final Instruction	OR			= new Instruction("OR", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doOR(rd, rs, rt);

													}

												};
	public static final Instruction	ORI			= new Instruction("ORI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doORI(rt, rs, imm16);

													}

												};
	public static final Instruction	XOR			= new Instruction("XOR", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doXOR(rd, rs, rt);

													}

												};
	public static final Instruction	XORI		= new Instruction("XORI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doXORI(rt, rs, imm16);

													}

												};
	public static final Instruction	SLL			= new Instruction("SLL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);

														cpu.doSLL(rd, rt, sa);

													}

												};
	public static final Instruction	DSLL		= new Instruction("DSLL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);

														cpu.doDSLL(rd, rt, sa);

													}

												};
	public static final Instruction	DSLL32		= new Instruction("DSLL32", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);

														cpu.doDSLL32(rd, rt, sa);

													}

												};
	public static final Instruction	SLLV		= new Instruction("SLLV", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doSLLV(rd, rt, rs);

													}

												};
	public static final Instruction	DSLLV		= new Instruction("DSLLV", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doDSLLV(rd, rt, rs);

													}

												};
	public static final Instruction	SRA			= new Instruction("SRA", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);

														cpu.doSRA(rd, rt, sa);

													}

												};
	public static final Instruction	DSRA		= new Instruction("DSRA", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);

														cpu.doDSRA(rd, rt, sa);

													}

												};
	public static final Instruction	DSRA32		= new Instruction("DSRA32", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);

														cpu.doDSRA32(rd, rt, sa);

													}

												};
	public static final Instruction	SRAV		= new Instruction("SRAV", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doSRAV(rd, rt, rs);

													}

												};

	public static final Instruction	DSRAV		= new Instruction("DSRAV", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doDSRAV(rd, rt, rs);

													}

												};
	public static final Instruction	SRL			= new Instruction("SRL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);

														cpu.doSRL(rd, rt, sa);

													}

												};
	public static final Instruction	DSRL		= new Instruction("DSRL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);

														cpu.doDSRL(rd, rt, sa);

													}

												};

	public static final Instruction	DSRL32		= new Instruction("DSRL32", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);

														cpu.doDSRL32(rd, rt, sa);

													}

												};
	public static final Instruction	SRLV		= new Instruction("SRLV", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doSRLV(rd, rt, rs);

													}

												};

	public static final Instruction	DSRLV		= new Instruction("DSRLV", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doDSRLV(rd, rt, rs);

													}

												};

	public static final Instruction	SLT			= new Instruction("SLT", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doSLT(rd, rs, rt);

													}

												};
	public static final Instruction	SLTI		= new Instruction("SLTI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSLTI(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SLTU		= new Instruction("SLTU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doSLTU(rd, rs, rt);

													}

												};
	public static final Instruction	TEQ			= new Instruction("TEQ", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doTEQ(rs, rt, insn, delay);

													}

												};
	public static final Instruction	TNE			= new Instruction("TNE", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doTNE(rs, rt, insn, delay);

													}

												};

	public static final Instruction	TNEI		= new Instruction("TNEI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														rs = (insn >> 21) & 31;

														cpu.doTNEI(rs, imm16, insn, delay);

													}

												};

	public static final Instruction	TEQI		= new Instruction("TEQI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														rs = (insn >> 21) & 31;

														cpu.doTEQI(rs, imm16, insn, delay);

													}

												};

	public static final Instruction	TGE			= new Instruction("TGE", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doTGE(rs, rt, insn, delay);

													}

												};

	public static final Instruction	TGEI		= new Instruction("TGEI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														rs = (insn >> 21) & 31;

														cpu.doTGEI(rs, imm16, insn, delay);

													}

												};

	public static final Instruction	TGEIU		= new Instruction("TGEIU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														rs = (insn >> 21) & 31;

														cpu.doTGEIU(rs, imm16, insn, delay);

													}

												};

	public static final Instruction	TGEU		= new Instruction("TGEU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doTGEU(rs, rt, insn, delay);

													}

												};

	public static final Instruction	TLT			= new Instruction("TLT", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doTLT(rs, rt, insn, delay);
													}
												};

	public static final Instruction	TLTI		= new Instruction("TLTI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														rs = (insn >> 21) & 31;

														cpu.doTLTI(rs, imm16, insn, delay);
													}
												};

	public static final Instruction	TLTIU		= new Instruction("TLTIU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														rs = (insn >> 21) & 31;

														cpu.doTLTIU(rs, imm16, insn, delay);
													}
												};

	public static final Instruction	TLTU		= new Instruction("TLTU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doTLTU(rs, rt, insn, delay);
													}
												};

	public static final Instruction	SLTIU		= new Instruction("SLTIU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSLTIU(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SUB			= new Instruction("SUB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doSUB(rd, rs, rt, insn, delay);

													}

												};
	public static final Instruction	DSUB		= new Instruction("DSUB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														// just ignore overflow
														// exception as it is
														// useless
														cpu.doDSUB(rd, rs, rt, insn, delay);
													}

												};
	public static final Instruction	SUBU		= new Instruction("SUBU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doSUBU(rd, rs, rt, insn, delay);
													}
												};
	public static final Instruction	DSUBU		= new Instruction("DSUBU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doDSUBU(rd, rs, rt);

													}

												};
	public static final Instruction	LUI			= new Instruction("LUI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														final int rt = (insn >> 16) & 31;

														cpu.doLUI(rt, imm16);

													}

												};
	public static final Instruction	SEB			= new Instruction("SEB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														rt = (insn >> 16) & 31;

														cpu.doSEB(rd, rt);

													}

												};
	public static final Instruction	SEH			= new Instruction("SEH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doSEH(rd, rt);

													}

												};
	public static final Instruction	BITREV		= new Instruction("BITREV", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doBITREV(rd, rt);

													}

												};
	public static final Instruction	WSBH		= new Instruction("WSBH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doWSBH(rd, rt);

													}

												};
	public static final Instruction	WSBW		= new Instruction("WSBW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doWSBW(rd, rt);

													}

												};
	public static final Instruction	MOVZ		= new Instruction("MOVZ", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doMOVZ(rd, rs, rt);
													}
												};
	public static final Instruction	MOVT		= new Instruction("MOVT", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														final int cc = (insn >> 18) & 7;
														rs = (insn >> 21) & 31;
														cpu.doMOVT(rd, rs, cc);
													}
												};
	public static final Instruction	MOVF		= new Instruction("MOVF", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														final int cc = (insn >> 18) & 7;
														rs = (insn >> 21) & 31;
														cpu.doMOVF(rd, rs, cc);
													}
												};
	public static final Instruction	MOVN		= new Instruction("MOVN", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doMOVN(rd, rs, rt);

													}

												};
	public static final Instruction	MAX			= new Instruction("MAX", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doMAX(rd, rs, rt);

													}

												};
	public static final Instruction	MIN			= new Instruction("MIN", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doMIN(rd, rs, rt);

													}

												};
	public static final Instruction	CLZ			= new Instruction("CLZ", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														rs = (insn >> 21) & 31;

														cpu.doCLZ(rd, rs);

													}

												};
	public static final Instruction	CLO			= new Instruction("CLO", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														rs = (insn >> 21) & 31;

														cpu.doCLO(rd, rs);

													}

												};
	public static final Instruction	EXT			= new Instruction("EXT", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int lsb = (insn >> 6) & 31;
														final int msb = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doEXT(rt, rs, lsb, msb);

													}

												};
	public static final Instruction	INS			= new Instruction("INS", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int lsb = (insn >> 6) & 31;
														final int msb = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doINS(rt, rs, lsb, msb);

													}

												};
	public static final Instruction	MULT		= new Instruction("MULT", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doMULT(rs, rt);
													}
												};

	public static final Instruction	MULT1		= new Instruction("MULT1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doMULT1(rs, rt, rd);
													}
												};

	public static final Instruction	MULTU		= new Instruction("MULTU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doMULTU(rs, rt);
													}
												};

	public static final Instruction	MULTU1		= new Instruction("MULTU1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doMULTU1(rs, rt, rd);
													}
												};
	public static final Instruction	MADD		= new Instruction("MADD", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doMADD(rs, rt, rd);
													}
												};

	public static final Instruction	MADD1		= new Instruction("MADD1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);

														cpu.doMADD1(rs, rt, rd);
													}
												};
	public static final Instruction	MADDU		= new Instruction("MADDU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doMADDU(rs, rt, rd);

													}

												};

	public static final Instruction	MADDU1		= new Instruction("MADDU1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doMADDU1(rs, rt, rd);

													}

												};
	public static final Instruction	DIV			= new Instruction("DIV", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doDIV(rs, rt);

													}

												};

	public static final Instruction	DIV1		= new Instruction("DIV1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doDIV1(rs, rt);
													}
												};
	public static final Instruction	DIVU		= new Instruction("DIVU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doDIVU(rs, rt);

													}

												};

	public static final Instruction	DIVU1		= new Instruction("DIVU1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int rt = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doDIVU1(rs, rt);
													}
												};

	public static final Instruction	MFHI		= new Instruction("MFHI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;

														cpu.doMFHI(rd);

													}

												};

	public static final Instruction	MFHI1		= new Instruction("MFHI1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;

														cpu.doMFHI1(rd);

													}

												};

	public static final Instruction	MFLO		= new Instruction("MFLO", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;

														cpu.doMFLO(rd);

													}

												};

	public static final Instruction	MFLO1		= new Instruction("MFLO1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;

														cpu.doMFLO1(rd);

													}

												};
	public static final Instruction	MTHI		= new Instruction("MTHI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rs = (insn >> 21) & 31;

														cpu.doMTHI(rs);

													}

												};

	public static final Instruction	MTHI1		= new Instruction("MTHI1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rs = (insn >> 21) & 31;

														cpu.doMTHI1(rs);

													}

												};
	public static final Instruction	MTLO		= new Instruction("MTLO", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rs = (insn >> 21) & 31;

														cpu.doMTLO(rs);

													}

												};

	public static final Instruction	MTLO1		= new Instruction("MTLO1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rs = (insn >> 21) & 31;

														cpu.doMTLO1(rs);

													}

												};
	public static final Instruction	BEQ			= new Instruction("BEQ", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														if (cpu.doBEQ(rs, rt, (short) imm16)) {
															cpu.processor.interpretDelayslot();
														}
													}

												};
	// TODO MFC2
	public static final Instruction	BEQL		= new Instruction("BEQL", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														if (cpu.doBEQL(rs, rt, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BGEZ		= new Instruction("BGEZ", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBGEZ(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BGEZAL		= new Instruction("BGEZAL", FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBGEZAL(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BGEZALL		= new Instruction("BGEZALL", FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBGEZALL(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BGEZL		= new Instruction("BGEZL", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBGEZL(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BGTZ		= new Instruction("BGTZ", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBGTZ(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BGTZL		= new Instruction("BGTZL", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBGTZL(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BLEZ		= new Instruction("BLEZ", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBLEZ(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BLEZL		= new Instruction("BLEZL", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBLEZL(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BLTZ		= new Instruction("BLTZ", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBLTZ(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BLTZAL		= new Instruction("BLTZAL", FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBLTZAL(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BLTZALL		= new Instruction("BLTZALL", FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBLTZALL(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BLTZL		= new Instruction("BLTZL", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														rs = (insn >> 21) & 31;

														if (cpu.doBLTZL(rs, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BNE			= new Instruction("BNE", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														if (cpu.doBNE(rs, rt, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BNEL		= new Instruction("BNEL", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														if (cpu.doBNEL(rs, rt, (short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	J			= new Instruction("J", FLAG_HAS_DELAY_SLOT | FLAG_IS_JUMPING | FLAG_CANNOT_BE_SPLIT | FLAG_ENDS_BLOCK) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm26 = (insn >> 0) & 67108863;

														if (cpu.doJ(imm26))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	JAL			= new Instruction("JAL", FLAGS_LINK_INSTRUCTION | FLAG_IS_JUMPING) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm26 = (insn >> 0) & 67108863;

														if (cpu.doJAL(imm26))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	JALR		= new Instruction("JALR", FLAG_HAS_DELAY_SLOT) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														rs = (insn >> 21) & 31;

														if (cpu.doJALR(rd, rs))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	JR			= new Instruction("JR", FLAG_HAS_DELAY_SLOT | FLAG_CANNOT_BE_SPLIT | FLAG_ENDS_BLOCK) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rs = (insn >> 21) & 31;

														if (cpu.doJR(rs))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BC1F		= new Instruction("BC1F", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;

														if (cpu.doBC1F((short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BC1T		= new Instruction("BC1T", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;

														if (cpu.doBC1T((short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BC1FL		= new Instruction("BC1FL", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;

														if (cpu.doBC1FL((short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};
	public static final Instruction	BC1TL		= new Instruction("BC1TL", FLAGS_BRANCH_INSTRUCTION) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;

														if (cpu.doBC1TL((short) imm16))
															cpu.processor.interpretDelayslot();

													}

												};

	public static final Instruction	LB			= new Instruction("LB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLB(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	LBU			= new Instruction("LBU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLBU(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	LH			= new Instruction("LH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLH(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	LHU			= new Instruction("LHU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLHU(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	LW			= new Instruction("LW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLW(rt, rs, (short) imm16);
													}

												};

	public static final Instruction	LD			= new Instruction("LD", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLD(rt, rs, (short) imm16);
													}

												};

	public static final Instruction	LWU			= new Instruction("LWU", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLWU(rt, rs, (short) imm16);
													}

												};
	public static final Instruction	LWL			= new Instruction("LWL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLWL(rt, rs, (short) imm16);
													}

												};
	public static final Instruction	LWR			= new Instruction("LWR", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLWR(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	LDR			= new Instruction("LDR", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLDR(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	LDL			= new Instruction("LDL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLDL(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	LQ			= new Instruction("LQ", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLQ(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	SQ			= new Instruction("SQ", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSQ(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	SB			= new Instruction("SB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSB(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SH			= new Instruction("SH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSH(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SW			= new Instruction("SW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSW(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SD			= new Instruction("SD", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSD(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SWL			= new Instruction("SWL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSWL(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SWR			= new Instruction("SWR", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSWR(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	SDL			= new Instruction("SDL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSDL(rt, rs, (short) imm16);

													}

												};

	public static final Instruction	SDR			= new Instruction("SDR", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSDR(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	LL			= new Instruction("LL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doLL(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	LWC1		= new Instruction("LWC1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														final int ft = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doLWC1(ft, rs, (short) imm16);

													}

												};
	public static final Instruction	LWXC1		= new Instruction("LWXC1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int base = (insn >> 21) & 31;
														final int index = (insn >> 16) & 31;

														cpu.doLWXC1(base, index, fd);

													}

												};

	public static final Instruction	SC			= new Instruction("SC", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doSC(rt, rs, (short) imm16);

													}

												};
	public static final Instruction	SWC1		= new Instruction("SWC1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int imm16 = (insn >> 0) & 65535;
														final int ft = (insn >> 16) & 31;
														rs = (insn >> 21) & 31;

														cpu.doSWC1(ft, rs, (short) imm16);

													}

												};
	public static final Instruction	ADD_S		= new Instruction("ADD.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;
														final int ft = (insn >> 16) & 31;

														cpu.doADDS(fd, fs, ft);

													}

												};
	public static final Instruction	SUB_S		= new Instruction("SUB.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;
														final int ft = (insn >> 16) & 31;

														cpu.doSUBS(fd, fs, ft);

													}

												};
	public static final Instruction	MUL_S		= new Instruction("MUL.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;
														final int ft = (insn >> 16) & 31;

														cpu.doMULS(fd, fs, ft);

													}

												};
	public static final Instruction	DIV_S		= new Instruction("DIV.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;
														final int ft = (insn >> 16) & 31;

														cpu.doDIVS(fd, fs, ft);

													}

												};
	public static final Instruction	SQRT_S		= new Instruction("SQRT.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doSQRTS(fd, fs);

													}

												};
	public static final Instruction	ABS_S		= new Instruction("ABS.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doABSS(fd, fs);

													}

												};
	public static final Instruction	MOV_S		= new Instruction("MOV.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doMOVS(fd, fs);

													}

												};
	public static final Instruction	NEG_S		= new Instruction("NEG.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doNEGS(fd, fs);

													}

												};
	public static final Instruction	ROUND_W_S	= new Instruction("ROUND.W.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doROUNDWS(fd, fs);

													}

												};
	public static final Instruction	TRUNC_W_S	= new Instruction("TRUNC.W.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doTRUNCWS(fd, fs);

													}

												};
	public static final Instruction	CEIL_W_S	= new Instruction("CEIL.W.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doCEILWS(fd, fs);

													}

												};
	public static final Instruction	FLOOR_W_S	= new Instruction("FLOOR.W.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doFLOORWS(fd, fs);

													}

												};
	public static final Instruction	CVT_S_W		= new Instruction("CVT.S.W", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doCVTSW(fd, fs);

													}

												};
	public static final Instruction	CVT_W_S		= new Instruction("CVT.W.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fd = (insn >> 6) & 31;
														final int fs = (insn >> 11) & 31;

														cpu.doCVTWS(fd, fs);

													}

												};
	public static final Instruction	C_COND_S	= new Instruction("C.COND.S", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int fcond = (insn >> 0) & 15;
														final int fs = (insn >> 11) & 31;
														final int ft = (insn >> 16) & 31;

														cpu.doCCONDS(fs, ft, fcond);

													}

												};
	public static final Instruction	MFC1		= new Instruction("MFC1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c1dr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doMFC1(rt, c1dr);

													}

												};
	public static final Instruction	DMFC1		= new Instruction("DMFC1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c1dr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doDMFC1(rt, c1dr);

													}

												};
	public static final Instruction	CFC1		= new Instruction("CFC1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c1cr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doCFC1(rt, c1cr);

													}

												};
	public static final Instruction	MTC1		= new Instruction("MTC1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c1dr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doMTC1(rt, c1dr);

													}

												};
	public static final Instruction	DMTC1		= new Instruction("DMTC1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c1dr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doDMTC1(rt, c1dr);

													}

												};
	public static final Instruction	CTC1		= new Instruction("CTC1", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c1cr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;

														cpu.doCFC1(rt, c1cr);

													}

												};
	public static final Instruction	MFC0		= new Instruction("MFC0", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c0dr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;
														cpu.doMFC0(rt, c0dr);
													}

												};
	public static final Instruction	DMFC0		= new Instruction("DMFC0", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c0dr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;
														// TODO
														throw new RuntimeException();
													}
												};
	public static final Instruction	CFC0		= new Instruction("CFC0", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c0cr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;
														throw new RuntimeException();
													}

												};
	public static final Instruction	MTC0		= new Instruction("MTC0", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doMTC0((insn >> 11) & 31, (insn >> 16) & 31, insn & 0x3F, delay);
													}

												};
	public static final Instruction	DMTC0		= new Instruction("DMTC0", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c0dr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;
														// TODO
														throw new RuntimeException();
													}

												};
	public static final Instruction	CTC0		= new Instruction("CTC0", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final int c0cr = (insn >> 11) & 31;
														final int rt = (insn >> 16) & 31;
														throw new RuntimeException();
													}

												};

	public static final Instruction	DERET		= new Instruction("DERET", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doDERET();
														throw new RuntimeException();
													}

												};

	public static final Instruction	WAIT		= new Instruction("WAIT", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doWAIT();
													}

												};

	public static final Instruction	CACHE		= new Instruction("CACHE", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtImm16(insn);

														cpu.doCACHE(rt, rs, (short) imm16);
													}

												};

	public static final Instruction	TLBR		= new Instruction("TLBR", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doTLBR();
													}

												};

	public static final Instruction	TLBP		= new Instruction("TLBP", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doTLBP();
													}

												};

	public static final Instruction	TLBWI		= new Instruction("TLBWI", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doTLBWI();
													}

												};

	public static final Instruction	TLBWR		= new Instruction("TLBWR", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														cpu.doTLBWR();
													}

												};
	public static final Instruction	MTSAB		= new Instruction("MTSAB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														rs = (insn >> 21) & 31;

														cpu.doMTSAB(rs, imm16);
													}

												};

	public static final Instruction	MTSAH		= new Instruction("MTSAH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														final short imm16 = (short) ((insn >> 0) & 65535);
														rs = (insn >> 21) & 31;

														cpu.doMTSAH(rs, imm16);
													}

												};

	public static final Instruction	LQC2		= new Instruction("LQC2", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														// TODO
														// cpu.doLQC2();
														throw new RuntimeException();
													}

												};
	public static final Instruction	SQC2		= new Instruction("SQC2", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														// TODO
														// cpu.doSQC2();
														throw new RuntimeException();
													}

												};

	public static final Instruction	PLZCW		= new Instruction("PLZCW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPLZCW(rs, rd);
													}

												};

	public static final Instruction	PMFHLLW		= new Instruction("PMFHLLW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														cpu.doPMFHLLW(rd);
													}
												};

	public static final Instruction	PMFHLUW		= new Instruction("PMFHLUW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														cpu.doPMFHLUW(rd);
													}
												};

	public static final Instruction	PMFHLSLW	= new Instruction("PMFHLSLW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														cpu.doPMFHLSLW(rd);
													}
												};

	public static final Instruction	PMFHLLH		= new Instruction("PMFHLLH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														cpu.doPMFHLLH(rd);
													}
												};

	public static final Instruction	PMFHLSH		= new Instruction("PMFHLSH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														cpu.doPMFHLSH(rd);
													}
												};

	public static final Instruction	PMTHL		= new Instruction("PMTHL", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rs = (insn >> 21) & 31;
														cpu.doPMTHL(rs);
													}
												};

	public static final Instruction	PSLLH		= new Instruction("PSLLH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);
														cpu.doPSLLH(rt, rd, sa);
													}
												};

	public static final Instruction	PSRLH		= new Instruction("PSRLH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);
														cpu.doPSRLH(rt, rd, sa);
													}
												};

	public static final Instruction	PSRAH		= new Instruction("PSRLH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);
														cpu.doPSRLH(rt, rd, sa);
													}
												};

	public static final Instruction	PSLLW		= new Instruction("PSLLW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);
														cpu.doPSLLW(rt, rd, sa);
													}
												};

	public static final Instruction	PSRLW		= new Instruction("PSRLW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);
														cpu.doPSRLW(rt, rd, sa);
													}
												};

	public static final Instruction	PSRAW		= new Instruction("PSRAW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRtRdSa(insn);
														cpu.doPSRAW(rt, rd, sa);
													}
												};

	public static final Instruction	PADDW		= new Instruction("PADDW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPADDW(rs, rt, rd);
													}
												};

	public static final Instruction	PSUBW		= new Instruction("PSUBW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPSUBW(rs, rt, rd);
													}
												};

	public static final Instruction	PCGTW		= new Instruction("PCGTW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPCGTW(rs, rt, rd);
													}
												};

	public static final Instruction	PMAXW		= new Instruction("PMAXW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPMAXW(rs, rt, rd);
													}
												};

	public static final Instruction	PPAC5		= new Instruction("PPAC5", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														rt = (insn >> 16) & 31;
														cpu.doPPAC5(rt, rd);
													}
												};

	public static final Instruction	PEXT5		= new Instruction("PEXT5", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														rd = (insn >> 11) & 31;
														rt = (insn >> 16) & 31;
														cpu.doPEXT5(rt, rd);
													}
												};

	public static final Instruction	PPACB		= new Instruction("PPACB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPPACB(rs, rt, rd);
													}
												};

	public static final Instruction	PEXTLB		= new Instruction("PEXTLB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPEXTLB(rs, rt, rd);
													}
												};

	public static final Instruction	PSUBSB		= new Instruction("PSUBSB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPSUBSB(rs, rt, rd);
													}
												};

	public static final Instruction	PADDSB		= new Instruction("PADDSB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPADDSB(rs, rt, rd);
													}
												};

	public static final Instruction	PPACH		= new Instruction("PPACH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPPACH(rs, rt, rd);
													}
												};

	public static final Instruction	PEXTLH		= new Instruction("PEXTLH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPEXTLH(rs, rt, rd);
													}
												};

	public static final Instruction	PADDH		= new Instruction("PADDH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPADDH(rs, rt, rd);
													}
												};

	public static final Instruction	PSUBH		= new Instruction("PSUBH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPSUBH(rs, rt, rd);
													}
												};

	public static final Instruction	PCGTH		= new Instruction("PCGTH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPCGTH(rs, rt, rd);
													}
												};

	public static final Instruction	PMAXH		= new Instruction("PMAXH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPMAXH(rs, rt, rd);
													}
												};

	public static final Instruction	PADDB		= new Instruction("PADDB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPADDB(rs, rt, rd);
													}
												};

	public static final Instruction	PSUBB		= new Instruction("PSUBB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPSUBB(rs, rt, rd);
													}
												};

	public static final Instruction	PCGTB		= new Instruction("PCGTB", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPCGTB(rs, rt, rd);
													}
												};

	public static final Instruction	PPACW		= new Instruction("PPACW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPPACW(rs, rt, rd);
													}
												};

	public static final Instruction	PADDSW		= new Instruction("PADDSW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPADDSW(rs, rt, rd);
													}
												};

	public static final Instruction	PSUBSW		= new Instruction("PSUBSW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPSUBSW(rs, rt, rd);
													}
												};

	public static final Instruction	PEXTLW		= new Instruction("PEXTLW", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPEXTLW(rs, rt, rd);
													}
												};

	public static final Instruction	PADDSH		= new Instruction("PADDSH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPADDSH(rs, rt, rd);
													}
												};

	public static final Instruction	PSUBSH		= new Instruction("PSUBSH", NO_FLAGS) {

													@Override
													public void interpret(final int insn, final boolean delay) {
														decodeRsRtRd(insn);
														cpu.doPSUBSH(rs, rt, rd);
													}
												};

	static CpuState					cpu;

	public static final void setCpu(final CpuState cpu) {
		EEInstructions.cpu = cpu;
	}
}
