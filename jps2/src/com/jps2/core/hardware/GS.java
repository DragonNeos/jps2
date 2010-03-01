package com.jps2.core.hardware;

import org.apache.log4j.Logger;

import com.jps2.core.memory.FastMemory;

public class GS extends FastMemory {

	public static final Logger	logger			= Logger.getLogger(GS.class);

	// HW Registers
	public static final int		GS_PMODE		= 0x0000;
	public static final int		GS_SMODE1		= 0x0010;
	public static final int		GS_SMODE2		= 0x0020;
	public static final int		GS_SRFSH		= 0x0030;
	public static final int		GS_SYNCH1		= 0x0040;
	public static final int		GS_SYNCH2		= 0x0050;
	public static final int		GS_SYNCV		= 0x0060;
	public static final int		GS_DISPFB1		= 0x0070;
	public static final int		GS_DISPLAY1		= 0x0080;
	public static final int		GS_DISPFB2		= 0x0090;
	public static final int		GS_DISPLAY2		= 0x00A0;
	public static final int		GS_EXTBUF		= 0x00B0;
	public static final int		GS_EXTDATA		= 0x00C0;
	public static final int		GS_EXTWRITE		= 0x00D0;
	public static final int		GS_BGCOLOR		= 0x00E0;
	public static final int		GS_CSR			= 0x1000;
	public static final int		GS_IMR			= 0x1010;
	public static final int		GS_BUSDIR		= 0x1040;
	public static final int		GS_SIGLBLID		= 0x1080;

	// Modes
	public static final int		GS_REGION_PAL	= 0;
	public static final int		GS_REGION_NTSC	= 1;

	int							regionMode		= 0;

	boolean						isInterlaced;

	public GS() {
		super("GS Memory", 0x200000);
	}

	@Override
	public void reset() {
		isInterlaced = false;
		super.reset();
	}

	@Override
	public void write64(int address, long data) {
		address &= offset;
		writeSMODE(address, (int) (data & 0xFFFFFFFF));
		switch (address) {
			default: {
				System.out.println("GS.write64() : " + Integer.toHexString(address));
				super.write64(address, data);
			}
		}
	}

	private final void writeSMODE(int address, int value) {
		switch (address) {
			case GS_SMODE1: {
				setRegionMode(((value & 0x6000) == 0x6000) ? GS_REGION_PAL : GS_REGION_NTSC);
				break;
			}
			case GS_SMODE2: {
				isInterlaced = (value & 0x1) == 0x1;
				break;
			}
		}
	}

	private final void setRegionMode(int region) {
		if (regionMode == region) {
			return;
		} else {
			regionMode = region;
			logger.info(String.format("%s Display Mode Initialized.", regionMode == GS_REGION_PAL ? "PAL" : "NTSC"));
			// UpdateVSyncRate();
		}
	}

}
