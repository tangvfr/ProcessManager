package fr.tangv.web.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.tangv.web.util.ByteArray;
import fr.tangv.web.util.PathHTTP;

public class ReceiveHTTP {

	private String methodeRequet;
	private PathHTTP pathRequet;
	private double versionHTTP;
	private Map<String, String> headRequet;
	private byte[] data;
	private boolean hasData;
	private String ip;
	private boolean valid;
	
	public ReceiveHTTP(Socket socket) throws IOException {
		ip = socket.getInetAddress().getHostAddress();
		methodeRequet = null;
		pathRequet = null;
		versionHTTP = 0.0;
		//traitement
		InputStream in = socket.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in));
		String requetHead = read.readLine();
		if (!requetHead.isEmpty()) {
			methodeRequet = requetHead.substring(0, requetHead.indexOf(" "));
			pathRequet = new PathHTTP(requetHead.substring(requetHead.indexOf(" ")+1, requetHead.indexOf(" HTTP/")));
			versionHTTP = Double.parseDouble(requetHead.substring(requetHead.indexOf(" HTTP/")+6, requetHead.length()));
		}
		headRequet = new HashMap<String, String>();
		String requetText;
		while (!(requetText = read.readLine()).isEmpty()) {
			String[] text = requetText.split(": ", 2);
			if (text.length == 2) {
				headRequet.put(text[0], text[1]);
			} else {
				headRequet.put(text[0], "");
			}
		}
		long lengthData = 0;
		String keyParam = "Content-Length";
		if (headRequet.containsKey(keyParam)) {
			lengthData = Long.parseLong(headRequet.get(keyParam));
		}
		if (lengthData <= 1048576L) { //limite entry to 1mio
			valid = true;
			data = new ByteArray(read, (int) lengthData).bytes();
			hasData = data.length > 0;
		} else {
			valid = false;
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
	
	public boolean isValid() {
		return valid;
	}
	
}
