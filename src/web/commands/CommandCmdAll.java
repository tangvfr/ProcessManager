package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandCmdAll {

	public CommandCmdAll(String newCmd) throws Exception {
		ProcessManager pm = Main.processManagerServer.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			process.setCmd(newCmd);
			pm.saveProcces(process.getName());
		}
	}
	
}
