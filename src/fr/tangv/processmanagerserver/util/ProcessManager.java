package fr.tangv.processmanagerserver.util;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class ProcessManager {

	private Vector<ProcessPlus> listProcess;
	private File folder;
	
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
				out.writeUTF(process.getRep());
				out.writeUTF(process.getEncoding());
				out.writeBoolean(process.isActiveOnStart());
				out.close();
				return true;
			}
		}
		return false;
	}
	
	public boolean loadProcces(String name) throws IOException {
		for(File file : folder.listFiles()) {
			System.err.println(file.getName());
			if (file.getName().equals(name)) {
				
			}
		}
		return false;
	}
	
	public ProcessManager() {
		listProcess = new Vector<ProcessPlus>();
		folder = new File("./process");
		if (!folder.exists()) folder.mkdirs();
		
		
	}
	
}
