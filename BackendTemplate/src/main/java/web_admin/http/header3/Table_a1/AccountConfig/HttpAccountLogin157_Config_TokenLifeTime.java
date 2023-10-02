package web_admin.http.header3.Table_a1.AccountConfig;

import java.io.IOException;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;

@Http(api = API.TableAccount_Config_TokenLifeTime)
@HttpDocument(id = 3157, Header = API.H3a1_TableAccount)
public class HttpAccountLogin157_Config_TokenLifeTime extends BaseDatabaseAccount {
	public long TokenLifeTime;


	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		databaseAccount.writeLong(DBGame_AccountLogin.Offset_Token_LifeTime, TokenLifeTime);
		return API.resSuccess;
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		TokenLifeTime = TimeManager.Eight_HOUR_MILLISECOND;
	}
}
