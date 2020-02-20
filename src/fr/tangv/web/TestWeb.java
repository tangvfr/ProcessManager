package fr.tangv.web;

import java.io.IOException;

import fr.tangv.web.main.Web;

public class TestWeb {

	public static void main(String[] args) {
		try {
			new Web(80, "web");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
