package web_admin.http.header3.Table;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

import backendgame.com.core.BGUtility;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.StringConstant;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.DBTable_Get_TableInfo)
@HttpDocument(id = 3010, Header = API.H3_Database, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessingTable010_Get_TableInfo extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	
	private transient RandomAccessFile rfData;
	@Override public MyRespone onPOST(BackendSession session) {
		if (DBId < 0)
			return API.resInvalidParameters;
		
		File file = new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.DATA_EXTENSION);
		if (file.exists() == false || file.length() < BGBaseDatabase.HEADER)
			return API.resTableNotExist;

		try {
			rfData = new RandomAccessFile(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.DATA_EXTENSION, "r");

			HashMap<String, Object> map = new HashMap<>();

			map.put(StringConstant.TableType, readByte(BGBaseDatabase.Offset_DatabaseType));
			map.put(StringConstant.Permission, readByte(BGBaseDatabase.Offset_Permission));
			map.put(StringConstant.LogoutId, readByte(BGBaseDatabase.Offset_LogoutId));

			map.put(StringConstant.AccessKey, readLong(BGBaseDatabase.Offset_AccessKey));
			map.put(StringConstant.ReadKey, readLong(BGBaseDatabase.Offset_ReadKey));
			map.put(StringConstant.WriteKey, readLong(BGBaseDatabase.Offset_WriteKey));

			map.put(StringConstant.TimeCreate, readLong(BGBaseDatabase.Offset_TimeCreate));
			map.put(StringConstant.Version, readLong(BGBaseDatabase.Offset_Version));

			return new MyRespone(MyRespone.STATUS_Success).setData(map);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception, BGUtility.getStringException(e));
		}
	}
	
	public byte readByte(long offset) throws IOException {
		rfData.seek(offset);
		return rfData.readByte();
	}
	public long readLong(long offset) throws IOException {
		rfData.seek(offset);
		return rfData.readLong();
	}
	
	@Override public void onDestroy() {
		super.onDestroy();
		if(rfData!=null)
			try {rfData.close();} catch (IOException e) {e.printStackTrace();}
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		TableId=1;
	}
}
