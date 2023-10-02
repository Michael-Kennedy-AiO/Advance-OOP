package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

public abstract class Base1Primary extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	
	public abstract MyRespone onProcess(DBTable_1Primary database1Primary,BackendSession session) throws IOException;
	
	private transient DBTable_1Primary database1Primary;
	@Override public MyRespone onPOST(BackendSession session) {
		try {
			if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.INDEX_EXTENSION).getParentFile().exists()==false)
				return API.resTableNotExist;
				
			database1Primary=new DBTable_1Primary(managingDatabase.pathTable(DBId, TableId));
			if(database1Primary.length()<BGBaseDatabase.HEADER)
				return new MyRespone(MyRespone.STATUS_TableNotExist).setMessage("Table is not init");
			if(database1Primary.readByte(BGBaseDatabase.Offset_DatabaseType)!=BGBaseDatabase.DBTYPE_1Primary)
				return new MyRespone(MyRespone.STATUS_DatabaseNotExist).setMessage("Database invalid type");
			if(database1Primary.islocked())
				return API.resPending;
			
			return onProcess(database1Primary,session);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception).setMessage(BGUtility.getStringException(e));
		}
	}

	@Override public void onDestroy() {
		if(database1Primary!=null)
			database1Primary.closeDatabase();
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		TableId=1;
	}
}
