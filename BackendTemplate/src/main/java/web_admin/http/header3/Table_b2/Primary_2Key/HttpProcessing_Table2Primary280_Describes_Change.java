package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.TimeManager;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.dto.DTO_Column;

@Http(api = API.SubTable_2Primary_Describes_Change)
@HttpDocument(id = 5280, Header = API.H3b2_Primary2)
public class HttpProcessing_Table2Primary280_Describes_Change extends Base2Primary {
	private transient DBTable_2Primary databaseOld=null;
	
	public DTO_Column[] Describes;

	@Override public MyRespone onProcess(DBTable_2Primary databaseNew,BackendSession session) throws IOException {
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
			
			databaseOld=new DBTable_2Primary(pathClone);
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
	
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		
		 int count=0;
        BGColumn describe;
        BGColumn[] listRandom = new BGColumn[4];
        /////////////////////////////////////////////////////////////////////////////////////////
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.IPV4;
        describe.Size = 4;
        describe.ColumnName = "IPV4";
        describe.loadDefaultData(123456);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.IPV6;
        describe.Size = 16;
        describe.ColumnName = "IPV6";
        describe.loadDefaultData(null);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.LONG;
        describe.Size = 8;
        describe.Indexing=BGColumn.INDEXING;
        describe.ColumnName = "Gold";
        describe.loadDefaultData((long)0);
        listRandom[count++]=describe;
        
        listRandom[count++] = randomDes();
        
        Describes=new DTO_Column[count];
        for(int i=0;i<count;i++)
        	Describes[i]=new DTO_Column(listRandom[i]);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	private BGColumn randomDes() {
		BGColumn describe = new BGColumn();
        describe.Type = -1;
        switch (random.nextInt(17)) {
            case 0:describe.Type = DBDefine_DataType.BOOLEAN;
            case 1:if (describe.Type == -1)describe.Type = DBDefine_DataType.BYTE;
            case 2:if (describe.Type == -1)describe.Type = DBDefine_DataType.STATUS;
            case 3:if (describe.Type == -1)describe.Type = DBDefine_DataType.PERMISSION;
            case 4:if (describe.Type == -1)describe.Type = DBDefine_DataType.AVARTAR;
                describe.Size = 1;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData((byte)random.nextInt());
                break;

            case 5:describe.Type = DBDefine_DataType.SHORT;
                describe.Size = 2;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData((short)random.nextInt());
                break;

            case 6:describe.Type = DBDefine_DataType.FLOAT;
                describe.Size = 4;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextFloat());
                break;

            case 7:describe.Type = DBDefine_DataType.IPV4;
            case 8:if (describe.Type == -1)describe.Type = DBDefine_DataType.INTEGER;
                describe.Size = 4;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextInt());
                break;

            case 9:describe.Type = DBDefine_DataType.DOUBLE;
                describe.Size = 8;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextDouble());
                break;

            case 10:describe.Type = DBDefine_DataType.USER_ID;
            case 11:if (describe.Type == -1)describe.Type = DBDefine_DataType.TIMEMILI;
            case 12:if (describe.Type == -1)describe.Type = DBDefine_DataType.LONG;
                describe.Size = 8;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextLong());
                break;

            case 13:describe.Type = DBDefine_DataType.STRING;
                describe.Size = 8 + random.nextInt(5);
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                describe.loadDefaultData(BGUtility.alphanumeric(3 + random.nextInt(2)));
                break;

            case 14:describe.Type = DBDefine_DataType.BYTE_ARRAY;
            case 15:if (describe.Type == -1)describe.Type = DBDefine_DataType.List;
                describe.Size = 5 + random.nextInt(5);
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                byte[] tmp = new byte[describe.Size-4];
                random.nextBytes(tmp);
                describe.loadDefaultData(tmp);
                break;

            case 16:describe.Type = DBDefine_DataType.IPV6;
                describe.Size = 16;
                describe.ColumnName = BGUtility.alphanumeric(8 + random.nextInt(3));
                byte[] ipv6 = new byte[describe.Size];
                random.nextBytes(ipv6);
                describe.loadDefaultData(ipv6);
                break;
                
            default:System.err.println("**********************************************************************===>");
                return null;
        }
        return describe;
	}
}
