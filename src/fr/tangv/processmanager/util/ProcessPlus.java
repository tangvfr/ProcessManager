package fr.tangv.processmanager.util;

import java.io.IOException;

public class ProcessPlus {

	private Process process;
	private boolean activeOnStart;
	private String encoding;
	private String rep;
	private String cmd;
	private String name;
	private String console;
	private String cmdStop;
	
	public ProcessPlus(String name, String cmd, String rep, String encoding, boolean activeOnStart, String cmdStop) throws IOException {
		this.activeOnStart = activeOnStart;
		this.name = name;
		this.cmd = cmd;
		this.rep = rep;
		this.encoding = encoding;
		this.cmdStop = cmdStop;
		this.process = new Process(cmd, rep, encoding);
		this.console = "";
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
	
	public synchronized void reload() {
		process.stop();
		process = new Process(cmd, rep, encoding);
	}
	
	public synchronized void send(String msg) throws IOException {
		if (process.isStart())
			process.send(msg);
	}
	
	public synchronized String read(int maxLine) throws IOException {
		String input = process.getInput();
		String error = process.getError();
		if (!input.isEmpty())
			this.console += input;
		if (!error.isEmpty())
			this.console += error;
		//-----------------------------------------------
		String[] consoleLine = console.split("\n");
		if (consoleLine.length > maxLine) {
			String newConsole = "";
			for (int i = consoleLine.length-maxLine; i < consoleLine.length; i++) {
				if (i == consoleLine.length-1)
					newConsole += consoleLine[i];
				else
					newConsole += consoleLine[i]+"\n";
			}
			this.console = newConsole;
		}
		return this.console;
	}
	
}
