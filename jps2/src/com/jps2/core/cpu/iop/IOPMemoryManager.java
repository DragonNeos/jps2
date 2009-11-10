package com.jps2.core.cpu.iop;

import com.jps2.core.cpu.Memories;
import com.jps2.core.memory.AbstractMemoryManager;
import com.jps2.core.memory.Memory;

public class IOPMemoryManager extends AbstractMemoryManager {

	public IOPMemoryManager() {

	}

	/**
	 * <pre>
	 * 0x1FC00000 - 0x1FFFFFFF ROM BIOS Mirror Address. (not cached)
	 * 0x9FC00000 - 0x9FFFFFFF ROM BIOS Mirror Address. (cached)
	 * 0xBFC00000 - 0xBFFFFFFF ROM BIOS Mirror Address. (not cached)
	 * 0x1F800000 - 0x1F80FFFF IOP Hardware Registers. (not cached)
	 * 0xBF800000 - 0xBF80FFFF IOP Hardware Registers. (not cached)
	 * </pre>
	 */
	@Override
	public Memory getMemoryByAddress(final int address, final boolean write) {
		// verify first bit
		switch ((address >> 28 & 0xF)) {
			// IOP RAM
			case 0x0:
				if (write && Memories.hwRegistersIOP.cpu.isWriteLocked()) {
					return null;
				}
				Memories.memoryIOP.setOffset(0x00000000);
				return Memories.memoryIOP;
				// IOP registers/ROM
			case 0x1:
				switch ((address >> 24 & 0xF)) {
					case 0xF:
						switch ((address >> 20 & 0xF)) {
							case 0x8:
								Memories.hwRegistersIOP.setOffset(0x1F800000);
								return Memories.hwRegistersIOP;
							case 0xC:
							case 0xD:
							case 0xE:
							case 0xF:
								if (write) {
									throw new RuntimeException("ReadOnly memory ROM " + Long.toHexString(address));
								}
								Memories.memoryROM.setOffset(0x1FC00000);
								return Memories.memoryROM;
						}
						break;
				}
				break;
			// IOP RAM
			case 0x8:
				if (write && Memories.hwRegistersIOP.cpu.isWriteLocked()) {
					return null;
				}
				Memories.memoryIOP.setOffset(0x80000000);
				return Memories.memoryIOP;
				// ROM
			case 0x9:
				switch ((address >> 24 & 0xF)) {
					case 0xF:
						switch ((address >> 20 & 0xF)) {
							case 0xC:
							case 0xD:
							case 0xE:
							case 0xF:
								if (write) {
									throw new RuntimeException("ReadOnly memory ROM " + Long.toHexString(address));
								}
								Memories.memoryROM.setOffset(0x9FC00000);
								return Memories.memoryROM;
						}
				}
				break;
			// IOP RAM
			case 0xA:
				if (write && Memories.hwRegistersIOP.cpu.isWriteLocked()) {
					return null;
				}
				Memories.memoryIOP.setOffset(0xA0000000);
				return Memories.memoryIOP;
				// IOP registers/ROM
			case 0xB:
				switch ((address >> 20 & 0xF)) {
					// CDVD
					case 0x4:
						Memories.cdvdRegistersIOP.setOffset(0xbf402000);
						return Memories.cdvdRegistersIOP;
						// IOP
					case 0x8:
						if (address >= 0xBF800000) {
							Memories.hwRegistersIOP.setOffset(0xBF800000);
							return Memories.hwRegistersIOP;
						}
						break;
					// ROM
					case 0xC:
					case 0xD:
					case 0xE:
					case 0xF:
						if (address >= 0xBFC00000) {
							if (write) {
								throw new RuntimeException("ReadOnly memory ROM " + Long.toHexString(address));
							}
							Memories.memoryROM.setOffset(0x1FC00000);
							return Memories.memoryROM;
						}
				}
				break;
			// Sysinfo special registers
			case 0xF:
				if (address >= 0xFFFE0000 && (address <= 0XFFFE0020 || (address >= 0XFFFE0100 && address < 0XFFFE0160))) {
					// int masked_addr = address & ~3;
					// if( masked_addr == 0xfffe0130 || masked_addr ==
					// 0xfffe0140 ||
					// masked_addr == 0xfffe0144)
					Memories.memorySysInfo.setOffset(0xFFFE0000);
					return Memories.memorySysInfo;
				}
		}

		throw new RuntimeException("Invalid memory " + Integer.toHexString(address));
	}
}
