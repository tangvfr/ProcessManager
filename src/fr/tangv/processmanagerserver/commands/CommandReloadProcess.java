package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;
import fr.tangv.processmanagerserver.util.ProcessPlus;

public class CommandReloadProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandReloadProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		if (arg.isEmpty()) return false;
		if (processManagerServer.getProcessManager().hasProcess(arg)) {
			ProcessPlus process = processManagerServer.getProcessManager().getProcess(arg);
			if (!process.getProcess().isStart()) {
				process.reload();
				sender.send("Process is reload !");
				return true;
			} else {
				sender.send("Process is not stoped !");
				return true;
			}
		} else {
			sender.send("No found process !");
			return true;
		}
	}

	@Override
	public String getUsage() {
		return "reloadprocess <name>";
	}

	@Override
	public String getDescription() {
		return "Reload process specify, load new parameter.";
	}

}
