package fr.tangv.processmanagerserver.sender;

import fr.tangv.processmanagerserver.ProcessManagerServer;

public class ConsoleSender implements Sender {

	@Override
	public void send(String string) {
		ProcessManagerServer.println(string);
	}

	@Override
	public String getName() {
		return "Console";
	}
	
}
