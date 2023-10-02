package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.TimeManager;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.dto.DTO_Column;

@Http(api = API.SubTable_1Primary_Describes_Change)
@HttpDocument(id = 5180, Header = API.H3b1_Primary1)
public class HttpProcessing_Table1Primary180_Describes_Change extends Base1Primary {
	private transient DBTable_1Primary databaseOld=null;
	public DTO_Column[] Describes;

	@Override public MyRespone onProcess(DBTable_1Primary databaseNew,BackendSession session) throws IOException {
		if(Describes==null || Describes.length==0)
			return API.resInvalidParameters;
		
		int numberNewDescribeTables = Describes.length;
		BGColumn[] newDes = new BGColumn[numberNewDescribeTables];
		for(short i=0;i<numberNewDescribeTables;i++)
			newDes[i]=Describes[i].toDescribe();
		
		databaseNew.lockDatabase(TimeManager.A_MINUTES_MILLISECOND);
		synchronized (managingDatabase.getLockDatabase(DBId).getLockTable(TableId)) {
			String pathClone = getCloneName(managingDatabase.pathTable(DBId, TableId));
			if(pathClone==null)
				throw new IOException("Can't not clone UserData -> UserData0-UserData99 is existed");
			
			databaseOld=new DBTable_1Primary(pathClone);
			///////////////////////////////////////////////////////////////////////////////clone to new File
			databaseNew.changingStructureColumn(newDes, databaseOld);
		}
		databaseNew.unlockDatabase();
		return API.resSuccess;
	}
	
	private String getCloneName(String currentPath) {
		for(int i=0;i<100;i++)
			if(new File(currentPath+i+BGBaseDatabase.DATA_EXTENSION).exists()==false && new File(currentPath+i+BGBaseDatabase.INDEX_EXTENSION).exists()==false)
				return currentPath+i;
		return null;
	}

	@Override public void onDestroy() {
		super.onDestroy();
		if(databaseOld!=null) {
			databaseOld.closeDatabase();
			
			try {
				Files.deleteIfExists(FileSystems.getDefault().getPath(databaseOld.getPath() + BGBaseDatabase.DATA_EXTENSION));
				Files.deleteIfExists(FileSystems.getDefault().getPath(databaseOld.getPath() + BGBaseDatabase.INDEX_EXTENSION));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void test_api_in_document() {
		super.test_api_in_document();
		
		
		
		
		int count = 0;
		BGColumn describe;
		BGColumn[] listRandom = new BGColumn[7];
		/////////////////////////////////////////////////////////////////////////////////////////
		describe = new BGColumn();
		describe.Type = DBDefine_DataType.BOOLEAN;
		describe.Size = 1;
		describe.ColumnName = "Cột một";
		describe.loadDefaultData(true);
		listRandom[count++] = describe;

		describe = new BGColumn();
		describe.Type = DBDefine_DataType.BYTE;
		describe.Size = 1;
		describe.ColumnName = "Cộ Số 2";
		describe.loadDefaultData((byte) 3);
		listRandom[count++] = describe;

		describe = new BGColumn();
		describe.Type = DBDefine_DataType.SHORT;
		describe.Size = 2;
		describe.ColumnName = "Column2";
		describe.loadDefaultData((short) 12);
		listRandom[count++] = describe;

		describe = new BGColumn();
		describe.Type = DBDefine_DataType.FLOAT;
		describe.Size = 4;
		describe.ColumnName = "Column3";
		describe.loadDefaultData(12.34f);
		listRandom[count++] = describe;

		describe = new BGColumn();
		describe.Type = DBDefine_DataType.INTEGER;
		describe.Indexing=1;
		describe.Size = 4;
		describe.ColumnName = "Column4";
		describe.loadDefaultData(4567);
		listRandom[count++] = describe;

		describe = new BGColumn();
		describe.Type = DBDefine_DataType.DOUBLE;
		describe.Size = 8;
		describe.ColumnName = "Column5";
		describe.loadDefaultData(1234.5678d);
		listRandom[count++] = describe;

		describe = new BGColumn();
		describe.Type = DBDefine_DataType.LONG;
		describe.Size = 8;
		describe.ColumnName = "Column6";
		describe.loadDefaultData(9999l);
		listRandom[count++] = describe;


		Describes = new DTO_Column[count];
		for (int i = 0; i < count; i++)
			Describes[i] = new DTO_Column(listRandom[i]);
	}
}
