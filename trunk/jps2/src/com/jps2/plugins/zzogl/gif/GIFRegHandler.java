package com.jps2.plugins.zzogl.gif;

import com.jps2.plugins.zzogl.GSInternal;

public abstract class GIFRegHandler {
	private final String	name;

	GIFRegHandler(final String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public abstract void handler(final GSInternal gs, int... data);
}
