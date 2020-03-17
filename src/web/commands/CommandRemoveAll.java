package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandRemoveAll {

	public CommandRemoveAll() {
		ProcessManager pm = Main.processManagerServer.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			try {
				pm.removeProcess(process.getName());
			} catch (Exception e) {
				ProcessManagerServer.logger.warning(e.getMessage());
			}
		}
	}
	
}
