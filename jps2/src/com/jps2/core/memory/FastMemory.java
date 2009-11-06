package com.jps2.core.memory;

import java.io.InputStream;
import java.util.Arrays;

public class FastMemory extends Memory {
	//
	// In a typical application, the following read/write operations are
	// performed:
	// - read8 : 1,45% of total read/write, 1,54% of total read operations
	// - read16 : 13,90% of total read/write, 14,76% of total read operations
	// - read32 : 78,80% of total read/write, 83,70% of total read operations
	// - write8 : 1,81% of total read/write, 30,96% of total write operations
	// - write16: 0,02% of total read/write, 0,38% of total write operations
	// - write32: 4,02% of total read/write, 68,67% of total write operations
	//
	// This is why this Memory implementation is optimized for fast read32
	// operations.
	// Drawback is the higher memory requirements.
	//
	// This implementation is performing very few checks for the validity of
	// memory address references to achieve the highest performance.
	//
	private int[] all;

	private final int size;

	public FastMemory(final String name, final int size) {
		super(name);
		this.size = size;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void copy(final Memory memory, int positionSource, int positionDest) {
		int readed = 0;
		while (readed < memory.getSize()) {
			write32(positionDest, memory.read32(positionSource));
			positionDest += 4;
			positionSource += 4;
			readed += 4;
		}
	}

	@Override
	public boolean allocate() {
		if (all == null) {
			final int allSize = (size + 1) / 4;
			try {
				all = new int[allSize];
			} catch (final OutOfMemoryError e) {
				// Not enough memory provided for this VM, cannot use FastMemory
				// model
				Memory.log
						.warning("Cannot allocate FastMemory: add the option '-Xmx256m' to the Java Virtual Machine startup command to improve Performance");
				Memory.log
						.info("The current Java Virtual Machine has been started using '-Xmx"
								+ (Runtime.getRuntime().maxMemory() / (1024 * 1024))
								+ "m'");
				return false;
			}
		} else {
			Arrays.fill(all, 0);
		}

		return true;
	}

	@Override
	public void reset() {
		Arrays.fill(all, 0);
	}

	@Override
	public int read8(int address) {
		try {
			address &= offset;
			address &= addressMask;
			int data = all[(address / 4)];
			switch ((address & 0x03)) {
			case 1:
				data >>= 8;
				break;
			case 2:
				data >>= 16;
				break;
			case 3:
				data >>= 24;
				break;
			}

			return data & 0xFF;
		} catch (final Exception e) {
			invalidMemoryAddress(address, "read8", 0);
			return 0;
		}
	}

	@Override
	public int read16(int address) {
		try {
			address &= offset;
			address &= addressMask;
			int data = all[(address / 4)];
			if ((address & 0x02) != 0) {
				data >>= 16;
			}
			return data & 0xFFFF;
		} catch (final Exception e) {
			invalidMemoryAddress(address, "read16", 0);
			return 0;
		}
	}

	@Override
	public int read32(int address) {
		try {
			address &= offset;
			address &= addressMask;
			return all[address / 4];
		} catch (final Exception e) {
			invalidMemoryAddress(address, "read32", 0);
			return 0;
		}
	}

	@Override
	public long read64(int address) {
		try {
			address &= offset;
			address &= addressMask;
			// return (all[(address / 4) + 1] << 32)
			// | (all[(address / 4)] & 0xFFFFFFFFL);
			return (all[(address / 4)] << 32)
					| (all[(address / 4) + 1] & 0xFFFFFFFFL);
		} catch (final Exception e) {
			invalidMemoryAddress(address, "read64", 0);
			return 0;
		}
	}

	@Override
	public long[] read128(int address) {
		try {
			address &= offset;
			address &= addressMask;
			return new long[] {
					(all[(address / 4)] << 32)
							| (all[(address / 4) + 1] & 0xFFFFFFFFL),
					(all[(address / 4) + 2] << 32)
							| (all[(address / 4) + 3] & 0xFFFFFFFFL) };
		} catch (final Exception e) {
			invalidMemoryAddress(address, "read64", 0);
			return new long[2];
		}
	}

	@Override
	public void write8(int address, final byte data) {
		try {
			address &= offset;
			address &= addressMask;
			int memData = all[(address / 4)];
			switch ((address & 0x03)) {
			case 0:
				memData = (memData & 0xFFFFFF00) | ((data & 0xFF));
				break;
			case 1:
				memData = (memData & 0xFFFF00FF) | ((data & 0xFF) << 8);
				break;
			case 2:
				memData = (memData & 0xFF00FFFF) | ((data & 0xFF) << 16);
				break;
			case 3:
				memData = (memData & 0x00FFFFFF) | ((data & 0xFF) << 24);
				break;
			}
			all[(address / 4)] = memData;
		} catch (final Exception e) {
			invalidMemoryAddress(address, "write8", 0);
		}
	}

	@Override
	public void write16(int address, final short data) {
		try {
			address &= offset;
			address &= addressMask;
			int memData = all[(address / 4)];
			if ((address & 0x02) == 0) {
				memData = (memData & 0xFFFF0000) | (data & 0xFFFF);
			} else {
				memData = (memData & 0x0000FFFF) | ((data & 0xFFFF) << 16);
			}
			all[(address / 4)] = memData;
		} catch (final Exception e) {
			invalidMemoryAddress(address, "write16", 0);
		}
	}

	@Override
	public void write32(int address, final int data) {
		try {
			address &= offset;
			address &= addressMask;
			all[(address / 4)] = data;
		} catch (final Exception e) {
			invalidMemoryAddress(address, "write32 : " + e.getMessage(), 0);
		}
	}

	@Override
	public void write64(int address, final long data) {
		try {
			address &= offset;
			address &= addressMask;
			// all[(address / 4)] = (int) data;
			// all[(address / 4) + 1] = (int) (data >> 32);
			all[(address / 4)] = (int) (data >> 32);
			all[(address / 4) + 1] = (int) data;
		} catch (final Exception e) {
			invalidMemoryAddress(address, "write64", 0);
		}
	}

	@Override
	public void write128(int address, final long[] data) {
		try {
			address &= offset;
			address &= addressMask;
			all[(address / 4)] = (int) (data[0] >> 32);
			all[(address / 4) + 1] = (int) data[0];
			all[(address / 4) + 2] = (int) (data[1] >> 32);
			all[(address / 4) + 3] = (int) data[1];
		} catch (final Exception e) {
			invalidMemoryAddress(address, "write64", 0);
		}
	}

	@Override
	public void writeStream(int address, final InputStream input) {
		address &= addressMask;
		try {
			final byte[] buffer = new byte[32 * 1024 * 100];
			int readed = -1;
			int i = 0;
			while ((readed = input.read(buffer)) != -1) {
				for (i = 0; i < readed; i++) {
					write8(address, buffer[i]);
					address++;
				}
			}
		} catch (final Exception e) {
			throw new OutOfMemoryError("Invalid Address for memory ("
					+ Long.toHexString(address) + ").");
		}
	}

	public int[] getAll() {
		return all;
	}
}
