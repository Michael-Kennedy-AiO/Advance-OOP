package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_2Primary_delete)
@HttpDocument(id = 5290, Header = API.H3b2_Primary2)
public class HttpProcessing_Table2Primary290_DeleteRow extends Base2Primary {
	public Object HashValue;
	public Object RangeValue;
	
	
	@Override public MyRespone onProcess(DBTable_2Primary database2Primary, BackendSession session) throws IOException {
		synchronized (managingDatabase.getLockDatabase(DBId).getLockTable(TableId)) {
			database2Primary.delete(HashValue,RangeValue);
		}
		return API.resSuccess;
	}
	
	

	
}
