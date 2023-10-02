package database_game.table;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import database_game.AccountType;
import database_game.DatabaseManager;

class AccountLoginTest_Neighbor {

	private DBGame_AccountLogin dbAccount;
	private DBGame_UserData dbUserData;
	public int count=0;
	public long timeBeginProcess;
	@Test void test() throws IOException {
		timeBeginProcess=System.currentTimeMillis();
		DBGame_AccountLogin.databaseManager=DatabaseManager.gI();
		dbAccount=new DBGame_AccountLogin((short) 1);
		dbUserData=new DBGame_UserData((short) 1);

		//////////////////////////////////////////////////////////
		String hash,hashLeft;
		byte range,rangeLeft;
		long numberRow = dbAccount.countRow();
		for(long i=1;i<numberRow;i++) {
			dbAccount.rfData.seek(dbAccount.getOffsetInBtree(i-1));
			hashLeft = dbAccount.rfData.readUTF();
			dbAccount.rfData.skipBytes(1);
			rangeLeft=dbAccount.rfData.readByte();
			
			dbAccount.rfData.seek(dbAccount.getOffsetInBtree(i));
			hash= dbAccount.rfData.readUTF();
			dbAccount.rfData.skipBytes(1);
			range=dbAccount.rfData.readByte();
			
			
			if(hash.equals(hashLeft) && range==rangeLeft)
				System.err.println(hash + " : "+AccountType.getName(range));
		}
	}

	
	
	@AfterEach
	void tearDown() throws Exception {
		dbAccount.closeDatabase();
		dbUserData.closeDatabase();
		System.out.println("Finish : "+(System.currentTimeMillis()-timeBeginProcess));
	}

}
