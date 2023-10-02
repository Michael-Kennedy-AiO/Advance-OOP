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
import web_admin.DES;

@Http(api = API.TABLERow_QuerryAccountData_ByLatest)
@HttpDocument(id = 3250, Header = API.H3a2_TableAccount_Querry, Description = DES.querryLatest)
public class HttpProcessing250_Querry_By_Latest extends BaseQuerryAccount {
	public short numberUser;

	@Override
	public long[] onGetUserId(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		if(numberUser<1)
			return null;
		
		long countUser = databaseUserData.countRow();
		if(numberUser>countUser)
			numberUser=(short) countUser;
		
		long[] listUserId = new long[numberUser];
		for(short i=0;i<numberUser;i++)
			listUserId[i] = countUser - i - 1;
		
		return listUserId;
	}
	

	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		
		DBId=1;
		
		numberUser = 20;
	}
}
