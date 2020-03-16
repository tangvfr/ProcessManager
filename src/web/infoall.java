package web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import fr.tangv.processmanager.Main;
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

public class infoall implements ClassPage {

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
						PageResoucre baliseMaj = new PageResoucre(pageResoucre.getContent("update"), "barg", false);
						Map<String, String> mapMaj = new HashMap<String, String>();
						mapMaj.put("text", textMaj == "ProcessManager est à jour !" ? "" : textMaj);
						textMaj = baliseMaj.remplaceText(mapMaj);
						remplaceValue.put("update", decodingUTF8(textMaj));
						//general
						remplaceValue.put("version", decodingUTF8(Main.version));
						remplaceValue.put("token", token.toString());
						//data
						remplaceValue.put("cmdend", Main.cmdEnd);
						remplaceValue.put("timestopnoforce", Main.timeStopNoForce+"");
			            remplaceValue.put("timestart", Main.timeStart+"");
			            remplaceValue.put("timeisstart", Main.timeIsStart+"");
			            remplaceValue.put("timerestart", Main.timeRestart+"");
			            remplaceValue.put("isstop", Main.processManagerServer.isStopNoForce()+"");
			            //process number
			            int processnumber = Main.processManagerServer.getProcessManager().getListProcess().size();
			            int processnumberon = 0;
			            int processnumberdem = 0;
			            for (ProcessPlus process : Main.processManagerServer.getProcessManager().getListProcess()) {
			            	if (process.getProcess().isStart())
			            		processnumberon++;
			            	if (process.isActiveOnStart())
			            		processnumberdem++;
			            }
			            int processnumberoff = processnumber-processnumberon;
			            int processnumbernodem = processnumber-processnumberdem;
			            remplaceValue.put("processnumber", processnumber+"");
			            remplaceValue.put("processnumberon", processnumberon+"");
			            remplaceValue.put("processnumberoff", processnumberoff+"");
			            remplaceValue.put("processnumberdem", processnumberdem+"");
			            remplaceValue.put("processnumbernodem", processnumbernodem+"");
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
