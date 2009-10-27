package com.jps2.core.cpu.r3000a;

import com.jps2.core.cpu.Common;
import com.jps2.core.cpu.Common.Instruction;
import com.jps2.core.cpu.Common.STUB;

public class Decoder {

    private static final Instruction opcodeTable[] = {
        new STUB() {
            @Override
            public Instruction instance(final int insn) {
                return specialTable[(insn >> 0) & 0x0000003f].instance(insn);
            }
        },
        new STUB() {
            @Override
            public Instruction instance(final int insn) {
                return regimmTable[(insn >> 16) & 0x0000001f].instance(insn);
            }
        },
        Instructions.J,
        Instructions.JAL,
        Instructions.BEQ,
        Instructions.BNE,
        Instructions.BLEZ,
        Instructions.BGTZ,
        Instructions.ADDI,
        Instructions.ADDIU,
        Instructions.SLTI,
        Instructions.SLTIU,
        Instructions.ANDI,
        Instructions.ORI,
        Instructions.XORI,
        Instructions.LUI,
        new STUB() {
            @Override
            public Instruction instance(final int insn) {
                return cpo0Table[(insn >> 21) & 0x0000001F].instance(insn);
            }
        },
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Instructions.LB,
        Instructions.LH,
        Instructions.LWL,
        Instructions.LW,
        Instructions.LBU,
        Instructions.LHU,
        Instructions.LWR,
        Common.UNK,
        Instructions.SB,
        Instructions.SH,
        Instructions.SWL,
        Instructions.SW,
        Common.UNK,
        Common.UNK,
        Instructions.SWR,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK
    };
    private static final Instruction specialTable[] = {
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if (insn == 0x00000000) {
                    return Instructions.NOP;
                } else {
                    return Instructions.SLL;
                }
            }
        },
        Common.UNK,
        Instructions.SRL,
        Instructions.SRA,
        Instructions.SLLV,
        Common.UNK,
        Instructions.SRLV,
        Instructions.SRAV,
        Instructions.JR,
        Instructions.JALR,
        Common.UNK,
        Common.UNK,
        Instructions.SYSCALL,
        Instructions.BREAK,
        Common.UNK,
        Common.UNK,
        Instructions.MFHI,
        Instructions.MTHI,
        Instructions.MFLO,
        Instructions.MTLO,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Instructions.MULT,
        Instructions.MULTU,
        Instructions.DIV,
        Instructions.DIVU,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Instructions.ADD,
        Instructions.ADDU,
        Instructions.SUB,
        Instructions.SUBU,
        Instructions.AND,
        Instructions.OR,
        Instructions.XOR,
        Instructions.NOR,
        Common.UNK,
        Common.UNK,
        Instructions.SLT,
        Instructions.SLTU,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK
    };
    private static final Instruction regimmTable[] = {
        Instructions.BLTZ,
        Instructions.BGEZ,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Instructions.BLTZAL,
        Instructions.BGEZAL,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK
    };
    private static final Instruction cpo0Table[] = {
        Instructions.MFC0,
        Common.UNK,
        Instructions.CFC0,
        Common.UNK,
        Instructions.MTC0,
        Common.UNK,
        Instructions.CTC0,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Instructions.RFE,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK
    };
    
    public static final Instruction instruction(final int insn) {
        return opcodeTable[(insn >> 26) & 0x0000003f].instance(insn);
    }
}
