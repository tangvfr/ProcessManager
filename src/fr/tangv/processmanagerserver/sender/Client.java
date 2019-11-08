package fr.tangv.processmanagerserver.sender;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import fr.tangv.processmanagerserver.ProcessManagerServer;

public class Client implements Sender {

	private ProcessManagerServer processManagerServer;
	private String name;
	private DataOutputStream out;
	
	public Client (Socket socket ,ProcessManagerServer processManagerServer) throws IOException {
		ProcessManagerServer.logger.info("Try connect \""+socket.getInetAddress().getHostAddress()+'\"');
		out = new DataOutputStream(socket.getOutputStream());
		DataInputStream in = new DataInputStream(socket.getInputStream());
		this.name = in.readUTF();
		String pwd = in.readUTF();
		if (processManagerServer.getUserAndMdp().containsKey(name)) {
			if (processManagerServer.getUserAndMdp().get(name).equals(pwd)) {
				for (Client client : processManagerServer.getServer().getClients()) {
					if (client.name.equals(name)) {
						ProcessManagerServer.logger.info("Connect deny \""+socket.getInetAddress().getHostAddress()+"\" user is already connect");
						out.writeUTF("Deny access because already connect !");
						socket.close();
						return;
					}
				}
				out.writeUTF("Allow access !");
				//suite
				
			} else {
				ProcessManagerServer.logger.info("Connect deny \""+socket.getInetAddress().getHostAddress()+"\" password of \""+name+"\" is invalid");
				out.writeUTF("Deny access !");
				socket.close();
				return;
			}
		} else {
			ProcessManagerServer.logger.info("Connect deny \""+socket.getInetAddress().getHostAddress()+"\" user is invalid");
			out.writeUTF("Deny access !");
			socket.close();
			return;
		}
	}

	@Override
	public void send(String string) {
		try {
			out.writeUTF(string);
			out.flush();
		} catch (IOException e) {
			ProcessManagerServer.logger.warning(e.getMessage());
		}
	}

	@Override
	public String getName() {
		return name;
	}
	
}
