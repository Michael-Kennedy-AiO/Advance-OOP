package web_admin.http.header3.Table_b4.Line;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLine;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_Line_update_Data)
@HttpDocument(id = 3820,Header = API.H3b4_Line, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableLine620_UpdateData extends BaseProcess_DatabaseLine {
	public long Id;
	public byte Type;
	public int Size;
	public Object value;
	
	@Override public MyRespone onProcess(DBLine databaseLine) throws IOException {
		if(databaseLine.validateTypeSize(Id, Type, Size)) {
			databaseLine.updateValue(Id, value);
			return API.resSuccess;
		}else
			return API.resInvalidParameters;
	}
}
