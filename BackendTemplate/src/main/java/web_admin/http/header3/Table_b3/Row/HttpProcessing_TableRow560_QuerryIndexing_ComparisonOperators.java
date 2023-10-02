package web_admin.http.header3.Table_b3.Row;

import java.io.IOException;

import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGCondition;
import backendgame.com.database.table.DBTableRow;
import web_admin.API;

@Http(api = API.SubTable_Row_QuerryIndexing_ComparisonOperators)
@HttpDocument(id = 5560,Header = API.H3b3_Row, Description = "Tạo database đăng nhập thông thường")
public class HttpProcessing_TableRow560_QuerryIndexing_ComparisonOperators extends BaseQuerryTableRow {
	public int indexDescribe;
	public String ColumnName;
	public byte ComparisonOperator;//> or < or >= or <=
	public Object valueQuerry;
	public int limit;
	
	@Override public long[] onListRowIds(DBTableRow database, BackendSession session) throws IOException {
		BGColumn des = database.readBGColumnByIndex(indexDescribe);
		if(des.Type<0 || 99<des.Type)
			if(ComparisonOperator!=BGCondition.EqualTo)
				return null;
		
		long[] result = null;
		switch (ComparisonOperator) {
			case BGCondition.EqualTo:result = database.querryIndexing_EqualTo(indexDescribe, valueQuerry, limit);break;
			case BGCondition.LessThan:result = database.querryIndexing_LessThan(indexDescribe, valueQuerry, limit);break;
			case BGCondition.LessThanOrEqualTo:result = database.querryIndexing_LessThanOrEqualTo(indexDescribe, valueQuerry, limit);break;
			case BGCondition.GreaterThan:result = database.querryIndexing_GreaterThan(indexDescribe, valueQuerry, limit);break;
			case BGCondition.GreaterThanOrEqualTo:result = database.querryIndexing_GreaterThanOrEqualTo(indexDescribe, valueQuerry, limit);break;
			default:return null;
		}
		if(result!=null)
			for(int i=0;i<result.length;i++)
				result[i] = database.getRowIdAt(result[i]);
		return result;
	}

	
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		TableId=3;
	}
}
