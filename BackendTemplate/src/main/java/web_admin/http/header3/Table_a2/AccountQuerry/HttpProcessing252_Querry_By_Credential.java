package web_admin.http.header3.Table_a2.AccountQuerry;

import java.io.IOException;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.AdminToken;

@Http(api = API.TABLERow_QuerryAccountData_ByCredential)
@HttpDocument(id = 3252, Header = API.H3a2_TableAccount_Querry)
public class HttpProcessing252_Querry_By_Credential extends BaseQuerryAccount {
	public String Credential;

	@Override
	public long[] onGetUserId(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		long[] listBtreeIndex = databaseAccount.querryIndexLike(Credential, 100);
		if(listBtreeIndex!=null) {
			int numberOffset = listBtreeIndex.length;
			for(int i=0;i<numberOffset;i++) {
				databaseAccount.rfData.seek(databaseAccount.getOffsetInBtree(listBtreeIndex[i]));
				databaseAccount.skipPrimary();
				listBtreeIndex[i] = databaseAccount.rfData.readLong();//Đây là userId
			}
		}
		return listBtreeIndex;
	}

	
	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		
		DBId=1;
		
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
		Credential="Esmé";
	}
}
