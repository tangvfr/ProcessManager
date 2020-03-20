package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandRestartAll {

	public CommandRestartAll() throws Exception {
		ManagerProcess pm = ProcessManager.processManagerServer.getProcessManager();
		if (!ProcessManager.processManagerServer.isStopNoForce()) {
			for (ProcessPlus process : pm.getListProcess()) {
				try {
					process.getProcess().stop();
				} catch (Exception e) {
					ProcessManagerServer.logger.warning(e.getMessage());
				}
			}
			for (ProcessPlus process : pm.getListProcess()) {
				try {
					process.reload();	
				} catch (Exception e) {
					ProcessManagerServer.logger.warning(e.getMessage());
				}		
			}
			for (ProcessPlus process : pm.getListProcess()) {
				try {
					process.getProcess().start();
				} catch (Exception e) {
					ProcessManagerServer.logger.warning(e.getMessage());
				}
			}
		} else {
			throw new Exception("CommandRestart: the server is being stopped");
		}
	}
	
}
