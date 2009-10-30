/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jps2.filesystems.umdiso.iso9660;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import com.jps2.filesystems.umdiso.UmdIsoFile;
import com.jps2.filesystems.umdiso.UmdIsoReader;

/**
 * 
 * @author gigaherz
 */
public class Iso9660Directory {

	private final Vector<Iso9660File> files;

	public Iso9660Directory(final UmdIsoReader r, final int directorySector,
			int directorySize) throws IOException {
		// parse directory sector
		final UmdIsoFile dataStream = new UmdIsoFile(r, directorySector,
				directorySize, null);

		files = new Vector<Iso9660File>();

		byte[] b;

		final int currentPos = 0;

		while (directorySize >= 4) {
			final int entryLength = dataStream.read();
			directorySize -= 4;

			if (entryLength == 0) {
				continue;
			}

			directorySize -= entryLength;

			b = new byte[entryLength - 1];
			dataStream.read(b);

			final Iso9660File file = new Iso9660File(b, b.length);
			files.add(file);
		}

	}

	public Iso9660File getEntryByIndex(final int index)
			throws ArrayIndexOutOfBoundsException {
		return files.get(index);
	}

	public int getFileIndex(final String fileName) throws FileNotFoundException {
		for (int i = 0; i < files.size(); i++) {
			final String file = files.get(i).getFileName();
			if (file.compareToIgnoreCase(fileName) == 0) {
				return i;
			}
		}

		throw new FileNotFoundException("File " + fileName
				+ " not found in directory.");
	}

	public String[] getFileList() throws FileNotFoundException {
		final String[] list = new String[files.size()];
		for (int i = 0; i < files.size(); i++) {
			list[i] = files.get(i).getFileName();
		}
		return list;
	}

}
