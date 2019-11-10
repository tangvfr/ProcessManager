package fr.tangv.processmanagerserver.commands;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;

public class CommandSavePara implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandSavePara(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		try {
			processManagerServer.saveParameter();
			sender.send("Server save parameter !");
			ProcessManagerServer.logger.info("Server save parameter !");
			return true;
		} catch (IOException e) {
			ProcessManagerServer.logger.warning(e.getMessage());
			return false;
		}
	}

	@Override
	public String getUsage() {
		return "/savepara";
	}

	@Override
	public String getDescription() {
		return "Save parameter server";
	}

}
