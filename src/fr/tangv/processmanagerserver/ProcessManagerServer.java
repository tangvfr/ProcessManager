package fr.tangv.processmanagerserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.tangv.processmanagerserver.util.Process;

public class ProcessManagerServer {

	public static void main(String args[]) throws IOException {
		/*Process pro = new Process("cmd", StandardCharsets.UTF_8);
		pro.start();
		pro.send("chcp 65001");*/
		
		Process pro = new Process("java -Dlog4j.skipJansi=true -jar spigot-1.14.jar", "C:\\Users\\tangv\\Bureau\\Jeux\\Serveur_test", StandardCharsets.UTF_8);
		pro.start();
		
		JFrame frame = new JFrame("Testage");
		JTextArea pane = new JTextArea();
		pane.setEditable(false);
		pane.setAutoscrolls(true);
		JScrollPane pans = new JScrollPane(pane);
		frame.getContentPane().add(pans, BorderLayout.CENTER);
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
						pro.send(env.getText());
						env.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					/*try {
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), "UTF-8"));
						bw.write(env.getText());
						bw.newLine();
						bw.flush();
						env.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}*/
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
				//process.destroy();
				pro.stop();
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
				env.setBackground(Color.GREEN);
				while (pro.isStart()) {
					reloadText();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				reloadText();
				env.setBackground(Color.RED);
			}
			
			private void reloadText() {
				try {
					String error = pro.getError();
					String input = pro.getInput();
					String text = input+(!error.isEmpty() ? "\n"+error : "");
					pane.setText(pane.getText()+text);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		thread.start();
		
		/*Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				InputStream in = process.getInputStream();
				Scanner sc = new Scanner(in, "UTF-8");
				while (sc.hasNext()) {
					String text = sc.nextLine();
					setLine(pane.getText()+text+"\n", pane);
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
					setLine(pane.getText()+text+"\n", pane);
				}
				sc.close();
			}
		});
		threadr.start();*/
	}
	
	/*private static void setLine(String text, JTextArea pane) {
		pane.setText(text);
	}*/
	
}
