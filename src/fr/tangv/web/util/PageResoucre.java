package fr.tangv.web.util;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageResoucre {

	private String nameBalise;
	private String text;
	
	public PageResoucre(String path, String nameBalise) throws IOException {
		this.nameBalise = nameBalise;
		if (ClassLoader.getSystemResource(path) != null) {
			text = new String(new ByteArray(ClassLoader.getSystemResourceAsStream(path)).bytes());
		}
	}
	
	public PageResoucre(char[] text, String nameBalise) {
		this.nameBalise = nameBalise;
		this.text = new String(text);
	}
	
	public String getText() {
		return this.text;
	}
	
	public String remplaceText(Map<String, String> remplaceValue) {
		Pattern pattern = Pattern.compile("<"+nameBalise+"=(\\w*)>", Pattern.UNICODE_CASE);
		Matcher matcher = pattern.matcher(this.text);
		String text = this.text;
		while (matcher.find()) {
			String valueBalise = matcher.group(1);
			if (remplaceValue != null && remplaceValue.containsKey(valueBalise)) {
				text = text.replace("<"+nameBalise+"="+valueBalise+">", remplaceValue.get(valueBalise));
			} else {
				text = text.replace("<"+nameBalise+"="+valueBalise+">", "");
			}
		}
		return text;
	}
	
}
