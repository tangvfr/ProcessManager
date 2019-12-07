package fr.tangv.processmanager.sender;

import fr.tangv.processmanager.ProcessManagerServer;

public class ConsoleSender implements Sender {

	@Override
	public void send(String string) {
		ProcessManagerServer.logger.info(string);
	}

	@Override
	public String getName() {
		return "Console";
	}
	
}
