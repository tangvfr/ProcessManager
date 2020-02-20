package fr.tangv.web.util;

public class PathHTTP {

	private String path;
	private String extension;
	private String data;
	private boolean hasData;
	
	public PathHTTP(String pathHTTP) {
		if (pathHTTP.contains("?")) {
			path = pathHTTP.substring(0,
					pathHTTP.lastIndexOf("?"));
			data = pathHTTP.substring(pathHTTP.lastIndexOf("?")+1, 
					pathHTTP.length());
		} else {
			path = pathHTTP;
			data = "";
		}
		hasData = !data.isEmpty();
		if (path.contains(".")) {
			extension = path.substring(path.lastIndexOf("."), path.length());
		} else {
			extension = "";
		}
	}
	
	public String getPath() {
		return path;
	}
	
	public String getData() {
		return data;
	}
	
	public boolean hasData() {
		return hasData;
	}
	
	public String getExtension() {
		return extension;
	}
	
	@Override
	public String toString() {
		return path;
	}
	
}
