package fr.tangv;

import fr.tangv.processmanager.webserver.GetExecute;
import fr.tangv.processmanager.webserver.PostExecute;
import fr.tangv.processmanager.webserver.ProcessAffiche;
import fr.tangv.processmanager.webserver.WebServer;
import fr.tangv.processmanagerserver.ProcessManagerServer;

public class Main {

	public static String version = "Alpha_1.4";
	
	public static void main(String[] args) {
		ProcessManagerServer pms = new ProcessManagerServer();
		if (args.length >= 1 && args[0].equalsIgnoreCase("-web")) {
			try {
				int port = args.length >= 2 ? Integer.parseInt(args[1]) : 80;
				WebServer webServer = new WebServer(port, pms);
				//----------------------------
				webServer.addGetRequetExecutes(new GetExecute());
				webServer.addPostRequetExecutes(new PostExecute());
				ProcessAffiche processAffiche = new ProcessAffiche();
				webServer.registreHandle("noProcess", processAffiche);
				webServer.registreHandle("oneProcess", processAffiche);
				//----------------------------
				ProcessManagerServer.logger.info("Open WebServer with port \""+port+"\" !");
			} catch (Exception e) {
				ProcessManagerServer.logger.warning("Error WebServer port is invalid !");
			}
		}
	}
	
}
