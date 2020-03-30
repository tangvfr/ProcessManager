package fr.tangv.web.main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import com.sun.istack.internal.Nullable;

import fr.tangv.processmanager.ProcessManager;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.PageType;

public class SendHTTP {
	
	public SendHTTP(Socket socket, CodeHTTP codeHTTP, byte[] data, PageType type) throws IOException {
		this(socket, codeHTTP, data, type, null);
	}
	
	public SendHTTP(Socket socket, CodeHTTP codeHTTP, byte[] data, PageType type, @Nullable Map<String, String> header) throws IOException {
		OutputStream out = socket.getOutputStream();
		String head = "HTTP/1.1 "+codeHTTP+"\r\n"+
				"Date: "+new Date()+"\r\n"+
				"Server: ProcessManager_"+ProcessManager.VERSION+"\r\n"+
				"Content-Length: "+data.length+"\r\n"+
				"Content-Type: "+type+"\r\n";
		if (header != null)
			for (String key : header.keySet()) {
				head += key+": "+header.get(key)+"\r\n";
			}
		head += "\r\n";
		out.write(head.getBytes());
		out.flush();
		out.write(data, 0, data.length);
		out.flush();
	}
	
}
