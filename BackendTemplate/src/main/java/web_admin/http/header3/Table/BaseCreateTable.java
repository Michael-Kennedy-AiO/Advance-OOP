package web_admin.http.header3.Table;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.BGBaseDatabase;
import database_game.table.DBGame_AccountLogin;
import server.config.PATH;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

public abstract class BaseCreateTable extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public String TableName;
	public String Description;
	public int ViewId;
	
	private transient DBGame_AccountLogin databaseAccount;
	public abstract byte onTableType();
	public abstract BGBaseDatabase onCreateTable(String path) throws IOException;
	
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		
		if(managingDatabase.checkDatabase(DBId)==false)
			return API.resDatabaseNotExist;
		
		short TableId=-1;
		synchronized (managingDatabase.getLockDatabase(DBId)) {
			TableId = managingDatabase.createTable(DBId, onTableType(), TableName, Description, ViewId);
			if(TableId==-1)
				return API.resFailure;
		}
		
		File file = new File(PATH.DATABASE_FOLDER+"/"+DBId+"/"+TableId);
		if(file.exists())
			BGUtility.deleteFolder(file);
		file.mkdirs();
		
		try {
			databaseAccount = new DBGame_AccountLogin(DBId);
			BGBaseDatabase database = onCreateTable(managingDatabase.pathTable(DBId, TableId));
			database.writeByte(BGBaseDatabase.Offset_DatabaseType, onTableType());
			
			if(databaseAccount.isExisted()) {
				database.writeLong(BGBaseDatabase.Offset_AccessKey, databaseAccount.readLong(DBGame_AccountLogin.Offset_AccessKey));
				database.writeLong(BGBaseDatabase.Offset_ReadKey, databaseAccount.readLong(DBGame_AccountLogin.Offset_ReadKey));
				database.writeLong(BGBaseDatabase.Offset_WriteKey, databaseAccount.readLong(DBGame_AccountLogin.Offset_WriteKey));
				
				database.writeByte(BGBaseDatabase.Offset_LogoutId, databaseAccount.readByte(DBGame_AccountLogin.Offset_LogoutId));
			}else {
				database.writeLong(BGBaseDatabase.Offset_AccessKey, 100000+random.nextInt(900000));
				database.writeLong(BGBaseDatabase.Offset_ReadKey, 100000+random.nextInt(900000));
				database.writeLong(BGBaseDatabase.Offset_WriteKey, 100000+random.nextInt(900000));
				
				database.writeByte(BGBaseDatabase.Offset_LogoutId, (byte) 1);
			}
			
			database.writeLong(BGBaseDatabase.Offset_TimeCreate, timeManager.getDayMonthYearAsInteger(System.currentTimeMillis()));
			
			if(database.length()<BGBaseDatabase.HEADER)
				database.getRandomAccessFile().setLength(BGBaseDatabase.HEADER);
			
			return new MyRespone(MyRespone.STATUS_Success).setData(TableId);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception).setData(BGUtility.getStringException(e));
		}
	}

	
	
	@Override public void onDestroy() {
		super.onDestroy();
		if(databaseAccount!=null)
			databaseAccount.closeDatabase();
	}
}
