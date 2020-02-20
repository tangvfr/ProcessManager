package web;

import java.util.Map;
import java.util.Vector;

import com.sun.istack.internal.Nullable;

import fr.tangv.processmanager.Main;
import fr.tangv.web.main.ReceiveHTTP;
import fr.tangv.web.main.Web;
import fr.tangv.web.util.ClassPage;
import fr.tangv.web.util.CodeHTTP;
import fr.tangv.web.util.Page;
import fr.tangv.web.util.PageData;
import fr.tangv.web.util.PageRedirect;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class index implements ClassPage {

	private static volatile Vector<Token> tokens;
	
	static {
		tokens = new Vector<Token>();
	}
	
	private static void clear() {
		long timeCo = 5*60*1000;
		long dateMax = System.currentTimeMillis()+timeCo;
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).getDate() > dateMax) {
				tokens.remove(tokens.get(i));
				i--;
			}
		}
	}
	
	@Nullable
	public static Token tokenValid(String token) {
		clear();
		for (Token tok : tokens) {
			if (tok.getUUID().toString().equals(token)) {
				tok.updateDate();
				return tok;
			}
		}
		return null;
	}
	
	public static Token newToken(String user) {
		clear();
		Token token = new Token(user);
		tokens.add(token);
		return token;
	}
	
	@Override
	public Page getPage(Web web, ReceiveHTTP receiveHTTP, PageResoucre pageResoucre) {
		if (receiveHTTP.getMethodeRequet().equalsIgnoreCase("GET") || receiveHTTP.getMethodeRequet().equalsIgnoreCase("POST")) {
			PageData data = null;
			if (receiveHTTP.hasData()) {
				data = new PageData(new String(receiveHTTP.getData()));
			} else if (receiveHTTP.getPathRequet().hasData()) {
				data = new PageData(receiveHTTP.getPathRequet().getData());
			}
			if (data != null && data.containsKey("user") && data.containsKey("pass")) {
				String user = data.get("user");
				String pass = data.get("pass");
				Map<String, String> auth = Main.processManagerServer.getUserAndMdp();
				if (auth.containsKey(user) && auth.get(user).equals(pass)) {
					Token token = newToken(user);
					return new PageRedirect("/info.tweb?token="+token.getUUID().toString());
				} else {
					return new PageRedirect("/invalide.html");
				}
			}
			String page = pageResoucre.get(0);
			return new Page(page, PageType.HTML, CodeHTTP.CODE_200_OK);
		} else {
			return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_405_METHOD_NOT_ALLOWED);
		}
	}

}
