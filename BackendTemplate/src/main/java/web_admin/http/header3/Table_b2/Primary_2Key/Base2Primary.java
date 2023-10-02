package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

public abstract class Base2Primary extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	
	public abstract MyRespone onProcess(DBTable_2Primary database2Primary,BackendSession session) throws IOException;
	
	private transient DBTable_2Primary database2Primary;
	@Override public MyRespone onPOST(BackendSession session) {
		try {
			if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.INDEX_EXTENSION).getParentFile().exists()==false)
				return API.resTableNotExist;
				
			database2Primary=new DBTable_2Primary(managingDatabase.pathTable(DBId, TableId));
			if(database2Primary.length()<BGBaseDatabase.HEADER)
				return new MyRespone(MyRespone.STATUS_TableNotExist).setMessage("Table is not init");
			if(database2Primary.readByte(BGBaseDatabase.Offset_DatabaseType)!=BGBaseDatabase.DBTYPE_2Primary)
				return new MyRespone(MyRespone.STATUS_DatabaseNotExist).setMessage("Database invalid type");
			if(database2Primary.islocked())
				return API.resPending;
			
			return onProcess(database2Primary,session);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception).setMessage(BGUtility.getStringException(e));
		}
	}

	@Override public void onDestroy() {
		if(database2Primary!=null)
			database2Primary.closeDatabase();
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		TableId=2;
	}
}
