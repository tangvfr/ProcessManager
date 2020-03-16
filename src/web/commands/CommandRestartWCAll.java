package web.commands;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class CommandRestartWCAll {

	public CommandRestartWCAll() throws Exception {
		if (!Main.processManagerServer.isStopNoForce()) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							ProcessManager pm = Main.processManagerServer.getProcessManager();
							for (ProcessPlus process : pm.getListProcess()) {
								if (!process.getCmdStop().isEmpty()) {
									process.getProcess().send(process.getCmdStop());
								} else {
									process.getProcess().stop();
								}		
							}
							boolean boucle = false;
							do {
								boucle = false;
								Thread.sleep(100);
								for (ProcessPlus process : pm.getListProcess()) {
									if (process.getProcess().isStart()) {
										boucle = true;
										break;
									}
								}
							} while(boucle);
							for (ProcessPlus process : pm.getListProcess()) {
								process.reload();			
							}
							for (ProcessPlus process : pm.getListProcess()) {
								process.getProcess().start();
							}
						} catch (Exception e) {
							ProcessManagerServer.logger.warning(e.getMessage());
						}
					}
				});
				thread.start();
		} else {
			throw new Exception("CommandRestart: the server is being stopped");
		}
	}
	
}
