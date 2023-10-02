package web_admin.http.header3.Table_a2.AccountQuerry;

import java.io.IOException;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.AdminToken;

@Http(api = API.TABLERow_QuerryAccountData_ByListUserId)
@HttpDocument(id = 3256, Header = API.H3a2_TableAccount_Querry)
public class HttpProcessing256_Querry_By_ListUserId extends BaseQuerryAccount {
	public long[] listUserId;

	@Override
	public long[] onGetUserId(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		return listUserId;
	}

	@Override
	public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		
		DBId=1;
		
		listUserId = new long[] {
				1
				,2
				,3
				,4
				,5
				,6
				,7
				,8
				,9
				,10
		};
	}
}
