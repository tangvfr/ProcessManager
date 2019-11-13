package fr.tangv.processmanagerserver.commands;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;

public class CommandRenameProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandRenameProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		String[] args = arg.split(" ");
		if (args.length == 2) {
			if (processManagerServer.getProcessManager().hasProcess(args[0])) {
				if (!processManagerServer.getProcessManager().hasProcess(args[1])) {
					try {
						if (processManagerServer.getProcessManager().renameProcess(args[0], args[1])) {
							sender.send("Process rename successfully !");
						} else {
							sender.send("Process rename error !");
						}
					} catch (IOException e) {
						sender.send("Process rename error !");
						ProcessManagerServer.logger.warning("Process rename error: "+e.getLocalizedMessage());
					}
				} else {
					sender.send("Process name already exist !");
				}
				return true;
			} else {
				sender.send("No found process !");
				return true;
			}
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "renameprocess <name> <newname>";
	}

	@Override
	public String getDescription() {
		return "Rename process specify";
	}

}
