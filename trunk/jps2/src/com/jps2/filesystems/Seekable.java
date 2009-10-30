/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jps2.filesystems;

import java.io.*;

/**
 * 
 * @author gigaherz
 */
public interface Seekable {

	public long length() throws IOException;

	public void seek(long position) throws IOException;

	public long getFilePointer() throws IOException;
}
