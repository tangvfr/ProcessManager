package web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import fr.tangv.processmanager.ProcessManager;
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
						String textMaj = ProcessManager.UPDATE;
						if (textMaj != "ProcessManager is update !") {
							PageResoucre baliseMaj = new PageResoucre(pageResoucre.getContent("update"), "barg", false);
							Map<String, String> mapMaj = new HashMap<String, String>();
							mapMaj.put("text", decodingUTF8(textMaj));
							remplaceValue.put("update", baliseMaj.remplaceText(mapMaj));
						}
						//process
						remplaceValue.put("version", decodingUTF8(ProcessManager.VERSION));
						remplaceValue.put("token", token.toString());
						remplaceValue.put("username", token.getUser());
						remplaceValue.put("link", data.get("link"));
						//data
						remplaceValue.put("cmdend", ProcessManager.CMD_END);
						remplaceValue.put("timestopnoforce", ProcessManager.TIME_STOP_NO_FORCE+"");
			            remplaceValue.put("timestart", ProcessManager.TIME_START+"");
			            remplaceValue.put("timeisstart", ProcessManager.TIME_IS_START+"");
			            remplaceValue.put("timerestart", ProcessManager.TIME_RESTART+"");
			            remplaceValue.put("isstop", ProcessManager.PROCESS_MANAGER_SERVER.isStopNoForce()+"");
			            //process number
			            int processnumber = ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager().getListProcess().size();
			            int processnumberon = 0;
			            int processnumberdem = 0;
			            for (ProcessPlus process : ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager().getListProcess()) {
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
