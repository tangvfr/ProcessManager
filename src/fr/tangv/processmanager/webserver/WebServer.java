package fr.tangv.processmanager.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import fr.tangv.processmanager.Main;
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
	
	public boolean isBlocked(String key) {
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
						new HandleSocket(web, socket).start();
					}
				} catch (IOException e) {
					ProcessManagerServer.logger.warning(e.getMessage());
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
		String res = "web/404.html";
		if (existRes(res)) {
			InputStream in = getClass().getClassLoader().getResourceAsStream(res);
			byte[] buff = new byte[in.available()];
			in.read(buff);
			in.close();
			return buff;
		} else {
			return	"<html><head><title>Not found !</title><meta charset=\"UTF-8\"></head><body style=\"background: #3333DD;\"><center><h1>Not found page</h1></center></body></html>"
			.getBytes("UTF8");
		}
	}
	
	public byte[] getCodeFile(String res) throws IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream(res);
		ByteArrayOutputStream bufo = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len;
		while (in.available() > 0) {
			len = in.read(buf);
			bufo.write(buf, 0, len);
		}
		bufo.close();
		in.close();
		byte[] buff = bufo.toByteArray();
		if (!res.endsWith(".tangweb"))
			return buff;
		String code = new String(buff, "UTF8");
		//----------------------------------------
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
		code = code
				.replace("<import=version>", Main.version)
				.replace("<import=cmdend>", Main.cmdEnd)
				.replace("<import=timestopnoforce>", Main.timeStopNoForce+"")
				.replace("<import=timestart>", Main.timeStart+"")
				.replace("<import=timeisstart>", Main.timeIsStart+"")
				.replace("<import=timerestart>", Main.timeRestart+"")
				.replace("<import=daterestart>", Main.dateRestart+"")
				.replace("<import=isstop>", web.getProcessManagerServer().isStopNoForce()+"")
				.replace("<import=checkupdate>", Main.getUpdate(true));
		//----------------------------------------
		return code.getBytes("UTF8");
	}
	
	private boolean existRes(String res) {
		return getClass().getClassLoader().getResource(res) != null;
	}
	
	public void sendPageName(OutputStream out, String name) throws IOException {
		if (name.equals("/")) {
			name = "/index.tangweb";
			if (!existRes("/web/"+name))
				name = "/index.html";
		}
		//------------------------------------
		String res = "web"+name;
		if (!existRes(res)) {
			sendRequet(out, getNotFound(), "text/html; charset=UTF-8");
		} else {
			String cont = getContentType(name);
			sendRequet(out, getCodeFile(res), cont);
		}
	}
	
	public void sendRequet(OutputStream out, byte[] data, String contentType) throws IOException {
		out.write(("HTTP/1.1 200 OK\r\n"+
				"Date: "+new Date()+"\r\n"+
				"Server: Tangv_Serveur_Web_"+Main.version+"\r\n"+
				"Content-Length: "+data.length+"\r\n"+
				"Content-Type: "+contentType+"\r\n"+
				"\r\n").getBytes());
		out.flush();
		out.write(data, 0, data.length);
		out.flush();
	}
	
}
