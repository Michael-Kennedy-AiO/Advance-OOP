package web_admin.http.header3.Table_a2.AccountQuerry;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;

@Http(api = API.TABLERow_QuerryIndexing_Between)
@HttpDocument(id = 3268, Header = API.H3a2_TableAccount_Querry)
public class HttpProcessing268_QuerryIndexing_Between extends BaseQuerryAccount {
	public int indexDescribe;
	public String ColumnName;
	public Object valueLeft;
	public Object valueRight;
	public int limit;

	@Override public long[] onGetUserId(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		
		
		long[] result = databaseUserData.querryIndexing_Between(indexDescribe, valueLeft, valueRight, limit);
		if(result!=null) {
			for(int i=0;i<result.length;i++)
				result[i] = databaseUserData.getRowIdAt(result[i]);
		}
		return result;
	}

	
	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		
		indexDescribe = 6;
		ColumnName = "AAAAAA";
		
		valueLeft = 10;
		valueRight = 20;
	}
}
