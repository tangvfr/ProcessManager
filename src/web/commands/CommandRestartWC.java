package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandRestartWC {

	public CommandRestartWC(String name) throws Exception {
		if (!name.isEmpty()) {
			ManagerProcess pm = ProcessManager.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				if (!ProcessManager.processManagerServer.isStopNoForce()) {
					ProcessPlus process = pm.getProcess(name);
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								if (!process.getCmdStop().isEmpty()) {
									process.getProcess().send(process.getCmdStop());
								} else {
									process.getProcess().stop();
								}
								while (process.getProcess().isStart())
									Thread.sleep(100);
								process.reload();
								process.getProcess().start();
							} catch (Exception e) {
								ProcessManagerServer.logger.warning(e.getMessage());
							}
						}
					});
					thread.start();
				} else {
					throw new Exception("CommandRestartWC: the server is being stopped");
				}
			} else {
				throw new Exception("CommandRestartWC: process "+name+" not exist");
			}
		} else {
			throw new Exception("CommandRestartWC: name is empty");
		}
	}
	
}
