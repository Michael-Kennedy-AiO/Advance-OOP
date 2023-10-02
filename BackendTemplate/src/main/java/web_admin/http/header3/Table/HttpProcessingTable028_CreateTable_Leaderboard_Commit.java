package web_admin.http.header3.Table;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBLeaderboard_Commit;
import web_admin.API;
import web_admin.DES;

@Http(api = API.DBTable_Create_Leaderboard_Commit)
@HttpDocument(id = 3028, Header = API.H3_Database, Description = DES.Table_Create)
public class HttpProcessingTable028_CreateTable_Leaderboard_Commit extends BaseCreateTable {
	
	@Override public byte onTableType() {return BGBaseDatabase.DBTYPE_LeaderboardCommit;}
	@Override public BGBaseDatabase onCreateTable(String path) throws IOException {
		DBLeaderboard_Commit database = new DBLeaderboard_Commit(path);
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
