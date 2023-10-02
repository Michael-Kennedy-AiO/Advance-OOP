package web_admin.http.header3.Table_b1.Primary_1Key;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;

@Http(api = API.SubTable_1Primary_Describes_Indexing)
@HttpDocument(id = 5182, Header = API.H3b1_Primary1)
public class HttpProcessing_Table1Primary182_Describes_Indexing extends BaseHttpAdminWeb_SignedIn {
	public String[] ColumnName;
	public int[] indexDescribe;
	public boolean[] Indexing;
	public byte[] Properties;
	
	
	@Override public MyRespone onPOST(BackendSession session) {
		return null;
	}

	
}
