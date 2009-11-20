package com.jps2.plugins.pad.jpad;

import com.jps2.gui.ConfigureComponent;
import com.jps2.plugins.AbstractPadPlugin;

public class JPad extends AbstractPadPlugin {

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public String getName() {
		return "JPad";
	}

	@Override
	public boolean open() {
		return true;
	}

	@Override
	public boolean test(final StringBuffer messages) {
		return true;
	}

	@Override
	public ConfigureComponent configure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getVersion() {
		return new int[] {
							0,
							0,
							1
		};
	}

}
