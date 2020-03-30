package web;

import fr.tangv.processmanager.ProcessManager;
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
import web.commands.CommandCmdAll;
import web.commands.CommandCmdEnd;
import web.commands.CommandFolder;
import web.commands.CommandFolderAll;
import web.commands.CommandLaunch;
import web.commands.CommandLaunchAll;
import web.commands.CommandReadConsole;
import web.commands.CommandReadError;
import web.commands.CommandRemove;
import web.commands.CommandRemoveAll;
import web.commands.CommandRename;
import web.commands.CommandRestart;
import web.commands.CommandRestartAll;
import web.commands.CommandRestartWC;
import web.commands.CommandRestartWCAll;
import web.commands.CommandSendConsole;
import web.commands.CommandStart;
import web.commands.CommandStartAll;
import web.commands.CommandStop;
import web.commands.CommandStopAll;
import web.commands.CommandStopCmd;
import web.commands.CommandStopCmdAll;
import web.commands.CommandStopWC;
import web.commands.CommandStopWCAll;
import web.commands.CommandTimeRestart;

public class command implements ClassPage {

	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		try {
			if(receiveHTTP.getMethodeRequet().equalsIgnoreCase("POST")) {
				PageData data = null;
				if (receiveHTTP.hasData()) {
					data = new PageData(new String(receiveHTTP.getData()));
				}
				
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
									
								case "stopforcenoscript":
									ProcessManager.PROCESS_MANAGER_SERVER.stop();
									break;
										
								case "stopforcewithscript":
									ProcessManager.PROCESS_MANAGER_SERVER.stopScript();
									break;
									
								case "stopnoforcenoscript":
									ProcessManager.PROCESS_MANAGER_SERVER.stopNoForce(false);
									break;
									
								case "stopnoforcewithscript":
									ProcessManager.PROCESS_MANAGER_SERVER.stopNoForce(true);
									break;
									
								case "removeall":
									new CommandRemoveAll();
									break;
									
								case "launchall":
									new CommandLaunchAll(data.get("launch"));
									break;

								case "folderall":
									new CommandFolderAll(data.get("newfolder"));
									break;

								case "cmdall":
									new CommandCmdAll(data.get("newcmd"));
									break;

								case "stopcmdall":
									new CommandStopCmdAll(data.get("newstopcmd"));
									break;

								case "stopall":
									new CommandStopAll();
									break;

								case "restartall":
									new CommandRestartAll();
									break;

								case "startall":
									new CommandStartAll();
									break;

								case "stopwcall":
									new CommandStopWCAll();
									break;

								case "restartwcall":
									new CommandRestartWCAll();
									break;
									
								case "readconsole":
									CommandReadConsole commandReadConsole = new CommandReadConsole(data.get("name"), Integer.parseInt(data.get("lines")));
									return new Page(commandReadConsole.console.getBytes("UTF8"), PageType.OTHER, CodeHTTP.CODE_200_OK);
									
								case "readerror":
									CommandReadError commandReadError = new CommandReadError(data.get("name"), Integer.parseInt(data.get("lines")));
									return new Page(commandReadError.error.getBytes("UTF8"), PageType.OTHER, CodeHTTP.CODE_200_OK);
									
								case "sendconsole":
									new CommandSendConsole(data.get("name"), data.get("command"));
									return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_200_OK);
									
								case "disconnect": 
									auth.removeToken(token);
									return new PageRedirectSeeOther("/");
									
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
