package web.commands;

import fr.tangv.processmanager.Main;

public class CommandCmdEnd {

	public CommandCmdEnd(String newCmdEnd) throws Exception {
		Main.cmdEnd = newCmdEnd;
		Main.saveData();
	}
	
}
