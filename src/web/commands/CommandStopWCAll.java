package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStopWCAll {

	public CommandStopWCAll() {
		ManagerProcess pm = ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			if (!process.getCmdStop().isEmpty()) {
				try {
					process.getProcess().send(process.getCmdStop());
				} catch (Exception e) {
					ProcessManagerServer.LOGGER.warning(e.getMessage());
				}
			} else {
				process.getProcess().stop();
			}
		}
	}
	
}
