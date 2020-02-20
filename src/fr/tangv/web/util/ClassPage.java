package fr.tangv.web.util;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;

public interface ClassPage {

	@NotNull
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, @Nullable PageResoucre pageResoucre);
	
}
