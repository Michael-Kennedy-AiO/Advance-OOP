package server.onehit.S2_Login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OnehitApi;
import backendgame.com.core.server.BackendSession;
import database_game.AccountType;
import server.config.CMD_ONEHIT;

@OnehitApi(CMD=CMD_ONEHIT.LGScreen_LoginAccount_4_Email)
public class OnehitS1_Login4_AdsId extends OnehitBaseLogin {
	
	
	@Override public MessageSending onMessage(BackendSession session, MessageReceiving messageReceiving) {
		short DBId=messageReceiving.readShort();
		long accessKey=messageReceiving.readLong();
		String adsId=messageReceiving.readString();
		
		if(messageReceiving.validate()==false)
			return mgParamsInvalid;
		
		return onLogin(DBId, accessKey, adsId, AccountType.AdsId, null, 0);
	}

	
	
}
