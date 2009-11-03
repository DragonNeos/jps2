package com.jps2.core.cpu;

//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;

import com.jps2.core.cpu.Common.Instruction;
import com.jps2.core.memory.AbstractMemoryManager;

public abstract class Processor {

	private static final boolean ENABLE_INSN_EXECUTE_COUNT = false;
	// cache controls
	private final CacheLine[] instCache = new CacheLine[0x10000];
	private final int cacheMask = instCache.length - 1;
	private long insnCacheHits, insnCacheMisses, insnCount = 0;

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

//	private  FileOutputStream out;
//	{
//		try {
//			out = new FileOutputStream("/work/JPS2/logs/jps2-log-IOP.txt");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
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
//		try {
//			out.write(("I/" + debugFormater(cpu.pc) + " "
//					+ debugFormater((int) insnCount) + ": " + debugFormater(cpu.pc)
//					+ " " + debugFormater(line.opcode) + ": " + line.insn + "\r\n").getBytes());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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

//	private static final String debugFormater(int code) {
//		StringBuilder builder = new StringBuilder(Integer.toHexString(code));
//		while (builder.length() < 8) {
//			builder.insert(0, 0);
//		}
//		return builder.toString();
//	}

	public void interpret() {
		cpu.cycle++;
		final CacheLine line = fetchDecodedInstruction();
		line.insn.setInstruction(line.opcode);
		line.insn.interpret(line.opcode, false);
		if (ENABLE_INSN_EXECUTE_COUNT) {
			line.insn.increaseCount();
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
