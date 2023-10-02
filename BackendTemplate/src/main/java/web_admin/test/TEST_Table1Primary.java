package web_admin.test;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.header3.Table_b1.Primary_1Key.Base1Primary;

@Http(api = API.TEST_Table1Primary)
@HttpDocument(id = 0,Description = "isBtree dùng để view theo Btree")
public class TEST_Table1Primary extends Base1Primary {
	public int numberRow;

	@Override public MyRespone onProcess(DBTable_1Primary database1Primary,BackendSession session) throws IOException {
		if(numberRow<1)
			return API.resInvalidParameters;
		
		BGAttribute attribute=new BGAttribute();
		attribute.indexDescribe=4;
		synchronized (managingDatabase.getLockDatabase(DBId).getLockTable(TableId)) {
			long offsetInsert;
			for(int i=0;i<numberRow;i++) {
				offsetInsert=-1;
				attribute.Value=random.nextLong();
				while(offsetInsert==-1)
					offsetInsert=database1Primary.insert(EnglishName.random(),attribute);
			}
		}
		
		return API.resSuccess;
	}

	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		numberRow=1024;
	}
}