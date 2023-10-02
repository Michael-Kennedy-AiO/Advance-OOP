package web_admin.http;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.BGHttp_Post;
import backendgame.com.database.table.DBString;
import database_game.DatabaseManager;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.MyRespone;

public abstract class BaseHttpAdminWeb_SignedIn extends BGHttp_Post {
	public static DBString dbString;
	public static TimeManager timeManager;
	public static DatabaseManager managingDatabase;
	public String AdminToken;
	
	public abstract MyRespone onPOST(BackendSession session);

	@Override public Object onHttp(BackendSession session) {
		if(isNullOrEmpty(AdminToken))
			return API.resTokenNotExist;
		AdminToken adminToken = new AdminToken();
		if(adminToken.decode(AdminToken, false)==false)
			return API.resTokenError;
		
		if(currentTimeMillis>adminToken.timeOut)
			return API.resTokenTimeout;
		
		return onPOST(session);
	}
	
	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
	}
}
