package web_admin.test;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBTableRow;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.header3.Table_b3.Row.BaseTableRow;

@Http(api = API.TEST_TableRow)
@HttpDocument(id = 0,Description = "isBtree dùng để view theo Btree")
public class TEST_TableRow extends BaseTableRow {
	public int numberRow;

	@Override public MyRespone onProcess(DBTableRow database, BackendSession session) throws IOException {
		BGAttribute attribute=new BGAttribute();
		attribute.indexDescribe=4;
		synchronized (managingDatabase.getLockDatabase(DBId).getLockTable(TableId)) {
			long offsetInsert;
			for(int i=0;i<numberRow;i++) {
				offsetInsert=-1;
				attribute.Value=random.nextLong();
				while(offsetInsert==-1)
					offsetInsert=database.insert(attribute);
			}
		}
		return API.resSuccess;
	}

	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		TableId=3;
		numberRow=1024;
	}

}