package com.jps2.core.cpu.iop;

import com.jps2.core.cpu.Common;
import com.jps2.core.cpu.Common.Instruction;
import com.jps2.core.cpu.Common.STUB;

public class IOPDecoder {

	private static final Instruction opcodeTable[] = { new STUB() {
		@Override
		public Instruction instance(final int insn) {
			return specialTable[(insn >> 0) & 0x0000003f].instance(insn);
		}
	}, new STUB() {
		@Override
		public Instruction instance(final int insn) {
			return regimmTable[(insn >> 16) & 0x0000001f].instance(insn);
		}
	}, IOPInstructions.J, IOPInstructions.JAL, IOPInstructions.BEQ, IOPInstructions.BNE,
			IOPInstructions.BLEZ, IOPInstructions.BGTZ, IOPInstructions.ADDI,
			IOPInstructions.ADDIU, IOPInstructions.SLTI, IOPInstructions.SLTIU,
			IOPInstructions.ANDI, IOPInstructions.ORI, IOPInstructions.XORI,
			IOPInstructions.LUI, new STUB() {
				@Override
				public Instruction instance(final int insn) {
					return cpo0Table[(insn >> 21) & 0x0000001F].instance(insn);
				}
			}, Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			IOPInstructions.LB, IOPInstructions.LH, IOPInstructions.LWL,
			IOPInstructions.LW, IOPInstructions.LBU, IOPInstructions.LHU,
			IOPInstructions.LWR, Common.UNK, IOPInstructions.SB, IOPInstructions.SH,
			IOPInstructions.SWL, IOPInstructions.SW, Common.UNK, Common.UNK,
			IOPInstructions.SWR, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK };
	private static final Instruction specialTable[] = { new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if (insn == 0x00000000) {
				return Common.NOP;
			} else {
				return IOPInstructions.SLL;
			}
		}
	}, Common.UNK, IOPInstructions.SRL, IOPInstructions.SRA, IOPInstructions.SLLV,
			Common.UNK, IOPInstructions.SRLV, IOPInstructions.SRAV, IOPInstructions.JR,
			IOPInstructions.JALR, Common.UNK, Common.UNK, IOPInstructions.SYSCALL,
			IOPInstructions.BREAK, Common.UNK, Common.UNK, IOPInstructions.MFHI,
			IOPInstructions.MTHI, IOPInstructions.MFLO, IOPInstructions.MTLO,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, IOPInstructions.MULT,
			IOPInstructions.MULTU, IOPInstructions.DIV, IOPInstructions.DIVU,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, IOPInstructions.ADD,
			IOPInstructions.ADDU, IOPInstructions.SUB, IOPInstructions.SUBU,
			IOPInstructions.AND, IOPInstructions.OR, IOPInstructions.XOR,
			IOPInstructions.NOR, Common.UNK, Common.UNK, IOPInstructions.SLT,
			IOPInstructions.SLTU, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK };
	private static final Instruction regimmTable[] = { IOPInstructions.BLTZ,
			IOPInstructions.BGEZ, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			IOPInstructions.BLTZAL, IOPInstructions.BGEZAL, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK };
	private static final Instruction cpo0Table[] = { IOPInstructions.MFC0,
			Common.UNK, IOPInstructions.CFC0, Common.UNK, IOPInstructions.MTC0,
			Common.UNK, IOPInstructions.CTC0, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			IOPInstructions.RFE, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK };

	public static final Instruction instruction(final int insn) {
		return opcodeTable[(insn >> 26) & 0x0000003f].instance(insn);
	}
}
