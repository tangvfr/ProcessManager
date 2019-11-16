package fr.tangv.processmanagerclient.windows;

import javax.swing.JButton;
import javax.swing.JFrame;

import fr.tangv.Main;

public class BootStrap extends JFrame {

	private JButton buttonConnect;
	
	public BootStrap() {
		super();
		//-------------------------------------
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("ProcessManagerClient "+Main.version);
		//-------------------------------------
		buttonConnect = new JButton("Connect");
		buttonConnect.setName("buttonConnect");
		
	}
	
	
	
}
