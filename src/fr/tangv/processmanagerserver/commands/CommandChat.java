package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Client;
import fr.tangv.processmanagerserver.sender.Sender;

public class CommandChat  implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandChat(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		if (arg.isEmpty()) return false;
		for (Client client : processManagerServer.getServer().getClients()) {
			client.send(sender.getName()+" > "+arg);
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "chat <message>";
	}

	@Override
	public String getDescription() {
		return "Send message all user connect.";
	}
	
}
