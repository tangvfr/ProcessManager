package fr.tangv.processmanager.commands;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;

public class CommandListUsers implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandListUsers(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		String text = "List users and password: number > "+processManagerServer.getUserAndMdp().size()+"\n";
		for (String user : processManagerServer.getUserAndMdp().keySet()) {
			text += user+" > "+processManagerServer.getUserAndMdp().get(user)+"\n";
		}
		sender.send(text);
		return true;
	}

	@Override
	public String getUsage() {
		return "listusers";
	}

	@Override
	public String getDescription() {
		return "List all users and their password.";
	}

}
