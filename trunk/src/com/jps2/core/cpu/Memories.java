package com.jps2.core.cpu;

import org.apache.log4j.Logger;

import com.jps2.core.cpu.iop.IOPHardwareRegisters;
import com.jps2.core.memory.FastMemory;
import com.jps2.util.Bios;

public abstract class Memories {

	public static final Logger	   logger	       = Logger.getLogger(Memories.class);

	public static final FastMemory	memoryRAM	   = new FastMemory("RAM", 0x01FFFFFF);
	public static final FastMemory	memoryREG	   = new FastMemory("REG", 0x0000FFFF);
	public static final FastMemory	memoryPAD	   = new FastMemory("PAD", 0x0000FFFF);
	public static final FastMemory	memoryROM	   = new FastMemory("ROM", 0x003FFFFF);
	public static final IOPHardwareRegisters	hwRegistersIOP	= new IOPHardwareRegisters();
	public static final FastMemory	memoryIOP	   = new FastMemory("IOP RAM", 0x001FFFFF);
	public static final FastMemory	memorySysInfo	= new FastMemory("SysInfo", 0x160);

	private Memories() {
	}

	public static final void alloc() {
		logger.info("Allocating memories.");
		memoryRAM.allocate();
		logger.info("RAM...OK.");
		memoryREG.allocate();
		logger.info("REG...OK.");
		memoryPAD.allocate();
		logger.info("PAD...OK.");
		memoryROM.allocate();
		logger.info("ROM...OK.");
		hwRegistersIOP.allocate();
		memoryIOP.allocate();
		logger.info("IOP...OK.");
		memorySysInfo.allocate();

		logger.info("Loading bios ROM.");
		Memories.memoryROM.setOffset(0xBFC00000);
		Bios.load(Memories.memoryROM);
		logger.info("Bios..OK.");
		// cache config
		Memories.memorySysInfo.write32(0x0130, 0x1edd8); // CACHE_CONFIG
		// memoryManager.write32(0x1f801450, 0x8); // PS1 mode
	}

	public static final void clear() {
	}
}
