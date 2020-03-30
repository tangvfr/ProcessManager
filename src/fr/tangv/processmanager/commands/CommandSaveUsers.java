package fr.tangv.processmanager.commands;

import java.io.IOException;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;

public class CommandSaveUsers implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandSaveUsers(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		try {
			processManagerServer.saveUsers();
			sender.send("Server save users !");
			return true;
		} catch (IOException e) {
			ProcessManagerServer.LOGGER.warning(e.getMessage());
			return false;
		}
	}

	@Override
	public String getUsage() {
		return "saveusers";
	}

	@Override
	public String getDescription() {
		return "Save users server";
	}

}
