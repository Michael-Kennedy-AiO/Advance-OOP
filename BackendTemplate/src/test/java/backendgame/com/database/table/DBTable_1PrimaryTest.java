package backendgame.com.database.table;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.database.BGBaseDatabase;
import server.config.PATH;
import web_admin.test.EnglishName;

class DBTable_1PrimaryTest {
	public DBTable_1Primary database1Primary;
	public long timeBegin;
	
	public static final String key1 = "Đức Trí";
	public static final String key2 = "Đức Tiến";
	public static final String key3 = "Dương Đức Trí";
	public static final String key4 = "Dương Đức Tiến";
	
	@Test void test() throws IOException {
		timeBegin=System.currentTimeMillis();
		
		database1Primary = new DBTable_1Primary(PATH.TEST_FOLDER+"/DBTable_1Primary");
		
		int count=0;
        BGColumn describe;
        BGColumn[] listRandom = new BGColumn[9];
        /////////////////////////////////////////////////////////////////////////////////////////
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BOOLEAN;
        describe.Size = 1;
        describe.ColumnName = "Cột một";
        describe.loadDefaultData(true);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BYTE;
        describe.Size = 1;
        describe.ColumnName = "Cộ Số 2";
        describe.loadDefaultData((byte)3);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.SHORT;
        describe.Size = 2;
        describe.ColumnName = "Column2";
        describe.loadDefaultData((short)12);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.FLOAT;
        describe.Size = 4;
        describe.ColumnName = "Column3";
        describe.loadDefaultData(12.34f);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.INTEGER;
        describe.Size = 4;
        describe.ColumnName = "Column4";
        describe.loadDefaultData(4567);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.DOUBLE;
        describe.Size = 8;
        describe.ColumnName = "Column5";
        describe.loadDefaultData(1234.5678d);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.LONG;
        describe.Size = 8;
        describe.ColumnName = "Column6";
        describe.loadDefaultData(9999l);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.STRING;
        describe.ColumnName = "Column7";
        describe.loadDefaultData("Đức Trí");
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BYTE_ARRAY;
        describe.Size = 8;
        describe.ColumnName = "Column8";
        byte[] dataTmp = new byte[describe.Size];
        BGUtility.random.nextBytes(dataTmp);
        describe.loadDefaultData(dataTmp);
        listRandom[count++]=describe;
        database1Primary.initNewDatabase(DBDefine_DataType.STRING, "Primary", listRandom);
		
        
        /////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("Step1 : "+(System.currentTimeMillis()-timeBegin)+" ms");
        insertRandom(500);
		long ok1 = database1Primary.insert(key1);
		insertRandom(500);
		
		System.out.println("Step2 : "+(System.currentTimeMillis()-timeBegin)+" ms");
		long ok2 = database1Primary.insert(key2);
		insertRandom(500);
		
		System.out.println("Step3 : "+(System.currentTimeMillis()-timeBegin)+" ms");
		long ok3 = database1Primary.insert(key3);
		insertRandom(500);
		
		System.out.println("Step4 : "+(System.currentTimeMillis()-timeBegin)+" ms");
		long ok4 = database1Primary.insert(key4);
		insertRandom(500);
		
		System.out.println("Step5 : "+(System.currentTimeMillis()-timeBegin)+" ms");
		System.out.println("QuerryOffset : " + database1Primary.querryOffset(key1));
		System.out.println("QuerryOffset : " + database1Primary.querryOffset(key2));
		System.out.println("QuerryOffset : " + database1Primary.querryOffset(key3));
		System.out.println("QuerryOffset : " + database1Primary.querryOffset(key4));
		System.out.println("Step6 : "+(System.currentTimeMillis()-timeBegin)+" ms");
		
		System.out.println(ok1+"*******---->"+database1Primary.loadRow(ok1, listRandom)+"===>"+database1Primary.getRandomAccessFile().readLong());
		System.out.println(ok2+"*******---->"+database1Primary.loadRow(ok2, listRandom)+"===>"+database1Primary.getRandomAccessFile().readLong());
		System.out.println(ok3+"*******---->"+database1Primary.loadRow(ok3, listRandom)+"===>"+database1Primary.getRandomAccessFile().readLong());
		System.out.println(ok4+"*******---->"+database1Primary.loadRow(ok4, listRandom)+"===>"+database1Primary.getRandomAccessFile().readLong());
		
		System.out.println("TotalRow : "+database1Primary.rfBTree.length()/8);
		database1Primary.insert(key1);
		database1Primary.insert(key2);
		database1Primary.insert(key3);
		database1Primary.insert(key4);
		System.out.println("TotalRow : "+database1Primary.rfBTree.length()/8);
		database1Primary.trace();
		
		database1Primary.traceRows(database1Primary.querryOffset(key1),database1Primary.querryOffset(key2),database1Primary.querryOffset(key3),database1Primary.querryOffset(key4));
	}

	private void insertRandom(int numberRow) throws IOException {
		long offsetInsert;
		for(int i=0;i<numberRow;i++) {
			offsetInsert=-1;
			while(offsetInsert==-1)
				offsetInsert=database1Primary.insert(EnglishName.random());
		}
	}
	
	@AfterEach void tearDown() throws Exception {
		if(database1Primary!=null) {
			database1Primary.closeDatabase();

			try {
				Files.deleteIfExists(FileSystems.getDefault().getPath(database1Primary.getPath() + BGBaseDatabase.DATA_EXTENSION));
				Files.deleteIfExists(FileSystems.getDefault().getPath(database1Primary.getPath() + BGBaseDatabase.INDEX_EXTENSION));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("DBTable_1PrimaryTest : "+(System.currentTimeMillis()-timeBegin)+" ms");
	}
}
