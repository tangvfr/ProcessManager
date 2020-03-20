package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ManagerProcess;

public class CommandRename {

	public CommandRename(String name, String newname) throws Exception {
		if (!name.isEmpty() && !newname.isEmpty()) {
			ManagerProcess pm = ProcessManager.processManagerServer.getProcessManager();
			if (!pm.hasProcess(newname)) {
				pm.renameProcess(name, newname);
			} else {
				throw new Exception("CommandRename: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandRename: name or newname is empty");
		}
	}
	
}
