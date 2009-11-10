package com.jps2.core.cpu.ee.state;

import org.apache.log4j.Logger;

import com.jps2.core.cpu.ExcCode;
import com.jps2.core.cpu.ee.EECounter;
import com.jps2.core.cpu.ee.EEHardwareRegisters;
import com.jps2.core.cpu.ee.EESyncCounter;
import com.jps2.core.cpu.registers.CP0Register;
import com.jps2.core.cpu.registers.CP0StatusRegister;

public final class CpuState extends SauState {

	private static final Logger	logger			= Logger.getLogger(CpuState.class);

	final EECounter[]			counters;
	final EESyncCounter			hSyncCounter;
	final EESyncCounter			vSyncCounter;

	int							nextsCounter;										// records
																					// the
																					// cpuRegs.cycle
																					// value
																					// of
																					// the
																					// last
																					// call
																					// to
	// rcntUpdate()
	int							nextCounter;										// delta
																					// from
																					// nextsCounter,
																					// in
																					// cycles,
																					// until
																					// the
																					// next
	// rcntUpdate()
	int[]						eCycle;
	int[]						sCycle;											// for
																					// internal
																					// counters

	int							lastCp0Cycle	= 0;
	final int[]					lastPERFCycle	= new int[2];

	final PERFregs				perfRegs;

	final CP0StatusRegister		statusReg;

	@Override
	public void reset() {
		resetAll();
		cp0[CP0_CONFIG].value = 0x440;
		statusReg.value = 0x70400004; // 0x10900000 <-- wrong; // COP0
		// enabled |
		// BEV = 1 | TS = 1
		cp0[CP0_PRID].value = 0x00002e20; // PRevID = Revision ID, same as
		// R5900
	}

	public CpuState() {
		cp0 = new CP0Register[32];
		statusReg = new CP0StatusRegister();
		for (int i = 0; i < 32; i++) {
			if (i == CP0_STATUS) {
				cp0[i] = statusReg;
			} else {
				cp0[i] = new CP0Register();
			}
		}
		eCycle = new int[32];
		sCycle = new int[32];
		counters = new EECounter[4];
		for (int i = 0; i < counters.length; i++) {
			counters[i] = new EECounter();
		}
		hSyncCounter = new EESyncCounter();
		vSyncCounter = new EESyncCounter();
		perfRegs = new PERFregs();
		reset();
	}

	public final boolean isWriteLocked() {
		return (statusReg.value & 0x10000) != 0;
	}

	public final void doDI(final boolean delay) {
		if (statusReg.getEDI() || statusReg.getEXL() || statusReg.getERL() || (statusReg.getKSU() == 0)) {
			statusReg.setEIE(false);
			updateCP0Status(delay);
		}
	}

	public final void doEI(final boolean delay) {
		if (statusReg.getEDI() || statusReg.getEXL() || statusReg.getERL() || (statusReg.getKSU() == 0)) {
			statusReg.setEIE(true);
			updateCP0Status(delay);
		}
	}

	public final void doMFC0(final int rt, final int c0dr) {
		if (rt != 0) {
			gpr[rt].write32(cp0[c0dr].value);
		}
	}

	public final void doMTC0(final int rd, final int rt, final int imm, final boolean delay) {
		switch (rd) {
			case 9:
				lastCp0Cycle = cycle;
				cp0[9].value = gpr[rt].read32();
				break;

			case 12:
				writeCP0Status(gpr[rt].read32(), delay);
				break;

			case 24:
				System.err.println(String.format("MTC0 Breakpoint debug Registers code = %x", imm));
				break;

			case 25:
				switch (imm) {
					case 0: // MTPS [LSB is clear]
						// Updates PCRs and sets the PCCR.
						updatePCCR();
						perfRegs.pccr.val = gpr[rt].read32();
						break;

					case 1: // MTPC [LSB is set] - set PCR0
						perfRegs.pcr0 = gpr[rt].read32();
						lastPERFCycle[0] = cycle;
						break;

					case 3: // MTPC [LSB is set] - set PCR0
						perfRegs.pcr1 = gpr[rt].read32();
						lastPERFCycle[1] = cycle;
						break;
				}
				break;

			default:
				cp0[rd].value = gpr[rt].read32();
				break;
		}

	}

	final void writeCP0Status(final int value, final boolean delay) {
		cp0[CP0_STATUS].value = value;
		updateCP0Status(delay);
	}

	final void updateCP0Status(final boolean delay) {
		cpuTestHwInts(delay);
	}

	static boolean shouldPerfCountEvent(final int evt) {
		switch (evt) {
			// This is a rough table of actions for various PCR modes. Some of
			// these
			// can be implemented more accurately later. Others (WBBs in
			// particular)
			// probably cannot without some severe complications.

			// left sides are PCR0 / right sides are PCR1

			case 1: // cpu cycle counter.
			case 2: // single/dual instruction issued
			case 3: // Branch issued / Branch mispredicated
			case 12: // Instruction completed
			case 13: // non-delayslot instruction completed
			case 14: // COP2/COP1 instruction complete
			case 15: // Load/Store completed
				return true;
			case 4: // BTAC/TLB miss
			case 5: // ITLB/DTLB miss
			case 6: // Data/Instruction cache miss
			case 7: // Access to DTLB / WBB single request fail
			case 8: // Non-blocking load / WBB burst request fail
			case 9:
			case 10:
			case 11: // CPU address bus busy / CPU data bus busy
				return false;
		}

		return false;
	}

	final void updatePCCR() {
		if ((perfRegs.pccr.val & 0xf) != 0) {
			// ----------------------------------
			// Update Performance Counter 0
			// ----------------------------------
			if (shouldPerfCountEvent(perfRegs.pccr.getEvent0())) {
				int incr = cycle - lastPERFCycle[0];
				if (incr == 0)
					incr++;

				// use prev/XOR method for one-time exceptions (but likely less
				// correct)
				// u32 prev = cpuRegs.PERF.n.pcr0;
				perfRegs.pcr0 += incr;
				lastPERFCycle[0] = cycle;
			}
		}
		if (perfRegs.pccr.getU1()) {
			// ----------------------------------
			// Update Performance Counter 1
			// ----------------------------------
			if (shouldPerfCountEvent(perfRegs.pccr.getEvent1())) {
				int incr = cycle - lastPERFCycle[1];
				if (incr == 0)
					incr++;

				perfRegs.pcr1 += incr;
				lastPERFCycle[1] = cycle;
			}
		}
	}

	public final void cpuTestINTCInts() {
		if ((interrupt & (1 << 30)) == 0) {
			if (cpuIntsEnabled()) {
				if ((processor.memory.read32(EEHardwareRegisters.INTC_STAT) & processor.memory.read32(EEHardwareRegisters.INTC_MASK)) != 0) {
					interrupt |= 1 << 30;
					sCycle[30] = cycle;
					eCycle[30] = 4; // Needs to be 4 to account for bus
					// delays/pipelines

					// only set the next branch delta if the exception won't be
					// handled
					// for the current branch...
					// if (!eeEventTestIsActive)
					// cpuSetNextBranchDelta(4);
					// else if (psxCycleEE > 0) {
					// psxBreak += psxCycleEE; // record the number of cycles
					// // the IOP
					// // didn't run.
					// psxCycleEE = 0;
					// }
				}
			}
		}
	}

	final boolean cpuIntsEnabled() {
		return statusReg.getEIE() && statusReg.getIE() && !statusReg.getEXL() && !statusReg.getERL();
	}

	public final void cpuTestDMACInts() {
		if ((interrupt & (1 << 31)) == 0) {
			if ((statusReg.value & 0x10807) == 0x10801) {
				if (((processor.memory.read16(0x1000e012) & processor.memory.read16(0x1000e010)) != 0) && ((processor.memory.read16(0x1000e010) & 0x8000) != 0)) {
					interrupt |= 1 << 31;
					sCycle[31] = cycle;
					eCycle[31] = 4; // Needs to be 4 to account for bus
					// delays/pipelines etc

					// only set the next branch delta if the exception won't be
					// handled for
					// the current branch...
					// if (!eeEventTestIsActive) {
					// cpuSetNextBranchDelta(4);
					// } else if (psxCycleEE > 0) {
					// psxBreak += psxCycleEE; // record the number of cycles
					// // the IOP
					//
					// // didn't run.
					// psxCycleEE = 0;
					// }
				}
			}
		}
	}

	final void cpuTestTIMRInts(final boolean delay) {
		if ((statusReg.value & 0x10007) == 0x10001) {
			updatePCCR();
			cpuTestTIMR(delay);
		}
	}

	final void cpuTestTIMR(final boolean delay) {
		cp0[CP0_COUNT].value += cycle - lastCp0Cycle;
		lastCp0Cycle = cycle;

		// fixme: this looks like a hack to make up for the fact that the TIMR
		// doesn't yet have a proper mechanism for setting itself up on a
		// nextBranchCycle.
		// A proper fix would schedule the TIMR to trigger at a specific cycle
		// anytime
		// the Count or Compare registers are modified.
		if (((statusReg.value & 0x8000) != 0) && cp0[CP0_COUNT].value >= cp0[CP0_COMPARE].value && cp0[CP0_COUNT].value < cp0[CP0_COMPARE].value + 1000) {
			logger.error(String.format("timr intr: %x, %x", cp0[CP0_COUNT].value, cp0[CP0_COMPARE].value));
			processor.processException(ExcCode.MACHINE_CHECK, 0x808000, delay);
		}
	}

	public final void cpuTestHwInts(final boolean delay) {
		cpuTestINTCInts();
		cpuTestDMACInts();
		cpuTestTIMRInts(delay);
	}

	private final void rcntInit() {
		int i;

		for (i = 0; i < 4; i++) {
			counters[i].rate = 2;
			counters[i].target = 0xffff;
		}
		counters[0].interrupt = 9;
		counters[1].interrupt = 10;
		counters[2].interrupt = 11;
		counters[3].interrupt = 12;

		hSyncCounter.mode = EESyncCounter.MODE_HRENDER;
		hSyncCounter.sCycle = cycle;
		vSyncCounter.mode = EESyncCounter.MODE_VRENDER;
		vSyncCounter.sCycle = cycle;

		updateVSyncRate();

		for (i = 0; i < 4; i++) {
			rcntReset(i);
		}
		cpuRcntSet();
	}

	private final void cpuRcntSet() {
		int i;

		nextsCounter = cycle;
		nextCounter = vSyncCounter.cycleT - (cycle - vSyncCounter.sCycle);

		for (i = 0; i < 4; i++) {
			rcntSet(i);
		}

		// sanity check!
		if (nextCounter < 0) {
			nextCounter = 0;
		}
	}

	// private static final String limiterMsg =
	// "Framelimiter rate updated (UpdateVSyncRate): %d.%d fps";

	private final int updateVSyncRate() {

		// TODO - Dyorgio, implement for support video mode change
		// // fixme - According to some docs, progressive-scan modes actually
		// refresh slower than
		// // interlaced modes. But I can't fathom how, since the refresh rate
		// is a function of
		// // the television and all the docs I found on TVs made no indication
		// that they ever
		// // run anything except their native refresh rate.
		//
		// //#define VBLANK_NTSC ((Config.PsxType & 2) ? 59.94 : 59.82) //59.94
		// is more precise
		// //#define VBLANK_PAL ((Config.PsxType & 2) ? 50.00 : 49.76)
		//
		// if( gsRegionMode == Region_PAL )
		// {
		// if( vSyncInfo.Framerate != FRAMERATE_PAL )
		// vSyncInfoCalc( &vSyncInfo, FRAMERATE_PAL, SCANLINES_TOTAL_PAL );
		// }
		// else
		// {
		// if( vSyncInfo.Framerate != FRAMERATE_NTSC )
		// vSyncInfoCalc( &vSyncInfo, FRAMERATE_NTSC, SCANLINES_TOTAL_NTSC );
		// }
		//
		// hSyncCounter.cycleT = vSyncInfo.hRender; // Amount of cycles before
		// the counter will be updated
		// vSyncCounter.cycleT = vSyncInfo.Render; // Amount of cycles before
		// the counter will be updated
		//
		// if( EmuConfig.Video.EnableFrameLimiting && (EmuConfig.Video.FpsLimit
		// > 0) )
		// {
		// final s64 ticks = GetTickFrequency() / EmuConfig.Video.FpsLimit;
		// if( m_iTicks != ticks )
		// {
		// m_iTicks = ticks;
		// gsOnModeChanged( vSyncInfo.Framerate, m_iTicks );
		// Console.Status( limiterMsg, EmuConfig.Video.FpsLimit, 0 );
		// }
		// }
		// else
		// {
		// final s64 ticks = (GetTickFrequency() * 50) / vSyncInfo.Framerate;
		// if( m_iTicks != ticks )
		// {
		// m_iTicks = ticks;
		// gsOnModeChanged( vSyncInfo.Framerate, m_iTicks );
		// Console.Status( limiterMsg, vSyncInfo.Framerate/50,
		// (vSyncInfo.Framerate*2)%100 );
		// }
		// }
		//
		// m_iStart = GetCPUTicks();
		// cpuRcntSet();
		//
		// return (u32)m_iTicks;
		return 0;
	}

	private final void rcntReset(final int index) {
		counters[index].count = 0;
		counters[index].cycleT = cycle;
	}

	public final int rcntRcount(final int index) {
		int ret;

		// only count if the counter is turned on (0x80) and is not an hsync
		// gate (!0x03)
		if (counters[index].mode.isCounting() && (counters[index].mode.getClockSource() != 0x3)) {
			ret = counters[index].count + ((cycle - counters[index].cycleT) / counters[index].rate);
		} else {
			ret = counters[index].count;
		}

		// Spams the Console.
		// logger.debug(String.format("EE Counter[%d] readCount32 = %x", index,
		// ret));
		return ret;
	}

	public final void rcntWcount(final int index, final int value) {
		logger.debug(String.format("EE Counter[%d] writeCount = %x,   oldcount=%x, target=%x", index, value, counters[index].count, counters[index].target));

		counters[index].count = value & 0xffff;

		// reset the target, and make sure we don't get a premature target.
		counters[index].target &= 0xffff;
		if (counters[index].count > counters[index].target)
			counters[index].target |= EECounter.EECNT_FUTURE_TARGET;

		// re-calculate the start cycle of the counter based on elapsed time
		// since the last counter update:
		if (counters[index].mode.isCounting()) {
			if (counters[index].mode.getClockSource() != 0x3) {
				int change = cycle - counters[index].cycleT;
				if (change > 0) {
					change -= (change / counters[index].rate) * counters[index].rate;
					counters[index].cycleT = cycle - change;
				}
			}
		} else {
			counters[index].cycleT = cycle;
		}

		rcntSet(index);
	}

	private final void rcntSet(final int cntidx) {

		final EECounter counter = counters[cntidx];

		// Stopped or special hsync gate?
		if (!counter.mode.isCounting() || (counter.mode.getClockSource() == 0x3)) {
			return;
		}

		// check for special cases where the overflow or target has just passed
		// (we probably missed it because we're doing/checking other things)
		if (counter.count > 0x10000 || counter.count > counter.target) {
			nextCounter = 4;
			return;
		}

		// nextCounter is relative to the cpuRegs.cycle when rcntUpdate() was
		// last called.
		// However, the current _rcntSet could be called at any cycle count, so
		// we need to take
		// that into account. Adding the difference from that cycle count to the
		// current one
		// will do the trick!
		int c = ((0x10000 - counter.count) * counter.rate) - (cycle - counter.cycleT);
		// adjust for time passed since last
		// rcntUpdate();
		c += cycle - nextsCounter;
		if (c < nextCounter) {
			nextCounter = c;
			// Need to update on counter
			// resets/target changes
			// cpuSetNextBranch(nextsCounter, nextCounter);
		}

		// Ignore target diff if target is currently disabled.
		// (the overflow is all we care about since it goes first, and then the
		// target will be turned on afterward, and handled in the next event
		// test).
		if ((counter.target & EECounter.EECNT_FUTURE_TARGET) != 0) {
			return;
		} else {
			c = ((counter.target - counter.count) * counter.rate) - (cycle - counter.cycleT);
			// adjust for time passed since
			// last rcntUpdate();
			c += cycle - nextsCounter;
			if (c < nextCounter) {
				nextCounter = c;
				// Need to update on counter
				// resets/target changes
				// cpuSetNextBranch(nextsCounter, nextCounter);
			}
		}
	}

	public final void doTLBR() {

	}

	public final void doTLBWI() {

	}

	public final void doTLBWR() {

	}

	public final void doTLBP() {

	}

	public final void doWAIT() {

	}

	public final void doDERET() {

	}

	public final void doCACHE(final int base, final int opcode, final int offset) {
		System.err.println(">>>>>>>>>>>>>>>>>CACHE<<<<<<<<<<<<<<<<<<<");
	}

	static final class PERFregs {

		// pccr details
		// u32 pad0:1; // LSB should always be zero (or undefined)
		// u32 EXL0:1; // enable PCR0 during Level 1 exception handling
		// u32 K0:1; // enable PCR0 during Kernel Mode execution
		// u32 S0:1; // enable PCR0 during Supervisor mode execution
		// u32 U0:1; // enable PCR0 during User-mode execution
		// u32 Event0:5; // PCR0 event counter (all values except 1 ignored at
		// this time)
		//
		// u32 pad1:1; // more zero/undefined padding [bit 10]
		//
		// u32 EXL1:1; // enable PCR1 during Level 1 exception handling
		// u32 K1:1; // enable PCR1 during Kernel Mode execution
		// u32 S1:1; // enable PCR1 during Supervisor mode execution
		// u32 U1:1; // enable PCR1 during User-mode execution
		// u32 Event1:5; // PCR1 event counter (all values except 1 ignored at
		// this time)
		//
		// u32 Reserved:11;
		// u32 CTE:1; // Counter enable bit, no counting if set to zero.
		//
		final PCCR	pccr	= new PCCR();
		int			pcr0, pcr1, pad;
		final int[]	r		= new int[4];

		static final class PCCR {
			int	val;

			public int getEvent0() {
				return (val >> 22) & 0x1F;
			}

			public int getEvent1() {
				return (val >> 12) & 0x1F;
			}

			public boolean getU1() {
				return (val & 0x20000) != 0;
			}
		}
	}

}
