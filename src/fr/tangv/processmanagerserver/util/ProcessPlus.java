package fr.tangv.processmanagerserver.util;

import java.io.IOException;

public class ProcessPlus {

	private Process process;
	private boolean activeOnStart;
	private String encoding;
	private String rep;
	private String cmd;
	private String name;
	private String console;
	
	public ProcessPlus(String name, String cmd, String rep, String encoding, boolean activeOnStart) throws IOException {
		this.activeOnStart = activeOnStart;
		this.name = name;
		this.cmd = cmd;
		this.rep = rep;
		this.encoding = encoding;
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
	
	public String read(int maxLine) throws IOException {
		String input = process.getInput().replace("\n", "<br>");
		String error = process.getError().replace("\n", "<br>");
		if (!input.isEmpty())
			this.console += "<span style=\"color: white;\">"+input+"</span>";
		if (!error.isEmpty())
			this.console += "<span style=\"color: red;\">"+error+"</span>";
		//-----------------------------------------------
		String[] consoleLine = console.split("<br>");
		if (consoleLine.length > maxLine) {
			String newConsole = "<span style=\"color: white;\">";
			for (int i = consoleLine.length-maxLine; i < consoleLine.length; i++) {
				if (i == consoleLine.length-1)
					newConsole += consoleLine[i];
				else
					newConsole += consoleLine[i]+"<br>";
			}
			this.console = newConsole+"</span>";
		}
		return this.console;
	}
	
}
