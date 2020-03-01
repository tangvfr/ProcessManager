package web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
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

	private String decodingUTF8(String text) throws UnsupportedEncodingException {
		return new String(text.getBytes("UTF8"));
	}
	
	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		try {
			if (receiveHTTP.getMethodeRequet().equalsIgnoreCase("GET")) {
				PageData data = null;
				if (receiveHTTP.getPathRequet().hasData()) {
					data = new PageData(receiveHTTP.getPathRequet().getData());
				}
				
				PrintData.printData(receiveHTTP, data);
				
				if (data != null && data.containsKey("token")) {
					Token token = auth.tokenValid(data.get("token"));
					if (token != null) {
						Map<String, String> remplaceValue = new HashMap<String, String>();
						//maj
						String textMaj = Main.getUpdate(true);
						PageResoucre baliseMaj = new PageResoucre(pageResoucre.getContent("update"), "barg", false);
						Map<String, String> mapMaj = new HashMap<String, String>();
						mapMaj.put("text", textMaj == "ProcessManager est à jour !" ? "" : textMaj);
						textMaj = baliseMaj.remplaceText(mapMaj);
						remplaceValue.put("update", decodingUTF8(textMaj));
						//process
						PageResoucre baliseProcessBox = new PageResoucre(pageResoucre.getContent("processbox"), "barg", false);
						PageResoucre baliseProcessMenu = new PageResoucre(pageResoucre.getContent("processmenu"), "barg", false);
						//general
						remplaceValue.put("version", decodingUTF8(Main.version));
						remplaceValue.put("token", token.toString());
						remplaceValue.put("menuopen", data.get("menu").isEmpty() ? "false" : data.get("menu"));
						remplaceValue.put("search", data.get("search"));
						remplaceValue.put("sort"+data.get("sort"), "selected");
						int page = data.get("page").isEmpty() ? 1 : Integer.parseInt(data.get("page"));
						int maxpage = 0;
						switch (data.get("button")) {
							case "next":
								page++;
								break;
							case "previous":
								page--;
								break;
							default:
								break;
						}
						//calc process
						String textProcessBox = "";
						String textProcessMenu = "";
						ProcessManager processManager = Main.processManagerServer.getProcessManager();
						remplaceValue.put("processnumber", ""+processManager.getListProcess().size());
						maxpage = (processManager.getListProcess().size()/4)+1;
						
						
						
						remplaceValue.put("processbox", textProcessBox);
						remplaceValue.put("processmenu", textProcessMenu);
						//end calc page
						if (page < 1) {
							page = maxpage;
						} else if (page > maxpage) {
							page = 1;
						}
						remplaceValue.put("page", ""+page);
						remplaceValue.put("maxpage", ""+maxpage);
						//return
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
