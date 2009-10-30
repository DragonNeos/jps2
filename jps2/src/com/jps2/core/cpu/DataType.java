package com.jps2.core.cpu;

public enum DataType {
	DOUBLEWORD(8), /**/
	SEPTIBYTE(7), /**/
	SEXTIBYTE(6), /**/
	QUINTIBYTE(5), /**/
	WORD(4), /**/
	TRIPLEBYTE(3), /**/
	HALFWORD(2), /**/
	BYTE(1);

	public final int	lenght;

	DataType(final int lenght) {
		this.lenght = lenght;
	}
}
