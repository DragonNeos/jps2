package com.jps2.core;

import org.apache.log4j.Logger;

import com.jps2.core.cpu.Memories;
import com.jps2.core.cpu.ee.EE;
import com.jps2.core.cpu.ee.EEInstructions;
import com.jps2.core.cpu.iop.IOP;
import com.jps2.core.cpu.iop.IOPInstructions;
import com.jps2.plugins.PluginManager;

public class Emulator {

	public static final Logger logger = Logger.getLogger(Emulator.class);

	private static final Emulator instance = new Emulator();

	public final SIO sio = new SIO();

	private IOPProcess iopProcess;
	private EEProcess eeProcess;

	private boolean running = false;
	private boolean paused = false;

	private EmulatorStateListener listener;

	private Emulator() {
	}

	public void pause(final boolean pause) {
		paused = pause;
		if (listener != null) {
			listener.paused(pause);
		}
	}

	public void setListener(final EmulatorStateListener listener) {
		this.listener = listener;
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isEmulating() {
		return running && !paused;
	}

	public synchronized void start() {
		try {
			if (running) {
				stop();
			}
			running = true;
			if (listener != null) {
				listener.started();
			}
			Memories.alloc();
			PluginManager.initialize();
			paused = false;
			// init cpus
			iopProcess = new IOPProcess();
			eeProcess = new EEProcess();
		} catch (final Throwable t) {
			if (listener != null) {
				listener.error(t);
			}
			stop();
		}
	}

	public void stop() {
		stop(true);
	}

	private void stop(final boolean join) {
		try {
			running = false;
			if (join) {
				if (iopProcess != null && Thread.currentThread() != iopProcess) {
					try {
						iopProcess.join();
					} catch (final InterruptedException e) {
						// ignore if interrupted
					}
				}
				if (eeProcess != null && Thread.currentThread() != eeProcess) {
					try {
						eeProcess.join();
					} catch (final InterruptedException e) {
						// ignore if interrupted
					}
				}
			}
			PluginManager.closeAll();
			Memories.clear();
			System.gc();
			if (listener != null) {
				listener.stopped();
			}
		} catch (final Throwable t) {
			if (listener != null) {
				listener.error(t);
			}
		}
	}

	public SIO getSio() {
		return sio;
	}

	public static final Emulator getInstance() {
		return instance;
	}

	private final class IOPProcess extends Thread {

		private final IOP iop = new IOP();

		IOPProcess() {
			super("IOP Process");
			setDaemon(true);
			// send cpu to others objects in emulator
			Memories.hwRegistersIOP
					.setCpu((com.jps2.core.cpu.iop.state.CpuState) iop.cpu);
			IOPInstructions
					.setCpu((com.jps2.core.cpu.iop.state.CpuState) iop.cpu);
			start();
		}

		@Override
		public void run() {
			try {
				while (Emulator.this.running) {
					// if is paused
					if (Emulator.this.paused) {
						// wait 100 millis :)
						sleep(100);
						// continue loop
						continue;
					}
					// step cpu
					iop.step();
				}
			} catch (final Throwable t) {
				Emulator.this.stop(false);
				if (listener != null) {
					listener.error(t);
				}
				logger.error("A error throw in IOP Process.", t);
			}
		}
	}

	private final class EEProcess extends Thread {

		private final EE ee = new EE();

		EEProcess() {
			super("EE Process");
			setDaemon(true);
			// send cpu to others objects in emulator
			EEInstructions.setCpu((com.jps2.core.cpu.ee.state.CpuState) ee.cpu);
			start();
		}

		@Override
		public void run() {
			try {
				while (Emulator.this.running) {
					// if is paused
					if (Emulator.this.paused) {
						// wait 100 millis :)
						sleep(100);
						// continue loop
						continue;
					}
					// step cpu
					ee.step();
				}
			} catch (final Throwable t) {
				Emulator.this.stop(false);
				if (listener != null) {
					listener.error(t);
				}
				logger.error("A error throw in EE Process.", t);
			}
		}
	}
}
