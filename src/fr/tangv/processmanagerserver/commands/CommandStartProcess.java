package fr.tangv.processmanagerserver.commands;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;
import fr.tangv.processmanagerserver.util.ProcessPlus;

public class CommandStartProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandStartProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		if (arg.isEmpty()) return false;
		if (processManagerServer.getProcessManager().hasProcess(arg)) {
			ProcessPlus process = processManagerServer.getProcessManager().getProcess(arg);
			if (!process.getProcess().isStart()) {
				try {
					process.getProcess().start();
					sender.send("Process is start !");
				} catch (IOException e) {
					sender.send("Process start error !");
					ProcessManagerServer.logger.warning("Process start error: "+e.getLocalizedMessage());
				}
				return true;
			} else {
				sender.send("Process already started !");
				return true;
			}
		} else {
			sender.send("No found process !");
			return true;
		}
	}

	@Override
	public String getUsage() {
		return "startprocess <name>";
	}

	@Override
	public String getDescription() {
		return "Start process specify";
	}

}
