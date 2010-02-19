package com.jps2.core.cpu.ee;

public class EECounter {

	public static final int		EECNT_FUTURE_TARGET		= 0x10000000;
	
	// Set to '3' to double the speed of games like KHII
	public static int			HBLANK_COUNTER_SPEED	= 1;

	public int					count;
	public int					target;
	public int					hold;
	public int					rate;
	public int					interrupt;
	public int					cycleT;
	public final EECounterMode	mode					= new EECounterMode();

	/**
	 * <pre>
	 * // Set to true by the counter when the target has overflowed.
	 * // Flag is set only when OverflowInterrupt is enabled.
	 * OverflowReached:1 bit [11];
	 * 
	 * // Set to true by the counter when the target is reached.
	 * // Flag is set only when TargetInterrupt is enabled.
	 * TargetReached:1  bit [10];
	 * 
	 * // Enables overflow interrupts.
	 * OverflowInterrupt:1 bit [9];
	 * 
	 * // Enables target interrupts.
	 * TargetInterrupt:1 bit [8];
	 * 
	 * // General count enable/status.  If 0, no counting happens.
	 * // This flag is set/unset by the gates.
	 * IsCounting:1 bit [7];
	 * 
	 * // Counter cleared to zero when target reached.
	 * // The PS2 only resets if the TargetInterrupt is enabled - Tested on PS2
	 * ZeroReturn:1 bit [6];
	 * 
	 * // 0 - count when the gate signal is low
	 * // 1 - reset and start counting at the signal's rising edge (h/v blank end)
	 * // 2 - reset and start counting at the signal's falling edge (h/v blank start)
	 * // 3 - reset and start counting at both signal edges
	 * GateMode:2 bits [5-4];
	 * 
	 * // 0 - hblank!  1 - vblank!
	 * // Note: the hblank source type is disabled when ClockSel = 3
	 * GateSource:1 bit [3];
	 * 
	 * // Enables the counter gate (turns counter on/off as according to the
	 * // h/v blank type set in GateType).
	 * EnableGate:1 bit [2];
	 * 
	 * // 0 - BUSCLK
	 * // 1 - 1/16th of BUSCLK
	 * // 2 - 1/256th of BUSCLK
	 * // 3 - External Clock (hblank!)
	 * ClockSource:2 bits [1-0];
	 * </pre>
	 * 
	 * @author dyorgio_develop
	 * 
	 */
	public class EECounterMode {
		public int	value;

		public boolean isCounting() {
			return (value & 0x80) != 0;
		}

		public void setCounting(boolean counting) {
			if (counting) {
				value |= 0x00000080;
			} else {
				value &= 0xFFFFFF7F;
			}
		}

		public int getClockSource() {
			return value & 0x3;
		}

		public int getGateMode() {
			return ((value >> 4) & 0x3);
		}

		public boolean isTargetInterrupt() {
			return (value & 0x100) != 0;
		}

		public boolean isOverflowInterrupt() {
			return (value & 0x200) != 0;
		}

		public boolean isZeroReturn() {
			return (value & 0x40) != 0;
		}

		public void setTargetReached(boolean reached) {
			if (reached) {
				value |= 0x00000400;
			} else {
				value &= 0xFFFFFBFF;
			}
		}

		public void setOverflowReached(boolean reached) {
			if (reached) {
				value |= 0x00000800;
			} else {
				value &= 0xFFFFF7FF;
			}
		}
	}
}
