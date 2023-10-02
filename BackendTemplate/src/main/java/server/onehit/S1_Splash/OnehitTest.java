package server.onehit.S1_Splash;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OneHitProcessing;
import backendgame.com.core.OnehitApi;
import backendgame.com.core.server.BackendSession;
import server.config.StatusOnehit;

@OnehitApi(CMD=1)
public class OnehitTest extends OneHitProcessing {
	
	@Override public MessageSending onMessage(BackendSession session, MessageReceiving messageReceiving) {
		return new MessageSending((short) 0,StatusOnehit.Success);
	}
}
