package web_admin.http.header3.Table_a2.AccountQuerry;

import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGAttribute;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.DES;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;

@Http(api = API.TABLERow_Create_Account)
@HttpDocument(id = 3200, Header = API.H3a2_TableAccount_Querry, Description = DES.insertAccount)
public class HttpProcessing200_Create_Account extends BaseDatabaseAccount {
	public String Credential;
	public byte AccountType;
	public String Password;
	public BGAttribute[] listWriter;
	
	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		for(BGAttribute writer:listWriter)
			writer.initValue(databaseUserData,session.socket);
		
		long userId = databaseAccount.insertAccount(Credential, AccountType, Password, databaseUserData, listWriter);
		if(userId==-1) {
			return API.resExisted;
		}else
			return new MyRespone(MyRespone.STATUS_Success).setData(userId);
	}
	
	
	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		
		DBId=1;
		
		AccountType=database_game.AccountType.random();
		if(AccountType==database_game.AccountType.SystemAccount)
			Password=BGUtility.alphanumeric(5);
		
		switch (random.nextInt(7)) {
			case 0:
				Credential = "Richard Kennedy";
				break;
			case 1:
				Credential = "Micheal Kennedy";
				break;
			case 2:
				Credential = "Jenny Kennedy";
				break;
			case 3:
				Credential = "Nike";
				break;
			case 4:
				Credential = "Adidas";
				break;
			case 5:
				Credential = "Jacqueline Lee Bouvier";
				break;
			case 6:
				Credential = "Jacqueline";
				break;
			default:
				Credential="Richard";
				break;
		}
	}
}
