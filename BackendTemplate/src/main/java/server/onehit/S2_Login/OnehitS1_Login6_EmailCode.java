package server.onehit.S2_Login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OnehitApi;
import backendgame.com.core.server.BackendSession;
import database_game.AccountType;
import server.config.CMD_ONEHIT;

@OnehitApi(CMD=CMD_ONEHIT.LGScreen_LoginAccount_6_PhoneNumber)
public class OnehitS1_Login6_EmailCode extends OnehitBaseLogin {
	
	@Override public MessageSending onMessage(BackendSession session, MessageReceiving messageReceiving) {
		short DBId=messageReceiving.readShort();
		long accessKey=messageReceiving.readLong();
		String userName=messageReceiving.readString();
		long emailCode=messageReceiving.readLong();

		if(messageReceiving.validate()==false)
			return mgParamsInvalid;
		
		return onLogin(DBId, accessKey, userName, AccountType.EmailCode, null, emailCode);
	}

	
	
	
}
