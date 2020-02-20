package web;

import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class touch implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		String text = pageResoucre.get(0);
		return new Page(text, PageType.HTML, CodeHTTP.CODE_200_OK);
	}

}
