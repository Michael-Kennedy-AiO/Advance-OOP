package web_admin.http.header3.Table_a1.AccountConfig;

import java.io.IOException;

import backendgame.com.core.TimeManager;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;
import web_admin.http.dto.DTO_Column;

@Http(api = API.TableAccount_Describes_Get)
@HttpDocument(id = 3160, Header = API.H3a1_TableAccount)
public class HttpAccountLogin160_Describes_Get extends BaseDatabaseAccount {

	@Override
	public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		BGColumn[] list = databaseUserData.readBGColumn();
		if (list == null || list.length == 0)
			return API.resEmpty;

		int numberDes = list.length;
		DTO_Column[] result = new DTO_Column[numberDes];
		for (int i = 0; i < numberDes; i++)
			result[i] = new DTO_Column(list[i]);

		return new MyRespone(MyRespone.STATUS_Success).setData(result);
	}

	@Override
	public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken = adminToken.toHashString();

		DBId = 1;
	}
}
