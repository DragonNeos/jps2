package com.jps2.core.cpu.ee.state;

import java.math.BigInteger;

public abstract class MmiState extends SauState {

	public final void doPLZCW(final int rs, final int rd) {
		if (rd != 0) {
			long result = 0;
			byte count = 0;
			final long value = gpr[rs].read64();

			int i = (int) (value & 0xFFFFFFFF);

			if (i >= 0) {
				i = ~i;
			}

			for (; i < 0; i <<= 1) {
				count++;
			}

			result = count;

			count = 0;

			i = (int) ((value >> 32) & 0xFFFFFFFF);

			if (i >= 0) {
				i = ~i;
			}

			for (; i < 0; i <<= 1) {
				count++;
			}

			result = result | (count << 32);

			gpr[rd].write64(result);
		}
	}

	public final void doMFHI1(final int rd) {
		if (rd != 0) {
			gpr[rd].write64(HI.read128()[0]);
		}
	}

	public final void doMTHI1(final int rs) {
		HI.write128(gpr[rs].read64(), 0);
	}

	public final void doMFLO1(final int rd) {
		if (rd != 0) {
			gpr[rd].write64(LO.read128()[0]);
		}
	}

	public final void doMTLO1(final int rs) {
		LO.write128(gpr[rs].read64(), 0);
	}

	public final void doPSLLH(final int rt, final int rd, int sa) {
		if (rd != 0) {
			sa = sa & 0xf;
			final long[] rtValue = gpr[rt].read128();
			final long[] value = new long[2];

			value[1] = (((rtValue[1] & 0xFFFF) << sa) & 0xFFFF);
			value[1] |= (((rtValue[1] & 0xFFFF0000) << sa) & 0xFFFF0000);
			value[1] |= (((rtValue[1] & 0xFFFF00000000L) << sa) & 0xFFFF00000000L);
			value[1] |= (((rtValue[1] & 0xFFFF000000000000L) << sa) & 0xFFFF000000000000L);

			value[0] = (((rtValue[0] & 0xFFFF) << sa) & 0xFFFF);
			value[0] |= (((rtValue[0] & 0xFFFF0000) << sa) & 0xFFFF0000);
			value[0] |= (((rtValue[0] & 0xFFFF00000000L) << sa) & 0xFFFF00000000L);
			value[0] |= (((rtValue[0] & 0xFFFF000000000000L) << sa) & 0xFFFF000000000000L);

			gpr[rd].write128(value);
		}
	}

	public final void doPSRLH(final int rt, final int rd, int sa) {
		if (rd != 0) {
			sa = sa & 0xf;
			final long[] rtValue = gpr[rt].read128();
			final long[] value = new long[2];

			value[1] = (((rtValue[1] & 0xFFFF) >>> sa) & 0xFFFF);
			value[1] |= (((rtValue[1] & 0xFFFF0000) >>> sa) & 0xFFFF0000);
			value[1] |= (((rtValue[1] & 0xFFFF00000000L) >>> sa) & 0xFFFF00000000L);
			value[1] |= (((rtValue[1] & 0xFFFF000000000000L) >>> sa) & 0xFFFF000000000000L);

			value[0] = (((rtValue[0] & 0xFFFF) >>> sa) & 0xFFFF);
			value[0] |= (((rtValue[0] & 0xFFFF0000) >>> sa) & 0xFFFF0000);
			value[0] |= (((rtValue[0] & 0xFFFF00000000L) >>> sa) & 0xFFFF00000000L);
			value[0] |= (((rtValue[0] & 0xFFFF000000000000L) >>> sa) & 0xFFFF000000000000L);

			gpr[rd].write128(value);
		}
	}

	public final void doPSRAH(final int rt, final int rd, int sa) {
		if (rd != 0) {
			sa = sa & 0xf;
			final long[] rtValue = gpr[rt].read128();
			final long[] value = new long[2];

			value[1] = ((((byte) (rtValue[1] & 0xFFFF)) >> sa) & 0xFFFF);
			value[1] |= ((((((byte) (rtValue[1] >> 16)) & 0xFF) >> sa) << 16) & 0xFFFF0000);
			value[1] |= ((((((byte) (rtValue[1] >> 32)) & 0xFF) >> sa) << 32) & 0xFFFF00000000L);
			value[1] |= ((((((byte) (rtValue[1] >> 48)) & 0xFF) >> sa) << 48) & 0xFFFF000000000000L);

			value[0] = ((((byte) (rtValue[0] & 0xFFFF)) >> sa) & 0xFFFF);
			value[0] |= ((((((byte) (rtValue[0] >> 16)) & 0xFF) >> sa) << 16) & 0xFFFF0000);
			value[0] |= ((((((byte) (rtValue[0] >> 32)) & 0xFF) >> sa) << 32) & 0xFFFF00000000L);
			value[0] |= ((((((byte) (rtValue[0] >> 48)) & 0xFF) >> sa) << 48) & 0xFFFF000000000000L);

			gpr[rd].write128(value);
		}
	}

	public final void doPSLLW(final int rt, final int rd, int sa) {
		if (rd != 0) {
			sa = sa & 0xf;
			final long[] rtValue = gpr[rt].read128();
			final long[] value = new long[2];

			value[1] = (rtValue[1] << sa) & 0xFFFFFFFF;
			value[1] |= (rtValue[1] & 0xFFFFFFFF00000000L) << sa;
			value[0] = (rtValue[0] << sa) & 0xFFFFFFFF;
			value[0] |= (rtValue[0] & 0xFFFFFFFF00000000L) << sa;

			gpr[rd].write128(value);
		}
	}

	public final void doPSRLW(final int rt, final int rd, int sa) {
		if (rd != 0) {
			sa = sa & 0xf;
			final long[] rtValue = gpr[rt].read128();
			final long[] value = new long[2];

			value[1] = (rtValue[1] & 0xFFFFFFFF) >> sa;
			value[1] |= (rtValue[1] >>> sa) & 0xFFFFFFFF00000000L;
			value[0] = (rtValue[0] & 0xFFFFFFFF) >> sa;
			value[0] |= (rtValue[0] >>> sa) & 0xFFFFFFFF00000000L;

			gpr[rd].write128(value);
		}
	}

	public final void doPSRAW(final int rt, final int rd, int sa) {
		if (rd != 0) {
			sa = sa & 0xf;
			final long[] rtValue = gpr[rt].read128();
			final long[] value = new long[2];

			value[1] = ((int) (rtValue[1] & 0xFFFFFFFF)) >> sa;
			value[1] |= (rtValue[1] >> sa) & 0xFFFFFFFF00000000L;
			value[0] = ((int) (rtValue[0] & 0xFFFFFFFF)) >> sa;
			value[0] |= (rtValue[0] >> sa) & 0xFFFFFFFF00000000L;

			gpr[rd].write128(value);
		}
	}

	public final void doPADDW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] value = new long[2];
			final long[] rsValue = gpr[rs].read128();
			final long[] rtValue = gpr[rt].read128();

			value[1] = (rsValue[1] + rtValue[1]) & 0xFFFFFFFF;
			value[1] |= ((rsValue[1] >>> 64) + (rtValue[1] >>> 64)) << 64;
			value[0] = (rsValue[0] + rtValue[0]) & 0xFFFFFFFF;
			value[0] |= ((rsValue[0] >>> 64) + (rtValue[0] >>> 64)) << 64;

			gpr[rd].write128(value);
		}
	}

	public final void doPSUBW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] value = new long[2];
			final long[] rsValue = gpr[rs].read128();
			final long[] rtValue = gpr[rt].read128();

			value[1] = (rsValue[1] - rtValue[1]) & 0xFFFFFFFF;
			value[1] |= ((rsValue[1] >>> 64) - (rtValue[1] >>> 64)) << 64;
			value[0] = (rsValue[0] - rtValue[0]) & 0xFFFFFFFF;
			value[0] |= ((rsValue[0] >>> 64) - (rtValue[0] >>> 64)) << 64;

			gpr[rd].write128(value);
		}
	}

	public final void doPCGTW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] value = new long[2];
			final long[] rsValue = gpr[rs].read128();
			final long[] rtValue = gpr[rt].read128();

			value[1] = (rsValue[1] & 0xFFFFFFFF) > (rtValue[1] & 0xFFFFFFFF) ? 0xFFFFFFFF : 0x0;
			value[1] |= (rsValue[1] & 0xFFFFFFFF00000000L) > (rtValue[1] & 0xFFFFFFFF00000000L) ? 0xFFFFFFFF00000000L : 0x0;

			value[0] = (rsValue[0] & 0xFFFFFFFF) > (rtValue[0] & 0xFFFFFFFF) ? 0xFFFFFFFF : 0x0;
			value[0] |= (rsValue[0] & 0xFFFFFFFF00000000L) > (rtValue[0] & 0xFFFFFFFF00000000L) ? 0xFFFFFFFF00000000L : 0x0;

			gpr[rd].write128(value);
		}
	}

	public final void doPMAXW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] value = new long[2];
			final long[] rsValue = gpr[rs].read128();
			final long[] rtValue = gpr[rt].read128();

			value[1] = (rsValue[1] & 0xFFFFFFFF) > (rtValue[1] & 0xFFFFFFFF) ? (rsValue[1] & 0xFFFFFFFF) : (rtValue[1] & 0xFFFFFFFF);
			value[1] |= (rsValue[1] & 0xFFFFFFFF00000000L) > (rtValue[1] & 0xFFFFFFFF00000000L) ? (rsValue[1] & 0xFFFFFFFF00000000L) : (rtValue[1] & 0xFFFFFFFF00000000L);

			value[0] = (rsValue[0] & 0xFFFFFFFF) > (rtValue[0] & 0xFFFFFFFF) ? (rsValue[0] & 0xFFFFFFFF) : (rtValue[0] & 0xFFFFFFFF);
			value[0] |= (rsValue[0] & 0xFFFFFFFF00000000L) > (rtValue[0] & 0xFFFFFFFF00000000L) ? (rsValue[0] & 0xFFFFFFFF00000000L) : (rtValue[0] & 0xFFFFFFFF00000000L);

			gpr[rd].write128(value);
		}
	}

	public final void doPOR(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] rsValue = gpr[rs].read128();
			final long[] rtValue = gpr[rt].read128();

			gpr[rd].write128(new long[] {
								rsValue[0] | rtValue[0],
								rsValue[1] | rtValue[1]
			});
		}
	}

	public final void doPPAC5(final int rt, final int rd) {
		if (rd != 0) {
			final int[] value = new int[4];
			final int[] rtValue = convertLongArrayToIntArray(gpr[rt].read128());

			value[3] = pack5(rtValue[3]);
			value[2] = pack5(rtValue[2]);
			value[1] = pack5(rtValue[1]);
			value[0] = pack5(rtValue[0]);

			gpr[rd].write128(convertIntArrayToLongArray(value));
		}
	}

	private static int pack5(final int toPack) {
		return (toPack >> 3) & 0x0000001F | (toPack >> 6) & 0x000003E0 | (toPack >> 9) & 0x00007C00 | ((toPack >> 16) & 0x00008000);
	}

	public final void doPEXT5(final int rt, final int rd) {
		if (rd != 0) {
			final int[] value = new int[4];
			final int[] rtValue = convertLongArrayToIntArray(gpr[rt].read128());

			value[3] = ext5(rtValue[3]);
			value[2] = ext5(rtValue[2]);
			value[1] = ext5(rtValue[1]);
			value[0] = ext5(rtValue[0]);

			gpr[rd].write128(convertIntArrayToLongArray(value));
		}
	}

	private static final int ext5(final int toExtend) {
		return (toExtend & 0x0000001F) << 3 | (toExtend & 0x000003E0) << 6 | (toExtend & 0x00007C00) << 9 | (toExtend & 0x00008000) << 16;
	}

	public final void doPPACB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] rtValue = gpr[rt].read128();
			long packedRtValue = rtValue[1] & 0xF;
			packedRtValue |= (rtValue[1] >> 8) & 0xF0;
			packedRtValue |= ((rtValue[1] >> 16) & 0xF00);
			packedRtValue |= ((rtValue[1] >> 24) & 0xF000);
			packedRtValue |= ((rtValue[0]) & 0xF) << 32;
			packedRtValue |= ((rtValue[0] >> 8) & 0xF0) << 32;
			packedRtValue |= ((rtValue[0] >> 16) & 0xF00) << 32;
			packedRtValue |= ((rtValue[0] >> 24) & 0xF000) << 32;

			final long[] rsValue = gpr[rt].read128();
			long packedRsValue = rsValue[1] & 0xF;
			packedRsValue |= (rsValue[1] >> 8) & 0xF0;
			packedRsValue |= ((rsValue[1] >> 16) & 0xF00);
			packedRsValue |= ((rsValue[1] >> 24) & 0xF000);
			packedRsValue |= ((rsValue[0]) & 0xF) << 32;
			packedRsValue |= ((rsValue[0] >> 8) & 0xF0) << 32;
			packedRsValue |= ((rsValue[0] >> 16) & 0xF00) << 32;
			packedRsValue |= ((rsValue[0] >> 24) & 0xF000) << 32;

			gpr[rd].write128(new long[] {
								packedRsValue,
								packedRtValue
			});
		}
	}

	public final void doPEXTLB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long rsValue = gpr[rs].read64();
			final long rtValue = gpr[rt].read64();

			final long[] value = new long[2];

			value[1] |= rtValue & 0xFF;
			value[1] |= (rsValue << 8) & 0xFF00;
			value[1] |= (rtValue << 8) & 0xFF0000;
			value[1] |= (rsValue << 16) & 0xFF000000;
			value[1] |= (rtValue << 16) & 0xFF00000000L;
			value[1] |= (rsValue << 24) & 0xFF0000000000L;
			value[1] |= (rtValue << 24) & 0xFF000000000000L;
			value[1] |= (rsValue << 32) & 0xFF00000000000000L;

			value[0] |= (rtValue >> 32) & 0xFF;
			value[0] |= (rsValue >> 24) & 0xFF00;
			value[0] |= (rtValue >> 24) & 0xFF0000;
			value[0] |= (rsValue >> 16) & 0xFF000000;
			value[0] |= (rtValue >> 16) & 0xFF00000000L;
			value[0] |= (rsValue >> 8) & 0xFF0000000000L;
			value[0] |= (rtValue >> 8) & 0xFF000000000000L;
			value[0] |= rsValue & 0xFF00000000000000L;

			gpr[rd].write128(value);
		}
	}

	public final void doPSUBSB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final byte[] rsValue = convertLongArrayToByteArray(gpr[rs].read128());
			final byte[] rtValue = convertLongArrayToByteArray(gpr[rt].read128());
			final byte[] value = new byte[16];

			for (int i = 0; i < 16; i++) {
				value[i] = subtractClampBytes(rsValue[i], rtValue[i]);
			}
			gpr[rd].write128(convertByteArrayToLongArray(value));
		}
	}

	public final void doPADDSB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final byte[] rsValue = convertLongArrayToByteArray(gpr[rs].read128());
			final byte[] rtValue = convertLongArrayToByteArray(gpr[rt].read128());
			final byte[] value = new byte[16];

			for (int i = 0; i < 16; i++) {
				value[i] = addClampBytes(rsValue[i], rtValue[i]);
			}
			gpr[rd].write128(convertByteArrayToLongArray(value));
		}
	}

	public final void doPPACH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] rsValue = gpr[rs].read128();
			final long[] rtValue = gpr[rt].read128();
			final long[] value = new long[2];

			value[0] = rtValue[0] & 0xFFFF;
			value[0] = (rtValue[0] >> 16) & 0xFFFF0000;
			value[0] = (rtValue[1] & 0xFFFF) << 32;
			value[0] = (rtValue[1] & 0xFFFF00000000L) << 16;

			value[1] = rsValue[0] & 0xFFFF;
			value[1] = (rsValue[0] >> 16) & 0xFFFF0000;
			value[1] = (rsValue[1] & 0xFFFF) << 32;
			value[1] = (rsValue[1] & 0xFFFF00000000L) << 16;

			gpr[rd].write128(value);
		}
	}

	public final void doPEXTLH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long rsValue = gpr[rs].read64();
			final long rtValue = gpr[rt].read64();
			final long[] value = new long[2];

			value[1] = rtValue & 0xFFFF;
			value[1] |= (rsValue << 16) & 0xFFFF0000;
			value[1] |= (rtValue << 16) & 0xFFFF00000000L;
			value[1] |= (rsValue << 32) & 0xFFFF000000000000L;

			value[0] = (rtValue >> 32) & 0xFFFF;
			value[0] = (rsValue >> 16) & 0xFFFF0000;
			value[0] = (rtValue >> 16) & 0xFFFF00000000L;
			value[0] = rsValue & 0xFFFF000000000000L;

			gpr[rd].write128(value);
		}
	}

	public final void doPADDH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());
			final short[] value = new short[8];

			for (int i = 0; i < 8; i++) {
				value[i] = (short) ((rsValue[i] + rtValue[i]) & 0xFFFF);
			}
			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPSUBH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());
			final short[] value = new short[8];

			for (int i = 0; i < 8; i++) {
				value[i] = (short) ((rsValue[i] - rtValue[i]) & 0xFFFF);
			}
			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPCGTH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());
			final short[] value = new short[8];

			for (int i = 0; i < 8; i++) {
				value[i] = rsValue[i] > rtValue[i] ? (short) 0xFFFF : (short) 0x0000;
			}
			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPMAXH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());
			final short[] value = new short[8];

			for (int i = 0; i < 8; i++) {
				value[i] = rsValue[i] > rtValue[i] ? rsValue[i] : rtValue[i];
			}
			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPADDB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final byte[] rsValue = convertLongArrayToByteArray(gpr[rs].read128());
			final byte[] rtValue = convertLongArrayToByteArray(gpr[rt].read128());
			final byte[] value = new byte[16];

			for (int i = 0; i < 16; i++) {
				value[i] = (byte) ((rsValue[i] + rtValue[i]) & 0xFF);
			}
			gpr[rd].write128(convertByteArrayToLongArray(value));
		}
	}

	public final void doPSUBB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final byte[] rsValue = convertLongArrayToByteArray(gpr[rs].read128());
			final byte[] rtValue = convertLongArrayToByteArray(gpr[rt].read128());
			final byte[] value = new byte[16];

			for (int i = 0; i < 16; i++) {
				value[i] = (byte) ((rsValue[i] - rtValue[i]) & 0xFF);
			}
			gpr[rd].write128(convertByteArrayToLongArray(value));
		}
	}

	public final void doPCGTB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final byte[] rsValue = convertLongArrayToByteArray(gpr[rs].read128());
			final byte[] rtValue = convertLongArrayToByteArray(gpr[rt].read128());
			final byte[] value = new byte[16];

			for (int i = 0; i < 16; i++) {
				value[i] = (byte) (rsValue[i] > rtValue[i] ? 0xFF : 0x00);
			}
			gpr[rd].write128(convertByteArrayToLongArray(value));
		}
	}

	public final void doPPACW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] rsValue = gpr[rs].read128();
			final long[] rtValue = gpr[rt].read128();
			final long[] value = new long[2];

			value[1] = rtValue[1] & 0xFFFFFFFF;
			value[1] = (rtValue[0] & 0xFFFFFFFF) << 32;

			value[0] = rsValue[1] & 0xFFFFFFFF;
			value[0] = (rsValue[0] & 0xFFFFFFFF) << 32;

			gpr[rd].write128(value);
		}
	}

	public final void doPADDSW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final int[] rsValue = convertLongArrayToIntArray(gpr[rs].read128());
			final int[] rtValue = convertLongArrayToIntArray(gpr[rt].read128());
			final int[] value = new int[4];
			for (int i = 0; i < 4; i++) {
				value[i] = addClampIntegers(rsValue[i], rtValue[i]);
			}
			gpr[rd].write128(convertIntArrayToLongArray(value));
		}
	}

	public final void doPSUBSW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final int[] rsValue = convertLongArrayToIntArray(gpr[rs].read128());
			final int[] rtValue = convertLongArrayToIntArray(gpr[rt].read128());
			final int[] value = new int[4];
			for (int i = 0; i < 4; i++) {
				value[i] = subtractClampIntegers(rsValue[i], rtValue[i]);
			}
			gpr[rd].write128(convertIntArrayToLongArray(value));
		}
	}

	public final void doPEXTLW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long rsValue = gpr[rs].read64();
			final long rtValue = gpr[rt].read64();
			final long[] value = new long[2];

			value[1] = rtValue & 0xFFFFFFFF;
			value[1] |= (rsValue << 32) & 0xFFFFFFFF00000000L;

			value[0] = (rtValue >> 32) & 0xFFFFFFFF;
			value[0] |= rsValue & 0xFFFFFFFF00000000L;

			gpr[rd].write128(value);
		}
	}

	public final void doPADDSH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());
			final short[] value = new short[8];
			for (int i = 0; i < 8; i++) {
				value[i] = addClampShorts(rsValue[i], rtValue[i]);
			}
			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPSUBSH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());
			final short[] value = new short[8];
			for (int i = 0; i < 8; i++) {
				value[i] = subtractClampShorts(rsValue[i], rtValue[i]);
			}
			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPABSW(final int rt, final int rd) {
		if (rd != 0) {
			final int[] value = new int[4];
			final int[] rtValue = convertLongArrayToIntArray(gpr[rt].read128());

			for (int i = 0; i < 4; i++) {
				if (rtValue[i] == 0x80000000) {
					value[i] = 0x7FFFFFFF;
				} else {
					value[i] = rtValue[i] < 0 ? -rtValue[i] : rtValue[i];
				}
			}

			gpr[rd].write128(convertIntArrayToLongArray(value));
		}
	}

	public final void doPCEQW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final int[] value = new int[4];
			final int[] rsValue = convertLongArrayToIntArray(gpr[rs].read128());
			final int[] rtValue = convertLongArrayToIntArray(gpr[rt].read128());

			for (int i = 0; i < 4; i++) {
				if (rtValue[i] == rsValue[i]) {
					value[i] = 0xFFFFFFFF;
				}
			}

			gpr[rd].write128(convertIntArrayToLongArray(value));
		}
	}

	public final void doPMINW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final int[] value = new int[4];
			final int[] rsValue = convertLongArrayToIntArray(gpr[rs].read128());
			final int[] rtValue = convertLongArrayToIntArray(gpr[rt].read128());

			for (int i = 0; i < 4; i++) {
				if (rsValue[i] > rtValue[i]) {
					value[i] = rtValue[i];
				} else {
					value[i] = rsValue[i];
				}
			}

			gpr[rd].write128(convertIntArrayToLongArray(value));
		}
	}

	public final void doPADSBH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] value = new short[8];
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());

			for (int i = 0; i < 4; i++) {
				value[i] = (short) ((rsValue[i] + rtValue[i]) & 0xFFFF);
			}

			for (int i = 4; i < 8; i++) {
				value[i] = (short) ((rsValue[i] - rtValue[i]) & 0xFFFF);
			}

			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPABSH(final int rt, final int rd) {
		if (rd != 0) {
			final short[] value = new short[8];
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());

			for (int i = 0; i < 8; i++) {
				if (rtValue[i] == 0x8000) {
					value[i] = 0x7FFF;
				} else {
					value[i] = (short) (rtValue[i] < 0 ? -rtValue[i] : rtValue[i]);
				}
			}

			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPCEQH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] value = new short[8];
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());

			for (int i = 0; i < 8; i++) {
				if (rsValue[i] == rtValue[i]) {
					value[i] = (short) 0xFFFF;
				}
			}

			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPMINH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] value = new short[8];
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());

			for (int i = 0; i < 8; i++) {
				value[i] = rsValue[i] > rtValue[i] ? rtValue[i] : rsValue[i];
			}

			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPCEQB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final byte[] value = new byte[16];
			final byte[] rsValue = convertLongArrayToByteArray(gpr[rs].read128());
			final byte[] rtValue = convertLongArrayToByteArray(gpr[rt].read128());

			for (int i = 0; i < 16; i++) {
				if (rsValue[i] == rtValue[i]) {
					value[i] = (byte) 0xFF;
				}
			}

			gpr[rd].write128(convertByteArrayToLongArray(value));
		}
	}

	public final void doPADDUW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final int[] value = new int[4];
			final int[] rsValue = convertLongArrayToIntArray(gpr[rs].read128());
			final int[] rtValue = convertLongArrayToIntArray(gpr[rt].read128());

			long tmp;
			for (int i = 0; i < 4; i++) {
				tmp = (((long) rsValue[i]) & 0xFFFFFFFF) + (((long) rtValue[i]) & 0xFFFFFFFF);

				if (tmp > 0xFFFFFFFFL) {
					tmp = 0xFFFFFFFFL;
				}

				value[i] = (int) tmp;
			}

			gpr[rd].write128(convertIntArrayToLongArray(value));
		}
	}

	public final void doPSUBUW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final int[] value = new int[4];
			final int[] rsValue = convertLongArrayToIntArray(gpr[rs].read128());
			final int[] rtValue = convertLongArrayToIntArray(gpr[rt].read128());

			long tmp;
			for (int i = 0; i < 4; i++) {
				tmp = (((long) rsValue[i]) & 0xFFFFFFFF) - (((long) rtValue[i]) & 0xFFFFFFFF);

				if (tmp < 0) {
					tmp = 0;
				}

				value[i] = (int) tmp;
			}

			gpr[rd].write128(convertIntArrayToLongArray(value));
		}
	}

	public final void doPEXTUW(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] rsValue = gpr[rs].read128();
			final long[] rtValue = gpr[rt].read128();
			final long[] value = new long[2];

			value[1] = (int) rtValue[0];
			value[1] |= rsValue[0] << 32;
			value[0] = rtValue[0] >>> 32;
			value[0] |= rsValue[0] & 0xFFFFFFFF00000000L;

			gpr[rd].write128(value);
		}
	}

	public final void doPADDUH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] value = new short[8];
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());

			int tmp;
			for (int i = 0; i < 8; i++) {
				tmp = ((rsValue[i]) & 0xFFFF) + ((rtValue[i]) & 0xFFFF);

				if (tmp > 0xFFFF) {
					tmp = 0xFFFF;
				}

				value[i] = (short) tmp;
			}

			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPSUBUH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final short[] value = new short[8];
			final short[] rsValue = convertLongArrayToShortArray(gpr[rs].read128());
			final short[] rtValue = convertLongArrayToShortArray(gpr[rt].read128());

			int tmp;
			for (int i = 0; i < 8; i++) {
				tmp = (rsValue[i] & 0xFFFF) - (rtValue[i] & 0xFFFF);

				if (tmp < 0) {
					tmp = 0;
				}

				value[i] = (short) tmp;
			}

			gpr[rd].write128(convertShortArrayToLongArray(value));
		}
	}

	public final void doPEXTUH(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] value = new long[2];
			final long[] rsValue = gpr[rs].read128();
			final long[] rtValue = gpr[rt].read128();

			value[1] = rtValue[0] & 0xFFFF;
			value[1] |= (rsValue[0] << 32) & 0xFFFF0000;
			value[1] |= (rtValue[0] << 32) & 0xFFFF00000000L;
			value[1] |= (rsValue[0] << 64) & 0xFFFF000000000000L;

			value[0] = rsValue[0] & 0xFFFF000000000000L;
			value[0] |= (rtValue[0] >> 32) & 0xFFFF00000000L;
			value[0] |= (rsValue[0] >> 32) & 0xFFFF0000L;
			value[0] |= (rtValue[0] >> 64) & 0xFFFFL;

			gpr[rd].write128(value);
		}
	}

	public final void doPADDUB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final byte[] value = new byte[16];
			final byte[] rsValue = convertLongArrayToByteArray(gpr[rs].read128());
			final byte[] rtValue = convertLongArrayToByteArray(gpr[rt].read128());

			short tmp;
			for (int i = 0; i < 16; i++) {
				tmp = (short) ((rsValue[i] & 0xFF) + (rtValue[i] & 0xFF));

				if (tmp > 0xFF) {
					tmp = 0xFF;
				}

				value[i] = (byte) tmp;
			}

			gpr[rd].write128(convertByteArrayToLongArray(value));
		}
	}

	public final void doPSUBUB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final byte[] value = new byte[16];
			final byte[] rsValue = convertLongArrayToByteArray(gpr[rs].read128());
			final byte[] rtValue = convertLongArrayToByteArray(gpr[rt].read128());

			short tmp;
			for (int i = 0; i < 16; i++) {
				tmp = (short) ((rsValue[i] & 0xFF) - (rtValue[i] & 0xFF));

				if (tmp < 0) {
					tmp = 0;
				}

				value[i] = (byte) tmp;
			}

			gpr[rd].write128(convertByteArrayToLongArray(value));
		}
	}

	private static final long[]	maskPEXTUB	= new long[] {
						0x00000000000000FFL,
						0x000000000000FF00L,
						0x0000000000FF0000L,
						0x00000000FF000000L,
						0x000000FF00000000L,
						0x0000FF0000000000L,
						0x00FF000000000000L,
						0xFF00000000000000L
											};

	public final void doPEXTUB(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			final long[] value = new long[2];
			final long rsValue = gpr[rs].read128()[0];
			final long rtValue = gpr[rt].read128()[0];

			for (int i = 0; i < 4; i++) {
				value[1] |= (rtValue << (i * 8)) & maskPEXTUB[(i * 2)];
				value[1] |= (rsValue << ((i + 1) * 8)) & maskPEXTUB[(i * 2) + 1];
			}

			for (int i = 0; i < 4; i++) {
				value[0] |= (rtValue >> (32 - (i * 8))) & maskPEXTUB[(i * 2)];
				value[0] |= (rsValue >> (32 - ((i + 1) * 8))) & maskPEXTUB[(i * 2) + 1];
			}

			gpr[rd].write128(value);
		}
	}

	public final void doQFSRV(final int rs, final int rt, final int rd) {
		if (rd != 0) {
			if (SA != 0) {
				final byte[] concatValue = new byte[32];
				System.arraycopy(convertLongArrayToByteArray(gpr[rs].read128()), 0, concatValue, 0, 16);
				System.arraycopy(convertLongArrayToByteArray(gpr[rt].read128()), 0, concatValue, 16, 16);
				final byte[] shiftValue = new BigInteger(concatValue).shiftRight(SA).toByteArray();

				final byte[] value = new byte[16];

				if (shiftValue.length >= 16) {
					System.arraycopy(shiftValue, shiftValue.length - 16, value, 0, 16);
				} else {
					System.arraycopy(shiftValue, 0, value, 16 - shiftValue.length, shiftValue.length);
				}

				gpr[rd].write128(convertByteArrayToLongArray(value));
			} else {
				gpr[rd].write128(gpr[rt].read128());
			}

		}
	}

	private static final byte subtractClampBytes(final byte a, final byte b) {
		final int subResult = a - b;

		if (subResult >= 0x7F) {
			return 0x7F;
		} else
			if (0x100 <= subResult && subResult < 0x180) {
				return (byte) 0x80;
			}

		return (byte) subResult;
	}

	private static final byte addClampBytes(final byte a, final byte b) {
		final int addResult = a + b;

		if (addResult > 0x7F) {
			return 0x7F;
		} else
			if (0x100 <= addResult && addResult < 0x180) {
				return (byte) 0x80;
			}

		return (byte) addResult;
	}

	private static final short addClampShorts(final short a, final short b) {
		final int addResult = ((a) & 0xFFFF) + ((b) & 0xFFF);

		if (addResult > 0x7FFF) {
			return 0x7FFF;
		} else
			if (0x10000L <= addResult && addResult < 0x8000) {
				return (short) 0x8000;
			}

		return (short) (addResult & 0xFFFF);
	}

	private static final short subtractClampShorts(final short a, final short b) {
		final int addResult = ((a) & 0xFFFF) + ((b) & 0xFFFF);

		if (addResult >= 0x7FFF) {
			return 0x7FFF;
		} else
			if (0x10000L <= addResult && addResult < 0x8000) {
				return (short) 0x8000;
			}

		return (short) (addResult & 0xFFFF);
	}

	private static final int addClampIntegers(final int a, final int b) {
		final long addResult = (((long) a) & 0xFFFFFFFF) + (((long) b) & 0xFFFFFFFF);

		if (addResult > 0x7FFFFFFF) {
			return 0x7FFFFFFF;
		} else
			if (0x100000000L <= addResult && addResult < 0x80000000) {
				return 0x80000000;
			}

		return (int) (addResult & 0xFFFFFFFF);
	}

	private static final int subtractClampIntegers(final int a, final int b) {
		final long addResult = (((long) a) & 0xFFFFFFFF) + (((long) b) & 0xFFFFFFFF);

		if (addResult >= 0x7FFFFFFF) {
			return 0x7FFFFFFF;
		} else
			if (0x100000000L <= addResult && addResult < 0x80000000) {
				return 0x80000000;
			}

		return (int) (addResult & 0xFFFFFFFF);
	}

	private static int[] convertLongArrayToIntArray(final long[] longArray) {
		final int[] intArray = new int[longArray.length * 2];

		for (int i = 0; i < longArray.length; i++) {
			intArray[2 * i] = (int) ((longArray[i] >> 64) & 0xFFFFFFFF);
			intArray[(2 * i) + 1] = (int) (longArray[i] & 0xFFFFFFFF);
		}

		return intArray;
	}

	private static long[] convertIntArrayToLongArray(final int[] intArray) {
		final long[] longArray = new long[intArray.length / 2];

		for (int i = 0; i < longArray.length; i++) {
			longArray[i] = intArray[(2 * i) + 1] & 0xFFFFFFFF;
			longArray[i] |= intArray[(2 * i)] << 64;
		}

		return longArray;
	}

	private static short[] convertLongArrayToShortArray(final long[] longArray) {
		final short[] shortArray = new short[longArray.length * 4];
		int s = 0;
		for (int l = 0; l < longArray.length; l++) {
			for (s = 0; s < 4; s++) {
				shortArray[l + s] = (short) ((longArray[l] >> (s * 16)) & 0xFFFF);
			}
		}

		return shortArray;
	}

	private static long[] convertShortArrayToLongArray(final short[] shortArray) {
		final long[] longArray = new long[shortArray.length / 4];
		int s = 0;
		for (int l = 0; l < longArray.length; l++) {
			for (s = 0; s < 4; s++) {
				longArray[l] |= (((long) shortArray[s + l]) & 0xFFFF) << (s * 16);
			}
		}

		return longArray;
	}

	private static byte[] convertLongArrayToByteArray(final long[] longArray) {
		final byte[] byteArray = new byte[longArray.length * 8];
		int b = 0;
		for (int l = 0; l < longArray.length; l++) {
			for (b = 0; b < 8; b++) {
				byteArray[l + b] = (byte) ((longArray[l] >> (b * 8)) & 0xFF);
			}
		}

		return byteArray;
	}

	private static long[] convertByteArrayToLongArray(final byte[] byteArray) {
		final long[] longArray = new long[byteArray.length / 8];
		int b = 0;
		for (int l = 0; l < longArray.length; l++) {
			for (b = 0; b < 8; b++) {
				longArray[l] |= (((long) byteArray[b + l]) & 0xFF) << (b * 8);
			}
		}

		return longArray;
	}
}
