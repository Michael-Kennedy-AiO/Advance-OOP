package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBTable_1Primary;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_2Primary_insert)
@HttpDocument(id = 5200, Header = API.H3b2_Primary2, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_Table2Primary210_InsertRow extends Base2Primary {
	public Object HashValue;
	public Object RangeValue;
	public BGAttribute[] listWriter;

	@Override public MyRespone onProcess(DBTable_2Primary database2Primary,BackendSession session) throws IOException {
		for(BGAttribute writer:listWriter)
			writer.initValue(database2Primary,session.socket);
		
		synchronized (managingDatabase.getLockDatabase(DBId).getLockTable(TableId)) {
			if(database2Primary.insert(HashValue, RangeValue,listWriter)==-1)
				return API.resExisted;
			else
				return API.resSuccess;
		}
	}
	
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.INDEX_EXTENSION).exists()==false)
			return;
		
		HashValue = "Richard";
		RangeValue=123;
		DBTable_1Primary database1Primary=null;
		try {
			database1Primary=new DBTable_1Primary(managingDatabase.pathTable(DBId, TableId));
				
			BGColumn[] list = database1Primary.readBGColumn();
			if(list!=null) {
				listWriter=new BGAttribute[list.length];
				for(int i=0;i<list.length;i++){
					listWriter[i]=new BGAttribute();
					listWriter[i].columnName = list[i].ColumnName;
					listWriter[i].indexDescribe = i;					
					
					if(list[i].Type==DBDefine_DataType.IPV4 || list[i].Type==DBDefine_DataType.IPV6)
						listWriter[i].InitType=BGAttribute.Init_IP;
					else
						listWriter[i].InitType=BGAttribute.Init_Random;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(database1Primary!=null)
			database1Primary.closeDatabase();
	}

}
