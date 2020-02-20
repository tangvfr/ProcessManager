package web;

import java.util.Map;

import fr.tangv.processmanager.Main;
import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageData;
import fr.tangv.web.util.PageRedirect;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class index implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		if (receiveHTTP.getMethodeRequet().equalsIgnoreCase("GET") || receiveHTTP.getMethodeRequet().equalsIgnoreCase("POST")) {
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

			if (data != null && data.containsKey("user") && data.containsKey("pass")) {
				String user = data.get("user");
				String pass = data.get("pass");
				Map<String, String> auth = Main.processManagerServer.getUserAndMdp();
				if (auth.containsKey(user) && auth.get(user).equals(pass)) {
					
					return new PageRedirect("/info.tweb?token=");
				} else {
					return new PageRedirect("/invalide.html");
				}
			}
			String page = pageResoucre.get(0);
			return new Page(page, PageType.HTML, CodeHTTP.CODE_200_OK);
		} else {
			return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_405_METHOD_NOT_ALLOWED);
		}
	}

}
