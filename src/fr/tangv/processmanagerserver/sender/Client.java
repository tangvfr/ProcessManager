package fr.tangv.processmanagerserver.sender;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import fr.tangv.processmanagerserver.ProcessManagerServer;

public class Client implements Sender {

	private String name;
	private OutputStream out;
	private String ip;
	
	public Client (Socket socket ,ProcessManagerServer processManagerServer) {
		this.ip = socket.getInetAddress().getHostAddress();
		this.name = null;
		try {
			ProcessManagerServer.logger.info("Try connect \""+ip+'\"');
			out = socket.getOutputStream();
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(socket.getInputStream(), "UTF8");
			this.name = sc.nextLine();
			String pwd = sc.nextLine();
			if (processManagerServer.getUserAndMdp().containsKey(name)) {
				if (processManagerServer.getUserAndMdp().get(name).equals(pwd)) {
					for (Client client : processManagerServer.getServer().getClients()) {
						if (client.name.equals(name)) {
							ProcessManagerServer.logger.info("Connect deny \""+ip+"\" user is already connect");
							out.write("Access deny because already connect !".getBytes());
							socket.close();
							return;
						}
					}
					ProcessManagerServer.logger.info("Connect allow \""+ip+"\" user \""+name+"\"");
					out.write("Access allow !".getBytes());
					processManagerServer.getServer().getClients().add(this);
					//suite
					
				} else {
					ProcessManagerServer.logger.info("Connect deny \""+ip+"\" password of \""+name+"\" is invalid");
					out.write("Access deny !".getBytes());
					socket.close();
					return;
				}
			} else {
				ProcessManagerServer.logger.info("Connect deny \""+ip+"\" user \""+name+"\" is invalid");
				out.write("Access deny !".getBytes());
				socket.close();
				return;
			}
		} catch (Exception e) {}
		if (processManagerServer.getServer().getClients().contains(this))
			processManagerServer.getServer().getClients().remove(this);
		ProcessManagerServer.logger.info("Deconnect \""+ip+"\" user \""+name+"\"");
	}

	@Override
	public void send(String string) {
		try {
			out.write(string.getBytes());
		} catch (IOException e) {
			ProcessManagerServer.logger.warning(e.getMessage());
		}
	}

	@Override
	public String getName() {
		return name;
	}
	
	public String getIp() {
		return ip;
	}
	
}
