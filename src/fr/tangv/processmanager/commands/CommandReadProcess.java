package fr.tangv.processmanager.commands;

import java.io.IOException;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandReadProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandReadProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		if (arg.isEmpty()) return false;
		if (processManagerServer.getProcessManager().hasProcess(arg)) {
			ProcessPlus process = processManagerServer.getProcessManager().getProcess(arg);
			try {
				String text = "Readprocess "+process.getProcess().getInput()+process.getProcess().getError();
				sender.send(text);
			} catch (IOException e) {
				sender.send("Process read error !");
				ProcessManagerServer.logger.warning("Process read error: "+e.getLocalizedMessage());
			}
			return true;
		} else {
			sender.send("No found process !");
			return true;
		}
	}

	@Override
	public String getUsage() {
		return "readprocess <name>";
	}

	@Override
	public String getDescription() {
		return "Read process specify";
	}

}
