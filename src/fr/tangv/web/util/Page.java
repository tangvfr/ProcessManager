package fr.tangv.web.util;

import java.util.Map;

public class Page {

	protected byte[] data;
	protected PageType type;
	protected CodeHTTP code;
	protected Map<String, String> header;
	
	public Page(byte[] data, PageType type, CodeHTTP code, Map<String, String> header) {
		this.data = data;
		this.type = type;
		this.code = code;
		this.header = header;
	}
	
	public Page(byte[] data, PageType type, CodeHTTP code) {
		this(data, type, code, null);
	}
	
	public Page(String data, PageType type, CodeHTTP code, Map<String, String> header) {
		this(data.getBytes(), type, code, header);
	}
	
	public Page(String data, PageType type, CodeHTTP code) {
		this(data.getBytes(), type, code, null);
	}
	
	public byte[] getData() {
		return data;
	}
	
	public PageType getType() {
		return type;
	}
	
	public CodeHTTP getCode() {
		return code;
	}
	
	public Map<String, String> getHeader() {
		return header;
	}
	
}
