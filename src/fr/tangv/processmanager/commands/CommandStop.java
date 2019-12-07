package fr.tangv.processmanager.commands;

import java.io.IOException;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;

public class CommandStop implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandStop(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		try {
			sender.send("Server Stoping !");
			processManagerServer.stop();
		} catch (IOException e) {
			ProcessManagerServer.logger.warning(e.getMessage());
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "stop";
	}

	@Override
	public String getDescription() {
		return "Stop this server and its process.";
	}

}
