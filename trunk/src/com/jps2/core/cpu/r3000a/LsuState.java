package com.jps2.core.cpu.r3000a;

import com.jps2.JPS2;
import com.jps2.core.memory.MemoryManager;

public class LsuState extends MduState {

	static final MemoryManager	memory	      = JPS2.getMemoryManager();
	protected static final boolean	  CHECK_ALIGNMENT	= true;

	@Override
	void reset() {
	}

	@Override
	void resetAll() {
		super.resetAll();
	}

	LsuState() {
	}

	void copy(final LsuState that) {
		super.copy(that);
	}

	LsuState(final LsuState that) {
		super(that);
	}

	final void doLB(final int rt, final int rs, final int simm16) {
		final int word = (byte) memory.read8(gpr[rs].read32() + simm16);
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	final void doLBU(final int rt, final int rs, final int simm16) {
		final int word = memory.read8(gpr[rs].read32() + simm16) & 0xff;
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	final void doLH(final int rt, final int rs, final int simm16) {
		if (CHECK_ALIGNMENT) {
			final int address = gpr[rs].read32() + simm16;
			if ((address & 1) != 0) {
				throw new RuntimeException(String.format("LH unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		final int word = (short) memory.read16(gpr[rs].read32() + simm16);
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	final void doLHU(final int rt, final int rs, final int simm16) {
		if (CHECK_ALIGNMENT) {
			final int address = gpr[rs].read32() + simm16;
			if ((address & 1) != 0) {
				throw new RuntimeException(String.format("LHU unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		final int word = memory.read16(gpr[rs].read32() + simm16) & 0xffff;
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	private static final int[]	lwlMask	 = { 0xffffff, 0xffff, 0xff, 0 };
	private static final int[]	lwlShift	= { 24, 16, 8, 0 };

	final void doLWL(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = address & 0x3;
		final int value = gpr[rt].read32();

		final int data = memory.read32(address & 0xfffffffc);
		if (rt != 0) {
			gpr[rt].write32((data << lwlShift[offset]) | (value & lwlMask[offset]));
		}
	}

	final void doLW(final int rt, final int rs, final int simm16) {
		if (CHECK_ALIGNMENT) {
			final int address = gpr[rs].read32() + simm16;
			if ((address & 3) != 0) {
				throw new RuntimeException(String.format("LW unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		final int word = memory.read32(gpr[rs].read32() + simm16);
		if (rt != 0) {
			gpr[rt].write32(word);
		}
	}

	private static final int[]	lwrMask	 = { 0, 0xff000000, 0xffff0000, 0xffffff00 };
	private static final int[]	lwrShift	= { 0, 8, 16, 24 };

	final void doLWR(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = address & 0x3;
		final int value = gpr[rt].read32();

		final int data = memory.read32(address & 0xfffffffc);
		if (rt != 0) {
			gpr[rt].write32((data >>> lwrShift[offset]) | (value & lwrMask[offset]));
		}
	}
	
	final void doSB(final int rt, final int rs, final int simm16) {
		memory.write8(gpr[rs].read32() + simm16, gpr[rt].read8());
	}

	final void doSH(final int rt, final int rs, final int simm16) {
		if (CHECK_ALIGNMENT) {
			final int address = gpr[rs].read32() + simm16;
			if ((address & 1) != 0) {
				throw new RuntimeException(String.format("SH unaligned addr:0x%08x pc:0x%08x", address, pc));
			}
		}

		memory.write16(gpr[rs].read32() + simm16, (short) (gpr[rt].read32() & 0xFFFF));
	}

	private static final int[]	swlMask	 = { 0xffffff00, 0xffff0000, 0xff000000, 0 };
	private static final int[]	swlShift	= { 24, 16, 8, 0 };

	final void doSWL(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = address & 0x3;
		final int value = gpr[rt].read32();
		int data = memory.read32(address & 0xfffffffc);

		data = (value >>> swlShift[offset]) | (data & swlMask[offset]);

		memory.write32(address & 0xfffffffc, data);
	}

	final void doSW(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;

		if (CHECK_ALIGNMENT && (address & 3) != 0) {
			throw new RuntimeException(String.format("SW unaligned addr:0x%08x pc:0x%08x", address, pc));
		}

		memory.write32(address, gpr[rt].read32());
	}

	private static final int[]	swrMask	 = { 0, 0xff, 0xffff, 0xffffff };
	private static final int[]	swrShift	= { 0, 8, 16, 24 };

	final void doSWR(final int rt, final int rs, final int simm16) {
		final int address = gpr[rs].read32() + simm16;
		final int offset = address & 0x3;
		final int value = gpr[rt].read32();
		int data = memory.read32(address & 0xfffffffc);

		data = (value << swrShift[offset]) | (data & swrMask[offset]);

		memory.write32(address & 0xfffffffc, data);
	}
}
