package com.jps2.plugins.zzogl;

public final class Vertex {
	// note: xy is 12d3
	public short x, y, f, resv0;
	public int rgba;
	public int z;
	public float s, t, q;
	// Texel coordinate of vertex. Used if prim.fst == 1
	// Bits 0-14 and 16-30 of UV
	public short u, v;
}
