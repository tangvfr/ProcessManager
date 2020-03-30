package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStopCmd {
	
	public CommandStopCmd(String name, String newStopCmd) throws Exception {
		if (!name.isEmpty()) {
			ManagerProcess pm = ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager();
			if (pm.hasProcess(name)) {
				ProcessPlus process = pm.getProcess(name);
				process.setCmdStop(newStopCmd);
				pm.saveProcces(process.getName());
			} else {
				throw new Exception("CommandStopCmd: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandStopCmd: name is empty");
		}
	}
	
}
