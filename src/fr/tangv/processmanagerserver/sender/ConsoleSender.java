package fr.tangv.processmanagerserver.sender;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;

public class ConsoleSender implements Sender {

	@Override
	public void send(String string) {
		try {
			ProcessManagerServer.out.write((ProcessManagerServer.getTime()+string).getBytes("UTF8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "Console";
	}
	
}
