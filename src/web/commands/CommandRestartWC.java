package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandRestartWC {

	public CommandRestartWC(String name) throws Exception {
		if (!name.isEmpty()) {
			ProcessManager pm = Main.processManagerServer.getProcessManager();
			if (pm.hasProcess(name)) {
				if (!Main.processManagerServer.isStopNoForce()) {
					ProcessPlus process = pm.getProcess(name);
					if (!process.getCmdStop().isEmpty()) {
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									process.getProcess().send(process.getCmdStop());
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
					}
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
