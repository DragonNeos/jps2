package com.jps2.plugins;

public abstract class AbstractUsbPlugin implements Plugin {

	@Override
	public abstract boolean close();

	@Override
	public abstract String getName();

	@Override
	public final PluginType getType() {
		return PluginType.USB;
	}

	@Override
	public abstract boolean open();

	@Override
	public abstract boolean test(final StringBuffer messages);

	public abstract void usbOpen();

	public abstract byte usbRead8(int address);

	public abstract short usbRead16(int address);

	public abstract int usbRead32(int address);

	public abstract void usbWrite8(int address,byte data);

	public abstract void usbWrite16(int address,short data);

	public abstract void usbWrite32(int address,int data);

	public abstract void usbAsync();

	public abstract void usbIrqCallback();

	public abstract void usbIrqHandler();

	public abstract void usbSetRAM();

}
