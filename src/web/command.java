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
import web.commands.CommandCmd;
import web.commands.CommandFolder;
import web.commands.CommandLaunch;
import web.commands.CommandRemove;
import web.commands.CommandRename;
import web.commands.CommandRestart;
import web.commands.CommandRestartWC;
import web.commands.CommandStart;
import web.commands.CommandStop;
import web.commands.CommandStopCmd;
import web.commands.CommandStopWC;

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
									
								case "launch":
									new CommandLaunch(data.get("name"), data.get("launch"));
									break;

								case "folder":
									new CommandFolder(data.get("name"), data.get("newfolder"));
									break;

								case "cmd":
									new CommandCmd(data.get("name"), data.get("newcmd"));
									break;

								case "stopcmd":
									new CommandStopCmd(data.get("name"), data.get("newstopcmd"));
									break;

								case "stop":
									new CommandStop(data.get("name"));
									break;

								case "restart":
									new CommandRestart(data.get("name"));
									break;

								case "start":
									new CommandStart(data.get("name"));
									break;

								case "stopwc":
									new CommandStopWC(data.get("name"));
									break;

								case "restartwc":
									new CommandRestartWC(data.get("name"));
									break;
									
								case "cmdend":
									new CommandCmdEnd(data.get("newcmdend"));
									break;
									
								case "timerestart":
									new CommandTimeRestart(data.get("time"), data.get("horaire"));
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
			return new Page(e.getMessage().getBytes(), PageType.OTHER, CodeHTTP.CODE_500_INTERNAL_SERVER_ERROR);
		}
	}

}
