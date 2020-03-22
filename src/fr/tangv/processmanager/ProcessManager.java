package fr.tangv.processmanager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import fr.tangv.web.main.Web;

public class ProcessManager {

	public static final String version = "1.6.3";
	public static volatile String cmdEnd = "";
	public static volatile long timeStopNoForce = 0L;
	public static volatile long timeStart;
	public static volatile long timeIsStart;
	public static volatile long timeRestart;
	public static final long valueh24H = (24*3600000);
	public static volatile long dateRestart;
	public static ProcessManagerServer processManagerServer;
	
	public static String getUpdate(boolean web) {
		try {
			URL url = new URL("https://tangv.fr/ProcessManager/version");
			try {
				InputStream in = url.openStream();
				byte[] buf = new byte[in.available()];
				in.read(buf);
				in.close();
				try {
					String lastVersion = new String(buf, "UTF8");
					if (!version.equals(lastVersion)) {
						return "Process Manager is not update, the last version is "+lastVersion+(web ? " ! <a href=\"https://github.com/tangvfr/ProcessManager/releases\">Download</a>" : " ! Download: https://github.com/tangvfr/ProcessManager/releases");
					} else {
						return "ProcessManager is update !";
					}
				} catch (Exception e) {
					return "Error read version";
				}
			} catch (IOException e) {
				return "Error internet";
			}
		} catch (MalformedURLException e) {
			return "Error url";
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Date dateRestart(long time) {
		time /= 1000;
		int heures = (int) (time/3600);
		int minutesI = (int) (time%3600);
		int minutes = minutesI/60;
		int secondes = minutesI%60;
		long cTime = System.currentTimeMillis();
		Date dateAc = new Date(cTime);
		Date date = new Date(cTime);
		date.setHours(heures);
		date.setMinutes(minutes);
		date.setSeconds(secondes);
		if (!date.after(dateAc))
			date.setDate(date.getDate()+1);
		return date;
	}
	
	public static String formatTime(long time) {
		int days = (int) (time/valueh24H);
		int dayI = (int) ((time%valueh24H)/1000);
		int heures = dayI/3600;
		int minutesI = dayI%3600;
		int minutes = minutesI/60;
		int secondes = minutesI%60;
		return days+"day "+heures+"H "+minutes+"min "+secondes+"sec";
	}
	
	public static synchronized void saveData() throws IOException {
		File file = new File("./data");
		if (!file.exists())
			file.createNewFile();
		DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
		out.writeUTF(cmdEnd);
		out.writeLong(timeStopNoForce);
		out.close();
	}
	
	public static synchronized void loadData() throws IOException {
		File file = new File("./data");
		if (!file.exists())
			saveData();
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		cmdEnd = in.readUTF();
		timeStopNoForce = in.readLong();
		in.close();
	}
	
	public static void main(String[] args) {
		if (args.length >= 1 && args[0].equalsIgnoreCase("-server")) {
			processManagerServer = new ProcessManagerServer();
			if (args.length >= 2 && args[1].equalsIgnoreCase("-web")) {
				try {
					int port = args.length >= 3 ? Integer.parseInt(args[2]) : 80;
					new Web(port, "web", ProcessManagerServer.logger);
					ProcessManagerServer.logger.info("Open WebServer with port \""+port+"\" !");
				} catch (Exception e) {
					ProcessManagerServer.logger.warning("Error WebServer port is invalid !");
				}
			}
		}
	}
	
}