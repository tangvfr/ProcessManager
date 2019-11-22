package fr.tangv.processmanagerwebserver;

import java.io.IOException;
import java.io.OutputStream;

public interface RequetExecute {

	public void execute(WebServer webServer, OutputStream out, String typeRequet, String repRequet,
			String hostRequet, String contTypeRequet, String dataRequet, String ipRequet) throws IOException;
	
}
