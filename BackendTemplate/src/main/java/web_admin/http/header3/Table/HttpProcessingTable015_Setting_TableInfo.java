package web_admin.http.header3.Table;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.DBTable_Setting_TableInfo)
@HttpDocument(id = 3015, Header = API.H3_Database, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessingTable015_Setting_TableInfo extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	public int ViewId;
	public String TableName;
	public String Description;
	
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		
		managingDatabase.updateTableInfo(DBId, TableId, ViewId, TableName, Description);
		return API.resSuccess;
	}

	@Override public void test_api_in_document() {
		super.test_api_in_document();
		
		DBId=1;
		TableId=1;
		ViewId=123456789;
		TableName="TableName Updated";
		Description="Description Updated";
	}
}
