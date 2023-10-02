package web_admin.http.header3.Table_b3.Row;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTableRow;
import web_admin.API;

@Http(api = API.SubTable_Row_querry_ListId)
@HttpDocument(id = 5560,Header = API.H3b3_Row, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableRow556_Querry_ListId extends BaseQuerryTableRow {
	public long[] listRowIds;

	@Override
	public long[] onListRowIds(DBTableRow database, BackendSession session) throws IOException {
		return listRowIds;
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		TableId=3;
		listRowIds = new long[] {1,2,3,4,5,6,7,8,9,10};
	}

	
}

