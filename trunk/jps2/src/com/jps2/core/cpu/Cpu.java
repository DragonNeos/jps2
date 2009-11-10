package com.jps2.core.cpu;

import com.jps2.core.cpu.registers.CP0Register;

public abstract class Cpu {

	public static final int	ZERO			= 0;
	public static final int	AT				= 1;
	public static final int	V0				= 2;
	public static final int	V1				= 3;
	public static final int	A0				= 4;
	public static final int	A1				= 5;
	public static final int	A2				= 6;
	public static final int	A3				= 7;
	public static final int	T0				= 8;
	public static final int	T1				= 9;
	public static final int	T2				= 10;
	public static final int	T3				= 11;
	public static final int	T4				= 12;
	public static final int	T5				= 13;
	public static final int	T6				= 14;
	public static final int	T7				= 15;
	public static final int	S0				= 16;
	public static final int	S1				= 17;
	public static final int	S2				= 18;
	public static final int	S3				= 19;
	public static final int	S4				= 20;
	public static final int	S5				= 21;
	public static final int	S6				= 22;
	public static final int	S7				= 23;
	public static final int	T8				= 24;
	public static final int	T9				= 25;
	public static final int	K0				= 26;
	public static final int	K1				= 27;
	public static final int	GP				= 28;
	public static final int	SP				= 29;
	public static final int	FP				= 30;
	public static final int	RA				= 31;

	public static final int	CP0_INDEX		= 0;
	public static final int	CP0_RANDOM		= 1;
	public static final int	CP0_ENTRYLO0	= 2;
	public static final int	CP0_ENTRYLO1	= 3;
	public static final int	CP0_CONTEXT		= 4;
	public static final int	CP0_PAGEMASK	= 5;
	public static final int	CP0_WIRED		= 6;
	public static final int	CP0_RESERVED0	= 7;
	public static final int	CP0_BADVADDR	= 8;
	public static final int	CP0_COUNT		= 9;
	public static final int	CP0_ENTRYHI		= 10;
	public static final int	CP0_COMPARE		= 11;
	public static final int	CP0_STATUS		= 12;
	public static final int	CP0_CAUSE		= 13;
	public static final int	CP0_EPC			= 14;
	public static final int	CP0_PRID		= 15;
	public static final int	CP0_CONFIG		= 16;
	public static final int	CP0_LLADDR		= 17;
	public static final int	CP0_WATCHLO		= 18;
	public static final int	CP0_WATCHHI		= 19;
	public static final int	CP0_XCONTEXT	= 20;
	public static final int	CP0_RESERVED1	= 21;
	public static final int	CP0_RESERVED2	= 22;
	public static final int	CP0_RESERVED3	= 23;
	public static final int	CP0_RESERVED4	= 24;
	public static final int	CP0_RESERVED5	= 25;
	public static final int	CP0_ECC			= 26;
	public static final int	CP0_CACHEERR	= 27;
	public static final int	CP0_TAGLO		= 28;
	public static final int	CP0_TAGHI		= 29;
	public static final int	CP0_ERROREPC	= 30;
	public static final int	CP0_RESERVED6	= 31;

	public int				pc;
	public int				npc;
	public int				cycle;
	public int				interrupt;

	public CP0Register[]	cp0;

	public Processor		processor;

	public abstract void nextPc();

	public abstract int nextOpcode();
}
