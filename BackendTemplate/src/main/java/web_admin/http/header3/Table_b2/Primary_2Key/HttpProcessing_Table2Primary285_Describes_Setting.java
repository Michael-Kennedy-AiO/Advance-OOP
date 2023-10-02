package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.IOException;

import backendgame.com.core.TimeManager;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.MyRespone;

@Http(api = API.SubTable_2Primary_Describes_Setting)
@HttpDocument(id = 5285, Header = API.H3b2_Primary2)
public class HttpProcessing_Table2Primary285_Describes_Setting extends Base2Primary {

	public String[] ColumnName;
	public int[] indexDescribe;
	public int[] ViewId;
	
	
	@Override public MyRespone onProcess(DBTable_2Primary database,BackendSession session) throws IOException {
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
