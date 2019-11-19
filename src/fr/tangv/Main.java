package fr.tangv;

import fr.tangv.processmanagerclient.WebServer;
import fr.tangv.processmanagerserver.ProcessManagerServer;

public class Main {

	public static String version = "Alpha_1.2";
	
	public static void main(String[] args) {
		if (args.length >= 1 && args[0].equalsIgnoreCase("-server")) {
			ProcessManagerServer pms = new ProcessManagerServer();
			if (args.length >= 3 && args[1].equalsIgnoreCase("-web")) {
				try {
					int port = Integer.parseInt(args[2]);
					WebServer webServer = new WebServer(port, pms);
					ProcessManagerServer.logger.info("Open WebServer with port \""+port+"\" !");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
