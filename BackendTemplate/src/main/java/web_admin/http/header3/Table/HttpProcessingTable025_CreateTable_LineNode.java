package web_admin.http.header3.Table;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBLine;
import web_admin.API;
import web_admin.DES;

@Http(api = API.DBTable_Create_LineNode)
@HttpDocument(id = 3025, Header = API.H3_Database, Description = DES.Table_Create)
public class HttpProcessingTable025_CreateTable_LineNode extends BaseCreateTable {
	
	private transient DBLine database;
	@Override public byte onTableType() {return BGBaseDatabase.DBTYPE_LineNode;}
	@Override public BGBaseDatabase onCreateTable(String path) throws IOException {
		database=new DBLine(path);
		database.initNewDatabase();
		return database;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		database.closeDatabase();
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		TableName = "LineNode";
		Description = "TestLinenode";
		ViewId = random.nextInt(100);
	}
}
