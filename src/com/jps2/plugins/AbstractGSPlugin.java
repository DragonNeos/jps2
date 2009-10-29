package com.jps2.plugins;

public abstract class AbstractGSPlugin implements Plugin {

	@Override
	public abstract boolean close();

	@Override
	public abstract String getName();

	@Override
	public final PluginType getType() {
		return PluginType.GS;
	}

	@Override
	public abstract int[] getVersion();

	@Override
	public abstract boolean open();

	@Override
	public abstract boolean test(final StringBuffer messages);

	// ////////////////////////////////
	// GS methods
	// ////////////////////////////////

	public abstract void vSync(int field);

	public abstract void gifTransfer1(int[] mem, int addr);

	public abstract void gifTransfer2(int[] mem, int size);

	public abstract void gifTransfer3(int[] mem, int size);

	public abstract long getLastTag(); // returns the last tag processed (64 bits)

	public abstract void gifSoftReset(int mask);

	public abstract void readFIFO(long[] mem);

	public abstract void readFIFO2(long[] mem, int qwc);

}
