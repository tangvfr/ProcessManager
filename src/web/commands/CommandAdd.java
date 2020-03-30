package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandAdd {

	public CommandAdd(String name, String cmd, String folder, String launch, String cmdStop) throws Exception {
		if (!name.isEmpty()) {
			ProcessPlus process = new ProcessPlus(name, cmd, folder, "UTF8", Boolean.parseBoolean(launch), cmdStop);
			ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager().addProcess(process);
		} else {
			throw new Exception("CommandAdd: name is empty");
		}
	}
	
}
