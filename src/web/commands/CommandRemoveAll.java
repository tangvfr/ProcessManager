package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandRemoveAll {

	public CommandRemoveAll() {
		ManagerProcess pm = ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			try {
				pm.removeProcess(process.getName());
			} catch (Exception e) {
				ProcessManagerServer.LOGGER.warning(e.getMessage());
			}
		}
	}
	
}
