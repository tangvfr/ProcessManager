package web;

import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class index implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		String page = pageResoucre.get(0);
		
		System.out.println(receiveHTTP.getMethodeRequet());
		System.out.println(receiveHTTP.getPathRequet().getPath());
		System.out.println(receiveHTTP.getPathRequet().hasData());
		System.out.println(receiveHTTP.getPathRequet().getData());
		System.out.println(receiveHTTP.hasData());
		System.out.println(new String(receiveHTTP.getData()));
		System.out.println("---------------");
		
		return new Page(page, PageType.HTML, CodeHTTP.CODE_200_OK);
	}

}
