package web_admin.test;

import java.io.IOException;

import backendgame.com.core.BGUtility;
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

@Http(api = API.TEST_InsertAccount)
@HttpDocument(id = 0)
public class TEST_AccountLogin extends BaseDatabaseAccount {
	public int numberAccount;
	
	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		long l = System.currentTimeMillis();
		byte AccountType;
		String Password="";
		long userIdInsert;
		
		for(int i=0;i<numberAccount;i++) {
			AccountType=database_game.AccountType.random();
			if(AccountType==database_game.AccountType.SystemAccount)
				Password=BGUtility.alphanumeric(5);
			
			userIdInsert=-1;
			while(userIdInsert==-1)
				userIdInsert = databaseAccount.insertAccount(EnglishName.random(), AccountType, Password, databaseUserData);
		}
		
//		numberAccount = (int) databaseUserData.countRow();
//		DBDescribe[] listDes=databaseUserData.readDescribes();
//		for(long i=0;i<numberAccount;i++) {
//			databaseUserData.writeData(i, listDes[6], random.nextInt((int) i+1));
//		}
		
		return new MyRespone(MyRespone.STATUS_Success).setData("TimeProcess : "+(System.currentTimeMillis()-l)+" ms");
	}
	
	
	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		
		DBId=1;
		
		numberAccount=4096;
	}
}
