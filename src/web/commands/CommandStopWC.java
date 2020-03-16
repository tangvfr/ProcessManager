package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStopWC {

	public CommandStopWC(String name) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
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
