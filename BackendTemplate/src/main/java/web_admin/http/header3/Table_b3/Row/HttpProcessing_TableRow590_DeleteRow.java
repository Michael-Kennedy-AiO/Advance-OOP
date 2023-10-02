package web_admin.http.header3.Table_b3.Row;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTableRow;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_Row_delete)
@HttpDocument(id = 5590,Header = API.H3b3_Row)
public class HttpProcessing_TableRow590_DeleteRow extends BaseTableRow {

	public long RowId;
	
	@Override
	public MyRespone onProcess(DBTableRow database, BackendSession session) throws IOException {

		synchronized (managingDatabase.getLockDatabase(DBId).getLockTable(TableId)) {
			database.deleteRow(RowId);
		}
		
		return API.resSuccess;
	}
	
	
}
