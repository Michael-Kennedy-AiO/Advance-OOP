package server.onehit.S2_Login;

import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.MessageSending;
import backendgame.com.core.database.BGColumn;
import database_game.AccountType;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.StatusOnehit;
import server.onehit.BaseOnehit_AiO;
import web_admin.http.dto.DTO_Column;

public abstract class OnehitBaseLogin extends BaseOnehit_AiO{
	private DBGame_AccountLogin databaseAccount;
	private DBGame_UserData databaseUserData;
	
	public MessageSending onLogin(short DBId, long accessKey,String credential,byte accountType,String password,long emailCode){
		try {
			databaseAccount=new DBGame_AccountLogin(DBId);
			if(databaseAccount.isExisted()==false)
				return mgTableNotExist;
			databaseUserData=new DBGame_UserData(DBId);
			if(databaseUserData.islocked())
				return mgPending;
			
			if(databaseAccount.readLong(DBGame_AccountLogin.Offset_AccessKey)!=accessKey)
				return mgAccessKey;
			///////////////////////////////////////////////////////////////////////////////////////
			long userId=-1;
			long offsetCredential=databaseAccount.querryOffset(credential, accountType);
			if(accountType==AccountType.SystemAccount) {
				if(offsetCredential==-1)
					return mgAccountNotExist;
				
				if(databaseAccount.readPassword(offsetCredential).equals(password)==false)
					return mgPasswordIncorrect;
				userId=databaseAccount.readUserId(offsetCredential);
			}else if(accountType==AccountType.EmailCode) {
				if(offsetCredential==-1)
					return mgAccountNotExist;
				
				if(databaseAccount.readEmailCode(offsetCredential)!=emailCode)
					return mgEmailCodeIncorrect;
				userId=databaseAccount.readUserId(offsetCredential);
			}else {
				if(offsetCredential==-1)
					userId=databaseAccount.insertAccount(credential, accountType, null, databaseUserData);
				else
					userId=databaseAccount.readUserId(offsetCredential);
			}
			
			MessageSending messageSending=new MessageSending((short) 0);//No API CMD
			messageSending.writeByte(StatusOnehit.Success);
			messageSending.writeLong(userId);
			messageSending.writeByte(databaseUserData.getStatus(userId));
			messageSending.writeByte(databaseAccount.readByte(DBGame_AccountLogin.Offset_LogoutId));
			BGColumn[] list=databaseUserData.readBGColumn();
			if(list==null)
				messageSending.writeInt(0);
			else{
				int numberDes=list.length;
				messageSending.writeInt(numberDes);
				for(int i=0;i<numberDes;i++) 
					new DTO_Column(list[i]).writeMessage(messageSending);
			}
			messageSending.writeSpecialArray_WithoutLength(databaseUserData.readRow(userId));
			return messageSending;
		}catch (IOException e) {
			e.printStackTrace();
			MessageSending mgException=new MessageSending((short) 0);
			mgException.writeByte(StatusOnehit.EXCEPTION);
			mgException.writeString(BGUtility.getStringException(e));
			return mgException;
		}
	}
	
	@Override public void onDestroy() {
		if(databaseAccount!=null)
			databaseAccount.closeDatabase();
		if(databaseUserData!=null)
			databaseUserData.closeDatabase();
	}
}
