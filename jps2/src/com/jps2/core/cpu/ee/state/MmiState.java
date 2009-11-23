package com.jps2.core.cpu.ee.state;

public class MmiState extends SauState {

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
}
