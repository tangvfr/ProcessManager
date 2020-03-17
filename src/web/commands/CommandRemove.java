package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;

public class CommandRemove {

	public CommandRemove(String name) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				pm.removeProcess(name);
			} else {
				throw new Exception("CommandRemove: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandRemove: name is empty");
		}
	}
	
}
