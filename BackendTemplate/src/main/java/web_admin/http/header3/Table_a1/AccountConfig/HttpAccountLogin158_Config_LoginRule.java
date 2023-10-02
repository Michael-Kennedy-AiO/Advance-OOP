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

@Http(api = API.TableAccount_Config_LoginRule)
@HttpDocument(id = 3158, Header = API.H3a1_TableAccount)
public class HttpAccountLogin158_Config_LoginRule extends BaseDatabaseAccount {
	public boolean Device;
	public boolean SystemAccount;
	public boolean Facebook;
	public boolean Google;
	public boolean AdsId;
	public boolean Apple;
	public boolean EmailCode;

	@Override
	public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_Permission_Device);
		databaseAccount.rfData.writeBoolean(Device);
		
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_Permission_SystemAccount);
		databaseAccount.rfData.writeBoolean(SystemAccount);
		
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_Permission_Facebook);
		databaseAccount.rfData.writeBoolean(Facebook);
		
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_Permission_Google);
		databaseAccount.rfData.writeBoolean(Google);
		
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_Permission_AdsId);
		databaseAccount.rfData.writeBoolean(AdsId);
		
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_Permission_Apple);
		databaseAccount.rfData.writeBoolean(Apple);
		
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_Permission_EmailCode);
		databaseAccount.rfData.writeBoolean(EmailCode);
		return API.resSuccess;
	}

	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		Facebook = true;
		Google = true;
	}
}
