package fr.tangv.web.util;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageResoucre {

	private String nameBalise;
	private String text;
	
	public PageResoucre(String path, String nameBalise) throws IOException {
		this(path, nameBalise, true);
	}
	
	public PageResoucre(String text, String nameBalise, boolean textIsPathFile) throws IOException {
		this.nameBalise = nameBalise;
		if (textIsPathFile) {
			if (ClassLoader.getSystemResource(text) != null) {
				this.text = new String(new ByteArray(ClassLoader.getSystemResourceAsStream(text)).bytes());
			}
		} else {
			this.text = text;
		}
	}
	
	public String getText() {
		return this.text;
	}
	
	public String remplaceText(Map<String, String> remplaceValue) {
		Pattern pattern = Pattern.compile("(?s)<"+nameBalise+"=([^>]*)>(.*?)</"+nameBalise+">", Pattern.UNICODE_CASE & Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(this.text);
		String text = this.text;
		while (matcher.find()) {
			String value = matcher.group(1);
			String balise = "<"+nameBalise+"="+value+">"+matcher.group(2)+"</"+nameBalise+">";
			if (remplaceValue != null && remplaceValue.containsKey(value)) {
				text = text.replace(balise, remplaceValue.get(value));
			} else {
				text = text.replace(balise, "");
			}
		}
		return text;
	}
	
	public String getContent(String value) {
		Pattern pattern = Pattern.compile("(?s)<"+nameBalise+"="+value+">(.*?)</"+nameBalise+">", Pattern.UNICODE_CASE & Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(this.text);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
	
	public String removeAllBalise(String text) {
		return text.replaceAll("<[^>]*>", "");
	}
	
}
