package fr.tangv.processmanagerserver.commands;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;
import fr.tangv.processmanagerserver.util.ProcessPlus;

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
		} else {
			sender.send("No found process !");
			return true;
		}
		return false;
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
