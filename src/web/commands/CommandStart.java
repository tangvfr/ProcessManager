package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStart {

	public CommandStart(String name) throws Exception {
		if (!name.isEmpty()) {
			ManagerProcess pm = ProcessManager.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				if (!ProcessManager.processManagerServer.isStopNoForce()) {
					ProcessPlus process = pm.getProcess(name);
					process.reload();
					process.getProcess().start();
				} else {
					throw new Exception("CommandStart: the server is being stopped");
				}
			} else {
				throw new Exception("CommandStart: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandStart: name is empty");
		}
	}
	
}
