package backendgame.com.database.table;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import database_game.DatabaseManager;

class Table2Primary_Show {
	private long timeBegin;
	private DBTable_2Primary database2Primary;
	public static final short DBId=1;
	public static final short TableId=2;
	public Random random;
	public RandomAccessFile rf;
	
	public static final short indexDescribe=2;
	@Test void test() throws IOException, URISyntaxException {
		database2Primary = new DBTable_2Primary(DatabaseManager.gI().pathTable(DBId, TableId));
		database2Primary.trace();
		random=new Random();
		
		int countRow = (int) database2Primary.countRow();
		if(countRow==0){
			System.out.println("No row");
			return;
		}
		int numberRow=100;
		if(numberRow>countRow)
			numberRow=(int) countRow;
		////////////////////////////////////////////////////////////////////////////////////
		long[] result=new long[numberRow];
		long[] listAll=new long[(int) countRow];
		for(int i=0;i<countRow;i++) {
			database2Primary.rfBTree.seek(i*8);
			listAll[i]=database2Primary.rfBTree.readLong();
		}
		Arrays.sort(listAll);
		
		for(int i=0;i<numberRow;i++) {
			result[i] = listAll[(int) (countRow-i-1)];
		}
		
		database2Primary.traceRows(result);
		
		
//		DBDescribe[] listDescribe=database2Primary.readDescribes();
//		for(int i=0;i<countRow;i++) {
//			System.out.println(database2Primary.readPrimaryAtRow(database2Primary.getOffsetInBtree(i)).toString()+"	"+database2Primary.readValue(database2Primary.getOffsetInBtree(i),listDescribe[2].OffsetRow,listDescribe[2].Type));
//		}
		
		System.out.println("*********************************************************************");
//		rf=new RandomAccessFile(database2Primary.getPathIndexing(indexDescribe), "r");
//		for(int i=0;i<countRow;i++) {
//			rf.seek(i*8);
//			database2Primary.getra.seek(rf.readLong());
//			System.out.println(database2Primary.rfData.readLong());
//		}
	}
	
	@AfterEach void tearDown() throws Exception {
		if(database2Primary!=null)
			database2Primary.closeDatabase();
		if(rf!=null)
			rf.close();
		System.out.println("DBTableAI_2PrimaryTest : "+(System.currentTimeMillis()-timeBegin)+" ms");
	}
}
