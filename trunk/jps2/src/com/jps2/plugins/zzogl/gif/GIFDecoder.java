package com.jps2.plugins.zzogl.gif;

public final class GIFDecoder {

	private static final GIFRegHandler[] handlerPackedTable = {
			GIFRegHandlers.PRIM, GIFRegHandlers.PACKED_RGBA,
			GIFRegHandlers.PACKED_STQ, GIFRegHandlers.PACKED_UV,
			GIFRegHandlers.PACKED_XYZF2, GIFRegHandlers.PACKED_XYZ2,
			GIFRegHandlers.TEX0_1, GIFRegHandlers.TEX0_2,
			GIFRegHandlers.CLAMP_1, GIFRegHandlers.CLAMP_2,
			GIFRegHandlers.PACKED_FOG, GIFRegHandlers.PACKED_NULL,
			GIFRegHandlers.XYZF3, GIFRegHandlers.XYZ3,
			GIFRegHandlers.PACKED_A_D, GIFRegHandlers.PACKED_NOP };

	private static final GIFRegHandler[] handlerTable = { GIFRegHandlers.PRIM,
			GIFRegHandlers.RGBAQ, GIFRegHandlers.ST, GIFRegHandlers.UV,
			GIFRegHandlers.XYZF2, GIFRegHandlers.XYZ2, GIFRegHandlers.TEX0_1,
			GIFRegHandlers.TEX0_2, GIFRegHandlers.CLAMP_1,
			GIFRegHandlers.CLAMP_2, GIFRegHandlers.FOG, GIFRegHandlers.NULL,
			GIFRegHandlers.XYZF3, GIFRegHandlers.XYZ3, GIFRegHandlers.NOP,
			GIFRegHandlers.NOP, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.TEX1_1,
			GIFRegHandlers.TEX1_2, GIFRegHandlers.TEX2_1,
			GIFRegHandlers.TEX2_2, GIFRegHandlers.XYOFFSET_1,
			GIFRegHandlers.XYOFFSET_2, GIFRegHandlers.PRMODECONT,
			GIFRegHandlers.PRMODE, GIFRegHandlers.TEXCLUT, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.SCANMSK, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.MIPTBP1_1,
			GIFRegHandlers.MIPTBP1_2, GIFRegHandlers.MIPTBP2_1,
			GIFRegHandlers.MIPTBP2_2, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.TEXA, GIFRegHandlers.NULL,
			GIFRegHandlers.FOGCOL, GIFRegHandlers.NULL,
			GIFRegHandlers.TEXFLUSH, GIFRegHandlers.SCISSOR_1,
			GIFRegHandlers.SCISSOR_2, GIFRegHandlers.ALPHA_1,
			GIFRegHandlers.ALPHA_2, GIFRegHandlers.DIMX, GIFRegHandlers.DTHE,
			GIFRegHandlers.COLCLAMP, GIFRegHandlers.TEST_1,
			GIFRegHandlers.TEST_2, GIFRegHandlers.PABE, GIFRegHandlers.FBA_1,
			GIFRegHandlers.FBA_2, GIFRegHandlers.FRAME_1,
			GIFRegHandlers.FRAME_2, GIFRegHandlers.ZBUF_1,
			GIFRegHandlers.ZBUF_2, GIFRegHandlers.BITBLTBUF,
			GIFRegHandlers.TRXPOS, GIFRegHandlers.TRXREG,
			GIFRegHandlers.TRXDIR, GIFRegHandlers.HWREG, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.NULL, GIFRegHandlers.NULL,
			GIFRegHandlers.NULL, GIFRegHandlers.SIGNAL, GIFRegHandlers.FINISH,
			GIFRegHandlers.LABEL, GIFRegHandlers.NULL };

	public static void handlerReg(final int reg, final int... data) {
		handlerTable[reg].handler(data);
	}

	public static void handlerPackedReg(final int reg, final int... data) {
		handlerPackedTable[reg].handler(data);
	}

	private GIFDecoder() {
	}
}
