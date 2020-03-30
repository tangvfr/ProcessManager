package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandFolder {

	public CommandFolder(String name, String newFolder) throws Exception {
		if (!name.isEmpty()) {
			ManagerProcess pm = ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager();
			if (pm.hasProcess(name)) {
				ProcessPlus process = pm.getProcess(name);
				process.setFolder(newFolder);
				pm.saveProcces(process.getName());
			} else {
				throw new Exception("CommandFolder: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandFolder: name is empty");
		}
	}
	
}
