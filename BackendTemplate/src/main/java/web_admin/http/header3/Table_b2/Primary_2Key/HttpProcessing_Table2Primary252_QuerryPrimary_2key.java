package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;

@Http(api = API.SubTable_2Primary_querry_primary2Key)
@HttpDocument(id = 5252, Header = API.H3b2_Primary2, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_Table2Primary252_QuerryPrimary_2key extends BaseQuerry2Primary {
	public Object HashValue,RangeValue;

	@Override public long[] onGetOffset(DBTable_2Primary database2Primary) throws IOException {
		long offset = database2Primary.querryOffset(HashValue, RangeValue);
		if(offset==-1)
			return new long[0];
		else
			return new long[] {offset};
	}
	
	

	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		HashValue="RichardKennedy";
		RangeValue=123;
	}
}
