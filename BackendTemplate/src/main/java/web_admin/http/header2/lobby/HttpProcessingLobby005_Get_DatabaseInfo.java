package web_admin.http.header2.lobby;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.DocURL;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.Lobby_Get_DatabaseInfo)
@HttpDocument(id = 2005, Header = API.H2_Lobby, Link = DocURL.AllFrame)
public class HttpProcessingLobby005_Get_DatabaseInfo extends BaseHttpAdminWeb_SignedIn {
	
	@Override public MyRespone onPOST(BackendSession session) {
		return new MyRespone(MyRespone.STATUS_Success).setData(managingDatabase.readListDatabaseInfo());
	}
	
	
	@Override
	public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
	}
}
