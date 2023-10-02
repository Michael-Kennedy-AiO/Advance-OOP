package web_admin.http.header3.Table_a2.AccountQuerry;

import java.io.IOException;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.entity.BGAttribute;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;

@Http(api = API.ParsingRow_Update_UserData)
@HttpDocument(id = 3220, Header = API.H3a2_TableAccount_Querry)
public class HttpProcessing220_Update_UserData extends BaseDatabaseAccount {
	public long userId;
	public BGAttribute[] listWriter;
	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		if(listWriter==null || listWriter.length==0)
			return API.resInvalid;
		
		if(userId>=databaseAccount.countRow())
			return API.resOutOfRange;
		
		for(BGAttribute att:listWriter)
			att.initValue(databaseUserData, session.socket);
		
		databaseUserData.updateAttributesAtRowId(userId, listWriter);
		return API.resSuccess;
	}
	
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		userId = 1020;
		if(managingDatabase.checkAccountLogin(DBId)==false)
			return;
		
		DBGame_UserData databaseUserData=null;
		try {
			databaseUserData=new DBGame_UserData(DBId);
			if(databaseUserData.countRow()>0) {
				BGColumn[] list = databaseUserData.readBGColumn();
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
		if(databaseUserData!=null)
			databaseUserData.closeDatabase();
	}
}
