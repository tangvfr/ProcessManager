package fr.tangv.processmanagerserver.util;

import java.io.IOException;

public class ProcessPlus {

	private Process process;
	private boolean activeOnStart;
	private String encoding;
	private String rep;
	private String cmd;
	private String name;
	
	public ProcessPlus(String name, String cmd, String rep, String encoding, boolean activeOnStart) throws IOException {
		this.activeOnStart = activeOnStart;
		this.name = name;
		this.cmd = cmd;
		this.rep = rep;
		this.encoding = encoding;
		this.process = new Process(cmd, rep, encoding);
		if (activeOnStart)
			this.process.start();
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
	
}
