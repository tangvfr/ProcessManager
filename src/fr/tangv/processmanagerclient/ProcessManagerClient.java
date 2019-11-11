package fr.tangv.processmanagerclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ProcessManagerClient {
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(206);
			System.err.println("Start !");
			Socket socket = server.accept();
			System.err.println("Client !");
			int len;
			byte[] buff = new byte[256];
			Scanner sc = new Scanner(socket.getInputStream());
			int i = 0;
			while(socket.isConnected()) {
				if (sc.hasNextLine()) {
					System.out.println(sc.nextLine());
					if (i == 13) {
						socket.getOutputStream().write("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\n\r\n<h1> Hello, NodeMCU.</h1>".getBytes());
						System.out.println("--------------send--------------");
					}
					i++;
				}
			}
			server.close();
			System.err.println("Close !");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
