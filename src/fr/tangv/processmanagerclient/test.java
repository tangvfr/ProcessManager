package fr.tangv.processmanagerclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class test {

	public static void main(String[] args) {
		try {
			ServerSocket serv = new ServerSocket(8080);
			while (true) {
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
							String lineData = data.substring(0, data.indexOf(" HTTP"));
							int separatorRequet = lineData.indexOf(" ");
							String typeRequet = lineData.substring(0, separatorRequet);
							String repRequet = lineData.substring(separatorRequet+1);
							String hostData = data.substring(data.indexOf("Host: ")+6);
							String hostRequet = hostData.substring(0, hostData.indexOf("\r\n"));
							String[] dataData = hostData.split("\n");
							String dataRequet = dataData[dataData.length-1];
							//--------------------------
							System.out.println(">:"+data+":<");
							System.out.println("Type: "+typeRequet);
							System.out.println("Rep: "+repRequet);
							System.out.println("Host: "+hostRequet);
							System.out.println("Data: "+dataRequet);
							System.out.println(":<");
							//traitement de la requet
							if (data.startsWith("GET") || data.startsWith("HEAD")) {
								sendSite(socket.getOutputStream(),
										"<html><head><title>Page Test !</title><meta charset=\"UTF-8\"></head><body><center> <form method=\"POST\" action=\"test.html\"><label for=\"pseudo\">Entrez un pseudo:</label><input type=\"text\" name=\"usercreat\" id=\"usercreat\"><br><br><label for=\"pswd\">Choisissez un mot de passe: </label><input type=\"password\" name=\"mdpcreat\" id=\"mdpcreat\"><br><br><label for=\"pswd\">Retapez votre mot de passe: </label><input type=\"password\" name=\"mdpcreat2\" id=\"mdpcreat\"><br><br><input type=\"submit\" value=\"Creation du compte !\" name=\"validation\"><h1>Bonsoir maitre</h1></center></body></html>"
										);
							} else if (data.startsWith("POST")) {
								sendSite(socket.getOutputStream(),
										"<html><head><title>Post fait</title><meta charset=\"UTF-8\"></head><body><center><h1>Test Post: "+repRequet+"</h1></center></body></html>"
										);
							}
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

	public static void sendSite(OutputStream out, String code) throws IOException {
		out.write(("HTTP/1.1 200 OK\n\r"+
				"Date: "+new Date()+"\n\r"+
				"Server: Tangv_Serveur_Web_1.0\n\r"+
				"Content-Length: "+code.length()+"\n\r"+
				"Content-Type: text/html; charset=UTF-8\n\r"+
				"\n\r").getBytes("UTF8"));
		out.flush();
		out.write(code.getBytes("UTF8"));
		out.flush();
	}
	
}
