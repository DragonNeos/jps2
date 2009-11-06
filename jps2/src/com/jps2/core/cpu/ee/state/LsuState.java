package com.jps2.core.cpu.ee.state;

public abstract class LsuState extends MduState {

	protected static final boolean CHECK_ALIGNMENT = true;

	@Override
	public void reset() {
	}

	@Override
	public void resetAll() {
		super.resetAll();
	}

	public LsuState() {
	}

	public void copy(final LsuState that) {
		super.copy(that);
	}

	public LsuState(final LsuState that) {
		super(that);
	}

	public void doLB(final int rt, final int rs, final int simm16) {
		final int word = (byte) processor.memory.read8(gpr[rs].read32()
				+ simm16);
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	public void doLBU(final int rt, final int rs, final int simm16) {
		final int word = processor.memory.read8(gpr[rs].read32() + simm16) & 0xff;
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	public void doLH(final int rt, final int rs, final int simm16) {
		if (CHECK_ALIGNMENT) {
			final int address = gpr[rs].read32() + simm16;
			if ((address & 1) != 0) {
				throw new RuntimeException(String.format(
						"LH unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		final int word = (short) processor.memory.read16(gpr[rs].read32()
				+ simm16);
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	public void doLHU(final int rt, final int rs, final int simm16) {
		if (CHECK_ALIGNMENT) {
			final int address = gpr[rs].read32() + simm16;
			if ((address & 1) != 0) {
				throw new RuntimeException(String.format(
						"LHU unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		final int word = processor.memory.read16(gpr[rs].read32() + simm16) & 0xffff;
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	private static final int[] lwlMask = { 0xffffff, 0xffff, 0xff, 0 };
	private static final int[] lwlShift = { 24, 16, 8, 0 };

	public void doLWL(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = address & 0x3;
		final int value = gpr[rt].read32();

		final int data = processor.memory.read32(address & 0xfffffffc);
		if (rt != 0) {
			gpr[rt].write32((data << lwlShift[offset])
					| (value & lwlMask[offset]));
		}
	}

	public void doLWU(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		if (rt != 0) {
			gpr[rt].write32(processor.memory.read32(address));
		} else {
			processor.memory.read32(address);
		}

	}

	public void doLW(final int rt, final int rs, final int simm16) {
		if (CHECK_ALIGNMENT) {
			final int address = gpr[rs].read32() + simm16;
			if ((address & 3) != 0) {
				throw new RuntimeException(String.format(
						"LW unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		final int word = processor.memory.read32(gpr[rs].read32() + simm16);
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	private static final int[] lwrMask = { 0, 0xff000000, 0xffff0000,
			0xffffff00 };
	private static final int[] lwrShift = { 0, 8, 16, 24 };

	public void doLWR(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = address & 0x3;
		final int value = gpr[rt].read32();

		final int data = processor.memory.read32(address & 0xfffffffc);
		if (rt != 0) {
			gpr[rt].write32((data >>> lwrShift[offset])
					| (value & lwrMask[offset]));
		}
	}

	private static final long[] ldrMask = new long[] { 0x0000000000000000L,
			0xff00000000000000L, 0xffff000000000000L, 0xffffff0000000000L,
			0xffffffff00000000L, 0xffffffffff000000L, 0xffffffffffff0000L,
			0xffffffffffffff00L };
	private static final int[] ldrShift = new int[] { 0, 8, 16, 24, 32, 40, 48,
			56 };

	public void doLDR(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = (address & 0x7);
		final long data = processor.memory.read64(address & 0xfffffffc);

		if (rt != 0) {
			gpr[rt].write64((data >>> ldrShift[offset])
					| (gpr[rt].read64() & ldrMask[offset]));
		}
	}

	private static final long[] ldlMask = new long[] { 0x00ffffffffffffffL,
			0x0000ffffffffffffL, 0x000000ffffffffffL, 0x00000000ffffffffL,
			0x0000000000ffffffL, 0x000000000000ffffL, 0x00000000000000ffL,
			0x0000000000000000L };
	private static final int[] ldlShift = new int[] { 56, 48, 40, 32, 24, 16,
			8, 0 };

	public void doLDL(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = (address & 0x7);
		final long data = processor.memory.read64(address & ~7);

		if (rt != 0) {
			gpr[rt].write64((gpr[rt].read64() & ldlMask[offset])
					| (data << ldlShift[offset]));
		}
	}

	public void doSB(final int rt, final int rs, final int simm16) {
		processor.memory.write8(gpr[rs].read32() + simm16, gpr[rt].read8());
	}

	public void doSH(final int rt, final int rs, final int simm16) {
		if (CHECK_ALIGNMENT) {
			final int address = gpr[rs].read32() + simm16;
			if ((address & 1) != 0) {
				throw new RuntimeException(String.format(
						"SH unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		processor.memory.write16(gpr[rs].read32() + simm16, (short) (gpr[rt]
				.read32() & 0xFFFF));
	}

	private static final int[] swlMask = { 0xffffff00, 0xffff0000, 0xff000000,
			0 };
	private static final int[] swlShift = { 24, 16, 8, 0 };

	public void doSWL(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = address & 0x3;
		final int value = gpr[rt].read32();
		int data = processor.memory.read32(address & 0xfffffffc);

		data = (value >>> swlShift[offset]) | (data & swlMask[offset]);

		processor.memory.write32(address & 0xfffffffc, data);
	}

	public void doSW(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;

		if (CHECK_ALIGNMENT && (address & 3) != 0) {
			throw new RuntimeException(String.format(
					"SW unaligned addr:0x%08x pc:0x%08x", address, pc));
		}

		processor.memory.write32(address, gpr[rt].read32());
	}

	private static final int[] swrMask = { 0, 0xff, 0xffff, 0xffffff };
	private static final int[] swrShift = { 0, 8, 16, 24 };

	public void doSWR(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = address & 0x3;
		final int value = gpr[rt].read32();
		int data = processor.memory.read32(address & 0xfffffffc);

		data = (value << swrShift[offset]) | (data & swrMask[offset]);

		processor.memory.write32(address & 0xfffffffc, data);
	}

	public void doLL(final int rt, final int rs, final int simm16) {
		final int word = processor.memory.read32(gpr[rs].read32() + simm16);
		if (rt != 0) {
			gpr[rt].write32(word);
		}
		// ll_bit = 1;
	}

	public void doSC(final int rt, final int rs, final int simm16) {
		processor.memory.write32(gpr[rs].read32() + simm16, gpr[rt].read32());
		if (rt != 0) {
			gpr[rt].write32(1); // = ll_bit;
		}
	}

	public final void doLQ(final int rt, final int rs, final int simm16) {
		int address = gpr[rs].read32() + simm16;
		address &= ~0xf;

		if (rt != 0) {
			gpr[rt].write128(processor.memory.read128(address));
		} else {
			// always read
			processor.memory.read128(address);
		}
	}

	public final void doSQ(final int rt, final int rs, final int simm16) {
		int address = gpr[rs].read32() + simm16;
		address &= ~0xf;
		processor.memory.write128(address, gpr[rt].read128());

	}
}
