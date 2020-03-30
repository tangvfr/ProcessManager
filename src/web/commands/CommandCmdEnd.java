package web.commands;

import fr.tangv.processmanager.ProcessManager;

public class CommandCmdEnd {

	public CommandCmdEnd(String newCmdEnd) throws Exception {
		ProcessManager.CMD_END = newCmdEnd;
		ProcessManager.saveData();
	}
	
}
