package com.jps2.plugins;

public abstract class AbstractPadPlugin implements Plugin {

	@Override
	public abstract boolean close();

	@Override
	public abstract String getName();

	@Override
	public final PluginType getType() {
		return PluginType.PAD;
	}

	@Override
	public abstract boolean open();

	@Override
	public abstract boolean test(final StringBuffer messages);

}
