package web;

import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageData;
import fr.tangv.web.util.PageRedirectSeeOther;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class command implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		try {
			if(receiveHTTP.getMethodeRequet().equalsIgnoreCase("POST")) {
				PageData data = null;
				if (receiveHTTP.getPathRequet().hasData()) {
					data = new PageData(receiveHTTP.getPathRequet().getData());
				}
				
				PrintData.printData(receiveHTTP, data);
				
				if (data != null && data.containsKey("token")) {
					Token token = auth.tokenValid(data.get("token"));
					if (token != null) {
						// add redirect to link in data
						return new PageRedirectSeeOther("");
					}
				}
				return new PageRedirectSeeOther("/invalide.html");
			} else {
				return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_405_METHOD_NOT_ALLOWED);
			}
		} catch (Exception e) {
			return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_500_INTERNAL_SERVER_ERROR);
		}
	}

}
