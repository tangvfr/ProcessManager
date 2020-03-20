package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandCmd {

	public CommandCmd(String name, String newCmd) throws Exception {
		if (!name.isEmpty()) {
			ManagerProcess pm = ProcessManager.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				ProcessPlus process = pm.getProcess(name);
				process.setCmd(newCmd);
				pm.saveProcces(process.getName());
			} else {
				throw new Exception("CommandCmd: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandCmd: name is empty");
		}
	}
	
}
