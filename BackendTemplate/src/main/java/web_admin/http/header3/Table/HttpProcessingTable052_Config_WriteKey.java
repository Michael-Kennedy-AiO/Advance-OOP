package web_admin.http.header3.Table;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.core.BGUtility;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.DBTable_Config_WriteKey)
@HttpDocument(id = 3052, Header = API.H3_Database, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessingTable052_Config_WriteKey extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	
	public long WriteKey;
	
	private transient RandomAccessFile rf;;
	@Override public MyRespone onPOST(BackendSession session) {
		if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.DATA_EXTENSION).exists()==false)
			return API.resTableNotExist;
		
		try {
			rf=new RandomAccessFile(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.DATA_EXTENSION, "rw");
			if(rf.length()<BGBaseDatabase.HEADER)
				new MyRespone(MyRespone.STATUS_TableNotExist).setMessage("Table is not init");
			
			rf.seek(BGBaseDatabase.Offset_WriteKey);
			rf.writeLong(WriteKey);
			return API.resSuccess;
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception).setMessage(BGUtility.getStringException(e));
		}
	}

	@Override public void onDestroy() {
		super.onDestroy();
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		TableId = 1;
		WriteKey = 33333;
	}
}
