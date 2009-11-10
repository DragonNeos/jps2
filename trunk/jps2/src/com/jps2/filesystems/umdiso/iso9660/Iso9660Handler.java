/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jps2.filesystems.umdiso.iso9660;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jps2.filesystems.umdiso.UmdIsoReader;

/**
 * 
 * @author gigaherz
 */
public class Iso9660Handler extends Iso9660Directory {

	private final Iso9660Directory	internalDir;

	public Iso9660Handler(final UmdIsoReader r) throws IOException {
		super(r, 0, 0);

		final byte[] sector = r.readSector(16);
		final ByteArrayInputStream byteStream = new ByteArrayInputStream(sector);

		byteStream.skip(157); // reach rootDirTocHeader

		final byte[] b = new byte[38];

		byteStream.read(b);
		final Iso9660File rootDirEntry = new Iso9660File(b, b.length);

		final int rootLBA = rootDirEntry.getLBA();
		final int rootSize = rootDirEntry.getSize();

		internalDir = new Iso9660Directory(r, rootLBA, rootSize);
	}

	@Override
	public Iso9660File getEntryByIndex(final int index) throws ArrayIndexOutOfBoundsException {
		return internalDir.getEntryByIndex(index);
	}

	@Override
	public int getFileIndex(final String fileName) throws FileNotFoundException {
		return internalDir.getFileIndex(fileName);
	}

	@Override
	public String[] getFileList() throws FileNotFoundException {
		return internalDir.getFileList();
	}

}
