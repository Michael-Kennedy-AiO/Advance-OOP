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

@Http(api = API.TABLERow_QuerryAccountData_ByRange)
@HttpDocument(id = 3255, Header = API.H3a2_TableAccount_Querry)
public class HttpProcessing255_Querry_By_UserIdRange extends BaseQuerryAccount {
	public long userIdBegin;
	public long userIdEnd;

	@Override
	public long[] onGetUserId(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		long countRow = databaseUserData.countRow();

		if(userIdBegin<0)
			userIdBegin=0;
		if(userIdBegin>countRow-1)
			userIdBegin=countRow-1;
		
		if(userIdEnd<0)
			userIdEnd=0;
		if(userIdEnd>countRow-1)
			userIdEnd=countRow-1;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return rangeToArray(userIdBegin, userIdEnd);
	}

	
	private long[] rangeToArray(long begin,long end) {
		if(begin>end) {
			int length = (int) (begin-end+1);
			long[] result=new long[length];
			for(int i=0;i<length;i++)
				result[i] = begin - i;
			return result;
		}else {
			int length = (int) (end-begin+1);
			long[] result=new long[length];
			for(int i=0;i<length;i++)
				result[i] = begin + i;
			return result;
		}
	}
	
	
	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		
		DBId=1;
		
		userIdBegin = 1;
		userIdEnd = 20;
	}
}
