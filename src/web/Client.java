package web;

public class Client {

	private long date;
	private String ip;
	private int numberTry;
	private static final int numberTryMax = 3;
	private static final int timeTry = 5_000;
	
	public Client(String ip) {
		this.ip = ip;
		updateTime();
		resetTry();
	}
	
	private void updateTime() {
		date = System.currentTimeMillis()+timeTry;
	}
	
	public void addTry() {
		updateTime();
		numberTry++;
	}
	
	public void resetTry() {
		numberTry = 0;
	}
	
	public boolean itCan() {
		if (numberTry < numberTryMax) {
			return true;
		} else if (date <= System.currentTimeMillis()) {
			numberTry--;
			return true;
		}
		return false;
	}
	
	public String getIp() {
		return ip;
	}
	
}
