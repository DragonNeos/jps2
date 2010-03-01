package com.jps2.core.cpu.ee;

import com.jps2.core.cpu.Memories;
import com.jps2.core.memory.AbstractMemoryManager;
import com.jps2.core.memory.Memory;

public class EEMemoryManager extends AbstractMemoryManager {

	public EEMemoryManager() {

	}

	/**
	 * <pre>
	 * 0x10000000 - 0x1000ffff hw EE REGs Mirror Address.
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
		// verifica o primeiro bit do endereço
		switch ((address >> 28 & 0xF)) {
			// RAM
			case 0x0:
				if (address >= 0x00100000) {
					if (write && Memories.hwRegistersEE.cpu.isWriteLocked()) {
						return null;
					}
					Memories.memoryRAM.setOffset(0x00100000);
					return Memories.memoryRAM;
				}
				// pode ser REG ,ROM, VUF ou GS
			case 0x1:
				switch ((address >> 24 & 0xF)) {
					// REG
					case 0x0:
						Memories.hwRegistersEE.setOffset(0x10000000);
						return Memories.hwRegistersEE;
					case 0x1:
						logger.info("VUF Memories.memory");
						break;
					case 0x2:
						if (address >= 0x12000000) {
							Memories.memoryGS.setOffset(0x12000000);
							return Memories.memoryGS;
						}
						break;
					case 0xF:
						switch ((address >> 20 & 0xF)) {
							case 0xC:
								if (write) {
									throw new RuntimeException("ReadOnly Memories.memory ROM " + Long.toHexString(address));
								}
								Memories.memoryROM.setOffset(0x1FC00000);
								return Memories.memoryROM;
							case 0x8:
								Memories.hwRegistersIOP.setOffset(0x1F800000);
								return Memories.hwRegistersIOP;
						}
						break;
				}
				break;
			// RAM
			case 0x2:
				if (address >= 0x20100000) {
					if (write && Memories.hwRegistersEE.cpu.isWriteLocked()) {
						return null;
					}
					Memories.memoryRAM.setOffset(0x20100000);
					return Memories.memoryRAM;
				}
				break;
			// RAM
			case 0x3:

				if (address >= 0x30100000) {
					if (write && Memories.hwRegistersEE.cpu.isWriteLocked()) {
						return null;
					}
					Memories.memoryRAM.setOffset(0x30100000);
					return Memories.memoryRAM;
				}
				break;
			// Scratch Pad RAM
			case 0x7:
				if (address >= 0x70000000) {
					Memories.memoryPAD.setOffset(0x70000000);
					return Memories.memoryPAD;
				}
				break;
			// KSEG 2
			case 0x8:
				if (write && Memories.hwRegistersEE.cpu.isWriteLocked()) {
					return null;
				}
				Memories.memoryRAM.setOffset(0x80000000);
				return Memories.memoryRAM;
				// ROM
			case 0x9:
				if (address >= 0x9FC00000) {
					if (write) {
						throw new RuntimeException("ReadOnly Memories.memory ROM " + Long.toHexString(address));
					}
					Memories.memoryROM.setOffset(0x9FC00000);
					return Memories.memoryROM;
				}
				break;
			// KSEG 1
			case 0xA:
				if (write && Memories.hwRegistersEE.cpu.isWriteLocked()) {
					return null;
				}
				Memories.memoryRAM.setOffset(0xA0000000);
				return Memories.memoryRAM;
				// REG ou ROM
			case 0xB:
				switch ((address >> 24 & 0xF)) {
					// EE HW REGISTERS
					case 0x0:
						if (address >= 0xB0000000) {
							Memories.hwRegistersEE.setOffset(0xB0000000);
							return Memories.hwRegistersEE;
						}
						break;
					// VUF
					case 0x1:
						logger.info("VUF Memories.memory");
						break;
					// GS
					case 0x2:
						if (address >= 0xB2000000) {
							Memories.memoryGS.setOffset(0xB2000000);
							return Memories.memoryGS;
						}
						break;
					// ROM | IOP
					case 0xF:
						// ROM
						if (address >= 0xBFC00000) {
							if (write) {
								throw new RuntimeException("ReadOnly Memories.memory ROM " + Long.toHexString(address));
							}
							Memories.memoryROM.setOffset(0xBFC00000);
							return Memories.memoryROM;
							// IOP
						} else
							if (address >= 0xBF800000) {
								Memories.hwRegistersIOP.setOffset(0xBF800000);
								return Memories.hwRegistersIOP;
							}
						break;
				}
				break;
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
