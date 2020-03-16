package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandLaunchAll {

	public CommandLaunchAll(String launch) throws Exception {
		ProcessManager pm = Main.processManagerServer.getProcessManager();
		for (ProcessPlus process : pm.getListProcess()) {
			process.setActiveOnStart(Boolean.parseBoolean(launch));
			pm.saveProcces(process.getName());
		}
	}
	
}
