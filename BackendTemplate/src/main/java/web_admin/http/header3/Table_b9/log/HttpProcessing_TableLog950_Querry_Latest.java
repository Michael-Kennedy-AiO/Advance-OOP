package web_admin.http.header3.Table_b9.log;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLog;
import web_admin.API;

@Http(api = API.SubTable_Log_querry_Latest)
@HttpDocument(id = 5950,Header = API.H3b9_Log)
public class HttpProcessing_TableLog950_Querry_Latest extends BaseQuerryTableLog {
	public short numberRow;
	
	@Override
	public long[] onListRows(DBLog database, BackendSession session) throws IOException {
		if(numberRow<1)
			return null;
		
		long countUser = database.countRow();
		if(numberRow>countUser)
			numberRow=(short) countUser;
		
		long[] listRowId = new long[numberRow];
		for(short i=0;i<numberRow;i++)
			listRowId[i] = countUser - i - 1;
		
		return listRowId;
	}

	@Override public void test_api_in_document() {
		super.test_api_in_document();
		numberRow = 20;
	}


	
}
