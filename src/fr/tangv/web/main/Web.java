package fr.tangv.web.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Web {

	private Web web;
	private int port;
	private ServerSocket serv;
	private String pathResource;
	private Logger logger;
	
	public void stop() throws IOException {
		serv.close();
	}
	
	public int getPort() {
		return port;
	}
	
	public String getPathResource() {
		return pathResource;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public boolean hasLogger() {
		return logger!=null;
	}
	
	public Web(int port, String pathResource) throws IOException {
		this(port, pathResource, null, 10);
	}
	
	public Web(int port, String pathResource, Logger logger, int backlog) throws IOException {
		this.port = port;
		this.pathResource = pathResource;
		this.web = this;
		this.logger = logger;
		serv = new ServerSocket(port, backlog);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!serv.isClosed()) {
					try {
						Socket socket = serv.accept();
						new HandleSocket(socket, web);
					} catch (IOException e) {
						if (web.hasLogger())
							web.getLogger().warning(e.getMessage());
						else
							e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
	
}
