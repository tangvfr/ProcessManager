package fr.tangv.processmanager.commands;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.sender.Sender;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandListProcess implements Command {

	private ProcessManagerServer processManagerServer;
	
	public CommandListProcess(ProcessManagerServer processManagerServer) {
		this.processManagerServer = processManagerServer;
	}
	
	@Override
	public boolean command(Sender sender, String cmd, String arg) {
		String text = "List process: > "+processManagerServer.getProcessManager().getListProcess().size()+"\n"
				+ "| \"Name\" | \"Cmd\" | \"Rep\" | ActiveOnStart | isStart |\n";
		for (ProcessPlus process : processManagerServer.getProcessManager().getListProcess())
			text += "| \""+process.getName()+"\" | \""+process.getCmd()+"\" | \""+process.getRep()+"\" | "+process.isActiveOnStart()+" | "+process.getProcess().isStart()+" |\n";
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
