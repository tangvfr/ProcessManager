package fr.tangv.web.util;

import java.io.IOException;
import java.util.Vector;

public class PageResoucre extends Vector<String> {

	private static final long serialVersionUID = -8998105352012578804L;

	public PageResoucre(String path, String split) throws IOException {
		super();
		if (ClassLoader.getSystemResource(path) != null) {
			String text = new String(new ByteArray(ClassLoader.getSystemResourceAsStream(path)).bytes());
			for (String add : text.split(split)) {
				this.add(add);
			}
		}
	}
	
}
