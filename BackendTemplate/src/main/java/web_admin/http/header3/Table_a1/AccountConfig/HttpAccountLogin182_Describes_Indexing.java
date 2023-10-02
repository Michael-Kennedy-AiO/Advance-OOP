package web_admin.http.header3.Table_a1.AccountConfig;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;

@Http(api = API.TableAccount_Describes_Indexing)
@HttpDocument(id = 3182, Header = API.H3a1_TableAccount)
public class HttpAccountLogin182_Describes_Indexing extends BaseDatabaseAccount {
	public int indexDescribe;
	public byte indexType;

	
	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		synchronized (managingDatabase.getLockDatabase(DBId).getLockIndex(indexDescribe)) {
			if(indexType==0) {
				databaseUserData.setCloumn_Indexing(indexDescribe, indexType);
				new File(databaseUserData.getPathIndexing(indexDescribe)).delete();
			}else {
				databaseUserData.setCloumn_Indexing(indexDescribe, indexType);
				databaseUserData.indexingColumn(indexDescribe);
			}
		}
		return API.resDeveloping;
	}

	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		indexDescribe = 1;
	}
}
