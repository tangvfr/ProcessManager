package web;

import java.util.UUID;

public class Token {

	private UUID uuid;
	private long date;
	private String user;
	
	public Token(String user) {
		this.uuid = UUID.randomUUID();
		this.date = System.currentTimeMillis();
		this.user = user;
	}
	
	public void updateDate() {
		this.date = System.currentTimeMillis();
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public long getDate() {
		return date;
	}
	
	public String getUser() {
		return user;
	}
	
	@Override
	public String toString() {
		return uuid.toString();
	}
	
}
