package web;

import java.util.HashMap;
import java.util.Map;

import fr.tangv.processmanager.Main;
import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageData;
import fr.tangv.web.util.PageRedirect;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class info implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		try {
			if (receiveHTTP.getMethodeRequet().equalsIgnoreCase("GET") || receiveHTTP.getMethodeRequet().equalsIgnoreCase("POST")) {
				PageData data = null;
				if (receiveHTTP.hasData()) {
					data = new PageData(new String(receiveHTTP.getData()));
				} else if (receiveHTTP.getPathRequet().hasData()) {
					data = new PageData(receiveHTTP.getPathRequet().getData());
				}
				
				PrintData.printData(receiveHTTP, data);
				
				if (data != null && data.containsKey("token")) {
					Token token = auth.tokenValid(data.get("token"));
					if (token != null) {
						Map<String, String> remplaceValue = new HashMap<String, String>();
						remplaceValue.put("version", new String(Main.version.getBytes("UTF8")));
						remplaceValue.put("token", token.toString());
						remplaceValue.put("menuopen", data.get("menu"));
						remplaceValue.put("search", data.get("search"));
						remplaceValue.put("sort"+data.get("sort"), "selected");
						
						remplaceValue.put("page", data.get("page"));
						remplaceValue.put("maxpage", "2");
						
						System.out.println(pageResoucre.getContent("processbox"));
						return new Page(pageResoucre.remplaceText(remplaceValue), PageType.HTML, CodeHTTP.CODE_200_OK);
					}
				}
				return new PageRedirect("/invalide.html");
			} else {
				return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_405_METHOD_NOT_ALLOWED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_500_INTERNAL_SERVER_ERROR);
		}
	}

}
