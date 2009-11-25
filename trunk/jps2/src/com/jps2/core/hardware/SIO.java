package com.jps2.core.hardware;

import com.jps2.core.cpu.iop.IOPHardwareRegisters;
import com.jps2.core.cpu.iop.state.CpuState;

public class SIO {
	// Status Flags
	private static final int	TX_RDY		= 0x0001;
	private static final int	RX_RDY		= 0x0002;
	private static final int	TX_EMPTY	= 0x0004;
	private static final int	PARITY_ERR	= 0x0008;
	private static final int	RX_OVERRUN	= 0x0010;
	private static final int	FRAMING_ERR	= 0x0020;
	private static final int	SYNC_DETECT	= 0x0040;
	private static final int	DSR			= 0x0080;
	private static final int	CTS			= 0x0100;
	private static final int	IRQ			= 0x0200;

	// Control Flags
	private static final int	TX_PERM		= 0x0001;
	private static final int	DTR			= 0x0002;
	private static final int	RX_PERM		= 0x0004;
	private static final int	BREAK		= 0x0008;
	private static final int	RESET_ERR	= 0x0010;
	private static final int	RTS			= 0x0020;
	private static final int	SIO_RESET	= 0x0040;

	public short				statReg;
	public short				modeReg;
	public short				ctrlReg;
	public short				baudReg;

	public final byte[]			buffer		= new byte[256];
	public int					bufcount;
	public int					parp;
	public int					mcdst;
	public int					rdwr;
	public byte					adrH;
	public byte					adrL;
	public int					padst;
	public int					mtapst;
	public int					packetsize;

	public byte					terminator;
	public byte					mode;
	public byte					mcCommand;
	public int					lastsector;
	public int					sector;
	public int					k;
	public int					count;

	private CpuState			cpu;

	public void setCpu(final CpuState cpu) {
		this.cpu = cpu;
	}

	// Active pad slot for each port. Not sure if these automatically reset
	// after each read or not.
	public final byte[]	activePadSlot		= new byte[2];
	// Active memcard slot for each port. Not sure if these automatically reset
	// after each read or not.
	public final byte[]	activeMemcardSlot	= new byte[2];

	public final int getMemcardIndex() {
		return (ctrlReg & 0x2000) >> 13;
	}

	public final int getMultitapPort() {
		return (ctrlReg & 0x2000) >> 13;
	}

	public void sioInit() {
		statReg = TX_RDY | TX_EMPTY;
		packetsize = 0;
		terminator = 0x55; // Command terminator 'U'

	}

	public byte sioRead8() {
		byte ret = (byte) 0xFF;

		if ((statReg & RX_RDY) != 0) {
			ret = buffer[parp];
			if (parp == bufcount) {
				statReg &= ~RX_RDY; // Receive is not Ready now?
				statReg |= TX_EMPTY; // Buffer is Empty

				if (padst == 2)
					padst = 0;
				/*
				 * if (mcdst == 1) { mcdst = 99; StatReg&= ~TX_EMPTY; StatReg|=
				 * RX_RDY; }
				 */
			}
		}
		// PAD_LOG("sio read8 ;ret = %x", ret);
		return ret;

	}

	public void sioWrite8(final byte value) {
//		commandWrite(value, 0);
	}

	public void sioDmaWrite(final byte value) {
//		commandWrite(value, 1);
	}

	public void sioWriteCtrl16(final short value) {
		ctrlReg = (short) (value & ~RESET_ERR);
		if ((value & RESET_ERR) != 0) {
			statReg &= ~IRQ;
		}
		if (((ctrlReg & SIO_RESET) != 0) || (ctrlReg == 0)) {
			mtapst = 0;
			padst = 0;
			mcdst = 0;
			parp = 0;
			statReg = TX_RDY | TX_EMPTY;
			cpu.interrupt &= ~(1 << IOPHardwareRegisters.IOP_EVT_SIO);
		}

	}

	public void sioInterrupt() {

	}

//	public void initializeSIO(final byte value) {
//		switch (value) {
//			case 0x01: // start pad
//				statReg &= ~TX_EMPTY; // Now the Buffer is not empty
//				statReg |= RX_RDY; // Transfer is Ready
//
//				bufcount = 4; // Default size, when no pad connected.
//				parp = 0;
//				padst = 1;
//				packetsize = 1;
//				count = 0;
//				sio2.packet.recvVal1 = 0x1100; // Pad is present
//
//				if ((ctrlReg & 2) == 2) {
//					int padslot = (ctrlReg >> 12) & 2; // move 0x2000 bitmask
//					// into
//					// leftmost bits
//					if (padslot != 1) {
//						padslot >>= 1; // transform 0/2 to be 0/1 values
//
//						if (!PADsetSlot(padslot + 1, 1 + activePadSlot[padslot]) && activePadSlot[padslot]) {
//							// Pad is not present. Don't send poll, just return
//							// a
//							// bunch of 0's.
//							sio2.packet.recvVal1 = 0x1D100;
//							padst = 3;
//						} else {
//							buffer[0] = PADstartPoll(padslot + 1);
//						}
//					}
//				}
//
//				SIO_INT();
//				break;
//
//			case 0x21: // start mtap
//				statReg &= ~TX_EMPTY; // Now the Buffer is not empty
//				statReg |= RX_RDY; // Transfer is Ready
//				parp = 0;
//				packetsize = 1;
//				mtapst = 1;
//				count = 0;
//				sio2.packet.recvVal1 = 0x1D100; // Mtap is not connected :(
//				if ((ctrlReg & 2) != 0) // No idea if this test is needed. Pads
//				// use
//				// it, memcards don't.
//				{
//					final int port = getMultitapPort();
//					if (!IsMtapPresent(port)) {
//						// If "unplug" multitap mid game, set active slots to 0.
//						activePadSlot[port] = 0;
//						activeMemcardSlot[port] = 0;
//					} else {
//						bufcount = 3;
//						buffer[0] = (byte) 0xFF;
//						buffer[1] = (byte) 0x80; // Have no idea if this is
//						// correct.
//						// From PSX mtap.
//						buffer[2] = 0x5A;
//						sio2.packet.recvVal1 = 0x1100; // Mtap is connected :)
//					}
//				}
//				SIO_INT();
//				break;
//
//			case 0x61: // start remote control sensor
//				statReg &= ~TX_EMPTY; // Now the Buffer is not empty
//				statReg |= RX_RDY; // Transfer is Ready
//				parp = 0;
//				packetsize = 1;
//				count = 0;
//				sio2.packet.recvVal1 = 0x1100; // Pad is present
//				SIO_INT();
//				break;
//			case (byte) 0x81: // start memcard
//				statReg &= ~TX_EMPTY;
//				statReg |= RX_RDY;
//				memcpy(buffer, cardh, 4);
//				parp = 0;
//				bufcount = 8;
//				mcdst = 1;
//				packetsize = 1;
//				rdwr = 0;
//				count = 0;
//
//				// Memcard presence reporting!
//				// Note:
//				// 0x01100 means Memcard is present
//				// 0x1D100 means Memcard is missing.
//
//				final int port = getMemcardIndex();
//				final int slot = activeMemcardSlot[port];
//
//				if (SysPlugins.McdIsPresent(port, slot)) {
//					sio2.packet.recvVal1 = 0x1100;
//					PAD_LOG("START MEMCARD [port:%d, slot:%d] - Present", port, slot);
//				} else {
//					sio2.packet.recvVal1 = 0x1D100;
//					PAD_LOG("START MEMCARD [port:%d, slot:%d] - Missing", port, slot);
//				}
//
//				SIO_INT();
//				break;
//		}
//	}
//
//	private final void commandWrite(final byte value,final int way) {
	// System.err.printf("sio write8 %x", value);
	//
	// // PAD COMMANDS
	// switch (padst) {
	// case 1: SIO_INT();
	// if ((value&0x40) == 0x40) {
	// padst = 2; parp = 1;
	// switch (ctrlReg&0x2002) {
	// case 0x0002:
	// packetsize ++; // Total packet size sent
	// buffer[parp] = PADpoll(value);
	// break;
	// case 0x2002:
	// packetsize ++; // Total packet size sent
	// buffer[parp] = PADpoll(value);
	// break;
	// }
	// if ((buffer[parp] & 0x0f) == 0) {
	// bufcount = 2 + 32;
	// } else {
	// bufcount = 2 + (buffer[parp] & 0x0f) * 2;
	// }
	// }
	// else padst = 0;
	// return;
	// case 2:
	// parp++;
	// switch (ctrlReg&0x2002) {
	// case 0x0002: packetsize ++; buffer[parp] = PADpoll(value); break;
	// case 0x2002: packetsize ++; buffer[parp] = PADpoll(value); break;
	// }
	// if (parp == bufcount) { padst = 0; return; }
	// SIO_INT();
	// return;
	// case 3:
	// // No pad connected.
	// parp++;
	// if (parp == bufcount) { padst = 0; return; }
	// SIO_INT();
	// return;
	// }
	//
	// // MEMORY CARD COMMANDS
	// switch (mcdst) {
	// case 1:
	// packetsize++;
	// SIO_INT();
	// if (rdwr) { parp++; return; }
	// parp = 1;
	// switch (value) {
	// case 0x11: // RESET
	// PAD_LOG("RESET MEMORY CARD");
	//
	// bufcount = 8;
	// Arrays.fill(buffer, (byte)0xff);
	// buffer[3] = terminator;
	// buffer[2] = '+';
	// mcdst = 99;
	// sio2.packet.recvVal3 = 0x8c;
	// break;
	// case 0x12: // RESET
	// bufcount = 8;
	// Arrays.fill(buffer, (byte)0xff);
	// buffer[3] = terminator;
	// buffer[2] = '+';
	// mcdst = 99;
	//
	// sio2.packet.recvVal3 = 0x8c;
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case (byte)0x81: // COMMIT
	// bufcount = 8;
	// Arrays.fill(buffer, (byte)0xff);
	// mcdst = 99;
	// buffer[3] = terminator;
	// buffer[2] = '+';
	// sio2.packet.recvVal3 = 0x8c;
	// if(value == 0x81) {
	// if(mcCommand==0x42)
	// sio2.packet.recvVal1 = 0x1600; // Writing
	// else if(mcCommand==0x43) sio2.packet.recvVal1 = 0x1700; // Reading
	// }
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case 0x21:
	// case 0x22:
	// case 0x23: // SECTOR SET
	// bufcount = 8; mcdst = 99; sector=0; k=0;
	// Arrays.fill(buffer, (byte)0xff);
	// sio2.packet.recvVal3 = 0x8c;
	// buffer[8]=terminator;
	// buffer[7]='+';
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case 0x24:
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case 0x25:
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case 0x26:
	// bufcount = 12; mcdst = 99; sio2.packet.recvVal3 = 0x83;
	// Arrays.fill(buffer, (byte)0xff);
	// memcpy(buf[2], mcCommand_0x26, sizeof(mcCommand_0x26));
	// buffer[12]=terminator;
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case 0x27:
	// case 0x28:
	// case (byte)0xBF:
	// bufcount = 4; mcdst = 99; sio2.packet.recvVal3 = 0x8b;
	// Arrays.fill(buffer, (byte)0xff);
	// buffer[4]=terminator;
	// buffer[3]='+';
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case 0x42: // WRITE
	// case 0x43: // READ
	// case (byte)0x82:
	// // fixme: THEORY! Clearing either sector or lastsector when loading from
	// // savestate may safely invalidate games' memorycard caches! -- air
	// if(value==0x82 && lastsector==sector) mode = 2;
	// if(value==0x42) mode = 0;
	// if(value==0x43) lastsector = sector; // Reading
	//
	// bufcount =133; mcdst = 99;
	// Arrays.fill(buffer, (byte)0xff);
	// buffer[133]=terminator;
	// buffer[132]='+';
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case (byte)0xf0:
	// case (byte)0xf1:
	// case (byte)0xf2:
	// mcdst = 99;
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case (byte)0xf3:
	// case (byte)0xf7:
	// bufcount = 4; mcdst = 99;
	// Arrays.fill(buffer, (byte)0xff);
	// buffer[4]=terminator;
	// buffer[3]='+';
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case 0x52:
	// rdwr = 1; Arrays.fill(buffer, (byte)0xff);
	// buffer[bufcount]=terminator; buffer[bufcount-1]='+';
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// case 0x57:
	// rdwr = 2; Arrays.fill(buffer, (byte)0xff);
	// buffer[bufcount]=terminator; buf[bufcount-1]='+';
	// System.err.printf("MC(%d) command 0x%02X", getMemcardIndex()+1, value);
	// break;
	// default:
	// mcdst = 0;
	// Arrays.fill(buffer, (byte)0xff);
	// buffer[bufcount]=terminator; buffer[bufcount-1]='+';
	// System.err.printf("Unknown MC(%d) command 0x%02X", getMemcardIndex()+1,
	// value);
	// }
	// mcCommand=value;
	// return;
	// // FURTHER PROCESSING OF THE MEMORY CARD COMMANDS
	// case 99:
	// packetsize++;
	// parp++;
	// switch(mcCommand)
	// {
	// // SET_ERASE_PAGE; the next erase commands will *clear* data starting
	// with the page set here
	// case 0x21:
	// // SET_WRITE_PAGE; the next write commands will commit data starting with
	// the page set here
	// case 0x22:
	// // SET_READ_PAGE; the next read commands will return data starting with
	// the page set here
	// case 0x23:
	// if (parp==2)sector|=(value & 0xFF)<< 0;
	// if (parp==3)sector|=(value & 0xFF)<< 8;
	// if (parp==4)sector|=(value & 0xFF)<<16;
	// if (parp==5)sector|=(value & 0xFF)<<24;
	// if (parp==6)
	// {
	// if (sio_xor((byte)sector, 4) == value)
	// System.err.printf("MC(%d) SET PAGE sector, sector=0x%04X",
	// getMemcardIndex()+1, sector);
	// else
	// System.err.printf("MC(%d) SET PAGE XOR value ERROR 0x%02X != ^0x%02X",
	// GetMemcardIndex()+1, value, sio_xor((byte)sector, 4));
	// }
	// break;
	//
	// // SET_TERMINATOR; reads the new terminator code
	// case 0x27:
	// if(parp==2) {
	// terminator = value;
	// buf[4] = value;
	// System.err.printf("MC(%d) SET TERMINATOR command, value=0x%02X",
	// GetMemcardIndex()+1, value);
	//
	// }
	// break;
	//
	// // GET_TERMINATOR; puts in position 3 the current terminator code and in
	// 4 the default one
	// // depending on the param
	// case 0x28:
	// if(parp == 2) {
	// buf[2] = '+';
	// buf[3] = terminator;
	//
	// //if(value == 0) buf[4] = 0xFF;
	// buf[4] = 0x55;
	// System.err.printf("MC(%d) GET TERMINATOR command, value=0x%02X",
	// GetMemcardIndex()+1, value);
	// }
	// break;
	// // WRITE DATA
	// case 0x42:
	// if (parp==2) {
	// bufcount=5+value;
	// Arrays.fill(buffer, (byte)0xff);
	// buffer[bufcount-1]='+';
	// buffer[bufcount]=terminator;
	// System.err.printf("MC(%d) WRITE command, size=0x%02X",
	// GetMemcardIndex()+1, value);
	// }
	// else
	// if ((parp>2) && (parp<bufcount-2)) {
	// buf[parp]=value;
	// //System.err.printf("MC(%d) WRITING 0x%02X", GetMemcardIndex()+1, value);
	// } else
	// if (parp==bufcount-2) {
	// if (sio_xor(buf[3], bufcount-5)==value) {
	// _SaveMcd(buf[3], (512+16)*sector+k, bufcount-5);
	// buf[bufcount-1]=value;
	// k+=bufcount-5;
	// } else {
	// System.err.printf("MC(%d) write XOR value error 0x%02X != ^0x%02X",
	// GetMemcardIndex()+1, value, sio_xor(buf[3], bufcount-5));
	// }
	// }
	// break;
	// // READ DATA
	// case 0x43:
	// if (parp==2)
	// {
	// //int i;
	// bufcount=value+5;
	// buf[3]='+';
	// System.err.printf("MC(%d) READ command, size=0x%02X",
	// GetMemcardIndex()+1, value);
	// _ReadMcd(buf[4], (512+16)*sector+k, value);
	//
	// /*if(mode==2)
	// {
	// int j;
	// for(j=0; j < value; j++)
	// buf[4+j] = ~buf[4+j];
	// }*/
	//
	// k+=value;
	// buf[bufcount-1]=sio_xor(buf[4], value);
	// buf[bufcount]=terminator;
	// }
	// break;
	// // INTERNAL ERASE
	// case (byte)0x82:
	// if(parp==2)
	// {
	// buf[2]='+';
	// buf[3]=terminator;
	// //if (k != 0 || (sector & 0xf) != 0)
	// // Console.Notice("saving : odd position for erase.");
	//
	// _EraseMCDBlock((512+16)*(sector&~0xf));
	//
	// /* memset(buf, -1, 256);
	// _SaveMcd(buf, (512+16)*sector, 256);
	// _SaveMcd(buf, (512+16)*sector+256, 256);
	// _SaveMcd(buf, (512+16)*sector+512, 16);
	// buf[2]='+';
	// buf[3]=terminator;*/
	// //buf[bufcount] = terminator;
	// System.err.printf("MC(%d) INTERNAL ERASE command 0x%02X",
	// GetMemcardIndex()+1, value);
	// }
	// break;
	// // CARD AUTHENTICATION CHECKS
	// case (byte)0xF0:
	// if (parp==2)
	// {
	// System.err.printf("MC(%d) CARD AUTH :0x%02X", GetMemcardIndex()+1,
	// value);
	// switch(value){
	// case 1:
	// case 2:
	// case 4:
	// case 15:
	// case 17:
	// case 19:
	// bufcount=13;
	// Arrays.fill(buffer, (byte)0xff);
	// buf[12] = 0; // Xor value of data from index 4 to 11
	// buf[3]='+';
	// buf[13] = terminator;
	// break;
	// case 6:
	// case 7:
	// case 11:
	// bufcount=13;
	// Arrays.fill(buffer, (byte)0xff);
	// buf[12]='+';
	// buf[13] = terminator;
	// break;
	// default:
	// bufcount=4;
	// Arrays.fill(buffer, (byte)0xff);
	// buffer[3]='+';
	// buffer[4] = terminator;
	// }
	// }
	// break;
	// }
	// if (bufcount<=parp) mcdst = 0;
	// return;
	// }
	//
	// switch (mtapst)
	// {
	// case 0x1:
	// packetsize++;
	// parp = 1;
	// SIO_INT();
	// switch(value) {
	// case 0x12:
	// // Query number of pads supported.
	// buf[3] = 4;
	// mtapst = 2;
	// bufcount = 5;
	// break;
	// case 0x13:
	// // Query number of memcards supported.
	// buf[3] = 4;
	// mtapst = 2;
	// bufcount = 5;
	// break;
	// case 0x21:
	// // Set pad slot.
	// mtapst = value;
	// bufcount = 6; // No idea why this is 6, saved from old code.
	// break;
	// case 0x22:
	// // Set memcard slot.
	// mtapst = value;
	// bufcount = 6; // No idea why this is 6, saved from old code.
	// break;
	// }
	// // Commented out values are from original code. They break multitap in
	// bios.
	// buf[bufcount-1]=0;//'+';
	// buf[bufcount]=0;//'Z';
	// return;
	// case 0x2:
	// packetsize++;
	// parp++;
	// if (bufcount<=parp) mcdst = 0;
	// SIO_INT();
	// return;
	// case 0x21:
	// // Set pad slot.
	// packetsize++;
	// parp++;
	// mtapst = 2;
	// if (CtrlReg & 2)
	// {
	// final int port = getMultitapPort();
	// if (IsMtapPresent(port))
	// activePadSlot[port] = value;
	// }
	// SIO_INT();
	// return;
	// case 0x22:
	// // Set memcard slot.
	// packetsize++;
	// parp++;
	// mtapst = 2;
	// if (CtrlReg & 2)
	// {
	// final int port = getMultitapPort();
	// if (IsMtapPresent(port))
	// activeMemcardSlot[port] = value;
	// }
	// SIO_INT();
	// return;
	// }
	//
	// if(count == 1 || way == 0) {
	// InitializeSIO(value);
	// }
	// }
}
