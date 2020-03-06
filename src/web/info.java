package web;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import fr.tangv.processmanager.Main;
import fr.tangv.processmanager.util.ProcessManager;
import fr.tangv.processmanager.util.ProcessPlus;
import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageData;
import fr.tangv.web.util.PageRedirectSeeOther;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class info implements ClassPage {

	private String decodingUTF8(String text) throws UnsupportedEncodingException {
		return new String(text.getBytes("UTF8"));
	}
	
	private void sortListProcess(Vector<ProcessPlus> listProcess, String nameSort) {
		listProcess.sort(new Comparator<ProcessPlus>() {
			@Override
			public int compare(ProcessPlus p1, ProcessPlus p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
		switch (nameSort) {
			case "name":
				break;
	
			case "foldername":
				listProcess.sort(new Comparator<ProcessPlus>() {
					@Override
					public int compare(ProcessPlus p1, ProcessPlus p2) {
						return p1.getRep().compareTo(p2.getRep());
					}
				});
				break;
	
			case "folder":
				listProcess.sort(new Comparator<ProcessPlus>() {
					@Override
					public int compare(ProcessPlus p1, ProcessPlus p2) {
						return p1.getRep().compareTo(p2.getRep());
					}
				});
				break;
	
			case "started":
				listProcess.sort(new Comparator<ProcessPlus>() {
					@Override
					public int compare(ProcessPlus p1, ProcessPlus p2) {
						return ((Boolean) p1.getProcess().isStart()).compareTo(p2.getProcess().isStart());
					}
				});
				break;
	
			case "launch":
				listProcess.sort(new Comparator<ProcessPlus>() {
					@Override
					public int compare(ProcessPlus p1, ProcessPlus p2) {
						return ((Boolean) p1.isActiveOnStart()).compareTo(p2.isActiveOnStart());
					}
				});
				break;
	
			default:
				break;
		}
	}
	
	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		try {
			if (receiveHTTP.getMethodeRequet().equalsIgnoreCase("GET")) {
				PageData data = null;
				if (receiveHTTP.getPathRequet().hasData()) {
					data = new PageData(receiveHTTP.getPathRequet().getData());
				}
				
				PrintData.printData(receiveHTTP, data);
				
				if (data != null && data.containsKey("token")) {
					Token token = auth.tokenValid(data.get("token"));
					if (token != null) {
						Map<String, String> remplaceValue = new HashMap<String, String>();
						//maj
						String textMaj = Main.getUpdate(true);
						PageResoucre baliseMaj = new PageResoucre(pageResoucre.getContent("update"), "barg", false);
						Map<String, String> mapMaj = new HashMap<String, String>();
						mapMaj.put("text", textMaj == "ProcessManager est à jour !" ? "" : textMaj);
						textMaj = baliseMaj.remplaceText(mapMaj);
						remplaceValue.put("update", decodingUTF8(textMaj));
						//process
						PageResoucre baliseProcessBox = new PageResoucre(pageResoucre.getContent("processbox"), "barg", false);
						PageResoucre baliseProcessMenu = new PageResoucre(pageResoucre.getContent("processmenu"), "barg", false);
						//general
						remplaceValue.put("version", decodingUTF8(Main.version));
						remplaceValue.put("token", token.toString());
						remplaceValue.put("menuopen", data.get("menu").isEmpty() ? "false" : data.get("menu"));
						remplaceValue.put("search", data.get("search"));
						remplaceValue.put("sort"+data.get("sort"), "selected");
						int page = data.get("page").isEmpty() ? 1 : Integer.parseInt(data.get("page"));
						int maxpage = 0;
						switch (data.get("button")) {
							case "next":
								page++;
								break;
							case "previous":
								page--;
								break;
							default:
								break;
						}
						//list process
						String textProcessBox = "";
						String textProcessMenu = "";
						ProcessManager processManager = Main.processManagerServer.getProcessManager();
						remplaceValue.put("processnumber", ""+processManager.getListProcess().size());
						int processByPage = 4;
						maxpage = (processManager.getListProcess().size()/processByPage)+1;
						//end calc page
						if (page < 1) {
							page = maxpage;
						} else if (page > maxpage) {
							page = 1;
						}
						remplaceValue.put("page", ""+page);
						remplaceValue.put("maxpage", ""+maxpage);
						//filtre
						@SuppressWarnings("unchecked")
						Vector<ProcessPlus> listProcess = (Vector<ProcessPlus>) processManager.getListProcess().clone();
						sortListProcess(listProcess, data.get("sort"));
						//implement folder sort
						//calc process
						for (int i = 0; i < listProcess.size(); i++) {
							ProcessPlus process = listProcess.get(i);
							Map<String, String> value = new HashMap<String, String>();
							String[] startedBalise = baliseProcessMenu.getContent("started").split(",");
							value.put("started", process.getProcess().isStart() ? startedBalise[0] : startedBalise[1]);
							String[] launchBalise = baliseProcessMenu.getContent("launch").split(",");
							value.put("launch", process.isActiveOnStart() ? launchBalise[0] : launchBalise[1]);
							value.put("name", process.getName());
							value.put("cmd", process.getCmd());
							value.put("stopcmd", process.getCmdStop());
							value.put("folder", process.getRep());
							value.put("maxpage", ""+maxpage);
							int thisPage = (i/processByPage)+1;
							value.put("page", ""+thisPage);
							textProcessMenu += baliseProcessMenu.remplaceText(value);
							if (thisPage == page) {
								startedBalise = baliseProcessBox.getContent("started").split(",");
								value.replace("started", process.getProcess().isStart() ? startedBalise[0] : startedBalise[1]);
								launchBalise = baliseProcessBox.getContent("launch").split(",");
								value.replace("launch", process.isActiveOnStart() ? launchBalise[0] : launchBalise[1]);
								textProcessBox += baliseProcessBox.remplaceText(value);
							}
						}
						remplaceValue.put("processbox", textProcessBox);
						remplaceValue.put("processmenu", textProcessMenu);
						//return
						return new Page(pageResoucre.remplaceText(remplaceValue), PageType.HTML, CodeHTTP.CODE_200_OK);
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
