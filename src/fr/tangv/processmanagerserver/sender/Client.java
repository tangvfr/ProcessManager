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
							sendData("Access deny because already connect !");
							socket.close();
							return;
						}
					}
					ProcessManagerServer.logger.info("Connect allow \""+ip+"\" user \""+name+"\"");
					sendData("Access allow !");
					processManagerServer.getServer().getClients().add(this);
					while(socket.isConnected()) {
						String text = sc.nextLine();
						String[] textSplit = text.split(" ", 2);
						if (!textSplit[0].isEmpty())
							processManagerServer.getCmdManager().executeCommand(textSplit[0], textSplit.length==2 ? textSplit[1] : "", this);
					}
				} else {
					ProcessManagerServer.logger.info("Connect deny \""+ip+"\" password of \""+name+"\" is invalid");
					sendData("Access deny !");
					socket.close();
					return;
				}
			} else {
				ProcessManagerServer.logger.info("Connect deny \""+ip+"\" user \""+name+"\" is invalid");
				sendData("Access deny !");
				socket.close();
				return;
			}
		} catch (Exception e) {}
		if (processManagerServer.getServer().getClients().contains(this))
			processManagerServer.getServer().getClients().remove(this);
		try {if (!socket.isClosed()) socket.close();} catch (Exception e) {}
		ProcessManagerServer.logger.info("Deconnect \""+ip+"\" user \""+name+"\"");
	}

	@Override
	public void send(String string) {
		try {
			sendData(string);
		} catch (Exception e) {
			ProcessManagerServer.logger.warning(e.getMessage());
		}
	}
	
	private void sendData(String string) throws Exception {
		out.write((byte) 2);
		out.write((string).getBytes());
		out.write((byte) 3);
	}

	@Override
	public String getName() {
		return name;
	}
	
	public String getIp() {
		return ip;
	}
	
}
