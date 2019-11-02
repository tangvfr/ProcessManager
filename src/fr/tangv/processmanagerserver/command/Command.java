package fr.tangv.processmanagerserver.command;

import fr.tangv.processmanagerserver.Sender.Sender;

public interface Command {
	
	public boolean command(Sender sender, String cmd, String arg);
	public String getUsage();
	public String getDescription();
	
}
