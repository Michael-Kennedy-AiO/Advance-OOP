package web_admin.http.header3.Table_a1.AccountConfig;

import java.io.IOException;
import java.util.HashMap;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;

@Http(api = API.TableAccount_Config_MailService)
@HttpDocument(id = 3159, Header = API.H3a1_TableAccount)
public class HttpAccountLogin159_Config_MailService extends BaseDatabaseAccount {
	public HashMap<String, String> listEmail;

	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		databaseAccount.writeMailService(listEmail);
		return API.resSuccess;
	}
	
	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		
		DBId=1;
		
		listEmail=new HashMap<>();
		listEmail.put("abc@gmail.com", "123456");
		listEmail.put("xyz@gmail.com", "123456");
	}
}
