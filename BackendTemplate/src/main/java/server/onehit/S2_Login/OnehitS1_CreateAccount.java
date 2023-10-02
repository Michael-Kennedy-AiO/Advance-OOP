package server.onehit.S2_Login;

import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OnehitApi;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import database_game.AccountType;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.CMD_ONEHIT;
import server.config.StatusOnehit;
import server.onehit.BaseOnehit_AiO;
import web_admin.http.dto.DTO_Column;

@OnehitApi(CMD=CMD_ONEHIT.LGScreen_RegisterAccount)
public class OnehitS1_CreateAccount extends BaseOnehit_AiO {
	
	private DBGame_AccountLogin databaseAccount;
	private DBGame_UserData databaseUserData;
	

	@Override
	public MessageSending onMessage(BackendSession session, MessageReceiving messageReceiving) {
		short DBId=messageReceiving.readShort();
		long accessKey=messageReceiving.readLong();
		String userName=messageReceiving.readString();
		String password=messageReceiving.readString();
		///////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgParamsInvalid;
		
		if(password.getBytes().length>DBGame_AccountLogin.PASSWORD_LENGTH)
			return mgVariableInvalid;
		///////////////////////////////////////////////////////////////////////////////////////
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
			long offsetCredential=databaseAccount.querryOffset(userName, AccountType.SystemAccount);
			if(offsetCredential==-1) {
				long userId=databaseAccount.insertAccount(userName, AccountType.SystemAccount, password, databaseUserData);
				
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
			}else
				return mgAccountExisted;
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
