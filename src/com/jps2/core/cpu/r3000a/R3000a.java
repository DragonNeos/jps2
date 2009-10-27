package com.jps2.core.cpu.r3000a;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

import com.jps2.JPS2;
import com.jps2.core.cpu.Common;
import com.jps2.core.cpu.ExcCode;
import com.jps2.core.memory.MemoryManager;


public class R3000a {

	public boolean	           ENABLE_STEP_TRACE	     = false;

	/**
	 * false=count how many times an insn appears in the code (static check). true=count how many times an insn is executed (dynamic check).
	 */
	public boolean	           ENABLE_INSN_EXECUTE_COUNT	= true;

	/** cache mem reads and insn decodes */
	private final static boolean	   ENABLE_INSN_CACHE	     = true;

	public CpuState	                   cpu	                     = new CpuState();

	public Common.Instruction	       insn;
	public Common.Instruction	       insnDelay;

	private static final MemoryManager	memory	                 = JPS2.getMemoryManager();
	public static Logger	           log	                     = Logger.getLogger("r3000a");

	R3000a() {
		insnCache = new CacheLine[INSN_CACHE_SIZE];
		for (int i = 0; i < INSN_CACHE_SIZE; i++)
			insnCache[i] = new CacheLine();
	}

	private static R3000a	processor;

	public static R3000a getProcessor() {
		if (processor == null) {
			processor = new R3000a();
			processor.reset();
		}

		return processor;
	}

	public static void clearProcessor() {
		processor = null;
	}

	public void reset() {
		invalidateICache();
		cpu.reset();
	}

	public void load(final ByteBuffer buffer) {

	}

	public void save(final ByteBuffer buffer) {

	}

	class CacheLine {
		boolean		       valid;
		int		           address;
		int		           opcode;
		Common.Instruction	insn;
	}

	private final int	      INSN_CACHE_SIZE	= 0x1000;
	private final int	      INSN_CACHE_MASK	= INSN_CACHE_SIZE - 1;
	private final CacheLine[]	insnCache;
	private long	          insnCacheHits, insnCacheMisses, insnCount;

	public long getInsnCount() {
		return insnCount;
	}

	public void invalidateICache() {
		if (insnCount != 0) {
			R3000a.log.info("icache hits:" + insnCacheHits + " (" + (insnCacheHits * 100 / insnCount) + "%)" + " misses:" + insnCacheMisses + " (" + (insnCacheMisses * 100 / insnCount) + "%)");
		}
		for (final CacheLine line : insnCache)
			line.valid = false;
		insnCacheHits = insnCacheMisses = insnCount = 0;
	}

	/** replaces (BcuState)cpu.fetchOpcode() */
	private CacheLine fetchDecodedInstruction() {
		final CacheLine line = insnCache[cpu.pc & INSN_CACHE_MASK];
		if (!line.valid || line.address != cpu.pc) {
			line.valid = true;
			line.address = cpu.pc;
			line.opcode = memory.read32(cpu.pc);
			line.insn = Decoder.instruction(line.opcode);
			insnCacheMisses++;
		} else {
			insnCacheHits++;
		}
		if (insnCount == 87915) {
			System.err.println("teste");
		}
		insnCount++;
		// by default, the next instruction to emulate is at the next address
		cpu.pc = cpu.npc = cpu.pc + 4;
		return line;
	}

	/** replaces (BcuState)cpu.nextOpcode() */
	private CacheLine nextDecodedInstruction() {
		final CacheLine line = insnCache[cpu.pc & INSN_CACHE_MASK];
		if (!line.valid || line.address != cpu.pc) {
			line.valid = true;
			line.address = cpu.pc;
			line.opcode = memory.read32(cpu.pc);
			line.insn = Decoder.instruction(line.opcode);
			insnCacheMisses++;
		} else {
			insnCacheHits++;
		}
		insnCount++;
		// by default, the next instruction to emulate is at the next address
		cpu.pc += 4;
		return line;
	}

	public void interpret() {
		cpu.cycle++;
		if (ENABLE_INSN_CACHE) {
			final CacheLine line = fetchDecodedInstruction();
			line.insn.setInstruction(line.opcode);
			if (ENABLE_STEP_TRACE) {
				System.err.println(line.insn);
			}
			line.insn.interpret(line.opcode, false);
			if (ENABLE_INSN_EXECUTE_COUNT) {
				line.insn.increaseCount();
			}
			insn = line.insn;
		} else {
			final int opcode = cpu.fetchOpcode();
			insn = Decoder.instruction(opcode);
			insn.setInstruction(opcode);
			if (ENABLE_STEP_TRACE) {
				System.err.println(insn);
			}
			insn.interpret(opcode, false);
			if (ENABLE_INSN_EXECUTE_COUNT) {
				insn.increaseCount();
			}
		}

		if (insn == Instructions.NOP) {
			if (Instructions.NOP.count() == 3) {
				throw new RuntimeException("NOP abuse.");
			}
		} else {
			Instructions.NOP.resetCount();
		}
	}

	public void interpretDelayslot() {
		cpu.cycle++;
		if (ENABLE_INSN_CACHE) {
			final CacheLine line = nextDecodedInstruction();
			line.insn.setInstruction(line.opcode);
			insnDelay = line.insn;
			line.insn.interpret(line.opcode, true);
			if (ENABLE_INSN_EXECUTE_COUNT)
				line.insn.increaseCount();
		} else {
			final int opcode = cpu.nextOpcode();

			insnDelay = Decoder.instruction(opcode);
			insnDelay.setInstruction(opcode);
			insnDelay.interpret(opcode, true);
			if (ENABLE_INSN_EXECUTE_COUNT)
				insnDelay.increaseCount();
		}

		cpu.nextPc();
	}

	public void step() {
		interpret();
	}

	public void psxException(final ExcCode e, final int inst, final boolean delay) {
		System.err.println(e);
		// if (e == ExcCode.TRAP) {
		// return;
		// } else if (e == ExcCode.SYSCALL) {
		// System.err.println("SYSCALL");
		// return;
		// }

		// switch (e.getCode()) {
		// case SYSCALL:
		// final int code = ((e.getInstruction() >> 6) & 0xFFFFF);
		// cpu.pc = 0x80000000 | code;
		// System.err.println(Integer.toHexString(code));
		// break;
		// }

		// psxRegs.CP0.n.Cause &= ~0x7f;
		// psxRegs.CP0.n.Cause |= code;
		//
		// // // Set the EPC & PC
		// if (delay) {
		// // psxRegs.CP0.n.Cause|= 0x80000000;
		// cpu.npc = cpu.pc - 4;
		// } else {
		// cpu.npc = cpu.pc;
		// }
		//
		// if (cpu.status.isBev()) {
		// cpu.pc = 0xbfc00180;
		// } else {
		// cpu.pc = 0x80000080;
		// }
		//
		// // Set the Status
		// cpu.status.setRawValue((cpu.status.getRawValue() & ~0x3f) | (cpu.status.getRawValue() & 0xf) << 2);
		//
		// final int call = (int) (cpu.gpr[GprState.T1] & 0xff);
		// switch (cpu.pc & 0x1fffff) {
		// case 0xa0:
		// // if (biosA0[call]){
		// // biosA0[call]();
		// // }
		// break;
		// case 0xb0:
		// // if (biosB0[call])
		// // biosB0[call]();
		// break;
		// case 0xc0:
		//
		// // if (biosC0[call])
		// // biosC0[call]();
		// break;
		// }
	}
}
