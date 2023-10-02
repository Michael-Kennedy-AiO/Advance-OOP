package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;

@Http(api = API.SubTable_2Primary_QuerryIndexing_Between)
@HttpDocument(id = 5268, Header = API.H3b2_Primary2, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_Table2Primary268_QuerryIndexing_Between extends BaseQuerry2Primary {
	public short indexDescribe;
	public String ColumnName;
	public Object valueLeft;
	public Object valueRight;
	public int limit;
	
	@Override public long[] onGetOffset(DBTable_2Primary database2Primary) throws IOException {
		BGColumn des = database2Primary.readBGColumnByIndex(indexDescribe);
		if(des.Type<0 || 99<des.Type)
			return null;
		
		if(new File(database2Primary.getPathIndexing(indexDescribe)).exists()==false)
			return null;
		
		return database2Primary.querryIndexing_Between(indexDescribe, valueLeft, valueRight, limit);
	}

	
	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		
		indexDescribe = 2;
		ColumnName = "AAAAAA";
		
		valueLeft = 10;
		valueRight = 20;
	}

}
