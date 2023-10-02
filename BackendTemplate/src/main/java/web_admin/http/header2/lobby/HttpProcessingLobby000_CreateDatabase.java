package web_admin.http.header2.lobby;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.DES;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.Lobby_Create_Database)
@HttpDocument(id = 2000, Header = API.H2_Lobby, Description = DES.CreateDatabase)
public class HttpProcessingLobby000_CreateDatabase extends BaseHttpAdminWeb_SignedIn {
	public String DatabaseName;
	public String Description;
	public int ViewId;
	
	@Override public MyRespone onPOST(BackendSession session) {
		short dBId = managingDatabase.createDatabase(DatabaseName, Description, ViewId); 
		if(dBId==-1)
			return API.resDatabaseError;
		
		return new MyRespone(MyRespone.STATUS_Success).setData(dBId);
	}
	
	
	
	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
        
        DatabaseName = "Test AAA";
        Description = "BBBB";
        ViewId = 123;
	}
}
