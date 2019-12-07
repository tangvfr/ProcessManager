package fr.tangv.processmanager.commands;

import java.io.IOException;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;

public class CommandRemoveProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandRemoveProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		if (arg.isEmpty()) return false;
		if (processManagerServer.getProcessManager().hasProcess(arg)) {
			try {
				if (processManagerServer.getProcessManager().removeProcess(arg)) {
					sender.send("Process remove successfully !");
				} else {
					sender.send("Process remove error !");
				}
			} catch (IOException e) {
				sender.send("Process remove error !");
				ProcessManagerServer.logger.warning("Process remove error: "+e.getLocalizedMessage());
			}
			return true;
		} else {
			sender.send("No found process !");
			return true;
		}
	}

	@Override
	public String getUsage() {
		return "removeprocess <name>";
	}

	@Override
	public String getDescription() {
		return "Remove process specify";
	}

}
