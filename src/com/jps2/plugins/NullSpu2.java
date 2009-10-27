package com.jps2.plugins;

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

}
