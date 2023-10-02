package web_admin.http.header3.Table_b4.Line;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBLine;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

public abstract class BaseProcess_DatabaseLine extends BaseHttpAdminWeb_SignedIn{
	public short DBId;
	public short TableId;
	
	public abstract MyRespone onProcess(DBLine databaseLine)throws IOException;
	
	private transient DBLine databaseLine=null;
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0 || TableId<0)
			return API.resInvalid;
		
		if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.DATA_EXTENSION).exists()==false)
			return API.resTableNotExist;
		
		try {
			databaseLine=new DBLine(managingDatabase.pathTable(DBId, TableId));
			if(databaseLine.readByte(BGBaseDatabase.Offset_DatabaseType)!=BGBaseDatabase.DBTYPE_LineNode)
				return API.resTableInvalid;
			
			return onProcess(databaseLine);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception,BGUtility.getStringException(e));
		}
	}

	
	@Override
	public void onDestroy() {
		if(databaseLine!=null)
			databaseLine.closeDatabase();
	}
	
	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		TableId=1;
	}
}
