/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jps2.filesystems.umdiso;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.HashMap;

import org.bolet.jgz.Inflater;

import com.jps2.filesystems.umdiso.iso9660.Iso9660Directory;
import com.jps2.filesystems.umdiso.iso9660.Iso9660File;
import com.jps2.filesystems.umdiso.iso9660.Iso9660Handler;
import com.jps2.util.Utilities;

/**
 * 
 * @author gigaherz
 */
public class UmdIsoReader {

	RandomAccessFile								fileReader;
	private final HashMap<String, Iso9660File>		fileCache	= new HashMap<String, Iso9660File>();
	private final HashMap<String, Iso9660Directory>	dirCache	= new HashMap<String, Iso9660Directory>();

	enum FileFormat {
		Uncompressed, CompressedCSO, CompressedDAX, // not implemented yet
		// ...
		Unknown
	}

	FileFormat	format;

	int			numSectors;	//
	long[]		sectorOffsets;	// for CSO/DAX

	int			offsetShift;	// for CSO

	String		fileName;

	private int Ubyte(final byte b) {
		return (b) & 255;
	}

	private int BytesToInt(final byte[] bytes, final int offset) throws ArrayIndexOutOfBoundsException {
		return Ubyte(bytes[offset + 0]) | (Ubyte(bytes[offset + 1]) << 8) | (Ubyte(bytes[offset + 2]) << 16) | (bytes[offset + 3] << 24);
	}

	public UmdIsoReader(final String umdFilename) throws IOException, FileNotFoundException {
		fileName = umdFilename;
		fileReader = new RandomAccessFile(umdFilename, "r");

		/*
		 * u32 'CISO' u32 0? u32 image size in bytes (why? it could have been
		 * sectors and make thigns simpler!) u32 sector size? (00000800 = 2048 =
		 * sector size) u32 ? (1) u32[] sector offsets (as many as image size /
		 * sector size, I guess)
		 */

		format = FileFormat.Uncompressed;
		numSectors = (int) (fileReader.length() / 2048);

		byte[] id = new byte[24];

		fileReader.seek(0);
		fileReader.read(id);

		if (new String(id,0,4).equals("CISO")) {
//		if ((((char) id[0]) == 'C') && (((char) id[1]) == 'I') && (((char) id[2]) == 'S') && (((char) id[3]) == 'O')) {
			format = FileFormat.CompressedCSO;

			final int lenInbytes = BytesToInt(id, 8);
			final int sectorSize = BytesToInt(id, 16);

			// int version = Ubyte(id[20]);
			offsetShift = Ubyte(id[21]);

			numSectors = lenInbytes / sectorSize;

			sectorOffsets = new long[numSectors + 1];

			final byte[] offsetData = new byte[(numSectors + 1) * 4];

			fileReader.read(offsetData);

			for (int i = 0; i <= numSectors; i++) {
				sectorOffsets[i] = (BytesToInt(offsetData, i * 4)) & 0xFFFFFFFFl;
				if (i > 0) {
					if ((sectorOffsets[i] & 0x7FFFFFFF) < (sectorOffsets[i - 1] & 0x7FFFFFFF)) {
						throw new IOException("Invalid offset [" + i + "]: " + sectorOffsets[i] + "<" + sectorOffsets[i - 1]);
					}
				}
			}
		}

		// when we reach here, we assume it's either a .ISO or a .CSO
		// but we still need to make sure of that

		id = new byte[6];

		final UmdIsoFile f = new UmdIsoFile(this, 16, 2048, null);
		f.read(id);

		if (new String(id,1,5).equals("CD001")) {
//		if ((((char) id[1]) == 'C') && (((char) id[2]) == 'D') && (((char) id[3]) == '0') && (((char) id[4]) == '0') && (((char) id[5]) == '1')) {
			if (format == FileFormat.Uncompressed) {
				numSectors = (int) (fileReader.length() / 2048);
			}
			return;
		}

		format = FileFormat.Unknown;
		throw new IOException("Unsupported file format or corrupt file.");
	}

	public byte[] readSector(final int sectorNumber) throws IOException {
		if ((sectorNumber < 0) || (sectorNumber >= numSectors))
			throw new ArrayIndexOutOfBoundsException("Sector number " + sectorNumber + " out of bounds.");

		if (format == FileFormat.Uncompressed) {
			final byte[] bytes = new byte[2048];

			fileReader.seek(2048 * sectorNumber);
			fileReader.read(bytes);
			return bytes;
		}

		if (format == FileFormat.CompressedCSO) {
			long sectorOffset = sectorOffsets[sectorNumber];
			long sectorEnd = sectorOffsets[sectorNumber + 1];

			if ((sectorOffset & 0x80000000) != 0) {
				final long realOffset = (sectorOffset & 0x7fffffff) << offsetShift;

				final byte[] bytes = new byte[2048];

				fileReader.seek(realOffset);
				fileReader.read(bytes);
				return bytes;
			}

			sectorEnd = (sectorEnd & 0x7fffffff) << offsetShift;
			sectorOffset = (sectorOffset & 0x7fffffff) << offsetShift;

			final int compressedLength = (int) (sectorEnd - sectorOffset);
			if (compressedLength < 0) {
				return new byte[2048];
			}

			final byte[] compressedData = new byte[compressedLength];

			fileReader.seek(sectorOffset);
			fileReader.read(compressedData);

			final byte[] data = new byte[2048];

			try {
				final Inflater inf = new Inflater();

				final ByteArrayInputStream b = new ByteArrayInputStream(compressedData);
				inf.reset(b);
				// inf.setRawStream(b);
				inf.readAll(data, 0, data.length);
			} catch (final IOException e) {
				System.err.print("Exception while uncompressing sector from " + fileName);
				e.printStackTrace();
			}

			return data;
		}

		throw new IOException("Unsupported file format or corrupt file.");
	}

	private Iso9660File getFileEntry(final String filePath) throws IOException, FileNotFoundException {
		Iso9660File info;

		info = fileCache.get(filePath);
		if (info != null) {
			return info;
		}

		final int parentDirectoryIndex = filePath.lastIndexOf('/');
		if (parentDirectoryIndex >= 0) {
			final String parentDirectory = filePath.substring(0, parentDirectoryIndex);
			final Iso9660Directory dir = dirCache.get(parentDirectory);
			if (dir != null) {
				final int index = dir.getFileIndex(filePath.substring(parentDirectoryIndex + 1));
				info = dir.getEntryByIndex(index);
				if (info != null) {
					fileCache.put(filePath, info);
					return info;
				}
			}
		}

		Iso9660Directory dir = new Iso9660Handler(this);

		final String[] path = filePath.split("[\\/]");

		// walk through path
		for (int i = 0; i < path.length;) {
			if (path[i].compareTo(".") == 0) {
				if (i == (path.length - 1)) {
					break;
				}
				// skip the path "."
				i++;
			} else
				if (path[i].compareTo("..") == 0) {
					i = Math.max(0, i - 1);
				} else {
					final String pathName = path[i];
					final int index = dir.getFileIndex(pathName);
					info = dir.getEntryByIndex(index);
					dir = null;
					if ((info.getProperties() & 2) == 2) // if it's a directory
					{
						dir = new Iso9660Directory(this, info.getLBA(), info.getSize());
						String dirPath = path[0];
						for (int j = 1; j <= i; j++) {
							dirPath += "/" + path[j];
						}
						dirCache.put(dirPath, dir);
					}
					i++;
				}
		}

		if (info != null) {
			fileCache.put(filePath, info);
		}

		return info;
	}

	public UmdIsoFile getFile(String filePath) throws IOException, FileNotFoundException {
		int fileStart;
		long fileLength;
		Date timestamp = null;

		if (filePath != null && filePath.startsWith("sce_lbn")) {
			//
			// Direct sector access on UMD is using the following file name
			// syntax:
			// sce_lbnSSSS_sizeLLLL
			// where SSSS is the index of the first sector (in base 16)
			// LLLL is the length in bytes (in base 16)
			// E.g.
			// disc0:/sce_lbn0x5fa0_size0x1428
			// disc0:/sce_lbn7050_sizeee850
			//
			filePath = filePath.substring(7);
			final int sep = filePath.indexOf("_size");
			fileStart = (int) Utilities.parseHexLong(filePath.substring(0, sep));
			fileLength = Utilities.parseHexLong(filePath.substring(sep + 5));

			if (fileStart < 0 || fileStart >= numSectors) {
				throw new IOException("File '" + filePath + "': Invalid Start Sector");
			}
		} else
			if (filePath != null && filePath.length() == 0) {
				fileStart = 0;
				fileLength = numSectors * 2048;
			} else {
				Iso9660File info = getFileEntry(filePath);
				if (info != null) {
					if ((info.getProperties() & 2) == 2) // if it's a directory
					{
						info = null;
					}
				}

				if (info == null)
					throw new FileNotFoundException("File '" + filePath + "' not found or not a file.");

				fileStart = info.getLBA();
				fileLength = info.getSize();
				timestamp = info.getTimestamp();
			}

		return new UmdIsoFile(this, fileStart, fileLength, timestamp);
	}

	public String[] listDirectory(final String filePath) throws IOException, FileNotFoundException {
		Iso9660Directory dir = null;

		if (filePath.compareTo("") == 0) {
			dir = new Iso9660Handler(this);
		} else {
			final Iso9660File info = getFileEntry(filePath);

			if (info != null) {
				if ((info.getProperties() & 2) == 2) // if it's a directory
				{
					dir = new Iso9660Directory(this, info.getLBA(), info.getSize());
				}
			}
		}

		if (dir == null)
			throw new FileNotFoundException("File '" + filePath + "' not found or not a directory.");

		return dir.getFileList();
	}

	public int getFileProperties(final String filePath) throws IOException, FileNotFoundException {
		if (filePath.compareTo("") == 0) {
			return 2;
		}

		final Iso9660File info = getFileEntry(filePath);

		if (info == null)
			throw new FileNotFoundException("File '" + filePath + "' not found.");

		return info.getProperties();
	}

	public boolean isDirectory(final String filePath) throws IOException, FileNotFoundException {
		return (getFileProperties(filePath) & 2) == 2;
	}

	public String getFilename() {
		return fileName;
	}
}
