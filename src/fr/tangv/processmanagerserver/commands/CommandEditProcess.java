package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;
import fr.tangv.processmanagerserver.util.ProcessPlus;

public class CommandEditProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandEditProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		String args[] = arg.split("~");
		if (args.length == 4) {
			if (processManagerServer.getProcessManager().hasProcess(args[0])) {
				try {
					boolean activeOnStart = Boolean.parseBoolean(args[3]);
					try {
						ProcessPlus process = processManagerServer.getProcessManager().getProcess(args[0]);
						process.setCmd(args[1]);
						process.setRep(args[2]);
						process.setActiveOnStart(activeOnStart);
						processManagerServer.getProcessManager().saveProcces(process.getName());
						sender.send("Process \""+process.getName()+"\" is edit !");
						return true;
					} catch (Exception e) {
						ProcessManagerServer.logger.warning("Error command editprocess: "+e.getMessage());
						sender.send("Error command editprocess !");
						return true;
					}
				} catch (Exception e) {
					return false;
				}
			} else {
				sender.send("No found process !");
				return true;
			}
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "editprocess <name>~<cmd>~<rep>~<activeonstart>";
	}

	@Override
	public String getDescription() {
		return "Edit process";
	}

}
