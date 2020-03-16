package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStopWCAll {

	public CommandStopWCAll() throws Exception {
		ProcessManager pm = Main.processManagerServer.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			if (!process.getCmdStop().isEmpty()) {
				process.getProcess().send(process.getCmdStop());
			} else {
				process.getProcess().stop();
			}
		}
	}
	
}
