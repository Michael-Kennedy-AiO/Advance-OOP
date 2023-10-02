package web_admin.http.header3.Table_a2.AccountQuerry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import backendgame.com.core.BGUtility;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.PATH;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.StringConstant;
import web_admin.http.BaseHttpAdminWeb_SignedIn;
import web_admin.http.dto.DTO_Column;

public abstract class BaseQuerryAccount extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	private transient DBGame_AccountLogin databaseAccount;
	private transient DBGame_UserData databaseUserData;
	
	public abstract long[] onGetUserId(BackendSession session,DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException;
	@Override public MyRespone onPOST(BackendSession session) {
		if(DBId<0)
			return API.resInvalidParameters;
		
		if(new File(PATH.DATABASE_FOLDER+"/"+DBId).exists()==false)
			return API.resDatabaseNotExist;
		
		try {
			databaseAccount=new DBGame_AccountLogin(DBId);
			if(databaseAccount.isExisted()==false)
				return new MyRespone(MyRespone.STATUS_TableNotExist,"Table AccountLogin is not enable");
			databaseUserData=new DBGame_UserData(DBId);

			if(databaseUserData.islocked())
				return API.resPending;
			
			HashMap<String, Object> mapData=new HashMap<>();
			mapData.put(StringConstant.TotalRow, databaseUserData.countRow());
			
			BGColumn[] list=databaseUserData.readBGColumn();
			DTO_Column[] listDescribes=null;
			if(list!=null) {
				int number=list.length;
				listDescribes=new DTO_Column[number];
				for(int i=0;i<number;i++)
					listDescribes[i]=new DTO_Column(list[i]);
			}
			mapData.put(StringConstant.Describes, listDescribes);
			
			if(databaseAccount.countRow()==0)
				mapData.put(StringConstant.DataRow, null);
			else{
				long[] listUserId = onGetUserId(session,databaseAccount, databaseUserData);
				if(listUserId==null)
					return API.resInvalid;
				if(listUserId.length==0)
					return API.resNotFound;
				
				
				ArrayList<ArrayList<Object>> listDataRow = new ArrayList<>();
				for(long userId:listUserId) {
					if(userId<databaseAccount.countRow())
						listDataRow.add(databaseAccount.loadRow(userId, databaseUserData, list));
				}
				mapData.put(StringConstant.DataRow, listDataRow);
			}
			
			return new MyRespone(MyRespone.STATUS_Success).setData(mapData);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception,BGUtility.getStringException(e));
		}
	}
	
	@Override public void onDestroy() {
		if(databaseAccount!=null)
			databaseAccount.closeDatabase();
		if(databaseUserData!=null)
			databaseUserData.closeDatabase();
	}
}
