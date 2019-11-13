package fr.tangv.processmanagerserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import fr.tangv.processmanagerserver.commands.CommandAddProcess;
import fr.tangv.processmanagerserver.commands.CommandChat;
import fr.tangv.processmanagerserver.commands.CommandEditProcess;
import fr.tangv.processmanagerserver.commands.CommandHelp;
import fr.tangv.processmanagerserver.commands.CommandKick;
import fr.tangv.processmanagerserver.commands.CommandKickAll;
import fr.tangv.processmanagerserver.commands.CommandList;
import fr.tangv.processmanagerserver.commands.CommandListProcess;
import fr.tangv.processmanagerserver.commands.CommandListUser;
import fr.tangv.processmanagerserver.commands.CommandLoadPara;
import fr.tangv.processmanagerserver.commands.CommandManager;
import fr.tangv.processmanagerserver.commands.CommandRemoveProcess;
import fr.tangv.processmanagerserver.commands.CommandRenameProcess;
import fr.tangv.processmanagerserver.commands.CommandSavePara;
import fr.tangv.processmanagerserver.commands.CommandStartProcess;
import fr.tangv.processmanagerserver.commands.CommandStop;
import fr.tangv.processmanagerserver.commands.CommandStopProcess;
import fr.tangv.processmanagerserver.util.ProcessManager;
import fr.tangv.processmanagerserver.util.Server;

public class ProcessManagerServer {
	
	public static Logger logger;
	
	private static String getLogTime() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		return format.format(new Date());
	}
	
	public static void main(String[] args) {
		new ProcessManagerServer();
	}
	
	private Server server;
	private File fileParameter;
	private int port;
	private Map<String, String> userAndMdp;
	private CommandManager cmdManager;
	private ProcessManager processManager;
	
	public ProcessManager getProcessManager() {
		return processManager;
	}
	
	public Server getServer() {
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
	
	public void printInfo() {
		ProcessManagerServer.logger.info("#--------------------------#");
		ProcessManagerServer.logger.info("   port > "+port);
		ProcessManagerServer.logger.info("   number user > "+userAndMdp.size());
		ProcessManagerServer.logger.info("#--------------------------#");
	}
	
	public void saveParameter() throws IOException {
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
				
	public void loadParameter() throws IOException {
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
			System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
			logger = Logger.getGlobal();
			File logs = new File("./logs");
			if (!logs.exists()) logs.mkdirs();
			String nameFile = "";
			int i = 0;
			do {
				nameFile = "./logs/"+getLogTime()+'_'+i+".log";
				i++;
			} while(new File(nameFile).exists());
			FileHandler fileHandler = new FileHandler(nameFile);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
			System.setErr(System.out);
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
				this.server = new Server(this);
			} catch (Exception e) {
				ProcessManagerServer.logger.warning(e.getMessage()+"\nCan be port \""+port+"\" already use !");
				return;
			}
			printInfo();
			processManager = new ProcessManager();
			//----------------------------------------------
			this.cmdManager = new CommandManager(System.in);
			cmdManager.registreCommand("help", new CommandHelp(this));
			cmdManager.registreCommand("listuser", new CommandListUser(this));
			cmdManager.registreCommand("stop", new CommandStop(this));
			cmdManager.registreCommand("savepara", new CommandSavePara(this));
			cmdManager.registreCommand("loadpara", new CommandLoadPara(this));
			cmdManager.registreCommand("kick", new CommandKick(this));
			cmdManager.registreCommand("kickall", new CommandKickAll(this));
			cmdManager.registreCommand("list", new CommandList(this));
			cmdManager.registreCommand("listprocess", new CommandListProcess(this));
			cmdManager.registreCommand("addprocess", new CommandAddProcess(this));
			cmdManager.registreCommand("editprocess", new CommandEditProcess(this));
			cmdManager.registreCommand("chat", new CommandChat(this));
			cmdManager.registreCommand("stopprocess", new CommandStopProcess(this));
			cmdManager.registreCommand("startprocess", new CommandStartProcess(this));
			cmdManager.registreCommand("removeprocess", new CommandRemoveProcess(this));
			cmdManager.registreCommand("renameprocess", new CommandRenameProcess(this));
			//cmdManager.registreCommand("reloadprocess", new CommandStartProcess(this));
			//cmdManager.registreCommand("restartprocess", new CommandStartProcess(this));
			cmdManager.start();
			//----------------------------------------------
		} catch (IOException e) {
			ProcessManagerServer.logger.warning(e.getMessage());
		}
	}
	
	public void stop() throws IOException {
		if (server != null) server.close();
		System.exit(0);
	}
				
}
