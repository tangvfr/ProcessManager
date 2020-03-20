package web.commands;

import fr.tangv.processmanager.ProcessManager;

public class CommandCmdEnd {

	public CommandCmdEnd(String newCmdEnd) throws Exception {
		ProcessManager.cmdEnd = newCmdEnd;
		ProcessManager.saveData();
	}
	
}
