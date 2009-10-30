package com.jps2.core.cpu.ee;

import com.jps2.core.cpu.Common;
import com.jps2.core.cpu.Common.Instruction;
import com.jps2.core.cpu.Common.STUB;

public class EEDecoder {

	private static final Instruction opcodeTable[] = {
			new STUB() {
				@Override
				public Instruction instance(final int insn) {
					return specialTable[(insn >> 0) & 0x0000003f]
							.instance(insn);
				}
			},
			new STUB() {
				@Override
				public Instruction instance(final int insn) {
					return regimmTable[(insn >> 16) & 0x0000001f]
							.instance(insn);
				}
			},
			EEInstructions.J,
			EEInstructions.JAL,
			EEInstructions.BEQ,
			EEInstructions.BNE,
			EEInstructions.BLEZ,
			EEInstructions.BGTZ,
			EEInstructions.ADDI,
			EEInstructions.ADDIU,
			EEInstructions.SLTI,
			EEInstructions.SLTIU,
			EEInstructions.ANDI,
			EEInstructions.ORI,
			EEInstructions.XORI,
			EEInstructions.LUI,
			new STUB() {
				@Override
				public Instruction instance(final int insn) {
					return cpo0Table[(insn >> 21) & 0x0000001F].instance(insn);
				}
			},
			new STUB() {
				@Override
				public Instruction instance(final int insn) {
					return cpo1Table[(insn >> 21) & 0x0000001F].instance(insn);
				}
			},
			// new STUB() {
			// @Override
			// public Instruction instance(int insn) {
			// throw new RuntimeException("não implementado");
			// TODO
			// if ((insn & 0x00200000) == 0x00000000) {
			// return table_7[(insn >> 16) & 0x00000003].instance(insn);
			// } else {
			// if ((insn & 0x00000080) == 0x00000000) {
			// if ((insn & 0x00800000) == 0x00000000) {
			// return EEInstructions.MFV;
			// } else {
			// return EEInstructions.MTV;
			// }
			// } else {
			// if ((insn & 0x00800000) == 0x00000000) {
			// return EEInstructions.MFVC;
			// } else {
			// return EEInstructions.MTVC;
			// }
			// }
			// }
			// }
			// }
			Common.UNK,
			new STUB() {
				@Override
				public Instruction instance(final int insn) {
					// TODO
					return cpo1xTable[(insn >> 23) & 0x00000007].instance(insn);
				}
			}, EEInstructions.BEQL, EEInstructions.BNEL, EEInstructions.BLEZL,
			EEInstructions.BGTZL, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return table_8[(insn >> 23) & 0x00000003].instance(insn);
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return table_9[(insn >> 23) & 0x00000007].instance(insn);
				}
			}, Common.UNK, EEInstructions.LDR, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000002) == 0x00000000) {
						if ((insn & 0x00000004) == 0x00000000) {
							return EEInstructions.HALT;
						} else {
							return EEInstructions.MFIC;
						}
					} else {
						return EEInstructions.MTIC;
					}
				}
			}, Common.UNK, Common.UNK, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000021) == 0x00000020) {
						if ((insn & 0x00000080) == 0x00000000) {
							if ((insn & 0x00000100) == 0x00000000) {
								return EEInstructions.SEH;
							} else {
								return EEInstructions.BITREV;
							}
						} else {
							if ((insn & 0x00000040) == 0x00000000) {
								return EEInstructions.WSBH;
							} else {
								return EEInstructions.WSBW;
							}
						}
					} else {
						if ((insn & 0x00000001) == 0x00000000) {
							if ((insn & 0x00000004) == 0x00000000) {
								return EEInstructions.EXT;
							} else {
								return EEInstructions.INS;
							}
						} else {
							return EEInstructions.SEB;
						}
					}
				}
			}, EEInstructions.LB, EEInstructions.LH, EEInstructions.LWL,
			EEInstructions.LW, EEInstructions.LBU, EEInstructions.LHU,
			EEInstructions.LWR, Common.UNK, EEInstructions.SB,
			EEInstructions.SH, EEInstructions.SWL, EEInstructions.SW,
			Common.UNK, Common.UNK, EEInstructions.SWR, Common.UNK,
			EEInstructions.LL, EEInstructions.LWC1, EEInstructions.LVS,
			EEInstructions.PREF, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return table_12[(insn >> 18) & 0x0000001f].instance(insn);
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000002) == 0x00000000) {
						return EEInstructions.LVLQ;
					} else {
						return EEInstructions.LVRQ;
					}
				}
			}, EEInstructions.LVQ, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return table_13[(insn >> 24) & 0x00000003].instance(insn);
				}
			}, EEInstructions.SC, EEInstructions.SWC1, EEInstructions.SVS,
			Common.UNK, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return table_14[(insn >> 23) & 0x00000007].instance(insn);
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000002) == 0x00000000) {
						return EEInstructions.SVLQ;
					} else {
						return EEInstructions.SVRQ;
					}
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000002) == 0x00000000) {
						return EEInstructions.SVQ;
					} else {
						return EEInstructions.SWB;
					}
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000001) == 0x00000000) {
						if ((insn & 0x00000020) == 0x00000000) {
							return EEInstructions.VNOP;
						} else {
							return EEInstructions.VSYNC;
						}
					} else {
						return EEInstructions.VFLUSH;
					}
				}
			}, };
	public static final Instruction specialTable[] = { new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x001fffc0) == 0x00000000) {
				return EEInstructions.NOP;
			} else {
				return EEInstructions.SLL;
			}
		}
	}, new STUB() {

		@Override
		public Instruction instance(final int insn) {
			if ((insn & 0x10000) == 0x10000) {
				return EEInstructions.MOVT;
			} else {
				return EEInstructions.MOVF;
			}
		}
	}, EEInstructions.SRL, EEInstructions.SRA, EEInstructions.SLLV, Common.UNK,
			EEInstructions.SRLV, EEInstructions.SRAV, EEInstructions.JR,
			EEInstructions.JALR, EEInstructions.MOVZ, EEInstructions.MOVN,
			EEInstructions.SYSCALL, EEInstructions.BREAK, Common.UNK,
			EEInstructions.SYNC, EEInstructions.MFHI, EEInstructions.MTHI,
			EEInstructions.MFLO, EEInstructions.MTLO, EEInstructions.DSLLV,
			Common.UNK, EEInstructions.DSRLV, EEInstructions.DSRAV,
			EEInstructions.MULT, EEInstructions.MULTU, EEInstructions.DIV,
			EEInstructions.DIVU, EEInstructions.DMULT, EEInstructions.DMULTU,
			EEInstructions.DDIV, EEInstructions.DDIVU, EEInstructions.ADD,
			EEInstructions.ADDU, EEInstructions.SUB, EEInstructions.SUBU,
			EEInstructions.AND, EEInstructions.OR, EEInstructions.XOR,
			EEInstructions.NOR, EEInstructions.MFSA, Common.UNK,
			EEInstructions.SLT, EEInstructions.SLTU, EEInstructions.DADD,
			EEInstructions.DADDU, EEInstructions.DSUB, EEInstructions.DSUBU,
			EEInstructions.TGE, EEInstructions.TGEU, EEInstructions.TLT,
			EEInstructions.TLTU, EEInstructions.TEQ, Common.UNK,
			EEInstructions.TNE, Common.UNK, EEInstructions.DSLL, Common.UNK,
			EEInstructions.DSRL, EEInstructions.DSRA, EEInstructions.DSLL32,
			Common.UNK, EEInstructions.DSRL32, EEInstructions.DSRA32, };
	public static final Instruction regimmTable[] = { EEInstructions.BLTZ,
			EEInstructions.BGEZ, EEInstructions.BLTZL, EEInstructions.BGEZL,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.TGEI, EEInstructions.TGEIU, EEInstructions.TLTI,
			EEInstructions.TLTIU, Common.UNK, EEInstructions.TNEI, Common.UNK,
			EEInstructions.BLTZAL, EEInstructions.BGEZAL,
			EEInstructions.BLTZALL, EEInstructions.BGEZALL, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK };
	private static final STUB coStub = new STUB() {
		@Override
		public Instruction instance(final int insn) {
			return coTable[insn & 0x0000003f];
		}
	};
	private static final Instruction cpo0Table[] = { EEInstructions.MFC0,
			EEInstructions.DMFC0, Common.UNK, Common.UNK, EEInstructions.MTC0,
			EEInstructions.DMTC0, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, coStub, coStub, coStub, coStub, coStub,
			coStub, coStub, coStub, coStub, coStub, coStub, coStub, coStub,
			coStub, coStub, coStub };

	public static final Instruction coTable[] = { Common.UNK,
			EEInstructions.TLBR, EEInstructions.TLBWI, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.TLBWR, Common.UNK, EEInstructions.TLBP,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			EEInstructions.ERET, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, EEInstructions.DERET,
			EEInstructions.WAIT, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK };

	public static final Instruction cpo1Table[] = { EEInstructions.MFC1,
			EEInstructions.DMFC1, EEInstructions.CFC1, Common.UNK,
			EEInstructions.MTC1, EEInstructions.DMTC1, EEInstructions.CTC1,
			Common.UNK, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return bc1Table[(insn >> 16) & 0x00000003].instance(insn);
				}
			}, Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return table_6[(insn >> 0) & 0x0000001f].instance(insn);
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return table_6[(insn >> 0) & 0x0000001f].instance(insn);
				}
			}, Common.UNK, Common.UNK, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return table_6[(insn >> 0) & 0x0000001f].instance(insn);
				}
			}, new STUB() {

				@Override
				public Instruction instance(final int insn) {
					return table_6[(insn >> 0) & 0x0000001f].instance(insn);
				}
			}, Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, Common.UNK, Common.UNK, Common.UNK, Common.UNK };
	public static final Instruction bc1Table[] = { EEInstructions.BC1F,
			EEInstructions.BC1T, EEInstructions.BC1FL, EEInstructions.BC1TL, };

	public static final Instruction cpo1xTable[] = { EEInstructions.LWXC1,
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

	public static final Instruction table_6[] = { EEInstructions.ADD_S,
			EEInstructions.SUB_S, EEInstructions.MUL_S, EEInstructions.DIV_S,
			new STUB() {

				@Override
				public Instruction instance(final int insn) {
					if ((insn & 0x00000020) == 0x00000000) {
						return EEInstructions.SQRT_S;
					} else {
						return EEInstructions.CVT_W_S;
					}
				}
			}, EEInstructions.ABS_S, EEInstructions.MOV_S,
			EEInstructions.NEG_S, Common.UNK, Common.UNK, Common.UNK,
			Common.UNK, EEInstructions.ROUND_W_S, EEInstructions.TRUNC_W_S,
			EEInstructions.CEIL_W_S, EEInstructions.FLOOR_W_S,
			EEInstructions.C_COND_S, EEInstructions.C_COND_S,
			EEInstructions.C_COND_S, EEInstructions.C_COND_S,
			EEInstructions.C_COND_S, EEInstructions.C_COND_S,
			EEInstructions.C_COND_S, EEInstructions.C_COND_S,
			EEInstructions.C_COND_S, EEInstructions.C_COND_S,
			EEInstructions.C_COND_S, EEInstructions.C_COND_S,
			EEInstructions.C_COND_S, EEInstructions.C_COND_S,
			EEInstructions.C_COND_S, EEInstructions.C_COND_S, };
	public static final Instruction table_7[] = { EEInstructions.BVF,
			EEInstructions.BVT, EEInstructions.BVFL, EEInstructions.BVTL, };
	public static final Instruction table_8[] = { EEInstructions.VADD,
			EEInstructions.VSUB, EEInstructions.VSBN, EEInstructions.VDIV, };
	public static final Instruction table_9[] = { EEInstructions.VMUL,
			EEInstructions.VDOT, EEInstructions.VSCL, Common.UNK,
			EEInstructions.VHDP, EEInstructions.VDET, EEInstructions.VCRS,
			Common.UNK, };
	public static final Instruction table_10[] = { EEInstructions.VCMP,
			Common.UNK, EEInstructions.VMIN, EEInstructions.VMAX,
			EEInstructions.VSLT, EEInstructions.VSCMP, EEInstructions.VSGE,
			Common.UNK, };
	public static final Instruction table_12[] = { new STUB() {

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
	public static final Instruction table_13[] = { EEInstructions.VPFXS,
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
	public static final Instruction table_14[] = { EEInstructions.VMMUL,
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
