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
	static final Instruction	SYSCALL	= new Instruction("SYSCALL",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												cpu.pc -= 4;
												cpu.processor.processException(ExcCode.SYSCALL, insn, delay);
											}

										};

	static final Instruction	BREAK	= new Instruction("BREAK",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												cpu.pc -= 4;
												cpu.processor.processException(ExcCode.BREAKPOINT, insn, delay);
											}

										};

	static final Instruction	ADD		= new Instruction("ADD",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doADD(rd, rs, rt, insn, delay);
											}

										};

	static final Instruction	ADDU	= new Instruction("ADDU",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doADDU(rd, rs, rt);

											}

										};

	static final Instruction	ADDI	= new Instruction("ADDI",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doADDIU(rt, rs, (short) imm16);
											}

										};
	static final Instruction	ADDIU	= new Instruction("ADDIU",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doADDIU(rt, rs, (short) imm16);

											}

										};
	static final Instruction	AND		= new Instruction("AND",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doAND(rd, rs, rt);

											}

										};
	static final Instruction	ANDI	= new Instruction("ANDI",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doANDI(rt, rs, imm16);

											}

										};
	static final Instruction	NOR		= new Instruction("NOR",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doNOR(rd, rs, rt);

											}

										};
	static final Instruction	OR		= new Instruction("OR",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doOR(rd, rs, rt);

											}

										};
	static final Instruction	ORI		= new Instruction("ORI",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doORI(rt, rs, imm16);

											}

										};
	static final Instruction	XOR		= new Instruction("XOR",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doXOR(rd, rs, rt);

											}

										};
	static final Instruction	XORI	= new Instruction("XORI",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doXORI(rt, rs, imm16);

											}

										};
	static final Instruction	SLL		= new Instruction("SLL",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int sa = (insn >> 6) & 31;
												final int rd = (insn >> 11) & 31;
												final int rt = (insn >> 16) & 31;

												cpu.doSLL(rd, rt, sa);

											}

										};

	static final Instruction	SLLV	= new Instruction("SLLV",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doSLLV(rd, rt, rs);

											}

										};

	static final Instruction	SRA		= new Instruction("SRA",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int sa = (insn >> 6) & 31;
												final int rd = (insn >> 11) & 31;
												final int rt = (insn >> 16) & 31;

												cpu.doSRA(rd, rt, sa);

											}

										};

	static final Instruction	SRAV	= new Instruction("SRAV",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doSRAV(rd, rt, rs);

											}

										};

	static final Instruction	SRL		= new Instruction("SRL",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int sa = (insn >> 6) & 31;
												final int rd = (insn >> 11) & 31;
												final int rt = (insn >> 16) & 31;

												cpu.doSRL(rd, rt, sa);

											}

										};

	static final Instruction	SRLV	= new Instruction("SRLV",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doSRLV(rd, rt, rs);

											}

										};

	static final Instruction	SLT		= new Instruction("SLT",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doSLT(rd, rs, rt);

											}

										};
	static final Instruction	SLTI	= new Instruction("SLTI",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doSLTI(rt, rs, (short) imm16);

											}

										};
	static final Instruction	SLTU	= new Instruction("SLTU",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doSLTU(rd, rs, rt);

											}

										};

	static final Instruction	SLTIU	= new Instruction("SLTIU",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doSLTIU(rt, rs, (short) imm16);

											}

										};
	static final Instruction	SUB		= new Instruction("SUB",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);

												cpu.doSUB(rd, rs, rt, insn, delay);

											}

										};

	static final Instruction	SUBU	= new Instruction("SUBU",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtRd(insn);
												cpu.doSUBU(rd, rs, rt, insn, delay);
											}
										};
	static final Instruction	LUI		= new Instruction("LUI",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int imm16 = (insn >> 0) & 65535;
												final int rt = (insn >> 16) & 31;

												cpu.doLUI(rt, imm16);

											}

										};

	static final Instruction	MULT	= new Instruction("MULT",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rt = (insn >> 16) & 31;
												final int rs = (insn >> 21) & 31;

												cpu.doMULT(rs, rt);

											}

										};

	static final Instruction	MULTU	= new Instruction("MULTU",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rt = (insn >> 16) & 31;
												final int rs = (insn >> 21) & 31;

												cpu.doMULTU(rs, rt);

											}

										};

	static final Instruction	DIV		= new Instruction("DIV",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rt = (insn >> 16) & 31;
												final int rs = (insn >> 21) & 31;

												cpu.doDIV(rs, rt);

											}

										};
	static final Instruction	DIVU	= new Instruction("DIVU",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rt = (insn >> 16) & 31;
												final int rs = (insn >> 21) & 31;

												cpu.doDIVU(rs, rt);

											}

										};

	static final Instruction	MFHI	= new Instruction("MFHI",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rd = (insn >> 11) & 31;

												cpu.doMFHI(rd);

											}

										};
	static final Instruction	MFLO	= new Instruction("MFLO",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rd = (insn >> 11) & 31;

												cpu.doMFLO(rd);

											}

										};
	static final Instruction	MTHI	= new Instruction("MTHI",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rs = (insn >> 21) & 31;

												cpu.doMTHI(rs);

											}

										};
	static final Instruction	MTLO	= new Instruction("MTLO",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rs = (insn >> 21) & 31;

												cpu.doMTLO(rs);

											}

										};
	static final Instruction	BEQ		= new Instruction("BEQ",FLAGS_BRANCH_INSTRUCTION){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												if (cpu.doBEQ(rs, rt, (short) imm16)) {
													cpu.processor.interpretDelayslot();
												}
											}

										};
	static final Instruction	BGEZ	= new Instruction("BGEZ",FLAGS_BRANCH_INSTRUCTION){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int imm16 = (insn >> 0) & 65535;
												final int rs = (insn >> 21) & 31;

												if (cpu.doBGEZ(rs, (short) imm16))
													cpu.processor.interpretDelayslot();

											}

										};
	static final Instruction	BGEZAL	= new Instruction("BGEZAL",FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int imm16 = (insn >> 0) & 65535;
												final int rs = (insn >> 21) & 31;

												if (cpu.doBGEZAL(rs, (short) imm16))
													cpu.processor.interpretDelayslot();

											}

										};

	static final Instruction	BGTZ	= new Instruction("BGTZ",FLAGS_BRANCH_INSTRUCTION){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int imm16 = (insn >> 0) & 65535;
												final int rs = (insn >> 21) & 31;

												if (cpu.doBGTZ(rs, (short) imm16))
													cpu.processor.interpretDelayslot();

											}

										};
	static final Instruction	BLEZ	= new Instruction("BLEZ",FLAGS_BRANCH_INSTRUCTION){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int imm16 = (insn >> 0) & 65535;
												final int rs = (insn >> 21) & 31;

												if (cpu.doBLEZ(rs, (short) imm16))
													cpu.processor.interpretDelayslot();

											}

										};
	static final Instruction	BLTZ	= new Instruction("BLTZ",FLAGS_BRANCH_INSTRUCTION){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int imm16 = (insn >> 0) & 65535;
												final int rs = (insn >> 21) & 31;

												if (cpu.doBLTZ(rs, (short) imm16))
													cpu.processor.interpretDelayslot();

											}

										};
	static final Instruction	BLTZAL	= new Instruction("BLTZAL",FLAGS_LINK_INSTRUCTION | FLAG_IS_CONDITIONAL | FLAG_IS_BRANCHING){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int imm16 = (insn >> 0) & 65535;
												final int rs = (insn >> 21) & 31;

												if (cpu.doBLTZAL(rs, (short) imm16))
													cpu.processor.interpretDelayslot();

											}

										};

	static final Instruction	BNE		= new Instruction("BNE",FLAGS_BRANCH_INSTRUCTION){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												if (cpu.doBNE(rs, rt, (short) imm16))
													cpu.processor.interpretDelayslot();

											}

										};
	static final Instruction	J		= new Instruction("J",FLAG_HAS_DELAY_SLOT | FLAG_IS_JUMPING | FLAG_CANNOT_BE_SPLIT | FLAG_ENDS_BLOCK){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int imm26 = (insn >> 0) & 67108863;

												if (cpu.doJ(imm26))
													cpu.processor.interpretDelayslot();

											}

										};
	static final Instruction	JAL		= new Instruction("JAL",FLAGS_LINK_INSTRUCTION | FLAG_IS_JUMPING){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int imm26 = (insn >> 0) & 67108863;

												if (cpu.doJAL(imm26))
													cpu.processor.interpretDelayslot();

											}

										};
	static final Instruction	JALR	= new Instruction("JALR",FLAG_HAS_DELAY_SLOT){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rd = (insn >> 11) & 31;
												final int rs = (insn >> 21) & 31;

												if (cpu.doJALR(rd, rs))
													cpu.processor.interpretDelayslot();

											}

										};
	static final Instruction	JR		= new Instruction("JR",FLAG_HAS_DELAY_SLOT | FLAG_CANNOT_BE_SPLIT | FLAG_ENDS_BLOCK){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int rs = (insn >> 21) & 31;

												if (cpu.doJR(rs))
													cpu.processor.interpretDelayslot();

											}

										};

	static final Instruction	LB		= new Instruction("LB",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doLB(rt, rs, (short) imm16);

											}

										};
	static final Instruction	LBU		= new Instruction("LBU",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doLBU(rt, rs, (short) imm16);

											}

										};
	static final Instruction	LH		= new Instruction("LH",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doLH(rt, rs, (short) imm16);

											}

										};
	static final Instruction	LHU		= new Instruction("LHU",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doLHU(rt, rs, (short) imm16);

											}

										};
	static final Instruction	LW		= new Instruction("LW",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doLW(rt, rs, (short) imm16);
											}

										};
	static final Instruction	LWL		= new Instruction("LWL",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doLWL(rt, rs, (short) imm16);
											}

										};
	static final Instruction	LWR		= new Instruction("LWR",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doLWR(rt, rs, (short) imm16);

											}

										};
	static final Instruction	SB		= new Instruction("SB",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doSB(rt, rs, (short) imm16);

											}

										};
	static final Instruction	SH		= new Instruction("SH",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doSH(rt, rs, (short) imm16);

											}

										};
	static final Instruction	SW		= new Instruction("SW",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);

												cpu.doSW(rt, rs, (short) imm16);

											}

										};
	static final Instruction	SWL		= new Instruction("SWL",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);
												cpu.doSWL(rt, rs, (short) imm16);

											}

										};
	static final Instruction	SWR		= new Instruction("SWR",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												decodeRsRtImm16(insn);
												cpu.doSWR(rt, rs, (short) imm16);
											}
										};

	static final Instruction	MFC0	= new Instruction("MFC0",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int c0dr = (insn >> 11) & 31;
												final int rt = (insn >> 16) & 31;
												cpu.doMFC0(rt, c0dr);
											}
										};

	static final Instruction	CFC0	= new Instruction("CFC0",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int c0cr = (insn >> 11) & 31;
												final int rt = (insn >> 16) & 31;
												cpu.doCFC0(rt, c0cr);
											}

										};
	static final Instruction	MTC0	= new Instruction("MTC0",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int c0dr = (insn >> 11) & 31;
												final int rt = (insn >> 16) & 31;
												cpu.doMTC0(rt, c0dr);
											}

										};

	static final Instruction	RFE		= new Instruction("RFE",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												cpu.doRFE();
											}

										};

	static final Instruction	CTC0	= new Instruction("CTC0",NO_FLAGS){

											@Override
											public void interpret(final int insn, final boolean delay) {
												final int c0cr = (insn >> 11) & 31;
												final int rt = (insn >> 16) & 31;
												cpu.doCTC0(rt, c0cr);
											}

										};

	static CpuState				cpu;

	public static final void setCpu(final CpuState cpu) {
		IOPInstructions.cpu = cpu;
	}
}
