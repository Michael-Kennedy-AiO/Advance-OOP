package web_admin.http;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.server.BackendSession;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.PATH;
import web_admin.API;
import web_admin.MyRespone;

public abstract class BaseDatabaseAccount extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	private transient DBGame_AccountLogin databaseAccount;
	private transient DBGame_UserData databaseUserData;
	
	public abstract MyRespone onProcess(BackendSession session,DBGame_AccountLogin databaseAccount,DBGame_UserData databaseUserData) throws IOException;
	
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		
		if(new File(PATH.DATABASE_FOLDER+"/"+DBId).exists()==false)
			return API.resDatabaseNotExist;
		
		try {
			databaseAccount=new DBGame_AccountLogin(DBId);
			databaseUserData=new DBGame_UserData(DBId);
			
			if(databaseUserData.islocked())
				return API.resPending;
			
			if(databaseAccount.isExisted()==false)
				return new MyRespone(MyRespone.STATUS_TableNotExist,"Table AccountLogin is not enable");
		} catch (IOException e) {
//			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_NotFound,"Database not exist");
		}
		try {
			return onProcess(session, databaseAccount, databaseUserData);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception,BGUtility.getStringException(e));
		}
	}
	
	@Override public void onDestroy() {
		if(databaseAccount!=null)
			databaseAccount.closeDatabase();
		if(databaseUserData!=null)
			databaseUserData.closeDatabase();
	}
}
