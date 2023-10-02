package server;

import java.net.Socket;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.ThreadPool;
import backendgame.com.core.log.ILog_Connecting;
import backendgame.com.core.realtime.SceneChannelRealtime;
import backendgame.com.core.realtime.ServerBG_WebSocket;

public class Test {

	public static void main(String[] args) {
		ThreadPool.gI();
		ServerBG_WebSocket server = new ServerBG_WebSocket("ServerBattle",1234) {

			@Override
			public SceneChannelRealtime onBeginChannel() {
				// TODO Auto-generated method stub
				return new SceneChannelRealtime() {
					
					@Override
					public boolean process(MessageReceiving messageReceiving) {
						switch (messageReceiving.CMD) {
							case 123:
								System.out.println("======>"+sessionId);
								System.out.println(messageReceiving.readByte());
								System.out.println(messageReceiving.readShort());
								System.out.println(messageReceiving.readInt());
								System.out.println(messageReceiving.readLong());
								System.out.println(messageReceiving.readString());
								System.out.println(messageReceiving.readFloat());
								System.out.println(messageReceiving.readDouble());
								
								messageSending = new MessageSending((short) 678);
								messageSending.writeByte((byte) 123);
								messageSending.writeShort((short) 456);
								messageSending.writeInt(1234);
								messageSending.writeLong(3454);
								messageSending.writeString("Đức Trí");
								messageSending.writeFloat((float) 33.44);
								messageSending.writeDouble(55.66);
								sendWait(messageSending);
								return true;
							case 456:

								return true;
							default:
								return false;
						}
					}
					
					@Override
					public void onCoreInit() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					protected void onCoreDestroy() {
						// TODO Auto-generated method stub
						
					}
				};
			}

			@Override
			public void onServerBusy() {
				// TODO Auto-generated method stub
				
			}
			
		};
		server.onConnecting = new ILog_Connecting() {
			
			@Override
			public void showLog(Socket socket) {
				System.out.println("Có kết nối từ : "+socket.getRemoteSocketAddress());
			}
		};
		server.start();
	}

}
