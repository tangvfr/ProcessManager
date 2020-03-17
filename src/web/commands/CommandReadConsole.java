package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;

public class CommandReadConsole {

	public String console;
	
	public CommandReadConsole(String name, int lines) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				console = pm.getProcess(name).readInput(lines);
			} else {
				throw new Exception("CommandRemove: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandRemove: name is empty");
		}
	}
	
}
