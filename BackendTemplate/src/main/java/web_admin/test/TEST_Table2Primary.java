package web_admin.test;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.header3.Table_b2.Primary_2Key.Base2Primary;

@Http(api = API.TEST_Table2Primary)
@HttpDocument(id = 0,Description = "isBtree dùng để view theo Btree")
public class TEST_Table2Primary extends Base2Primary {
	public int numberRow;

	@Override public MyRespone onProcess(DBTable_2Primary database2Primary,BackendSession session) throws IOException {
		if(numberRow<1)
			return API.resInvalidParameters;
		
		BGAttribute attribute=new BGAttribute();
		attribute.indexDescribe=2;
		synchronized (managingDatabase.getLockDatabase(DBId).getLockTable(TableId)) {
			long offsetInsert;
			for(int i=0;i<numberRow;i++) {
				offsetInsert=-1;
				attribute.Value=random.nextLong();
				while(offsetInsert==-1)
					offsetInsert=database2Primary.insert(EnglishName.random(),random.nextInt(),attribute);
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