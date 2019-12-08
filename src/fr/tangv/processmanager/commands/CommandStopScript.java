package fr.tangv.processmanager.commands;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;

public class CommandStopScript implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandStopScript(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		sender.send("Server StopingScript !");
		processManagerServer.stopScript();
		return true;
	}

	@Override
	public String getUsage() {
		return "stopscript";
	}

	@Override
	public String getDescription() {
		return "StopScript this server and its process.";
	}

}
