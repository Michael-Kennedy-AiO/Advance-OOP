package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;

@Http(api = API.SubTable_1Primary_QuerryIndexing_Between)
@HttpDocument(id = 5168, Header = API.H3b1_Primary1)
public class HttpProcessing_Table1Primary168_QuerryIndexing_Between extends BaseQuerry1Primary {
	public short indexDescribe;
	public String ColumnName;
	public Object valueLeft;
	public Object valueRight;
	public int limit;

	private transient RandomAccessFile rf;
	@Override public long[] onGetOffset(DBTable_1Primary database1Primary) throws IOException {
		BGColumn des = database1Primary.readBGColumnByIndex(indexDescribe);
		if(des.Type<0 || 99<des.Type)
			return null;
		
		if(new File(database1Primary.getPathIndexing(indexDescribe)).exists()==false)
			return null;
		
		long[] result = database1Primary.querryIndexing_Between(indexDescribe,valueLeft, valueRight, limit);
		if(result!=null) {
			int skip = database1Primary.getDataLength() - des.OffsetRow;
			for(int i=0;i<result.length;i++) 
				result[i] = database1Primary.readLong(result[i]+skip);//result[i]+skip = end of data → offset primary
		}
		return result;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		
		indexDescribe = 4;
		ColumnName = "AAAAAA";
		
		valueLeft = 10;
		valueRight = 20;
	}

}
