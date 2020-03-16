package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStopAll {
	
	public CommandStopAll() throws Exception {
		ProcessManager pm = Main.processManagerServer.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			try {
				process.getProcess().stop();
			} catch (Exception e) {
				ProcessManagerServer.logger.warning(e.getMessage());
			}
		}
	}
	
}
