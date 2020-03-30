package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ManagerProcess;

public class CommandReadError {
	
	public String error;
	
	public CommandReadError(String name, int lines) throws Exception {
		if (!name.isEmpty()) {
			ManagerProcess pm = ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager();
			if (pm.hasProcess(name)) {
				this.error = pm.getProcess(name).getError();
			} else {
				throw new Exception("CommandRemove: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandRemove: name is empty");
		}
	}

}
