package web_admin.http.header3.Table_a2.AccountQuerry;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.PATH;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;

@Http(api = API.ParsingRow_Update_AccountStatus)
@HttpDocument(id = 3285, Header = API.H3a2_TableAccount_Querry)
public class HttpProcessing285_Update_AccountStatus extends BaseDatabaseAccount {
	public long userId;
	public byte status;
	
	
	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		databaseUserData.updateStatus(userId, status);
		return API.resSuccess;
	}
	

	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		
		status=2;
		if(new File(PATH.DATABASE_FOLDER+"/"+DBId).exists())
			try {
				DBGame_AccountLogin databaseAccount=new DBGame_AccountLogin(DBId);
				if(databaseAccount.isExisted()==false)
					userId = databaseAccount.countRow()-1;
				databaseAccount.closeDatabase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
