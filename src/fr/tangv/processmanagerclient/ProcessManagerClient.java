package fr.tangv.processmanagerclient;

import java.io.IOException;

import fr.tangv.processmanagerserver.util.ProcessManager;

public class ProcessManagerClient {
	
	public static void main(String[] args) {
		ProcessManager pm = new ProcessManager();
		try {
			pm.loadProcces("test");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
