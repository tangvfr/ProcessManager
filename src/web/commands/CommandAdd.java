package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandAdd {

	public CommandAdd(String name, String cmd, String folder, String launch, String cmdStop) throws Exception {
		if (!name.isEmpty() && !launch.isEmpty()) {
			ProcessPlus process = new ProcessPlus(name, cmd, folder, "UTF8", Boolean.parseBoolean(launch), cmdStop);
			Main.processManagerServer.getProcessManager().addProcess(process);
		} else {
			throw new Exception("CommandAdd: name or launch is empty");
		}
	}
	
}
