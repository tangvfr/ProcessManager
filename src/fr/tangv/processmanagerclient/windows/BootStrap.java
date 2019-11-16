package fr.tangv.processmanagerclient.windows;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import fr.tangv.Main;

public class BootStrap extends JFrame {

	private static final long serialVersionUID = -2426224975575734396L;
	private JButton buttonConnect;
	private JPanel panel;
	
	public BootStrap() {
		super();
		//-------------------------------------
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("ProcessManager "+Main.version);
		//-------------------------------------
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		this.setContentPane(panel);
		//-------------------------------------
		Dimension dimMax = new Dimension(160, 22);
		//-------------------------------------
		JPanel ip = new JPanel();
		ip.setLayout(new BoxLayout(ip, BoxLayout.X_AXIS));
		ip.add(new JLabel("Ip:"));
		JTextField test1 = new JTextField("localhost");
		test1.setMaximumSize(dimMax);
		ip.add(test1);
		this.add(ip);
		//-------------------------------------
		JPanel port = new JPanel();
		port.setLayout(new BoxLayout(port, BoxLayout.X_AXIS));
		port.add(new JLabel("Port:"));
		JTextField test2 = new JTextField("206");
		test2.setMaximumSize(dimMax);
		port.add(test2);
		this.add(port);
		//-------------------------------------
		JPanel username = new JPanel();
		username.setLayout(new BoxLayout(username, BoxLayout.X_AXIS));
		username.add(new JLabel("Username:"));
		JTextField test3 = new JTextField("admin");
		test3.setMaximumSize(dimMax);
		username.add(test3);
		this.add(username);
		//-------------------------------------
		JPanel password = new JPanel();
		password.setLayout(new BoxLayout(password, BoxLayout.X_AXIS));
		password.add(new JLabel("Password:"));
		JPasswordField test4 = new JPasswordField();
		test4.setMaximumSize(dimMax);
		password.add(test4);
		this.add(password);
		//-------------------------------------
		buttonConnect = new JButton("Connect");
		buttonConnect.setName("buttonConnect");
		buttonConnect.setSize(90, 20);
		this.add(buttonConnect);
	}
	
	
	
}
