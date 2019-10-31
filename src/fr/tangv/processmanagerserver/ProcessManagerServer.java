package fr.tangv.processmanagerserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessManagerServer {

	public static void main(String args[]) throws IOException {
		//Process process = Runtime.getRuntime().exec("java -jar spigot-1.14.jar", new String[] {}, new File("C:\\Users\\tangv\\Bureau\\Jeux\\Serveur 1.14\\"));
		Process process = Runtime.getRuntime().exec("cmd", new String[] {}, new File("C:\\Users\\tangv\\Bureau\\Jeux\\Serveur 1.14\\"));
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream in = process.getInputStream();
					OutputStream out = System.out;
					byte[] buffer = new byte[16];
					int taille;
					while((taille = in.read(buffer)) != -1) {
						out.write(buffer, 0, taille);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream in = System.in;
					OutputStream out = process.getOutputStream();
					byte[] buffer = new byte[16];
					int taille;
					while((taille = in.read(buffer)) != -1) {
						out.write(buffer, 0, taille);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		thread2.start();
	}
	
}
