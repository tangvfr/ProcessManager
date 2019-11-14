package fr.tangv.processmanagerserver.commands;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;
import fr.tangv.processmanagerserver.util.ProcessPlus;

public class CommandRestartProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandRestartProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		if (arg.isEmpty()) return false;
		if (processManagerServer.getProcessManager().hasProcess(arg)) {
			ProcessPlus process = processManagerServer.getProcessManager().getProcess(arg);
			try {
				process.getProcess().stop();
				process.reload();
				process.getProcess().start();
				sender.send("Process is restart !");
			} catch (IOException e) {
				sender.send("Process restart error !");
				ProcessManagerServer.logger.warning("Process restart error: "+e.getLocalizedMessage());
			}
			return true;
		} else {
			sender.send("No found process !");
			return true;
		}
	}

	@Override
	public String getUsage() {
		return "restartprocess <name>";
	}

	@Override
	public String getDescription() {
		return "Restart and reload process specify";
	}

}
