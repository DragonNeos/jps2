package com.jps2.plugins.zzogl;

import com.jps2.plugins.zzogl.info.BufInfo;
import com.jps2.plugins.zzogl.info.ClutInfo;
import com.jps2.plugins.zzogl.info.PathInfo;
import com.jps2.plugins.zzogl.info.PrimInfo;
import com.jps2.plugins.zzogl.info.TexaInfo;
import com.jps2.plugins.zzogl.info.TrxPosInfo;

public final class GSInternal {
	public Vertex[] gsvertex = new Vertex[3];
	public int rgba;
	public float q;
	public Vertex vertexregs;
	// number of verts current storing
	public int primC;
	// current prim index
	public int primIndex;
	public int nTriFanVert;

	public int prac;
	public int dthe;
	public int colclamp;
	public int fogcol;
	public int smask;
	public int pabe;
	public long[] buff = new long[2];
	public int buffsize;
	// internal cbp registers
	public int[] cbp = new int[2];

	public int CSRw;

	public PrimInfo[] prim = new PrimInfo[2];
	public BufInfo srcbuf;
	public BufInfo srcbufnew;
	public BufInfo dstbuf;
	public BufInfo dstbufnew;

	public ClutInfo clut;

	public TexaInfo texa;
	public TrxPosInfo trxpos;
	public TrxPosInfo trxposnew;

	public int imageWtemp;
	public int imageHtemp;

	public int imageTransfer;
	public int imageWnew;
	public int imageHnew;
	public int imageX;
	public int imageY;
	public int imageEndX;
	public int imageEndY;

	public PathInfo path1;
	public PathInfo path2;
	public PathInfo path3;
}
