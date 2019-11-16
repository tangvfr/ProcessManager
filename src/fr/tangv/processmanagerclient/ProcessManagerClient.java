package fr.tangv.processmanagerclient;

import javax.swing.JOptionPane;

import fr.tangv.processmanagerclient.util.DataConnect;
import fr.tangv.processmanagerclient.windows.BootStrap;

public class ProcessManagerClient {
	
	private BootStrap bootStrap;
	private InterfacePM interfacePM;
	
	public BootStrap getBootStrap() {
		return bootStrap;
	}
	
	public InterfacePM getInterfacePM() {
		return interfacePM;
	}
	
	public ProcessManagerClient() {
		bootStrap = new BootStrap(this);
		bootStrap.setVisible(true);
	}
	
	public void connect(String ip, int port, String username, String password) {
		System.out.println(ip);
		System.out.println(port);
		System.out.println(username);
		System.out.println(password);
		DataConnect data = new DataConnect(ip, port, username, password);
		if (data.isValid()) {
			bootStrap.setVisible(false);
			interfacePM = new InterfacePM(data, this);
			interfacePM.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(bootStrap, "Invalid input !", "Connect", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
