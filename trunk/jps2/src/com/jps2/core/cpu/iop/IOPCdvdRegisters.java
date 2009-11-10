package com.jps2.core.cpu.iop;

import com.jps2.core.memory.FastMemory;

public class IOPCdvdRegisters extends FastMemory {
	public IOPCdvdRegisters() {
		super("IOP Cdvd Registers", 0xFF);
	}

	@Override
	public int read8(int address) {
		address &= offset;
		switch (address) {
			case 0x04:
				return 0;// cdvdRead04();
			case 0x05:
				return 0;// cdvdRead05();
			case 0x06:
				return 0;// cdvdRead06();
			case 0x07:
				return 0;// cdvdRead07();
			case 0x08:
				return 0;// cdvdRead08();
			case 0x0A:
				return 0;// cdvdRead0A();
			case 0x0B:
				return 0;// cdvdRead0B();
			case 0x0C:
				return 0;// cdvdRead0C();
			case 0x0D:
				return 0;// cdvdRead0D();
			case 0x0E:
				return 0;// cdvdRead0E();
			case 0x0F:
				return 0;// cdvdRead0F();
			case 0x13:
				return 0;// cdvdRead13();
			case 0x15:
				return 0;// cdvdRead15();
			case 0x16:
				return 0;// cdvdRead16();
			case 0x17:
				return 0;// cdvdRead17();
			case 0x18:
				return 0;// cdvdRead18();
			case 0x20:
				return 0;// cdvdRead20();
			case 0x21:
				return 0;// cdvdRead21();
			case 0x22:
				return 0;// cdvdRead22();
			case 0x23:
				return 0;// cdvdRead23();
			case 0x24:
				return 0;// cdvdRead24();
			case 0x28:
				return 0;// cdvdRead28();
			case 0x29:
				return 0;// cdvdRead29();
			case 0x2A:
				return 0;// cdvdRead2A();
			case 0x2B:
				return 0;// cdvdRead2B();
			case 0x2C:
				return 0;// cdvdRead2C();
			case 0x30:
				return 0;// cdvdRead30();
			case 0x31:
				return 0;// cdvdRead31();
			case 0x32:
				return 0;// cdvdRead32();
			case 0x33:
				return 0;// cdvdRead33();
			case 0x34:
				return 0;// cdvdRead34();
			case 0x38:
				return 0;// cdvdRead38();
			case 0x39:
				return 0;// cdvdRead39();
			case 0x3A:
				return 0;// cdvdRead3A();
			default:
		}
		return super.read8(address);
	}
}
