package fr.tangv.processmanagerserver.commands;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import fr.tangv.processmanagerserver.sender.ConsoleSender;
import fr.tangv.processmanagerserver.sender.Sender;

public class CommandManager implements Runnable {

	private ConsoleSender consoleSender;
	private Scanner sc;
	private Thread thread;
	private boolean started;
	private Map<String, Command> commands;
	
	public ConsoleSender getConsoleSender() {
		return consoleSender;
	}
	
	public Map<String, Command> getCommands() {
		return commands;
	}
	
	public void registreCommand(String name, Command cmd) {
		commands.put(name, cmd);
	}
	
	public void unRegistreCommand(String name) {
		commands.remove(name);
	}
	
	public void executeCommand(String name, String arg, Sender sender) {
		consoleSender.send("\""+sender.getName()+"\" excute > "+name+" "+arg);
		if (commands.containsKey(name)) {
			if(!commands.get(name).command(sender, name, arg))
				sender.send(commands.get(name).getUsage());
		} else
			sender.send("Unknown command \""+name+"\" !");
	}
	
	public CommandManager(InputStream in) {
		sc = new Scanner(in,"UTF8");
		thread = new Thread(this);
		started = false;
		this.consoleSender = new ConsoleSender();
		this.commands = new HashMap<String, Command>();
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public void start() {
		started = true;
		thread.start();
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		started = false;
		thread.stop();
	}

	@Override
	public void run() {
		while(started) {
			String text = sc.nextLine();
			String[] textSplit = text.split(" ", 2);
			if (!textSplit[0].isEmpty())
				executeCommand(textSplit[0], textSplit.length==2 ? textSplit[1] : "", consoleSender);
		}
	}
	
}
