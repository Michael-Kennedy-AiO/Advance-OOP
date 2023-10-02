package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGCondition;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;

@Http(api = API.SubTable_2Primary_QuerryIndexing_ComparisonOperators)
@HttpDocument(id = 5260, Header = API.H3b2_Primary2)
public class HttpProcessing_Table2Primary260_QuerryIndexing_ComparisonOperators extends BaseQuerry2Primary {
	public short indexDescribe;
	public String ColumnName;
	public byte ComparisonOperator;//> or < or >= or <=
	public Object valueQuerry;
	public int limit;
	

	@Override public long[] onGetOffset(DBTable_2Primary database2Primary) throws IOException {
		BGColumn des = database2Primary.readBGColumnByIndex(indexDescribe);
		if(des.Type<0 || 99<des.Type)
			if(ComparisonOperator!=BGCondition.EqualTo)
				return null;
		
		if(new File(database2Primary.getPathIndexing(indexDescribe)).exists()==false)
			return null;
		
		long[] result = null;
		switch (ComparisonOperator) {
			case BGCondition.EqualTo:result = database2Primary.querryIndexing_EqualTo(indexDescribe, valueQuerry, limit);break;
			case BGCondition.LessThan:result = database2Primary.querryIndexing_LessThan(indexDescribe, valueQuerry, limit);break;
			case BGCondition.LessThanOrEqualTo:result = database2Primary.querryIndexing_LessThanOrEqualTo(indexDescribe, valueQuerry, limit);break;
			case BGCondition.GreaterThan:result = database2Primary.querryIndexing_GreaterThan(indexDescribe, valueQuerry, limit);break;
			case BGCondition.GreaterThanOrEqualTo:result = database2Primary.querryIndexing_GreaterThanOrEqualTo(indexDescribe, valueQuerry, limit);break;
			default:return null;
		}
		
		return result;
	}
	
	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		
		indexDescribe = 2;
		ColumnName = "AAAAAA";
		
		ComparisonOperator = BGCondition.LessThan;
		valueQuerry = 100;
		limit = 20;
	}
}
