package server;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.Test;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.TimeManager;
import backendgame.com.core.database.BGColumn;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBTableRow;
import backendgame.com.database.table.DBTable_1Primary;
import backendgame.com.database.table.DBTable_2Primary;
import database_game.AdminLoginManager;
import database_game.DatabaseManager;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.PATH;
import server.onehit.BaseOnehit_AiO;
import web_admin.http.BaseHttpAdminWeb_SignedIn;
import web_admin.http.header1.login.HttpProcessing_Login;
import web_admin.test.EnglishName;

class InitDatabaseTest {
	public DBGame_AccountLogin databaseAccount;
	public DBGame_UserData databaseUserData;
	public DBTable_1Primary database1Primary;
	public DBTable_2Primary database2Primary;
	public DBTableRow tableRow;
	public static final short DBId=1;
	public Random random;
	
	@Test void test() {long timeBeginProcess = System.currentTimeMillis();
		BGUtility.deleteFolder(new File(PATH.DATABASE_FOLDER));
		random=new Random();
		BaseOnehit_AiO.setup();
	   	HttpProcessing_Login.adminLoginManager=AdminLoginManager.gI();
    	HttpProcessing_Login.databaseManager=DatabaseManager.gI();
    	BaseHttpAdminWeb_SignedIn.managingDatabase=DatabaseManager.gI();
    	DBGame_AccountLogin.databaseManager=DatabaseManager.gI();
    	DBGame_UserData.databaseManager=DatabaseManager.gI();
    	
    	DBGame_AccountLogin.timeManager = TimeManager.gI();
    	BaseHttpAdminWeb_SignedIn.timeManager = TimeManager.gI();
		DatabaseManager.gI().createDatabase("Database0", "aaaa", 0);
		DatabaseManager.gI().createDatabase("Test", "Testing", 1);
		
		
        int count=0;
        BGColumn describe;
        BGColumn[] listRandom = new BGColumn[4];
        /////////////////////////////////////////////////////////////////////////////////////////
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.STRING;
        describe.Size = 12;
        describe.ColumnName = "NameShow";
        describe.loadDefaultData("User can change");
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BYTE;
        describe.Size = 1;
        describe.ColumnName = "AvatarId";
        describe.loadDefaultData((byte)3);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BYTE;
        describe.Size = 1;
        describe.ColumnName = "DeviceType";
        describe.loadDefaultData((short)99);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.LONG;
        describe.Size = 8;
        describe.ColumnName = "Gold";
        describe.Indexing = BGColumn.INDEXING;
        describe.loadDefaultData((long)1024);
        listRandom[count++]=describe;
        
        BGAttribute objectWriter=new BGAttribute();
        File file = new File(PATH.DATABASE_FOLDER+"/"+DBId);
		if(file.exists())
			BGUtility.deleteFolder(file);
		file.mkdir();
		try {
			databaseAccount = new DBGame_AccountLogin(DBId);
			databaseUserData = new DBGame_UserData(DBId);
			databaseAccount.initNewDatabase();
			databaseUserData.initNewDatabase(listRandom);
			
			
			byte AccountType;
			String Password="";
			long userIdInsert;
			objectWriter.indexDescribe = 3;
			for(int i=0;i<1024;i++) {
				AccountType=database_game.AccountType.random();
				if(AccountType==database_game.AccountType.SystemAccount)
					Password=BGUtility.alphanumeric(5);
				
				userIdInsert=-1;
				while(userIdInsert==-1) {
					objectWriter.Value = random.nextInt(100);
					userIdInsert = databaseAccount.insertAccount(EnglishName.random(), AccountType, Password, databaseUserData,objectWriter);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Database Account : "+(System.currentTimeMillis()-timeBeginProcess)+" ms");timeBeginProcess=System.currentTimeMillis();
		//////////////////////////////////////////////////////////////////Primary 1
        /////////////////////////////////////////////////////////////////////////////////////////
        try {
			create1Primary();
			create1Primary();
			
			System.out.println("Create Table : "+(System.currentTimeMillis()-timeBeginProcess)+" ms");timeBeginProcess=System.currentTimeMillis();
			
			database1Primary=new DBTable_1Primary(DatabaseManager.gI().pathTable(DBId, (short) 1));
			
			long offsetInsert;
			objectWriter.indexDescribe=4;
			
			for(int i=0;i<1024;i++) {
				offsetInsert=-1;
				objectWriter.Value = random.nextInt(100);
				while(offsetInsert==-1) {
					offsetInsert=database1Primary.insert(EnglishName.random(),objectWriter);
				}
			}
			
			System.out.println("Create "+database1Primary.countRow()+" row in Primary1 : "+(System.currentTimeMillis()-timeBeginProcess)+" ms");timeBeginProcess=System.currentTimeMillis();
			
			//////////////////////////////////////////////////////////////////
			create2Primary();
			database2Primary=new DBTable_2Primary(DatabaseManager.gI().pathTable(DBId, (short) 2));
//			database2Primary.initNewDatabase(DBDefine_DataType.STRING, "Hash", DBDefine_DataType.INTEGER, "Range");
//			list = database2Primary.readDescribes();
			
			objectWriter.indexDescribe=2;
			for(int i=0;i<1024;i++) {
				offsetInsert=-1;
				objectWriter.Value = random.nextInt(100);
				while(offsetInsert==-1) {
					offsetInsert=database2Primary.insert(EnglishName.random(),random.nextInt(),objectWriter);
				}
			}
			System.out.println("Create "+database2Primary.countRow()+" row in Primary2 : "+(System.currentTimeMillis()-timeBeginProcess)+" ms");timeBeginProcess=System.currentTimeMillis();
			database2Primary.trace();
			
			//////////////////////////////////////////////////////////////////
			createTableRow();
			
			tableRow=new DBTableRow(DatabaseManager.gI().pathTable(DBId, (short) 3));
			
			objectWriter.indexDescribe=4;
			for(int i=0;i<1024;i++) {
				offsetInsert=-1;
				objectWriter.Value = random.nextInt(100);
				while(offsetInsert==-1) {
					offsetInsert=tableRow.insert(objectWriter);
				}
			}
			//////////////////////////////////////////////////////////////////
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("FINISH");
	}
	
	public void create1Primary() throws IOException {
		int count=0;
        BGColumn describe;
        BGColumn[] listRandom = new BGColumn[9];
		
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
        describe.Indexing = BGColumn.INDEXING;
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
		
		
		
		short TableId = DatabaseManager.gI().createTable(DBId, BGBaseDatabase.DBTYPE_1Primary, "Primary1Key", EnglishName.random(), 0);
		
		File file = new File(PATH.DATABASE_FOLDER+"/"+DBId+"/"+TableId);
		if(file.exists())
			BGUtility.deleteFolder(file);
		file.mkdirs();
		
		database1Primary=new DBTable_1Primary(DatabaseManager.gI().pathTable(DBId, TableId));
		database1Primary.initNewDatabase(DBDefine_DataType.STRING, "Primary",listRandom);
		database1Primary.closeDatabase();
	}
	public void create2Primary() throws IOException {
		int count=0;
		BGColumn describe;
		BGColumn[] listRandom = new BGColumn[4];
		
		describe = new BGColumn();
		describe.Type = DBDefine_DataType.IPV4;
		describe.Size = 4;
		describe.ColumnName = "IPV4";
		describe.loadDefaultData(123456);
		listRandom[count++] = describe;

		describe = new BGColumn();
		describe.Type = DBDefine_DataType.IPV6;
		describe.Size = 16;
		describe.ColumnName = "IPV6";
		describe.loadDefaultData(null);
		listRandom[count++] = describe;

		describe = new BGColumn();
		describe.Type = DBDefine_DataType.LONG;
		describe.Size = 8;
		describe.ColumnName = "Gold";
		describe.Indexing=BGColumn.INDEXING;
		describe.loadDefaultData((long) 0);
		listRandom[count++] = describe;
		
		describe = new BGColumn();
		describe.Type = DBDefine_DataType.LONG;
		describe.Size = 8;
		describe.ColumnName = "Diamond";
		describe.loadDefaultData(123456l);
		listRandom[count++] = describe;
		
		
		
		short TableId = DatabaseManager.gI().createTable(DBId, BGBaseDatabase.DBTYPE_2Primary, "Primary2Key", EnglishName.random(), 0);
		
		File file = new File(PATH.DATABASE_FOLDER+"/"+DBId+"/"+TableId);
		if(file.exists())
			BGUtility.deleteFolder(file);
		file.mkdirs();
		
		databaseAccount = new DBGame_AccountLogin(DBId);
		database2Primary=new DBTable_2Primary(DatabaseManager.gI().pathTable(DBId, TableId));
		database2Primary.initNewDatabase(DBDefine_DataType.STRING, "Hash", DBDefine_DataType.INTEGER, "Range",listRandom);
		database2Primary.closeDatabase();
	}
	
	public void createTableRow() throws IOException {
		int count=0;
        BGColumn describe;
        BGColumn[] listRandom = new BGColumn[9];
		
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
        describe.Indexing = BGColumn.INDEXING;
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
		
		
		
		short TableId = DatabaseManager.gI().createTable(DBId, BGBaseDatabase.DBTYPE_1Primary, "Primary1Key", EnglishName.random(), 0);
		
		File file = new File(PATH.DATABASE_FOLDER+"/"+DBId+"/"+TableId);
		if(file.exists())
			BGUtility.deleteFolder(file);
		file.mkdirs();
		
		tableRow=new DBTableRow(DatabaseManager.gI().pathTable(DBId, TableId));
		tableRow.initNewDatabase(listRandom);
		tableRow.closeDatabase();
	}
}
