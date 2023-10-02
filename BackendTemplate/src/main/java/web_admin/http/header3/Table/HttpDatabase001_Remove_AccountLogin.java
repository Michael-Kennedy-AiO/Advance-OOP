package web_admin.http.header3.Table;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.PATH;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.Database_AccountLogin_Remove)
@HttpDocument(id = 3001, Header = API.H3_Database, Description = "Tạo database đăng nhập thông thường")
public class HttpDatabase001_Remove_AccountLogin extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	private transient DBGame_AccountLogin databaseAccount;
	private transient DBGame_UserData databaseUserData;
	private transient boolean isDeleteDatabase;
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		
		if(new File(PATH.DATABASE_FOLDER+"/"+DBId).exists()==false)
			return API.resDatabaseNotExist;
		
		try {
			databaseAccount=new DBGame_AccountLogin(DBId);
			if(databaseAccount.isExisted()==false)
				return new MyRespone(MyRespone.STATUS_TableNotExist,"Table AccountLogin is not enable");
			databaseUserData=new DBGame_UserData(DBId);

			if(databaseUserData.islocked())
				return API.resPending;
			else
				isDeleteDatabase = true;
			
			return API.resSuccess;
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception,BGUtility.getStringException(e));
		}
	}
	
	@Override public void onDestroy() {
		if(isDeleteDatabase) {
			databaseAccount.deleteAndClose();
			databaseUserData.deleteAndClose();
		}else {
			if(databaseAccount!=null)
				databaseAccount.closeDatabase();
			if(databaseUserData!=null)
				databaseUserData.closeDatabase();
		}
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
	}
}
