package fr.tangv.processmanager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import fr.tangv.web.main.Web;

public class ProcessManager {

	public static final String VERSION = "1.6.4";
	public static volatile String CMD_END = "";
	public static volatile long TIME_STOP_NO_FORCE = 0L;
	public static volatile long TIME_START;
	public static volatile long TIME_IS_START;
	public static volatile long TIME_RESTART;
	public static final long VALUE_24H = (24*3600000);
	public static volatile long DATE_RESTART;
	public static ProcessManagerServer PROCESS_MANAGER_SERVER;
	public static String UPDATE;
	
	private static String getUpdate() {
		try {
			URLConnection urlCo = new URL("https://api.github.com/repos/tangvfr/ProcessManager/tags").openConnection();
			InputStream in = urlCo.getInputStream();
			byte[] buf = new byte[urlCo.getContentLength()];
			in.read(buf);
			in.close();
			try {
				String pageJson = new String(buf, "UTF8");
				int debutVersion = pageJson.indexOf("\"name\": \"")+11;
				String lastVersion = pageJson.substring(debutVersion, pageJson.indexOf("\","));
				int resultComp = VERSION.compareToIgnoreCase(lastVersion);
				if (resultComp == -1) {
					return "ProcessManager is not update, the last version is "+lastVersion+" ! <a href=\"https://github.com/tangvfr/ProcessManager/releases\">Download</a>";
				} else if (resultComp == 1) {
					return "ProcessManager is in improve !";
				} else {
					return "ProcessManager is update !";
				}
			} catch (Exception e) {
				return "Error read version";
			}
		} catch (IOException e) {
			return "Error internet or with the link";
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
		int days = (int) (time/VALUE_24H);
		int dayI = (int) ((time%VALUE_24H)/1000);
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
		out.writeUTF(CMD_END);
		out.writeLong(TIME_STOP_NO_FORCE);
		out.close();
	}
	
	public static synchronized void loadData() throws IOException {
		File file = new File("./data");
		if (!file.exists())
			saveData();
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		CMD_END = in.readUTF();
		TIME_STOP_NO_FORCE = in.readLong();
		in.close();
	}
	
	public static void main(String[] args) {
		if (args.length >= 1 && args[0].equalsIgnoreCase("-server")) {
			UPDATE = getUpdate();
			PROCESS_MANAGER_SERVER = new ProcessManagerServer();
			if (args.length >= 2 && args[1].equalsIgnoreCase("-web")) {
				try {
					int port = args.length >= 3 ? Integer.parseInt(args[2]) : 80;
					int backlog = args.length >= 4 ? Integer.parseInt(args[3]) : 10;
					new Web(port, "web", ProcessManagerServer.LOGGER, backlog);
					ProcessManagerServer.LOGGER.info("Open WebServer with port \""+port+"\" with "+backlog+" backlog!");
				} catch (Exception e) {
					ProcessManagerServer.LOGGER.warning("Error WebServer port is invalid !");
				}
			}
		}
	}
	
}
