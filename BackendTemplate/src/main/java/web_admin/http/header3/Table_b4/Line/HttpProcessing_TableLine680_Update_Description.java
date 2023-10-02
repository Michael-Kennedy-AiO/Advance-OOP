package web_admin.http.header3.Table_b4.Line;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLine;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_Line_update_Description)
@HttpDocument(id = 3880,Header = API.H3b4_Line, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableLine680_Update_Description extends BaseProcess_DatabaseLine {
	public long Id;
	public String Description;
	
	@Override public MyRespone onProcess(DBLine databaseLine) throws IOException {
		databaseLine.updateNodeDescription(dbString, Id, Description);
		return API.resSuccess;
	}
}
