package fr.tangv.web.util;

import java.util.HashMap;

public class PageRedirectSeeOther extends Page {

	public PageRedirectSeeOther(String location) {
		super(new byte[0], PageType.OTHER, CodeHTTP.CODE_303_SEE_OTHER);
		this.header = new HashMap<String, String>();
		this.header.put("Location", location);
	}
	
}
