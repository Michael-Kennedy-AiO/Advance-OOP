package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.IOException;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.test.EnglishName;

@Http(api = API.SubTable_1Primary_querry_primary)
@HttpDocument(id = 5150, Header = API.H3b1_Primary1)
public class HttpProcessing_Table1Primary150_QuerryPrimary extends BaseQuerry1Primary {
	public Object primaryKey;

	@Override public long[] onGetOffset(DBTable_1Primary database1Primary) throws IOException {
		long offset = database1Primary.querryOffset(primaryKey);
		if(offset==-1)
			return new long[0];
		else
			return new long[] {offset};
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		primaryKey=EnglishName.random();
	}
}
