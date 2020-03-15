package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStop {
	
	public CommandStop(String name) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				ProcessPlus process = pm.getProcess(name);
				process.getProcess().stop();
			} else {
				throw new Exception("CommandStop: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandStop: name is empty");
		}
	}
	
}
