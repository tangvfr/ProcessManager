package fr.tangv.processmanager.commands;

import java.io.IOException;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;

public class CommandLoadUsers implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandLoadUsers(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		try {
			processManagerServer.loadUsers();
			sender.send("Server load users !");
			return true;
		} catch (IOException e) {
			ProcessManagerServer.LOGGER.warning(e.getMessage());
			return false;
		}
	}

	@Override
	public String getUsage() {
		return "loadusers";
	}

	@Override
	public String getDescription() {
		return "Load users server";
	}

}
