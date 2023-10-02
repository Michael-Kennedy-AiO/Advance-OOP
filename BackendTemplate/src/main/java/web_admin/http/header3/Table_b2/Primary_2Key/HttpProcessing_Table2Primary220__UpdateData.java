package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.IOException;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.MyRespone;

@Http(api = API.SubTable_2Primary_update)
@HttpDocument(id = 5220, Header = API.H3b2_Primary2, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_Table2Primary220__UpdateData extends Base2Primary {
	
	public Object HashValue;
	public Object RangeValue;
	public BGAttribute[] listWriter;
	
	@Override public MyRespone onProcess(DBTable_2Primary database2Primary,BackendSession session) throws IOException{
		if(HashValue==null)
			return API.resInvalid;
		
		if(listWriter==null || listWriter.length==0)
			return API.resInvalidParameters;
		
		database2Primary.updateAttribute(HashValue, RangeValue, listWriter);
		
		return API.resSuccess;
	}

	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		
		DBId=1;
		TableId=2;
		
		DBTable_2Primary database2Primary=null;
		try {
			database2Primary=new DBTable_2Primary(managingDatabase.pathTable(DBId, TableId));
			if(database2Primary.countRow()>0) {
				
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}
		if(database2Primary!=null)
			database2Primary.closeDatabase();
	}
}
