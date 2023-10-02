package database_game.table;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import database_game.DatabaseManager;

class AccountLogin_ShowAccount {

	private DBGame_AccountLogin dbAccount;
	private DBGame_UserData dbUserData;
	public int count=0;

	@Test void test() throws IOException {
		DBGame_AccountLogin.databaseManager=DatabaseManager.gI();
		dbAccount=new DBGame_AccountLogin((short) 1);
		dbUserData=new DBGame_UserData((short) 1);
		dbAccount.trace();
	}

	
	
	@AfterEach
	void tearDown() throws Exception {
		dbAccount.closeDatabase();
		dbUserData.closeDatabase();
	}
}
