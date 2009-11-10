package com.jps2.plugins;

public abstract class PluginManager {

	private static AbstractUsbPlugin		usbPlugin;

	private static AbstractFirewirePlugin	firewirePlugin;

	private static AbstractSpu2Plugin		spu2Plugin;

	public static final void initialize() {
		spu2Plugin = new NullSpu2();
	}

	public static final void closeAll() {
		if (spu2Plugin != null) {
			spu2Plugin.close();
			spu2Plugin = null;
		}
	}

	public static final AbstractUsbPlugin getUsbPlugin() {
		return usbPlugin;
	}

	public static AbstractFirewirePlugin getFirewirePlugin() {
		return firewirePlugin;
	}

	public static AbstractSpu2Plugin getSpu2Plugin() {
		return spu2Plugin;
	}
}
