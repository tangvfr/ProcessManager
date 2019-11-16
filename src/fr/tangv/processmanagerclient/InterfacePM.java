package fr.tangv.processmanagerclient;

import javax.swing.JFrame;

import fr.tangv.Main;
import fr.tangv.processmanagerclient.util.DataConnect;

public class InterfacePM extends JFrame {

	private static final long serialVersionUID = -8204388851389070178L;
	private ProcessManagerClient processManagerClient;
	
	public InterfacePM(DataConnect data, ProcessManagerClient processManagerClient) {
		super();
		this.processManagerClient = processManagerClient;
		//-------------------------------------
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setResizable(true);
		this.setLocationRelativeTo(processManagerClient.getBootStrap());
		this.setTitle("ProcessManager "+Main.version);
		//-------------------------------------
	}
	
}
