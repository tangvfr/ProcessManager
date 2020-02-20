package fr.tangv.web.util;

import java.util.HashMap;

public class PageData extends HashMap<String, String> {

	private static final long serialVersionUID = -9077560878615243069L;

	public PageData(String data) {
		super();
		try {
			String[] inputs = data.split("&");
			for (String input : inputs) {
				String name = replaceChars(input.substring(0, input.indexOf("=")));
				String value = replaceChars(input.substring(input.indexOf("=")+1, input.length()));
				if (!this.containsKey(name)) {
					this.put(name, value);
				} else {
					this.replace(name, value);
				}
			}
		} catch (Exception e) {
			return;
		}
	}
	
	public static String replaceChars(String string) {
		String text = "";
		char[] cara = string.replace("+", " ").toCharArray();
		for (int i = 0; i < cara.length; i++) {
			if (cara[i] == '%') {
				i++;
				char num1 = cara[i];
				i++;
				char num2 = cara[i];
				int value = Integer.parseInt(""+num1+num2, 16);
				text += (char) value;
			} else {
				text += cara[i];
			}
		}
		return text;
	}
	
}
