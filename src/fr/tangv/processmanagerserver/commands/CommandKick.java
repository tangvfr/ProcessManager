package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.sender.Sender;

public class CommandKick implements Command {

	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		
		return false;
	}

	@Override
	public String getUsage() {
		return "kick <name>";
	}

	@Override
	public String getDescription() {
		return "Make kick one client connect !";
	}

}
