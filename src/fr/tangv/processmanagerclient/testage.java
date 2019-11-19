package fr.tangv.processmanagerclient;

import java.util.HashMap;
import java.util.Map;

public class testage {

	public static void main(String[] args) {
		String code = "<salut> saojzefnn ze nunzfuze heello|<export=saddlut>'jhfuze huzegfuze yufgzefgze fze fg'</export>boco ahnc ugzzyrz ryg";
		
		Map<String, String> maps = new HashMap<String, String>();
		while (code.contains("<export=")) {
			int startOneBalise = code.indexOf("<export=")+8;
			int endOneBalise = code.indexOf(">", startOneBalise);
			String nameBalise = code.substring(startOneBalise, endOneBalise);
			int endBalise = code.indexOf("</export>", endOneBalise);
			String contBalise = code.substring(endOneBalise+1, endBalise);
			code = code.substring(0, startOneBalise-8)+code.substring(endBalise+9, code.length());
			if (maps.containsKey(nameBalise))
				maps.replace(nameBalise, contBalise);
			else
				maps.put(nameBalise, contBalise);
		}

	}

}
