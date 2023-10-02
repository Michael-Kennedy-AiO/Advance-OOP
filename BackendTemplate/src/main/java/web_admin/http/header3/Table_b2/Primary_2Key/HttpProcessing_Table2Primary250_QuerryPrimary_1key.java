package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.IOException;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;

@Http(api = API.SubTable_2Primary_querry_primary1Key)
@HttpDocument(id = 5250, Header = API.H3b2_Primary2, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_Table2Primary250_QuerryPrimary_1key extends BaseQuerry2Primary {
	public Object HashValue;
	
	
	@Override public long[] onGetOffset(DBTable_2Primary database2Primary) throws IOException {
		if(database2Primary.TypeHash==DBDefine_DataType.STRING)
			return database2Primary.querryOffsetLike((String) HashValue, 100);
		else
			return database2Primary.querryOffset(HashValue);
	}
	

	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		HashValue="RichardKennedy";
	}
}
