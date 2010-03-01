package com.jps2.core.cpu.ee;

import org.apache.log4j.Logger;

import com.jps2.core.cpu.ee.state.CpuState;
import com.jps2.core.memory.FastMemory;

public class EEHardwareRegisters extends FastMemory {

	public static final Logger	logger			= Logger.getLogger(EEHardwareRegisters.class);

	public static final int		RCNT0_COUNT		= 0x0000;
	public static final int		RCNT0_MODE		= 0x0010;
	public static final int		RCNT0_TARGET	= 0x0020;
	public static final int		RCNT0_HOLD		= 0x0030;

	public static final int		RCNT1_COUNT		= 0x0800;
	public static final int		RCNT1_MODE		= 0x0810;
	public static final int		RCNT1_TARGET	= 0x0820;
	public static final int		RCNT1_HOLD		= 0x0830;

	public static final int		RCNT2_COUNT		= 0x1000;
	public static final int		RCNT2_MODE		= 0x1010;
	public static final int		RCNT2_TARGET	= 0x1020;

	public static final int		RCNT3_COUNT		= 0x1800;
	public static final int		RCNT3_MODE		= 0x1810;
	public static final int		RCNT3_TARGET	= 0x1820;

	public static final int		IPU_CMD			= 0x2000;
	public static final int		IPU_CTRL		= 0x2010;
	public static final int		IPU_BP			= 0x2020;
	public static final int		IPU_TOP			= 0x2030;

	public static final int		GIF_CTRL		= 0x3000;
	public static final int		GIF_MODE		= 0x3010;
	public static final int		GIF_STAT		= 0x3020;
	public static final int		GIF_TAG0		= 0x3040;
	public static final int		GIF_TAG1		= 0x3050;
	public static final int		GIF_TAG2		= 0x3060;
	public static final int		GIF_TAG3		= 0x3070;
	public static final int		GIF_CNT			= 0x3080;
	public static final int		GIF_P3CNT		= 0x3090;
	public static final int		GIF_P3TAG		= 0x30A0;

	// Vif Memory Locations
	public static final int		VIF0_STAT		= 0x3800;
	public static final int		VIF0_FBRST		= 0x3810;
	public static final int		VIF0_ERR		= 0x3820;
	public static final int		VIF0_MARK		= 0x3830;
	public static final int		VIF0_CYCLE		= 0x3840;
	public static final int		VIF0_MODE		= 0x3850;
	public static final int		VIF0_NUM		= 0x3860;
	public static final int		VIF0_MASK		= 0x3870;
	public static final int		VIF0_CODE		= 0x3880;
	public static final int		VIF0_ITOPS		= 0x3890;
	public static final int		VIF0_ITOP		= 0x38d0;
	public static final int		VIF0_TOP		= 0x38e0;
	public static final int		VIF0_R0			= 0x3900;
	public static final int		VIF0_R1			= 0x3910;
	public static final int		VIF0_R2			= 0x3920;
	public static final int		VIF0_R3			= 0x3930;
	public static final int		VIF0_C0			= 0x3940;
	public static final int		VIF0_C1			= 0x3950;
	public static final int		VIF0_C2			= 0x3960;
	public static final int		VIF0_C3			= 0x3970;

	public static final int		VIF1_STAT		= 0x3c00;
	public static final int		VIF1_FBRST		= 0x3c10;
	public static final int		VIF1_ERR		= 0x3c20;
	public static final int		VIF1_MARK		= 0x3c30;
	public static final int		VIF1_CYCLE		= 0x3c40;
	public static final int		VIF1_MODE		= 0x3c50;
	public static final int		VIF1_NUM		= 0x3c60;
	public static final int		VIF1_MASK		= 0x3c70;
	public static final int		VIF1_CODE		= 0x3c80;
	public static final int		VIF1_ITOPS		= 0x3c90;
	public static final int		VIF1_BASE		= 0x3ca0;
	public static final int		VIF1_OFST		= 0x3cb0;
	public static final int		VIF1_TOPS		= 0x3cc0;
	public static final int		VIF1_ITOP		= 0x3cd0;
	public static final int		VIF1_TOP		= 0x3ce0;
	public static final int		VIF1_R0			= 0x3d00;
	public static final int		VIF1_R1			= 0x3d10;
	public static final int		VIF1_R2			= 0x3d20;
	public static final int		VIF1_R3			= 0x3d30;
	public static final int		VIF1_C0			= 0x3d40;
	public static final int		VIF1_C1			= 0x3d50;
	public static final int		VIF1_C2			= 0x3d60;
	public static final int		VIF1_C3			= 0x3d70;

	public static final int		VIF0_FIFO		= 0x4000;
	public static final int		VIF1_FIFO		= 0x5000;
	public static final int		GIF_FIFO		= 0x6000;

	public static final int		IPUout_FIFO		= 0x7000;
	public static final int		IPUin_FIFO		= 0x7010;

	// VIF0
	public static final int		D0_CHCR			= 0x8000;
	public static final int		D0_MADR			= 0x8010;
	public static final int		D0_QWC			= 0x8020;

	// VIF1
	public static final int		D1_CHCR			= 0x9000;
	public static final int		D1_MADR			= 0x9010;
	public static final int		D1_QWC			= 0x9020;
	public static final int		D1_TADR			= 0x9030;
	public static final int		D1_ASR0			= 0x9040;
	public static final int		D1_ASR1			= 0x9050;
	public static final int		D1_SADR			= 0x9080;

	// GS
	public static final int		D2_CHCR			= 0xA000;
	public static final int		D2_MADR			= 0xA010;
	public static final int		D2_QWC			= 0xA020;
	public static final int		D2_TADR			= 0xA030;
	public static final int		D2_ASR0			= 0xA040;
	public static final int		D2_ASR1			= 0xA050;
	public static final int		D2_SADR			= 0xA080;

	// fromIPU
	public static final int		D3_CHCR			= 0xB000;
	public static final int		D3_MADR			= 0xB010;
	public static final int		D3_QWC			= 0xB020;
	public static final int		D3_TADR			= 0xB030;
	public static final int		D3_SADR			= 0xB080;

	// toIPU
	public static final int		D4_CHCR			= 0xB400;
	public static final int		D4_MADR			= 0xB410;
	public static final int		D4_QWC			= 0xB420;
	public static final int		D4_TADR			= 0xB430;
	public static final int		D4_SADR			= 0xB480;

	// SIF0
	public static final int		D5_CHCR			= 0xC000;
	public static final int		D5_MADR			= 0xC010;
	public static final int		D5_QWC			= 0xC020;

	// SIF1
	public static final int		D6_CHCR			= 0xC400;
	public static final int		D6_MADR			= 0xC410;
	public static final int		D6_QWC			= 0xC420;
	public static final int		D6_TADR			= 0xC430;

	// SIF2
	public static final int		D7_CHCR			= 0xC800;
	public static final int		D7_MADR			= 0xC810;
	public static final int		D7_QWC			= 0xC820;

	// fromSPR
	public static final int		D8_CHCR			= 0xD000;
	public static final int		D8_MADR			= 0xD010;
	public static final int		D8_QWC			= 0xD020;
	public static final int		D8_SADR			= 0xD080;
	public static final int		SPR1_CHCR		= 0xD400;

	public static final int		DMAC_CTRL		= 0xE000;
	public static final int		DMAC_STAT		= 0xE010;
	public static final int		DMAC_PCR		= 0xE020;
	public static final int		DMAC_SQWC		= 0xE030;
	public static final int		DMAC_RBSR		= 0xE040;
	public static final int		DMAC_RBOR		= 0xE050;
	public static final int		DMAC_STADR		= 0xE060;

	public static final int		INTC_STAT		= 0xF000;
	public static final int		INTC_MASK		= 0xF010;

	public static final int		SIO_LCR			= 0xF100;
	public static final int		SIO_LSR			= 0xF110;
	public static final int		SIO_IER			= 0xF120;
	public static final int		SIO_ISR			= 0xF130;										//
	public static final int		SIO_FCR			= 0xF140;
	public static final int		SIO_BGR			= 0xF150;
	public static final int		SIO_TXFIFO		= 0xF180;
	public static final int		SIO_RXFIFO		= 0xF1C0;

	public static final int		SBUS_F200		= 0xF200;										// MSCOM
	public static final int		SBUS_F210		= 0xF210;										// SMCOM
	public static final int		SBUS_F220		= 0xF220;										// MSFLG
	public static final int		SBUS_F230		= 0xF230;										// SMFLG
	public static final int		SBUS_F240		= 0xF240;
	public static final int		SBUS_F250		= 0xF250;
	public static final int		SBUS_F260		= 0xF260;

	public static final int		UNK_F410		= 0xF410;

	public static final int		MCH_RICM		= 0xF430;
	public static final int		MCH_DRD			= 0xF440;

	public static final int		DMAC_ENABLER	= 0xF520;
	public static final int		DMAC_ENABLEW	= 0xF590;

	public EEHardwareRegisters() {
		super("EE Hardware Registers", 0xFFFF);
	}

	public CpuState	cpu;

	public void setCpu(final CpuState cpu) {
		this.cpu = cpu;
	}

	@Override
	public int read32(int address) {
		address &= offset;

		switch (address) {
			case RCNT0_COUNT: {
				int result = cpu.rcntRcount(0);
				// System.err.println("COUNTER 0 : " + result);
				return result;
			}
			case SIO_ISR:
			case SBUS_F260:
			case UNK_F410:
			case MCH_RICM: {
				return 0;
			}
			default: {
				throw new RuntimeException("Unknow EE hardware address:" + Integer.toHexString(address));
			}
		}

		// return super.read32(address);
	}

	@Override
	public long read64(int address) {
		System.out.println("EEHardwareRegisters.read64()");
		return super.read64(address);
	}

	@Override
	public void write32(int address, int data) {
		address &= offset;

		switch (address) {
			case RCNT0_MODE: {
				cpu.counters[0].mode.value = data;
				break;
			}
			default: {
				System.out.println("EEHardwareRegisters.write32() : " + (Integer.toHexString(address)));
				super.write32(address, data);
			}
		}
	}

	@Override
	public void write64(int address, long data) {
		address &= offset;

		switch (address) {
			default: {
				System.out.println("EEHardwareRegisters.write64() : " + (Integer.toHexString(address)));
				super.write64(address, data);
			}
		}
	}

	@Override
	public int read16(int address) {
		System.out.println("EEHardwareRegisters.read16()");
		return super.read16(address);
	}

	@Override
	public int read8(int address) {
		int ret;

		if (address >= IPU_CMD && address < D0_CHCR) {
			logger.error(String.format("Unexpected hwRead8 from 0x%x", address));
		}

		switch (address) {
			case RCNT0_COUNT:
				ret = (byte) cpu.rcntRcount(0);
				break;
			case RCNT0_MODE:
				ret = (byte) cpu.counters[0].mode.value;
				break;
			case RCNT0_TARGET:
				ret = (byte) cpu.counters[0].target;
				break;
			case RCNT0_HOLD:
				ret = (byte) cpu.counters[0].hold;
				break;
			case RCNT0_COUNT + 1:
				ret = (byte) (cpu.rcntRcount(0) >> 8);
				break;
			case RCNT0_MODE + 1:
				ret = (byte) (cpu.counters[0].mode.value >> 8);
				break;
			case RCNT0_TARGET + 1:
				ret = (byte) (cpu.counters[0].target >> 8);
				break;
			case RCNT0_HOLD + 1:
				ret = (byte) (cpu.counters[0].hold >> 8);
				break;

			case RCNT1_COUNT:
				ret = (byte) cpu.rcntRcount(1);
				break;
			case RCNT1_MODE:
				ret = (byte) cpu.counters[1].mode.value;
				break;
			case RCNT1_TARGET:
				ret = (byte) cpu.counters[1].target;
				break;
			case RCNT1_HOLD:
				ret = (byte) cpu.counters[1].hold;
				break;
			case RCNT1_COUNT + 1:
				ret = (byte) (cpu.rcntRcount(1) >> 8);
				break;
			case RCNT1_MODE + 1:
				ret = (byte) (cpu.counters[1].mode.value >> 8);
				break;
			case RCNT1_TARGET + 1:
				ret = (byte) (cpu.counters[1].target >> 8);
				break;
			case RCNT1_HOLD + 1:
				ret = (byte) (cpu.counters[1].hold >> 8);
				break;

			case RCNT2_COUNT:
				ret = (byte) cpu.rcntRcount(2);
				break;
			case RCNT2_MODE:
				ret = (byte) cpu.counters[2].mode.value;
				break;
			case RCNT2_TARGET:
				ret = (byte) cpu.counters[2].target;
				break;
			case RCNT2_COUNT + 1:
				ret = (byte) (cpu.rcntRcount(2) >> 8);
				break;
			case RCNT2_MODE + 1:
				ret = (byte) (cpu.counters[2].mode.value >> 8);
				break;
			case RCNT2_TARGET + 1:
				ret = (byte) (cpu.counters[2].target >> 8);
				break;

			case RCNT3_COUNT:
				ret = (byte) cpu.rcntRcount(3);
				break;
			case RCNT3_MODE:
				ret = (byte) cpu.counters[3].mode.value;
				break;
			case RCNT3_TARGET:
				ret = (byte) cpu.counters[3].target;
				break;
			case RCNT3_COUNT + 1:
				ret = (byte) (cpu.rcntRcount(3) >> 8);
				break;
			case RCNT3_MODE + 1:
				ret = (byte) (cpu.counters[3].mode.value >> 8);
				break;
			case RCNT3_TARGET + 1:
				ret = (byte) (cpu.counters[3].target >> 8);
				break;

			default:
				if ((address & 0xffffff0f) == SBUS_F200) {
					switch (address) {
						case SBUS_F240:
							ret = super.read32(address);
							break;

						case SBUS_F260:
							ret = 0;
							break;

						default:
							ret = super.read32(address);
							break;
					}
					return (byte) ret;
				}

				ret = super.read8(address);
				logger.error(String.format("Unknown Hardware Read 8 from 0x%x = 0x%x", address, ret));
				break;
		}

		return ret;
	}

	@Override
	public long[] read128(int address) {
		System.out.println("EEHardwareRegisters.read128()");
		return super.read128(address);
	}
}
