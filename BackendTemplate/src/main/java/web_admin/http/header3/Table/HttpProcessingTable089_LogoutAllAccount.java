package web_admin.http.header3.Table;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.http.BaseDatabaseAccount;

@Http(api = API.TableAccount_Config_LogoutAllAccount)
@HttpDocument(id = 3089, Header = API.H3_Database)
public class HttpProcessingTable089_LogoutAllAccount extends BaseDatabaseAccount {
	
	private transient RandomAccessFile[] listRF;
	@Override public MyRespone onProcess(BackendSession session, DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData) throws IOException {
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_LogoutId);
		byte logoutId = (byte) (databaseAccount.rfData.readByte()+1);
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_LogoutId);
		databaseAccount.rfData.writeByte(logoutId);
		
		short numberTable = managingDatabase.countTable(DBId);
		if(numberTable>0) {
			listRF=new RandomAccessFile[numberTable];
			for(short i=0;i<numberTable;i++)
				if(new File(managingDatabase.pathTable(DBId, i)+BGBaseDatabase.DATA_EXTENSION).exists()) {
					listRF[i]=new RandomAccessFile(managingDatabase.pathTable(DBId, i)+BGBaseDatabase.DATA_EXTENSION, "rw");
					listRF[i].seek(BGBaseDatabase.Offset_LogoutId);
					listRF[i].writeByte(logoutId);
				}
		}
		return API.resSuccess;
	}

	
	@Override public void onDestroy() {
		super.onDestroy();
		if(listRF!=null)
			for(int i=0;i<listRF.length;i++)
				if(listRF[i]!=null)
					try {listRF[i].close();} catch (IOException e) {e.printStackTrace();}
		
	}
}
