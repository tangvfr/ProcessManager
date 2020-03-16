package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandStopCmdAll {
	
	public CommandStopCmdAll(String newStopCmd) throws Exception {
		ProcessManager pm = Main.processManagerServer.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			process.setCmdStop(newStopCmd);
			pm.saveProcces(process.getName());
		}
	}
	
}
