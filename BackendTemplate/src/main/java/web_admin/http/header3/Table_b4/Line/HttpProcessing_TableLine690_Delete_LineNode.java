package web_admin.http.header3.Table_b4.Line;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLine;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_Line_delete_LineNode)
@HttpDocument(id = 3890,Header = API.H3b4_Line, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableLine690_Delete_LineNode extends BaseProcess_DatabaseLine {
	public long Id;

	@Override
	public MyRespone onProcess(DBLine databaseLine) throws IOException {
		if(Id<0)
			return API.resInvalidParameters;
		databaseLine.removeLineNode(Id);
		return API.resSuccess;
	}
	
	

	
}
