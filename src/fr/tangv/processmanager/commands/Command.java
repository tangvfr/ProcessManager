package fr.tangv.processmanager.commands;

import fr.tangv.processmanager.sender.Sender;

public interface Command {
	
	public boolean command(Sender sender, String cmd, String arg);
	public String getUsage();
	public String getDescription();
	
}
