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
import web.commands.CommandAdd;
import web.commands.CommandRemove;

public class command implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		try {
			if(receiveHTTP.getMethodeRequet().equalsIgnoreCase("POST")) {
				PageData data = null;
				if (receiveHTTP.hasData()) {
					data = new PageData(new String(receiveHTTP.getData()));
				}
				
				PrintData.printData(receiveHTTP, data);
				
				if (data != null && data.containsKey("token")) {
					Token token = auth.tokenValid(data.get("token"));
					if (token != null) {
						if (data.get("namecmd") != null) {
							switch (data.get("namecmd")) {
								case "add":
									new CommandAdd(data.get("name"), data.get("cmd"), data.get("folder"), data.get("launch"), data.get("cmdstop"));
									break;
									
								case "remove":
									new CommandRemove(data.get("name"));
									break;
									
								case "rename":
									new CommandRename(data.get("name"), data.get("newname"));
									break;
									
								default:
									break;
							}
						}
						if (data.containsKey("link"))
							return new PageRedirectSeeOther(data.get("link"));
						else
							return new PageRedirectSeeOther("/info.tweb?token="+token.getUUID());
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
