package web_admin.http.header3.Table;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBLeaderboard_Link;
import web_admin.API;
import web_admin.DES;

@Http(api = API.DBTable_Create_Leaderboard_Link)
@HttpDocument(id = 3026, Header = API.H3_Database, Description = DES.Table_Create)
public class HttpProcessingTable026_CreateTable_Leaderboard_Link extends BaseCreateTable {
	
	@Override public byte onTableType() {return BGBaseDatabase.DBTYPE_LeaderboardLink;}
	@Override public BGBaseDatabase onCreateTable(String path) throws IOException {
		DBLeaderboard_Link database = new DBLeaderboard_Link(path);
		return database;
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		TableName = "Leaderboard";
		Description = "Leaderboard";
		ViewId = random.nextInt(100);
	}
}
