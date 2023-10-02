package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.IOException;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.MyRespone;

@Http(api = API.SubTable_1Primary_Describes_Setting)
@HttpDocument(id = 5185, Header = API.H3b1_Primary1)
public class HttpProcessing_Table1Primary185_Describes_Setting extends Base1Primary {

	public String[] ColumnName;
	public int[] indexDescribe;
	public int[] ViewId;
	
	
	@Override public MyRespone onProcess(DBTable_1Primary database,BackendSession session) throws IOException {
		if(ColumnName==null || ColumnName.length==0)
			return API.resInvalidParameters;
		if(indexDescribe==null || indexDescribe.length==0)
			return API.resInvalidParameters;
		if(ViewId==null || ViewId.length==0)
			return API.resInvalidParameters;
		////////////////////////////////////
		if(ColumnName.length!=ViewId.length || indexDescribe.length!=ViewId.length)
			return API.resInvalidParameters;
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		for(int i=0;i<ViewId.length;i++) {
			database.setCloumnViewId(indexDescribe[i], ViewId[i]);
			database.setCloumnName(indexDescribe[i], ColumnName[i]);
		}
		return API.resSuccess;
	}

	@Override
	public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken = adminToken.toHashString();

		DBId = 1;
		
		indexDescribe = new int[] {0,1,2,3,4,5};
		ViewId=new int[] {1,3,5,0,2,4};
	}
}
