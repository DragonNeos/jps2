package com.jps2.core.cpu.registers;

/**
 * <pre>
 * CU:4;               // Bits 28-31: Co-processor Usable flag
 * unused3:1;
 * FR:1;               // (?)
 * unused2:2;
 * DEV:1;              // Bit 23: if set, use bootstrap for perf/debug exceptions
 * BEV:1;              // Bit 22: if set, use bootstrap for TLB/general exceptions
 * unused1:3;
 *  CH:1;               // Bit 18: Status of most recent cache instruction (set for hit, clear for miss)
 * _EDI:1;             // Bit 17: Interrupt Enable (set enables ints in all modes, clear enables ints in kernel mode only)
 * EIE:1;              // Bit 16: IE bit enabler.  When cleared, ints are disabled regardless of IE status.
 * IM:8;               // Bits 10-15: Interrupt mask (bits 12,13,14 are unused)
 * unused0:3;
 * KSU:2;              // Bits 3-4: Kernel [clear] / Supervisor [set] mode
 *  ERL:1;              // Bit 2: Error level, set on Resetm NMI, perf/debug exceptions.
 * EXL:1;              // Bit 1: Exception Level, set on any exception not covered by ERL.
 * IE:1;               // Bit 0: Interrupt Enable flag.
 * </pre>
 * 
 * @author dyorgio
 */
public class CP0StatusRegister extends CP0Register {
	public boolean getIE() {
		return (value & 0x00000001) != 0;
	}

	public boolean getEXL() {
		return (value & 0x00000002) != 0;
	}

	public boolean getERL() {
		return (value & 0x00000004) != 0;
	}

	public boolean getEIE() {
		return (value & 0x00008000) != 0;
	}
}
