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
import fr.tangv.web.util.PageRedirectSeeOther;
import fr.tangv.web.util.PageResoucre;
import fr.tangv.web.util.PageType;

public class auth implements ClassPage {

	private static volatile Vector<Token> tokens;
	
	static {
		tokens = new Vector<Token>();
	}
	
	private static void clear() {
		long timeCo = 5*60*1000;
		long dateMax = System.currentTimeMillis()-timeCo;
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).getDate() < dateMax) {
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
		if (receiveHTTP.getMethodeRequet().equalsIgnoreCase("POST")) {
			PageData data = null;
			if (receiveHTTP.hasData()) {
				data = new PageData(new String(receiveHTTP.getData()));
			}
			
			if (data != null && data.containsKey("user") && data.containsKey("pass")) {
				String user = data.get("user");
				String pass = data.get("pass");
				Map<String, String> auth = Main.processManagerServer.getUserAndMdp();
				if (auth.containsKey(user) && auth.get(user).equals(pass)) {
					Token token = newToken(user);
					return new PageRedirectSeeOther("/info.tweb?token="+token.getUUID());
				} else {
					return new PageRedirectSeeOther("/invalide.html");
				}
			} else {
				return new PageRedirectSeeOther("/");
			}
		} else {
			return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_405_METHOD_NOT_ALLOWED);
		}
	}

}
