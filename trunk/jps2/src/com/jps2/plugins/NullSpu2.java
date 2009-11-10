package com.jps2.plugins;

import com.jps2.gui.ConfigureComponent;

public class NullSpu2 extends AbstractSpu2Plugin {

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public String getName() {
		return "NullSpu2";
	}

	@Override
	public boolean open() {
		return true;
	}

	@Override
	public void spu2write(final int address, final short data) {
		// TODO Auto-generated method stub
		System.err.println("spu2 write");
	}

	@Override
	public boolean test(final StringBuffer messages) {
		return true;
	}

	@Override
	public int[] getVersion() {
		return new int[] {
							0,
							0,
							1
		};
	}

	@Override
	public ConfigureComponent configure() {
		return null;
	}
}
