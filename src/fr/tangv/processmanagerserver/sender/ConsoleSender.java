package fr.tangv.processmanagerserver.sender;

import java.util.logging.Level;

import fr.tangv.processmanagerserver.ProcessManagerServer;

public class ConsoleSender implements Sender {

	@Override
	public void send(String string) {
		ProcessManagerServer.logger.log(Level.ALL, string);
	}

	@Override
	public String getName() {
		return "Console";
	}
	
}
