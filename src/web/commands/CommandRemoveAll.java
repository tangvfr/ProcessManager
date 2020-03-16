package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandRemoveAll {

	public CommandRemoveAll() throws Exception {
		ProcessManager pm = Main.processManagerServer.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			pm.removeProcess(process.getName());
		}
	}
	
}
