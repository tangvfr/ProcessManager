package web;

import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageData;
import fr.tangv.web.util.PageRedirect;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class info implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		if (receiveHTTP.getMethodeRequet().equalsIgnoreCase("GET") || receiveHTTP.getMethodeRequet().equalsIgnoreCase("POST")) {
			PageData data = null;
			if (receiveHTTP.hasData()) {
				data = new PageData(new String(receiveHTTP.getData()));
			} else if (receiveHTTP.getPathRequet().hasData()) {
				data = new PageData(receiveHTTP.getPathRequet().getData());
			}
			
			//PrintData.printData(receiveHTTP, data);
			
			if (data != null && data.containsKey("token")) {
				Token token = index.tokenValid(data.get("token"));
				if (token != null) {
					return new Page(pageResoucre.get(0), PageType.HTML, CodeHTTP.CODE_200_OK);
				}
			}
			return new PageRedirect("/invalide.html");
		} else {
			return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_405_METHOD_NOT_ALLOWED);
		}
	}

}
