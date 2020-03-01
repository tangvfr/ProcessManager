package fr.tangv.processmanager.util;

import java.io.IOException;

public class ProcessPlus {

	private volatile Process process;
	private volatile boolean activeOnStart;
	private volatile String encoding;
	private volatile String rep;
	private volatile String cmd;
	private volatile String name;
	private volatile String consoleInput;
	private volatile String consoleError;
	private volatile String cmdStop;
	
	public ProcessPlus(String name, String cmd, String rep, String encoding, boolean activeOnStart, String cmdStop) throws IOException {
		this.activeOnStart = activeOnStart;
		this.name = name;
		this.cmd = cmd;
		this.rep = rep;
		this.encoding = encoding;
		this.cmdStop = cmdStop;
		this.process = new Process(cmd, rep, encoding);
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
	
	public String getRep() {
		return rep;
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
	
	public void setRep(String rep) {
		this.rep = rep;
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
	
	public void reload() {
		process.stop();
		process = new Process(cmd, rep, encoding);
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
