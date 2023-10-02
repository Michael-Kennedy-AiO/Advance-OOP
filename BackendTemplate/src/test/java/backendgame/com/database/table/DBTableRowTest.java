package backendgame.com.database.table;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import database_game.DatabaseManager;

class DBTableRowTest {
	public DBTableRow database;
	public long timeBegin;
	public final short DBId=1;
	public final short TableId=3;
	
	@Test void test() throws IOException {timeBegin=System.currentTimeMillis();
		database=new DBTableRow(DatabaseManager.gI().pathTable(DBId, TableId));
		database.trace();
		
		
		database.trace(1,2,3,4,5,6,7,8,9,10);
	}

	
	@AfterEach void tearDown() throws Exception {
		if(database!=null)
			database.closeDatabase();
		System.out.println("\n\n\nDBTableRowTest : "+(System.currentTimeMillis()-timeBegin)+" ms");
	}
}
