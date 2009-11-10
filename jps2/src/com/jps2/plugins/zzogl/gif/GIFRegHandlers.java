package com.jps2.plugins.zzogl.gif;

import com.jps2.plugins.zzogl.GSInternal;

public final class GIFRegHandlers {

	public static final GIFRegHandler PACKED_RGBA = new GIFRegHandler(
			"PACKED_RGBA") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.rgba = (data[0] & 0xff) /**/
					| ((data[1] & 0xff) << 8) /**/
					| ((data[2] & 0xff) << 16) /**/
					| ((data[3] & 0xff) << 24);

			gs.vertexregs.rgba = gs.rgba;
			gs.vertexregs.q = gs.q;
		}
	};
	public static final GIFRegHandler PACKED_STQ = new GIFRegHandler(
			"PACKED_STQ") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.vertexregs.s = data[0] & 0xffffff00;
			gs.vertexregs.t = data[1] & 0xffffff00;
			gs.q = data[2];
		}
	};
	public static final GIFRegHandler PACKED_UV = new GIFRegHandler("PACKED_UV") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.vertexregs.u = (short) (data[0] & 0x3fff);
			gs.vertexregs.v = (short) (data[1] & 0x3fff);
		}
	};
	public static final GIFRegHandler PACKED_XYZF2 = new GIFRegHandler(
			"PACKED_XYZF2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler PACKED_XYZ2 = new GIFRegHandler(
			"PACKED_XYZ2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler PACKED_FOG = new GIFRegHandler(
			"PACKED_FOG") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.vertexregs.f = (short) ((data[3] & 0xff0) >> 4);
		}
	};
	public static final GIFRegHandler PACKED_NULL = new GIFRegHandler(
			"PACKED_NULL") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler PACKED_A_D = new GIFRegHandler(
			"PACKED_A_D") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			if ((data[2] & 0xff) < 100) {
				GIFDecoder.handlerReg(data[2] & 0xff, data);
			} else {
				NULL.handler(gs, data);
			}
		}
	};
	public static final GIFRegHandler PACKED_NOP = new GIFRegHandler(
			"PACKED_NOP") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			// Nothing to do
		}
	};

	public static final GIFRegHandler PRIM = new GIFRegHandler("PRIM") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler RGBAQ = new GIFRegHandler("RGBAQ") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.rgba = data[0];
			gs.vertexregs.rgba = data[0];
			gs.vertexregs.q = data[1];
		}
	};
	public static final GIFRegHandler ST = new GIFRegHandler("ST") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.vertexregs.s = data[0] & 0xffffff00;
			gs.vertexregs.t = data[1] & 0xffffff00;
		}
	};
	public static final GIFRegHandler UV = new GIFRegHandler("UV") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.vertexregs.u = (short) ((data[0]) & 0x3fff);
			gs.vertexregs.v = (short) ((data[0] >> 16) & 0x3fff);
		}
	};
	public static final GIFRegHandler XYZF2 = new GIFRegHandler("XYZF2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler XYZ2 = new GIFRegHandler("XYZ2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEX0_1 = new GIFRegHandler("TEX0_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEX0_2 = new GIFRegHandler("TEX0_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler CLAMP_1 = new GIFRegHandler("CLAMP_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler CLAMP_2 = new GIFRegHandler("CLAMP_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler FOG = new GIFRegHandler("FOG") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.vertexregs.f = (short) (data[1] >> 24);
		}
	};
	public static final GIFRegHandler XYZF3 = new GIFRegHandler("XYZF3") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler XYZ3 = new GIFRegHandler("XYZ3") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler NOP = new GIFRegHandler("NOP") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			// Nothing to do
		}
	};
	public static final GIFRegHandler TEX1_1 = new GIFRegHandler("TEX1_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEX1_2 = new GIFRegHandler("TEX1_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEX2_1 = new GIFRegHandler("TEX2_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEX2_2 = new GIFRegHandler("TEX2_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler XYOFFSET_1 = new GIFRegHandler(
			"XYOFFSET_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler XYOFFSET_2 = new GIFRegHandler(
			"XYOFFSET_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler PRMODECONT = new GIFRegHandler(
			"PRMODECONT") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler PRMODE = new GIFRegHandler("PRMODE") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEXCLUT = new GIFRegHandler("TEXCLUT") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler SCANMSK = new GIFRegHandler("SCANMSK") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler MIPTBP1_1 = new GIFRegHandler("MIPTBP1_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler MIPTBP1_2 = new GIFRegHandler("MIPTBP1_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler MIPTBP2_1 = new GIFRegHandler("MIPTBP2_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler MIPTBP2_2 = new GIFRegHandler("MIPTBP2_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEXA = new GIFRegHandler("TEXA") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler FOGCOL = new GIFRegHandler("FOGCOL") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEXFLUSH = new GIFRegHandler("TEXFLUSH") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler SCISSOR_1 = new GIFRegHandler("SCISSOR_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler SCISSOR_2 = new GIFRegHandler("SCISSOR_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler ALPHA_1 = new GIFRegHandler("ALPHA_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler ALPHA_2 = new GIFRegHandler("ALPHA_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler DIMX = new GIFRegHandler("DIMX") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler DTHE = new GIFRegHandler("DTHE") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler COLCLAMP = new GIFRegHandler("COLCLAMP") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEST_1 = new GIFRegHandler("TEST_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler TEST_2 = new GIFRegHandler("TEST_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler PABE = new GIFRegHandler("PABE") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler FBA_1 = new GIFRegHandler("FBA_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler FBA_2 = new GIFRegHandler("FBA_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler FRAME_1 = new GIFRegHandler("FRAME_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler FRAME_2 = new GIFRegHandler("FRAME_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler ZBUF_1 = new GIFRegHandler("ZBUF_1") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler ZBUF_2 = new GIFRegHandler("ZBUF_2") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler BITBLTBUF = new GIFRegHandler("BITBLTBUF") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.srcbufnew.bp = ((data[0]) & 0x3fff);// * 64;
			gs.srcbufnew.bw = ((data[0] >> 16) & 0x3f) * 64;
			gs.srcbufnew.psm = (data[0] >> 24) & 0x3f;
			gs.dstbufnew.bp = ((data[1]) & 0x3fff);// * 64;
			gs.dstbufnew.bw = ((data[1] >> 16) & 0x3f) * 64;
			gs.dstbufnew.psm = (data[1] >> 24) & 0x3f;

			if (gs.dstbufnew.bw == 0) {
				gs.dstbufnew.bw = 64;
			}
		}
	};
	public static final GIFRegHandler TRXPOS = new GIFRegHandler("TRXPOS") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.trxposnew.sx = (data[0]) & 0x7ff;
			gs.trxposnew.sy = (data[0] >> 16) & 0x7ff;
			gs.trxposnew.dx = (data[1]) & 0x7ff;
			gs.trxposnew.dy = (data[1] >> 16) & 0x7ff;
			gs.trxposnew.dir = (data[1] >> 27) & 0x3;
		}
	};
	public static final GIFRegHandler TRXREG = new GIFRegHandler("TRXREG") {
		@Override
		public void handler(final GSInternal gs, final int... data) {
			gs.imageWtemp = data[0] & 0xfff;
			gs.imageHtemp = data[1] & 0xfff;
		}
	};
	public static final GIFRegHandler TRXDIR = new GIFRegHandler("TRXDIR") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler HWREG = new GIFRegHandler("HWREG") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler SIGNAL = new GIFRegHandler("SIGNAL") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler FINISH = new GIFRegHandler("FINISH") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler LABEL = new GIFRegHandler("LABEL") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};
	public static final GIFRegHandler NULL = new GIFRegHandler("NULL") {
		@Override
		public void handler(final GSInternal gs, final int... data) {// TODO -
			// make
			// it
		}
	};

	private GIFRegHandlers() {
	}
}
