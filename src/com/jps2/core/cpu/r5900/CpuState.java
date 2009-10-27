package com.jps2.core.cpu.r5900;

public class CpuState extends VfpuState {

	@Override
	public void reset() {
		resetAll();
	}

	public CpuState() {
	}

	public void copy(final CpuState that) {
		super.copy(that);
	}

	public CpuState(final CpuState that) {
		copy(that);
	}
	
	public void doTLBR(){
		
	}
	
	public void doTLBWI(){
		
	}
	
	public void doTLBWR(){
		
	}
	
	public void doTLBP(){
		
	}
	
	public static final class CP0regs {
		
	}
}
