package fr.tangv.processmanagerserver.commands;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Client;
import fr.tangv.processmanagerserver.sender.Sender;

public class CommandKickAll implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandKickAll(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		while (processManagerServer.getServer().getClients().size() > 0) {
			Client client = processManagerServer.getServer().getClients().firstElement();
			try {
				client.send("[KickAll] You were kicked by \""+sender.getName()+"\" !");
				client.getSocket().close();
				processManagerServer.getServer().getClients().remove(client);
			} catch (IOException e) {
				sender.send("Kick has fail !");
			}
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "kickall";
	}

	@Override
	public String getDescription() {
		return "Make kick one client connect.";
	}

}
