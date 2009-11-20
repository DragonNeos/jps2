package com.jps2.core.cpu.ee.state;

public class MmiState extends SauState {

	public final void doPLZCW(final int rs, final int rd) {
		if (rd != 0) {
			long result = 0;
			byte count = 0;
			long value = gpr[rs].read64();

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
}
