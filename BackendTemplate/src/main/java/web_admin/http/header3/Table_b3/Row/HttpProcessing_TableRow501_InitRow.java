package web_admin.http.header3.Table_b3.Row;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBTableRow;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_Row_init)
@HttpDocument(id = 5501,Header = API.H3b3_Row)
public class HttpProcessing_TableRow501_InitRow extends BaseTableRow {
	public long rowId;
	public BGAttribute[] listWriter;

	@Override
	public MyRespone onProcess(DBTableRow database, BackendSession session) throws IOException {
		database.initRow(rowId, listWriter);
		return API.resSuccess;
	}
	
	

	
}
