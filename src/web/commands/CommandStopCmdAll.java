package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStopCmdAll {
	
	public CommandStopCmdAll(String newStopCmd) {
		ManagerProcess pm = ProcessManager.processManagerServer.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			try {
				process.setCmdStop(newStopCmd);
				pm.saveProcces(process.getName());
			} catch (Exception e) {
				ProcessManagerServer.logger.warning(e.getMessage());
			}
		}
	}
	
}
