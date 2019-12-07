package fr.tangv.processmanager.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import fr.tangv.Main;
import fr.tangv.processmanager.ProcessManagerServer;

public class WebServer {

	private ProcessManagerServer processManagerServer;
	private ServerSocket serv;
	private WebServer web;
	private int port;
	private Vector<RequetExecute> postRequetExecutes;
	private Vector<RequetExecute> getRequetExecutes;
	private Map<String, HandleBaliseExport> mapHandle;
	private ConcurrentHashMap<String, Long> listAntiBrutus;
	
	public ConcurrentHashMap<String, Long> getListAntiBrutus() {
		return listAntiBrutus;
	}
	
	public void addPostRequetExecutes(RequetExecute req) {
		postRequetExecutes.add(req);
	}
	
	public void addGetRequetExecutes(RequetExecute req) {
		getRequetExecutes.add(req);
	}
	
	public void registreHandle(String name, HandleBaliseExport handle) {
		mapHandle.put(name, handle);
	}
	
	public void stop() throws IOException {
		serv.close();
	}
	
	public int getPort() {
		return port;
	}
	
	public Vector<RequetExecute> getPostRequetExecutes() {
		return postRequetExecutes;
	}
	
	public Vector<RequetExecute> getGetRequetExecutes() {
		return getRequetExecutes;
	}
	
	public ProcessManagerServer getProcessManagerServer() {
		return processManagerServer;
	}
	
	private boolean isBlocked(String key) {
		Enumeration<String> keys = listAntiBrutus.keys();
		while (keys.hasMoreElements()) {
			if (keys.nextElement().equals(key))
				return true;
		}
		return false;
	}
	
	public WebServer(int port, ProcessManagerServer processManagerServer) throws IOException {
		this.web = this;
		this.getRequetExecutes = new Vector<RequetExecute>();
		this.postRequetExecutes = new Vector<RequetExecute>();
		this.mapHandle = new HashMap<String, HandleBaliseExport>();
		this.listAntiBrutus = new ConcurrentHashMap<String, Long>();
		this.port = port;
		this.processManagerServer = processManagerServer;
		serv = new ServerSocket(port);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (!serv.isClosed()) {
						Socket socket = serv.accept();
						String ip = socket.getInetAddress().getHostAddress();
						if (isBlocked(ip)) {
							long time = listAntiBrutus.get(ip);
							long last = System.currentTimeMillis()-time;
							if (last < 3000) {
								socket.close();
								ProcessManagerServer.logger.info(ip+" >: blocked");
							} else {
								listAntiBrutus.remove(ip);
								new HandleSocket(web, socket).start();
							}
						} else {
							new HandleSocket(web, socket).start();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	public String getContentType(String nameFile) {
		if (nameFile.endsWith(".html") || nameFile.endsWith(".htm") || nameFile.endsWith(".tangweb"))
			return "text/html; charset=UTF-8";
		else if (nameFile.endsWith(".css"))
			return "text/css";
		else
			return "text/plain";
	}
	
	public byte[] getNotFound() throws IOException {
		File file = new File("404.html");
		if (file.exists()) {
			FileInputStream inF = new FileInputStream(file);
			byte[] buff = new byte[(int) file.length()];
			inF.read(buff);
			inF.close();
			return buff;
		} else {
			return	"<html><head><title>Not found !</title><meta charset=\"UTF-8\"></head><body style=\"background: #3333DD;\"><center><h1>Not found page</h1></center></body></html>"
			.getBytes("UTF8");
		}
	}
	
	public byte[] getCodeFile(File file) throws IOException {
		FileInputStream inF = new FileInputStream(file);
		byte[] buff = new byte[(int) file.length()];
		inF.read(buff);
		inF.close();
		if (!file.getName().endsWith(".tangweb"))
			return buff;
		String code = new String(buff, "UTF8");
		//-----------------------------------------
		//modif opti
		while (code.contains("<export=")) {
			int startOneBalise = code.indexOf("<export=")+8;
			int endOneBalise = code.indexOf(">", startOneBalise);
			String nameBalise = code.substring(startOneBalise, endOneBalise);
			int endBalise = code.indexOf("</export>", endOneBalise);
			String contBalise = code.substring(endOneBalise+1, endBalise);
			//-------------------------------
			if (mapHandle.containsKey(nameBalise))
				code = code.substring(0, startOneBalise-8)+mapHandle.get(nameBalise).handle(this, nameBalise, contBalise)+code.substring(endBalise+9, code.length());
			else
				code = code.substring(0, startOneBalise-8)+code.substring(endBalise+9, code.length());
		}
		code.replace("<import=version>", Main.version);
		//-----------------------------------------
		return code.getBytes("UTF8");
	}
	
	public void sendPageName(OutputStream out, String name) throws IOException {
		if (name.equals("/")) {
			name = "/index.tangweb";
			if (!new File("./web/"+name).exists())
				name = "/index.html";
		}
		//------------------------------------
		File fileGet = new File("./web"+name);
		if (!fileGet.exists()) {
			sendRequet(out, getNotFound(), "text/html; charset=UTF-8");
		} else {
			String cont = getContentType(name);
			sendRequet(out, getCodeFile(fileGet), cont);
		}
	}
	
	public void sendRequet(OutputStream out, byte[] data, String contentType) throws IOException {
		out.write(("HTTP/1.1 200 OK\n"+
				"Date: "+new Date()+"\n"+
				"Server: Tangv_Serveur_Web_"+Main.version+"\n"+
				"Content-Length: "+data.length+"\n"+
				"Content-Type: "+contentType+"\n"+
				"\n").getBytes());
		out.flush();
		out.write(data, 0, data.length);
		out.flush();
	}
	
}
