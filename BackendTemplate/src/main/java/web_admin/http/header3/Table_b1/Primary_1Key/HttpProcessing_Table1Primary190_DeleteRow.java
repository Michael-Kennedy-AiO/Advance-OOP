package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.test.EnglishName;

@Http(api = API.SubTable_1Primary_delete)
@HttpDocument(id = 5190, Header = API.H3b1_Primary1)
public class HttpProcessing_Table1Primary190_DeleteRow extends Base1Primary {
	public Object primaryKey;

	@Override
	public MyRespone onProcess(DBTable_1Primary database1Primary,BackendSession session) throws IOException {

		synchronized (managingDatabase.getLockDatabase(DBId).getLockTable(TableId)) {
			database1Primary.delete(primaryKey);
		}
		
		return API.resSuccess;
	}


	@Override public void test_api_in_document() {
		super.test_api_in_document();
		primaryKey=EnglishName.random();
	}
}
