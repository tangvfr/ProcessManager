package fr.tangv.processmanager.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.sun.istack.internal.NotNull;

public class Process {

	private ProcessBuilder processBuilder;
	private java.lang.Process process;
	private String encoding;
	
	public Process(@NotNull String cmd, String rep, @NotNull String encoding) {
		this.processBuilder = new ProcessBuilder(cmd.split(" "));
		this.encoding = encoding;
		if (rep != null && !rep.isEmpty())
			this.processBuilder.directory(new File(rep));
		this.process = null;
	}
	
	public Process(@NotNull String cmd, @NotNull String encoding) {
		this(cmd, null, encoding);
	}
	
	public Process(@NotNull String cmd) {
		this(cmd, null, "UTF8");
	}
	
	public Process(@NotNull String[] cmd, String rep, @NotNull String encoding) {
		this.processBuilder = new ProcessBuilder(cmd);
		this.encoding = encoding;
		if (rep != null && !rep.isEmpty())
			this.processBuilder.directory(new File(rep));
		this.process = null;
	}
	
	public Process(@NotNull String[] cmd, @NotNull String encoding) {
		this(cmd, null, encoding);
	}
	
	public Process(@NotNull String[] cmd) {
		this(cmd, null, "UTF8");
	}
	
	public void start() throws IOException {
		if (!isStart()) {
			process = processBuilder.start();
		}
	}
	
	public void stop() {
		if (isStart()) {
			process.destroy();
		}
	}
	
	public boolean isStart() {
		if (process == null) return false;
		return process.isAlive();
	}
	
	public boolean send(String string) throws IOException {
		if (isStart()) {
			process.getOutputStream().write((string+'\n').getBytes(encoding));
			process.getOutputStream().flush();
			return true;
		}
		return false;
	}
	
	public String getInput() throws IOException {
		if (process != null) {
			ByteArrayOutputStream outl = new ByteArrayOutputStream();
			InputStream in = process.getInputStream();
			if (in.available() > 0) {
				byte[] buffer = new byte[1024];
				while (in.available() > 0) {
					outl.write(buffer, 0, in.read(buffer));
				}
				outl.flush();
				outl.close();
				String text = new String(outl.toByteArray(), encoding);
				return text;
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	public String getError() throws IOException {
		if (process != null) {
			ByteArrayOutputStream outl = new ByteArrayOutputStream();
			InputStream in = process.getErrorStream();
			if (in.available() > 0) {
				byte[] buffer = new byte[1024];
				while (in.available() > 0) {
					outl.write(buffer, 0, in.read(buffer));
				}
				outl.flush();
				outl.close();
				String text = new String(outl.toByteArray(), encoding);
				return text;
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	public void resetInput() throws IOException {
		if (isStart()) {
			process.getInputStream().reset();
		}
	}
	
	public void resetError() throws IOException {
		if (isStart()) {
			process.getErrorStream().reset();
		}
	}
	
}