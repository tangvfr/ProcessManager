package fr.tangv.processmanagerserver.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import fr.tangv.processmanagerserver.ProcessManagerServer;
import fr.tangv.processmanagerserver.sender.Client;

public class Server extends Thread {

	private ProcessManagerServer processManagerServer;
	private ServerSocket server;
	private boolean close;
	private int port;
	private Vector<Client> clients;
	
	public Vector<Client> getClients() {
		return clients;
	}
	
	public void close() throws IOException {
		this.clients.clear();
		this.close = true;
		this.server.close();
	}
	
	public Server(ProcessManagerServer processManagerServer) throws IOException {
		super();
		this.processManagerServer = processManagerServer;
		this.close = false;
		this.port = processManagerServer.getPort();
		this.server = new ServerSocket(port);
		this.clients = new Vector<Client>();
		this.start();
	}
	
	@Override
	public void run() {
		while (server != null && !server.isClosed() && !close) {
			try {
				Socket socket = server.accept();
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							new Client(socket, processManagerServer);
						} catch (IOException e) {
							if (!close) 
								ProcessManagerServer.logger.warning(e.getMessage());
						}
					}
				});
				thread.start();
			} catch (IOException e) {
				if (!close) 
					ProcessManagerServer.logger.warning(e.getMessage());
			}
		}
	}
	
}
