package com.jps2.core.cpu.ee.state;

import java.util.Arrays;
import java.util.Random;

/**
 * Vectorial Floating Point Unit, handles scalar, vector and matrix operations.
 * 
 * @author hli
 */
public abstract class VfpuState extends FpuState {

	public float[][][]	       vpr;	            // mtx, fsl, idx
	private static final float	floatConstants[]	= { 0.0f, Float.MAX_VALUE, (float) Math.sqrt(2.0f), (float) Math.sqrt(0.5f), 2.0f / (float) Math.sqrt(Math.PI), 2.0f / (float) Math.PI,
	        1.0f / (float) Math.PI, (float) Math.PI / 4.0f, (float) Math.PI / 2.0f, (float) Math.PI, (float) Math.E,
	        (float) (Math.log(Math.E) / Math.log(2.0)), // log2(E) = log(E) / log(2)
	        (float) Math.log10(Math.E), (float) Math.log(2.0), (float) Math.log(10.0), (float) Math.PI * 2.0f, (float) Math.PI / 6.0f, (float) Math.log10(2.0),
	        (float) (Math.log(10.0) / Math.log(2.0)), // log2(10) = log(10) / log(2)
	        (float) Math.sqrt(3.0) / 2.0f	     };

	private static Random	   rnd;

	public class Vcr {

		public class PfxSrc /* $128, $129 */{

			public int[]		swz;
			public boolean[]	abs;
			public boolean[]	cst;
			public boolean[]	neg;
			public boolean		enabled;

			public void reset() {
				Arrays.fill(swz, 0);
				Arrays.fill(abs, false);
				Arrays.fill(cst, false);
				Arrays.fill(neg, false);
				enabled = false;
			}

			public PfxSrc() {
				swz = new int[4];
				abs = new boolean[4];
				cst = new boolean[4];
				neg = new boolean[4];
				enabled = false;
			}

			public void copy(final PfxSrc that) {
				swz = that.swz.clone();
				abs = that.abs.clone();
				cst = that.cst.clone();
				neg = that.neg.clone();
				enabled = that.enabled;
			}

			public PfxSrc(final PfxSrc that) {
				copy(that);
			}
		}

		public PfxSrc	pfxs;
		public PfxSrc	pfxt;

		public class PfxDst /* 130 */{

			public int[]		sat;
			public boolean[]	msk;
			public boolean		enabled;

			public void reset() {
				Arrays.fill(sat, 0);
				Arrays.fill(msk, false);
				enabled = false;
			}

			public PfxDst() {
				sat = new int[4];
				msk = new boolean[4];
				enabled = false;
			}

			public void copy(final PfxDst that) {
				sat = that.sat.clone();
				msk = that.msk.clone();
				enabled = that.enabled;
			}

			public PfxDst(final PfxDst that) {
				copy(that);
			}
		}

		public PfxDst		pfxd;
		public boolean[] /* 131 */cc;

		public void reset() {
			pfxs.reset();
			pfxt.reset();
			pfxd.reset();
			Arrays.fill(cc, false);
		}

		public Vcr() {
			pfxs = new PfxSrc();
			pfxt = new PfxSrc();
			pfxd = new PfxDst();
			cc = new boolean[6];
		}

		public void copy(final Vcr that) {
			pfxs.copy(that.pfxs);
			pfxt.copy(that.pfxt);
			pfxd.copy(that.pfxd);
			cc = that.cc.clone();
		}

		public Vcr(final Vcr that) {
			pfxs = new PfxSrc(that.pfxs);
			pfxt = new PfxSrc(that.pfxt);
			pfxd = new PfxDst(that.pfxd);
			cc = that.cc.clone();
		}
	}

	public Vcr	vcr;

	private void resetFpr() {
		for (final float[][] m : vpr) {
			for (final float[] v : m) {
				Arrays.fill(v, 0.0f);
			}
		}
	}

	@Override
	public void reset() {
		resetFpr();
		vcr.reset();
	}

	@Override
	public void resetAll() {
		super.resetAll();
		resetFpr();
		vcr.reset();
	}

	public VfpuState() {
		vpr = new float[8][4][4]; // [matrix][column][row]
		vcr = new Vcr();
		rnd = new Random();
	}

	public void copy(final VfpuState that) {
		super.copy(that);
		vpr = that.vpr.clone();
		vcr = new Vcr(that.vcr);
	}

	public VfpuState(final VfpuState that) {
		super(that);
		vpr = that.vpr.clone();
		vcr = new Vcr(that.vcr);
	}

	private static float[]	v1	= new float[4];
	private static float[]	v2	= new float[4];
	private static float[]	v3	= new float[4];

	// VFPU stuff
	private float transformVr(final int swz, final boolean abs, final boolean cst, final boolean neg, final float[] x) {
		float value = 0.0f;
		if (cst) {
			switch (swz) {
			case 0:
				value = abs ? 3.0f : 0.0f;
				break;
			case 1:
				value = abs ? (1.0f / 3.0f) : 1.0f;
				break;
			case 2:
				value = abs ? (1.0f / 4.0f) : 2.0f;
				break;
			case 3:
				value = abs ? (1.0f / 6.0f) : 0.5f;
				break;
			}
		} else {
			value = x[swz];
		}

		if (abs) {
			value = Math.abs(value);
		}
		return neg ? (0.0f - value) : value;
	}

	private float applyPrefixVs(final int i, final float[] x) {
		return transformVr(vcr.pfxs.swz[i], vcr.pfxs.abs[i], vcr.pfxs.cst[i], vcr.pfxs.neg[i], x);
	}

	private float applyPrefixVt(final int i, final float[] x) {
		return transformVr(vcr.pfxt.swz[i], vcr.pfxt.abs[i], vcr.pfxt.cst[i], vcr.pfxt.neg[i], x);
	}

	private float applyPrefixVd(final int i, final float value) {
		switch (vcr.pfxd.sat[i]) {
		case 1:
			return Math.max(0.0f, Math.min(1.0f, value));
		case 3:
			return Math.max(-1.0f, Math.min(1.0f, value));
		}
		return value;
	}

	public void loadVs(final int vsize, final int vs) {
		int m, s, i;

		m = (vs >> 2) & 7;
		i = (vs >> 0) & 3;

		switch (vsize) {
		case 1:
			s = (vs >> 5) & 3;
			v1[0] = vpr[m][i][s];
			if (vcr.pfxs.enabled) {
				v1[0] = applyPrefixVs(0, v1);
				vcr.pfxs.enabled = false;
			}
			return;

		case 2:
			s = (vs & 64) >> 5;
			if ((vs & 32) != 0) {
				v1[0] = vpr[m][s + 0][i];
				v1[1] = vpr[m][s + 1][i];
			} else {
				v1[0] = vpr[m][i][s + 0];
				v1[1] = vpr[m][i][s + 1];
			}
			if (vcr.pfxs.enabled) {
				v3[0] = applyPrefixVs(0, v1);
				v3[1] = applyPrefixVs(1, v1);
				v1[0] = v3[0];
				v1[1] = v3[1];
				vcr.pfxs.enabled = false;
			}
			return;

		case 3:
			s = (vs & 64) >> 6;
			if ((vs & 32) != 0) {
				v1[0] = vpr[m][s + 0][i];
				v1[1] = vpr[m][s + 1][i];
				v1[2] = vpr[m][s + 2][i];
			} else {
				v1[0] = vpr[m][i][s + 0];
				v1[1] = vpr[m][i][s + 1];
				v1[2] = vpr[m][i][s + 2];
			}
			if (vcr.pfxs.enabled) {
				v3[0] = applyPrefixVs(0, v1);
				v3[1] = applyPrefixVs(1, v1);
				v3[2] = applyPrefixVs(2, v1);
				v1[0] = v3[0];
				v1[1] = v3[1];
				v1[2] = v3[2];
				vcr.pfxs.enabled = false;
			}
			return;

		case 4:
			if ((vs & 32) != 0) {
				v1[0] = vpr[m][0][i];
				v1[1] = vpr[m][1][i];
				v1[2] = vpr[m][2][i];
				v1[3] = vpr[m][3][i];
			} else {
				v1[0] = vpr[m][i][0];
				v1[1] = vpr[m][i][1];
				v1[2] = vpr[m][i][2];
				v1[3] = vpr[m][i][3];
			}
			if (vcr.pfxs.enabled) {
				v3[0] = applyPrefixVs(0, v1);
				v3[1] = applyPrefixVs(1, v1);
				v3[2] = applyPrefixVs(2, v1);
				v3[3] = applyPrefixVs(3, v1);
				v1[0] = v3[0];
				v1[1] = v3[1];
				v1[2] = v3[2];
				v1[3] = v3[3];
				vcr.pfxs.enabled = false;
			}
		default:
		}
	}

	public void loadVt(final int vsize, final int vt) {
		int m, s, i;

		m = (vt >> 2) & 7;
		i = (vt >> 0) & 3;

		switch (vsize) {
		case 1:
			s = (vt >> 5) & 3;
			v2[0] = vpr[m][i][s];
			if (vcr.pfxt.enabled) {
				v2[0] = applyPrefixVt(0, v2);
				vcr.pfxt.enabled = false;
			}
			return;

		case 2:
			s = (vt & 64) >> 5;
			if ((vt & 32) != 0) {
				v2[0] = vpr[m][s + 0][i];
				v2[1] = vpr[m][s + 1][i];
			} else {
				v2[0] = vpr[m][i][s + 0];
				v2[1] = vpr[m][i][s + 1];
			}
			if (vcr.pfxt.enabled) {
				v3[0] = applyPrefixVt(0, v2);
				v3[1] = applyPrefixVt(1, v2);
				v2[0] = v3[0];
				v2[1] = v3[1];
				vcr.pfxt.enabled = false;
			}
			return;

		case 3:
			s = (vt & 64) >> 6;
			if ((vt & 32) != 0) {
				v2[0] = vpr[m][s + 0][i];
				v2[1] = vpr[m][s + 1][i];
				v2[2] = vpr[m][s + 2][i];
			} else {
				v2[0] = vpr[m][i][s + 0];
				v2[1] = vpr[m][i][s + 1];
				v2[2] = vpr[m][i][s + 2];
			}
			if (vcr.pfxt.enabled) {
				v3[0] = applyPrefixVt(0, v2);
				v3[1] = applyPrefixVt(1, v2);
				v3[2] = applyPrefixVt(2, v2);
				v2[0] = v3[0];
				v2[1] = v3[1];
				v2[2] = v3[2];
				vcr.pfxt.enabled = false;
			}
			return;

		case 4:
			if ((vt & 32) != 0) {
				v2[0] = vpr[m][0][i];
				v2[1] = vpr[m][1][i];
				v2[2] = vpr[m][2][i];
				v2[3] = vpr[m][3][i];
			} else {
				v2[0] = vpr[m][i][0];
				v2[1] = vpr[m][i][1];
				v2[2] = vpr[m][i][2];
				v2[3] = vpr[m][i][3];
			}
			if (vcr.pfxt.enabled) {
				v3[0] = applyPrefixVt(0, v2);
				v3[1] = applyPrefixVt(1, v2);
				v3[2] = applyPrefixVt(2, v2);
				v3[3] = applyPrefixVt(3, v2);
				v2[0] = v3[0];
				v2[1] = v3[1];
				v2[2] = v3[2];
				v2[3] = v3[3];
				vcr.pfxt.enabled = false;
			}
		default:
		}
	}

	public void saveVd(final int vsize, final int vd, final float[] vr) {
		int m, s, i;

		m = (vd >> 2) & 7;
		i = (vd >> 0) & 3;

		switch (vsize) {
		case 1:
			s = (vd >> 5) & 3;
			if (vcr.pfxd.enabled) {
				if (!vcr.pfxd.msk[0]) {
					vpr[m][i][s] = applyPrefixVd(0, vr[0]);
				}
				vcr.pfxd.enabled = false;
			} else {
				vpr[m][i][s] = vr[0];
			}
			break;

		case 2:
			s = (vd & 64) >> 5;
			if (vcr.pfxd.enabled) {
				if ((vd & 32) != 0) {
					for (int j = 0; j < 2; ++j) {
						if (!vcr.pfxd.msk[j]) {
							vpr[m][s + j][i] = applyPrefixVd(j, vr[j]);
						}
					}
				} else {
					for (int j = 0; j < 2; ++j) {
						if (!vcr.pfxd.msk[j]) {
							vpr[m][i][s + j] = applyPrefixVd(j, vr[j]);
						}
					}
				}
				vcr.pfxd.enabled = false;
			} else {
				if ((vd & 32) != 0) {
					for (int j = 0; j < 2; ++j) {
						vpr[m][s + j][i] = vr[j];
					}
				} else {
					for (int j = 0; j < 2; ++j) {
						vpr[m][i][s + j] = vr[j];
					}
				}
			}
			break;

		case 3:
			s = (vd & 64) >> 6;
			if (vcr.pfxd.enabled) {
				if ((vd & 32) != 0) {
					for (int j = 0; j < 3; ++j) {
						if (!vcr.pfxd.msk[j]) {
							vpr[m][s + j][i] = applyPrefixVd(j, vr[j]);
						}
					}
				} else {
					for (int j = 0; j < 3; ++j) {
						if (!vcr.pfxd.msk[j]) {
							vpr[m][i][s + j] = applyPrefixVd(j, vr[j]);
						}
					}
				}
				vcr.pfxd.enabled = false;
			} else {
				if ((vd & 32) != 0) {
					for (int j = 0; j < 3; ++j) {
						vpr[m][s + j][i] = vr[j];
					}
				} else {
					for (int j = 0; j < 3; ++j) {
						vpr[m][i][s + j] = vr[j];
					}
				}
			}
			break;

		case 4:
			if (vcr.pfxd.enabled) {
				if ((vd & 32) != 0) {
					for (int j = 0; j < 4; ++j) {
						if (!vcr.pfxd.msk[j]) {
							vpr[m][j][i] = applyPrefixVd(j, vr[j]);
						}
					}
				} else {
					for (int j = 0; j < 4; ++j) {
						if (!vcr.pfxd.msk[j]) {
							vpr[m][i][j] = applyPrefixVd(j, vr[j]);
						}
					}
				}
				vcr.pfxd.enabled = false;
			} else {
				if ((vd & 32) != 0) {
					for (int j = 0; j < 4; ++j) {
						vpr[m][j][i] = vr[j];
					}
				} else {
					for (int j = 0; j < 4; ++j) {
						vpr[m][i][j] = vr[j];
					}
				}
			}
			break;

		default:
			break;
		}
	}

	float halffloatToFloat(final int imm16) {
		final int s = (imm16 >> 15) & 0x00000001; // sign
		int e = (imm16 >> 10) & 0x0000001f; // exponent
		int f = (imm16 >> 0) & 0x000003ff; // fraction

		// need to handle 0x7C00 INF and 0xFC00 -INF?
		if (e == 0) {
			// need to handle +-0 case f==0 or f=0x8000?
			if (f == 0) {
				// Plus or minus zero
				return Float.intBitsToFloat(s << 31);
			} else {
				// Denormalized number -- renormalize it
				while ((f & 0x00000400) == 0) {
					f <<= 1;
					e -= 1;
				}
				e += 1;
				f &= ~0x00000400;
			}
		} else if (e == 31) {
			if (f == 0) {
				// Inf
				return Float.intBitsToFloat((s << 31) | 0x7f800000);
			} else {
				// NaN
				return Float.intBitsToFloat((s << 31) | 0x7f800000 | (f << 13));
			}
		}

		e = e + (127 - 15);
		f = f << 13;

		return Float.intBitsToFloat((s << 31) | (e << 23) | f);
	}

	int floatToHalffloat(final float v) {
		final int i = Float.floatToRawIntBits(v);
		final int s = ((i >> 16) & 0x00008000); // sign
		final int e = ((i >> 23) & 0x000000ff) - (127 - 15); // exponent
		int f = ((i >> 0) & 0x007fffff); // fraction

		// need to handle NaNs and Inf?
		if (e <= 0) {
			if (e < -10) {
				if (s != 0) {
					// handle -0.0
					return 0x8000;
				}
				return 0;
			}
			f = (f | 0x00800000) >> (1 - e);
			return s | (f >> 13);
		} else if (e == 0xff - (127 - 15)) {
			if (f == 0) {
				// Inf
				return s | 0x7c00;
			}
			// NAN
			f >>= 13;
			return s | 0x7c00 | f | ((f == 0) ? 1 : 0);
		}
		if (e > 30) {
			// Overflow
			return s | 0x7c00;
		}
		return s | (e << 10) | (f >> 13);
	}

	// group VFPU0
	// VFPU0:VADD
	public void doVADD(final int vsize, final int vd, final int vs, final int vt) {
		loadVs(vsize, vs);
		loadVt(vsize, vt);

		for (int i = 0; i < vsize; ++i) {
			v1[i] += v2[i];
		}

		saveVd(vsize, vd, v1);
	}

	// VFPU0:VSUB
	public void doVSUB(final int vsize, final int vd, final int vs, final int vt) {
		loadVs(vsize, vs);
		loadVt(vsize, vt);

		for (int i = 0; i < vsize; ++i) {
			v1[i] -= v2[i];
		}

		saveVd(vsize, vd, v1);
	}

	// VFPU0:VSBN
	public void doVSBN(final int vsize, final int vd, final int vs, final int vt) {
		if (vsize != 1) {
			doUNK("Only supported VSBN.S");
		}

		loadVs(1, vs);
		loadVt(1, vt);

		v1[0] = Math.scalb(v1[0], Float.floatToRawIntBits(v2[0]));

		saveVd(1, vd, v1);
	}

	// VFPU0:VDIV
	public void doVDIV(final int vsize, final int vd, final int vs, final int vt) {
		loadVs(vsize, vs);
		loadVt(vsize, vt);

		for (int i = 0; i < vsize; ++i) {
			v1[i] /= v2[i];
		}

		saveVd(vsize, vd, v1);
	}

	// group VFPU1
	// VFPU1:VMUL
	public void doVMUL(final int vsize, final int vd, final int vs, final int vt) {
		loadVs(vsize, vs);
		loadVt(vsize, vt);

		for (int i = 0; i < vsize; ++i) {
			v1[i] *= v2[i];
		}

		saveVd(vsize, vd, v1);
	}

	// VFPU1:VDOT
	public void doVDOT(final int vsize, final int vd, final int vs, final int vt) {
		if (vsize == 1) {
			doUNK("Unsupported VDOT.S");
		}

		loadVs(vsize, vs);
		loadVt(vsize, vt);

		float dot = v1[0] * v2[0];

		for (int i = 1; i < vsize; ++i) {
			dot += v1[i] * v2[i];
		}

		v3[0] = dot;

		saveVd(1, vd, v3);
	}

	// VFPU1:VSCL
	public void doVSCL(final int vsize, final int vd, final int vs, final int vt) {
		if (vsize == 1) {
			doUNK("Unsupported VSCL.S");
		}

		loadVs(vsize, vs);
		loadVt(1, vt);

		final float scale = v2[0];

		for (int i = 0; i < vsize; ++i) {
			v1[i] *= scale;
		}

		saveVd(vsize, vd, v1);
	}

	// VFPU1:VHDP
	public void doVHDP(final int vsize, final int vd, final int vs, final int vt) {
		if (vsize == 1) {
			doUNK("Unsupported VHDP.S");
		}

		loadVs(vsize, vs);
		loadVt(vsize, vt);

		float hdp = v1[0] * v2[0];

		int i;

		for (i = 1; i < vsize - 1; ++i) {
			hdp += v1[i] * v2[i];
		}

		// Tested: last element is only v2[i] (and not v1[i]*v2[i])
		v2[0] = hdp + v2[i];

		saveVd(1, vd, v2);
	}

	// VFPU1:VCRS
	public void doVCRS(final int vsize, final int vd, final int vs, final int vt) {
		if (vsize != 3) {
			doUNK("Only supported VCRS.T");
		}

		loadVs(3, vs);
		loadVt(3, vt);

		v3[0] = v1[1] * v2[2];
		v3[1] = v1[2] * v2[0];
		v3[2] = v1[0] * v2[1];

		saveVd(3, vd, v3);
	}

	// VFPU1:VDET
	public void doVDET(final int vsize, final int vd, final int vs, final int vt) {
		if (vsize != 2) {
			doUNK("Only supported VDET.P");
			return;
		}

		loadVs(2, vs);
		loadVt(2, vt);

		v1[0] = v1[0] * v2[1] - v1[1] * v2[0];

		saveVd(1, vd, v1);
	}

	// VFPU2

	// VFPU2:MFV
	public void doMFV(final int rt, final int imm7) {
		final int r = (imm7 >> 5) & 3;
		final int m = (imm7 >> 2) & 7;
		final int c = (imm7 >> 0) & 3;

		gpr[rt].write32(Float.floatToRawIntBits(vpr[m][c][r]));
	}

	// VFPU2:MFVC
	public void doMFVC(final int rt, final int imm7) {
		doUNK("Unimplemented MFVC (rt=" + rt + ", imm7=" + imm7 + ")");
	}

	// VFPU2:MTV
	public void doMTV(final int rt, final int imm7) {
		final int r = (imm7 >> 5) & 3;
		final int m = (imm7 >> 2) & 7;
		final int c = (imm7 >> 0) & 3;

		vpr[m][c][r] = Float.intBitsToFloat(gpr[rt].read32());
	}

	// VFPU2:MTVC
	public void doMTVC(final int rt, final int imm7) {
		doUNK("Unimplemented MTVC");
	}

	// VFPU2:BVF
	public boolean doBVF(final int imm3, final int simm16) {
		npc = (!vcr.cc[imm3]) ? branchTarget(pc, simm16) : (pc + 4);
		return true;
	}

	// VFPU2:BVT
	public boolean doBVT(final int imm3, final int simm16) {
		npc = (vcr.cc[imm3]) ? branchTarget(pc, simm16) : (pc + 4);
		return true;
	}

	// VFPU2:BVFL
	public boolean doBVFL(final int imm3, final int simm16) {
		if (!vcr.cc[imm3]) {
			npc = branchTarget(pc, simm16);
			return true;
		} else {
			pc = pc + 4;
		}
		return false;
	}

	// VFPU2:BVTL
	public boolean doBVTL(final int imm3, final int simm16) {
		if (vcr.cc[imm3]) {
			npc = branchTarget(pc, simm16);
			return true;
		} else {
			pc = pc + 4;
		}
		return false;
	}

	// group VFPU3

	// VFPU3:VCMP
	public void doVCMP(final int vsize, final int vs, final int vt, final int cond) {
		boolean cc_or = false;
		boolean cc_and = true;

		if ((cond & 8) == 0) {
			final boolean not = ((cond & 4) == 4);

			boolean cc = false;

			loadVs(vsize, vs);
			loadVt(vsize, vt);

			for (int i = 0; i < vsize; ++i) {
				switch (cond & 3) {
				case 0:
					cc = not;
					break;

				case 1:
					cc = not ? (v1[i] != v2[i]) : (v1[i] == v2[i]);
					break;

				case 2:
					cc = not ? (v1[i] >= v2[i]) : (v1[i] < v2[i]);
					break;

				case 3:
					cc = not ? (v1[i] > v2[i]) : (v1[i] <= v2[i]);
					break;

				}

				vcr.cc[i] = cc;
				cc_or = cc_or || cc;
				cc_and = cc_and && cc;
			}

		} else {
			loadVs(vsize, vs);

			for (int i = 0; i < vsize; ++i) {
				boolean cc;
				if ((cond & 3) == 0) {
					cc = ((cond & 4) == 0) ? (v1[i] == 0.0f) : (v1[i] != 0.0f);
				} else {
					cc = (((cond & 1) == 1) && Float.isNaN(v1[i])) || (((cond & 2) == 2) && Float.isInfinite(v1[i]));
					if ((cond & 4) == 4) {
						cc = !cc;
					}

				}
				vcr.cc[i] = cc;
				cc_or = cc_or || cc;
				cc_and = cc_and && cc;
			}

		}
		vcr.cc[4] = cc_or;
		vcr.cc[5] = cc_and;
	}

	// VFPU3:VMIN
	public void doVMIN(final int vsize, final int vd, final int vs, final int vt) {
		loadVs(vsize, vs);
		loadVt(vsize, vt);

		for (int i = 0; i < vsize; ++i) {
			v3[i] = Math.min(v1[i], v2[i]);
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU3:VMAX
	public void doVMAX(final int vsize, final int vd, final int vs, final int vt) {
		loadVs(vsize, vs);
		loadVt(vsize, vt);

		for (int i = 0; i < vsize; ++i) {
			v3[i] = Math.max(v1[i], v2[i]);
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU3:VSCMP
	public void doVSCMP(final int vsize, final int vd, final int vs, final int vt) {
		loadVs(vsize, vs);
		loadVt(vsize, vt);

		for (int i = 0; i < vsize; ++i) {
			v3[i] = Math.signum(v1[i] - v2[i]);
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU3:VSGE
	public void doVSGE(final int vsize, final int vd, final int vs, final int vt) {
		loadVs(vsize, vs);
		loadVt(vsize, vt);

		for (int i = 0; i < vsize; ++i) {
			v3[i] = (v1[i] >= v2[i]) ? 1.0f : 0.0f;
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU3:VSLT
	public void doVSLT(final int vsize, final int vd, final int vs, final int vt) {
		loadVs(vsize, vs);
		loadVt(vsize, vt);

		for (int i = 0; i < vsize; ++i) {
			v3[i] = (v1[i] < v2[i]) ? 1.0f : 0.0f;
		}

		saveVd(vsize, vd, v3);
	}

	// group VFPU4
	// VFPU4:VMOV
	public void doVMOV(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		saveVd(vsize, vd, v1);
	}

	// VFPU4:VABS
	public void doVABS(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = Math.abs(v1[i]);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VNEG
	public void doVNEG(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = 0.0f - v1[i];
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VIDT
	public void doVIDT(final int vsize, final int vd) {
		final int id = vd & 3;
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (id == i) ? 1.0f : 0.0f;
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VSAT0
	public void doVSAT0(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = Math.min(Math.max(0.0f, v1[i]), 1.0f);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VSAT1
	public void doVSAT1(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = Math.min(Math.max(-1.0f, v1[i]), 1.0f);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VZERO
	public void doVZERO(final int vsize, final int vd) {
		for (int i = 0; i < vsize; ++i) {
			v3[i] = 0.0f;
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VONE
	public void doVONE(final int vsize, final int vd) {
		for (int i = 0; i < vsize; ++i) {
			v3[i] = 1.0f;
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VRCP
	public void doVRCP(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = 1.0f / v1[i];
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VRSQ
	public void doVRSQ(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (float) (1.0 / Math.sqrt(v1[i]));
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VSIN
	public void doVSIN(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (float) Math.sin(0.5 * Math.PI * v1[i]);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VCOS
	public void doVCOS(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (float) Math.cos(0.5 * Math.PI * v1[i]);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VEXP2
	public void doVEXP2(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (float) Math.pow(2.0, v1[i]);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VLOG2
	public void doVLOG2(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (float) (Math.log(v1[i]) / Math.log(2.0));
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VSQRT
	public void doVSQRT(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (float) (Math.sqrt(v1[i]));
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VASIN
	public void doVASIN(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (float) (Math.asin(v1[i]) * 2.0 / Math.PI);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VNRCP
	public void doVNRCP(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = 0.0f - (1.0f / v1[i]);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VNSIN
	public void doVNSIN(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = 0.0f - (float) Math.sin(0.5 * Math.PI * v1[i]);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VREXP2
	public void doVREXP2(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (float) (1.0 / Math.pow(2.0, v1[i]));
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VRNDS
	public void doVRNDS(final int vsize, final int vs) {
		// temporary solution
		if (vsize != 1) {
			doUNK("Only supported VRNDS.S");
			return;
		}

		loadVs(1, vs);
		rnd.setSeed(Float.floatToRawIntBits(v1[0]));
	}

	// VFPU4:VRNDI
	public void doVRNDI(final int vsize, final int vd) {
		// temporary solution
		for (int i = 0; i < vsize; ++i) {
			v3[i] = Float.intBitsToFloat(rnd.nextInt());
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VRNDF1
	public void doVRNDF1(final int vsize, final int vd) {
		// temporary solution
		for (int i = 0; i < vsize; ++i) {
			v3[i] = 1.0f + rnd.nextFloat();
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VRNDF2
	public void doVRNDF2(final int vsize, final int vd) {
		// temporary solution
		for (int i = 0; i < vsize; ++i) {
			v3[i] = (1.0f + rnd.nextFloat()) * 2.0f;
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VF2H
	public void doVF2H(final int vsize, final int vd, final int vs) {
		if ((vsize & 1) == 1) {
			doUNK("Only supported VF2H.P or VF2H.Q");
			return;
		}
		loadVs(vsize, vs);
		for (int i = 0; i < vsize / 2; ++i) {
			v3[i] = (floatToHalffloat(v1[1 + i * 2]) << 16) | (floatToHalffloat(v1[0 + i * 2]) << 0);
		}
		saveVd(vsize / 2, vd, v3);
	}

	// VFPU4:VH2F
	public void doVH2F(final int vsize, final int vd, final int vs) {
		if (vsize > 2) {
			doUNK("Only supported VH2F.S or VH2F.P");
			return;
		}
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			final int imm32 = Float.floatToRawIntBits(v1[i]);
			v3[0 + 2 * i] = halffloatToFloat(imm32 & 65535);
			v3[1 + 2 * i] = halffloatToFloat(imm32 >>> 16);
		}
		saveVd(vsize * 2, vd, v3);
	}

	// VFPU4:VSBZ
	public void doVSBZ(final int vsize, final int vd, final int vs) {
		doUNK("Unimplemented VSBZ");
	}

	// VFPU4:VLGB
	public void doVLGB(final int vsize, final int vd, final int vs) {
		doUNK("Unimplemented VLGB");
	}

	// VFPU4:VUC2I
	public void doVUC2I(final int vsize, final int vd, final int vs) {
		if (vsize != 1) {
			doUNK("Only supported VUC2I.S");
			return;
		}
		loadVs(1, vs);
		final int n = Float.floatToRawIntBits(v1[0]);
		// Performs pseudo-full-scale conversion
		v3[0] = Float.intBitsToFloat((((n) & 0xFF) * 0x01010101) >>> 1);
		v3[1] = Float.intBitsToFloat((((n >> 8) & 0xFF) * 0x01010101) >>> 1);
		v3[2] = Float.intBitsToFloat((((n >> 16) & 0xFF) * 0x01010101) >>> 1);
		v3[3] = Float.intBitsToFloat((((n >> 24) & 0xFF) * 0x01010101) >>> 1);
		saveVd(4, vd, v3);
	}

	// VFPU4:VC2I
	public void doVC2I(final int vsize, final int vd, final int vs) {
		if (vsize != 1) {
			doUNK("Only supported VC2I.S");
			return;
		}
		loadVs(1, vs);
		final int n = Float.floatToRawIntBits(v1[0]);
		v3[0] = Float.intBitsToFloat(((n) & 0xFF) << 24);
		v3[1] = Float.intBitsToFloat(((n >> 8) & 0xFF) << 24);
		v3[2] = Float.intBitsToFloat(((n >> 16) & 0xFF) << 24);
		v3[3] = Float.intBitsToFloat(((n >> 24) & 0xFF) << 24);
		saveVd(4, vd, v3);
	}

	// VFPU4:VUS2I
	public void doVUS2I(final int vsize, final int vd, final int vs) {
		if (vsize > 2) {
			doUNK("Only supported VUS2I.S or VUS2I.P");
			return;
		}
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			final int imm32 = Float.floatToRawIntBits(v1[i]);
			v3[0 + 2 * i] = Float.intBitsToFloat(((imm32) & 0xFFFF) << 15);
			v3[1 + 2 * i] = Float.intBitsToFloat(((imm32 >>> 16) & 0xFFFF) << 15);
		}
		saveVd(vsize * 2, vd, v3);
	}

	// VFPU4:VS2I
	public void doVS2I(final int vsize, final int vd, final int vs) {
		if (vsize > 2) {
			doUNK("Only supported VS2I.S or VS2I.P");
			return;
		}
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			final int imm32 = Float.floatToRawIntBits(v1[i]);
			v3[0 + 2 * i] = Float.intBitsToFloat(((imm32) & 0xFFFF) << 16);
			v3[1 + 2 * i] = Float.intBitsToFloat(((imm32 >>> 16) & 0xFFFF) << 16);
		}
		saveVd(vsize * 2, vd, v3);
	}

	// VFPU4:VI2UC
	public void doVI2UC(final int vsize, final int vd, final int vs) {
		if (vsize != 4) {
			doUNK("Only supported VI2UC.Q");
			return;
		}

		loadVs(4, vs);

		final int x = Float.floatToRawIntBits(v1[0]);
		final int y = Float.floatToRawIntBits(v1[1]);
		final int z = Float.floatToRawIntBits(v1[2]);
		final int w = Float.floatToRawIntBits(v1[3]);

		v3[0] = Float.intBitsToFloat(((x < 0) ? 0 : ((x >> 23) << 0)) | ((y < 0) ? 0 : ((y >> 23) << 8)) | ((z < 0) ? 0 : ((z >> 23) << 16)) | ((w < 0) ? 0 : ((w >> 23) << 24)));

		saveVd(1, vd, v3);
	}

	// VFPU4:VI2C
	public void doVI2C(final int vsize, final int vd, final int vs) {
		if (vsize != 4) {
			doUNK("Only supported VI2C.Q");
			return;
		}

		loadVs(4, vs);

		final int x = Float.floatToRawIntBits(v1[0]);
		final int y = Float.floatToRawIntBits(v1[1]);
		final int z = Float.floatToRawIntBits(v1[2]);
		final int w = Float.floatToRawIntBits(v1[3]);

		v3[0] = Float.intBitsToFloat(((x >>> 24) << 0) | ((y >>> 24) << 8) | ((z >>> 24) << 16) | ((w >>> 24) << 24));

		saveVd(1, vd, v3);
	}

	// VFPU4:VI2US
	public void doVI2US(final int vsize, final int vd, final int vs) {
		if ((vsize & 1) != 0) {
			doUNK("Only supported VI2US.P and VI2US.Q");
			return;
		}

		loadVs(vsize, vs);

		final int x = Float.floatToRawIntBits(v1[0]);
		final int y = Float.floatToRawIntBits(v1[1]);

		v3[0] = Float.intBitsToFloat(((x < 0) ? 0 : ((x >> 15) << 0)) | ((y < 0) ? 0 : ((y >> 15) << 16)));

		if (vsize == 4) {
			final int z = Float.floatToRawIntBits(v1[2]);
			final int w = Float.floatToRawIntBits(v1[3]);

			v3[1] = Float.intBitsToFloat(((z < 0) ? 0 : ((z >> 15) << 0)) | ((w < 0) ? 0 : ((w >> 15) << 16)));
			saveVd(2, vd, v3);
		} else {
			saveVd(1, vd, v3);
		}
	}

	// VFPU4:VI2S
	public void doVI2S(final int vsize, final int vd, final int vs) {
		if ((vsize & 1) != 0) {
			doUNK("Only supported VI2S.P and VI2S.Q");
			return;
		}

		loadVs(vsize, vs);

		final int x = Float.floatToRawIntBits(v1[0]);
		final int y = Float.floatToRawIntBits(v1[1]);

		v3[0] = Float.intBitsToFloat(((x >>> 16) << 0) | ((y >>> 16) << 16));

		if (vsize == 4) {
			final int z = Float.floatToRawIntBits(v1[2]);
			final int w = Float.floatToRawIntBits(v1[3]);

			v3[1] = Float.intBitsToFloat(((z >>> 16) << 0) | ((w >>> 16) << 16));
			saveVd(2, vd, v3);
		} else {
			saveVd(1, vd, v3);
		}
	}

	// VFPU4:VSRT1
	public void doVSRT1(final int vsize, final int vd, final int vs) {
		if (vsize != 4) {
			doUNK("Only supported VSRT1.Q");
			return;
		}

		loadVs(4, vs);
		final float x = v1[0];
		final float y = v1[1];
		final float z = v1[2];
		final float w = v1[3];
		v3[0] = Math.min(x, y);
		v3[1] = Math.max(x, y);
		v3[2] = Math.min(z, w);
		v3[3] = Math.max(z, w);
		saveVd(4, vd, v3);
	}

	// VFPU4:VSRT2
	public void doVSRT2(final int vsize, final int vd, final int vs) {
		if (vsize != 4) {
			doUNK("Only supported VSRT2.Q");
			return;
		}

		loadVs(4, vs);
		final float x = v1[0];
		final float y = v1[1];
		final float z = v1[2];
		final float w = v1[3];
		v3[0] = Math.min(x, w);
		v3[1] = Math.min(y, z);
		v3[2] = Math.max(y, z);
		v3[3] = Math.max(x, w);
		saveVd(4, vd, v3);
	}

	// VFPU4:VBFY1
	public void doVBFY1(final int vsize, final int vd, final int vs) {
		if ((vsize & 1) == 1) {
			doUNK("Only supported VBFY1.P or VBFY1.Q");
			return;
		}

		loadVs(vsize, vs);
		final float x = v1[0];
		final float y = v1[1];
		v3[0] = x + y;
		v3[1] = x - y;
		if (vsize > 2) {
			final float z = v1[2];
			final float w = v1[3];
			v3[2] = z + w;
			v3[3] = z - w;
			saveVd(4, vd, v3);
		} else {
			saveVd(2, vd, v3);
		}
	}

	// VFPU4:VBFY2
	public void doVBFY2(final int vsize, final int vd, final int vs) {
		if (vsize != 4) {
			doUNK("Only supported VBFY2.Q");
			return;
		}

		loadVs(vsize, vs);
		final float x = v1[0];
		final float y = v1[1];
		final float z = v1[2];
		final float w = v1[3];
		v3[0] = x + z;
		v3[1] = y + w;
		v3[2] = x - z;
		v3[3] = y - w;
		saveVd(4, vd, v3);
	}

	// VFPU4:VOCP
	public void doVOCP(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);

		for (int i = 0; i < vsize; ++i) {
			v1[i] = 1.0f - v1[i];
		}

		saveVd(vsize, vd, v1);
	}

	// VFPU4:VSOCP
	public void doVSOCP(final int vsize, final int vd, final int vs) {
		if (vsize > 2) {
			doUNK("Only supported VSOCP.S or VSOCP.P");
			return;
		}

		loadVs(vsize, vs);
		final float x = v1[0];
		v3[0] = Math.min(Math.max(0.0f, 1.0f - x), 1.0f);
		v3[1] = Math.min(Math.max(0.0f, 1.0f + x), 1.0f);
		if (vsize > 1) {
			final float y = v1[1];
			v3[2] = Math.min(Math.max(0.0f, 1.0f - y), 1.0f);
			v3[3] = Math.min(Math.max(0.0f, 1.0f + y), 1.0f);
			saveVd(4, vd, v3);
		} else {
			saveVd(2, vd, v3);
		}
	}

	// VFPU4:VFAD
	public void doVFAD(final int vsize, final int vd, final int vs) {
		if (vsize == 1) {
			doUNK("Unsupported VFAD.S");
			return;
		}

		loadVs(vsize, vs);

		for (int i = 1; i < vsize; ++i) {
			v1[0] += v1[i];
		}

		saveVd(1, vd, v1);
	}

	// VFPU4:VAVG
	public void doVAVG(final int vsize, final int vd, final int vs) {
		if (vsize == 1) {
			doUNK("Unsupported VAVG.S");
			return;
		}

		loadVs(vsize, vs);

		for (int i = 1; i < vsize; ++i) {
			v1[0] += v1[i];
		}

		v1[0] /= vsize;

		saveVd(1, vd, v1);
	}

	// VFPU4:VSRT3
	public void doVSRT3(final int vsize, final int vd, final int vs) {
		if (vsize != 4) {
			doUNK("Only supported VSRT3.Q (vsize=" + vsize + ")");
			// The instruction is somehow supported on the PSP (see VfpuTest),
			// but leave the error message here to help debugging the Decoder.
			return;
		}

		loadVs(4, vs);
		final float x = v1[0];
		final float y = v1[1];
		final float z = v1[2];
		final float w = v1[3];
		v3[0] = Math.max(x, y);
		v3[1] = Math.min(x, y);
		v3[2] = Math.max(z, w);
		v3[3] = Math.min(z, w);
		saveVd(4, vd, v3);
	}

	// VFPU4:VSGN
	public void doVSGN(final int vsize, final int vd, final int vs) {
		loadVs(vsize, vs);
		for (int i = 0; i < vsize; ++i) {
			v3[i] = Math.signum(v1[i]);
		}
		saveVd(vsize, vd, v3);
	}

	// VFPU4:VSRT4
	public void doVSRT4(final int vsize, final int vd, final int vs) {
		if (vsize != 4) {
			doUNK("Only supported VSRT4.Q");
			return;
		}

		loadVs(4, vs);
		final float x = v1[0];
		final float y = v1[1];
		final float z = v1[2];
		final float w = v1[3];
		v3[0] = Math.max(x, w);
		v3[1] = Math.max(y, z);
		v3[2] = Math.min(y, z);
		v3[3] = Math.min(x, w);
		saveVd(4, vd, v3);
	}

	// VFPU4:VMFVC
	public void doVMFVC(final int vd, final int imm7) {
		doUNK("Unimplemented VMFVC");
	}

	// VFPU4:VMTVC
	public void doVMTVC(final int vd, final int imm7) {
		doUNK("Unimplemented VMTVC");
	}

	// VFPU4:VT4444
	public void doVT4444(final int vsize, final int vd, final int vs) {
		loadVs(4, vs);
		final int i0 = Float.floatToRawIntBits(v1[0]);
		final int i1 = Float.floatToRawIntBits(v1[1]);
		final int i2 = Float.floatToRawIntBits(v1[2]);
		final int i3 = Float.floatToRawIntBits(v1[3]);
		int o0 = 0, o1 = 0;
		o0 |= ((i0 >> 4) & 15) << 0;
		o0 |= ((i0 >> 12) & 15) << 4;
		o0 |= ((i0 >> 20) & 15) << 8;
		o0 |= ((i0 >> 28) & 15) << 12;
		o0 |= ((i1 >> 4) & 15) << 16;
		o0 |= ((i1 >> 12) & 15) << 20;
		o0 |= ((i1 >> 20) & 15) << 24;
		o0 |= ((i1 >> 28) & 15) << 28;
		o1 |= ((i2 >> 4) & 15) << 0;
		o1 |= ((i2 >> 12) & 15) << 4;
		o1 |= ((i2 >> 20) & 15) << 8;
		o1 |= ((i2 >> 28) & 15) << 12;
		o1 |= ((i3 >> 4) & 15) << 16;
		o1 |= ((i3 >> 12) & 15) << 20;
		o1 |= ((i3 >> 20) & 15) << 24;
		o1 |= ((i3 >> 28) & 15) << 28;
		v3[0] = Float.intBitsToFloat(o0);
		v3[1] = Float.intBitsToFloat(o1);
		saveVd(2, vd, v3);
	}

	// VFPU4:VT5551
	public void doVT5551(final int vsize, final int vd, final int vs) {
		loadVs(4, vs);
		final int i0 = Float.floatToRawIntBits(v1[0]);
		final int i1 = Float.floatToRawIntBits(v1[1]);
		final int i2 = Float.floatToRawIntBits(v1[2]);
		final int i3 = Float.floatToRawIntBits(v1[3]);
		int o0 = 0, o1 = 0;
		o0 |= ((i0 >> 3) & 31) << 0;
		o0 |= ((i0 >> 11) & 31) << 5;
		o0 |= ((i0 >> 19) & 31) << 10;
		o0 |= ((i0 >> 31) & 1) << 15;
		o0 |= ((i1 >> 3) & 31) << 16;
		o0 |= ((i1 >> 11) & 31) << 21;
		o0 |= ((i1 >> 19) & 31) << 26;
		o0 |= ((i1 >> 31) & 1) << 31;
		o1 |= ((i2 >> 3) & 31) << 0;
		o1 |= ((i2 >> 11) & 31) << 5;
		o1 |= ((i2 >> 19) & 31) << 10;
		o1 |= ((i2 >> 31) & 1) << 15;
		o1 |= ((i3 >> 3) & 31) << 16;
		o1 |= ((i3 >> 11) & 31) << 21;
		o1 |= ((i3 >> 19) & 31) << 26;
		o1 |= ((i3 >> 31) & 1) << 31;
		v3[0] = Float.intBitsToFloat(o0);
		v3[1] = Float.intBitsToFloat(o1);
		saveVd(2, vd, v3);
	}

	// VFPU4:VT5650
	public void doVT5650(final int vsize, final int vd, final int vs) {
		loadVs(4, vs);
		final int i0 = Float.floatToRawIntBits(v1[0]);
		final int i1 = Float.floatToRawIntBits(v1[1]);
		final int i2 = Float.floatToRawIntBits(v1[2]);
		final int i3 = Float.floatToRawIntBits(v1[3]);
		int o0 = 0, o1 = 0;
		o0 |= ((i0 >> 3) & 31) << 0;
		o0 |= ((i0 >> 10) & 63) << 5;
		o0 |= ((i0 >> 19) & 31) << 11;
		o0 |= ((i1 >> 3) & 31) << 16;
		o0 |= ((i1 >> 10) & 63) << 21;
		o0 |= ((i1 >> 19) & 31) << 27;
		o1 |= ((i2 >> 3) & 31) << 0;
		o1 |= ((i2 >> 10) & 63) << 5;
		o1 |= ((i2 >> 19) & 31) << 11;
		o1 |= ((i3 >> 3) & 31) << 16;
		o1 |= ((i3 >> 10) & 63) << 21;
		o1 |= ((i3 >> 19) & 31) << 27;
		v3[0] = Float.intBitsToFloat(o0);
		v3[1] = Float.intBitsToFloat(o1);
		saveVd(2, vd, v3);
	}

	// VFPU4:VCST
	public void doVCST(final int vsize, final int vd, final int imm5) {
		float constant = 0.0f;

		if (imm5 >= 0 && imm5 < floatConstants.length) {
			constant = floatConstants[imm5];
		}

		for (int i = 0; i < vsize; ++i) {
			v3[i] = constant;
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU4:VF2IN
	public void doVF2IN(final int vsize, final int vd, final int vs, final int imm5) {
		loadVs(vsize, vs);

		for (int i = 0; i < vsize; ++i) {
			final float value = Math.scalb(v1[i], imm5);
			v3[i] = Float.intBitsToFloat(Math.round(value));
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU4:VF2IZ
	public void doVF2IZ(final int vsize, final int vd, final int vs, final int imm5) {
		loadVs(vsize, vs);

		for (int i = 0; i < vsize; ++i) {
			final float value = Math.scalb(v1[i], imm5);
			v3[i] = Float.intBitsToFloat(v1[i] >= 0 ? (int) Math.floor(value) : (int) Math.ceil(value));
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU4:VF2IU
	public void doVF2IU(final int vsize, final int vd, final int vs, final int imm5) {
		loadVs(vsize, vs);

		for (int i = 0; i < vsize; ++i) {
			final float value = Math.scalb(v1[i], imm5);
			v3[i] = Float.intBitsToFloat((int) Math.ceil(value));
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU4:VF2ID
	public void doVF2ID(final int vsize, final int vd, final int vs, final int imm5) {
		loadVs(vsize, vs);

		for (int i = 0; i < vsize; ++i) {
			final float value = Math.scalb(v1[i], imm5);
			v3[i] = Float.intBitsToFloat((int) Math.floor(value));
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU4:VI2F
	public void doVI2F(final int vsize, final int vd, final int vs, final int imm5) {
		loadVs(vsize, vs);

		for (int i = 0; i < vsize; ++i) {
			final float value = Float.floatToRawIntBits(v1[i]);
			v3[i] = Math.scalb(value, -imm5);
		}

		saveVd(vsize, vd, v3);
	}

	// VFPU4:VCMOVT
	public void doVCMOVT(final int vsize, final int imm3, final int vd, final int vs) {
		if (imm3 < 6) {
			if (vcr.cc[imm3]) {
				loadVs(vsize, vs);
				saveVd(vsize, vd, v1);
			}
		} else if (imm3 == 6) {
			loadVs(vsize, vs);
			loadVt(vsize, vd);
			for (int i = 0; i < vsize; ++i) {
				if (vcr.cc[i]) {
					v2[i] = v1[i];
				}
			}
			saveVd(vsize, vd, v2);
		} else {
			// Never copy (checked on a PSP)
		}
	}

	// VFPU4:VCMOVF
	public void doVCMOVF(final int vsize, final int imm3, final int vd, final int vs) {
		if (imm3 < 6) {
			if (!vcr.cc[imm3]) {
				loadVs(vsize, vs);
				saveVd(vsize, vd, v1);
			}
		} else if (imm3 == 6) {
			loadVs(vsize, vs);
			loadVt(vsize, vd);
			for (int i = 0; i < vsize; ++i) {
				if (!vcr.cc[i]) {
					v2[i] = v1[i];
				}
			}
			saveVd(vsize, vd, v2);
		} else {
			// Always copy (checked on a PSP)
			loadVs(vsize, vs);
			saveVd(vsize, vd, v1);
		}
	}

	// VFPU4:VWBN
	public void doVWBN(final int vsize, final int vd, final int vs, final int imm8) {
		doUNK("Unimplemented VWBN");
	}

	// group VFPU5
	// VFPU5:VPFXS
	public void doVPFXS(final int negw, final int negz, final int negy, final int negx, final int cstw, final int cstz, final int csty, final int cstx, final int absw, final int absz, final int absy,
	        final int absx, final int swzw, final int swzz, final int swzy, final int swzx) {
		vcr.pfxs.swz[0] = swzx;
		vcr.pfxs.swz[1] = swzy;
		vcr.pfxs.swz[2] = swzz;
		vcr.pfxs.swz[3] = swzw;
		vcr.pfxs.abs[0] = absx != 0;
		vcr.pfxs.abs[1] = absy != 0;
		vcr.pfxs.abs[2] = absz != 0;
		vcr.pfxs.abs[3] = absw != 0;
		vcr.pfxs.cst[0] = cstx != 0;
		vcr.pfxs.cst[1] = csty != 0;
		vcr.pfxs.cst[2] = cstz != 0;
		vcr.pfxs.cst[3] = cstw != 0;
		vcr.pfxs.neg[0] = negx != 0;
		vcr.pfxs.neg[1] = negy != 0;
		vcr.pfxs.neg[2] = negz != 0;
		vcr.pfxs.neg[3] = negw != 0;
		vcr.pfxs.enabled = true;
	}

	// VFPU5:VPFXT
	public void doVPFXT(final int negw, final int negz, final int negy, final int negx, final int cstw, final int cstz, final int csty, final int cstx, final int absw, final int absz, final int absy,
	        final int absx, final int swzw, final int swzz, final int swzy, final int swzx) {
		vcr.pfxt.swz[0] = swzx;
		vcr.pfxt.swz[1] = swzy;
		vcr.pfxt.swz[2] = swzz;
		vcr.pfxt.swz[3] = swzw;
		vcr.pfxt.abs[0] = absx != 0;
		vcr.pfxt.abs[1] = absy != 0;
		vcr.pfxt.abs[2] = absz != 0;
		vcr.pfxt.abs[3] = absw != 0;
		vcr.pfxt.cst[0] = cstx != 0;
		vcr.pfxt.cst[1] = csty != 0;
		vcr.pfxt.cst[2] = cstz != 0;
		vcr.pfxt.cst[3] = cstw != 0;
		vcr.pfxt.neg[0] = negx != 0;
		vcr.pfxt.neg[1] = negy != 0;
		vcr.pfxt.neg[2] = negz != 0;
		vcr.pfxt.neg[3] = negw != 0;
		vcr.pfxt.enabled = true;
	}

	// VFPU5:VPFXD
	public void doVPFXD(final int mskw, final int mskz, final int msky, final int mskx, final int satw, final int satz, final int saty, final int satx) {
		vcr.pfxd.sat[0] = satx;
		vcr.pfxd.sat[1] = saty;
		vcr.pfxd.sat[2] = satz;
		vcr.pfxd.sat[3] = satw;
		vcr.pfxd.msk[0] = mskx != 0;
		vcr.pfxd.msk[1] = msky != 0;
		vcr.pfxd.msk[2] = mskz != 0;
		vcr.pfxd.msk[3] = mskw != 0;
		vcr.pfxd.enabled = true;
	}

	// VFPU5:VIIM
	public void doVIIM(final int vd, final int imm16) {
		v3[0] = imm16;

		saveVd(1, vd, v3);
	}

	// VFPU5:VFIM
	public void doVFIM(final int vd, final int imm16) {
		v3[0] = halffloatToFloat(imm16);

		saveVd(1, vd, v3);
	}

	// group VFPU6
	// VFPU6:VMMUL
	public void doVMMUL(final int vsize, final int vd, final int vs, final int vt) {
		if (vsize == 1) {
			doUNK("Not supported VMMUL.S");
			return;
		}

		// you must do it for disasm, not for emulation !
		// vs = vs ^ 32;

		for (int i = 0; i < vsize; ++i) {
			loadVt(vsize, vt + i);
			for (int j = 0; j < vsize; ++j) {
				loadVs(vsize, vs + j);
				float dot = v1[0] * v2[0];
				for (int k = 1; k < vsize; ++k) {
					dot += v1[k] * v2[k];
				}
				v3[j] = dot;
			}
			saveVd(vsize, vd + i, v3);
		}
	}

	// VFPU6:VHTFM2
	public void doVHTFM2(final int vd, final int vs, final int vt) {
		loadVt(1, vt);
		loadVs(2, vs + 0);
		v3[0] = v1[0] * v2[0] + v1[1];
		loadVs(2, vs + 1);
		v3[1] = v1[0] * v2[0] + v1[1];
		saveVd(2, vd, v3);
	}

	// VFPU6:VTFM2
	public void doVTFM2(final int vd, final int vs, final int vt) {
		loadVt(2, vt);
		loadVs(2, vs + 0);
		v3[0] = v1[0] * v2[0] + v1[1] * v2[1];
		loadVs(2, vs + 1);
		v3[1] = v1[0] * v2[0] + v1[1] * v2[1];
		saveVd(2, vd, v3);
	}

	// VFPU6:VHTFM3
	public void doVHTFM3(final int vd, final int vs, final int vt) {
		loadVt(2, vt);
		loadVs(3, vs + 0);
		v3[0] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2];
		loadVs(3, vs + 1);
		v3[1] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2];
		loadVs(3, vs + 2);
		v3[2] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2];
		saveVd(3, vd, v3);
	}

	// VFPU6:VTFM3
	public void doVTFM3(final int vd, final int vs, final int vt) {
		loadVt(3, vt);
		loadVs(3, vs + 0);
		v3[0] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
		loadVs(3, vs + 1);
		v3[1] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
		loadVs(3, vs + 2);
		v3[2] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
		saveVd(3, vd, v3);
	}

	// VFPU6:VHTFM4
	public void doVHTFM4(final int vd, final int vs, final int vt) {
		loadVt(3, vt);
		loadVs(4, vs + 0);
		v3[0] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2] + v1[3];
		loadVs(4, vs + 1);
		v3[1] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2] + v1[3];
		loadVs(4, vs + 2);
		v3[2] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2] + v1[3];
		loadVs(4, vs + 3);
		v3[3] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2] + v1[3];
		saveVd(4, vd, v3);
	}

	// VFPU6:VTFM4
	public void doVTFM4(final int vd, final int vs, final int vt) {
		loadVt(4, vt);
		loadVs(4, vs + 0);
		v3[0] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2] + v1[3] * v2[3];
		loadVs(4, vs + 1);
		v3[1] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2] + v1[3] * v2[3];
		loadVs(4, vs + 2);
		v3[2] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2] + v1[3] * v2[3];
		loadVs(4, vs + 3);
		v3[3] = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2] + v1[3] * v2[3];
		saveVd(4, vd, v3);
	}

	// VFPU6:VMSCL
	public void doVMSCL(final int vsize, final int vd, final int vs, final int vt) {
		for (int i = 0; i < vsize; ++i) {
			this.doVSCL(vsize, vd + i, vs + i, vt);
		}
	}

	// VFPU6:VCRSP
	public void doVCRSP(final int vd, final int vs, final int vt) {
		loadVs(3, vs);
		loadVt(3, vt);

		v3[0] = +v1[1] * v2[2] - v1[2] * v2[1];
		v3[1] = +v1[2] * v2[0] - v1[0] * v2[2];
		v3[2] = +v1[0] * v2[1] - v1[1] * v2[0];

		saveVd(3, vd, v3);
	}

	// VFPU6:VQMUL
	public void doVQMUL(final int vd, final int vs, final int vt) {
		loadVs(4, vs);
		loadVt(4, vt);

		v3[0] = +v1[0] * v2[3] + v1[1] * v2[2] - v1[2] * v2[1] + v1[3] * v2[0];
		v3[1] = -v1[0] * v2[2] + v1[1] * v2[3] + v1[2] * v2[0] + v1[3] * v2[1];
		v3[2] = +v1[0] * v2[1] - v1[1] * v2[0] + v1[2] * v2[3] + v1[3] * v2[2];
		v3[3] = -v1[0] * v2[0] - v1[1] * v2[1] - v1[2] * v2[2] + v1[3] * v2[3];

		saveVd(4, vd, v3);
	}

	// VFPU6:VMMOV
	public void doVMMOV(final int vsize, final int vd, final int vs) {
		for (int i = 0; i < vsize; ++i) {
			this.doVMOV(vsize, vd + i, vs + i);
		}
	}

	// VFPU6:VMIDT
	public void doVMIDT(final int vsize, final int vd) {
		for (int i = 0; i < vsize; ++i) {
			this.doVIDT(vsize, vd + i);
		}
	}

	// VFPU6:VMZERO
	public void doVMZERO(final int vsize, final int vd) {
		for (int i = 0; i < vsize; ++i) {
			this.doVZERO(vsize, vd + i);
		}
	}

	// VFPU7:VMONE
	public void doVMONE(final int vsize, final int vd) {
		for (int i = 0; i < vsize; ++i) {
			this.doVONE(vsize, vd + i);
		}
	}

	// VFPU6:VROT
	public void doVROT(final int vsize, final int vd, final int vs, final int imm5) {
		loadVs(1, vs);

		final double a = 0.5 * Math.PI * v1[0];
		final double ca = Math.cos(a);
		double sa = Math.sin(a);

		int i;
		final int si = (imm5 >>> 2) & 3;
		final int ci = (imm5 >>> 0) & 3;

		if (((imm5 & 16) != 0)) {
			sa = 0.0 - sa;
		}

		if (si == ci) {
			for (i = 0; i < vsize; ++i) {
				v3[i] = (float) sa;
			}
		} else {
			for (i = 0; i < vsize; ++i) {
				v3[i] = (float) 0.0;
			}
			v3[si] = (float) sa;
		}
		v3[ci] = (float) ca;

		saveVd(vsize, vd, v3);
	}

	// group VLSU
	// LSU:LVS
	public void doLVS(final int vt, final int rs, final int simm14_a16) {
		final int s = (vt >> 5) & 3;
		final int m = (vt >> 2) & 7;
		final int i = (vt >> 0) & 3;

		vpr[m][i][s] = Float.intBitsToFloat(processor.memory.read32(gpr[rs].read32() + simm14_a16));
	}

	// LSU:SVS
	public void doSVS(final int vt, final int rs, final int simm14_a16) {
		final int s = (vt >> 5) & 3;
		final int m = (vt >> 2) & 7;
		final int i = (vt >> 0) & 3;

		if (CHECK_ALIGNMENT) {
			final int address = gpr[rs].read32() + simm14_a16;
			if ((address & 3) != 0) {
				throw new RuntimeException(String.format("SV.S unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		processor.memory.write32(gpr[rs].read32() + simm14_a16, Float.floatToRawIntBits(vpr[m][i][s]));
	}

	// LSU:LVQ
	public void doLVQ(final int vt, final int rs, final int simm14_a16) {
		final int m = (vt >> 2) & 7;
		final int i = (vt >> 0) & 3;

		final int address = gpr[rs].read32() + simm14_a16;

		if (CHECK_ALIGNMENT) {
			if ((address & 15) != 0) {
				throw new RuntimeException(String.format("LV.Q unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		if ((vt & 32) != 0) {
			for (int j = 0; j < 4; ++j) {
				vpr[m][j][i] = Float.intBitsToFloat(processor.memory.read32(address + j * 4));
			}
		} else {
			for (int j = 0; j < 4; ++j) {
				vpr[m][i][j] = Float.intBitsToFloat(processor.memory.read32(address + j * 4));
			}
		}
	}

	// LSU:LVLQ
	public void doLVLQ(final int vt, final int rs, final int simm14_a16) {
		final int m = (vt >> 2) & 7;
		final int i = (vt >> 0) & 3;

		int address = gpr[rs].read32() + simm14_a16 - 12;
		// Memory.log.error("Forbidden LVL.Q");

		if (CHECK_ALIGNMENT) {
			if ((address & 3) != 0) {
				throw new RuntimeException(String.format("LVL.Q unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		/*
		 * I assume it should be something like that : Mem = 4321 Reg = wzyx
		 * 
		 * 0 1 z y x 1 2 1 y x 2 3 2 1 x 3 4 3 2 1
		 */

		final int k = 4 - ((address >> 2) & 3);

		if ((vt & 32) != 0) {
			for (int j = 0; j < k; ++j) {
				vpr[m][j][i] = Float.intBitsToFloat(processor.memory.read32(address));
				address += 4;
			}
		} else {
			for (int j = 0; j < k; ++j) {
				vpr[m][i][j] = Float.intBitsToFloat(processor.memory.read32(address));
				address += 4;
			}
		}
	}

	// LSU:LVRQ
	public void doLVRQ(final int vt, final int rs, final int simm14_a16) {
		final int m = (vt >> 2) & 7;
		final int i = (vt >> 0) & 3;

		int address = gpr[rs].read32() + simm14_a16;
		// Memory.log.error("Forbidden LVR.Q");

		if (CHECK_ALIGNMENT) {
			if ((address & 3) != 0) {
				throw new RuntimeException(String.format("LVR.Q unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		/*
		 * I assume it should be something like that : Mem = 4321 Reg = wzyx
		 * 
		 * 0 4 3 2 1 1 w 4 3 2 2 w z 4 3 3 w z y 4
		 */

		final int k = (address >> 2) & 3;
		address += (4 - k) << 2;
		if ((vt & 32) != 0) {
			for (int j = 4 - k; j < 4; ++j) {
				vpr[m][j][i] = Float.intBitsToFloat(processor.memory.read32(address));
				address += 4;
			}
		} else {
			for (int j = 4 - k; j < 4; ++j) {
				vpr[m][i][j] = Float.intBitsToFloat(processor.memory.read32(address));
				address += 4;
			}
		}
	}

	// LSU:SVQ
	public void doSVQ(final int vt, final int rs, final int simm14_a16) {
		final int m = (vt >> 2) & 7;
		final int i = (vt >> 0) & 3;

		final int address = gpr[rs].read32() + simm14_a16;

		if (CHECK_ALIGNMENT) {
			if ((address & 15) != 0) {
				throw new RuntimeException(String.format("SV.Q unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		if ((vt & 32) != 0) {
			for (int j = 0; j < 4; ++j) {
				processor.memory.write32((address + j * 4), Float.floatToRawIntBits(vpr[m][j][i]));
			}
		} else {
			for (int j = 0; j < 4; ++j) {
				processor.memory.write32((address + j * 4), Float.floatToRawIntBits(vpr[m][i][j]));
			}
		}
	}

	// LSU:SVLQ
	public void doSVLQ(final int vt, final int rs, final int simm14_a16) {
		final int m = (vt >> 2) & 7;
		final int i = (vt >> 0) & 3;

		int address = gpr[rs].read32() + simm14_a16 - 12;

		if (CHECK_ALIGNMENT) {
			if ((address & 3) != 0) {
				throw new RuntimeException(String.format("SVL.Q unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		final int k = 4 - ((address >> 2) & 3);

		if ((vt & 32) != 0) {
			for (int j = 0; j < k; ++j) {
				processor.memory.write32((address), Float.floatToRawIntBits(vpr[m][j][i]));
				address += 4;
			}
		} else {
			for (int j = 0; j < k; ++j) {
				processor.memory.write32((address), Float.floatToRawIntBits(vpr[m][i][j]));
				address += 4;
			}
		}
	}

	// LSU:SVRQ
	public void doSVRQ(final int vt, final int rs, final int simm14_a16) {
		final int m = (vt >> 2) & 7;
		final int i = (vt >> 0) & 3;

		int address = gpr[rs].read32() + simm14_a16;

		if (CHECK_ALIGNMENT) {
			if ((address & 3) != 0) {
				throw new RuntimeException(String.format("SVR.Q unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		final int k = (address >> 2) & 3;
		address += (4 - k) << 2;
		if ((vt & 32) != 0) {
			for (int j = 4 - k; j < 4; ++j) {
				processor.memory.write32((address), Float.floatToRawIntBits(vpr[m][j][i]));
				address += 4;
			}
		} else {
			for (int j = 4 - k; j < 4; ++j) {
				processor.memory.write32((address), Float.floatToRawIntBits(vpr[m][i][j]));
				address += 4;
			}
		}
	}
}
