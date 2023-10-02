package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBBaseTable_Primary;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_2Primary_KeyName)
@HttpDocument(id = 5200, Header = API.H3b2_Primary2, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_Table2Primary200_KeyName extends Base2Primary {
	public String HashName;
	public String RangeName;

	@Override
	public MyRespone onProcess(DBTable_2Primary database1Primary,BackendSession session) throws IOException {
		if(HashName==null || RangeName==null || (HashName.getBytes().length+RangeName.getBytes().length)>500)
			throw new IOException("Invalid params");
		
		database1Primary.getRandomAccessFile().seek(DBBaseTable_Primary.Offset_Primary_Name);
		database1Primary.getRandomAccessFile().writeUTF(HashName);
		database1Primary.getRandomAccessFile().writeUTF(RangeName);
		
		return API.resSuccess;
	}
	
	
	






}
