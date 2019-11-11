package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Client;
import fr.tangv.processmanagerserver.sender.Sender;

public class CommandList implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandList(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		String text = "List user connected:\n";
		for (Client client : processManagerServer.getServer().getClients())
			text += '\"'+client.getName()+"\" | \""+client.getIp()+'\n';
		sender.send(text);
		return true;
	}

	@Override
	public String getUsage() {
		return "list";
	}

	@Override
	public String getDescription() {
		return "List all clients connect.";
	}

}
