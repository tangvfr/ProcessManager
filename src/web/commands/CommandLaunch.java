package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandLaunch {

	public CommandLaunch(String name, String launch) throws Exception {
		if (!name.isEmpty()) {
			ManagerProcess pm = ProcessManager.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				ProcessPlus process = pm.getProcess(name);
				process.setActiveOnStart(Boolean.parseBoolean(launch));
				pm.saveProcces(process.getName());
			} else {
				throw new Exception("CommandLaunch: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandLaunch: name is empty");
		}
	}
	
}
