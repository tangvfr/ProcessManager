package fr.tangv;

import fr.tangv.processmanagerclient.ProcessManagerClient;
import fr.tangv.processmanagerserver.ProcessManagerServer;

public class Main {

	public static void main(String[] args) {
		if (args.length >= 1 && args[0].equalsIgnoreCase("-server")) {
			new ProcessManagerServer();
		} else {
			new ProcessManagerClient();
		}
	}
	
}
