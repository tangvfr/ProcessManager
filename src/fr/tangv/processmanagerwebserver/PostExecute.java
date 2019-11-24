package fr.tangv.processmanagerwebserver;

import java.io.IOException;
import java.io.OutputStream;

import fr.tangv.processmanagerserver.util.ProcessManager;
import fr.tangv.processmanagerserver.util.ProcessPlus;

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
							String[] args = dataRequet.split(" ", 2);
							if (args.length == 2) {
								String[] cmd;
								switch(args[0]) {
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
										if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
											ProcessPlus process = pmg.getProcess(cmd[0]);
											process.setName(cmd[1]);
											pmg.saveProcces(process.getName());
										}
										break;
									case "remove":
										if (pmg.hasProcess(args[1])) {
											pmg.removeProcess(args[1]);
										}
										break;
									default:
										break;
								}
							} else {
								if (dataRequet.startsWith("add~")) {
									String[] data = dataRequet.split("~");
									if (data.length == 5) {
										ProcessPlus process = new ProcessPlus(data[1], data[2], data[3], "UTF8", Boolean.parseBoolean(data[4]));
										pmg.addProcess(process);
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					webServer.sendPageName(out, repRequet);
					return;
				}
			}
			 webServer.sendPageName(out, "/invalide.html");
		} else {
			webServer.sendPageName(out, repRequet);
		}
		webServer.sendPageName(out, repRequet);
	}

}
