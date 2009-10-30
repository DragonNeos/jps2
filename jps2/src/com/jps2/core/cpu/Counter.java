package com.jps2.core.cpu;

public class Counter {
	public long count;
	public long target;
	public int mode;
	public int rate;
	public int interrupt;
	public int cycleT;

	public void reset(int cycle) {
		count = 0;
		mode &= ~0x18301C00;
		cycleT = cycle;
	}

}
