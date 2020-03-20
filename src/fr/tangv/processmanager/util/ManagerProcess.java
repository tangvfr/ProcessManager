package fr.tangv.processmanager.util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import fr.tangv.processmanager.ProcessManagerServer;

public class ManagerProcess {

	private volatile Vector<ProcessPlus> listProcess;
	private File folder;
	
	public Vector<ProcessPlus> getListProcess() {
		return listProcess;
	}
	
	public boolean removeProcess(String name) throws IOException {
		for (ProcessPlus process : listProcess) {
			if (process.getName().equals(name)) {
				File file  = new File("./process/"+name);
				if (!file.delete()) return false;
				process.getProcess().stop();
				listProcess.remove(process);
				return true;
			}
		}
		return false;
	}
	
	public boolean addProcess(ProcessPlus process) throws IOException {
		if (!hasProcess(process.getName())) {
			listProcess.add(process);
			saveProcces(process.getName());
			return true;
		}
		return false;
	}
	
	public boolean renameProcess(String name, String newName) throws IOException {
		for (ProcessPlus process : listProcess) {
			if (process.getName().equals(name)) {
				File file  = new File("./process/"+name);
				if (!file.delete()) return false;
				process.setName(newName);
				saveProcces(newName);
				return true;
			}
		}
		return false;
	}
	
	public boolean hasProcess(String name) {
		for (ProcessPlus process : listProcess) {
			if (process.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public ProcessPlus getProcess(String name) {
		for (ProcessPlus process : listProcess) {
			if (process.getName().equals(name)) {
				return process;
			}
		}
		return null;
	}
	
	public boolean saveProcces(String name) throws IOException {
		for (ProcessPlus process : listProcess) {
			if (process.getName().equals(name)) {
				File file  = new File("./process/"+process.getName());
				if (!file.exists()) file.createNewFile();
				DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
				out.writeUTF(process.getName());
				out.writeUTF(process.getCmd());
				out.writeUTF(process.getFolder());
				out.writeUTF(process.getEncoding());
				out.writeUTF(""+process.isActiveOnStart());
				out.writeUTF(process.getCmdStop());
				out.close();
				return true;
			}
		}
		return false;
	}
	
	public boolean loadProcces(String name) throws IOException {
		for(File file : folder.listFiles()) {
			if (file.isFile() && file.getName().equals(name)) {
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				String nameP = in.readUTF();
				String cmd = in.readUTF();
				String rep = in.readUTF();
				String encoding = in.readUTF();
				boolean active = Boolean.parseBoolean(in.readUTF());
				String cmdStop = in.available() > 0 ? in.readUTF() : "";
				in.close();
				if (!hasProcess(name)) {
					ProcessPlus process = new ProcessPlus(nameP, cmd, rep, encoding, active, cmdStop);
					listProcess.add(process);
				} else {
					ProcessPlus process = getProcess(name);
					process.setCmd(cmd);
					process.setFolder(rep);
					process.setEncoding(encoding);
					process.setActiveOnStart(active);
				}
				return true;
			}
		}
		return false;
	}
	
	public void loadProccesAll() throws IOException {
		for(File file : folder.listFiles()) {
			if (file.isFile()) {
				String name = file.getName();
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				String nameP = in.readUTF();
				String cmd = in.readUTF();
				String rep = in.readUTF();
				String encoding = in.readUTF();
				boolean active = Boolean.parseBoolean(in.readUTF());
				String cmdStop = in.available() > 0 ? in.readUTF() : "";
				in.close();
				if (!hasProcess(name)) {
					ProcessPlus process = new ProcessPlus(nameP, cmd, rep, encoding, active, cmdStop);
					listProcess.add(process);
				} else {
					ProcessPlus process = getProcess(name);
					process.setCmd(cmd);
					process.setFolder(rep);
					process.setEncoding(encoding);
					process.setActiveOnStart(active);
				}
			}
		}
	}
	
	public ManagerProcess() throws IOException {
		listProcess = new Vector<ProcessPlus>();
		folder = new File("./process");
		if (!folder.exists()) folder.mkdirs();
		loadProccesAll();
		for (ProcessPlus process : listProcess) {
			if (process.isActiveOnStart()) {
				try {
					process.getProcess().start();
				} catch (Exception e) {
					ProcessManagerServer.logger.warning(e.getLocalizedMessage());
				}
			}
		}
	}
	
}
