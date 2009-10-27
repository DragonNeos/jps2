/*
This file is part of jpcsp.

Jpcsp is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jpcsp is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Jpcsp.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jps2.core.memory;

import java.io.InputStream;
import java.util.logging.Logger;

import com.jps2.core.cpu.r5900.R5900;


public abstract class Memory {
	public static final Logger	log	         = Logger.getLogger("memory");
	protected boolean	       useSafeMemory	= true;
	protected int	           addressMask	 = 0x1FFFFFF;
	protected int	           offset;
	private final String	   name;

	public Memory(final String name) {
		this.name = name;
	}

	public abstract void copy(Memory memory, int positionSource, int positionDest);

	public void setOffset(final int offset) {
		this.offset = ~offset;
	}

	public void invalidMemoryAddress(final int address, final String prefix, final int status) {
		final String message = String.format(name + "-%s - Invalid memory address : 0x%X PC=%08X", prefix, address, R5900.getProcessor().cpu.pc);
		Memory.log.severe(message);
		throw new RuntimeException(message);
	}

	public abstract boolean allocate();

	public abstract void reset();

	public abstract int read8(int address);

	public abstract int read16(int address);

	public abstract int read32(int address);

	public abstract long read64(int address);

	public abstract void write8(int address, byte data);

	public abstract void write16(int address, short data);

	public abstract void write32(int address, int data);

	public abstract void write64(int address, long data);

	public abstract void writeStream(int address, InputStream input);

	public abstract int getSize();
}
