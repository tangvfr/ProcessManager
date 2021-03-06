package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStopWC {

	public CommandStopWC(String name) throws Exception {
		if (!name.isEmpty()) {
			ManagerProcess pm = ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager();
			if (pm.hasProcess(name)) {
				ProcessPlus process = pm.getProcess(name);
				if (!process.getCmdStop().isEmpty()) {
					process.getProcess().send(process.getCmdStop());
				} else {
					process.getProcess().stop();
				}
			} else {
				throw new Exception("CommandStopWC: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandStopWC: name is empty");
		}
	}
	
}
