package fr.tangv.processmanagerserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class ProcessManagerServer {

	public static void main(String args[]) throws IOException {
		Process process = Runtime.getRuntime().exec("java -Dlog4j.skipJansi=true -jar spigot-1.14.jar", new String[] {}, new File("C:/Users/tangv/Bureau/Jeux/Serveur_test"));
		//Process process = Runtime.getRuntime().exec("cmd");//, new String[] {}, new File("C:\\Users\\tangv\\Bureau\\Jeux\\Serveur 1.14\\"));
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream in = process.getInputStream();
					OutputStream out = System.out;
					byte[] buffer = new byte[1024];
					int taille;
					while((taille = in.read(buffer)) != -1) {
						out.write(buffer, 0, taille);
					}
					System.out.println(process.isAlive());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		
		Thread threaderr = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream in = process.getErrorStream();
					OutputStream out = System.out;
					byte[] buffer = new byte[1024];
					int taille;
					while((taille = in.read(buffer)) != -1) {
						out.write(buffer, 0, taille);
					}
					System.out.println(process.isAlive());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		threaderr.start();
		
		Thread threads = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Scanner sc = new Scanner(System.in, "UTF-8");
					DataOutputStream out = new DataOutputStream(process.getOutputStream());
					while (process.isAlive()) {
						String text = sc.next();
						System.out.println(">>"+text);
						out.writeUTF(text);
						out.flush();
					}
					sc.close();
					out.close();
					System.out.println("End scanner !");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		threads.start();
	}
	
}
