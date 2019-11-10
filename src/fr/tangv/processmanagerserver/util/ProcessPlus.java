package fr.tangv.processmanagerserver.util;

import java.nio.charset.Charset;

public class ProcessPlus {

	private Process process;
	private boolean activeOnStart;
	private String encoding;
	private String rep;
	private String cmd;
	private String name;
	
	public Process getProcess() {
		return process;
	}
	
	public boolean getActiveOnStart() {
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
		process = new Process(cmd, rep, Charset.forName(encoding));
	}
	
}
