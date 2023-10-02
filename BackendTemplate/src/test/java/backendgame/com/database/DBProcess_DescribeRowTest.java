package backendgame.com.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.core.database.BGColumn;
import backendgame.com.database.table.DBTableRow;
import server.config.PATH;
import utility.Utility;

class DBProcess_DescribeRowTest {
	private long timeBegin;
	public DBTableRow database;
	public static final String filePath = PATH.TEST_FOLDER+"/DBProcess_DescribeRowTest";
	private Random random;

	@Test
	void test() throws IOException {
		timeBegin=System.currentTimeMillis();
		database = new DBTableRow(filePath);
		random=new Random();
		
		BGColumn[] listRandom = new BGColumn[10+random.nextInt(8)];
		for(int i=0;i<listRandom.length;i++)
			listRandom[i]=Utility.randomDescribe(null);
		
		database.initNewDatabase(listRandom);
		BGColumn[] list=database.readBGColumn();
		for(int i=0;i<1009;i++)
			database.insert();
		
		assertTrue(listRandom.length==list.length);
		for(int i=0;i<listRandom.length;i++) {
			listRandom[i].trace();
			list[i].trace();
			assertEquals(list[i].ColumnName, listRandom[i].ColumnName);
			assertEquals(list[i].OffsetRow, listRandom[i].OffsetRow);
			assertEquals(list[i].Type, listRandom[i].Type);
			assertEquals(list[i].Size, listRandom[i].Size);

			assertEquals(list[i].ViewId, listRandom[i].ViewId);
			assertEquals(list[i].Indexing, listRandom[i].Indexing);
//			assertEquals(list[i].getDefaultData(), listRandom[i].getDefaultData());
		}
		database.trace();
		database.traceRows(1,2,300,4,5,1001,6,7);
	}
	
	@AfterEach void tearDown() throws Exception {
		database.closeDatabase();
		Files.deleteIfExists(FileSystems.getDefault().getPath(filePath));
		System.out.println("DBProcess_DescribeRowTest : "+(System.currentTimeMillis()-timeBegin)+" ms");
	}
}
