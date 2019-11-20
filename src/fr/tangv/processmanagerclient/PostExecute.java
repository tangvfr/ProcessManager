package fr.tangv.processmanagerclient;

import java.io.IOException;
import java.io.OutputStream;

public class PostExecute implements RequetExecute {

	@Override
	public void execute(WebServer webServer, OutputStream out, String typeRequet, String repRequet, String hostRequet,
			String contTypeRequet, String dataRequet, String ipRequet) throws IOException {
		System.out.println(contTypeRequet);
		System.out.println(dataRequet);
		System.out.println(repRequet);
		webServer.sendPageName(out, repRequet);
	}

}
