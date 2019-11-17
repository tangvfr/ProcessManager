package fr.tangv;

import fr.tangv.processmanagerserver.ProcessManagerServer;

public class Main {

	public static String version = "Alpha_1.2";
	
	public static void main(String[] args) {
		if (args.length >= 1 && args[0].equalsIgnoreCase("-server")) {
			ProcessManagerServer pms = new ProcessManagerServer();
			if (args.length >= 2 && args[1].equalsIgnoreCase("-web")) {
				
			}
		}
	}
	
}
