package fr.tangv.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class ByteArray {

	private byte[] out;
	
	public ByteArray(InputStream in, int length) throws IOException {
		out = new byte[length];
		while (in.available() >= length);
		in.read(out, 0, length);
	}
	
	public ByteArray(Reader in, int length) throws IOException {
		char[] outChar = new char[length];
		in.read(outChar, 0, length);
		out = new String(outChar).getBytes();
	}
	
	public byte[] bytes() {
		return out;
	}
	
	public int length() {
		return out.length;
	}
	
}
