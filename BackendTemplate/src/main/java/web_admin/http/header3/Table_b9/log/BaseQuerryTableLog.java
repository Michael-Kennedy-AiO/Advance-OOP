package web_admin.http.header3.Table_b9.log;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBLog;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

public abstract class BaseQuerryTableLog extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	
	public abstract long[] onListRows(DBLog database,BackendSession session) throws IOException;
	
	private transient DBLog database;
	@Override public MyRespone onPOST(BackendSession session) {
		try {
			if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.INDEX_EXTENSION).getParentFile().exists()==false)
				return API.resTableNotExist;
				
			database=new DBLog(managingDatabase.pathTable(DBId, TableId));
			if(database.length()<BGBaseDatabase.HEADER)
				return new MyRespone(MyRespone.STATUS_TableNotExist).setMessage("Table is not init");
			if(database.readByte(BGBaseDatabase.Offset_DatabaseType)!=BGBaseDatabase.DBTYPE_Row)
				return new MyRespone(MyRespone.STATUS_DatabaseNotExist).setMessage("Database invalid type");
			if(database.islocked())
				return API.resPending;
			
//			return onProcess(database,session);
			return API.resSuccess;
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception).setMessage(BGUtility.getStringException(e));
		}
	}

	@Override public void onDestroy() {
		if(database!=null)
			database.closeDatabase();
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		TableId=2;
	}

}
