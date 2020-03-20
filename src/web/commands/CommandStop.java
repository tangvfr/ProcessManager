package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStop {
	
	public CommandStop(String name) throws Exception {
		if (!name.isEmpty()) {
			ManagerProcess pm = ProcessManager.processManagerServer.getProcessManager();
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
