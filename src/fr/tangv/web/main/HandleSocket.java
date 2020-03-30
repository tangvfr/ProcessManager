package fr.tangv.web.main;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import fr.tangv.web.util.ByteArray;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class HandleSocket {

	private Web web;
	
	public Web getWeb() {
		return web;
	}
	
	public PageType getContentType(String nameFile) {
		if (nameFile.endsWith(".html") || nameFile.endsWith(".htm"))
			return PageType.HTML;
		else if (nameFile.endsWith(".css"))
			return PageType.CSS;
		else if (nameFile.endsWith(".js"))
			return PageType.JS;
		else
			return PageType.OTHER;
	}
	
	public void sendHTTP404(Socket socket) throws IOException {
		new SendHTTP(socket, CodeHTTP.CODE_404_NOT_FOUND, new byte[0], PageType.OTHER);
	}
	
	public HandleSocket(Socket socket, Web web) throws IOException {
		this.web = web;
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ReceiveHTTP receiveHTTP = new ReceiveHTTP(socket);
					if (web.hasLogger())
						web.getLogger().info(receiveHTTP.getIp()+" > "+receiveHTTP.getMethodeRequet()+" > "+receiveHTTP.getPathRequet());
					else
						System.out.println(receiveHTTP.getIp()+" > "+receiveHTTP.getMethodeRequet()+" > "+receiveHTTP.getPathRequet());
					if (receiveHTTP.getMethodeRequet() != null && receiveHTTP.isValid()) {
						String pathStick = receiveHTTP.getPathRequet().getPath();
						String extension = receiveHTTP.getPathRequet().getExtension();
						if (pathStick.isEmpty() || pathStick.equals("/")) {
							if (ClassLoader.getSystemResource(web.getPathResource()+"/index.class") != null) {
								pathStick = "/index.tweb";
								extension = ".tweb";
							} else if (ClassLoader.getSystemResource(web.getPathResource()+"/index.html") != null) {
								pathStick = "/index.html";
								extension = ".html";
							}
						}
						if (extension.equals(".tweb")) {
							String pathNE = (web.getPathResource()+pathStick.substring(0, pathStick.length()-5)).replace("/", ".");
							if (!pathNE.equals(web.getPathResource()+".")) {
								try {
									Class<? extends ClassPage> cl = Class.forName(pathNE).asSubclass(ClassPage.class);
									Page page = cl.newInstance().getPage(web, receiveHTTP, new PageResoucre(pathNE.replace(".", "/")+".rweb", "targ"));
									if (page != null)
										new SendHTTP(socket, page.getCode(), page.getData(), page.getType(), page.getHeader());
									else
										new SendHTTP(socket, CodeHTTP.CODE_500_INTERNAL_SERVER_ERROR, new byte[0], PageType.OTHER);
								} catch (Exception e) {
									sendHTTP404(socket);
								}
							} else {
								sendHTTP404(socket);
							}
						} else if (extension.equals(".class") || extension.equals(".rweb")) {
							new SendHTTP(socket, CodeHTTP.CODE_403_FORBIDDEN, new byte[0], PageType.OTHER);
						} else {
							String path = web.getPathResource()+pathStick;
							URL url = ClassLoader.getSystemResource(path);
							if (url != null) {
								URLConnection connection = url.openConnection();
								byte[] data = new ByteArray(connection.getInputStream(), connection.getContentLength()).bytes();
								new SendHTTP(socket, CodeHTTP.CODE_200_OK, data, getContentType(path));
							} else {
								sendHTTP404(socket);
							}
						}
					} else {
						new SendHTTP(socket, CodeHTTP.CODE_400_BAD_REQUEST, new byte[0], PageType.OTHER);
					}
					socket.close();
				} catch (IOException e) {
					if (web.hasLogger())
						web.getLogger().warning(e.getMessage());
					else
						e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
}
