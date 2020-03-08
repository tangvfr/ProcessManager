package web;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;

public class CommandRename {

	public CommandRename(String name, String newname) throws Exception {
		if (!name.isEmpty() && !newname.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
			if (!pm.hasProcess(newname)) {
				pm.renameProcess(name, newname);
			}
		} else {
			throw new Exception("CommandRename: name or newname is empty");
		}
	}
	
}
