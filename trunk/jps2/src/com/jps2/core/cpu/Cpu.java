package com.jps2.core.cpu;

public abstract class Cpu {

	public int pc;
	public int npc;
	public int cycle;

	public Processor processor;

	public abstract void nextPc();

	public abstract int nextOpcode();
}
