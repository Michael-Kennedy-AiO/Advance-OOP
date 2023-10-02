package backendgame.com.database.table;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.database.BGBaseDatabase;
import database_game.DatabaseManager;

class Table1Primary_Show {
	public DBTable_1Primary database1Primary;
	public long timeBegin;
	
	public final short DBId=1;
	public final short TableId=1;
	@Test void test() throws IOException {timeBegin=System.currentTimeMillis();
		database1Primary=new DBTable_1Primary(DatabaseManager.gI().pathTable(DBId, TableId));
		if (database1Primary.length() < BGBaseDatabase.HEADER) {
			System.out.println("Table is not init");
			return;
		}
		
		if(database1Primary.readByte(BGBaseDatabase.Offset_DatabaseType)!=BGBaseDatabase.DBTYPE_1Primary) {
			System.out.println("Database invalid type : "+database1Primary.readByte(BGBaseDatabase.Offset_DatabaseType));
			return;
		}
		
		database1Primary.trace();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
//		database1Primary.rfData.seek(DB_BaseTable.Offset_PrimaryName);
//		mapData.put(StringConstant.PrimaryName, database1Primary.rfData.readUTF());
//		
//		DBDescribe[] list=database1Primary.readDescribes();
	
		long countRow=database1Primary.countRow();
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
		for(int i=0;i<countRow;i++)
			listAll[i]=database1Primary.getOffsetInBtree(i);
		Arrays.sort(listAll);
		
		for(int i=0;i<numberRow;i++) {
			result[i] = listAll[(int) (countRow-i-1)];
		}
	
	
		database1Primary.traceRows(result);
		
		
		System.out.println("*************************************************************************************************");
		System.out.println("*************************************************************************************************");
		System.out.println("*************************************************************************************************");
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		short indexDescribe = 4;
		int limit = 10;
		result = database1Primary.querryIndexing_EqualTo(indexDescribe, 50, limit);
//		switch (ComparisonOperator) {
//			case ComparisonOperators.EqualTo:result = database1Primary.querryIndexing_EqualTo(des, valueQuerry, rf, limit);break;
//			case ComparisonOperators.LessThan:result = database1Primary.querryIndexing_LessThan(des, valueQuerry, rf, limit);break;
//			case ComparisonOperators.LessThanOrEqualTo:result = database1Primary.querryIndexing_LessThanOrEqualTo(des, valueQuerry, rf, limit);break;
//			case ComparisonOperators.GreaterThan:result = database1Primary.querryIndexing_GreaterThan(des, valueQuerry, rf, limit);break;
//			case ComparisonOperators.GreaterThanOrEqualTo:result = database1Primary.querryIndexing_GreaterThanOrEqualTo(des, valueQuerry, rf, limit);break;
//			default:return null;
//		}
		
		if(result!=null) {
			int skip = database1Primary.getDataLength() - database1Primary.getCloumn_DataOffset(indexDescribe);
			for(int i=0;i<result.length;i++) {
				result[i] = database1Primary.readLong(result[i]+skip);
			}
		}
		database1Primary.traceRows(result);
	}

	
	
	
	
	@AfterEach void tearDown() throws Exception {
		if(database1Primary!=null)
			database1Primary.closeDatabase();
		System.out.println("DBTable_1PrimaryTest : "+(System.currentTimeMillis()-timeBegin)+" ms");
	}
}
