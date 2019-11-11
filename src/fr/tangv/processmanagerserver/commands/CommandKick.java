package fr.tangv.processmanagerserver.commands;

import java.io.IOException;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Client;
import fr.tangv.processmanagerserver.sender.Sender;

public class CommandKick implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandKick(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		if (arg.isEmpty()) return false;
		for (Client client : processManagerServer.getServer().getClients()) {
			if (client.getName().equals(arg)) {
				try {
					client.send("[Kick] You were kicked by \""+sender.getName()+"\" !");
					client.getSocket().close();
					processManagerServer.getServer().getClients().remove(client);
					sender.send("User \""+client.getName()+"\" \""+client.getIp()+"\" is kicked !");
				} catch (IOException e) {
					sender.send("Kick has fail !");
				}
				return true;
			}
		}
		sender.send("User no found.");
		return true;
	}

	@Override
	public String getUsage() {
		return "kick <user_name>";
	}

	@Override
	public String getDescription() {
		return "Make kick one client connect.";
	}

}
