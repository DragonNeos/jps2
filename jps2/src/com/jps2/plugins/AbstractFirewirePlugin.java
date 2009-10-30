package com.jps2.plugins;

public abstract class AbstractFirewirePlugin implements Plugin {

	@Override
	public abstract boolean close();

	@Override
	public abstract String getName();

	@Override
	public final PluginType getType() {
		return PluginType.FIREWIRE;
	}

	@Override
	public abstract boolean open();

	@Override
	public abstract boolean test(final StringBuffer messages);

	public abstract void firewireOpen();

	public abstract int firewireRead32(int address);

	public abstract void firewireWrite32(int address,int data);

	public abstract void firewireIrqCallback();

}
