package web_admin.http.header3.Table_a1.AccountConfig;

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
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;
import web_admin.http.dto.DTO_Column;

@Http(api = API.TableAccount_Describes_Change)
@HttpDocument(id = 3180, Header = API.H3a1_TableAccount)
public class HttpAccountLogin180_Describes_Change extends BaseHttpAdminWeb_SignedIn {
	private transient DBGame_UserData dbUserData=null;
	private transient DBGame_UserData dbTmp=null;
	
	public DTO_Column[] Describes;
	public short DBId;

	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		
		if(Describes==null || Describes.length==0)
			return API.resInvalidParameters;
		
		int numberNewDescribeTables = Describes.length;
		BGColumn[] newDes = new BGColumn[numberNewDescribeTables];
		for(short i=0;i<numberNewDescribeTables;i++)
			newDes[i]=Describes[i].toDescribe();
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		synchronized (managingDatabase.getLockDatabase(DBId)) {
			try {
				dbUserData=new DBGame_UserData(DBId);
				if(dbUserData.islocked())
					return API.resPending;
				
				dbUserData.lockDatabase(TimeManager.A_MINUTES_MILLISECOND);
				
				String pathClone = getCloneName(dbUserData.getPath());
				if(pathClone==null)
					throw new IOException("Can't not clone UserData -> UserData0-UserData99 is existed");
				else{
					dbTmp=new DBGame_UserData(DBId, pathClone);
					dbUserData.changingStructureColumn(newDes, dbTmp);
					dbUserData.unlockDatabase();
					return API.resSuccess;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return API.resSuccess;
	}
	
	@Override public void onDestroy() {
		if(dbUserData!=null)
			dbUserData.closeDatabase();
		if(dbTmp!=null) {
			dbTmp.closeDatabase();
			try {
				Files.deleteIfExists(FileSystems.getDefault().getPath(dbTmp.getPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getCloneName(String currentPath) {
		for(int i=0;i<100;i++)
			if(new File(currentPath+i).exists()==false)
				return currentPath+i;
		return null;
	}

	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		
		DBId=1;
//		DBDescribe[] list=new DBDescribe[3+random.nextInt(5)];
//		for(int i=0;i<list.length;i++)
//			list[i]=randomDes();
		
		Describes = new DTO_Column[3+random.nextInt(5)];
		for(int i=0;i<Describes.length;i++)
			Describes[i]=new DTO_Column(randomDes());
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
