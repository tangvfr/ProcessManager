package fr.tangv.processmanagerwebserver;

import java.io.IOException;

public interface HandleBaliseExport {

	public String handle(WebServer webServeur, String name, String cont) throws IOException;
	
}
