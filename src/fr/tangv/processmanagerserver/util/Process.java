package fr.tangv.processmanagerserver.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.sun.istack.internal.NotNull;

public class Process {

	private ProcessBuilder processBuilder;
	private java.lang.Process process;
	private Charset encoding;
	
	public Process(@NotNull String cmd, String rep, @NotNull Charset encoding) {
		this.processBuilder = new ProcessBuilder(cmd.split(" "));
		this.encoding = encoding;
		if (rep != null && !rep.isEmpty())
			this.processBuilder.directory(new File(rep));
		this.process = null;
	}
	
	public Process(@NotNull String cmd, @NotNull Charset encoding) {
		this(cmd, null, encoding);
	}
	
	public Process(@NotNull String cmd) {
		this(cmd, null, StandardCharsets.UTF_8);
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
	
	public String getConsole() throws IOException {
		if (isStart()) {
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
			return null;
		}
	}
	
	public void resetInputStream() throws IOException {
		if (isStart()) {
			process.getInputStream().reset();
		}
	}
	
}
