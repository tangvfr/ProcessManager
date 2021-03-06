package fr.tangv.processmanager.commands;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;

public class CommandStopNoForce implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandStopNoForce(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		sender.send("Server StopingNoForce !");
		processManagerServer.stopNoForce(true);
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
