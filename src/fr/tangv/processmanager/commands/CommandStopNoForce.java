package fr.tangv.processmanager.commands;

import java.io.IOException;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;

public class CommandStopNoForce implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandStopNoForce(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		try {
			sender.send("Server StopingNoForce !");
			processManagerServer.stopNoForce();
		} catch (IOException e) {
			ProcessManagerServer.logger.warning(e.getMessage());
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "stopnoforce";
	}

	@Override
	public String getDescription() {
		return "StopNoForce this server and its process.";
	}

}
