package com.jps2.core.cpu;

public enum ExcCode {
	INTERRUPT(0), TLB_MODIFICATION(1), TLB_LOAD(2), TLB_STORE(3), ADDRESS_ERROR_LOAD(4), ADDRESS_ERROR_STORE(5), BUS_ERROR_INST(6), BUS_ERROR_LOAD(7), SYSCALL(8), BREAKPOINT(9), RESERVED_INST(
			10), COPROCESSOR_UNUSABLE(11), ARITHMETIC_OVERFLOW(12), TRAP(13), FLOATING_POINT(15), COPROCESSOR2(18), MDMX_UNUSABLE(22), WATCH(23), MACHINE_CHECK(24), CACHE_ERROR(30);

	private final int	value;

	ExcCode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
