package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;

public class CommandListUser implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandListUser(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		String text = "List user and password: number > "+processManagerServer.getUserAndMdp().size()+"\n";
		for (String user : processManagerServer.getUserAndMdp().keySet()) {
			text += user+" > "+processManagerServer.getUserAndMdp().get(user)+"\n";
		}
		sender.send(text);
		return true;
	}

	@Override
	public String getUsage() {
		return "listuser";
	}

	@Override
	public String getDescription() {
		return "All list user and their password.";
	}

}