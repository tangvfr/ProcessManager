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

import fr.tangv.processmanager.webserver.GetExecute;
import fr.tangv.processmanager.webserver.PostExecute;
import fr.tangv.processmanager.webserver.ProcessAffiche;
import fr.tangv.processmanager.webserver.WebServer;

public class Main {

	public static final String version = "Alpha_1.4.1";
	public static volatile String cmdEnd = " ";
	public static volatile long timeStopNoForce = 0L;
	public static volatile long timeStart;
	public static volatile long timeIsStart;
	public static volatile long timeRestart;
	public static final long value24H = (24*3600000);
	
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
						return "ProcessManager n'est pas à jour, la dernière version est "+lastVersion+(web ? " ! <a href=\"https://tangv.fr/ProcessManager/\">Download</a>" : " ! Download: https://tangv.fr/ProcessManager/");
					} else {
						return "ProcessManager est à jour !";
					}
				} catch (Exception e) {
					return "Error Read Version";
				}
			} catch (IOException e) {
				return "Error Internet";
			}
		} catch (MalformedURLException e) {
			return "Error Url";
		}
	}
	
	@SuppressWarnings("deprecation")
	public static long transTime(long time) {
		Date date = new Date(time);
		return (date.getHours()*3600+date.getMinutes()*60+date.getSeconds())*1000;
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
			ProcessManagerServer pms = new ProcessManagerServer();
			if (args.length >= 2 && args[1].equalsIgnoreCase("-web")) {
				try {
					int port = args.length >= 3 ? Integer.parseInt(args[2]) : 80;
					WebServer webServer = new WebServer(port, pms);
					//----------------------------
					webServer.addGetRequetExecutes(new GetExecute());
					webServer.addPostRequetExecutes(new PostExecute());
					ProcessAffiche processAffiche = new ProcessAffiche();
					webServer.registreHandle("noProcess", processAffiche);
					webServer.registreHandle("oneProcess", processAffiche);
					//----------------------------
					ProcessManagerServer.logger.info("Open WebServer with port \""+port+"\" !");
				} catch (Exception e) {
					ProcessManagerServer.logger.warning("Error WebServer port is invalid !");
				}
			}
		}
	}
	
}
