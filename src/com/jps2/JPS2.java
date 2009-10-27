package com.jps2;

import java.util.logging.Logger;

import javax.swing.UIManager;

import com.jps2.core.SIO;
import com.jps2.core.cpu.r3000a.R3000a;
import com.jps2.core.cpu.r5900.R5900;
import com.jps2.core.memory.FastMemory;
import com.jps2.core.memory.IOPMemory;
import com.jps2.core.memory.Memory;
import com.jps2.core.memory.MemoryManager;
import com.jps2.plugins.PluginManager;
import com.jps2.util.Bios;


public class JPS2 {

	public static Logger	     log	       = Logger.getLogger("cpu");
	private final FastMemory	 memoryRAM	   = new FastMemory("RAM", 0x01FFFFFF);
	private final FastMemory	 memoryREG	   = new FastMemory("REG", 0x0000FFFF);
	private final FastMemory	 memoryPAD	   = new FastMemory("PAD", 0x0000FFFF);
	private final FastMemory	 memoryROM	   = new FastMemory("ROM", 0x003FFFFF);
	 private final IOPMemory memoryIOP = new IOPMemory();
//	private final FastMemory	 memoryIOP	   = new FastMemory("IOP", 0x0000FFFF);
	private final FastMemory	 memorySysInfo	= new FastMemory("SysInfo", 0x160);

	private static SIO	         sio	       = new SIO();

	private static MemoryManager	memoryManager;

	public JPS2() {
		// aloca as memórias
		System.err.print("Alocando memória...");
		memoryRAM.allocate();
		memoryREG.allocate();
		memoryPAD.allocate();
		memoryROM.allocate();
		memoryIOP.allocate();
		memorySysInfo.allocate();
		System.err.println("memória alocada!");
		// carrega a Bios
		System.err.print("Carregando BIOS na memoria ROM...");
		memoryROM.setOffset(0xBFC00000);
		Bios.load(memoryROM);
		// marca a memoria como read-only
		System.err.println("BIOS carregada.");
		
//		 System.err.print("Carregando BIOS na memoria RAM...");
//		 memoryRAM.copy(memoryROM, 0, 0);
//		 System.err.println("BIOS carregada.");
		
		PluginManager.initialize();
		memoryManager = new MemoryManager() {

			/**
			 * <pre>
			 * 0x10000000 - 0x1000ffff REG Mirror Address.
			 * 0x11000000 - 0x1100ffff VUF Mirror Address.
			 * 0x12000000 - 0x1200ffff GS  Mirror Address.
			 * 0x00100000 - 0x01ffffff RAM Mirror Address. (cached)
			 * 0x20100000 - 0x21ffffff RAM Mirror Address. (not cached)
			 * 0x30100000 - 0x31ffffff RAM Mirror Address. (not cached &amp; accelerated)
			 * 0x1FC00000 - 0x1FFFFFFF ROM BIOS Mirror Address. (not cached)
			 * 0x9FC00000 - 0x9FFFFFFF ROM BIOS Mirror Address. (cached)
			 * 0xBFC00000 - 0xBFFFFFFF ROM BIOS Mirror Address. (not cached)
			 * 0x1F800000 - 0x1F80FFFF IOP Mirror Address. (not cached)
			 * 0xBF800000 - 0xBF80FFFF IOP Mirror Address. (not cached)
			 * 0x70000000 - 0x70003fff Scratch Pad RAM Address.
			 * </pre>
			 */
			@Override
			public Memory getMemoryByAddress(final int address, final boolean write) {
				if (address == 0) {
					System.err.println("reset");
				}

				// verifica o primeiro bit do endereço
				switch ((address >> 28 & 0xF)) {
				// RAM
				case 0x0:
					if (address >= 0x00100000) {
						memoryRAM.setOffset(0x00100000);
						return memoryRAM;
					} else {
						memoryRAM.setOffset(0);
						return memoryRAM;
					}
					// pode ser REG ,ROM, VUF ou GS
				case 0x1:
					switch ((address >> 24 & 0xF)) {
					// REG
					case 0x0:
						System.err.println("REG memory");
						memoryREG.setOffset(0x10000000);
						return memoryREG;
					case 0x1:
						System.err.println("VUF memory");
						break;
					case 0x2:
						System.err.println("GS memory");
						break;
					case 0xF:
						switch ((address >> 20 & 0xF)) {
						case 0xC:
							if (write) {
								throw new RuntimeException("ReadOnly memory ROM " + Long.toHexString(address));
							}
							memoryROM.setOffset(0x1FC00000);
							return memoryROM;
						case 0x8:
							memoryIOP.setOffset(0x1F800000);
							return memoryIOP;
						case 0xA:// TODO - hack
							memoryRAM.setOffset(0x1FA00000);
							return memoryRAM;
						}
						break;
					}
					break;
				// RAM
				case 0x2:
					if (address >= 0x20100000) {
						memoryRAM.setOffset(0x20100000);
						return memoryRAM;
					}
					break;
				// RAM
				case 0x3:

					if (address >= 0x30100000) {
						memoryRAM.setOffset(0x30100000);
						return memoryRAM;
					}
					break;
				// Scratch Pad RAM
				case 0x7:
					if (address >= 0x70000000) {
						System.err.println("PAD");
						memoryPAD.setOffset(0x70000000);
						return memoryPAD;
					}
					break;
				// KSEG 2
				case 0x8:
					memoryRAM.setOffset(0x80000000);
					return memoryRAM;
					// ROM
				case 0x9:
					if (address >= 0x9FC00000) {
						if (write) {
							throw new RuntimeException("ReadOnly memory ROM " + Long.toHexString(address));
						}
						memoryROM.setOffset(0x9FC00000);
						return memoryROM;
					}
					break;
				// KSEG 1
				case 0xA:
					memoryRAM.setOffset(0xA0000000);
					return memoryRAM;
					// REG ou ROM
				case 0xB:
					switch ((address >> 20 & 0xF)) {
					// REG
					case 0x0:
						if (address >= 0xB0000000) {
							System.err.println("REG");
							memoryREG.setOffset(0xB0000000);
							return memoryREG;
						}
						break;
					// IOP
					case 0x8:
						if (address >= 0xBF800000) {
							memoryIOP.setOffset(0xBF800000);
							return memoryIOP;
						}
						break;
					// ROM
					case 0xC:
						if (address >= 0xBFC00000) {
							if (address == 0xBFC10000){
								System.err.println("IOP START");
							}
							if (write) {
								throw new RuntimeException("ReadOnly memory ROM " + Long.toHexString(address));
							}
							memoryROM.setOffset(0xBFC00000);
						}
						return memoryROM;
					}
					break;
				case 0xF:
					if (address >= 0xFFFE0000 && (address <= 0XFFFE0020 || (address >= 0XFFFE0100 && address < 0XFFFE0160))) {
						// int masked_addr = address & ~3;
						// if( masked_addr == 0xfffe0130 || masked_addr == 0xfffe0140 || masked_addr == 0xfffe0144)
						memorySysInfo.setOffset(0xFFFE0000);
						return memorySysInfo;
					}
				}

				throw new RuntimeException("Invalid memory " + Integer.toHexString(address));
			}
		};
		// bfc4d3fc
		memoryManager.write32(0xFFFE0130, 0x1edd8); // CACHE_CONFIG
		// memoryManager.write32(0x1f801450, 0x8); // PS1 mode
		final R3000a r3000 = R3000a.getProcessor();
		final R5900 r5900 = R5900.getProcessor();
		long count = 0;
		long time = System.currentTimeMillis();
		try {
			while (true /* && processor.cpu.pc != 0x00200008 && processor.cpu.pc != 0x00100008 */) {
				// if (count % 8 == 0) {
				r3000.step();
				// }
				// r5900.step();
				count++;
				if (count % 1000000 == 0) {
					final long atualTime = System.currentTimeMillis();
					System.err.println("Count:" + count + " - instruction per second:" + (int) (1000000l / ((atualTime - time) / 1000d)) + " - elapsedTime:" + (atualTime - time) + "ms");
					System.err.println("R5900 last instruction:" + r5900.insn);
					System.err.println("R3000 last instruction:" + r3000.insn);
					time = atualTime;
				}
			}
		} finally {
			System.err.println("Count : " + count);
			// System.err.println("Inst Count : " + processor.getInsnCount());
			System.err.println("R5900 last instruction:" + r5900.insn);
			System.err.println("R3000 last instruction:" + r3000.insn);
		}
	}

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e) {
			e.printStackTrace();
		}

//		MainWindow.getInstance();
		new JPS2();
	}

	public static MemoryManager getMemoryManager() {
		return memoryManager;
	}

	public static SIO getSio() {
		return sio;
	}
}
