package web_admin.http.header2.lobby;

import java.io.File;

import backendgame.com.core.BGUtility;
import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import server.config.PATH;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.Lobby_Delete_Database)
@HttpDocument(id = 2090, Header = API.H2_Lobby)
public class HttpProcessingLobby090_DeleteDatabase extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		
		BGUtility.deleteFolder(new File(PATH.DATABASE_FOLDER + "/" + DBId));
		managingDatabase.deleteDatabase(DBId);
	    return API.resSuccess;
	}

	@Override
	public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
	}
}
