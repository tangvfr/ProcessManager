package fr.tangv.web.util;

public enum PageType {

	HTML("text/html"),
	CSS("text/css"),
	JS("text/javascript"),
	OTHER("text/plain");
	
	private String type;
	
	private PageType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
	
}
