package com.jps2.core.cpu.iop;

import java.io.InputStream;

import org.apache.log4j.Logger;

import com.jps2.core.Emulator;
import com.jps2.core.cpu.iop.state.CpuState;
import com.jps2.core.memory.FastMemory;
import com.jps2.core.memory.Memory;
import com.jps2.plugins.PluginManager;

public final class IOPHardwareRegisters extends FastMemory {

	public static final long	IOPCNT_FUTURE_TARGET	= 0x1000000000L;

	public static final int		HW_USB_START			= 0x1600;
	public static final int		HW_USB_END				= 0x1700;
	public static final int		HW_FW_START				= 0x8400;
	public static final int		HW_FW_END				= 0x8550;										// end
																										// addr
																										// for
																										// FW
																										// is
																										// a
																										// guess...
	public static final int		HW_SPU2_START			= 0x1c00;
	public static final int		HW_SPU2_END				= 0x1e00;

	public static final int		HW_SSBUS_SPD_ADDR		= 0x1000;
	public static final int		HW_SSBUS_PIO_ADDR		= 0x1004;
	public static final int		HW_SSBUS_SPD_DELAY		= 0x1008;
	public static final int		HW_SSBUS_DEV1_DELAY		= 0x100C;
	public static final int		HW_SSBUS_ROM_DELAY		= 0x1010;
	public static final int		HW_SSBUS_SPU_DELAY		= 0x1014;
	public static final int		HW_SSBUS_DEV5_DELAY		= 0x1018;
	public static final int		HW_SSBUS_PIO_DELAY		= 0x101c;
	public static final int		HW_SSBUS_COM_DELAY		= 0x1020;

	// SIO read/write register
	public static final int		HW_SIO_DATA				= 0x1040;
	public static final int		HW_SIO_STAT				= 0x1044;
	public static final int		HW_SIO_MODE				= 0x1048;
	public static final int		HW_SIO_CTRL				= 0x104a;
	public static final int		HW_SIO_BAUD				= 0x104e;

	// Memory registers
	public static final int		HW_RAM_SIZE				= 0x1060;
	public static final int		HW_IREG					= 0x1070;
	public static final int		HW_IMASK				= 0x1074;
	public static final int		HW_ICTRL				= 0x1078;

	// SSBUS registers
	public static final int		HW_SSBUS_DEV1_ADDR		= 0x1400;
	public static final int		HW_SSBUS_SPU_ADDR		= 0x1404;
	public static final int		HW_SSBUS_DEV5_ADDR		= 0x1408;
	public static final int		HW_SSBUS_SPU1_ADDR		= 0x140c;
	public static final int		HW_SSBUS_DEV9_ADDR3		= 0x1410;
	public static final int		HW_SSBUS_SPU1_DELAY		= 0x1414;
	public static final int		HW_SSBUS_DEV9_DELAY2	= 0x1418;
	public static final int		HW_SSBUS_DEV9_DELAY3	= 0x141c;
	public static final int		HW_SSBUS_DEV9_DELAY1	= 0x1420;

	// ???
	public static final int		HW_ICFG					= 0x1450;
	public static final int		HW_DEV9_DATA			= 0x146e;										// DEV9
																										// read/write
																										// register

	// CDRom registers are used for various command; status; and data stuff.
	public static final int		HW_CDR_DATA0			= 0x1800;										// CDROM
																										// multipurpose
																										// data
	// register 1
	public static final int		HW_CDR_DATA1			= 0x1801;										// CDROM
																										// multipurpose
																										// data
	// register 2
	public static final int		HW_CDR_DATA2			= 0x1802;										// CDROM
																										// multipurpose
																										// data
	// register 3
	public static final int		HW_CDR_DATA3			= 0x1803;										// CDROM
																										// multipurpose
																										// data
	// register 4

	// SIO2 is a DMA interface for the SIO.
	public static final int		HW_SIO2_DATAIN			= 0x8260;
	public static final int		HW_SIO2_FIFO			= 0x8264;
	public static final int		HW_SIO2_CTRL			= 0x8268;
	public static final int		HW_SIO2_RECV1			= 0x826c;
	public static final int		HW_SIO2_RECV2			= 0x8270;
	public static final int		HW_SIO2_RECV3			= 0x8274;
	public static final int		HW_SIO2_8278			= 0x8278;										// May
																										// as
																										// well
																										// add
																										// defs
	public static final int		HW_SIO2_827C			= 0x827C;										// for
																										// these
																										// 2...
	public static final int		HW_SIO2_INTR			= 0x8280;

	// DMAMadrAddresses
	public static final int		HWx_DMA0_MADR			= 0x1080;
	public static final int		HWx_DMA1_MADR			= 0x1090;
	public static final int		HWx_DMA2_MADR			= 0x10a0;
	public static final int		HWx_DMA3_MADR			= 0x10b0;
	public static final int		HWx_DMA4_MADR			= 0x10c0;
	public static final int		HWx_DMA5_MADR			= 0x10d0;
	public static final int		HWx_DMA6_MADR			= 0x10e0;
	public static final int		HWx_DMA7_MADR			= 0x1500;
	public static final int		HWx_DMA8_MADR			= 0x1510;
	public static final int		HWx_DMA9_MADR			= 0x1520;
	public static final int		HWx_DMA10_MADR			= 0x1530;
	public static final int		HWx_DMA11_MADR			= 0x1540;
	public static final int		HWx_DMA12_MADR			= 0x1550;

	// DMABcrAddresses
	public static final int		HWx_DMA0_BCR			= 0x1084;
	public static final int		HWx_DMA1_BCR			= 0x1094;
	public static final int		HWx_DMA2_BCR			= 0x10a4;
	public static final int		HWx_DMA3_BCR			= 0x10b4;
	public static final int		HWx_DMA3_BCR_L16		= 0x10b4;
	public static final int		HWx_DMA3_BCR_H16		= 0x10b6;
	public static final int		HWx_DMA4_BCR			= 0x10c4;
	public static final int		HWx_DMA5_BCR			= 0x10d4;
	public static final int		HWx_DMA6_BCR			= 0x10e4;
	public static final int		HWx_DMA7_BCR			= 0x1504;
	public static final int		HWx_DMA8_BCR			= 0x1514;
	public static final int		HWx_DMA9_BCR			= 0x1524;
	public static final int		HWx_DMA10_BCR			= 0x1534;
	public static final int		HWx_DMA11_BCR			= 0x1544;
	public static final int		HWx_DMA12_BCR			= 0x1554;

	// DMAChcrAddresses
	public static final int		HWx_DMA0_CHCR			= 0x1088;
	public static final int		HWx_DMA1_CHCR			= 0x1098;
	public static final int		HWx_DMA2_CHCR			= 0x10a8;
	public static final int		HWx_DMA3_CHCR			= 0x10b8;
	public static final int		HWx_DMA4_CHCR			= 0x10c8;
	public static final int		HWx_DMA5_CHCR			= 0x10d8;
	public static final int		HWx_DMA6_CHCR			= 0x10e8;
	public static final int		HWx_DMA7_CHCR			= 0x1508;
	public static final int		HWx_DMA8_CHCR			= 0x1518;
	public static final int		HWx_DMA9_CHCR			= 0x1528;
	public static final int		HWx_DMA10_CHCR			= 0x1538;
	public static final int		HWx_DMA11_CHCR			= 0x1548;
	public static final int		HWx_DMA12_CHCR			= 0x1558;

	// DMATadrAddresses
	public static final int		HWx_DMA0_TADR			= 0x108c;
	public static final int		HWx_DMA1_TADR			= 0x109c;
	public static final int		HWx_DMA2_TADR			= 0x10ac;
	public static final int		HWx_DMA3_TADR			= 0x10bc;
	public static final int		HWx_DMA4_TADR			= 0x10cc;
	public static final int		HWx_DMA5_TADR			= 0x10dc;
	public static final int		HWx_DMA6_TADR			= 0x10ec;
	public static final int		HWx_DMA7_TADR			= 0x150c;
	public static final int		HWx_DMA8_TADR			= 0x151c;
	public static final int		HWx_DMA9_TADR			= 0x152c;
	public static final int		HWx_DMA10_TADR			= 0x153c;
	public static final int		HWx_DMA11_TADR			= 0x154c;
	public static final int		HWx_DMA12_TADR			= 0x155c;

	// Registers for the IOP Counters */
	public static final int		IOP_T0_COUNT			= 0x1100;
	public static final int		IOP_T1_COUNT			= 0x1110;
	public static final int		IOP_T2_COUNT			= 0x1120;
	public static final int		IOP_T3_COUNT			= 0x1480;
	public static final int		IOP_T4_COUNT			= 0x1490;
	public static final int		IOP_T5_COUNT			= 0x14a0;

	public static final int		IOP_T0_MODE				= 0x1104;
	public static final int		IOP_T1_MODE				= 0x1114;
	public static final int		IOP_T2_MODE				= 0x1124;
	public static final int		IOP_T3_MODE				= 0x1484;
	public static final int		IOP_T4_MODE				= 0x1494;
	public static final int		IOP_T5_MODE				= 0x14a4;

	public static final int		IOP_T0_TARGET			= 0x1108;
	public static final int		IOP_T1_TARGET			= 0x1118;
	public static final int		IOP_T2_TARGET			= 0x1128;
	public static final int		IOP_T3_TARGET			= 0x1488;
	public static final int		IOP_T4_TARGET			= 0x1498;
	public static final int		IOP_T5_TARGET			= 0x14a8;

	public static final int		IOP_EVT_CDVD			= 5;											// General
																										// Cdvd
																										// commands
																										// (Seek,
	// Standby, Break, etc)
	public static final int		IOP_EVT_SIF0			= 9;
	public static final int		IOP_EVT_SIF1			= 10;
	public static final int		IOP_EVT_DMA11			= 11;
	public static final int		IOP_EVT_DMA12			= 12;
	public static final int		IOP_EVT_SIO				= 16;
	public static final int		IOP_EVT_CDROM			= 17;
	public static final int		IOP_EVT_CDROMREAD		= 18;
	public static final int		IOP_EVT_CDVDREAD		= 19;
	public static final int		IOP_EVT_DEV9			= 20;
	public static final int		IOP_EVT_USB				= 21;

	public static final Logger	logger					= Logger.getLogger(IOPHardwareRegisters.class);

	public CpuState				cpu;

	public void setCpu(final CpuState cpu) {
		this.cpu = cpu;
	}

	public IOPHardwareRegisters() {
		super("IOP Hardware Memory.", 0xFFFF);
	}

	@Override
	public void copy(final Memory memory, final int positionSource, final int positionDest) {
		throw new RuntimeException("Unsuported Operation (copy is not allowed here (IOP)).");
	}

	@Override
	public int read16(final int address) {
		throw new RuntimeException("IOPMemory.read16()");
	}

	@Override
	public int read32(int address) {
		address &= offset;
		int hard = 0;
		if (address >= HW_USB_START && address < HW_USB_END) {
			logger.debug(String.format("USB read %x", address));
			return PluginManager.getUsbPlugin().usbRead32(address);
		}
		if (address >= HW_FW_START && address <= HW_FW_END) {
			logger.debug(String.format("FW read %x", address));
			return PluginManager.getFirewirePlugin().firewireRead32(address);
		}

		switch (address) {
			case 0x1040:
				hard = Emulator.getInstance().getSio().sioRead8();
				hard |= Emulator.getInstance().getSio().sioRead8() << 8;
				hard |= Emulator.getInstance().getSio().sioRead8() << 16;
				hard |= Emulator.getInstance().getSio().sioRead8() << 24;
				logger.debug(String.format("sio read32 ;ret = %x", hard));
				return hard;

				// case 0x1050: hard = serial_read32(); break;//serial port
			case 0x1060:
				logger.debug(String.format("RAM size read %x", super.read32(0x1060)));
				return super.read32(0x1060);
			case 0x1070:
				logger.debug(String.format("IREG 32bit read %x", super.read32(0x1070)));
				return super.read32(0x1070);
			case 0x1074:
				logger.debug(String.format("IMASK 32bit read %x", super.read32(0x1074)));
				return super.read32(0x1074);
			case 0x1078:
				logger.debug(String.format("ICTRL 32bit read %x", super.read32(0x1078)));
				hard = super.read32(0x1078);
				super.write32(0x1078, 0);
				return hard;
			case 0x10a0:
				logger.debug(String.format("DMA2 MADR 32bit read %x", super.read32(0x10a0)));
				return HWx_DMA2_MADR;
			case 0x10a4:
				logger.debug(String.format("DMA2 BCR 32bit read %x", super.read32(0x10a4)));
				return HWx_DMA2_BCR;
			case 0x10a8:
				logger.debug(String.format("DMA2 CHCR 32bit read %x", super.read32(0x10a8)));
				return HWx_DMA2_CHCR;

			case 0x10b0:
				logger.debug(String.format("DMA3 MADR 32bit read %x", super.read32(0x10b0)));
				return HWx_DMA3_MADR;
			case 0x10b4:
				logger.debug(String.format("DMA3 BCR 32bit read %x", super.read32(0x10b4)));
				return HWx_DMA3_BCR;
			case 0x10b8:
				logger.debug(String.format("DMA3 CHCR 32bit read %x", super.read32(0x10b8)));
				return HWx_DMA3_CHCR;

			case 0x1520:
				logger.debug(String.format("DMA9 MADR 32bit read %x", HWx_DMA9_MADR));
				return HWx_DMA9_MADR;
			case 0x1524:
				logger.debug(String.format("DMA9 BCR 32bit read %x", HWx_DMA9_BCR));
				return HWx_DMA9_BCR;
			case 0x1528:
				logger.debug(String.format("DMA9 CHCR 32bit read %x", HWx_DMA9_CHCR));
				return HWx_DMA9_CHCR;
			case 0x152C:
				logger.debug(String.format("DMA9 TADR 32bit read %x", HWx_DMA9_TADR));
				return HWx_DMA9_TADR;

			case 0x1530:
				logger.debug(String.format("DMA10 MADR 32bit read %x", HWx_DMA10_MADR));
				return HWx_DMA10_MADR;
			case 0x1534:
				logger.debug(String.format("DMA10 BCR 32bit read %x", HWx_DMA10_BCR));
				return HWx_DMA10_BCR;
			case 0x1538:
				logger.debug(String.format("DMA10 CHCR 32bit read %x", HWx_DMA10_CHCR));
				return HWx_DMA10_CHCR;

				// case 0x10f0: logger.debug(String.format("DMA PCR 32bit read "
				// <<
				// super.read32(0x10f0));
				// return HWx_DMA_PCR; // dma rest channel

				// case 0x10f4:
				// logger.debug(String.format("DMA ICR 32bit read %x",
				// HWx_DMA_ICR));
				// return HWx_DMA_ICR;

				// SSBus registers
			case 0x1000:
				hard = super.read32(0x1000);
				logger.debug(String.format("SSBUS <spd_addr> 32bit read %x", hard));
				return hard;
			case 0x1004:
				hard = super.read32(0x1004);
				logger.debug(String.format("SSBUS <pio_addr> 32bit read %x", hard));
				return hard;
			case 0x1008:
				hard = super.read32(0x1008);
				logger.debug(String.format("SSBUS <spd_delay> 32bit read %x", hard));
				return hard;
			case 0x100C:
				hard = super.read32(0x100C);
				logger.debug(String.format("SSBUS dev1_delay 32bit read %x", hard));
				return hard;
			case 0x1010:
				hard = super.read32(0x1010);
				logger.debug(String.format("SSBUS rom_delay 32bit read %x", hard));
				return hard;
			case 0x1014:
				hard = super.read32(0x1014);
				logger.debug(String.format("SSBUS spu_delay 32bit read %x", hard));
				return hard;
			case 0x1018:
				hard = super.read32(0x1018);
				logger.debug(String.format("SSBUS dev5_delay 32bit read %x", hard));
				return hard;
			case 0x101C:
				hard = super.read32(0x101C);
				logger.debug(String.format("SSBUS <pio_delay> 32bit read %x", hard));
				return hard;
			case 0x1020:
				hard = super.read32(0x1020);
				logger.debug(String.format("SSBUS com_delay 32bit read %x", hard));
				return hard;
			case 0x1400:
				hard = super.read32(0x1400);
				logger.debug(String.format("SSBUS dev1_addr 32bit read %x", hard));
				return hard;
			case 0x1404:
				hard = super.read32(0x1404);
				logger.debug(String.format("SSBUS spu_addr 32bit read %x", hard));
				return hard;
			case 0x1408:
				hard = super.read32(0x1408);
				logger.debug(String.format("SSBUS dev5_addr 32bit read %x", hard));
				return hard;
			case 0x140C:
				hard = super.read32(0x140C);
				logger.debug(String.format("SSBUS spu1_addr 32bit read %x", hard));
				return hard;
			case 0x1410:
				hard = super.read32(0x1410);
				logger.debug(String.format("SSBUS <dev9_addr3> 32bit read %x", hard));
				return hard;
			case 0x1414:
				hard = super.read32(0x1414);
				logger.debug(String.format("SSBUS spu1_delay 32bit read %x", hard));
				return hard;
			case 0x1418:
				hard = super.read32(0x1418);
				logger.debug(String.format("SSBUS <dev9_delay2> 32bit read %x", hard));
				return hard;
			case 0x141C:
				hard = super.read32(0x141C);
				logger.debug(String.format("SSBUS <dev9_delay3> 32bit read %x", hard));
				return hard;
			case 0x1420:
				hard = super.read32(0x1420);
				logger.debug(String.format("SSBUS <dev9_delay1> 32bit read %x", hard));
				return hard;

				// case 0x10f0:
				// logger.debug(String.format("DMA PCR 32bit read %x",
				// HWx_DMA_PCR));
				// return HWx_DMA_PCR;

			case 0x10c8:
				logger.debug(String.format("DMA4 CHCR 32bit read %x", HWx_DMA4_CHCR));
				return HWx_DMA4_CHCR; // DMA4 chcr (SPU DMA)

				// time for rootcounters :)
			case IOP_T0_COUNT:
				hard = cpu.psxRcntRcount16(0);
				logger.debug(String.format("T0 count read32: %x", hard));
				return hard;
			case IOP_T0_MODE:
				hard = (short) cpu.getCounter(0).mode;
				logger.debug(String.format("T0 mode read32: %x", hard));
				return hard;
			case IOP_T0_TARGET:
				hard = (int) cpu.getCounter(0).target;
				logger.debug(String.format("T0 target read32: %x", hard));
				return hard;
			case IOP_T1_COUNT:
				hard = cpu.psxRcntRcount16(1);
				logger.debug(String.format("T1 count read32: %x", hard));
				return hard;
			case IOP_T1_MODE:
				hard = (short) cpu.getCounter(1).mode;
				logger.debug(String.format("T1 mode read32: %x", hard));
				return hard;
			case IOP_T1_TARGET:
				hard = (int) cpu.getCounter(1).target;
				logger.debug(String.format("T1 target read32: %x", hard));
				return hard;
			case IOP_T2_COUNT:
				hard = cpu.psxRcntRcount16(2);
				logger.debug(String.format("T2 count read32: %x", hard));
				return hard;
			case IOP_T2_MODE:
				hard = (short) cpu.getCounter(2).mode;
				logger.debug(String.format("T2 mode read32: %x", hard));
				return hard;
			case IOP_T2_TARGET:
				hard = (int) cpu.getCounter(2).target;
				logger.debug(String.format("T2 target read32: %x", hard));
				return hard;
			case IOP_T3_COUNT:
				hard = cpu.psxRcntRcount32(3);
				logger.debug(String.format("T3 count read32: %x", hard));
				return hard;
			case IOP_T3_MODE:
				hard = (short) cpu.getCounter(3).mode;
				logger.debug(String.format("T3 mode read32: %x", hard));
				return hard;
			case IOP_T3_TARGET:
				hard = (int) cpu.getCounter(3).target;
				logger.debug(String.format("T3 target read32: %x", hard));
				return hard;
			case IOP_T4_COUNT:
				hard = cpu.psxRcntRcount32(4);
				logger.debug(String.format("T4 count read32: %x", hard));
				return hard;
			case IOP_T4_MODE:
				hard = (short) cpu.getCounter(4).mode;
				logger.debug(String.format("T4 mode read32: %x", hard));
				return hard;
			case IOP_T4_TARGET:
				hard = (int) cpu.getCounter(4).target;
				logger.debug(String.format("T4 target read32: %x", hard));
				return hard;
			case IOP_T5_COUNT:
				hard = cpu.psxRcntRcount32(5);
				logger.debug(String.format("T5 count read32: %x", hard));
				return hard;
			case IOP_T5_MODE:
				hard = (short) cpu.getCounter(5).mode;
				logger.debug(String.format("T5 mode read32: %x", hard));
				return hard;
			case IOP_T5_TARGET:
				hard = (int) cpu.getCounter(5).target;
				logger.debug(String.format("T5 target read32: %x", hard));
				return hard;

			case 0x1450:
				hard = super.read32(address);
				logger.debug(String.format("%08X ICFG 32bit read %x", cpu.pc, hard));
				return hard;

				// case 0x10C0:
				// hard = SPU2ReadMemAddr(0);
				// super.write32(HWx_DMA4_MADR, hard);
				// return hard;
				//
				// case 0x1500:
				// hard = SPU2ReadMemAddr(1);
				// super.write32(HWx_DMA7_MADR, hard);
				// logger.debug(String.format("DMA7 MADR 32bit read %x",
				// HWx_DMA7_MADR));
				// return hard; // DMA7 madr
			case 0x1504:
				logger.debug(String.format("DMA7 BCR 32bit read %x", HWx_DMA7_BCR));
				return HWx_DMA7_BCR; // DMA7 bcr

			case 0x1508:
				logger.debug(String.format("DMA7 CHCR 32bit read %x", HWx_DMA7_CHCR));
				return HWx_DMA7_CHCR; // DMA7 chcr (SPU2)

			case 0x1570:
				hard = super.read32(0x1570);
				logger.debug(String.format("DMA PCR2 32bit read %x", hard));
				return hard;
				// case 0x1574:
				// logger.debug(String.format("DMA ICR2 32bit read %x",
				// HWx_DMA_ICR2));
				// return HWx_DMA_ICR2;
				//
				// case 0x8200:
				// case 0x8204:
				// case 0x8208:
				// case 0x820C:
				// case 0x8210:
				// case 0x8214:
				// case 0x8218:
				// case 0x821C:
				// case 0x8220:
				// case 0x8224:
				// case 0x8228:
				// case 0x822C:
				// case 0x8230:
				// case 0x8234:
				// case 0x8238:
				// case 0x823C:
				// hard = sio2_getSend3((address - 0x8200) / 4);
				// logger.debug(String.format("SIO2 read param[%d] (%x)",
				// (address - 0x8200) / 4, hard);
				// return hard;
				//
				// case 0x8240:
				// case 0x8248:
				// case 0x8250:
				// case 0x8258:
				// hard = sio2_getSend1((address - 0x8240) / 8);
				// logger.debug(String.format("SIO2 read send1[%d] (%x)",
				// (address - 0x8240) / 8, hard);
				// return hard;
				//
				// case 0x8244:
				// case 0x824C:
				// case 0x8254:
				// case 0x825C:
				// hard = sio2_getSend2((address - 0x8244) / 8);
				// logger.debug(String.format("SIO2 read send2[%d] (%x)",
				// (address - 0x8244) / 8, hard);
				// return hard;
				//
				// case 0x8268:
				// hard = sio2_getCtrl();
				// logger.debug(String.format("SIO2 read CTRL (%x)", hard));
				// return hard;
				//
				// case 0x826C:
				// hard = sio2_getRecv1();
				// logger.debug(String.format("SIO2 read Recv1 (%x)", hard));
				// return hard;
				//
				// case 0x8270:
				// hard = sio2_getRecv2();
				// logger.debug(String.format("SIO2 read Recv2 (%x)", hard));
				// return hard;
				//
				// case 0x8274:
				// hard = sio2_getRecv3();
				// logger.debug(String.format("SIO2 read Recv3 (%x)", hard));
				// return hard;
				//
				// case 0x8278:
				// hard = sio2_get8278();
				// logger.debug(String.format("SIO2 read [8278] (%x)", hard));
				// return hard;
				//
				// case 0x827C:
				// hard = sio2_get827C();
				// logger.debug(String.format("SIO2 read [827C] (%x)", hard));
				// return hard;
				//
				// case 0x8280:
				// hard = sio2_getIntr();
				// logger.debug(String.format("SIO2 read INTR (%x)", hard));
				// return hard;

			default:
				hard = super.read32(address);
				logger.debug(String.format("*Unknown 32bit read at address %x: %x", address, hard));
				return hard;
		}
	}

	@Override
	public long read64(final int address) {
		throw new RuntimeException("IOPMemory.read64()");
	}

	@Override
	public int read8(int address) {
		int hard;
		address &= offset;
		if (address >= HW_USB_START && address < HW_USB_END) {
			return PluginManager.getUsbPlugin().usbRead8(address);
		}

		switch (address) {
			case 0x1040:
				hard = Emulator.getInstance().sio.sioRead8();
				break;
			// case 0x1f801050: hard = serial_read8(); break;//for use of serial
			// port ignore for now

			case 0x146e: // DEV9_R_REV
				return 0;// DEV9read8(address);

			case 0x1800:
				hard = 0;// cdrRead0();
				break;
			case 0x1801:
				hard = 0;// cdrRead1();
				break;
			case 0x1802:
				hard = 0;// cdrRead2();
				break;
			case 0x1803:
				hard = 0;// cdrRead3();
				break;

			case 0x3100: // PS/EE/IOP conf related
				hard = 0x10; // Dram 2M
				break;

			case 0x8264:
				hard = 0;// sio2_fifoOut();// sio2 serial data feed/fifo_out
				logger.debug(String.format("SIO2 read8 DATAOUT %08X", hard));
				return hard;

			default:
				hard = super.read8(address);
				logger.debug(String.format("Unknown 8bit read at address %x value %x", address, hard));
				return hard;
		}

		logger.debug(String.format("Known 8bit read at address %x value %x", address, hard));
		return hard;
	}

	@Override
	public void write16(int address, final short data) {
		address &= offset;
		if (address >= HW_USB_START && address < HW_USB_END) {
			logger.debug(String.format("USB write %x", address));
			PluginManager.getUsbPlugin().usbWrite16(address, data);
			return;
		}

		if ((address & 0xf) == 0x9)
			logger.debug(String.format("16bit write (possible chcr set) %x value %x", address, data));

		switch (address) {
			case 0x1040:
				Emulator.getInstance().getSio().sioWrite8((byte) data);
				Emulator.getInstance().getSio().sioWrite8((byte) (data >> 8));
				logger.debug(String.format("sio write16 %x, %x", address & 0xf, data));
				return;
			case 0x1044:
				logger.debug(String.format("sio write16 %x, %x", address & 0xf, data));
				return;
			case 0x1048:
				Emulator.getInstance().getSio().modeReg = data;
				logger.debug(String.format("sio write16 %x, %x", address & 0xf, data));
				return;
			case 0x104a: // control register
				Emulator.getInstance().getSio().sioWriteCtrl16(data);
				logger.debug(String.format("sio write16 %x, %x", address & 0xf, data));
				return;
			case 0x104e: // baudrate register
				Emulator.getInstance().getSio().baudReg = data;
				logger.debug(String.format("sio write16 %x, %x", address & 0xf, data));
				return;

				// serial port ;P
				// case 0x1050: serial_write16(value); break;
				// case 0x105a: serial_control_write(value);break;
				// case 0x105e: serial_baud_write(value); break;
				// case 0x1054: serial_status_write(value); break;

			case 0x1070:
				logger.debug(String.format("IREG 16bit write %x", data));
				// if (Config.Sio) psxHu16(0x1070) |= 0x80;
				// if (Config.SpuIrq) psxHu16(0x1070) |= 0x200;
				super.write16(address, (short) (read16(address) & data));
				return;

			case 0x1074:
				logger.debug(String.format("IMASK 16bit write %x", data));
				super.write16(address, data);
				cpu.iopTestIntc();
				return;

			case 0x1078: // see the 32-bit version for notes!
				logger.debug(String.format("ICTRL 16bit write %n", data));
				super.write16(address, data);
				cpu.iopTestIntc();
				return;

			case 0x10c4:
				logger.debug(String.format("DMA4 BCR_size 16bit write %x", data));
				super.write16(address, data);
				return; // DMA4 bcr_size

			case 0x10c6:
				logger.debug(String.format("DMA4 BCR_count 16bit write %x", data));
				super.write16(address, data);
				return; // DMA4 bcr_count

				// case IOP_T0_COUNT:
				// PSXCNT_LOG("COUNTER 0 COUNT 16bit write %x", data);
				// psxRcntWcount16(0, data);
				// return;
				// case IOP_T0_MODE:
				// PSXCNT_LOG("COUNTER 0 MODE 16bit write %x", data);
				// psxRcntWmode16(0, data);
				// return;
				// case IOP_T0_TARGET:
				// PSXCNT_LOG("COUNTER 0 TARGET 16bit write %x", data);
				// psxRcntWtarget16(0, data);
				// return;
				//
				// case IOP_T1_COUNT:
				// PSXCNT_LOG("COUNTER 1 COUNT 16bit write %x", data);
				// psxRcntWcount16(1, data);
				// return;
				// case IOP_T1_MODE:
				// PSXCNT_LOG("COUNTER 1 MODE 16bit write %x", data);
				// psxRcntWmode16(1, data);
				// return;
				// case IOP_T1_TARGET:
				// PSXCNT_LOG("COUNTER 1 TARGET 16bit write %x", data);
				// psxRcntWtarget16(1, data);
				// return;
				//
				// case IOP_T2_COUNT:
				// PSXCNT_LOG("COUNTER 2 COUNT 16bit write %x", data);
				// psxRcntWcount16(2, data);
				// return;
				// case IOP_T2_MODE:
				// PSXCNT_LOG("COUNTER 2 MODE 16bit write %x", data);
				// psxRcntWmode16(2, data);
				// return;
				// case IOP_T2_TARGET:
				// PSXCNT_LOG("COUNTER 2 TARGET 16bit write %x", data);
				// psxRcntWtarget16(2, data);
				// return;

			case 0x1450:
				logger.debug(String.format("%08X ICFG 16bit write %x", cpu.pc, data));
				super.write16(address, data/* & (~0x8) */);
				;
				return;

				// case IOP_T3_COUNT:
				// PSXCNT_LOG("COUNTER 3 COUNT 16bit write %x", data);
				// psxRcntWcount32(3, data);
				// return;
				// case IOP_T3_MODE:
				// PSXCNT_LOG("COUNTER 3 MODE 16bit write %x", data);
				// psxRcntWmode32(3, data);
				// return;
				// case IOP_T3_TARGET:
				// PSXCNT_LOG("COUNTER 3 TARGET 16bit write %x", data);
				// psxRcntWtarget32(3, data);
				// return;
				//
				// case IOP_T4_COUNT:
				// PSXCNT_LOG("COUNTER 4 COUNT 16bit write %x", data);
				// psxRcntWcount32(4, data);
				// return;
				// case IOP_T4_MODE:
				// PSXCNT_LOG("COUNTER 4 MODE 16bit write %x", data);
				// psxRcntWmode32(4, data);
				// return;
				// case IOP_T4_TARGET:
				// PSXCNT_LOG("COUNTER 4 TARGET 16bit write %x", data);
				// psxRcntWtarget32(4, data);
				// return;
				//
				// case IOP_T5_COUNT:
				// PSXCNT_LOG("COUNTER 5 COUNT 16bit write %x", data);
				// psxRcntWcount32(5, data);
				// return;
				// case IOP_T5_MODE:
				// PSXCNT_LOG("COUNTER 5 MODE 16bit write %x", data);
				// psxRcntWmode32(5, data);
				// return;
				// case IOP_T5_TARGET:
				// PSXCNT_LOG("COUNTER 5 TARGET 16bit write %x", data);
				// psxRcntWtarget32(5, data);
				// return;

			case 0x1504:
				super.write16(address, data);
				logger.debug(String.format("DMA7 BCR_size 16bit write %x", data));
				return;
			case 0x1506:
				super.write16(address, data);
				logger.debug(String.format("DMA7 BCR_count 16bit write %x", data));
				return;
			default:
				if (address >= HW_SPU2_START && address < HW_SPU2_END) {
					logger.debug(String.format("SPU2 write %x", address));
					PluginManager.getSpu2Plugin().spu2write(address, data);
					return;
				}

				super.write16(address, data);
				logger.debug(String.format("*Unknown 16bit write at address %x value %x", address, data));
				return;
		}

	}

	@Override
	public void write32(int address, final int data) {
		address &= offset;
		if (address >= HW_USB_START && address < HW_USB_END) {
			logger.debug(String.format("USB write %x", address));
			PluginManager.getUsbPlugin().usbWrite32(address, data);
		}
		if (address >= HW_FW_START && address <= HW_FW_END) {
			logger.debug(String.format("FW write %x", address));
			PluginManager.getFirewirePlugin().firewireWrite32(address, data);
		}
		switch (address) {
			case 0x1040:
				Emulator.getInstance().getSio().sioWrite8((byte) (data & 0xff));
				Emulator.getInstance().getSio().sioWrite8((byte) ((data & 0xff00) >> 8));
				Emulator.getInstance().getSio().sioWrite8((byte) ((data & 0xff0000) >> 16));
				Emulator.getInstance().getSio().sioWrite8((byte) ((data & 0xff000000) >> 24));
				logger.debug(String.format("sio write32 %x", data));
				return;
				// case 0x1050: serial_write32(value); break;//serial port
			case 0x1060:
				logger.debug(String.format("RAM size write %x", data));
				super.write32(address, data);
				return; // Ram size

				// ------------------------------------------------------------------
			case 0x1070:
				logger.debug(String.format("IREG 32bit write %x", data));
				// if (Config.Sio) psxHu32(0x1070) |= 0x80;
				// if (Config.SpuIrq) psxHu32(0x1070) |= 0x200;
				super.write32(address, read32(address) & data);
				return;

			case 0x1074:
				logger.debug(String.format("IMASK 32bit write %x", data));
				super.write32(address, data);
				cpu.iopTestIntc();
				return;

			case 0x1078:
				logger.debug(String.format("ICTRL 32bit write %x", data));
				super.write32(address, data); // 1; //According to pSXAuthor
												// this
				// always becomes 1 on write, but
				// MHPB won't boot if value is not
				// writen ;p
				cpu.iopTestIntc();
				return;

				// ------------------------------------------------------------------
				// SSBus registers
			case 0x1000:
				super.write32(address, data);
				logger.debug(String.format("SSBUS <spd_addr> 32bit write %x", data));
				return;
			case 0x1004:
				super.write32(address, data);
				logger.debug(String.format("SSBUS <pio_addr> 32bit write %x", data));
				return;
			case 0x1008:
				super.write32(address, data);
				logger.debug(String.format("SSBUS <spd_delay> 32bit write %x", data));
				return;
			case 0x100C:
				super.write32(address, data);
				logger.debug(String.format("SSBUS dev1_delay 32bit write %x", data));
				return;
			case 0x1010:
				super.write32(address, data);
				logger.debug(String.format("SSBUS rom_delay 32bit write %x", data));
				return;
			case 0x1014:
				super.write32(address, data);
				logger.debug(String.format("SSBUS spu_delay 32bit write %x", data));
				return;
			case 0x1018:
				super.write32(address, data);
				logger.debug(String.format("SSBUS dev5_delay 32bit write %x", data));
				return;
			case 0x101C:
				super.write32(address, data);
				logger.debug(String.format("SSBUS <pio_delay> 32bit write %x", data));
				return;
			case 0x1020:
				super.write32(address, data);
				logger.debug(String.format("SSBUS com_delay 32bit write %x", data));
				return;
			case 0x1400:
				super.write32(address, data);
				logger.debug(String.format("SSBUS dev1_addr 32bit write %x", data));
				return;
			case 0x1404:
				super.write32(address, data);
				logger.debug(String.format("SSBUS spu_addr 32bit write %x", data));
				return;
			case 0x1408:
				super.write32(address, data);
				logger.debug(String.format("SSBUS dev5_addr 32bit write %x", data));
				return;
			case 0x140C:
				super.write32(address, data);
				logger.debug(String.format("SSBUS spu1_addr 32bit write %x", data));
				return;
			case 0x1410:
				super.write32(address, data);
				logger.debug(String.format("SSBUS <dev9_addr3> 32bit write %x", data));
				return;
			case 0x1414:
				super.write32(address, data);
				logger.debug(String.format("SSBUS spu1_delay 32bit write %x", data));
				return;
			case 0x1418:
				super.write32(address, data);
				logger.debug(String.format("SSBUS <dev9_delay2> 32bit write %x", data));
				return;
			case 0x141C:
				super.write32(address, data);
				logger.debug(String.format("SSBUS <dev9_delay3> 32bit write %x", data));
				return;
			case 0x1420:
				super.write32(address, data);
				logger.debug(String.format("SSBUS <dev9_delay1> 32bit write %x", data));
				return;

				// ------------------------------------------------------------------
				// case 0x1080:
				// logger.debug(String.format("DMA0 MADR 32bit write %x",
				// data));
				// HW_DMA0_MADR = data;
				// return; // DMA0 madr
				// case 0x1084:
				// logger.debug(String.format("DMA0 BCR 32bit write %x", data));
				// HW_DMA0_BCR = data;
				// return; // DMA0 bcr
				// case 0x1088:
				// logger.debug(String.format("DMA0 CHCR 32bit write %x",
				// data));
				// HW_DMA0_CHCR = data; // DMA0 chcr (MDEC in DMA)
				// // DmaExec(0);
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// case 0x1090:
				// logger.debug(String.format("DMA1 MADR 32bit write %x",
				// data));
				// HW_DMA1_MADR = data;
				// return; // DMA1 madr
				// case 0x1094:
				// logger.debug(String.format("DMA1 BCR 32bit write %x", data));
				// HW_DMA1_BCR = data;
				// return; // DMA1 bcr
				// case 0x1098:
				// logger.debug(String.format("DMA1 CHCR 32bit write %x",
				// data));
				// HW_DMA1_CHCR = data; // DMA1 chcr (MDEC out DMA)
				// // DmaExec(1);
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// case 0x10a0:
				// logger.debug(String.format("DMA2 MADR 32bit write %x",
				// data));
				// HW_DMA2_MADR = data;
				// return; // DMA2 madr
				// case 0x10a4:
				// logger.debug(String.format("DMA2 BCR 32bit write %x", data));
				// HW_DMA2_BCR = data;
				// return; // DMA2 bcr
				// case 0x10a8:
				// logger.debug(String.format("DMA2 CHCR 32bit write %x",
				// data));
				// HW_DMA2_CHCR = data; // DMA2 chcr (GPU DMA)
				// DmaExec(2);
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// case 0x10b0:
				// logger.debug(String.format("DMA3 MADR 32bit write %x",
				// data));
				// HW_DMA3_MADR = data;
				// return; // DMA3 madr
				// case 0x10b4:
				// logger.debug(String.format("DMA3 BCR 32bit write %x", data));
				// HW_DMA3_BCR = data;
				// return; // DMA3 bcr
				// case 0x10b8:
				// logger.debug(String.format("DMA3 CHCR 32bit write %x",
				// data));
				// HW_DMA3_CHCR = data; // DMA3 chcr (CDROM DMA)
				// DmaExec(3);
				//
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// case 0x10c0:
				// logger.debug(String.format("DMA4 MADR 32bit write %x",
				// data));
				// SPU2WriteMemAddr(0, data);
				// HW_DMA4_MADR = data;
				// return; // DMA4 madr
				// case 0x10c4:
				// logger.debug(String.format("DMA4 BCR 32bit write %x", data));
				// HW_DMA4_BCR = data;
				// return; // DMA4 bcr
				// case 0x10c8:
				// logger.debug(String.format("DMA4 CHCR 32bit write %x",
				// data));
				// HW_DMA4_CHCR = data; // DMA4 chcr (SPU DMA)
				// DmaExecNew(4);
				// return;
				//
				// //
				// ------------------------------------------------------------------
				//
				// case 0x10e0:
				// logger.debug(String.format("DMA6 MADR 32bit write %x",
				// data));
				// HW_DMA6_MADR = value;
				// return; // DMA6 madr
				// case 0x10e4:
				// logger.debug(String.format("DMA6 BCR 32bit write %x", data));
				// HW_DMA6_BCR = value;
				// return; // DMA6 bcr
				// case 0x10e8:
				// logger.debug(String.format("DMA6 CHCR 32bit write %x",
				// data));
				// HW_DMA6_CHCR = value; // DMA6 chcr (OT clear)
				// DmaExec(6);
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// case 0x1500:
				// logger.debug(String.format("DMA7 MADR 32bit write %x",
				// data));
				// SPU2WriteMemAddr(1, data);
				// HW_DMA7_MADR = data;
				// return; // DMA7 madr
				// case 0x1504:
				// logger.debug(String.format("DMA7 BCR 32bit write %x", data));
				// HW_DMA7_BCR = data;
				// return; // DMA7 bcr
				// case 0x1508:
				// logger.debug(String.format("DMA7 CHCR 32bit write %x",
				// data));
				// HW_DMA7_CHCR = data; // DMA7 chcr (SPU2)
				// DmaExecNew2(7);
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// case 0x1510:
				// logger.debug(String.format("DMA8 MADR 32bit write %x",
				// data));
				// HW_DMA8_MADR = data;
				// return; // DMA8 madr
				// case 0x1514:
				// logger.debug(String.format("DMA8 BCR 32bit write %x", data));
				// HW_DMA8_BCR = data;
				// return; // DMA8 bcr
				// case 0x1518:
				// logger.debug(String.format("DMA8 CHCR 32bit write %x",
				// data));
				// HW_DMA8_CHCR = data; // DMA8 chcr (DEV9)
				// DmaExec2(8);
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// case 0x1520:
				// logger.debug(String.format("DMA9 MADR 32bit write %x",
				// data));
				// HW_DMA9_MADR = data;
				// return; // DMA9 madr
				// case 0x1524:
				// logger.debug(String.format("DMA9 BCR 32bit write %x", data));
				// HW_DMA9_BCR = data;
				// return; // DMA9 bcr
				// case 0x1528:
				// logger.debug(String.format("DMA9 CHCR 32bit write %x",
				// data));
				// HW_DMA9_CHCR = data; // DMA9 chcr (SIF0)
				// DmaExec2(9);
				// return;
				// case 0x152c:
				// logger.debug(String.format("DMA9 TADR 32bit write %x",
				// data));
				// HW_DMA9_TADR = data;
				// return; // DMA9 tadr
				//
				// //
				// ------------------------------------------------------------------
				// case 0x1530:
				// logger.debug(String.format("DMA10 MADR 32bit write %x",
				// data));
				// HW_DMA10_MADR = data;
				// return; // DMA10 madr
				// case 0x1534:
				// logger.debug(String.format("DMA10 BCR 32bit write %x",
				// data));
				// HW_DMA10_BCR = data;
				// return; // DMA10 bcr
				// case 0x1538:
				// logger.debug(String.format("DMA10 CHCR 32bit write %x",
				// data));
				// HW_DMA10_CHCR = data; // DMA10 chcr (SIF1)
				// DmaExec2(10);
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// case 0x1540:
				// logger.debug(String.format("DMA11 SIO2in MADR 32bit write %x",
				// data));
				// HW_DMA11_MADR = data;
				// return;
				//
				// case 0x1544:
				// logger.debug(String.format("DMA11 SIO2in BCR 32bit write %x",
				// data));
				// HW_DMA11_BCR = data;
				// return;
				// case 0x1548:
				// logger.debug(String.format("DMA11 SIO2in CHCR 32bit write %x",
				// data));
				// HW_DMA11_CHCR = data; // DMA11 chcr (SIO2 in)
				// DmaExec2(11);
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// case 0x1550:
				// logger.debug(String.format("DMA12 SIO2out MADR 32bit write %x",
				// data));
				// HW_DMA12_MADR = data;
				// return;
				//
				// case 0x1554:
				// logger.debug(String.format("DMA12 SIO2out BCR 32bit write %x",
				// data));
				// HW_DMA12_BCR = data;
				// return;
				// case 0x1558:
				// logger.debug(String.format("DMA12 SIO2out CHCR 32bit write %x",
				// data));
				// HW_DMA12_CHCR = data; // DMA12 chcr (SIO2 out)
				// DmaExec2(12);
				// return;
				//
				// //
				// ------------------------------------------------------------------
			case 0x1570:
				super.write32(address, data);
				logger.debug(String.format("DMA PCR2 32bit write %x", data));
				return;
			case 0x10f0:
				logger.debug(String.format("DMA PCR 32bit write %x", data));
				super.write32(address, data);
				return;
				//
				// case 0x10f4:
				// logger.debug(String.format("DMA ICR 32bit write %x", data));
				// {
				// final int tmp = (~data) & HW_DMA_ICR;
				// HW_DMA_ICR = ((tmp ^ data) & 0xffffff) ^ tmp;
				// }
				// return;
				//
				// case 0x1574:
				// logger.debug(String.format("DMA ICR2 32bit write %x", data));
				// {
				// final int tmp = (~data) & HW_DMA_ICR2;
				// HW_DMA_ICR2 = ((tmp ^ data) & 0xffffff) ^ tmp;
				// }
				// return;
				//
				// //
				// ------------------------------------------------------------------
				// /*
				// * case 0x1810:
				// logger.debug(String.format("GPU DATA 32bit write %x", value);
				// GPU_writeData(value); return); case 0x1814:
				// * logger.debug(String.format("GPU STATUS 32bit write %x",
				// value);
				// GPU_writeStatus(value); return);
				// */
				// /*
				// * case 0x1820: mdecWrite0(value); break; case 0x1824:
				// mdecWrite1(value); break;
				// */
				// case IOP_T0_COUNT:
				// logger.debug(String.format("COUNTER 0 COUNT 32bit write %x",
				// data));
				// psxRcntWcount16(0, data);
				// return;
				// case IOP_T0_MODE:
				// logger.debug(String.format("COUNTER 0 MODE 32bit write %x",
				// data));
				// psxRcntWmode16(0, data);
				// return;
				// case IOP_T0_TARGET:
				// logger.debug(String.format("COUNTER 0 TARGET 32bit write %x",
				// data));
				// psxRcntWtarget16(0, data);
				// return;
				//
				// case IOP_T1_COUNT:
				// logger.debug(String.format("COUNTER 1 COUNT 32bit write %x",
				// data));
				// psxRcntWcount16(1, data);
				// return;
				// case IOP_T1_MODE:
				// logger.debug(String.format("COUNTER 1 MODE 32bit write %x",
				// data));
				// psxRcntWmode16(1, data);
				// return;
				// case IOP_T1_TARGET:
				// logger.debug(String.format("COUNTER 1 TARGET 32bit write %x",
				// data));
				// psxRcntWtarget16(1, data);
				// return;
				//
				// case IOP_T2_COUNT:
				// logger.debug(String.format("COUNTER 2 COUNT 32bit write %x",
				// data));
				// psxRcntWcount16(2, data);
				// return;
				// case IOP_T2_MODE:
				// logger.debug(String.format("COUNTER 2 MODE 32bit write %x",
				// data));
				// psxRcntWmode16(0, data);
				// return;
				// case IOP_T2_TARGET:
				// logger.debug(String.format("COUNTER 2 TARGET 32bit write %x",
				// data));
				// psxRcntWtarget16(2, data);
				// return;
				//
				// case IOP_T3_COUNT:
				// logger.debug(String.format("COUNTER 3 COUNT 32bit write %x",
				// data));
				// psxRcntWcount32(3, data);
				// return;
				// case IOP_T3_MODE:
				// logger.debug(String.format("COUNTER 3 MODE 32bit write %x",
				// data));
				// psxRcntWmode32(3, data);
				// return;
				// case IOP_T3_TARGET:
				// logger.debug(String.format("COUNTER 3 TARGET 32bit write %x",
				// data));
				// psxRcntWtarget32(3, data);
				// return;
				//
				// case IOP_T4_COUNT:
				// logger.debug(String.format("COUNTER 4 COUNT 32bit write %x",
				// data));
				// psxRcntWcount32(4, data);
				// return;
				// case IOP_T4_MODE:
				// logger.debug(String.format("COUNTER 4 MODE 32bit write %x",
				// data));
				// psxRcntWmode32(4, data);
				// return;
				// case IOP_T4_TARGET:
				// logger.debug(String.format("COUNTER 4 TARGET 32bit write %x",
				// data));
				// psxRcntWtarget32(4, data);
				// return;
				//
				// case IOP_T5_COUNT:
				// logger.debug(String.format("COUNTER 5 COUNT 32bit write %x",
				// data));
				// psxRcntWcount32(5, data);
				// return;
				// case IOP_T5_MODE:
				// logger.debug(String.format("COUNTER 5 MODE 32bit write %x",
				// data));
				// psxRcntWmode32(5, data);
				// return;
				// case IOP_T5_TARGET:
				// logger.debug(String.format("COUNTER 5 TARGET 32bit write %x",
				// data));
				// psxRcntWtarget32(5, data);
				// return;

				// ------------------------------------------------------------------
			case 0x14c0:
				logger.debug(String.format("RTC_HOLDMODE 32bit write %x", data));
				break;

			case 0x1450:
				// if (data != 0) {
				logger.debug(String.format("%08X ICFG 32bit write %x", cpu.pc, data));
				// }
				/*
				 * if (value && psxSu32(0x20) == 0x20000 && (psxSu32(0x30) ==
				 * 0x20000 || psxSu32(0x30) == 0x40000)) { // don't ask me why
				 * :P psxSu32(0x20) = 0x10000; psxSu32(0x30) = 0x10000; }
				 */
				// psxHu32(0x1450) = /* ( */value/* & (~0x8)) | (psxHu32(0x1450)
				// &
				// 0x8) */;
				super.write32(address, data);
				return;

				// ------------------------------------------------------------------
				// case 0x8200:
				// case 0x8204:
				// case 0x8208:
				// case 0x820C:
				// case 0x8210:
				// case 0x8214:
				// case 0x8218:
				// case 0x821C:
				// case 0x8220:
				// case 0x8224:
				// case 0x8228:
				// case 0x822C:
				// case 0x8230:
				// case 0x8234:
				// case 0x8238:
				// case 0x823C:
				// logger.debug(String.format("SIO2 write param[%d] <- %x",
				// (address
				// - 8200) / 4, data));
				// sio2_setSend3((address - 8200) / 4, data);
				// return;
				//
				// case 0x8240:
				// case 0x8248:
				// case 0x8250:
				// case 0x8258:
				// logger.debug(String.format("SIO2 write send1[%d] <- %x",
				// (address
				// - 8240) / 8, data));
				// sio2_setSend1((address - 8240) / 8, data);
				// return;
				//
				// case 0x8244:
				// case 0x824C:
				// case 0x8254:
				// case 0x825C:
				// logger.debug(String.format("SIO2 write send2[%d] <- %x",
				// (address
				// - 8244) / 8, data));
				// sio2_setSend2((address - 8244) / 8, data);
				// return;
				//
				// case 0x8268:
				// logger.debug(String.format("SIO2 write CTRL <- %x", data));
				// sio2_setCtrl(data);
				// return;
				//
				// case 0x8278:
				// logger.debug(String.format("SIO2 write [8278] <- %x", data));
				// sio2_set8278(data);
				// return;
				//
				// case 0x827C:
				// logger.debug(String.format("SIO2 write [827C] <- %x", data));
				// sio2_set827C(data);
				// return;
				//
				// case 0x8280:
				// logger.debug(String.format("SIO2 write INTR <- %x", data));
				// sio2_setIntr(data);
				// return;

				// ------------------------------------------------------------------
			default:
				super.write32(address, data);
				logger.debug(String.format("*Unknown 32bit write at address %x value %x", address, data));
				return;
		}
	}

	@Override
	public void write64(final int address, final long data) {
		throw new RuntimeException("IOPMemory.write64()");
	}

	@Override
	public void write8(int address, final byte data) {
		address &= offset;
		if (address >= HW_USB_START && address < HW_USB_END) {
			logger.debug(String.format("USB write8 %x", address));
			PluginManager.getUsbPlugin().usbWrite8(address, data);
			return;
		}
		if ((address & 0xf) == 0xa) {
			logger.debug(String.format("8bit write (possible chcr set) to addr 0x%x = 0x%x", address, data));
		}

		switch (address) {
			case 0x1040:
				logger.debug(String.format("SIO write8 %x", address));
				Emulator.getInstance().getSio().sioWrite8(data);
				break;
			// case 0x1050: serial_write8(value); break;//serial port

			case IOP_T0_COUNT:
			case IOP_T0_MODE:
			case IOP_T0_TARGET:
			case IOP_T1_COUNT:
			case IOP_T1_MODE:
			case IOP_T1_TARGET:
			case IOP_T2_COUNT:
			case IOP_T2_MODE:
			case IOP_T2_TARGET:
			case IOP_T3_COUNT:
			case IOP_T3_MODE:
			case IOP_T3_TARGET:
			case IOP_T4_COUNT:
			case IOP_T4_MODE:
			case IOP_T4_TARGET:
			case IOP_T5_COUNT:
			case IOP_T5_MODE:
			case IOP_T5_TARGET:
				logger.debug(String.format("IOP Counter Write8 to addr 0x%x = 0x%x", address, data));
				super.write8(address, data);
				return;

			case 0x1450:
				logger.debug(String.format("%08X ICFG 8bit write %x", cpu.pc, data));
				super.write8(address, data);
				return;

				// case 0x1800:
				// cdrWrite0(data);
				// break;
				// case 0x1801:
				// cdrWrite1(data);
				// break;
				// case 0x1802:
				// cdrWrite2(data);
				// break;
				// case 0x1803:
				// cdrWrite3(data);
				// break;

			case 0x380c: {
				logger.debug(String.format("380c write8 %x", address));
				// bool flush = false;
				// TODO - Util, processador de textos
				// Terminate lines on CR or full buffers, and ignore \n's if the
				// string contents
				// are empty (otherwise terminate on \n too!)
				// if ((data == '\r') || (g_pbufi == 1023) || (data == '\n' &&
				// g_pbufi != 0)) {
				// g_pbuf[g_pbufi] = 0;
				// DevCon.WriteLn(Color_Cyan, "%s", g_pbuf);
				// g_pbufi = 0;
				// } else if (value != '\n') {
				// g_pbuf[g_pbufi++] = value;
				// }
				super.write8(address, data);
			}
				return;

				// case 0x8260:
				// logger.debug(String.format("SIO2 write8 DATAIN <- %08X",
				// data));
				// sio2_serialIn(data);
				// return;// serial data feed/fifo

			default:
				super.write8(address, data);
				logger.debug(String.format("*Unknown 8bit write at address %x value %x", address, data));
				return;
		}
	}

	@Override
	public void writeStream(final int address, final InputStream input) {
		throw new RuntimeException("Unsuported Operation (copy is not allowed here (IOP)).");
	}
}
