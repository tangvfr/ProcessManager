package fr.tangv.processmanager.commands;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;

public class CommandStopNoForceNoScript implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandStopNoForceNoScript(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		sender.send("Server StopingNoForceNoScript !");
		processManagerServer.stopNoForce(false);
		return true;
	}

	@Override
	public String getUsage() {
		return "stopnoforcenoscript";
	}

	@Override
	public String getDescription() {
		return "StopNoForceNoScript this server and its process.";
	}

}
