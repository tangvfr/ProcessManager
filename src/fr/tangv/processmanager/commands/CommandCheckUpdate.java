package fr.tangv.processmanager.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.sender.Sender;

public class CommandCheckUpdate implements Command {

	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		sender.send(Main.getUpdate(false));
		return true;
	}

	@Override
	public String getUsage() {
		return "checkupdate";
	}

	@Override
	public String getDescription() {
		return "Check Update";
	}

}
