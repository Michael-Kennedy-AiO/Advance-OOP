package web_admin.http.header3.Table_a1.AccountConfig;

import java.io.IOException;
import java.util.HashMap;

import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.StringConstant;
import web_admin.http.BaseDatabaseAccount;
import web_admin.http.dto.DTO_Column;

@Http(api = API.TableAccount_Get_TableInfo)
@HttpDocument(id = 3100, Header = API.H3a1_TableAccount)
public class HttpAccountLogin100_Get_TableInfo extends BaseDatabaseAccount {
	
	
	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		HashMap<String, Object> databaseInfo = new HashMap<>();
		
		databaseInfo.put(StringConstant.TimeCreate, timeManager.getStringTime(databaseAccount.readLong(DBGame_AccountLogin.Offset_TimeCreate)));
		databaseInfo.put(StringConstant.AccessKey, databaseAccount.readLong(DBGame_AccountLogin.Offset_AccessKey));
		databaseInfo.put(StringConstant.ReadKey, databaseAccount.readLong(DBGame_AccountLogin.Offset_ReadKey));
		databaseInfo.put(StringConstant.WriteKey, databaseAccount.readLong(DBGame_AccountLogin.Offset_WriteKey));
		databaseInfo.put(StringConstant.LogoutId, databaseAccount.readByte(DBGame_AccountLogin.Offset_LogoutId));
		databaseInfo.put(StringConstant.Version, databaseAccount.readLong(DBGame_AccountLogin.Offset_Version));
		databaseInfo.put(StringConstant.TokenLifeTime, databaseAccount.readLong(DBGame_AccountLogin.Offset_Token_LifeTime));
		databaseInfo.put(StringConstant.MailService, databaseAccount.readMailService());
		
		HashMap<String, Boolean> mapPermission=new HashMap<>();
		mapPermission.put(StringConstant.Device, databaseAccount.readByte(DBGame_AccountLogin.Offset_Permission_Device)!=0);
		mapPermission.put(StringConstant.SystemAccount, databaseAccount.readByte(DBGame_AccountLogin.Offset_Permission_SystemAccount)!=0);
		mapPermission.put(StringConstant.Facebook, databaseAccount.readByte(DBGame_AccountLogin.Offset_Permission_Facebook)!=0);
		mapPermission.put(StringConstant.Google, databaseAccount.readByte(DBGame_AccountLogin.Offset_Permission_Google)!=0);
		mapPermission.put(StringConstant.AdsId, databaseAccount.readByte(DBGame_AccountLogin.Offset_Permission_AdsId)!=0);
		mapPermission.put(StringConstant.Apple, databaseAccount.readByte(DBGame_AccountLogin.Offset_Permission_Apple)!=0);
		mapPermission.put(StringConstant.EmailCode, databaseAccount.readByte(DBGame_AccountLogin.Offset_Permission_EmailCode)!=0);
		databaseInfo.put(StringConstant.Permission, mapPermission);
		
		HashMap<String, Object> mapTrackingCreate=new HashMap<>();
		mapTrackingCreate.put(StringConstant.Yesterday, databaseAccount.readInt(DBGame_AccountLogin.Offset_TrackingCreate_Yesterday));
		mapTrackingCreate.put(StringConstant.Today, databaseAccount.readInt(DBGame_AccountLogin.Offset_TrackingCreate_Today));
		mapTrackingCreate.put(StringConstant.ThisMonth, databaseAccount.readInt(DBGame_AccountLogin.Offset_TrackingCreate_ThisMonth));
		mapTrackingCreate.put(StringConstant.AllTime, databaseAccount.readLong(DBGame_AccountLogin.Offset_TrackingCreate_AllTime));
		databaseInfo.put(StringConstant.Create, mapTrackingCreate);
		
		HashMap<String, Object> mapTrackingLogin=new HashMap<>();
		mapTrackingLogin.put(StringConstant.Yesterday, databaseAccount.readInt(DBGame_AccountLogin.Offset_TrackingLogin_Yesterday));
		mapTrackingLogin.put(StringConstant.Today, databaseAccount.readInt(DBGame_AccountLogin.Offset_TrackingLogin_Today));
		mapTrackingLogin.put(StringConstant.ThisMonth, databaseAccount.readInt(DBGame_AccountLogin.Offset_TrackingLogin_ThisMonth));
		mapTrackingLogin.put(StringConstant.AllTime, databaseAccount.readLong(DBGame_AccountLogin.Offset_TrackingLogin_AllTime));
		databaseInfo.put(StringConstant.Login, mapTrackingLogin);
		
		
		BGColumn[] list = databaseUserData.readBGColumn();
		if(list==null)
			databaseInfo.put(StringConstant.Describes, null);
		else {
			int numberColumn = list.length;
			DTO_Column[] listDes = new DTO_Column[numberColumn];
			for(int i=0;i<numberColumn;i++)
				listDes[i]=new DTO_Column(list[i]);
			databaseInfo.put(StringConstant.Describes, listDes);
		}
		databaseInfo.put(StringConstant.TotalRow, databaseAccount.countRow());
		
		return new MyRespone(MyRespone.STATUS_Success).setData(databaseInfo);
	}
	
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		
		DBId=1;
	}
}
