package web_admin.http.header3.Table_b3.Row;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTableRow;
import web_admin.API;

@Http(api = API.SubTable_Row_querry_Latest)
@HttpDocument(id = 5550,Header = API.H3b3_Row)
public class HttpProcessing_TableRow550_Querry_Latest extends BaseQuerryTableRow {
	public short numberUser;
	
	@Override
	public long[] onListRowIds(DBTableRow database, BackendSession session) throws IOException {
		if(numberUser<1)
			return null;
		
		long countUser = database.countRow();
		if(numberUser>countUser)
			numberUser=(short) countUser;
		
		long[] listRowId = new long[numberUser];
		for(short i=0;i<numberUser;i++)
			listRowId[i] = countUser - i - 1;
		
		return listRowId;
	}

	@Override public void test_api_in_document() {
		super.test_api_in_document();
		TableId=3;
		numberUser = 20;
	}


	
}
