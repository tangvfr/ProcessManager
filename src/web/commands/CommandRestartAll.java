package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandRestartAll {

	public CommandRestartAll() throws Exception {
		ProcessManager pm = Main.processManagerServer.getProcessManager();
		if (!Main.processManagerServer.isStopNoForce()) {
			for (ProcessPlus process : pm.getListProcess()) {
				process.getProcess().stop();
			}
			for (ProcessPlus process : pm.getListProcess()) {
				process.reload();			
			}
			for (ProcessPlus process : pm.getListProcess()) {
				process.getProcess().start();
			}
		} else {
			throw new Exception("CommandRestart: the server is being stopped");
		}
	}
	
}
