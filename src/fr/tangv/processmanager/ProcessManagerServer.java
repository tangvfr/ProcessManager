package fr.tangv.processmanager;

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

import fr.tangv.processmanager.commands.CommandCheckUpdate;
import fr.tangv.processmanager.commands.CommandHelp;
import fr.tangv.processmanager.commands.CommandListUsers;
import fr.tangv.processmanager.commands.CommandLoadUsers;
import fr.tangv.processmanager.commands.CommandManager;
import fr.tangv.processmanager.commands.CommandSaveUsers;
import fr.tangv.processmanager.commands.CommandStop;
import fr.tangv.processmanager.commands.CommandStopNoForce;
import fr.tangv.processmanager.commands.CommandStopNoForceNoScript;
import fr.tangv.processmanager.commands.CommandStopScript;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;

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
	private boolean stopNoForce;
	
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
	
	public boolean isStopNoForce() {
		return this.stopNoForce;
	}
	
	public void saveUsers() throws IOException {
		if (!fileParameter.exists())
			fileParameter.createNewFile();
		FileOutputStream out = new FileOutputStream(fileParameter);
		String text = "";
		for (String user : userAndMdp.keySet()) {
			text += user;
			text += "\n"+userAndMdp.get(user);
			text += "\n";
			text += "\n";
		}
		out.write(text.getBytes("UTF8"));
		out.close();
	}
				
	public void loadUsers() throws IOException {
		if (!fileParameter.exists())
			saveUsers();
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
		this.stopNoForce = false;
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
				"Version: "+Main.version+"\r\n"+Main.getUpdate(false)+"\r\n");
		//----------------------------------------
		this.userAndMdp = new HashMap<String, String>();
		this.userAndMdp.put("admin", "pass");
		fileParameter = new File("./users");
		try {
			Main.loadData();
			loadUsers();
			ProcessManagerServer.logger.info("Load Users, Number Users: "+userAndMdp.size());
			processManager = new ProcessManager();
			//----------------------------------------------
			this.cmdManager = new CommandManager(System.in);
			cmdManager.registreCommand("help", new CommandHelp(this));
			cmdManager.registreCommand("listusers", new CommandListUsers(this));
			cmdManager.registreCommand("stop", new CommandStop(this));
			cmdManager.registreCommand("stopnoforce", new CommandStopNoForce(this));
			cmdManager.registreCommand("stopnoforcenoscript", new CommandStopNoForceNoScript(this));
			cmdManager.registreCommand("stopscript", new CommandStopScript(this));
			cmdManager.registreCommand("saveusers", new CommandSaveUsers(this));
			cmdManager.registreCommand("loadusers", new CommandLoadUsers(this));
			cmdManager.registreCommand("checkupdate", new CommandCheckUpdate());
			cmdManager.start();
			//----------------------------------------------
			Main.timeStart = System.currentTimeMillis();
			Main.dateRestart = Main.dateRestart(-Main.timeStopNoForce).getTime();
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						long time = System.currentTimeMillis();
						Main.timeIsStart = time-Main.timeStart;
						if (Main.timeStopNoForce != 0) {
							if (Main.timeStopNoForce > 0) {
								Main.timeRestart = Main.timeStopNoForce-Main.timeIsStart;
								if (Main.timeRestart <= 0) {
									Main.timeRestart = 0;
									stopNoForce(true);
									break;
								}
							} else {
								Main.timeRestart = Main.dateRestart-time;
								if (Main.timeRestart <= 0) { 
									Main.timeRestart = 0;
									stopNoForce(true);
									break;
								}
							}
						} else {
							Main.timeRestart = 0;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							ProcessManagerServer.logger.warning(e.getMessage());
						}
					}
				}
			});
			thread.start();
			//----------------------------------------------
		} catch (IOException e) {
			ProcessManagerServer.logger.warning(e.getMessage());
		}
	}
	
	private boolean allProcessIsOff() {
		for (ProcessPlus process : processManager.getListProcess()) {
			if (process.getProcess().isStart())
				return false;
		}
		return true;
	}
	
	public void stopNoForce(boolean script) {
		this.stopNoForce = true;
		for (ProcessPlus process : processManager.getListProcess()) {
			if (process.getCmdStop() != null && !process.getCmdStop().isEmpty() && !process.getCmdStop().equals(" ")) {
				try {
					process.send(process.getCmdStop());
				} catch (IOException e) {
					ProcessManagerServer.logger.warning(e.getMessage());
				}
			} else {
				process.getProcess().stop();
			}
		}
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				ProcessManagerServer.logger.warning(e.getMessage());
			}
			if (allProcessIsOff())
				break;
		}
		if (script)
			stopScript();
		else
			stop();
	}
	
	public void stopScript() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				String cmd = Main.cmdEnd;
				if (cmd != null && !cmd.isEmpty() && !cmd.equals(" ")) {
					try {
			            String os = System.getProperty("os.name").toLowerCase();
			            if (os.contains("win")) {
			                Runtime.getRuntime().exec("cmd /c start "+cmd);
			            } else {
			                Runtime.getRuntime().exec("sh "+cmd);
			            }
			        } catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		stop();
	}
	
	public void stop() {
		System.exit(0);
	}
				
}
