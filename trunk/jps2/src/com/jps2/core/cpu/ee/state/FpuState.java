/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jps2.core.cpu.ee.state;

import java.util.Arrays;

/**
 * Floating Point Unit, handles floating point operations, including BCU and LSU
 * 
 * @author hli
 */
public abstract class FpuState extends BcuState {

	public static final class Fcr0 {

		public static final int	imp	= 0;	/* FPU design number */

		public static final int	rev	= 0;	/* FPU revision bumber */

	}

	public class Fcr31 {

		public int		rm;
		public boolean	c;
		public boolean	fs;

		public void reset() {
			rm = 0;
			c = false;
			fs = false;
		}

		public Fcr31() {
			reset();
		}

		public Fcr31(final Fcr31 that) {
			rm = that.rm;
			c = that.c;
			fs = that.fs;
		}
	}

	public double[]	fpr;
	public Fcr31	fcr31;

	@Override
	public void reset() {
		Arrays.fill(fpr, 0.0f);
		fpr[0] = 0x00002e00; // fpu Revision..
		fpr[31] = 0x01000001; // fpu Status/Control
		fcr31.reset();
	}

	@Override
	public void resetAll() {
		super.resetAll();
		Arrays.fill(fpr, 0.0f);
		fpr[0] = 0x00002e00; // fpu Revision..
		fpr[31] = 0x01000001; // fpu Status/Control
		fcr31.reset();
	}

	public FpuState() {
		fpr = new double[32];
		fcr31 = new Fcr31();
	}

	public void copy(final FpuState that) {
		super.copy(that);
		fpr = that.fpr.clone();
		fcr31 = new Fcr31(that.fcr31);
	}

	public FpuState(final FpuState that) {
		super(that);
		fpr = that.fpr.clone();
		fcr31 = new Fcr31(that.fcr31);
	}

	public void doMFC1(final int rt, final int c1dr) {
		gpr[rt].write32((int) (Double.doubleToRawLongBits(fpr[c1dr]) & 0xFFFFFFFF));
	}

	public void doDMFC1(final int rt, final int c1dr) {
		gpr[rt].write64(Double.doubleToRawLongBits(fpr[c1dr]));
	}

	public void doCFC1(final int rt, final int c1cr) {
		if (rt != 0) {
			switch (c1cr) {
				case 0:
					gpr[rt].write32((Fcr0.imp << 8) | (Fcr0.rev));
					break;

				case 31:
					gpr[rt].write32((fcr31.fs ? (1 << 24) : 0) | (fcr31.c ? (1 << 23) : 0) | (fcr31.rm & 3));
					break;

				default:
					doUNK("Unsupported cfc1 instruction for fcr" + Integer.toString(c1cr));
			}
		}
	}

	public void doMTC1(final int rt, final int c1dr) {
		fpr[c1dr] = Double.longBitsToDouble(gpr[rt].read32());
	}

	public void doDMTC1(final int rt, final int c1dr) {
		fpr[c1dr] = Double.longBitsToDouble(gpr[rt].read64());
	}

	public void doCTC1(final int rt, final int c1cr) {
		switch (c1cr) {
			case 31:
				int bits = (gpr[rt].read32() & 0x01800003);
				fcr31.rm = bits & 3;
				bits >>= 23;
				fcr31.fs = (bits > 1);
				fcr31.c = (bits >> 1) == 1;
				break;

			default:
				doUNK("Unsupported ctc1 instruction for fcr" + Integer.toString(c1cr));
		}
	}

	public boolean doBC1F(final int simm16) {
		npc = !fcr31.c ? branchTarget(pc, simm16) : (pc + 4);
		return true;
	}

	public boolean doBC1T(final int simm16) {
		npc = fcr31.c ? branchTarget(pc, simm16) : (pc + 4);
		return true;
	}

	public boolean doBC1FL(final int simm16) {
		if (!fcr31.c) {
			npc = branchTarget(pc, simm16);
			return true;
		} else {
			pc += 4;
		}
		return false;
	}

	public boolean doBC1TL(final int simm16) {
		if (fcr31.c) {
			npc = branchTarget(pc, simm16);
			return true;
		} else {
			pc += 4;
		}
		return false;
	}

	public void doADDS(final int fd, final int fs, final int ft) {
		fpr[fd] = fpr[fs] + fpr[ft];
	}

	public void doSUBS(final int fd, final int fs, final int ft) {
		fpr[fd] = fpr[fs] - fpr[ft];
	}

	public void doMULS(final int fd, final int fs, final int ft) {
		fpr[fd] = fpr[fs] * fpr[ft];
	}

	public void doDIVS(final int fd, final int fs, final int ft) {
		fpr[fd] = fpr[fs] / fpr[ft];
	}

	public void doSQRTS(final int fd, final int fs) {
		fpr[fd] = (float) Math.sqrt(fpr[fs]);
	}

	public void doABSS(final int fd, final int fs) {
		fpr[fd] = Math.abs(fpr[fs]);
	}

	public void doMOVS(final int fd, final int fs) {
		fpr[fd] = fpr[fs];
	}

	public void doNEGS(final int fd, final int fs) {
		fpr[fd] = 0.0f - fpr[fs];
	}

	public void doROUNDWS(final int fd, final int fs) {
		fpr[fd] = Float.intBitsToFloat((int) Math.round(fpr[fs]));
	}

	public void doTRUNCWS(final int fd, final int fs) {
		fpr[fd] = Float.intBitsToFloat((int) (fpr[fs]));
	}

	public void doCEILWS(final int fd, final int fs) {
		fpr[fd] = Float.intBitsToFloat((int) Math.ceil(fpr[fs]));
	}

	public void doFLOORWS(final int fd, final int fs) {
		fpr[fd] = Float.intBitsToFloat((int) Math.floor(fpr[fs]));
	}

	public void doCVTSW(final int fd, final int fs) {
		fpr[fd] = Double.doubleToRawLongBits(fpr[fs]);
	}

	public void doCVTWS(final int fd, final int fs) {
		switch (fcr31.rm) {
			case 1:
				fpr[fd] = Float.intBitsToFloat((int) (fpr[fs]));
				break;
			case 2:
				fpr[fd] = Float.intBitsToFloat((int) Math.ceil(fpr[fs]));
				break;
			case 3:
				fpr[fd] = Float.intBitsToFloat((int) Math.floor(fpr[fs]));
				break;
			default:
				fpr[fd] = Float.intBitsToFloat((int) Math.rint(fpr[fs]));
				break;
		}
	}

	public void doCCONDS(final int fs, final int ft, final int cond) {
		final double x = fpr[fs];
		final double y = fpr[ft];
		final boolean unordered = ((cond & 1) != 0) && (Double.isNaN(x) || Double.isNaN(y));

		if (unordered) {
			fcr31.c = true;
		} else {
			final boolean equal = ((cond & 2) != 0) && (x == y);
			final boolean less = ((cond & 4) != 0) && (x < y);

			fcr31.c = less || equal;
		}
	}

	public void doLWC1(final int ft, final int rs, final int simm16) {
		fpr[ft] = Float.intBitsToFloat(processor.memory.read32(gpr[rs].read32() + simm16));
	}

	public void doLWXC1(final int base, final int index, final int fd) {
		fpr[fd] = Float.intBitsToFloat(processor.memory.read32(gpr[base].read32() + gpr[index].read32()));
	}

	public void doSWC1(final int ft, final int rs, final int simm16) {
		processor.memory.write32(gpr[rs].read32() + simm16, (int) Double.doubleToRawLongBits(fpr[ft]));
	}
}
