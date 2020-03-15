package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandFolder {

	public CommandFolder(String name, String newFolder) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
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
