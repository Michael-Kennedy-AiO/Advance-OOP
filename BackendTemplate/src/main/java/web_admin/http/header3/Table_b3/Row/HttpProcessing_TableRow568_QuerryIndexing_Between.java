package web_admin.http.header3.Table_b3.Row;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTableRow;
import web_admin.API;

@Http(api = API.SubTable_Row_QuerryIndexing_Between)
@HttpDocument(id = 5560,Header = API.H3b3_Row, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableRow568_QuerryIndexing_Between extends BaseQuerryTableRow {
	public int indexDescribe;
	public String ColumnName;
	public Object valueLeft;
	public Object valueRight;
	public int limit;

	@Override public long[] onListRowIds(DBTableRow database, BackendSession session) throws IOException {
		long[] result = database.querryIndexing_Between(indexDescribe, valueLeft, valueRight, limit);
		if(result!=null) {
			for(int i=0;i<result.length;i++)
				result[i] = database.getRowIdAt(result[i]);
		}
		return result;
	}

	
}
