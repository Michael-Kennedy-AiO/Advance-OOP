package web_admin.http.header3.Table_a1.AccountConfig;

import java.io.IOException;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;

@Http(api = API.TableAccount_Describes_Update_ViewId)
@HttpDocument(id = 3183, Header = API.H3a1_TableAccount)
public class HttpAccountLogin183_Describes_Setting extends BaseDatabaseAccount {

	public String[] ColumnName;
	public int[] indexDescribe;
	public int[] ViewId;
	
	
	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		if(ColumnName==null || ColumnName.length==0)
			return API.resInvalidParameters;
		if(indexDescribe==null || indexDescribe.length==0)
			return API.resInvalidParameters;
		if(ViewId==null || ViewId.length==0)
			return API.resInvalidParameters;
		////////////////////////////////////
		if(ColumnName.length!=ViewId.length || indexDescribe.length!=ViewId.length)
			return API.resInvalidParameters;
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		for(int i=0;i<ViewId.length;i++) {
			databaseUserData.setCloumnViewId(indexDescribe[i], ViewId[i]);
			databaseUserData.setCloumnName(indexDescribe[i], ColumnName[i]);
		}
		return API.resSuccess;
	}

	@Override
	public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken = adminToken.toHashString();

		DBId = 1;
		
		indexDescribe = new int[] {0,1,2,3,4,5};
		ViewId=new int[] {1,3,5,0,2,4};
	}
}
