package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.IOException;
import java.util.Arrays;

import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;

@Http(api = API.SubTable_2Primary_querry_random)
@HttpDocument(id = 5259, Header = API.H3b2_Primary2)
public class HttpProcessing_Table2Primary259_QuerryRandom extends BaseQuerry2Primary {
	public short numberRow;
	
	@Override public long[] onGetOffset(DBTable_2Primary database1Primary) throws IOException {
		if(numberRow<1)
			return new long[0];
		
		long countRow=database1Primary.countRow();
		if(countRow==0)
			return null;
		
		if(numberRow>countRow)
			numberRow=(short) countRow;
		
		long[] listOffset=new long[numberRow];
		if(countRow<4097) {
			long[] listAll=new long[(int) countRow];
			for(int i=0;i<countRow;i++) {
				database1Primary.rfBTree.seek(i*8);
				listAll[i]=database1Primary.rfBTree.readLong();
			}
			Arrays.sort(listAll);
			
			for(int i=0;i<numberRow;i++)
				listOffset[i] = listAll[(int) (countRow-i-1)];
		}else
			for(int i=1;i<numberRow;i++) {
				database1Primary.rfBTree.seek(random.nextInt((int) countRow)*8);
				listOffset[i]=database1Primary.rfBTree.readLong();
			}
		return listOffset;
	}

	@Override public void test_api_in_document() {
		super.test_api_in_document();
		numberRow=50;
	}
}
