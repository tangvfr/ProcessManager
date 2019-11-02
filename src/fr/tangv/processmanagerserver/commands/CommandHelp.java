package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;

public class CommandHelp implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandHelp(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String string, String arg) {
		String text = "Command help: \n";
		for (String nameCmd : processManagerServer.getCmdManager().getCommands().keySet()) {
			Command cmd = processManagerServer.getCmdManager().getCommands().get(nameCmd);
			text += " - "+nameCmd+" > "+cmd.getUsage()+"\n"
			+"Description: "+cmd.getDescription()+"\n";
		}
		sender.send(text);
		return true;
	}

	@Override
	public String getUsage() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "List all command and their description.";
	}

}
