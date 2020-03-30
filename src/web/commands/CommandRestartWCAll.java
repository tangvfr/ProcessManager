package web.commands;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ManagerProcess;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandRestartWCAll {

	public CommandRestartWCAll() throws Exception {
		if (!ProcessManager.PROCESS_MANAGER_SERVER.isStopNoForce()) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
							ManagerProcess pm = ProcessManager.PROCESS_MANAGER_SERVER.getProcessManager();
							for (ProcessPlus process : pm.getListProcess()) {
								if (!process.getCmdStop().isEmpty()) {
									try {
										process.getProcess().send(process.getCmdStop());
									} catch (Exception e) {
										ProcessManagerServer.LOGGER.warning(e.getMessage());
									}
								} else {
									process.getProcess().stop();
								}		
							}
							boolean boucle = false;
							do {
								boucle = false;
								try {
									Thread.sleep(100);
								} catch (Exception e) {
									ProcessManagerServer.LOGGER.warning(e.getMessage());
								}
								for (ProcessPlus process : pm.getListProcess()) {
									if (process.getProcess().isStart()) {
										boucle = true;
										break;
									}
								}
							} while(boucle);
							for (ProcessPlus process : pm.getListProcess()) {
								try {
									process.reload();	
								} catch (Exception e) {
									ProcessManagerServer.LOGGER.warning(e.getMessage());
								}
							}
							for (ProcessPlus process : pm.getListProcess()) {
								try {
									process.getProcess().start();
								} catch (Exception e) {
									ProcessManagerServer.LOGGER.warning(e.getMessage());
								}
							}
					}
				});
				thread.start();
		} else {
			throw new Exception("CommandRestart: the server is being stopped");
		}
	}
	
}
