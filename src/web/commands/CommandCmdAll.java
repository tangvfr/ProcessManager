package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandCmdAll {

	public CommandCmdAll(String newCmd) throws Exception {
		ManagerProcess pm = ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			try {
				process.setCmd(newCmd);
				pm.saveProcces(process.getName());
			} catch (Exception e) {
				ProcessManagerServer.LOGGER.warning(e.getMessage());
			}
		}
	}
	
}
