package com.jps2.core.cpu.ee;

public class EESyncCounter {

	// Set during the Render/Frame Scanlines
	public static final int MODE_VRENDER = 0x0;
	// Set during the Blanking Scanlines
	public static final int MODE_VBLANK = 0x1;
	// Set during the Syncing Scanlines
	public static final int MODE_VSYNC = 0x3;
	// Set during the Blanking Scanlines (half-frame 1)
	public static final int MODE_VBLANK1 = 0x0;
	// Set during the Blanking Scanlines (half-frame 2)
	public static final int MODE_VBLANK2 = 0x1;
	// Set for ~5/6 of 1 Scanline
	public static final int MODE_HRENDER = 0x0;
	// Set for the remaining ~1/6 of 1 Scanline
	public static final int MODE_HBLANK = 0x1;

	public int mode;
	// start cycle of timer
	public int sCycle;
	public int cycleT;
}
