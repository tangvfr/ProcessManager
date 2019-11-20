package fr.tangv.processmanagerclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import fr.tangv.processmanagerserver.ProcessManagerServer;

public class WebServer {

	private ProcessManagerServer processManagerServer;
	private ServerSocket serv;
	private int port;
	private Vector<RequetExecute> postRequetExecutes;
	private Vector<RequetExecute> getRequetExecutes;
	private Map<String, HandleBaliseExport> mapHandle;
	private WebServer web;
	
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
	
	public ProcessManagerServer getProcessManagerServer() {
		return processManagerServer;
	}
	
	public WebServer(int port, ProcessManagerServer processManagerServer) {
		this.web = this;
		this.getRequetExecutes = new Vector<RequetExecute>();
		this.postRequetExecutes = new Vector<RequetExecute>();
		this.mapHandle = new HashMap<String, HandleBaliseExport>();
		this.port = port;
		this.processManagerServer = processManagerServer;
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serv = new ServerSocket(port);
					while (!serv.isClosed()) {
						Socket socket = serv.accept();
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									//lecture text
									InputStream in = socket.getInputStream();
									byte[] buf = new byte[1];
									String data = "";
									in.read(buf);
									data += new String(buf, "UTF8");
									if (in.available() > 0) {
										buf = new byte[in.available()];
										in.read(buf);
										data += new String(buf, "UTF8");
									}
									//traitement text
									try {
										String lineData = data.substring(0, data.indexOf(" HTTP"));
										int separatorRequet = lineData.indexOf(" ");
										String typeRequet = lineData.substring(0, separatorRequet);
										String repRequet = lineData.substring(separatorRequet+1);
										String hostData = data.substring(data.indexOf("Host: ")+6);
										String hostRequet = hostData.substring(0, hostData.indexOf("\r\n"));
										String contType = data.substring(data.indexOf("Content-Type: ")+14);
										String contTypeRequet = contType.substring(0, contType.indexOf("\r\n"));
										String[] dataData = data.split("\n");
										String dataRequet = dataData[dataData.length-1].replace("\r", "").replace("\n", "");
										String ipRequet = socket.getInetAddress().getHostAddress();
										//traitement de la requet
										OutputStream out = socket.getOutputStream();
										try {
											if (typeRequet.equals("GET")) {
												for (RequetExecute req : getRequetExecutes) {
													req.execute(web, out, typeRequet, repRequet, hostRequet, contTypeRequet, dataRequet, ipRequet);
												}
											} else if (typeRequet.equals("POST")) {
												for (RequetExecute req : postRequetExecutes) {
													req.execute(web, out, typeRequet, repRequet, hostRequet, contTypeRequet, dataRequet, ipRequet);
												}
											}
										} catch (IOException e) {
											e.printStackTrace();
										}
									} catch (Exception e) {}
									socket.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});
						thread.start();
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
				"Server: Tangv_Serveur_Web_1.0\n"+
				"Content-Length: "+data.length+"\n"+
				"Content-Type: "+contentType+"\n"+
				"\n").getBytes());
		out.flush();
		out.write(data, 0, data.length);
		out.flush();
	}
	
}
