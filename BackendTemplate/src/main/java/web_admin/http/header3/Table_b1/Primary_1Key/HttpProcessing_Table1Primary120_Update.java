package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.MyRespone;

@Http(api = API.SubTable_1Primary_update)
@HttpDocument(id = 5120, Header = API.H3b1_Primary1)
public class HttpProcessing_Table1Primary120_Update extends Base1Primary {
	public Object PrimaryValue;
	public BGAttribute[] listWriter;

	private transient RandomAccessFile[] listRFIndexing;
	@Override public MyRespone onProcess(DBTable_1Primary database1Primary,BackendSession session) throws IOException{
		if(PrimaryValue==null)
			return API.resInvalid;
		
		if(listWriter==null || listWriter.length==0)
			return API.resInvalidParameters;
		
		database1Primary.updateAttribute(PrimaryValue, listWriter);

		return API.resSuccess;
	}
	
	@Override public void onDestroy() {
		super.onDestroy();
		if(listRFIndexing!=null){
			int numberRF=listRFIndexing.length;
			for(int i=0;i<numberRF;i++)
				if(listRFIndexing[i]!=null)
					try {listRFIndexing[i].close();} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		
		if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.INDEX_EXTENSION).exists()==false)
			return;
		
		DBTable_1Primary database1Primary=null;
		try {
			database1Primary=new DBTable_1Primary(managingDatabase.pathTable(DBId, TableId));
			if(database1Primary.countRow()>0) {
				long countRow=database1Primary.countRow();
				if(countRow<4097) {
					long[] listAll=new long[(int) countRow];
					for(int i=0;i<countRow;i++) {
						database1Primary.rfBTree.seek(i*8);
						listAll[i]=database1Primary.rfBTree.readLong();
					}
					Arrays.sort(listAll);
					
					PrimaryValue=database1Primary.readPrimaryAtOffset(listAll[(int) (countRow-1)]);
				}else {
					database1Primary.rfBTree.seek((countRow-1)*8);
					PrimaryValue=database1Primary.readPrimaryAtOffset(database1Primary.rfBTree.readLong());
				}
				
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
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(database1Primary!=null)
			database1Primary.closeDatabase();
	}
}
