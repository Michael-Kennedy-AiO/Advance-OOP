package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGCondition;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;

@Http(api = API.SubTable_1Primary_QuerryIndexing_ComparisonOperators)
@HttpDocument(id = 5160, Header = API.H3b1_Primary1)
public class HttpProcessing_Table1Primary160_QuerryIndexing_ComparisonOperators extends BaseQuerry1Primary {
	public short indexDescribe;
	public String ColumnName;
	public byte ComparisonOperator;//> or < or >= or <=
	public Object valueQuerry;
	public int limit;
	

	@Override public long[] onGetOffset(DBTable_1Primary database1Primary) throws IOException {
		BGColumn des = database1Primary.readBGColumnByIndex(indexDescribe);
		if(des.Type<0 || 99<des.Type)
			if(ComparisonOperator!=BGCondition.EqualTo)
				return null;
		
		if(new File(database1Primary.getPathIndexing(indexDescribe)).exists()==false)
			return null;
		
		long[] result = null;
		switch (ComparisonOperator) {
			case BGCondition.EqualTo:result = database1Primary.querryIndexing_EqualTo(indexDescribe, valueQuerry, limit);break;
			case BGCondition.LessThan:result = database1Primary.querryIndexing_LessThan(indexDescribe, valueQuerry, limit);break;
			case BGCondition.LessThanOrEqualTo:result = database1Primary.querryIndexing_LessThanOrEqualTo(indexDescribe, valueQuerry, limit);break;
			case BGCondition.GreaterThan:result = database1Primary.querryIndexing_GreaterThan(indexDescribe, valueQuerry, limit);break;
			case BGCondition.GreaterThanOrEqualTo:result = database1Primary.querryIndexing_GreaterThanOrEqualTo(indexDescribe, valueQuerry, limit);break;
			default:return null;
		}
		
		if(result==null)
			return new long[0];
		
		int skip = database1Primary.getDataLength() - des.OffsetRow;
		for(int i=0;i<result.length;i++)
			result[i] = database1Primary.readLong(result[i]+skip);//result[i]+skip = end of data → offset primary
		
		return result;
	}
	
	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		
		indexDescribe = 4;
		ColumnName = "AAAAAA";
		
		ComparisonOperator = BGCondition.LessThan;
		valueQuerry = 66;
		limit = 20;
	}
}
