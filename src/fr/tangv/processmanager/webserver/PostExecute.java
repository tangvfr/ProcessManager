package fr.tangv.processmanager.webserver;

import java.io.IOException;
import java.io.OutputStream;

import fr.tangv.Main;
import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

public class PostExecute implements RequetExecute {
	
	@Override
	public void execute(WebServer webServer, OutputStream out, String typeRequet, String repRequet, String hostRequet,
			String contTypeRequet, String dataRequet, String ipRequet) throws IOException {
		if (repRequet.equals("/info.tangweb")) {
			if (contTypeRequet.contains(" ")) {
				String name = contTypeRequet.substring(0, contTypeRequet.indexOf(" "));
				String pwd = contTypeRequet.substring(name.length()+1);
				if (webServer.getProcessManagerServer().getUserAndMdp().containsKey(name))
					if (webServer.getProcessManagerServer().getUserAndMdp().get(name).equals(pwd)) {
						try {
							ProcessManager pmg = webServer.getProcessManagerServer().getProcessManager();
							dataRequet = dataRequet.replace("<", "").replace(">", "");
							String[] args = dataRequet.split(" ", 2);
							if (args.length == 2) {
								String[] cmd;
								switch(args[0]) {
									case "timestopnoforce":
										
										break;
									case "stopnoforce":
										
										break;
									case "cmdend":
										Main.cmdEnd = args[1];
										break;
									case "onStart":
										if (pmg.hasProcess(args[1])) {
											ProcessPlus process = pmg.getProcess(args[1]);
											process.setActiveOnStart(!process.isActiveOnStart());
											pmg.saveProcces(process.getName());
										}
										break;
									case "start":
										if (pmg.hasProcess(args[1])) {
											ProcessPlus process = pmg.getProcess(args[1]);
											process.reload();
											process.getProcess().start();
										}
										break;
									case "restart":
										if (pmg.hasProcess(args[1])) {
											ProcessPlus process = pmg.getProcess(args[1]);
											process.getProcess().stop();
											process.reload();
											process.getProcess().start();
										}
										break;
									case "stop":
										if (pmg.hasProcess(args[1])) {
											ProcessPlus process = pmg.getProcess(args[1]);
											process.getProcess().stop();
										}
										break;
									case "cmd":
										cmd = args[1].split(" ", 2);
										if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
											ProcessPlus process = pmg.getProcess(cmd[0]);
											process.setCmd(cmd[1]);
											pmg.saveProcces(process.getName());
										}
										break;
									case "cmdstop":
										cmd = args[1].split(" ", 2);
										if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
											ProcessPlus process = pmg.getProcess(cmd[0]);
											process.setCmdStop(cmd[1]);
											pmg.saveProcces(process.getName());
										}
										break;
									case "rep":
										cmd = args[1].split(" ", 2);
										if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
											ProcessPlus process = pmg.getProcess(cmd[0]);
											process.setRep(cmd[1]);
											pmg.saveProcces(process.getName());
										}
										break;
									case "rename":
										cmd = args[1].split(" ", 2);
										if (cmd.length == 2 && pmg.hasProcess(cmd[0]) && !pmg.hasProcess(cmd[1])) {
											pmg.renameProcess(cmd[0], cmd[1]);
										}
										break;
									case "remove":
										if (pmg.hasProcess(args[1])) {
											pmg.removeProcess(args[1]);
										}
										break;
									case "send":
										cmd = args[1].split(" ", 2);
										if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
											ProcessPlus process = pmg.getProcess(cmd[0]);
											process.send(cmd[1]);
											return;
										} else {
											return;
										}
									case "read":
										if (pmg.hasProcess(args[1])) {
											ProcessPlus process = pmg.getProcess(args[1]);
											String console = '@'+process.read(100);
											webServer.sendRequet(out, console.getBytes("UTF8"), "console");
											return;
										} else {
											return;
										}
									case "add":
										String[] data = args[1].split("~");
										if (data.length == 5) {
											ProcessPlus process = new ProcessPlus(data[0], data[1], data[2], "UTF8", Boolean.parseBoolean(data[3]), data[4]);
											pmg.addProcess(process);
										}
										break;
									default:
										break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					if (!dataRequet.isEmpty())
						webServer.sendPageName(out, repRequet);
					return;
				}
			}
			webServer.getListAntiBrutus().put(ipRequet, System.currentTimeMillis());
			ProcessManagerServer.logger.info(ipRequet+" >: "+contTypeRequet+" >: add_blocked");
			webServer.sendPageName(out, "/invalide.html");
		} else {
			webServer.sendPageName(out, repRequet);
		}
		webServer.sendPageName(out, repRequet);
	}

}
