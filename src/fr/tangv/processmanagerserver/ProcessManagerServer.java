package fr.tangv.processmanagerserver;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProcessManagerServer {

	public static void main(String args[]) throws IOException {
		//Process process = Runtime.getRuntime().exec("java -Dlog4j.skipJansi=true -jar spigot-1.14.jar", new String[] {}, new File("C:/Users/tangv/Bureau/Jeux/Serveur_test"));
		//Process process = Runtime.getRuntime().exec("cmd", new String[] {}, new File("C:/Users/tangv/Bureau/Jeux/Serveur_test"));
		Process process = Runtime.getRuntime().exec("cmd");
		//ProcessBuilder pb = new ProcessBuilder("java -Dlog4j.skipJansi=true -jar spigot-1.14.jar");
		JFrame frame = new JFrame("Testage");
		JTextArea pane = new JTextArea();
		pane.setLineWrap(true);
		pane.setEditable(false);
		pane.setAutoscrolls(true);
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		JTextField env = new JTextField();
		env.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					try {
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), "UTF-8"));
						bw.write(env.getText());
						bw.newLine();
						bw.flush();
						env.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		frame.getContentPane().add(env, BorderLayout.SOUTH);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				process.destroy();
				System.out.println("closing");
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				InputStream in = process.getInputStream();
				Scanner sc = new Scanner(in, "UTF-8");
				while (sc.hasNext()) {
					String text = sc.nextLine();
					pane.setText(pane.getText()+text+"\n");
				}
				sc.close();
			}
		});
		thread.start();
		
		Thread threadr = new Thread(new Runnable() {
			@Override
			public void run() {
				InputStream in = process.getErrorStream();
				Scanner sc = new Scanner(in, "UTF-8");
				while (sc.hasNext()) {
					String text = sc.nextLine();
					pane.setText(pane.getText()+text+"\n");
				}
				sc.close();
			}
		});
		threadr.start();
	}
	
}
