package web_admin.http.header3.Table_b4.Line;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLine;
import web_admin.API;

@Http(api = API.SubTable_Line_read_ListId)
@HttpDocument(id = 3850,Header = API.H3b4_Line, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableLine656_Querry_ListId extends BaseQuerry_LineNode {
	public long[] listId;

	@Override
	public long[] onListId(DBLine databaseLine) throws IOException {
		return listId;
	}
	
	

	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		listId = new long[] {0,1,2,3,4,5,6,7,8,9,10};
	}
}
