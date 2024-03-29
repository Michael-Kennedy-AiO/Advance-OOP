package server.onehit.S2_Login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OnehitApi;
import backendgame.com.core.server.BackendSession;
import database_game.AccountType;
import server.config.CMD_ONEHIT;

@OnehitApi(CMD=CMD_ONEHIT.LGScreen_LoginAccount_3_Google)
public class OnehitS1_Login3_Google extends OnehitBaseLogin {
	
	@Override public MessageSending onMessage(BackendSession session, MessageReceiving messageReceiving) {
		short DBId=messageReceiving.readShort();
		long accessKey=messageReceiving.readLong();
		String tokenGoogle=messageReceiving.readString();
		
		if(messageReceiving.validate()==false)
			return mgParamsInvalid;
		
		JsonGoogle google = new JsonGoogle(tokenGoogle);
		if(google.email==null)
			return mgTokenError;
		
		return onLogin(DBId, accessKey, google.email, AccountType.Google, null, 0);
	}

	
	
	
}
