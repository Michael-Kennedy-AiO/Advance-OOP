package web_admin.http.header3.Table_b9.log;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBLog;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_Log_insert)
@HttpDocument(id = 5900,Header = API.H3b9_Log)
public class HttpProcessing_TableLog900_InsertRow extends BaseTableLog {
	public BGAttribute[] listWriter;

	@Override public MyRespone onProcess(DBLog database, BackendSession session) throws IOException {
		
		
		
		
		return null;
	}

}
