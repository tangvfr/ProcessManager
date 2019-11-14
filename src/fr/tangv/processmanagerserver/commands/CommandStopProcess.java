package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;
import fr.tangv.processmanagerserver.util.ProcessPlus;

public class CommandStopProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandStopProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		if (arg.isEmpty()) return false;
		if (processManagerServer.getProcessManager().hasProcess(arg)) {
			ProcessPlus process = processManagerServer.getProcessManager().getProcess(arg);
			if (process.getProcess().isStart()) {
				process.getProcess().stop();
				sender.send("Process is stop !");
			} else {
				sender.send("Process already stoped !");
				return true;
			}
			return true;
		} else {
			sender.send("No found process !");
			return true;
		}
	}

	@Override
	public String getUsage() {
		return "stopprocess <name>";
	}

	@Override
	public String getDescription() {
		return "Stop process specify";
	}

}
