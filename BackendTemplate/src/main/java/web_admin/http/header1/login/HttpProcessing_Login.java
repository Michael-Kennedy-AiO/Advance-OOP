package web_admin.http.header1.login;

import java.util.HashMap;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.BGHttp_Post;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.AdminLoginManager;
import database_game.DatabaseManager;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.DocURL;
import web_admin.MyRespone;
import web_admin.StringConstant;

@Http(api = API.LoginScreen_Login)
@HttpDocument(id = 1000, Header = API.H1_Login, Description = "Mô tả ở đây", Link = DocURL.Figma)
public class HttpProcessing_Login extends BGHttp_Post {
	public static AdminLoginManager adminLoginManager;
	public static DatabaseManager databaseManager;
	
    public String username;
    public String password;

    @Override public Object onHttp(BackendSession session) {
    	if(isNullOrEmpty(username) || isNullOrEmpty(password))
    		return API.resInvalidParameters;
    	
    	HashMap<String, String> mapAccount = adminLoginManager.mapAccount;
    	if(mapAccount.containsKey(username)==false)
    		return API.resNotFound;
    	
    	String _pass = mapAccount.get(username);
    	if(isNullOrEmpty(_pass))
    		return API.resNotFound;
    	
    	if(_pass.equals(password)) {
    		AdminToken adminToken = new AdminToken();
			adminToken.username = username;
			adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
			
			
			HashMap<String, Object> dataRespone = new HashMap<>();
			dataRespone.put(StringConstant.AdminToken, adminToken.toHashString());
			dataRespone.put(StringConstant.listDatabase, databaseManager.readListDatabaseInfo());
    		return new MyRespone(MyRespone.STATUS_Success).setData(dataRespone);
    	}else
    		return API.resWrongPassword;
    }
    @Override public void test_api_in_document() {
    	username="admin";
    	password="admin";
    }
}
