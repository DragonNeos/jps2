package com.jps2.core.hardware;

import com.jps2.core.memory.FastMemory;

public class GS extends FastMemory {
	public GS() {
		super("GS Memory", 0x200000);
	}
}
