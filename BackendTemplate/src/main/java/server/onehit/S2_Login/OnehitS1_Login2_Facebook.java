package server.onehit.S2_Login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OnehitApi;
import backendgame.com.core.server.BackendSession;
import database_game.AccountType;
import server.config.CMD_ONEHIT;

@OnehitApi(CMD=CMD_ONEHIT.LGScreen_LoginAccount_2_Facebook)
public class OnehitS1_Login2_Facebook extends OnehitBaseLogin {
	
	@Override public MessageSending onMessage(BackendSession session, MessageReceiving messageReceiving) {
		short DBId=messageReceiving.readShort();
		long accessKey=messageReceiving.readLong();
		String tokenFacebook=messageReceiving.readString();
		
		if(messageReceiving.validate()==false)
			return mgParamsInvalid;
		
		FacebookidUserApp facebook = new FacebookidUserApp(tokenFacebook);
		if(facebook.token_for_business==null)
			return mgTokenError;
		
		return onLogin(DBId, accessKey, facebook.token_for_business, AccountType.Facebook, null, 0);
	}

	
	
	
}
