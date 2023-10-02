package web_admin.http.header3.Table;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.TimeManager;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.PATH;
import web_admin.API;
import web_admin.AdminToken;
import web_admin.DES;
import web_admin.MyRespone;
import web_admin.http.BaseHttpAdminWeb_SignedIn;
import web_admin.http.dto.DTO_Column;

@Http(api = API.Database_AccountLogin_Enable)
@HttpDocument(id = 3000, Header = API.H3_Database, Description = DES.Enable_AccountLogin)
public class HttpDatabase000_Enable_AccountLogin extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public DTO_Column[] Describes;
	
	
	private transient DBGame_AccountLogin databaseAccount;
	private transient DBGame_UserData game_UserData;
	@Override public void onDestroy() {if(databaseAccount!=null)databaseAccount.closeDatabase();if(game_UserData!=null)game_UserData.closeDatabase();}
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		
		if(new File(PATH.DATABASE_FOLDER + "/" + DBId).exists()==false)
			return API.resDatabaseNotExist;
		///////////////////////////////////////////////////////////////////////////////////////////
		BGColumn[] list=null;
		if(Describes!=null && Describes.length>0) {
			int numberDes = Describes.length;
			list=new BGColumn[numberDes];
			for(int i=0;i<numberDes;i++)
				try {
					list[i]=Describes[i].toDescribe();
					if(list[i].validate()==false)
						return new MyRespone(MyRespone.STATUS_Invalid);
				} catch (IOException e) {
					e.printStackTrace();
					return new MyRespone(MyRespone.STATUS_Invalid,BGUtility.getStringException(e));
				}
			
		}
		///////////////////////////////////////////////////////////////////////////////////////////
		try {
			databaseAccount = new DBGame_AccountLogin(DBId);
			game_UserData = new DBGame_UserData(DBId);
			if(databaseAccount.isExisted())
				return API.resExisted;
			databaseAccount.initNewDatabase();
			game_UserData.initNewDatabase(list);
			return API.resSuccess;
		}catch (Exception e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Failure,BGUtility.getStringException(e));
		}
	}


	
	
	
	@Override public void test_api_in_document() {
		AdminToken adminToken = new AdminToken();
		adminToken.username = "admin";
		adminToken.timeOut = System.currentTimeMillis() + TimeManager.Eight_HOUR_MILLISECOND;
		AdminToken=adminToken.toHashString();
		DBId=1;
		
		
        int count=0;
        BGColumn describe;
        BGColumn[] listRandom = new BGColumn[4];
        /////////////////////////////////////////////////////////////////////////////////////////
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.STRING;
        describe.Size = 12;
        describe.ColumnName = "NameShow";
        describe.loadDefaultData("User can change");
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BYTE;
        describe.Size = 1;
        describe.ColumnName = "AvatarId";
        describe.loadDefaultData((byte)3);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BYTE;
        describe.Size = 1;
        describe.ColumnName = "DeviceType";
        describe.loadDefaultData((short)99);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.LONG;
        describe.Size = 8;
        describe.ColumnName = "Gold";
        describe.loadDefaultData((long)1024);
        listRandom[count++]=describe;
        
       
        
        Describes=new DTO_Column[count];
        for(int i=0;i<count;i++)
        	Describes[i]=new DTO_Column(listRandom[i]);
	}
}
