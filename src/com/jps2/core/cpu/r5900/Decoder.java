package com.jps2.core.cpu.r5900;

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
        new STUB() {
            @Override
            public Instruction instance(final int insn) {
                return cpo1Table[(insn >> 21) & 0x0000001F].instance(insn);
            }
        },
//        new STUB() {
//            @Override
//            public Instruction instance(int insn) {
//            	throw new RuntimeException("não implementado");
            	//TODO
//                if ((insn & 0x00200000) == 0x00000000) {
//                    return table_7[(insn >> 16) & 0x00000003].instance(insn);
//                } else {
//                    if ((insn & 0x00000080) == 0x00000000) {
//                        if ((insn & 0x00800000) == 0x00000000) {
//                            return Instructions.MFV;
//                        } else {
//                            return Instructions.MTV;
//                        }
//                    } else {
//                        if ((insn & 0x00800000) == 0x00000000) {
//                            return Instructions.MFVC;
//                        } else {
//                            return Instructions.MTVC;
//                        }
//                    }
//                }
//            }
//        }
        Common.UNK,
        new STUB() {
            @Override
            public Instruction instance(final int insn) {
            	//TODO
                return cpo1xTable[(insn >> 23) & 0x00000007].instance(insn);
            }
        },
        Instructions.BEQL,
        Instructions.BNEL,
        Instructions.BLEZL,
        Instructions.BGTZL,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return table_8[(insn >> 23) & 0x00000003].instance(insn);
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return table_9[(insn >> 23) & 0x00000007].instance(insn);
            }
        },
        Common.UNK,
        Instructions.LDR,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000002) == 0x00000000) {
                    if ((insn & 0x00000004) == 0x00000000) {
                        return Instructions.HALT;
                    } else {
                        return Instructions.MFIC;
                    }
                } else {
                    return Instructions.MTIC;
                }
            }
        },
        Common.UNK,
        Common.UNK,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000021) == 0x00000020) {
                    if ((insn & 0x00000080) == 0x00000000) {
                        if ((insn & 0x00000100) == 0x00000000) {
                            return Instructions.SEH;
                        } else {
                            return Instructions.BITREV;
                        }
                    } else {
                        if ((insn & 0x00000040) == 0x00000000) {
                            return Instructions.WSBH;
                        } else {
                            return Instructions.WSBW;
                        }
                    }
                } else {
                    if ((insn & 0x00000001) == 0x00000000) {
                        if ((insn & 0x00000004) == 0x00000000) {
                            return Instructions.EXT;
                        } else {
                            return Instructions.INS;
                        }
                    } else {
                        return Instructions.SEB;
                    }
                }
            }
        },
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
        Instructions.LL,
        Instructions.LWC1,
        Instructions.LVS,
        Instructions.PREF,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return table_12[(insn >> 18) & 0x0000001f].instance(insn);
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000002) == 0x00000000) {
                    return Instructions.LVLQ;
                } else {
                    return Instructions.LVRQ;
                }
            }
        },
        Instructions.LVQ,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return table_13[(insn >> 24) & 0x00000003].instance(insn);
            }
        },
        Instructions.SC,
        Instructions.SWC1,
        Instructions.SVS,
        Common.UNK,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return table_14[(insn >> 23) & 0x00000007].instance(insn);
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000002) == 0x00000000) {
                    return Instructions.SVLQ;
                } else {
                    return Instructions.SVRQ;
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000002) == 0x00000000) {
                    return Instructions.SVQ;
                } else {
                    return Instructions.SWB;
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000001) == 0x00000000) {
                    if ((insn & 0x00000020) == 0x00000000) {
                        return Instructions.VNOP;
                    } else {
                        return Instructions.VSYNC;
                    }
                } else {
                    return Instructions.VFLUSH;
                }
            }
        },
    };
    public static final Instruction specialTable[] = {
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x001fffc0) == 0x00000000) {
                    return Instructions.NOP;
                } else {
                    return Instructions.SLL;
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
            	if ((insn & 0x10000) == 0x10000){
            		return Instructions.MOVT;
            	}else{
            		return Instructions.MOVF;
            	}
            }
        },
        Instructions.SRL,
        Instructions.SRA,
        Instructions.SLLV,
        Common.UNK,
        Instructions.SRLV,
        Instructions.SRAV,
        Instructions.JR,
        Instructions.JALR,
        Instructions.MOVZ,
        Instructions.MOVN,
        Instructions.SYSCALL,
        Instructions.BREAK,
        Common.UNK,
        Instructions.SYNC,
        Instructions.MFHI,
        Instructions.MTHI,
        Instructions.MFLO,
        Instructions.MTLO,
        Instructions.DSLLV,
        Common.UNK,
        Instructions.DSRLV,
        Instructions.DSRAV,
        Instructions.MULT,
        Instructions.MULTU,
        Instructions.DIV,
        Instructions.DIVU,
        Instructions.DMULT,
        Instructions.DMULTU,
        Instructions.DDIV,
        Instructions.DDIVU,
        Instructions.ADD,
        Instructions.ADDU,
        Instructions.SUB,
        Instructions.SUBU,
        Instructions.AND,
        Instructions.OR,
        Instructions.XOR,
        Instructions.NOR,
        Instructions.MFSA,
        Common.UNK,
        Instructions.SLT,
        Instructions.SLTU,
        Instructions.DADD,
        Instructions.DADDU,
        Instructions.DSUB,
        Instructions.DSUBU,
        Instructions.TGE,
        Instructions.TGEU,
        Instructions.TLT,
        Instructions.TLTU,
        Instructions.TEQ,
        Common.UNK,
        Instructions.TNE,
        Common.UNK,
        Instructions.DSLL,
        Common.UNK,
        Instructions.DSRL,
        Instructions.DSRA,
        Instructions.DSLL32,
        Common.UNK,
        Instructions.DSRL32,
        Instructions.DSRA32,
    };
    public static final Instruction regimmTable[] = {
        Instructions.BLTZ,
        Instructions.BGEZ,
        Instructions.BLTZL,
        Instructions.BGEZL,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Instructions.TGEI,
        Instructions.TGEIU,
        Instructions.TLTI,
        Instructions.TLTIU,
        Common.UNK,
        Instructions.TNEI,
        Common.UNK,
        Instructions.BLTZAL,
        Instructions.BGEZAL,
        Instructions.BLTZALL,
        Instructions.BGEZALL,
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
    private static final STUB coStub = new STUB(){
    	@Override
        public Instruction instance(final int insn) {
            return coTable[insn& 0x0000003f];
        }
    };
    private static final Instruction cpo0Table[] = {
        Instructions.MFC0,
        Instructions.DMFC0,
        Common.UNK,
        Common.UNK,
        Instructions.MTC0,
        Instructions.DMTC0,
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
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub,
        coStub
    };
    
    public static final Instruction coTable[] = {
    	Common.UNK,
    	Instructions.TLBR,
    	Instructions.TLBWI,
    	Common.UNK,
    	Common.UNK,
    	Common.UNK,
    	Instructions.TLBWR,
    	Common.UNK,
    	Instructions.TLBP,
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
    	Instructions.ERET,
    	Common.UNK,
    	Common.UNK,
    	Common.UNK,
    	Common.UNK,
    	Common.UNK,
    	Common.UNK,
    	Instructions.DERET,
    	Instructions.WAIT,
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
   
    public static final Instruction cpo1Table[] = {
        Instructions.MFC1,
        Instructions.DMFC1,
        Instructions.CFC1,
        Common.UNK,
        Instructions.MTC1,
        Instructions.DMTC1,
        Instructions.CTC1,
        Common.UNK,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return bc1Table[(insn >> 16) & 0x00000003].instance(insn);
            }
        },
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return table_6[(insn >> 0) & 0x0000001f].instance(insn);
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return table_6[(insn >> 0) & 0x0000001f].instance(insn);
            }
        },
        Common.UNK,
        Common.UNK,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return table_6[(insn >> 0) & 0x0000001f].instance(insn);
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                return table_6[(insn >> 0) & 0x0000001f].instance(insn);
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
        Common.UNK
    };
    public static final Instruction bc1Table[] = {
        Instructions.BC1F,
        Instructions.BC1T,
        Instructions.BC1FL,
        Instructions.BC1TL,
    };
    
    public static final Instruction cpo1xTable[] = {
    	Instructions.LWXC1,
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
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK
    };
    
    public static final Instruction table_6[] = {
        Instructions.ADD_S,
        Instructions.SUB_S,
        Instructions.MUL_S,
        Instructions.DIV_S,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000020) == 0x00000000) {
                    return Instructions.SQRT_S;
                } else {
                    return Instructions.CVT_W_S;
                }
            }
        },
        Instructions.ABS_S,
        Instructions.MOV_S,
        Instructions.NEG_S,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Common.UNK,
        Instructions.ROUND_W_S,
        Instructions.TRUNC_W_S,
        Instructions.CEIL_W_S,
        Instructions.FLOOR_W_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
        Instructions.C_COND_S,
    };
    public static final Instruction table_7[] = {
        Instructions.BVF,
        Instructions.BVT,
        Instructions.BVFL,
        Instructions.BVTL,
    };
    public static final Instruction table_8[] = {
        Instructions.VADD,
        Instructions.VSUB,
        Instructions.VSBN,
        Instructions.VDIV,
    };
    public static final Instruction table_9[] = {
        Instructions.VMUL,
        Instructions.VDOT,
        Instructions.VSCL,
        Common.UNK,
        Instructions.VHDP,
        Instructions.VDET,
        Instructions.VCRS,
        Common.UNK,
    };
    public static final Instruction table_10[] = {
        Instructions.VCMP,
        Common.UNK,
        Instructions.VMIN,
        Instructions.VMAX,
        Instructions.VSLT,
        Instructions.VSCMP,
        Instructions.VSGE,
        Common.UNK,
    };
    public static final Instruction table_12[] = {
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VMOV;
                    } else {
                        return Instructions.VNEG;
                    }
                } else {
                    if ((insn & 0x02020000) == 0x00000000) {
                        return Instructions.VABS;
                    } else {
                        if ((insn & 0x02000000) == 0x00000000) {
                            return Instructions.VIDT;
                        } else {
                            if ((insn & 0x01800000) == 0x00000000) {
                                return Instructions.VF2IN;
                            } else {
                                if ((insn & 0x01000000) == 0x00000000) {
                                    return Instructions.VI2F;
                                } else {
                                    return Instructions.VWBN;
                                }
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VSAT0;
                    } else {
                        return Instructions.VZERO;
                    }
                } else {
                    if ((insn & 0x02020000) == 0x00000000) {
                        return Instructions.VSAT1;
                    } else {
                        if ((insn & 0x02000000) == 0x00000000) {
                            return Instructions.VONE;
                        } else {
                            if ((insn & 0x01800000) == 0x00000000) {
                                return Instructions.VF2IN;
                            } else {
                                if ((insn & 0x01000000) == 0x00000000) {
                                    return Instructions.VI2F;
                                } else {
                                    return Instructions.VWBN;
                                }
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x01800000) == 0x00000000) {
                    return Instructions.VF2IN;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VI2F;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x01800000) == 0x00000000) {
                    return Instructions.VF2IN;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VI2F;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VRCP;
                    } else {
                        return Instructions.VSIN;
                    }
                } else {
                    if ((insn & 0x02020000) == 0x00000000) {
                        return Instructions.VRSQ;
                    } else {
                        if ((insn & 0x02000000) == 0x00000000) {
                            return Instructions.VCOS;
                        } else {
                            if ((insn & 0x01800000) == 0x00000000) {
                                return Instructions.VF2IN;
                            } else {
                                if ((insn & 0x01000000) == 0x00000000) {
                                    return Instructions.VI2F;
                                } else {
                                    return Instructions.VWBN;
                                }
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VEXP2;
                    } else {
                        return Instructions.VSQRT;
                    }
                } else {
                    if ((insn & 0x02020000) == 0x00000000) {
                        return Instructions.VLOG2;
                    } else {
                        if ((insn & 0x02000000) == 0x00000000) {
                            return Instructions.VASIN;
                        } else {
                            if ((insn & 0x01800000) == 0x00000000) {
                                return Instructions.VF2IN;
                            } else {
                                if ((insn & 0x01000000) == 0x00000000) {
                                    return Instructions.VI2F;
                                } else {
                                    return Instructions.VWBN;
                                }
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02020000) == 0x00000000) {
                    return Instructions.VNRCP;
                } else {
                    if ((insn & 0x02000000) == 0x00000000) {
                        return Instructions.VNSIN;
                    } else {
                        if ((insn & 0x01800000) == 0x00000000) {
                            return Instructions.VF2IN;
                        } else {
                            if ((insn & 0x01000000) == 0x00000000) {
                                return Instructions.VI2F;
                            } else {
                                return Instructions.VWBN;
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000000) == 0x00000000) {
                    return Instructions.VREXP2;
                } else {
                    if ((insn & 0x01800000) == 0x00000000) {
                        return Instructions.VF2IN;
                    } else {
                        if ((insn & 0x01000000) == 0x00000000) {
                            return Instructions.VI2F;
                        } else {
                            return Instructions.VWBN;
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VRNDS;
                    } else {
                        return Instructions.VRNDF1;
                    }
                } else {
                    if ((insn & 0x02020000) == 0x00000000) {
                        return Instructions.VRNDI;
                    } else {
                        if ((insn & 0x02000000) == 0x00000000) {
                            return Instructions.VRNDF2;
                        } else {
                            if ((insn & 0x01800000) == 0x00800000) {
                                return Instructions.VCMOVT;
                            } else {
                                if ((insn & 0x01000000) == 0x00000000) {
                                    return Instructions.VF2IZ;
                                } else {
                                    return Instructions.VWBN;
                                }
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x01800000) == 0x00800000) {
                    return Instructions.VCMOVT;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2IZ;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x01800000) == 0x00800000) {
                    return Instructions.VCMOVF;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2IZ;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x01800000) == 0x00800000) {
                    return Instructions.VCMOVF;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2IZ;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    return Instructions.VF2H;
                } else {
                    if ((insn & 0x02000000) == 0x00000000) {
                        return Instructions.VH2F;
                    } else {
                        if ((insn & 0x01000000) == 0x00000000) {
                            return Instructions.VF2IZ;
                        } else {
                            return Instructions.VWBN;
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    return Instructions.VSBZ;
                } else {
                    if ((insn & 0x02000000) == 0x00000000) {
                        return Instructions.VLGB;
                    } else {
                        if ((insn & 0x01000000) == 0x00000000) {
                            return Instructions.VF2IZ;
                        } else {
                            return Instructions.VWBN;
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VUC2I;
                    } else {
                        return Instructions.VUS2I;
                    }
                } else {
                    if ((insn & 0x02020000) == 0x00000000) {
                        return Instructions.VC2I;
                    } else {
                        if ((insn & 0x02000000) == 0x00000000) {
                            return Instructions.VS2I;
                        } else {
                            if ((insn & 0x01000000) == 0x00000000) {
                                return Instructions.VF2IZ;
                            } else {
                                return Instructions.VWBN;
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VI2UC;
                    } else {
                        return Instructions.VI2US;
                    }
                } else {
                    if ((insn & 0x02020000) == 0x00000000) {
                        return Instructions.VI2C;
                    } else {
                        if ((insn & 0x02000000) == 0x00000000) {
                            return Instructions.VI2S;
                        } else {
                            if ((insn & 0x01000000) == 0x00000000) {
                                return Instructions.VF2IZ;
                            } else {
                                return Instructions.VWBN;
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VSRT1;
                    } else {
                        return Instructions.VBFY1;
                    }
                } else {
                    if ((insn & 0x02020000) == 0x00000000) {
                        return Instructions.VSRT2;
                    } else {
                        if ((insn & 0x02000000) == 0x00000000) {
                            return Instructions.VBFY2;
                        } else {
                            if ((insn & 0x01000000) == 0x00000000) {
                                return Instructions.VF2IU;
                            } else {
                                return Instructions.VWBN;
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VOCP;
                    } else {
                        return Instructions.VFAD;
                    }
                } else {
                    if ((insn & 0x02020000) == 0x00000000) {
                        return Instructions.VSOCP;
                    } else {
                        if ((insn & 0x02000000) == 0x00000000) {
                            return Instructions.VAVG;
                        } else {
                            if ((insn & 0x01000000) == 0x00000000) {
                                return Instructions.VF2IU;
                            } else {
                                return Instructions.VWBN;
                            }
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00000000) {
                    return Instructions.VSRT3;
                } else {
                    if ((insn & 0x02000000) == 0x00000000) {
                        return Instructions.VSRT4;
                    } else {
                        if ((insn & 0x01000000) == 0x00000000) {
                            return Instructions.VF2IU;
                        } else {
                            return Instructions.VWBN;
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x01000000) == 0x00000000) {
                    return Instructions.VF2IU;
                } else {
                    return Instructions.VWBN;
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000080) == 0x00000000) {
                    return Instructions.VMFVC;
                } else {
                    if ((insn & 0x02000000) == 0x00000000) {
                        return Instructions.VMTVC;
                    } else {
                        if ((insn & 0x01000000) == 0x00000000) {
                            return Instructions.VF2IU;
                        } else {
                            return Instructions.VWBN;
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x01000000) == 0x00000000) {
                    return Instructions.VF2IU;
                } else {
                    return Instructions.VWBN;
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02010000) == 0x00010000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VT4444;
                    } else {
                        return Instructions.VT5650;
                    }
                } else {
                    if ((insn & 0x02000000) == 0x00000000) {
                        return Instructions.VT5551;
                    } else {
                        if ((insn & 0x01000000) == 0x00000000) {
                            return Instructions.VF2IU;
                        } else {
                            return Instructions.VWBN;
                        }
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x01000000) == 0x00000000) {
                    return Instructions.VF2IU;
                } else {
                    return Instructions.VWBN;
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000000) == 0x00000000) {
                    return Instructions.VCST;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2ID;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000000) == 0x00000000) {
                    return Instructions.VCST;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2ID;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000000) == 0x00000000) {
                    return Instructions.VCST;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2ID;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000000) == 0x00000000) {
                    return Instructions.VCST;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2ID;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000000) == 0x00000000) {
                    return Instructions.VCST;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2ID;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000000) == 0x00000000) {
                    return Instructions.VCST;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2ID;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000000) == 0x00000000) {
                    return Instructions.VCST;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2ID;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x02000000) == 0x00000000) {
                    return Instructions.VCST;
                } else {
                    if ((insn & 0x01000000) == 0x00000000) {
                        return Instructions.VF2ID;
                    } else {
                        return Instructions.VWBN;
                    }
                }
            }
        },
    };
    public static final Instruction table_13[] = {
        Instructions.VPFXS,
        Instructions.VPFXT,
        Instructions.VPFXD,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00800000) == 0x00000000) {
                    return Instructions.VIIM;
                } else {
                    return Instructions.VFIM;
                }
            }
        },
    };
    public static final Instruction table_14[] = {
        Instructions.VMMUL,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000080) == 0x00000000) {
                    return Instructions.VHTFM2;
                } else {
                    return Instructions.VTFM2;
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000080) == 0x00000000) {
                    return Instructions.VTFM3;
                } else {
                    return Instructions.VHTFM3;
                }
            }
        },
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00000080) == 0x00000000) {
                    return Instructions.VHTFM4;
                } else {
                    return Instructions.VTFM4;
                }
            }
        },
        Instructions.VMSCL,
        Instructions.VQMUL,
        Common.UNK,
        new STUB() {

            @Override
            public Instruction instance(final int insn) {
                if ((insn & 0x00210000) == 0x00000000) {
                    if ((insn & 0x00020000) == 0x00000000) {
                        return Instructions.VMMOV;
                    } else {
                        return Instructions.VMZERO;
                    }
                } else {
                    if ((insn & 0x00200000) == 0x00000000) {
                        if ((insn & 0x00040000) == 0x00000000) {
                            return Instructions.VMIDT;
                        } else {
                            return Instructions.VMONE;
                        }
                    } else {
                        return Instructions.VROT;
                    }
                }
            }
        },
    };

    public static final Instruction instruction(final int insn) {
        return opcodeTable[(insn >> 26) & 0x0000003f].instance(insn);
    }
}
