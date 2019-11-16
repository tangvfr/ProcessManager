package fr.tangv.processmanagerclient;

import fr.tangv.processmanagerclient.windows.BootStrap;

public class ProcessManagerClient {
	
	public ProcessManagerClient() {
		BootStrap bootStrap = new BootStrap();
		bootStrap.setVisible(true);
	}
	
}
