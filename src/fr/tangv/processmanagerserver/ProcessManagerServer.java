package fr.tangv.processmanagerserver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class ProcessManagerServer {

	public static void main(String args[]) throws IOException {
		Process process = Runtime.getRuntime().exec("java -Dlog4j.skipJansi=true -jar spigot-1.14.jar", new String[] {}, new File("C:/Users/tangv/Bureau/Jeux/Serveur_test"));
		//Process process = Runtime.getRuntime().exec("cmd", new String[] {}, new File("C:/Users/tangv/Bureau/Jeux/Serveur_test"));
		
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
						out.flush();
					}
					System.out.println(process.isAlive());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		
		Thread threads = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Scanner sc = new Scanner(System.in, "UTF-8");
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), "UTF-8"));
					while (process.isAlive()) {
						String text = sc.next();
						bw.write(text);
						bw.newLine();
						bw.flush();
					}
					sc.close();
					bw.close();
					System.out.println("End scanner !");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
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
						out.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		threaderr.start();
		threads.start();
	}
	
}
