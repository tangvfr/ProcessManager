package fr.tangv.web.util;

import java.util.HashMap;

public class PageRedirectFound extends Page {

	public PageRedirectFound(String location) {
		super(new byte[0], PageType.OTHER, CodeHTTP.CODE_302_FOUND);
		this.header = new HashMap<String, String>();
		this.header.put("Location", location);
	}
	
}
