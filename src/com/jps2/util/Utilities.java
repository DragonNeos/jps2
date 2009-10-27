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
package com.jps2.util;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.jps2.core.memory.Memory;
import com.jps2.filesystems.SeekableDataInput;


public class Utilities {

    public static String formatString(final String type, String oldstring) {
        int counter = 0;
        if (type.equals("byte")) {
            counter = 2;
        }
        if (type.equals("short")) {
            counter = 4;
        }
        if (type.equals("long")) {
            counter = 8;
        }
        int len = oldstring.length();
        final StringBuilder sb = new StringBuilder();
        while (len++ < counter) {
            sb.append('0');
        }
        oldstring = sb.append(oldstring).toString();
        return oldstring;

    }

    public static String integerToBin(final int value) {
        return Long.toBinaryString(0x0000000100000000L|((value)&0x00000000FFFFFFFFL)).substring(1);
    }

    public static String integerToHex(final int value) {
        return Integer.toHexString(0x100 | value).substring(1).toUpperCase();
    }

    public static String integerToHexShort(final int value) {
        return Integer.toHexString(0x10000 | value).substring(1).toUpperCase();
    }

    public static long readUWord(final SeekableDataInput f) throws IOException {
        final long l = (f.readUnsignedByte() | (f.readUnsignedByte() << 8) | (f.readUnsignedByte() << 16) | (f.readUnsignedByte() << 24));
        return (l & 0xFFFFFFFFL);
    }

    public static int readUByte(final SeekableDataInput f) throws IOException {
        return f.readUnsignedByte();
    }

    public static int readUHalf(final SeekableDataInput f) throws IOException {
        return f.readUnsignedByte() | (f.readUnsignedByte() << 8);
    }

    public static int readWord(final SeekableDataInput f) throws IOException {
        //readByte() isn't more correct? (already exists one readUWord() method to unsign values)
        return (f.readUnsignedByte() | (f.readUnsignedByte() << 8) | (f.readUnsignedByte() << 16) | (f.readUnsignedByte() << 24));
    }

    public static void readBytesToBuffer(
        final SeekableDataInput f, final ByteBuffer buf,
        final int offset, final int size) throws IOException
    {
        f.readFully(buf.array(), offset + buf.arrayOffset(), size);
    }
    public static String readStringZ(final SeekableDataInput f) throws IOException {
        final StringBuffer sb = new StringBuffer();
        int b;
        for (; f.getFilePointer() < f.length();) {
            b = f.readUnsignedByte();
            if (b == 0) {
                break;
            }
            sb.append((char)b);
        }
        return sb.toString();
    }

    public static String readStringZ(final byte[] mem, int offset) {
        final StringBuffer sb = new StringBuffer();
        int b;
        for (; offset < mem.length;) {
            b = mem[offset++];
            if (b == 0) {
                break;
            }
            sb.append((char)b);
        }
        return sb.toString();
    }

    public static String readStringZ(final ByteBuffer buf) throws IOException {
        final StringBuffer sb = new StringBuffer();
        byte b;
        for (; buf.position() < buf.limit();) {
              b = (byte)readUByte(buf);
            if (b == 0)
                break;
            sb.append((char)b);
        }
        return sb.toString();
    }
    public static String readStringNZ(final Memory mem, int address, int n) {
        final StringBuffer sb = new StringBuffer();
//        if (address + n > MemoryMap.END_RAM) {
//        	n = MemoryMap.END_RAM - address + 1;
//        }
        int b;
        for (; n > 0; n--) {
            b = mem.read8(address++);
            if (b == 0)
                break;
            sb.append((char)b);
        }
        return sb.toString();
    }
//    public static String readStringZ(Memory mem, int address) {
//    	return readStringNZ(mem, address, MemoryMap.END_RAM - address + 1);
//    }
//    public static String readStringZ(int address) {
//    	return readStringZ(Memory.getInstance(), address);
//    }
//    public static String readStringNZ(int address, int n) {
//    	return readStringNZ(Memory.getInstance(), address, n);
//    }
    public static void writeStringNZ(final Memory mem, final int address, final int n, final String s) {
    	int offset = 0;
    	while (offset < s.length() && offset < n) {
    		mem.write8(address + offset, (byte) s.charAt(offset));
    		offset++;
    	}
    	while (offset < n) {
    		mem.write8(address + offset, (byte) 0);
    		offset++;
    	}
    }
    public static void writeStringZ(final Memory mem, final int address, final String s) {
    	int offset = 0;
    	while (offset < s.length()) {
    		mem.write8(address + offset, (byte) s.charAt(offset));
    		offset++;
    	}
        mem.write8(address + offset, (byte) 0);
    }
    public static void writeStringZ(final ByteBuffer buf, final String s) {
		buf.put(s.getBytes());
		buf.put((byte) 0);
    }
    public static short getUnsignedByte (final ByteBuffer bb) throws IOException
    {
       return ((short)(bb.get() & 0xff));
    }
    public static void putUnsignedByte(final ByteBuffer bb, final int value)
    {
    	bb.put((byte) (value & 0xFF));
    }
    public static short readUByte(final ByteBuffer buf) throws IOException
    {
    	return getUnsignedByte(buf);
    }
    public static int readUHalf(final ByteBuffer buf) throws IOException
    {
    	return getUnsignedByte(buf) | (getUnsignedByte(buf) << 8);
    }
    public static long readUWord(final ByteBuffer buf) throws IOException
    {
        final long l = (getUnsignedByte(buf) | (getUnsignedByte(buf) << 8 ) | (getUnsignedByte(buf) << 16 ) | (getUnsignedByte(buf) << 24));
        return (l & 0xFFFFFFFFL);

    }
    public static int readWord(final ByteBuffer buf) throws IOException
    {
        return (getUnsignedByte(buf) | (getUnsignedByte(buf) << 8 ) | (getUnsignedByte(buf) << 16 ) | (getUnsignedByte(buf) << 24));
    }
    public static void writeWord(final ByteBuffer buf, final long value)
    {
    	putUnsignedByte(buf, (int) (value >>  0));
    	putUnsignedByte(buf, (int) (value >>  8));
    	putUnsignedByte(buf, (int) (value >> 16));
    	putUnsignedByte(buf, (int) (value >> 24));
    }
    public static void writeHalf(final ByteBuffer buf, final int value)
    {
    	putUnsignedByte(buf, value >> 0);
    	putUnsignedByte(buf, value >> 8);
    }
    public static void writeByte(final ByteBuffer buf, final int value)
    {
    	putUnsignedByte(buf, value);
    }

    public static int parseAddress(String value)
    {
    	int address = 0;

    	if (value == null) {
    		return address;
    	}

        if (value.startsWith("0x"))
            value = value.substring(2);

    	if (Integer.SIZE == 32 && value.length() == 8 && value.startsWith("8")) {
    		address = Integer.parseInt(value.substring(1), 16);
    		address |= 0x80000000;
    	} else {
    		address = Integer.parseInt(value, 16);
    	}

    	return address;
    }


    /**
     * Parse the string as a number and returns its value.
     * If the string starts with "0x", the number is parsed
     * in base 16, otherwise base 10.
     * 
     * @param s the string to be parsed
     * @return the numeric value represented by the string.
     */
    public static long parseLong(final String s)
    {
    	long value = 0;

    	if (s == null) {
    		return value;
    	}

    	if (s.startsWith("0x")) {
    		value = Long.parseLong(s.substring(2), 16);
    	} else {
    		value = Long.parseLong(s);
    	}

    	return value;
    }

    /**
     * Parse the string as a number and returns its value.
     * The number is always parsed in base 16.
     * The string can start as an option with "0x".
     * 
     * @param s the string to be parsed in base 16
     * @return the numeric value represented by the string.
     */
    public static long parseHexLong(String s)
    {
        long value = 0;

        if (s == null) {
            return value;
        }

        if (s.startsWith("0x")) {
            s = s.substring(2);
        }
        value = Long.parseLong(s, 16);

        return value;
    }

    public static int makePow2(int n) {
        --n;
        n = (n >>  1) | n;
        n = (n >>  2) | n;
        n = (n >>  4) | n;
        n = (n >>  8) | n;
        n = (n >> 16) | n;
        return ++n;
    }

//    public static void readFully(SeekableDataInput input, int address, int length) throws IOException {
//        final int blockSize = 1024 * 1024;  // 1Mb
//        while (length > 0) {
//            int size = Math.min(length, blockSize);
//            byte[] buffer = new byte[size];
//            input.readFully(buffer);
//            Memory.getInstance().copyToMemory(address, ByteBuffer.wrap(buffer), size);
//            address += size;
//            length -= size;
//        }
//    }
//
//    public static void write(SeekableRandomFile output, int address, int length) throws IOException {
//    	Buffer buffer = Memory.getInstance().getBuffer(address, length);
//    	if (buffer instanceof ByteBuffer) {
//    		output.getChannel().write((ByteBuffer) buffer);
//    	} else {
//    		Memory mem = Memory.getInstance();
//    		for (int i = 0; i < length; i++) {
//    			output.writeByte(mem.read8(address + i));
//    		}
//    	}
//    }

    public static void bytePositionBuffer(final Buffer buffer, final int bytePosition) {
    	buffer.position(bytePosition / bufferElementSize(buffer));
    }

    public static int bufferElementSize(final Buffer buffer) {
    	if (buffer instanceof IntBuffer) {
    		return 4;
    	}

    	return 1;
    }

    public static String stripNL(String s) {
    	if (s != null && s.endsWith("\n")) {
    		s = s.substring(0, s.length() - 1);
    	}

    	return s;
    }
}
