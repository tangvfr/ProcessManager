package web.commands;

import fr.tangv.processmanager.ProcessManager;

public class CommandTimeRestart {

	public CommandTimeRestart(String time, String horaire) throws Exception {
		int hor = Boolean.parseBoolean(horaire) ? -1 : 1;
		String[] tim = time.split(":");
		int hour = Integer.parseInt(tim[0]);
		int minute = Integer.parseInt(tim[1]);
		ProcessManager.TIME_STOP_NO_FORCE = ((hour*3_600_000)+(minute*60_000))*hor;
		ProcessManager.DATE_RESTART = ProcessManager.dateRestart(-ProcessManager.TIME_STOP_NO_FORCE).getTime();
		ProcessManager.saveData();
	}
	
}
