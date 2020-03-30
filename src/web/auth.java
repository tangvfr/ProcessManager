package web;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

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

public class auth implements ClassPage {

	private static volatile Vector<Token> TOKENS;
	private static volatile ConcurrentHashMap<String, Client> CLIENTS;
	
	static {
		TOKENS = new Vector<Token>();
		CLIENTS = new ConcurrentHashMap<String, Client>();
	}
	
	@NotNull
	public Client getClient(String ip) {
		if (CLIENTS.containsKey(ip)) {
			return CLIENTS.get(ip);
		} else {
			Client client = new Client(ip);
			CLIENTS.put(ip, client);
			return client;
		}
	}
	
	public static void removeToken(Token token) {
		TOKENS.remove(token);
	}
	
	private static void clear() {
		long timeCo = 5*60*1000;
		long dateMax = System.currentTimeMillis()-timeCo;
		for (int i = 0; i < TOKENS.size(); i++) {//---a optimisé
			if (TOKENS.get(i).getDate() < dateMax) {
				TOKENS.remove(TOKENS.get(i));
				i--;
			}
		}
	}
	
	@Nullable
	public static Token tokenValid(String token) {
		clear();
		for (Token tok : TOKENS) {
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
		TOKENS.add(token);
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
				Map<String, String> auth = ProcessManager.PROCESS_MANAGER_SERVER.getUserAndMdp();
				Client client = getClient(receiveHTTP.getIp());
				if (client.itCan()) {
					if (auth.containsKey(user) && auth.get(user).equals(pass)) {
						client.resetTry();
						Token token = newToken(user);
						return new PageRedirectSeeOther("/info.tweb?token="+token.getUUID());
					} else {
						client.addTry();
						return new PageRedirectSeeOther("/invalidepwd.html");
					}
				}
			}
			return new PageRedirectSeeOther("/");
		} else {
			return new Page(new byte[0], PageType.OTHER, CodeHTTP.CODE_405_METHOD_NOT_ALLOWED);
		}
	}

}
