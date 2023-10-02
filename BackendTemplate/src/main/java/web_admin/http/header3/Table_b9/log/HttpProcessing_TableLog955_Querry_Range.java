package web_admin.http.header3.Table_b9.log;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLog;
import web_admin.API;

@Http(api = API.SubTable_Log_querry_Range)
@HttpDocument(id = 5960,Header = API.H3b9_Log)
public class HttpProcessing_TableLog955_Querry_Range extends BaseQuerryTableLog {
	public long rowBegin;
	public long rowEnd;

	

	@Override
	public long[] onListRows(DBLog database, BackendSession session) throws IOException {
		long countRow = database.countRow();

		if(rowBegin<0)
			rowBegin=0;
		if(rowBegin>countRow-1)
			rowBegin=countRow-1;
		
		if(rowEnd<0)
			rowEnd=0;
		if(rowEnd>countRow-1)
			rowEnd=countRow-1;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return rangeToArray(rowBegin, rowEnd);
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
		rowBegin = 1;
		rowEnd = 20;
	}



	
}
