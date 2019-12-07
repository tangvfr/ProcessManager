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

import fr.tangv.Main;
import fr.tangv.processmanagerserver.commands.CommandAddProcess;
import fr.tangv.processmanagerserver.commands.CommandEditProcess;
import fr.tangv.processmanagerserver.commands.CommandHelp;
import fr.tangv.processmanagerserver.commands.CommandListProcess;
import fr.tangv.processmanagerserver.commands.CommandListUser;
import fr.tangv.processmanagerserver.commands.CommandLoadPara;
import fr.tangv.processmanagerserver.commands.CommandManager;
import fr.tangv.processmanagerserver.commands.CommandReadProcess;
import fr.tangv.processmanagerserver.commands.CommandRemoveProcess;
import fr.tangv.processmanagerserver.commands.CommandRenameProcess;
import fr.tangv.processmanagerserver.commands.CommandRestartProcess;
import fr.tangv.processmanagerserver.commands.CommandSavePara;
import fr.tangv.processmanagerserver.commands.CommandSendProcess;
import fr.tangv.processmanagerserver.commands.CommandStartProcess;
import fr.tangv.processmanagerserver.commands.CommandStop;
import fr.tangv.processmanagerserver.commands.CommandStopProcess;
import fr.tangv.processmanagerserver.util.ProcessManager;

public class ProcessManagerServer {
	
	public static Logger logger;
	
	private static String getLogTime() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		return format.format(new Date());
	}
	
	private File fileParameter;
	private Map<String, String> userAndMdp;
	private CommandManager cmdManager;
	private ProcessManager processManager;
	
	public ProcessManager getProcessManager() {
		return processManager;
	}
	
	public File getFileParameter() {
		return fileParameter;
	}
	
	public Map<String, String> getUserAndMdp() {
		return userAndMdp;
	}
	
	public CommandManager getCmdManager() {
		return cmdManager;
	}
	
	public void saveParameter() throws IOException {
		if (!fileParameter.exists())
			fileParameter.createNewFile();
		FileOutputStream out = new FileOutputStream(fileParameter);
		String text = "";
		for (String user : userAndMdp.keySet()) {
			text += "\n"+user;
			text += "\n"+userAndMdp.get(user);
			text += "\n";
		}
		out.write(text.getBytes("UTF8"));
		out.close();
	}
				
	public void loadParameter() throws IOException {
		if (!fileParameter.exists())
			saveParameter();
		Map<String, String> userAndMdp = new HashMap<String, String>();
		Scanner sc = new Scanner(fileParameter,"UTF8");
		while (sc.hasNextLine()) {
			String user = sc.nextLine();
			if (sc.hasNextLine()) {
				String mdp = sc.nextLine();
				if (!userAndMdp.containsKey(user))
					userAndMdp.put(user, mdp);
				else
					ProcessManagerServer.logger.warning("Error the user \""+user+"\" already exist !");
				if (sc.hasNextLine()) sc.nextLine();
			}
		}
		sc.close();
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
				"Version: "+Main.version+"\r\n");
		//----------------------------------------
		this.userAndMdp = new HashMap<String, String>();
		this.userAndMdp.put("admin", "password");
		fileParameter = new File("./parameter");
		try {
			loadParameter();
			ProcessManagerServer.logger.info("Number User: "+userAndMdp.size()+"\r\n");
			processManager = new ProcessManager();
			//----------------------------------------------
			this.cmdManager = new CommandManager(System.in);
			cmdManager.registreCommand("help", new CommandHelp(this));
			cmdManager.registreCommand("listuser", new CommandListUser(this));
			cmdManager.registreCommand("stop", new CommandStop(this));
			cmdManager.registreCommand("savepara", new CommandSavePara(this));
			cmdManager.registreCommand("loadpara", new CommandLoadPara(this));
			cmdManager.registreCommand("listprocess", new CommandListProcess(this));
			cmdManager.registreCommand("addprocess", new CommandAddProcess(this));
			cmdManager.registreCommand("editprocess", new CommandEditProcess(this));
			cmdManager.registreCommand("stopprocess", new CommandStopProcess(this));
			cmdManager.registreCommand("startprocess", new CommandStartProcess(this));
			cmdManager.registreCommand("removeprocess", new CommandRemoveProcess(this));
			cmdManager.registreCommand("renameprocess", new CommandRenameProcess(this));
			cmdManager.registreCommand("restartprocess", new CommandRestartProcess(this));
			cmdManager.registreCommand("readprocess", new CommandReadProcess(this));
			cmdManager.registreCommand("sendprocess", new CommandSendProcess(this));
			cmdManager.start();
			//----------------------------------------------
		} catch (IOException e) {
			ProcessManagerServer.logger.warning(e.getMessage());
		}
	}
	
	public void stop() throws IOException {
		System.exit(0);
	}
				
}
