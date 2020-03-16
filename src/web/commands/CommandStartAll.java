package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStartAll {

	public CommandStartAll() throws Exception {
		ProcessManager pm = Main.processManagerServer.getProcessManager();
		if (!Main.processManagerServer.isStopNoForce()) {
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
