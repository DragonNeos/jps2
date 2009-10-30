package com.jps2.plugins;

public abstract class AbstractSpu2Plugin implements Plugin {

	@Override
	public abstract boolean close();

	@Override
	public abstract String getName();

	@Override
	public final PluginType getType() {
		return PluginType.SPU2;
	}

	@Override
	public abstract boolean open();

	@Override
	public abstract boolean test(final StringBuffer messages);

	public abstract void spu2write(int address, short data);

}
