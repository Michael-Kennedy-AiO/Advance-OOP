package database_game.table;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import database_game.AccountType;
import database_game.DatabaseManager;
import server.config.PATH;
import utility.Utility;

class AccountLoginTest_MultiDatabase {
	private long timeBegin;
	private int countAccount;
	public DBGame_AccountLogin databaseAccount;
	public DBGame_UserData databaseUserData;
	public Random random;

	public static final String password = "pass";
	
	@Test void test() throws IOException, InterruptedException {
		timeBegin=System.currentTimeMillis();
		random=new Random();
		countAccount = 0;
		
		DBGame_AccountLogin.databaseManager = DatabaseManager.gI();
		for(short k=100;k<120;k++) {
			if(new File(PATH.DATABASE_FOLDER+"/"+k).exists()==false)
				new File(PATH.DATABASE_FOLDER+"/"+k).mkdirs();
			databaseAccount=new DBGame_AccountLogin(k);
			databaseUserData=new DBGame_UserData(k);
			
			BGColumn[] listRandom = new BGColumn[5];
			for(int i=0;i<listRandom.length;i++)
				listRandom[i]=Utility.randomDescribe();
			databaseUserData.initNewDatabase(listRandom);
			
			randomAccount(50 + random.nextInt(10));
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.Device, password, databaseUserData);
			randomAccount(50 + random.nextInt(10));
			databaseAccount.insertAccount("Dương Đức Tiến", AccountType.EmailCode, password, databaseUserData);
			randomAccount(50 + random.nextInt(10));
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.SystemAccount, password, databaseUserData);
			randomAccount(50 + random.nextInt(10));
			databaseAccount.insertAccount("Nguyễn Thùy Dung", AccountType.EmailCode, password, databaseUserData);
			randomAccount(50 + random.nextInt(10));
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.Facebook, password, databaseUserData);
			randomAccount(50 + random.nextInt(10));
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.Google, password, databaseUserData);
			randomAccount(50 + random.nextInt(10));
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.AdsId, password, databaseUserData);
			randomAccount(50 + random.nextInt(10));
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.Apple, password, databaseUserData);
			randomAccount(50 + random.nextInt(10));
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.EmailCode, password, databaseUserData);
			randomAccount(50 + random.nextInt(10));
			
			
			
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.Device, password, databaseUserData);
			databaseAccount.insertAccount("Dương Đức Tiến", AccountType.EmailCode, password, databaseUserData);
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.SystemAccount, password, databaseUserData);
			databaseAccount.insertAccount("Nguyễn Thùy Dung", AccountType.EmailCode, password, databaseUserData);
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.Facebook, password, databaseUserData);
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.Google, password, databaseUserData);
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.AdsId, password, databaseUserData);
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.Apple, password, databaseUserData);
			databaseAccount.insertAccount("Dương Đức Trí", AccountType.EmailCode, password, databaseUserData);
//			
//			
//			System.out.println("*******************************************************************************");
//			databaseAccount.traceInfo("Dương Đức Trí", databaseUserData);
//			System.out.println("*******************************************************************************");
			databaseAccount.traceInfo("Dương Đức Trí");
			countAccount+=databaseAccount.countRow();
			if(databaseAccount.querryOffset("Dương Đức Trí").length!=7) {
				System.err.println("error : "+databaseAccount.querryOffset("Dương Đức Trí").length);
				assert false;
			}else
				System.out.println("Process ("+k+"): "+databaseAccount.countRow());
			
			
			databaseAccount.closeDatabase();
			databaseUserData.closeDatabase();
			BGUtility.deleteFolder(new File(PATH.DATABASE_FOLDER+"/"+k));
		}
	}
	

    public BGColumn randomDescribe() {
        BGColumn describe = new BGColumn();
        describe.Type = -1;
        switch (random.nextInt(14)) {
            case 0:describe.Type = DBDefine_DataType.BOOLEAN;
            case 1:if (describe.Type == -1)describe.Type = DBDefine_DataType.BYTE;
            case 2:if (describe.Type == -1)describe.Type = DBDefine_DataType.STATUS;
            case 3:if (describe.Type == -1)describe.Type = DBDefine_DataType.PERMISSION;
            case 4:if (describe.Type == -1)describe.Type = DBDefine_DataType.AVARTAR;
                describe.Size = 1;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData((byte)random.nextInt());
                break;

            case 5:describe.Type = DBDefine_DataType.SHORT;
                describe.Size = 2;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData((short)random.nextInt());
                break;

            case 6:describe.Type = DBDefine_DataType.FLOAT;
                describe.Size = 4;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextFloat());
                break;

            case 7:describe.Type = DBDefine_DataType.IPV4;
            case 8:if (describe.Type == -1)describe.Type = DBDefine_DataType.INTEGER;
                describe.Size = 4;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextInt());
                break;

            case 9:describe.Type = DBDefine_DataType.DOUBLE;
                describe.Size = 8;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextDouble());
                break;

            case 10:describe.Type = DBDefine_DataType.USER_ID;
            case 11:if (describe.Type == -1)describe.Type = DBDefine_DataType.TIMEMILI;
            case 12:if (describe.Type == -1)describe.Type = DBDefine_DataType.LONG;
                describe.Size = 8;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextLong());
                break;

            case 13:describe.Type = DBDefine_DataType.STRING;
                describe.Size = 8 + random.nextInt(5);
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(BGUtility.alphanumeric(3 + random.nextInt(2)));
                break;

            case 14:describe.Type = DBDefine_DataType.BYTE_ARRAY;
            case 15:if (describe.Type == -1)describe.Type = DBDefine_DataType.List;
                describe.Size = 5 + random.nextInt(5);
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                byte[] tmp = new byte[describe.Size-4];
                random.nextBytes(tmp);
                describe.loadDefaultData(tmp);
                break;

            case 16:describe.Type = DBDefine_DataType.IPV6;
                describe.Size = 16;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                byte[] ipv6 = new byte[describe.Size];
                random.nextBytes(ipv6);
                describe.loadDefaultData(ipv6);
                break;
                
            default:System.err.println("**********************************************************************===>");
                return null;
        }
        return describe;
    }
	
	
	public void randomAccount(int length) throws IOException {
		for(int i=0;i<length;i++)
			databaseAccount.insertAccount(BGUtility.alphanumeric(10+random.nextInt(10)), (byte) random.nextInt(7), BGUtility.alphanumeric(10+random.nextInt(10)), databaseUserData);
	}
	
	
	@AfterEach void tearDown() throws Exception {
		System.out.println("Finish Account("+countAccount+"): "+(System.currentTimeMillis()-timeBegin));
	}
}