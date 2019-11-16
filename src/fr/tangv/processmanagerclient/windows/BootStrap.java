package fr.tangv.processmanagerclient.windows;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import fr.tangv.Main;
import fr.tangv.processmanagerclient.ProcessManagerClient;

public class BootStrap extends JFrame implements MouseListener {

	private static final long serialVersionUID = -2426224975575734396L;
	private JButton buttonConnect;
	private JPanel panel;
	private ProcessManagerClient processManagerClient;
	private JTextField ipComp;
	private JTextField userComp;
	private JPasswordField passComp;
	private JSpinner portComp;
	
	public BootStrap(ProcessManagerClient processManagerClient) {
		super();
		this.processManagerClient = processManagerClient;
		//-------------------------------------
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(180, 150);
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
		ip.add(new JLabel("                Ip: "));
		ipComp = new JTextField("localhost");
		ipComp.setMaximumSize(dimMax);
		ip.add(ipComp);
		this.add(ip);
		//-------------------------------------
		JPanel port = new JPanel();
		port.setLayout(new BoxLayout(port, BoxLayout.X_AXIS));
		port.add(new JLabel("           Port: "));
		portComp = new JSpinner();
		portComp.setValue(new Integer(206));
		portComp.setMaximumSize(dimMax);
		port.add(portComp);
		this.add(port);
		//-------------------------------------
		JPanel username = new JPanel();
		username.setLayout(new BoxLayout(username, BoxLayout.X_AXIS));
		username.add(new JLabel("Username: "));
		userComp = new JTextField("admin");
		userComp.setMaximumSize(dimMax);
		username.add(userComp);
		this.add(username);
		//-------------------------------------
		JPanel password = new JPanel();
		password.setLayout(new BoxLayout(password, BoxLayout.X_AXIS));
		password.add(new JLabel("Password: "));
		passComp = new JPasswordField();
		passComp.setMaximumSize(dimMax);
		password.add(passComp);
		this.add(password);
		//-------------------------------------
		buttonConnect = new JButton("Connect");
		buttonConnect.setName("buttonConnect");
		buttonConnect.setSize(90, 20);
		buttonConnect.addMouseListener(this);
		this.add(buttonConnect);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		processManagerClient.connect(ipComp.getText(), (int)portComp.getValue(), userComp.getText(), new String(passComp.getPassword()));
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
