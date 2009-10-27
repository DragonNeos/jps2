package com.jps2.core.cpu.r3000a;

public interface HardwareRegisters {
	public static final int	HW_USB_START	     = 0x1600;
	public static final int	HW_USB_END	         = 0x1700;
	public static final int	HW_FW_START	         = 0x8400;
	public static final int	HW_FW_END	         = 0x8550;	  // end addr for FW is a guess...
	public static final int	HW_SPU2_START	     = 0x1c00;
	public static final int	HW_SPU2_END	         = 0x1e00;

	public static final int	HW_SSBUS_SPD_ADDR	 = 0x1000;
	public static final int	HW_SSBUS_PIO_ADDR	 = 0x1004;
	public static final int	HW_SSBUS_SPD_DELAY	 = 0x1008;
	public static final int	HW_SSBUS_DEV1_DELAY	 = 0x100C;
	public static final int	HW_SSBUS_ROM_DELAY	 = 0x1010;
	public static final int	HW_SSBUS_SPU_DELAY	 = 0x1014;
	public static final int	HW_SSBUS_DEV5_DELAY	 = 0x1018;
	public static final int	HW_SSBUS_PIO_DELAY	 = 0x101c;
	public static final int	HW_SSBUS_COM_DELAY	 = 0x1020;

	// SIO read/write register
	public static final int	HW_SIO_DATA	         = 0x1040;
	public static final int	HW_SIO_STAT	         = 0x1044;
	public static final int	HW_SIO_MODE	         = 0x1048;
	public static final int	HW_SIO_CTRL	         = 0x104a;
	public static final int	HW_SIO_BAUD	         = 0x104e;

	// Memory registers
	public static final int	HW_RAM_SIZE	         = 0x1060;
	public static final int	HW_IREG	             = 0x1070;
	public static final int	HW_IMASK	         = 0x1074;
	public static final int	HW_ICTRL	         = 0x1078;

	// SSBUS registers
	public static final int	HW_SSBUS_DEV1_ADDR	 = 0x1400;
	public static final int	HW_SSBUS_SPU_ADDR	 = 0x1404;
	public static final int	HW_SSBUS_DEV5_ADDR	 = 0x1408;
	public static final int	HW_SSBUS_SPU1_ADDR	 = 0x140c;
	public static final int	HW_SSBUS_DEV9_ADDR3	 = 0x1410;
	public static final int	HW_SSBUS_SPU1_DELAY	 = 0x1414;
	public static final int	HW_SSBUS_DEV9_DELAY2	= 0x1418;
	public static final int	HW_SSBUS_DEV9_DELAY3	= 0x141c;
	public static final int	HW_SSBUS_DEV9_DELAY1	= 0x1420;

	// ???
	public static final int	HW_ICFG	             = 0x1450;
	public static final int	HW_DEV9_DATA	     = 0x146e;	  // DEV9 read/write register

	// CDRom registers are used for various command; status; and data stuff.
	public static final int	HW_CDR_DATA0	     = 0x1800;	  // CDROM multipurpose data register 1
	public static final int	HW_CDR_DATA1	     = 0x1801;	  // CDROM multipurpose data register 2
	public static final int	HW_CDR_DATA2	     = 0x1802;	  // CDROM multipurpose data register 3
	public static final int	HW_CDR_DATA3	     = 0x1803;	  // CDROM multipurpose data register 4

	// SIO2 is a DMA interface for the SIO.
	public static final int	HW_SIO2_DATAIN	     = 0x8260;
	public static final int	HW_SIO2_FIFO	     = 0x8264;
	public static final int	HW_SIO2_CTRL	     = 0x8268;
	public static final int	HW_SIO2_RECV1	     = 0x826c;
	public static final int	HW_SIO2_RECV2	     = 0x8270;
	public static final int	HW_SIO2_RECV3	     = 0x8274;
	public static final int	HW_SIO2_8278	     = 0x8278;	  // May as well add defs
	public static final int	HW_SIO2_827C	     = 0x827C;	  // for these 2...
	public static final int	HW_SIO2_INTR	     = 0x8280;

	// DMAMadrAddresses
	public static final int	HWx_DMA0_MADR	     = 0x1080;
	public static final int	HWx_DMA1_MADR	     = 0x1090;
	public static final int	HWx_DMA2_MADR	     = 0x10a0;
	public static final int	HWx_DMA3_MADR	     = 0x10b0;
	public static final int	HWx_DMA4_MADR	     = 0x10c0;
	public static final int	HWx_DMA5_MADR	     = 0x10d0;
	public static final int	HWx_DMA6_MADR	     = 0x10e0;
	public static final int	HWx_DMA7_MADR	     = 0x1500;
	public static final int	HWx_DMA8_MADR	     = 0x1510;
	public static final int	HWx_DMA9_MADR	     = 0x1520;
	public static final int	HWx_DMA10_MADR	     = 0x1530;
	public static final int	HWx_DMA11_MADR	     = 0x1540;
	public static final int	HWx_DMA12_MADR	     = 0x1550;

	// DMABcrAddresses
	public static final int	HWx_DMA0_BCR	     = 0x1084;
	public static final int	HWx_DMA1_BCR	     = 0x1094;
	public static final int	HWx_DMA2_BCR	     = 0x10a4;
	public static final int	HWx_DMA3_BCR	     = 0x10b4;
	public static final int	HWx_DMA3_BCR_L16	 = 0x10b4;
	public static final int	HWx_DMA3_BCR_H16	 = 0x10b6;
	public static final int	HWx_DMA4_BCR	     = 0x10c4;
	public static final int	HWx_DMA5_BCR	     = 0x10d4;
	public static final int	HWx_DMA6_BCR	     = 0x10e4;
	public static final int	HWx_DMA7_BCR	     = 0x1504;
	public static final int	HWx_DMA8_BCR	     = 0x1514;
	public static final int	HWx_DMA9_BCR	     = 0x1524;
	public static final int	HWx_DMA10_BCR	     = 0x1534;
	public static final int	HWx_DMA11_BCR	     = 0x1544;
	public static final int	HWx_DMA12_BCR	     = 0x1554;

	// DMAChcrAddresses
	public static final int	HWx_DMA0_CHCR	     = 0x1088;
	public static final int	HWx_DMA1_CHCR	     = 0x1098;
	public static final int	HWx_DMA2_CHCR	     = 0x10a8;
	public static final int	HWx_DMA3_CHCR	     = 0x10b8;
	public static final int	HWx_DMA4_CHCR	     = 0x10c8;
	public static final int	HWx_DMA5_CHCR	     = 0x10d8;
	public static final int	HWx_DMA6_CHCR	     = 0x10e8;
	public static final int	HWx_DMA7_CHCR	     = 0x1508;
	public static final int	HWx_DMA8_CHCR	     = 0x1518;
	public static final int	HWx_DMA9_CHCR	     = 0x1528;
	public static final int	HWx_DMA10_CHCR	     = 0x1538;
	public static final int	HWx_DMA11_CHCR	     = 0x1548;
	public static final int	HWx_DMA12_CHCR	     = 0x1558;

	// DMATadrAddresses
	public static final int	HWx_DMA0_TADR	     = 0x108c;
	public static final int	HWx_DMA1_TADR	     = 0x109c;
	public static final int	HWx_DMA2_TADR	     = 0x10ac;
	public static final int	HWx_DMA3_TADR	     = 0x10bc;
	public static final int	HWx_DMA4_TADR	     = 0x10cc;
	public static final int	HWx_DMA5_TADR	     = 0x10dc;
	public static final int	HWx_DMA6_TADR	     = 0x10ec;
	public static final int	HWx_DMA7_TADR	     = 0x150c;
	public static final int	HWx_DMA8_TADR	     = 0x151c;
	public static final int	HWx_DMA9_TADR	     = 0x152c;
	public static final int	HWx_DMA10_TADR	     = 0x153c;
	public static final int	HWx_DMA11_TADR	     = 0x154c;
	public static final int	HWx_DMA12_TADR	     = 0x155c;

	// Registers for the IOP Counters */
	public static final int	IOP_T0_COUNT	     = 0x1100;
	public static final int	IOP_T1_COUNT	     = 0x1110;
	public static final int	IOP_T2_COUNT	     = 0x1120;
	public static final int	IOP_T3_COUNT	     = 0x1480;
	public static final int	IOP_T4_COUNT	     = 0x1490;
	public static final int	IOP_T5_COUNT	     = 0x14a0;

	public static final int	IOP_T0_MODE	         = 0x1104;
	public static final int	IOP_T1_MODE	         = 0x1114;
	public static final int	IOP_T2_MODE	         = 0x1124;
	public static final int	IOP_T3_MODE	         = 0x1484;
	public static final int	IOP_T4_MODE	         = 0x1494;
	public static final int	IOP_T5_MODE	         = 0x14a4;

	public static final int	IOP_T0_TARGET	     = 0x1108;
	public static final int	IOP_T1_TARGET	     = 0x1118;
	public static final int	IOP_T2_TARGET	     = 0x1128;
	public static final int	IOP_T3_TARGET	     = 0x1488;
	public static final int	IOP_T4_TARGET	     = 0x1498;
	public static final int	IOP_T5_TARGET	     = 0x14a8;

	public static final int	IOP_EVT_CDVD	         = 5;	      // General Cdvd commands (Seek, Standby, Break, etc)
	public static final int	IOP_EVT_SIF0	         = 9;
	public static final int	IOP_EVT_SIF1	         = 10;
	public static final int	IOP_EVT_DMA11	     = 11;
	public static final int	IOP_EVT_DMA12	     = 12;
	public static final int	IOP_EVT_SIO	         = 16;
	public static final int	IOP_EVT_CDROM	     = 17;
	public static final int	IOP_EVT_CDROMREAD	 = 18;
	public static final int	IOP_EVT_CDVDREAD	     = 19;
	public static final int	IOP_EVT_DEV9	         = 20;
	public static final int	IOP_EVT_USB	         = 21;

}
