package fr.tangv.processmanagerclient.util;

public class DataConnect {

	private String ip;
	private int port;
	private String username;
	private String password;
	
	public String getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public DataConnect(String ip, int port, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public boolean isValid() {
		return !ip.isEmpty() && port > 0 && !username.isEmpty() && !password.isEmpty();
	}
	
}
