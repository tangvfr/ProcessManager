package web;

import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageRedirect;
import fr.tangv.web.util.PageResoucre;

public class test implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		return new PageRedirect("/index.tweb");
	}

}
