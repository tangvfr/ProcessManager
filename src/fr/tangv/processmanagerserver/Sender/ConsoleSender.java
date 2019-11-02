package fr.tangv.processmanagerserver.Sender;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;

public class ConsoleSender implements Sender {

	@Override
	public void send(String string) {
		try {
			System.out.write((ProcessManagerServer.getLogsTime()+string).getBytes("UTF8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
