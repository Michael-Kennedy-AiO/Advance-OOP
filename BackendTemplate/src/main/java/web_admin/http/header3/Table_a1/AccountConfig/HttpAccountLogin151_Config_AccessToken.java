package web_admin.http.header3.Table_a1.AccountConfig;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;

@Http(api = API.TableAccount_Config_AccessToken)
@HttpDocument(id = 3151, Header = API.H3a1_TableAccount)
public class HttpAccountLogin151_Config_AccessToken extends BaseDatabaseAccount {
	public long AccessKey;

	@Override
	public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		if (DBId < 1)
			return API.resInvalidParameters;
		databaseAccount.writeLong(DBGame_AccountLogin.Offset_AccessKey, AccessKey);
		return API.resSuccess;
	}

	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		AccessKey = 111111111;
	}
}
