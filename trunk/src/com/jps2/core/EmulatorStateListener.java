package com.jps2.core;

public interface EmulatorStateListener {
	void started();

	void stopped();

	void paused(boolean pause);

	void error(Throwable throwable);
}
