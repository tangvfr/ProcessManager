package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandLaunch {

	public CommandLaunch(String name, String launch) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
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
