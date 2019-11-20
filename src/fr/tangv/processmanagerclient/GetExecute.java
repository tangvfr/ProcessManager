package fr.tangv.processmanagerclient;

import java.io.IOException;
import java.io.OutputStream;

public class GetExecute implements RequetExecute {
	
	@Override
	public void execute(WebServer webServer, OutputStream out, String typeRequet, String repRequet, String hostRequet,
			String contTypeRequet, String dataRequet, String ipRequet) throws IOException {
		 webServer.sendPageName(out, repRequet);
	}
	
}
