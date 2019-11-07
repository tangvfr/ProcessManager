package fr.tangv.processmanagerserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import fr.tangv.processmanagerserver.commands.CommandHelp;
import fr.tangv.processmanagerserver.commands.CommandListUser;
import fr.tangv.processmanagerserver.commands.CommandManager;
import fr.tangv.processmanagerserver.commands.CommandStop;

public class ProcessManagerServer {
	
	public static Logger logger;
	
	public static String getStringTime() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH-mm-ss");
		return '['+format.format(new Date())+"] ";
	}
	
	public static void main(String[] args) {
		new ProcessManagerServer();
	}
	
	private ServerSocket server;
	private File fileParameter;
	private int port;
	private Map<String, String> userAndMdp;
	private CommandManager cmdManager;
	
	public ServerSocket getServer() {
		return server;
	}
	
	public File getFileParameter() {
		return fileParameter;
	}
	
	public int getPort() {
		return port;
	}
	
	public Map<String, String> getUserAndMdp() {
		return userAndMdp;
	}
	
	public CommandManager getCmdManager() {
		return cmdManager;
	}
	
	private void saveParameter() throws IOException {
		if (!fileParameter.exists())
			fileParameter.createNewFile();
		FileOutputStream out = new FileOutputStream(fileParameter);
		String text = ""+port;
		for (String user : userAndMdp.keySet()) {
			text += "\n";
			text += "\n"+user;
			text += "\n"+userAndMdp.get(user);
		}
		out.write(text.getBytes("UTF8"));
		out.close();
	}
	
	private void loadParameter() throws IOException {
		if (!fileParameter.exists())
			saveParameter();
		Map<String, String> userAndMdp = new HashMap<String, String>();
		int port = 0;
		Scanner sc = new Scanner(fileParameter,"UTF8");
		port = Integer.parseInt(sc.nextLine());
		while (sc.hasNextLine()) {
			sc.nextLine();
			if (sc.hasNextLine()) {
				String user = sc.nextLine();
				if (sc.hasNextLine()) {
					String mdp = sc.nextLine();
					if (!userAndMdp.containsKey(user))
						userAndMdp.put(user, mdp);
					else
						ProcessManagerServer.logger.warning("Error the user \""+user+"\" already exist !");
				} else {
					break;
				}
			}
		}
		sc.close();
		this.port = port;
		this.userAndMdp = userAndMdp;
	}
	
	public ProcessManagerServer() {
		try {
			System.setProperty("java.util.logging.ConsoleHandler.formatter", "java.util.logging.SimpleFormatter");
			System.setProperty("java.util.logging.SimpleFormatter.format",  "[%1$tF %1$tT] [%4$-7s] %5$s %n");
			logger = Logger.getGlobal();
			FileHandler fileHandler = new FileHandler("./logstest.log", true);
			logger.addHandler(fileHandler);
		} catch (SecurityException | IOException e1) {
			ProcessManagerServer.logger.warning(e1.getMessage());
		}
		//---------------------------------------
		ProcessManagerServer.logger.info("\r\n" + 
				"    ____                                 __  ___                                 \r\n" + 
				"   / __ \\_________  ________  __________/  |/  /___ _____  ____ _____ ____  _____\r\n" + 
				"  / /_/ / ___/ __ \\/ ___/ _ \\/ ___/ ___/ /|_/ / __ `/ __ \\/ __ `/ __ `/ _ \\/ ___/\r\n" + 
				" / ____/ /  / /_/ / /__/  __(__  |__  ) /  / / /_/ / / / / /_/ / /_/ /  __/ /    \r\n" + 
				"/_/   /_/   \\____/\\___/\\___/____/____/_/  /_/\\__,_/_/ /_/\\__,_/\\__, /\\___/_/     \r\n" + 
				"                                                              /____/             \r\n" + 
				"");
		//----------------------------------------
		this.port = 206;
		this.userAndMdp = new HashMap<String, String>();
		this.userAndMdp.put("admin", "password");
		fileParameter = new File("./parameter");
		try {
			loadParameter();
			try {
				this.server = new ServerSocket(port);
			} catch (IOException e) {
				ProcessManagerServer.logger.warning(e.getMessage()+"\nCan be port \""+port+"\" already use !");
				return;
			}
			ProcessManagerServer.logger.info("#--------------------------#");
			ProcessManagerServer.logger.info("   port > "+port);
			ProcessManagerServer.logger.info("   number user > "+userAndMdp.size());
			ProcessManagerServer.logger.info("#--------------------------#");
			//------------------------------
			this.cmdManager = new CommandManager(System.in);
			cmdManager.registreCommand("help", new CommandHelp(this));
			cmdManager.registreCommand("listuser", new CommandListUser(this));
			cmdManager.registreCommand("stop", new CommandStop(this));
			cmdManager.start();
		} catch (IOException e) {
			ProcessManagerServer.logger.warning(e.getMessage());
		}
	}
	
	public void stop() throws IOException {
		server.close();
		System.exit(0);
	}
	
}
