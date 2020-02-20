package fr.tangv.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArray {

	private ByteArrayOutputStream out;
	
	public ByteArray(InputStream in) throws IOException {
		out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int length;
		while ((length = in.read(buf)) != -1) {
			out.write(buf, 0, length);
			if (length < 1024 || in.available() <= 0)
				break;
		}
		out.close();
	}
	
	public byte[] bytes() {
		return out.toByteArray();
	}
	
	public int size() {
		return out.size();
	}
	
}
