package fr.tangv.processmanager.commands;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandAddProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandAddProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		String args[] = arg.split("~");
		if (args.length == 4) {
			if (!processManagerServer.getProcessManager().hasProcess(args[0])) {
				try {
					boolean activeOnStart = Boolean.parseBoolean(args[3]);
					try {
						ProcessPlus process = new ProcessPlus(args[0], args[1], args[2], "UTF8", activeOnStart);
						processManagerServer.getProcessManager().addProcess(process);
						sender.send("Process \""+process.getName()+"\" is added !");
						return true;
					} catch (Exception e) {
						ProcessManagerServer.logger.warning("Error command addprocess: "+e.getMessage());
						sender.send("Error command addprocess !");
						return true;
					}
				} catch (Exception e) {
					return false;
				}
			} else {
				sender.send("Process already exist !");
				return true;
			}
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "addprocess <name>~<cmd>~<rep>~<activeonstart>";
	}

	@Override
	public String getDescription() {
		return "Add new process";
	}

}