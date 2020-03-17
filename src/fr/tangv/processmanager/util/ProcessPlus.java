package fr.tangv.processmanager.util;

import java.io.IOException;

public class ProcessPlus {

	private volatile Process process;
	private volatile boolean activeOnStart;
	private volatile String encoding;
	private volatile String folder;
	private volatile String cmd;
	private volatile String name;
	private volatile String consoleInput;
	private volatile String consoleError;
	private volatile String cmdStop;
	
	public ProcessPlus(String name, String cmd, String folder, String encoding, boolean activeOnStart, String cmdStop) throws IOException {
		this.activeOnStart = activeOnStart;
		this.name = name;
		this.cmd = cmd;
		this.folder = folder;
		this.encoding = encoding;
		this.cmdStop = cmdStop;
		this.process = new Process(cmd, folder, encoding);
		this.consoleInput = "";
		this.consoleError = "";
	}
	
	public Process getProcess() {
		return process;
	}
	
	public boolean isActiveOnStart() {
		return activeOnStart;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public String getFolder() {
		return folder;
	}
	
	public String getCmd() {
		return cmd; 
	}
	
	public String getCmdStop() {
		return cmdStop; 
	}
	
	public String getName() {
		return name;
	}
	
	public void setActiveOnStart(boolean activeOnStart) {
		this.activeOnStart = activeOnStart;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public void setCmdStop(String cmdStop) {
		this.cmdStop = cmdStop;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void reload() throws Exception {
		if (!process.isStart()) {
			process = new Process(cmd, folder, encoding);
		} else {
			throw new Exception("reload impossible process "+name);
		}
	}
	
	public void send(String msg) throws IOException {
		if (process.isStart())
			process.send(msg);
	}
	
	public String readInput(int maxLine) throws IOException {
		String input = process.getInput();
		if (!input.isEmpty())
			this.consoleInput += input+"\r\n";
		//-----------------------------------------------
		String[] consoleLine = consoleInput.split("\n");
		if (consoleLine.length > maxLine) {
			String newConsole = "";
			for (int i = consoleLine.length-maxLine; i < consoleLine.length; i++) {
				newConsole += consoleLine[i]+"\n";
			}
			this.consoleInput = newConsole;
		}
		return this.consoleInput;
	}
	
	public String readError(int maxLine) throws IOException {
		String error = process.getError();
		if (!error.isEmpty())
			this.consoleError += error+"\r\n";
		//-----------------------------------------------
		String[] consoleLine = consoleError.split("\n");
		if (consoleLine.length > maxLine) {
			String newConsole = "";
			for (int i = consoleLine.length-maxLine; i < consoleLine.length; i++) {
				newConsole += consoleLine[i]+"\n";
			}
			this.consoleError = newConsole;
		}
		return this.consoleError;
	}
	
}
