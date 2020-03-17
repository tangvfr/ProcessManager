package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;

public class CommandSendConsole {

	public CommandSendConsole(String name, String command) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				pm.getProcess(name).send(command);
			} else {
				throw new Exception("CommandRemove: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandRemove: name is empty");
		}
	}
	
}
