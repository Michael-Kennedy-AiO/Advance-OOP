package web_admin.http.header3.Table_a2.AccountQuerry;

import java.io.IOException;

import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGCondition;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;

@Http(api = API.TABLERow_QuerryIndexing_ComparisonOperators)
@HttpDocument(id = 3260, Header = API.H3a2_TableAccount_Querry)
public class HttpProcessing260_QuerryIndexing_ComparisonOperators extends BaseQuerryAccount {
	public int indexDescribe;
	public String ColumnName;
	public byte ComparisonOperator;//> or < or >= or <=
	public Object valueQuerry;
	public int limit;
	
	@Override public long[] onGetUserId(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		BGColumn des = databaseUserData.readBGColumnByIndex(indexDescribe);
		if(des.Type<0 || 99<des.Type)
			if(ComparisonOperator!=BGCondition.EqualTo)
				return null;
		
		long[] result = null;
		switch (ComparisonOperator) {
			case BGCondition.EqualTo:result = databaseUserData.querryIndexing_EqualTo(indexDescribe, valueQuerry, limit);break;
			case BGCondition.LessThan:result = databaseUserData.querryIndexing_LessThan(indexDescribe, valueQuerry, limit);break;
			case BGCondition.LessThanOrEqualTo:result = databaseUserData.querryIndexing_LessThanOrEqualTo(indexDescribe, valueQuerry, limit);break;
			case BGCondition.GreaterThan:result = databaseUserData.querryIndexing_GreaterThan(indexDescribe, valueQuerry, limit);break;
			case BGCondition.GreaterThanOrEqualTo:result = databaseUserData.querryIndexing_GreaterThanOrEqualTo(indexDescribe, valueQuerry, limit);break;
			default:return null;
		}
		if(result!=null)
			for(int i=0;i<result.length;i++)
				result[i] = databaseUserData.getRowIdAt(result[i]);
		return result;
	}

	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		
		indexDescribe = 6;
		ColumnName = "AAAAAA";
		
		ComparisonOperator = BGCondition.LessThan;
		valueQuerry = 100;
		limit = 20;
	}
}
