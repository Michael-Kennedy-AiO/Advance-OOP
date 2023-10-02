package web_admin.http.header3.Table;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.SubTable_Delete_Table)
@HttpDocument(id = 3090, Header = API.H3_Database, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessingTable090_Delete extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		managingDatabase.deleteTable(DBId, TableId);
		return API.resSuccess;
	}

	
}
