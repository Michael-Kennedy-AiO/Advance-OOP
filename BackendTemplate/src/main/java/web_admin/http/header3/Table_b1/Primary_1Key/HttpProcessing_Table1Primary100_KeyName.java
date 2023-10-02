package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.IOException;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBBaseTable_Primary;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_1Primary_KeyName)
@HttpDocument(id = 5100, Header = API.H3b1_Primary1)
public class HttpProcessing_Table1Primary100_KeyName extends Base1Primary {
	public String PrimaryName;

	@Override public MyRespone onProcess(DBTable_1Primary database1Primary,BackendSession session) throws IOException {
		if(PrimaryName==null || PrimaryName.getBytes().length>500)
			throw new IOException("Invalid params");
		
		database1Primary.writeUTF(DBBaseTable_Primary.Offset_Primary_Name,PrimaryName);
		return API.resSuccess;
	}
}
