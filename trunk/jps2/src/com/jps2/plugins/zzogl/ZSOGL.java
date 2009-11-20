package com.jps2.plugins.zzogl;

import com.jps2.core.memory.Memory;
import com.jps2.gui.ConfigureComponent;
import com.jps2.plugins.AbstractGSPlugin;
import com.jps2.plugins.event.KeyEvent;
import com.jps2.plugins.zzogl.gif.GIFDecoder;
import com.jps2.plugins.zzogl.gif.GIFRegHandlers;
import com.jps2.plugins.zzogl.info.PathInfo;

public class ZSOGL extends AbstractGSPlugin {

	private final GSInternal	gs	= new GSInternal();

	@Override
	public final long getLastTag() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final void gifSoftReset(final int mask) {
		// TODO Auto-generated method stub

	}

	@Override
	public final void gifTransfer1(final int[] mem, final int addr) {
		gs.path1.tag.nloop = 0;
		gs.path1.tag.eop = 0;

		gifTransferInternal(gs.path1, mem, (0x4000 - addr) / 16);

		if (gs.path1.tag.eop == 0 && gs.path1.tag.nloop > 0) {
			assert ((addr & 0xf) == 0) : "BUG";
			gs.path1.tag.nloop = 0;
			return;
		}
	}

	@Override
	public final void gifTransfer2(final int[] mem, final int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public final void gifTransfer3(final int[] mem, final int size) {
		// TODO Auto-generated method stub

	}

	private final void gifTransferInternal(final PathInfo path, final  int  pMem,final int size){
		while(size > 0){
			if (path.tag.nloop == 0) {
				GIFtag(path, pMem);
				pMem+= 4;
				size--;

				if ((g_GameSettings & GAME_PATH3HACK) && path == gs.path3 && gs.path3.tag.eop){
					nPath3Hack = 1;
				}

				if (path == gs.path1) {
					if (path.mode == 1) {
						// check if 0xb is in any reg, if yes, exit (kh2)
						for(int i = 0; i < path.tag.nreg; i += 4){
							if (((path.regs >> i)& 0xf) == 11){
								ERROR_LOG_SPAM("Invalid unpack type\n");
								path.tag.nloop = 0;
								return;
							}
						}
					}
				}

				if(path.tag.nloop == 0 ) {
					if( path == gs.path1 ) {
						// ffx hack
						if( path.tag.eop != 0){
							return;
						}
						continue;					
					}

					if( path.tag.eop  == 0) {
						continue;
					}

					// Issue 174 fix!
					continue;
//					break;
				}
			}else{
				if (path == gs.path1) {
					printf ("Reread %d %x %x\n", path.tag.nloop, pMem, pMem-0x4000);
					}
			}

			switch(path.mode) {
			case 1: // PACKED
			{
				assert( path.tag.nloop > 0 );
				for(; size > 0; size--, pMem += 4)
				{
					final int reg = (int)((path.regs >> path.regn) & 0xf);
					
					GIFDecoder.handlerPackedReg(gs,reg,pMem);

					path.regn += 4;
					if (path.tag.nreg == path.regn) 
					{
						path.regn = 0;
						if( path.tag.nloop-- <= 1 ) 
						{
							size--;
							pMem += 4;
							break;
						}
					}
				}
				break;
			}
			case 2: // REGLIST
			{
				//GS_LOG("%8.8x%8.8x %d L\n", ((u32*)&gs.regs)[1], *(u32*)&gs.regs, path->tag.nreg/4);
				assert( path.tag.nloop > 0 );
				size *= 2;
				for(; size > 0; pMem+= 2, size--)
				{
					final int reg = (int)((path.regs >> path.regn) & 0xf);
					GIFDecoder.handlerReg(gs,reg,pMem);
					path.regn += 4;
					if (path.tag.nreg == path.regn) 
					{
						path.regn = 0;
						if( path.tag.nloop-- <= 1 ) 
						{
							size--;
							pMem += 2;
							break;
						}
					}
				}

				if( (size & 1) != 0 ) {
					pMem += 2;
				}
				size /= 2;
				break;
			}
			case 3: // GIF_IMAGE (FROM_VFRAM)
			case 4: // Used in the DirectX version, so we'll use it here too.
			{
				if(gs.imageTransfer >= 0 && gs.imageTransfer <= 1)
				{
					final int process = Math.min(size, path.tag.nloop);

					if( process > 0 ) 
					{
						if ( gs.imageTransfer != 0 ) {
//							ZeroGS::TransferLocalHost(pMem, process);
						}else {
//							ZeroGS::TransferHostLocal(pMem, process*4);
						}
						path.tag.nloop -= process;
						pMem += process*4;
						size -= process;

						assert( size == 0 || path.tag.nloop == 0 );
					}
					break;
				}
				else 
				{
					// simulate
					final int process =gifTransferInternalmin(size, path.tag.nloop);
					path.tag.nloop -= process;
					pMem += process*4;
					size -= process;
				}

				break;
			}
			default: // GIF_IMAGE
				GS_LOG("*** WARNING **** Unexpected GIFTag flag\n");
				assert(0);
				path.tag.nloop = 0;
				break;
			}

			if( path == gs.path1 && path.tag.eop  != 0)
				return;
		}

		// This is case when not all data was readed from one try: VU1 to much data.
		// So we should redone reading from start
	  if (path == gs.path1 && size == 0 && path.tag.nloop > 0) {
			ERROR_LOG_SPAMA("VU1 too much data, ignore if gfx are fine %d\n", path.tag.nloop);
//		TODO: this code is not working correctly. Anyway, ringing work only in single-threadred mode.		
//			_GSgifTransfer(&gs.path1, (u32*)((u8*)pMem-0x4000), (0x4000)/16);
		}
	}

	private final void GIFtag(final PathInfo path, final int address) {
		path.tag.nloop	= data[0] & 0x7fff;
		path.tag.eop	= (data[0] >> 15) & 0x1;
		final int tagpre		= (data[1] >> 14) & 0x1;
		final int tagprim		= (data[1] >> 15) & 0x7ff;
		final int tagflg		= (data[1] >> 26) & 0x3;
		path.tag.nreg	= (data[1] >> 28)<<2;
		
		if (path.tag.nreg == 0) {
			path.tag.nreg = 64;
		}

		gs.q = 1;
		 path.mode = tagflg+1;

		switch (tagflg) {
			case 0x0:
				path.regs = *(u64 *)(data+2);
				path.regn = 0;
				if (tagpre != 0){
					GIFRegHandlers.PRIM.handler(tagprim);
				}

				break;

			case 0x1:
				path.regs = *(u64 *)(data+2);
				path.regn = 0;
				break;
		}
	}

	@Override
	public void keyEvent(final KeyEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFIFO(final long[] mem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFIFO2(final long[] mem, final int qwc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFrameSkip(final int frameskip) {
		// TODO Auto-generated method stub

	}

	@Override
	public void vSync(final int field) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ConfigureComponent configure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "ZZOGL Java Port";
	}

	@Override
	public int[] getVersion() {
		return new int[] {
							0,
							0,
							1
		};
	}

	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean test(final StringBuffer messages) {
		// TODO Auto-generated method stub
		return false;
	}

}
