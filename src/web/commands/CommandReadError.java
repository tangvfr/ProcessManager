package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;

public class CommandReadError {
	
	public String error;
	
	public CommandReadError(String name, int lines) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				this.error = pm.getProcess(name).readError(lines);
			} else {
				throw new Exception("CommandRemove: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandRemove: name is empty");
		}
	}

}
