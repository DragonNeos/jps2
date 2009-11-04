package com.jps2.core.cpu.iop.state;

import com.jps2.core.cpu.Counter;
import com.jps2.core.cpu.iop.IOPHardwareRegisters;
import com.jps2.core.cpu.registers.CP0Register;

public class CpuState extends BcuState {
	Counter[] counters;

	public int interrupt = 0;

	@Override
	void reset() {
		resetAll();
		cp0[CP0_STATUS].value = 0x10900000; // COP0 enabled | BEV = 1 | TS = 1
		cp0[CP0_PRID].value = 0x0000001f;
		for (int i = 0; i < counters.length; i++) {
			counters[i].reset(cycle);
		}
	}

	public CpuState() {
		cp0 = new CP0Register[32];
		for (int i = 0; i < cp0.length; i++) {
			cp0[i] = new CP0Register();
		}
		counters = new Counter[8];
		for (int i = 0; i < counters.length; i++) {
			counters[i] = new Counter();
		}
		reset();
	}

	public int getPc() {
		return pc;
	}

	public Counter getCounter(final int index) {
		return counters[index];
	}

	public void iopTestIntc() {
		if (processor.memory.read32(0x1F801078) == 0)
			return;
		if ((processor.memory.read32(0x1F801070) & processor.memory
				.read32(0x1FB01074)) == 0)
			return;

		// if( !eeEventTestIsActive )
		// {
		// // An iop exception has occurred while the EE is running code.
		// // Inform the EE to branch so the IOP can handle it promptly:
		//
		// cpuSetNextBranchDelta( 16 );
		// iopBranchAction = true;
		// //Console.Error( "** IOP Needs an EE EventText, kthx **  %d",
		// psxCycleEE );
		//
		// // Note: No need to set the iop's branch delta here, since the EE
		// // will run an IOP branch test regardless.
		// }
		// else if( !iopEventTestIsActive )
		// psxSetNextBranchDelta( 2 );
	}

	void psxRcntInit() {
		int i;

		for (i = 0; i < counters.length; i++) {
			counters[i].reset(cycle);
		}

		for (i = 0; i < 3; i++) {
			counters[i].rate = 1;
			counters[i].mode |= 0x0400;
			counters[i].target = IOPHardwareRegisters.IOPCNT_FUTURE_TARGET;
		}
		for (i = 3; i < 6; i++) {
			counters[i].rate = 1;
			counters[i].mode |= 0x0400;
			counters[i].target = IOPHardwareRegisters.IOPCNT_FUTURE_TARGET;
		}

		counters[0].interrupt = 0x10;
		counters[1].interrupt = 0x20;
		counters[2].interrupt = 0x40;

		counters[3].interrupt = 0x04000;
		counters[4].interrupt = 0x08000;
		counters[5].interrupt = 0x10000;
		//
		// if (SPU2async != NULL) {
		// counters[6].rate = 768 * 12;
		// counters[6].cycleT = counters[6].rate;
		// counters[6].mode = 0x8;
		// }
		//
		// if (USBasync != NULL) {
		// counters[7].rate = PSXCLK / 1000;
		// counters[7].cycleT = counters[7].rate;
		// counters[7].mode = 0x8;
		// }

		for (i = 0; i < 8; i++) {
			counters[i].cycleT = cycle;
		}
	}

	public short psxRcntRcount16(final int index) {
		final Counter counter = counters[index];
		if ((counter.mode & 0x1000000) != 0) {
			return (short) counter.count;
		}
		return (short) (counter.count + ((cycle - counter.cycleT) / counter.rate));
	}

	public int psxRcntRcount32(final int index) {
		final Counter counter = counters[index];
		if ((counter.mode & 0x1000000) != 0) {
			return (int) counter.count;
		}
		return (int) (counter.count + ((cycle - counter.cycleT) / counter.rate));
	}

	public final boolean isWriteLocked() {
		return (cp0[CP0_STATUS].value & 0x10000) != 0;
	}

	public final void doRFE() {
		cp0[CP0_STATUS].value = (cp0[CP0_STATUS].value & 0xfffffff0)
				| ((cp0[CP0_STATUS].value & 0x3c) >> 2);
	}

	public final void doMFC0(final int rt, final int c0dr) {
		if (rt != 0) {
			gpr[rt].write32(cp0[c0dr].value);
		}
	}

	public final void doMTC0(final int rt, final int c0dr) {
		cp0[c0dr].value = gpr[rt].read32();
	}

	public final void doCFC0(final int rt, final int c0dr) {
		if (rt != 0) {
			gpr[rt].write32(cp0[c0dr].value);
		}
	}

	public final void doCTC0(final int rt, final int c0dr) {
		cp0[c0dr].value = gpr[rt].read32();
	}
}
