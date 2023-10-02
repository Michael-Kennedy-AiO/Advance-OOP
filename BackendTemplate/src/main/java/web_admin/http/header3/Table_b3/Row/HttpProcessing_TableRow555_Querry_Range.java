package web_admin.http.header3.Table_b3.Row;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTableRow;
import web_admin.API;

@Http(api = API.SubTable_Row_querry_Range)
@HttpDocument(id = 5560,Header = API.H3b3_Row, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableRow555_Querry_Range extends BaseQuerryTableRow {
	public long userIdBegin;
	public long userIdEnd;

	

	@Override
	public long[] onListRowIds(DBTableRow database, BackendSession session) throws IOException {
		long countRow = database.countRow();

		if(userIdBegin<0)
			userIdBegin=0;
		if(userIdBegin>countRow-1)
			userIdBegin=countRow-1;
		
		if(userIdEnd<0)
			userIdEnd=0;
		if(userIdEnd>countRow-1)
			userIdEnd=countRow-1;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return rangeToArray(userIdBegin, userIdEnd);
	}

	private long[] rangeToArray(long begin,long end) {
		if(begin>end) {
			int length = (int) (begin-end+1);
			long[] result=new long[length];
			for(int i=0;i<length;i++)
				result[i] = begin - i;
			return result;
		}else {
			int length = (int) (end-begin+1);
			long[] result=new long[length];
			for(int i=0;i<length;i++)
				result[i] = begin + i;
			return result;
		}
	}
	
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		TableId=3;
		userIdBegin = 1;
		userIdEnd = 20;
	}



	
}
