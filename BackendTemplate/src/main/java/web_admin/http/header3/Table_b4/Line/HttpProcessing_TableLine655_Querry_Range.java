package web_admin.http.header3.Table_b4.Line;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLine;
import web_admin.API;

@Http(api = API.SubTable_Line_read_Range)
@HttpDocument(id = 3854,Header = API.H3b4_Line, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableLine655_Querry_Range extends BaseQuerry_LineNode {
	public long idBegin;
	public long idEnd;

	@Override
	public long[] onListId(DBLine databaseLine) throws IOException {
		long countRow = databaseLine.count();

		if(idBegin<0)
			idBegin=0;
		if(idBegin>countRow-1)
			idBegin=countRow-1;
		
		if(idEnd<0)
			idEnd=0;
		if(idEnd>countRow-1)
			idEnd=countRow-1;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return rangeToArray(idBegin, idEnd);
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

	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		
		idBegin = 10;
		idEnd = 0;
	}
}
