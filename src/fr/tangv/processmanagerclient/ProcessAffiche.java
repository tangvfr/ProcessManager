package fr.tangv.processmanagerclient;

import java.io.IOException;

import fr.tangv.processmanagerserver.util.ProcessPlus;

public class ProcessAffiche implements HandleBaliseExport {

	private String noProcess;
	
	public ProcessAffiche() {
		noProcess = "";
	}
	
	@Override
	public String handle(WebServer webServer, String name, String cont) throws IOException {
		if (name.equals("oneProcess")) {
			String codeAdd = "";
			for (ProcessPlus process : webServer.getProcessManagerServer().getProcessManager().getListProcess()) {
				codeAdd += cont
				.replace("<import=etatProcess>", process.getProcess().isStart() ? "on" : "off")
				.replace("<import=onStartProcess>", process.isActiveOnStart() ? "on" : "off")
				.replace("<import=nameProcess>", process.getName())
				.replace("<import=cmdProcess>",	process.getCmd())
				.replace("<import=repProcess>",	process.getRep());
			}
			if (codeAdd.isEmpty())
				return noProcess;
			else
				return codeAdd;
		} else if (name.equals("noProcess")) {
			noProcess = cont;
			return "";
		}
		return "";
	}

}
