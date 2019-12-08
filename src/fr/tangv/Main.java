package fr.tangv;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.tangv.processmanager.ProcessManagerServer;
import fr.tangv.processmanager.webserver.GetExecute;
import fr.tangv.processmanager.webserver.PostExecute;
import fr.tangv.processmanager.webserver.ProcessAffiche;
import fr.tangv.processmanager.webserver.WebServer;

public class Main {

	public static final String version = "Alpha_1.4";
	public static volatile String cmdEnd = "";
	public static volatile long timeStopNoForce = 0L;
	
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
