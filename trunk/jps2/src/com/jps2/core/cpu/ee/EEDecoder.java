package com.jps2.core.cpu.ee;

import com.jps2.core.cpu.Common;
import com.jps2.core.cpu.Common.Instruction;
import com.jps2.core.cpu.Common.STUB;

public class EEDecoder {

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
	}, EEInstructions.J, EEInstructions.JAL, EEInstructions.BEQ,
			EEInstructions.BNE, EEInstructions.BLEZ, EEInstructions.BGTZ,
			EEInstructions.ADDI, EEInstructions.ADDIU, EEInstructions.SLTI,
			EEInstructions.SLTIU, EEInstructions.ANDI, EEInstructions.ORI,
			EEInstructions.XORI, EEInstructions.LUI, new STUB() {
				@Override
				public Instruction instance(final int insn) {
					return cpo0Table[(insn >> 21) & 0x0000001F].instance(insn);
				}
			}, new STUB() {
				@Override
				public Instruction instance(final int insn) {
					return cpo1Table[(insn >> 21) & 0x0000001F].instance(insn);
				}
			}, new STUB() {
				@Override
				public Instruction instance(final int insn) {
					// return cpo2Table[(insn >> 21) &
					// 0x0000001F].instance(insn);
					throw new RuntimeException("VPU not supported.");
				}
			}, Common.UNK, EEInstructions.BEQL, EEInstructions.BNEL,
			EEInstructions.BLEZL, EEInstructions.BGTZL, EEInstructions.DADDI,
			EEInstructions.DADDIU, EEInstructions.LDL, EEInstructions.LDR,
			new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return mmiTable[insn];
				}
			}, Common.UNK, EEInstructions.LQ, EEInstructions.SQ,
			EEInstructions.LB, EEInstructions.LH, EEInstructions.LWL,
			EEInstructions.LW, EEInstructions.LBU, EEInstructions.LHU,
			EEInstructions.LWR, EEInstructions.LWU, EEInstructions.SB,
			EEInstructions.SH, EEInstructions.SWL, EEInstructions.SW,
			EEInstructions.SDL, EEInstructions.SDR, EEInstructions.SWR,
			EEInstructions.CACHE, Common.UNK, EEInstructions.LWC1, Common.UNK,
			EEInstructions.PREF, Common.UNK, Common.UNK, EEInstructions.LQC2,
			EEInstructions.LD, Common.UNK, EEInstructions.SWC1, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, EEInstructions.SQC2,
			EEInstructions.SD };

	private static final Instruction specialTable[] = { new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x001fffc0) == 0x00000000) {
				return EEInstructions.NOP;
			} else {
				return EEInstructions.SLL;
			}
		}
	}, Common.UNK, EEInstructions.SRL, EEInstructions.SRA, EEInstructions.SLLV,
			Common.UNK, EEInstructions.SRLV, EEInstructions.SRAV,
			EEInstructions.JR, EEInstructions.JALR, EEInstructions.MOVZ,
			EEInstructions.MOVN, EEInstructions.SYSCALL, EEInstructions.BREAK,
			Common.UNK, EEInstructions.SYNC, EEInstructions.MFHI,
			EEInstructions.MTHI, EEInstructions.MFLO, EEInstructions.MTLO,
			EEInstructions.DSLLV, Common.UNK, EEInstructions.DSRLV,
			EEInstructions.DSRAV, EEInstructions.MULT, EEInstructions.MULTU,
			EEInstructions.DIV, EEInstructions.DIVU, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, EEInstructions.ADD, EEInstructions.ADDU,
			EEInstructions.SUB, EEInstructions.SUBU, EEInstructions.AND,
			EEInstructions.OR, EEInstructions.XOR, EEInstructions.NOR,
			EEInstructions.MFSA, EEInstructions.MTSA, EEInstructions.SLT,
			EEInstructions.SLTU, EEInstructions.DADD, EEInstructions.DADDU,
			EEInstructions.DSUB, EEInstructions.DSUBU, EEInstructions.TGE,
			EEInstructions.TGEU, EEInstructions.TLT, EEInstructions.TLTU,
			EEInstructions.TEQ, Common.UNK, EEInstructions.TNE, Common.UNK,
			EEInstructions.DSLL, Common.UNK, EEInstructions.DSRL,
			EEInstructions.DSRA, EEInstructions.DSLL32, Common.UNK,
			EEInstructions.DSRL32, EEInstructions.DSRA32 };

	private	 static final Instruction[] regimmTable = { EEInstructions.BLTZ,
			EEInstructions.BGEZ, EEInstructions.BLTZL, EEInstructions.BGEZL,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.TGEI, EEInstructions.TGEIU, EEInstructions.TLTI,
			EEInstructions.TLTIU, EEInstructions.TEQI, Common.UNK,
			EEInstructions.TNEI, Common.UNK, EEInstructions.BLTZAL,
			EEInstructions.BGEZAL, EEInstructions.BLTZALL,
			EEInstructions.BGEZALL, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.MTSAB, EEInstructions.MTSAH, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK };

	private static final Instruction[] mmiTable = { EEInstructions.MADD,
			EEInstructions.MADDU, Common.UNK, Common.UNK, EEInstructions.PLZCW,
			Common.UNK, Common.UNK, Common.UNK, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return mmi0Table[insn];
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return mmi2Table[insn];
				}
			}, Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.MFHI1, EEInstructions.MTHI1,
			EEInstructions.MFLO1, EEInstructions.MTLO1, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, EEInstructions.MULT1,
			EEInstructions.MULTU1, EEInstructions.DIV1, EEInstructions.DIVU1,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.MADD1, EEInstructions.MADDU1, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return mmi1Table[insn];
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return mmi3Table[insn];
				}
			}, Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.PMFHL, EEInstructions.PMTHL, Common.UNK,
			Common.UNK, EEInstructions.PSLLH, Common.UNK, EEInstructions.PSRLH,
			EEInstructions.PSRAH, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.PSLLW, Common.UNK, EEInstructions.PSRLW,
			EEInstructions.PSRAW };

	private static final Instruction[] mmi0Table = { EEInstructions.PADDW,
			EEInstructions.PSUBW, EEInstructions.PCGTW, EEInstructions.PMAXW,
			EEInstructions.PADDH, EEInstructions.PSUBH, EEInstructions.PCGTH,
			EEInstructions.PMAXH, EEInstructions.PADDB, EEInstructions.PSUBB,
			EEInstructions.PCGTB, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, EEInstructions.PADDSW,
			EEInstructions.PSUBSW, EEInstructions.PEXTLW, EEInstructions.PPACW,
			EEInstructions.PADDSH, EEInstructions.PSUBSH,
			EEInstructions.PEXTLH, EEInstructions.PPACH, EEInstructions.PADDSB,
			EEInstructions.PSUBSB, EEInstructions.PEXTLB, EEInstructions.PPACB,
			Common.UNK, Common.UNK, EEInstructions.PEXT5, EEInstructions.PPAC5 };

	private static final Instruction[] mmi1Table = { Common.UNK,
			EEInstructions.PABSW, EEInstructions.PCEQW, EEInstructions.PMINW,
			EEInstructions.PADSBH, EEInstructions.PABSH, EEInstructions.PCEQH,
			EEInstructions.PMINH, Common.UNK, Common.UNK, EEInstructions.PCEQB,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.PADDUW, EEInstructions.PSUBUW,
			EEInstructions.PEXTUW, Common.UNK, EEInstructions.PADDUH,
			EEInstructions.PSUBUH, EEInstructions.PEXTUH, Common.UNK,
			EEInstructions.PADDUB, EEInstructions.PSUBUB,
			EEInstructions.PEXTUB, EEInstructions.QFSRV, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK };

	private static final Instruction[] mmi2Table = { EEInstructions.PMADDW,
			Common.UNK, EEInstructions.PSLLVW, EEInstructions.PSRLVW,
			EEInstructions.PMSUBW, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.PMFHI, EEInstructions.PMFLO, EEInstructions.PINTH,
			Common.UNK, EEInstructions.PMULTW, EEInstructions.PDIVW,
			EEInstructions.PCPYLD, Common.UNK, EEInstructions.PMADDH,
			EEInstructions.PHMADH, EEInstructions.PAND, EEInstructions.PXOR,
			EEInstructions.PMSUBH, EEInstructions.PHMSBH, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, EEInstructions.PEXEH,
			EEInstructions.PREVH, EEInstructions.PMULTH, EEInstructions.PDIVBW,
			EEInstructions.PEXEW, EEInstructions.PROT3W };

	private static final Instruction[] mmi3Table = { EEInstructions.PMADDUW,
			Common.UNK, Common.UNK, EEInstructions.PSRAVW, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, EEInstructions.PMTHI,
			EEInstructions.PMTLO, EEInstructions.PINTEH, Common.UNK,
			EEInstructions.PMULTUW, EEInstructions.PDIVUW,
			EEInstructions.PCPYUD, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.POR, EEInstructions.PNOR, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.PEXCH, EEInstructions.PCPYH, Common.UNK, Common.UNK,
			EEInstructions.PEXCW, Common.UNK, };

	private static final STUB c0Stub = new STUB() {
		@Override
		public Instruction instance(final int insn) {
			return c0Table[insn & 0x0000003f];
		}
	};

	private static final STUB bc0Stub = new STUB() {
		@Override
		public Instruction instance(final int insn) {
			return bc0Table[(insn >> 16) & 0x1f];
		}
	};

	private static final Instruction cpo0Table[] = { EEInstructions.MF0,
			Common.UNK, Common.UNK, Common.UNK, EEInstructions.MT0, Common.UNK,
			Common.UNK, Common.UNK, bc0Stub, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK, c0Stub,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK };

	private static final Instruction bc0Table[] = { EEInstructions.BC0F,
			EEInstructions.BC0T, EEInstructions.BC0FL, EEInstructions.BC0TL,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK };

	private static final Instruction c0Table[] = { Common.UNK,
			EEInstructions.TLBR, EEInstructions.TLBWI, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.TLBWR, Common.UNK, EEInstructions.TLBP,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.ERET, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, EEInstructions.EI,
			EEInstructions.DI, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK };

	private static final Instruction cpo1Table[] = { EEInstructions.MFC1,
			Common.UNK, EEInstructions.CFC1, Common.UNK, EEInstructions.MTC1,
			Common.UNK, EEInstructions.CTC1, Common.UNK, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return bc1Table[(insn >> 16) & 0x00000003].instance(insn);
				}
			}, Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return sTable[(insn >> 0) & 0x0000001f].instance(insn);
				}
			}, Common.UNK, Common.UNK, Common.UNK, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return wTable[(insn >> 0) & 0x0000001f].instance(insn);
				}
			}, Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK };

	private static final Instruction bc1Table[] = { EEInstructions.BC1F,
			EEInstructions.BC1T, EEInstructions.BC1FL, EEInstructions.BC1TL,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK };

	private static final Instruction cpo1xTable[] = { EEInstructions.LWXC1,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK };

	private static final Instruction sTable[] = { EEInstructions.ADD_S,
			EEInstructions.SUB_S, EEInstructions.MUL_S, EEInstructions.DIV_S,
			EEInstructions.SQRT_S, EEInstructions.ABS_S, EEInstructions.MOV_S,
			EEInstructions.NEG_S, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.RSQRT_S, Common.UNK,
			EEInstructions.ADDA_S, EEInstructions.SUBA_S,
			EEInstructions.MULA_S, Common.UNK, EEInstructions.MADD_S,
			EEInstructions.MSUB_S, EEInstructions.MADDA_S,
			EEInstructions.MSUBA_S, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.CVTW_S, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.MAX_S, EEInstructions.MIN_S, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.C_F_S, Common.UNK, EEInstructions.C_EQ_S,
			Common.UNK, EEInstructions.C_LT_S, Common.UNK,
			EEInstructions.C_LE_S, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK };

	private static final Instruction[] wTable = { Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.CVTS_W, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK

	};

	private static final Instruction table_7[] = { EEInstructions.BVF,
			EEInstructions.BVT, EEInstructions.BVFL, EEInstructions.BVTL, };
	private static final Instruction table_8[] = { EEInstructions.VADD,
			EEInstructions.VSUB, EEInstructions.VSBN, EEInstructions.VDIV, };
	private static final Instruction table_9[] = { EEInstructions.VMUL,
			EEInstructions.VDOT, EEInstructions.VSCL, Common.UNK,
			EEInstructions.VHDP, EEInstructions.VDET, EEInstructions.VCRS,
			Common.UNK, };
	private static final Instruction table_10[] = { EEInstructions.VCMP,
			Common.UNK, EEInstructions.VMIN, EEInstructions.VMAX,
			EEInstructions.VSLT, EEInstructions.VSCMP, EEInstructions.VSGE,
			Common.UNK, };
	private static final Instruction table_12[] = { new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VMOV;
				} else {
					return EEInstructions.VNEG;
				}
			} else {
				if ((insn & 0x02020000) == 0x00000000) {
					return EEInstructions.VABS;
				} else {
					if ((insn & 0x02000000) == 0x00000000) {
						return EEInstructions.VIDT;
					} else {
						if ((insn & 0x01800000) == 0x00000000) {
							return EEInstructions.VF2IN;
						} else {
							if ((insn & 0x01000000) == 0x00000000) {
								return EEInstructions.VI2F;
							} else {
								return EEInstructions.VWBN;
							}
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VSAT0;
				} else {
					return EEInstructions.VZERO;
				}
			} else {
				if ((insn & 0x02020000) == 0x00000000) {
					return EEInstructions.VSAT1;
				} else {
					if ((insn & 0x02000000) == 0x00000000) {
						return EEInstructions.VONE;
					} else {
						if ((insn & 0x01800000) == 0x00000000) {
							return EEInstructions.VF2IN;
						} else {
							if ((insn & 0x01000000) == 0x00000000) {
								return EEInstructions.VI2F;
							} else {
								return EEInstructions.VWBN;
							}
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x01800000) == 0x00000000) {
				return EEInstructions.VF2IN;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VI2F;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x01800000) == 0x00000000) {
				return EEInstructions.VF2IN;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VI2F;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VRCP;
				} else {
					return EEInstructions.VSIN;
				}
			} else {
				if ((insn & 0x02020000) == 0x00000000) {
					return EEInstructions.VRSQ;
				} else {
					if ((insn & 0x02000000) == 0x00000000) {
						return EEInstructions.VCOS;
					} else {
						if ((insn & 0x01800000) == 0x00000000) {
							return EEInstructions.VF2IN;
						} else {
							if ((insn & 0x01000000) == 0x00000000) {
								return EEInstructions.VI2F;
							} else {
								return EEInstructions.VWBN;
							}
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VEXP2;
				} else {
					return EEInstructions.VSQRT;
				}
			} else {
				if ((insn & 0x02020000) == 0x00000000) {
					return EEInstructions.VLOG2;
				} else {
					if ((insn & 0x02000000) == 0x00000000) {
						return EEInstructions.VASIN;
					} else {
						if ((insn & 0x01800000) == 0x00000000) {
							return EEInstructions.VF2IN;
						} else {
							if ((insn & 0x01000000) == 0x00000000) {
								return EEInstructions.VI2F;
							} else {
								return EEInstructions.VWBN;
							}
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02020000) == 0x00000000) {
				return EEInstructions.VNRCP;
			} else {
				if ((insn & 0x02000000) == 0x00000000) {
					return EEInstructions.VNSIN;
				} else {
					if ((insn & 0x01800000) == 0x00000000) {
						return EEInstructions.VF2IN;
					} else {
						if ((insn & 0x01000000) == 0x00000000) {
							return EEInstructions.VI2F;
						} else {
							return EEInstructions.VWBN;
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000000) == 0x00000000) {
				return EEInstructions.VREXP2;
			} else {
				if ((insn & 0x01800000) == 0x00000000) {
					return EEInstructions.VF2IN;
				} else {
					if ((insn & 0x01000000) == 0x00000000) {
						return EEInstructions.VI2F;
					} else {
						return EEInstructions.VWBN;
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VRNDS;
				} else {
					return EEInstructions.VRNDF1;
				}
			} else {
				if ((insn & 0x02020000) == 0x00000000) {
					return EEInstructions.VRNDI;
				} else {
					if ((insn & 0x02000000) == 0x00000000) {
						return EEInstructions.VRNDF2;
					} else {
						if ((insn & 0x01800000) == 0x00800000) {
							return EEInstructions.VCMOVT;
						} else {
							if ((insn & 0x01000000) == 0x00000000) {
								return EEInstructions.VF2IZ;
							} else {
								return EEInstructions.VWBN;
							}
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x01800000) == 0x00800000) {
				return EEInstructions.VCMOVT;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2IZ;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x01800000) == 0x00800000) {
				return EEInstructions.VCMOVF;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2IZ;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x01800000) == 0x00800000) {
				return EEInstructions.VCMOVF;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2IZ;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				return EEInstructions.VF2H;
			} else {
				if ((insn & 0x02000000) == 0x00000000) {
					return EEInstructions.VH2F;
				} else {
					if ((insn & 0x01000000) == 0x00000000) {
						return EEInstructions.VF2IZ;
					} else {
						return EEInstructions.VWBN;
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				return EEInstructions.VSBZ;
			} else {
				if ((insn & 0x02000000) == 0x00000000) {
					return EEInstructions.VLGB;
				} else {
					if ((insn & 0x01000000) == 0x00000000) {
						return EEInstructions.VF2IZ;
					} else {
						return EEInstructions.VWBN;
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VUC2I;
				} else {
					return EEInstructions.VUS2I;
				}
			} else {
				if ((insn & 0x02020000) == 0x00000000) {
					return EEInstructions.VC2I;
				} else {
					if ((insn & 0x02000000) == 0x00000000) {
						return EEInstructions.VS2I;
					} else {
						if ((insn & 0x01000000) == 0x00000000) {
							return EEInstructions.VF2IZ;
						} else {
							return EEInstructions.VWBN;
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VI2UC;
				} else {
					return EEInstructions.VI2US;
				}
			} else {
				if ((insn & 0x02020000) == 0x00000000) {
					return EEInstructions.VI2C;
				} else {
					if ((insn & 0x02000000) == 0x00000000) {
						return EEInstructions.VI2S;
					} else {
						if ((insn & 0x01000000) == 0x00000000) {
							return EEInstructions.VF2IZ;
						} else {
							return EEInstructions.VWBN;
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VSRT1;
				} else {
					return EEInstructions.VBFY1;
				}
			} else {
				if ((insn & 0x02020000) == 0x00000000) {
					return EEInstructions.VSRT2;
				} else {
					if ((insn & 0x02000000) == 0x00000000) {
						return EEInstructions.VBFY2;
					} else {
						if ((insn & 0x01000000) == 0x00000000) {
							return EEInstructions.VF2IU;
						} else {
							return EEInstructions.VWBN;
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VOCP;
				} else {
					return EEInstructions.VFAD;
				}
			} else {
				if ((insn & 0x02020000) == 0x00000000) {
					return EEInstructions.VSOCP;
				} else {
					if ((insn & 0x02000000) == 0x00000000) {
						return EEInstructions.VAVG;
					} else {
						if ((insn & 0x01000000) == 0x00000000) {
							return EEInstructions.VF2IU;
						} else {
							return EEInstructions.VWBN;
						}
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00000000) {
				return EEInstructions.VSRT3;
			} else {
				if ((insn & 0x02000000) == 0x00000000) {
					return EEInstructions.VSRT4;
				} else {
					if ((insn & 0x01000000) == 0x00000000) {
						return EEInstructions.VF2IU;
					} else {
						return EEInstructions.VWBN;
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x01000000) == 0x00000000) {
				return EEInstructions.VF2IU;
			} else {
				return EEInstructions.VWBN;
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000080) == 0x00000000) {
				return EEInstructions.VMFVC;
			} else {
				if ((insn & 0x02000000) == 0x00000000) {
					return EEInstructions.VMTVC;
				} else {
					if ((insn & 0x01000000) == 0x00000000) {
						return EEInstructions.VF2IU;
					} else {
						return EEInstructions.VWBN;
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x01000000) == 0x00000000) {
				return EEInstructions.VF2IU;
			} else {
				return EEInstructions.VWBN;
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02010000) == 0x00010000) {
				if ((insn & 0x00020000) == 0x00000000) {
					return EEInstructions.VT4444;
				} else {
					return EEInstructions.VT5650;
				}
			} else {
				if ((insn & 0x02000000) == 0x00000000) {
					return EEInstructions.VT5551;
				} else {
					if ((insn & 0x01000000) == 0x00000000) {
						return EEInstructions.VF2IU;
					} else {
						return EEInstructions.VWBN;
					}
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x01000000) == 0x00000000) {
				return EEInstructions.VF2IU;
			} else {
				return EEInstructions.VWBN;
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000000) == 0x00000000) {
				return EEInstructions.VCST;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2ID;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000000) == 0x00000000) {
				return EEInstructions.VCST;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2ID;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000000) == 0x00000000) {
				return EEInstructions.VCST;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2ID;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000000) == 0x00000000) {
				return EEInstructions.VCST;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2ID;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000000) == 0x00000000) {
				return EEInstructions.VCST;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2ID;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000000) == 0x00000000) {
				return EEInstructions.VCST;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2ID;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000000) == 0x00000000) {
				return EEInstructions.VCST;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2ID;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x02000000) == 0x00000000) {
				return EEInstructions.VCST;
			} else {
				if ((insn & 0x01000000) == 0x00000000) {
					return EEInstructions.VF2ID;
				} else {
					return EEInstructions.VWBN;
				}
			}
		}
	}, };
	private static final Instruction table_13[] = { EEInstructions.VPFXS,
			EEInstructions.VPFXT, EEInstructions.VPFXD, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00800000) == 0x00000000) {
						return EEInstructions.VIIM;
					} else {
						return EEInstructions.VFIM;
					}
				}
			}, };
	private static final Instruction table_14[] = { EEInstructions.VMMUL,
			new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000080) == 0x00000000) {
						return EEInstructions.VHTFM2;
					} else {
						return EEInstructions.VTFM2;
					}
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000080) == 0x00000000) {
						return EEInstructions.VTFM3;
					} else {
						return EEInstructions.VHTFM3;
					}
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000080) == 0x00000000) {
						return EEInstructions.VHTFM4;
					} else {
						return EEInstructions.VTFM4;
					}
				}
			}, EEInstructions.VMSCL, EEInstructions.VQMUL, Common.UNK,
			new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00210000) == 0x00000000) {
						if ((insn & 0x00020000) == 0x00000000) {
							return EEInstructions.VMMOV;
						} else {
							return EEInstructions.VMZERO;
						}
					} else {
						if ((insn & 0x00200000) == 0x00000000) {
							if ((insn & 0x00040000) == 0x00000000) {
								return EEInstructions.VMIDT;
							} else {
								return EEInstructions.VMONE;
							}
						} else {
							return EEInstructions.VROT;
						}
					}
				}
			}, };

	public static final Instruction instruction(final int insn) {
		return opcodeTable[(insn >> 26) & 0x0000003f].instance(insn);
	}
}
