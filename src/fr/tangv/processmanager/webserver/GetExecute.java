package fr.tangv.processmanager.webserver;

import java.io.IOException;
import java.io.OutputStream;

import fr.tangv.processmanager.ProcessManagerServer;

public class GetExecute implements RequetExecute {
	
	@Override
	public void execute(WebServer webServer, OutputStream out, String typeRequet, String repRequet, String hostRequet,
			String contTypeRequet, String dataRequet, String ipRequet) throws IOException {
		if (repRequet.equals("/info.tangweb")) {
			if (contTypeRequet.contains(" ")) {
				String name = contTypeRequet.substring(0, contTypeRequet.indexOf(" "));
				String pwd = contTypeRequet.substring(name.length()+1);
				if (webServer.getProcessManagerServer().getUserAndMdp().containsKey(name))
					if (webServer.getProcessManagerServer().getUserAndMdp().get(name).equals(pwd)) {
						webServer.sendPageName(out, repRequet);
						return;
					}
			}
			webServer.getListAntiBrutus().put(ipRequet, System.currentTimeMillis());
			ProcessManagerServer.logger.info(ipRequet+" >: "+contTypeRequet+" >: add_blocked");
			webServer.sendPageName(out, "/invalide.html");
		} else {
			webServer.sendPageName(out, repRequet);
		}
	}
	
}
