package com.jps2.core;

import org.apache.log4j.Logger;

import com.jps2.core.cpu.Memories;
import com.jps2.core.cpu.ee.EE;
import com.jps2.core.cpu.ee.EEInstructions;
import com.jps2.core.cpu.iop.IOP;
import com.jps2.core.cpu.iop.IOPInstructions;
import com.jps2.core.hardware.SIO;
import com.jps2.plugins.PluginManager;

public class Emulator {

	public static final Logger		logger		= Logger.getLogger(Emulator.class);

	private static final Emulator	instance	= new Emulator();

	public static SIO				SIO;

	private EmulatorProcess				emulatorProcess;
	public static EE				EE;
	public static IOP				IOP;

	private boolean					running		= false;
	private boolean					paused		= false;

	private EmulatorStateListener	listener;

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
			SIO = new SIO();
			EE = new EE();
			IOP = new IOP();
			paused = false;
			// init emulator process
			emulatorProcess = new EmulatorProcess();
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
				if (emulatorProcess != null && Thread.currentThread() != emulatorProcess) {
					try {
						emulatorProcess.join();
					} catch (final InterruptedException e) {
						// ignore if interrupted
					}
				}
			}
			PluginManager.closeAll();
			SIO = null;
			EE = null;
			IOP = null;
			System.gc();
			if (listener != null) {
				listener.stopped();
			}
			paused = false;
		} catch (final Throwable t) {
			if (listener != null) {
				listener.error(t);
			}
		}
	}

	public static final Emulator getInstance() {
		return instance;
	}

	private final class EmulatorProcess extends Thread {

		EmulatorProcess() {
			super("Emulator Process");
			setPriority(MIN_PRIORITY);
			setDaemon(true);
			// send cpus to others objects in emulator
			Memories.hwRegistersIOP.setCpu((com.jps2.core.cpu.iop.state.CpuState) IOP.cpu);
			IOPInstructions.setCpu((com.jps2.core.cpu.iop.state.CpuState) IOP.cpu);
			SIO.setCpu((com.jps2.core.cpu.iop.state.CpuState) IOP.cpu);
			EEInstructions.setCpu((com.jps2.core.cpu.ee.state.CpuState) EE.cpu);
			Memories.hwRegistersEE.setCpu((com.jps2.core.cpu.ee.state.CpuState) EE.cpu);
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
					
					// step EE 300 MHz
					EE.step();
					EE.step();
					EE.step();
					EE.step();
					
					//step IOP 37.5 Mhz
					IOP.step();

					EE.step();
					EE.step();
					EE.step();
					EE.step();
					
					EE.step();
					EE.step();
					EE.step();
					EE.step();
					
					IOP.step();

					EE.step();
					EE.step();
					EE.step();
					EE.step();
					
					// TODO - SPU 1 step here
					
					EE.step();
					EE.step();
					EE.step();
					EE.step();
					
					IOP.step();

					EE.step();
					EE.step();
					EE.step();
					EE.step();
					
					EE.step();
					EE.step();
					EE.step();
					EE.step();
					
					IOP.step();

					EE.step();
					EE.step();
					EE.step();
					EE.step();
				}
			} catch (final Throwable t) {
				System.err.println("EE count = " + EE.cpu.cycle);
				Emulator.this.stop(false);
				if (listener != null) {
					listener.error(t);
				}
				logger.error("A error throw in Emulator Process.", t);
			}
		}
	}
}
