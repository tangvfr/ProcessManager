package web;

import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageData;
import fr.tangv.web.util.PageRedirect;
import fr.tangv.web.util.PageResoucre;

public class info implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		PageData data = null;
		if (receiveHTTP.hasData()) {
			data = new PageData(new String(receiveHTTP.getData()));
		} else if (receiveHTTP.getPathRequet().hasData()) {
			data = new PageData(receiveHTTP.getPathRequet().getData());
		}
		
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
		
		if (data != null && data.containsKey("token")) {
			String token = data.get("token");
			
		}
		return new PageRedirect("/invalide.html");
	}

}
