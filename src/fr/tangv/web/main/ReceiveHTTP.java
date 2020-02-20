package fr.tangv.web.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.tangv.web.util.ByteArray;
import fr.tangv.web.util.ByteMark;
import fr.tangv.web.util.PathHTTP;

public class ReceiveHTTP {

	private String methodeRequet;
	private PathHTTP pathRequet;
	private double versionHTTP;
	private Map<String, String> headRequet;
	private byte[] data;
	private boolean hasData;
	private String ip;
	
	public ReceiveHTTP(Socket socket) throws IOException {
		ip = socket.getInetAddress().getHostAddress();
		InputStream in = socket.getInputStream();
		byte[] requet = new ByteArray(in).bytes();
		byte[] mark = "\r\n\r\n".getBytes();
		int lengthMark = new ByteMark(requet, mark).locMark()+1;
		ByteArrayInputStream inb = new ByteArrayInputStream(requet);
		byte[] head = new byte [lengthMark];
		inb.read(head, 0, head.length);
		data = new byte [requet.length-lengthMark];
		inb.read(data, 0, data.length);
		hasData = data.length > 0;
		//traitement
		String[] requetText = new String(head).split("\r\n");
		if (!requetText[0].isEmpty()) {
			methodeRequet = requetText[0].substring(0, requetText[0].indexOf(" "));
			pathRequet = new PathHTTP(requetText[0].substring(requetText[0].indexOf(" ")+1, requetText[0].indexOf(" HTTP/")));
			versionHTTP = Double.parseDouble(requetText[0].substring(requetText[0].indexOf(" HTTP/")+6, requetText[0].length()));
		} else {
			methodeRequet = null;
			pathRequet = null;
			versionHTTP = 0.0;
		}
		headRequet = new HashMap<String, String>();
		for (int i = 1; i < requetText.length; i++) {
			String[] text = requetText[i].split(": ", 2);
			if (text.length == 2) {
				headRequet.put(text[0], text[1]);
			} else {
				headRequet.put(text[0], "");
			}
		}
	}
	
	public String getMethodeRequet() {
		return methodeRequet;
	}
	
	public PathHTTP getPathRequet() {
		return pathRequet;
	}
	
	public double getVersionHTTP() {
		return versionHTTP;
	}
	
	public String getHeadRequet(String name) {
		return headRequet.get(name);
	}
	
	public boolean containsHeadRequet(String name) {
		return headRequet.containsKey(name);
	}
	
	public Set<String> getAllHeadRequet() {
		return headRequet.keySet();
	}
	
	public boolean hasData() {
		return hasData;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public String getIp() {
		return ip;
	}
	
}
