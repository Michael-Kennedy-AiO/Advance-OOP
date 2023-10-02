package web_admin.http.header3.Table_b9.log;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLog;
import web_admin.API;

@Http(api = API.SubTable_Log_querry_ListId)
@HttpDocument(id = 5960,Header = API.H3b9_Log)
public class HttpProcessing_TableLog956_Querry_ListId extends BaseQuerryTableLog {
	public long[] listRowIds;

	@Override
	public long[] onListRows(DBLog database, BackendSession session) throws IOException {
		return listRowIds;
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		TableId=3;
		listRowIds = new long[] {1,2,3,4,5,6,7,8,9,10};
	}

	
}

