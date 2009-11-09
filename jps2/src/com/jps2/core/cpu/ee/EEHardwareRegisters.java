package com.jps2.core.cpu.ee;

import com.jps2.core.cpu.ee.state.CpuState;
import com.jps2.core.memory.FastMemory;

public class EEHardwareRegisters extends FastMemory {
	public static final int RCNT0_COUNT = 0x10000000;
	public static final int RCNT0_MODE = 0x10000010;
	public static final int RCNT0_TARGET = 0x10000020;
	public static final int RCNT0_HOLD = 0x10000030;

	public static final int RCNT1_COUNT = 0x10000800;
	public static final int RCNT1_MODE = 0x10000810;
	public static final int RCNT1_TARGET = 0x10000820;
	public static final int RCNT1_HOLD = 0x10000830;

	public static final int RCNT2_COUNT = 0x10001000;
	public static final int RCNT2_MODE = 0x10001010;
	public static final int RCNT2_TARGET = 0x10001020;

	public static final int RCNT3_COUNT = 0x10001800;
	public static final int RCNT3_MODE = 0x10001810;
	public static final int RCNT3_TARGET = 0x10001820;

	public static final int IPU_CMD = 0x10002000;
	public static final int IPU_CTRL = 0x10002010;
	public static final int IPU_BP = 0x10002020;
	public static final int IPU_TOP = 0x10002030;

	public static final int GIF_CTRL = 0x10003000;
	public static final int GIF_MODE = 0x10003010;
	public static final int GIF_STAT = 0x10003020;
	public static final int GIF_TAG0 = 0x10003040;
	public static final int GIF_TAG1 = 0x10003050;
	public static final int GIF_TAG2 = 0x10003060;
	public static final int GIF_TAG3 = 0x10003070;
	public static final int GIF_CNT = 0x10003080;
	public static final int GIF_P3CNT = 0x10003090;
	public static final int GIF_P3TAG = 0x100030A0;

	// Vif Memory Locations
	public static final int VIF0_STAT = 0x10003800;
	public static final int VIF0_FBRST = 0x10003810;
	public static final int VIF0_ERR = 0x10003820;
	public static final int VIF0_MARK = 0x10003830;
	public static final int VIF0_CYCLE = 0x10003840;
	public static final int VIF0_MODE = 0x10003850;
	public static final int VIF0_NUM = 0x10003860;
	public static final int VIF0_MASK = 0x10003870;
	public static final int VIF0_CODE = 0x10003880;
	public static final int VIF0_ITOPS = 0x10003890;
	public static final int VIF0_ITOP = 0x100038d0;
	public static final int VIF0_TOP = 0x100038e0;
	public static final int VIF0_R0 = 0x10003900;
	public static final int VIF0_R1 = 0x10003910;
	public static final int VIF0_R2 = 0x10003920;
	public static final int VIF0_R3 = 0x10003930;
	public static final int VIF0_C0 = 0x10003940;
	public static final int VIF0_C1 = 0x10003950;
	public static final int VIF0_C2 = 0x10003960;
	public static final int VIF0_C3 = 0x10003970;

	public static final int VIF1_STAT = 0x10003c00;
	public static final int VIF1_FBRST = 0x10003c10;
	public static final int VIF1_ERR = 0x10003c20;
	public static final int VIF1_MARK = 0x10003c30;
	public static final int VIF1_CYCLE = 0x10003c40;
	public static final int VIF1_MODE = 0x10003c50;
	public static final int VIF1_NUM = 0x10003c60;
	public static final int VIF1_MASK = 0x10003c70;
	public static final int VIF1_CODE = 0x10003c80;
	public static final int VIF1_ITOPS = 0x10003c90;
	public static final int VIF1_BASE = 0x10003ca0;
	public static final int VIF1_OFST = 0x10003cb0;
	public static final int VIF1_TOPS = 0x10003cc0;
	public static final int VIF1_ITOP = 0x10003cd0;
	public static final int VIF1_TOP = 0x10003ce0;
	public static final int VIF1_R0 = 0x10003d00;
	public static final int VIF1_R1 = 0x10003d10;
	public static final int VIF1_R2 = 0x10003d20;
	public static final int VIF1_R3 = 0x10003d30;
	public static final int VIF1_C0 = 0x10003d40;
	public static final int VIF1_C1 = 0x10003d50;
	public static final int VIF1_C2 = 0x10003d60;
	public static final int VIF1_C3 = 0x10003d70;

	public static final int VIF0_FIFO = 0x10004000;
	public static final int VIF1_FIFO = 0x10005000;
	public static final int GIF_FIFO = 0x10006000;

	public static final int IPUout_FIFO = 0x10007000;
	public static final int IPUin_FIFO = 0x10007010;

	// VIF0
	public static final int D0_CHCR = 0x10008000;
	public static final int D0_MADR = 0x10008010;
	public static final int D0_QWC = 0x10008020;

	// VIF1
	public static final int D1_CHCR = 0x10009000;
	public static final int D1_MADR = 0x10009010;
	public static final int D1_QWC = 0x10009020;
	public static final int D1_TADR = 0x10009030;
	public static final int D1_ASR0 = 0x10009040;
	public static final int D1_ASR1 = 0x10009050;
	public static final int D1_SADR = 0x10009080;

	// GS
	public static final int D2_CHCR = 0x1000A000;
	public static final int D2_MADR = 0x1000A010;
	public static final int D2_QWC = 0x1000A020;
	public static final int D2_TADR = 0x1000A030;
	public static final int D2_ASR0 = 0x1000A040;
	public static final int D2_ASR1 = 0x1000A050;
	public static final int D2_SADR = 0x1000A080;

	// fromIPU
	public static final int D3_CHCR = 0x1000B000;
	public static final int D3_MADR = 0x1000B010;
	public static final int D3_QWC = 0x1000B020;
	public static final int D3_TADR = 0x1000B030;
	public static final int D3_SADR = 0x1000B080;

	// toIPU
	public static final int D4_CHCR = 0x1000B400;
	public static final int D4_MADR = 0x1000B410;
	public static final int D4_QWC = 0x1000B420;
	public static final int D4_TADR = 0x1000B430;
	public static final int D4_SADR = 0x1000B480;

	// SIF0
	public static final int D5_CHCR = 0x1000C000;
	public static final int D5_MADR = 0x1000C010;
	public static final int D5_QWC = 0x1000C020;

	// SIF1
	public static final int D6_CHCR = 0x1000C400;
	public static final int D6_MADR = 0x1000C410;
	public static final int D6_QWC = 0x1000C420;
	public static final int D6_TADR = 0x1000C430;

	// SIF2
	public static final int D7_CHCR = 0x1000C800;
	public static final int D7_MADR = 0x1000C810;
	public static final int D7_QWC = 0x1000C820;

	// fromSPR
	public static final int D8_CHCR = 0x1000D000;
	public static final int D8_MADR = 0x1000D010;
	public static final int D8_QWC = 0x1000D020;
	public static final int D8_SADR = 0x1000D080;
	public static final int SPR1_CHCR = 0x1000D400;

	public static final int DMAC_CTRL = 0x1000E000;
	public static final int DMAC_STAT = 0x1000E010;
	public static final int DMAC_PCR = 0x1000E020;
	public static final int DMAC_SQWC = 0x1000E030;
	public static final int DMAC_RBSR = 0x1000E040;
	public static final int DMAC_RBOR = 0x1000E050;
	public static final int DMAC_STADR = 0x1000E060;

	public static final int INTC_STAT = 0x1000F000;
	public static final int INTC_MASK = 0x1000F010;

	public static final int SIO_LCR = 0x1000F100;
	public static final int SIO_LSR = 0x1000F110;
	public static final int SIO_IER = 0x1000F120;
	public static final int SIO_ISR = 0x1000F130;//
	public static final int SIO_FCR = 0x1000F140;
	public static final int SIO_BGR = 0x1000F150;
	public static final int SIO_TXFIFO = 0x1000F180;
	public static final int SIO_RXFIFO = 0x1000F1C0;

	public static final int SBUS_F200 = 0x1000F200; // MSCOM
	public static final int SBUS_F210 = 0x1000F210; // SMCOM
	public static final int SBUS_F220 = 0x1000F220; // MSFLG
	public static final int SBUS_F230 = 0x1000F230; // SMFLG
	public static final int SBUS_F240 = 0x1000F240;
	public static final int SBUS_F250 = 0x1000F250;
	public static final int SBUS_F260 = 0x1000F260;

	public static final int MCH_RICM = 0x1000F430;
	public static final int MCH_DRD = 0x1000F440;

	public static final int DMAC_ENABLER = 0x1000F520;
	public static final int DMAC_ENABLEW = 0x1000F590;

	public EEHardwareRegisters() {
		super("EE Hardware Registers", 0xFFFF);
	}
	
	public CpuState cpu;

	public void setCpu(final CpuState cpu) {
		this.cpu = cpu;
	}
}
