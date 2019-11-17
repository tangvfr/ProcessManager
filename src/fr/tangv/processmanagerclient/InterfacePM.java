package fr.tangv.processmanagerclient;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import fr.tangv.Main;
import fr.tangv.processmanagerclient.util.DataConnect;
import jdk.internal.util.xml.impl.ReaderUTF8;

public class InterfacePM extends JFrame {

	private static final long serialVersionUID = -8204388851389070178L;
	private ProcessManagerClient processManagerClient;
	private DataConnect data;
	private Socket socket;
	
	public InterfacePM(DataConnect data, ProcessManagerClient processManagerClient) throws IOException {
		super();
		this.data = data;
		this.processManagerClient = processManagerClient;
		//-------------------------------------
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setResizable(true);
		this.setLocationRelativeTo(processManagerClient.getBootStrap());
		this.setTitle("ProcessManager "+Main.version);
		//-------------------------------------
		JTextField text = new JTextField();
		this.getContentPane().add(text,BorderLayout.CENTER);
		JButton button = new JButton("Send");
		button.addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {
				try {
					send(text.getText());
					text.setText("");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseClicked(MouseEvent e) {}
		});
		this.getContentPane().add(button,BorderLayout.SOUTH);
		connect();
	}
	
	public void send(String data) throws IOException {
		OutputStream out = socket.getOutputStream();
		out.write((data+'\n').getBytes("UTF8"));
		out.flush();
	}
	
	public void connect() throws IOException {
		socket = new Socket(data.getIp(), data.getPort());
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(socket.isConnected()) {
						InputStream in = socket.getInputStream();
						if (in.available() != 0) {
							byte[] buf = new byte[in.available()];
							in.read(buf);
							String text = new String(buf, "UTF8");
							System.out.println(">D: "+text);
						}
					}
					//sc.close();
				} catch (Exception e) {
					System.out.println(">E: "+e.getLocalizedMessage());
				}
				System.out.println(">C: close");
				processManagerClient.getInterfacePM().setVisible(false);
				processManagerClient.getBootStrap().setVisible(true);
			}
		});
		thread.start();
	}
	
	public void stop() throws IOException {
		socket.close();
	}
	
}
