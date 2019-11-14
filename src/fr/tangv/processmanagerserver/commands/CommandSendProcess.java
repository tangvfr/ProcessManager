package fr.tangv.processmanagerserver.commands;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;
import fr.tangv.processmanagerserver.util.ProcessPlus;

public class CommandSendProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandSendProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		String[] args = arg.split(" ", 2);
		if (args.length != 2) return false;
		if (processManagerServer.getProcessManager().hasProcess(args[0])) {
			ProcessPlus process = processManagerServer.getProcessManager().getProcess(args[0]);
			if (process.getProcess().isStart()) {
				try {
					if(process.getProcess().send(args[1]))
						sender.send("Send data to process !");
					else
						sender.send("Process send error !");
				} catch (IOException e) {
					sender.send("Process send error !");
					ProcessManagerServer.logger.warning("Process send error: "+e.getLocalizedMessage());
				}
				return true;
			} else {
				sender.send("Process is stoped !");
				return true;
			}
		} else {
			sender.send("No found process !");
			return true;
		}
	}

	@Override
	public String getUsage() {
		return "sendprocess <name> <data>";
	}

	@Override
	public String getDescription() {
		return "Send process specify";
	}

}
