package fr.tangv.processmanagerserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProcessManagerServer {

	public static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH-mm-ss");
		return format.format(new Date());
	}
	
	public static String getLogsTime() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH-mm-ss");
		return '['+format.format(new Date())+"] ";
	}
	
	public static void main(String[] args) {
		new ProcessManagerServer();
	}
	
	private ServerSocket server;
	private File fileParameter;
	private int port;
	private Map<String, String> userAndMdp;
	
	private void saveParameter() throws IOException {
		if (!fileParameter.exists())
			fileParameter.createNewFile();
		FileOutputStream out = new FileOutputStream(fileParameter);
		String text = ""+port;
		for (String user : userAndMdp.keySet()) {
			text += "\n";
			text += "\n"+user;
			text += "\n"+userAndMdp.get(user);
		}
		out.write(text.getBytes("UTF8"));
		out.close();
	}
	
	private void loadParameter() throws IOException {
		if (!fileParameter.exists())
			saveParameter();
		Map<String, String> userAndMdp = new HashMap<String, String>();
		int port = 0;
		Scanner sc = new Scanner(fileParameter,"UTF8");
		port = Integer.parseInt(sc.nextLine());
		while (sc.hasNextLine()) {
			sc.nextLine();
			if (sc.hasNextLine()) {
				String user = sc.nextLine();
				if (sc.hasNextLine()) {
					String mdp = sc.nextLine();
					if (!userAndMdp.containsKey(user))
						userAndMdp.put(user, mdp);
					else
						System.err.println(getLogsTime()+"Error the user \""+user+"\" already exist !");
				} else {
					break;
				}
			}
		}
		sc.close();
		this.port = port;
		this.userAndMdp = userAndMdp;
	}
	
	public ProcessManagerServer() {
		this.port = 206;
		this.userAndMdp = new HashMap<String, String>();
		this.userAndMdp.put("admin", "password");
		fileParameter = new File("./parameter");
		try {
			loadParameter();
			this.server = new ServerSocket(port);
			System.out.println(getLogsTime()+"*---------------*");
			System.out.println(getLogsTime()+"port > "+port);
			System.out.println(getLogsTime()+"user-size > "+userAndMdp.size());
			System.out.println(getLogsTime()+"*---------------*");
			/*for (String user : userAndMdp.keySet()) {
				System.out.println(getLogsTime()+user+" > "+userAndMdp.get(user));
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
