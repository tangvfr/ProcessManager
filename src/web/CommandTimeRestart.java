package web;

import fr.tangv.processmanager.Main;

public class CommandTimeRestart {

	public CommandTimeRestart(String time, String horaire) throws Exception {
		int hor = Boolean.parseBoolean(horaire) ? -1 : 1;
		String[] tim = time.split(":");
		int hour = Integer.parseInt(tim[0]);
		int minute = Integer.parseInt(tim[1]);
		Main.timeStopNoForce = ((hour*3_600_000)+(minute*60_000))*hor;
		Main.dateRestart = Main.dateRestart(-Main.timeStopNoForce).getTime();
		Main.saveData();
	}
	
}
