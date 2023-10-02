package web_admin.http.header3.Table_b4.Line;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLine;
import web_admin.API;

@Http(api = API.SubTable_Line_read_Latest)
@HttpDocument(id = 3852,Header = API.H3b4_Line, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableLine650_Querry_Latest extends BaseQuerry_LineNode {
	public short numberNode;

	@Override public long[] onListId(DBLine databaseLine) throws IOException {
		if(numberNode<1)
			return null;
		
		long countNode = databaseLine.count();
		if(numberNode>countNode)
			numberNode=(short) countNode;
		
		long[] listUserId = new long[numberNode];
		for(short i=0;i<numberNode;i++)
			listUserId[i] = countNode - i - 1;
		
		return listUserId;
	}
	
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		
		numberNode = 100;
	}
}
