package com.jps2.core.cpu;

import com.jps2.core.cpu.Common.Instruction;
import com.jps2.core.memory.AbstractMemoryManager;

public abstract class Processor {

	private static final boolean ENABLE_INSN_EXECUTE_COUNT = false;
	// cache controls
	private final CacheLine[] instCache = new CacheLine[0x10000];
	private final int cacheMask = instCache.length - 1;
	private long insnCacheHits, insnCacheMisses, insnCount;

	public final Cpu cpu;
	public final AbstractMemoryManager memory;

	public Processor(final Cpu cpu, final AbstractMemoryManager memory) {
		this.cpu = cpu;
		cpu.processor = this;
		this.memory = memory;
		for (int i = 0; i < instCache.length; i++) {
			instCache[i] = new CacheLine();
		}
	}

	private CacheLine nextDecodedInstruction() {
		final CacheLine line = getFromCache();
		// by default, the next instruction to emulate is at the next address
		cpu.pc += 4;
		return line;
	}

	private final CacheLine getFromCache() {
		final CacheLine line = instCache[cpu.pc & cacheMask];
		if (!line.valid || line.address != cpu.pc) {
			line.valid = true;
			line.address = cpu.pc;
			line.opcode = memory.read32(cpu.pc);
			line.insn = decode(line.opcode);
			insnCacheMisses++;
		} else {
			insnCacheHits++;
		}
		insnCount++;

		return line;
	}

	protected abstract Instruction decode(int opcode);

	private CacheLine fetchDecodedInstruction() {
		final CacheLine line = getFromCache();
		// by default, the next instruction to emulate is at the next address
		cpu.pc = cpu.npc = cpu.pc + 4;
		return line;
	}

	public void interpretDelayslot() {
		cpu.cycle++;
		final CacheLine line = nextDecodedInstruction();
		line.insn.setInstruction(line.opcode);
		line.insn.interpret(line.opcode, true);
		if (ENABLE_INSN_EXECUTE_COUNT) {
			line.insn.increaseCount();
		}

		cpu.nextPc();
	}

	public void step() {
		interpret();
	}

	public void interpret() {
		cpu.cycle++;
		final CacheLine line = fetchDecodedInstruction();
		line.insn.setInstruction(line.opcode);
		line.insn.interpret(line.opcode, false);
		if (ENABLE_INSN_EXECUTE_COUNT) {
			line.insn.increaseCount();
		}

		if (line.insn == Common.NOP) {
			if (Common.NOP.count() == 1000) {
				throw new RuntimeException("NOP abuse.");
			}
		} else {
			Common.NOP.resetCount();
		}
	}

	public abstract void processException(final ExcCode e, final int inst,
			final boolean delay);

	private final static class CacheLine {
		boolean valid;
		int address;
		int opcode;
		Common.Instruction insn;
	}
}
