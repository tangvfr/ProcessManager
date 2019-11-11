package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.sender.Sender;

public class CommandListProcess implements Command {

	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUsage() {
		return "listprocess <name>";
	}

	@Override
	public String getDescription() {
		return "List all process.";
	}
	
}
