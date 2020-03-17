package web;

import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.util.PageData;

public class PrintData {

	private PrintData() {}
	
	public static void printData(ReceiveHTTP receiveHTTP, PageData data) {
		System.out.println(receiveHTTP.getHeadRequet("Content-Type"));
		if (data != null) {
			for (String key : data.keySet()) {
				System.out.println("Key: "+key+" Value: "+data.get(key));
			}
		} else {
			System.out.println("No data !");
		}
		System.out.println(receiveHTTP.getMethodeRequet());
		System.out.println(receiveHTTP.getPathRequet().getPath());
		System.out.println(receiveHTTP.getPathRequet().hasData());
		System.out.println(receiveHTTP.getPathRequet().getData());
		System.out.println(receiveHTTP.hasData());
		System.out.println(new String(receiveHTTP.getData()));
		System.out.println("---------------");
	}
	
}
