package web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;
import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageData;
import fr.tangv.web.util.PageRedirectSeeOther;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class console implements ClassPage {

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
				
				if (data != null && data.containsKey("token")) {
					Token token = auth.tokenValid(data.get("token"));
					if (token != null) {
						Map<String, String> remplaceValue = new HashMap<String, String>();
						//maj
						String textMaj = Main.getUpdate(true);
						if (textMaj != "ProcessManager est à jour !") {
							PageResoucre baliseMaj = new PageResoucre(pageResoucre.getContent("update"), "barg", false);
							Map<String, String> mapMaj = new HashMap<String, String>();
							textMaj = baliseMaj.remplaceText(mapMaj);
							remplaceValue.put("update", decodingUTF8(textMaj));
						}
						//general
						remplaceValue.put("version", decodingUTF8(Main.version));
						remplaceValue.put("token", token.toString());
						remplaceValue.put("username", token.getUser());
						remplaceValue.put("link", data.get("link"));
						//data
						ProcessManager processManager = Main.processManagerServer.getProcessManager();
						String name = data.get("name");
						if (!name.isEmpty() && processManager.hasProcess(name)) {
							ProcessPlus process = processManager.getProcess(name);
							String[] startedBalise = pageResoucre.getContent("started").split(",");
							remplaceValue.put("started", process.getProcess().isStart() ? startedBalise[0] : startedBalise[1]);
							String[] launchBalise = pageResoucre.getContent("launch").split(",");
							remplaceValue.put("launch", process.isActiveOnStart() ? launchBalise[0] : launchBalise[1]);
							remplaceValue.put("name", process.getName());
							remplaceValue.put("cmd", process.getCmd());
							remplaceValue.put("stopcmd", process.getCmdStop());
							remplaceValue.put("folder", process.getFolder());
						} else {
							return new PageRedirectSeeOther(data.get("link"));
						}
						//return page
						return new Page(pageResoucre.remplaceText(remplaceValue), PageType.HTML, CodeHTTP.CODE_200_OK);
					}
				}
				return new PageRedirectSeeOther("/invalide.html");
			} else {
				return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_405_METHOD_NOT_ALLOWED);
			}
		} catch (Exception e) {
			return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_500_INTERNAL_SERVER_ERROR);
		}
	}
	
}
