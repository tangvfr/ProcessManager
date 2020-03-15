package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStopCmd {
	
	public CommandStopCmd(String name, String newStopCmd) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
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
