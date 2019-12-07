package fr.tangv.processmanager.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import fr.tangv.processmanager.ProcessManagerServer;

public class HandleSocket extends Thread {
	
	private Socket socket;
	private WebServer webServer;
	
	public HandleSocket(WebServer webServer ,Socket socket) {
		this.webServer = webServer;
		this.socket = socket;
	}
	
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
				if (!dataRequet.startsWith("read "))
					ProcessManagerServer.logger.info(ipRequet+" >: "+typeRequet+" >: "+hostRequet+" >: "+repRequet+" >: "+contTypeRequet+" >: "+dataRequet);
				OutputStream out = socket.getOutputStream();
				try {
					if (typeRequet.equals("GET")) {
						for (RequetExecute req : webServer.getGetRequetExecutes()) {
							req.execute(webServer, out, typeRequet, repRequet, hostRequet, contTypeRequet, dataRequet, ipRequet);
						}
					} else if (typeRequet.equals("POST")) {
						for (RequetExecute req : webServer.getPostRequetExecutes()) {
							req.execute(webServer, out, typeRequet, repRequet, hostRequet, contTypeRequet, dataRequet, ipRequet);
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
	
}
