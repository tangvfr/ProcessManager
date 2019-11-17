package fr.tangv.processmanagerclient;

import java.io.File;
import java.io.FileInputStream;
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
							try {
								String lineData = data.substring(0, data.indexOf(" HTTP"));
								int separatorRequet = lineData.indexOf(" ");
								String typeRequet = lineData.substring(0, separatorRequet);
								String repRequet = lineData.substring(separatorRequet+1);
								String hostData = data.substring(data.indexOf("Host: ")+6);
								String hostRequet = hostData.substring(0, hostData.indexOf("\r\n"));
								String[] dataData = hostData.split("\n");
								String dataRequet = dataData[dataData.length-1].replace("\r", "").replace("\n", "");
								String ipRequet = socket.getInetAddress().getHostAddress();
								//--------------------------
								System.out.println(">:");
								System.out.println("Type: "+typeRequet);
								System.out.println("Rep: "+repRequet);
								System.out.println("Host: "+hostRequet);
								System.out.println("Data: "+dataRequet);
								System.out.println("Ip: "+ipRequet);
								System.out.println(":<");
								//traitement de la requet
								OutputStream out = socket.getOutputStream();
								try {
									if (data.startsWith("GET") || data.startsWith("HEAD")) {
										if (repRequet.equals("/")) {
											repRequet = "/index.tangweb";
											if (!new File("./web/"+repRequet).exists())
												repRequet = "/index.html";
										}
										//------------------------------------
										File fileGet = new File("./web/"+repRequet);
										if (!fileGet.exists()) {
											sendRequet(out,
													("<html><head><title>Not found !</title><meta charset=\"UTF-8\"></head><body style=\"background: #3333DD;\"><center><h1>Not found page</h1></center></body></html>"
													).getBytes("UTF8"), "text/html; charset=UTF-8");
										} else {
											String cont = getContentType(repRequet);
											sendRequet(out, getCodeFile(fileGet), cont);
										}
									} else if (data.startsWith("POST")) {
										sendRequet(out,
												("<html><head><title>Post fait</title><meta charset=\"UTF-8\"></head><body><center style=\"background: #222222; color: #ff00ff;\"><h1>Test Post: "+repRequet+"</h1><h1>Data send: "+dataRequet+"</h1></center></body></html>"
												).getBytes("UTF8"), "text/html; charset=UTF-8");
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
	
	public static String getContentType(String nameFile) {
		if (nameFile.endsWith(".html") || nameFile.endsWith(".htm") || nameFile.endsWith(".tangweb"))
			return "text/html; charset=UTF-8";
		else if (nameFile.endsWith(".css"))
			return "text/css";
		else
			return "text/plain";
	}
	
	public static byte[] getCodeFile(File file) throws IOException {
		FileInputStream inF = new FileInputStream(file);
		byte[] buff = new byte[(int) file.length()];
		inF.read(buff);
		inF.close();
		if (!file.getName().endsWith(".tangweb"))
			return buff;
		String code = new String(buff, "UTF8");
		

		code = code.replace("<import=etatProcess>", "onCase");
		code = code.replace("<import=onStartProcess>", "onStart");
		code = code.replace("<import=nameProcess>",	"serveurTest");
		code = code.replace("<import=cmdProcess>",	"java -jar server.jar");
		code = code.replace("<import=repProcess>",	"C:/Users/tangv/desktop");
		
		return code.getBytes("UTF8");
	}
	
	public static void sendRequet(OutputStream out, byte[] data, String contentType) throws IOException {
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
