package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.sender.Sender;

public interface Command {
	
	public boolean command(Sender sender, String cmd, String arg);
	public String getUsage();
	public String getDescription();
	
}
