package fr.tangv.processmanagerserver.commands;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Sender;
import fr.tangv.processmanagerserver.util.ProcessPlus;

public class CommandListProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandListProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		String text = "List process:\n"
				+ "~ \"Name\" ~ \"Cmd\" ~ \"Rep\" ~ ActiveOnStart ~ isStart ~\n";
		for (ProcessPlus process : processManagerServer.getProcessManager().getListProcess())
			text += "~ \""+process.getName()+"\" ~ \""+process.getCmd()+"\" ~ \""+process.getRep()+"\" ~ "+process.isActiveOnStart()+" ~ "+process.getProcess().isStart()+" ~\n";
		sender.send(text);
		return true;
	}

	@Override
	public String getUsage() {
		return "listprocess";
	}

	@Override
	public String getDescription() {
		return "List all process.";
	}
	
}
