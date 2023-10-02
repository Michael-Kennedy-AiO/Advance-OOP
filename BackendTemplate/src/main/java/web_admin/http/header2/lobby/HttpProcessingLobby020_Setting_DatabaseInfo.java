package web_admin.http.header2.lobby;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.Lobby_Setting_Database)
@HttpDocument(id = 2020, Header = API.H2_Lobby)
public class HttpProcessingLobby020_Setting_DatabaseInfo extends BaseHttpAdminWeb_SignedIn {

	public short DBId;
	public String DatabaseName;
	public String Description;
	public int ViewId;
	
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		
		managingDatabase.updateDatabaseInfo(DBId,ViewId,DatabaseName,Description);
		return API.resSuccess;
	}
	
	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		
		DBId = 1;
		DatabaseName = "Database with new name";
		Description = "new description";
		ViewId = 999;
	}
}
