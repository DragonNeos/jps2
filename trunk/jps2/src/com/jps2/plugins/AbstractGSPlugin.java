package com.jps2.plugins;

import com.jps2.plugins.event.KeyEvent;

public abstract class AbstractGSPlugin implements Plugin {

	@Override
	public final PluginType getType() {
		return PluginType.GS;
	}

	// ////////////////////////////////
	// GS methods
	// ////////////////////////////////

	public abstract void vSync(int field);

	public abstract void gifTransfer1(int[] mem, int addr);

	public abstract void gifTransfer2(int[] mem, int size);

	public abstract void gifTransfer3(int[] mem, int size);

	// returns the last tag processed (64 bits)
	public abstract long getLastTag();

	public abstract void gifSoftReset(int mask);

	public abstract void readFIFO(long[] mem);

	public abstract void readFIFO2(long[] mem, int qwc);

	// keyEvent gets called when there is a keyEvent from the PAD plugin
	public abstract void keyEvent(KeyEvent evt);

	/*
	 * void CALLBACK GSchangeSaveState(int, const char* filename);
	 * 
	 * void CALLBACK GSmakeSnapshot(char *path); void CALLBACK
	 * GSmakeSnapshot2(char *pathname, int* snapdone, int savejpg); void
	 * CALLBACK GSirqCallback(void (*callback)()); public abstract void
	 * printf(int timeout, String...msg); void CALLBACK GSsetBaseMem(void*);
	 * void CALLBACK GSsetGameCRC(final int crc, final int gameoptions);
	 */
	// controls frame skipping in the GS, if this routine isn't present, frame
	// skipping won't be done
	public abstract void setFrameSkip(int frameskip);

	/*
	 * // if start is 1, starts recording spu2 data, else stops // returns a non
	 * zero value if successful // for now, pData is not used
	 * 
	 * int CALLBACK GSsetupRecording(final int start, void* pData);
	 * 
	 * void CALLBACK GSwriteCSR(final u32 value); void CALLBACK
	 * GSgetDriverInfo(GSdriverInfo *info); #ifdef _WIN32 s32 CALLBACK
	 * GSsetWindowInfo(winInfo *info); #endif s32 CALLBACK GSfreeze(final int
	 * mode, freezeData *data);
	 */

}
