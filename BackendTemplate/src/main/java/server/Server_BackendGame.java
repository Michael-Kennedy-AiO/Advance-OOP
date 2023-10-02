package server;

import java.awt.Desktop;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import backendgame.com.core.BGUtility;
import backendgame.com.core.OneHitProcessing;
import backendgame.com.core.OnehitApi;
import backendgame.com.core.ThreadPool;
import backendgame.com.core.TimeManager;
import backendgame.com.core.log.ILogOnehit_CMD_NotExist;
import backendgame.com.core.log.ILogOnehit_OnMessage;
import backendgame.com.core.server.ServerBG_EncryptingOnehit;
import backendgame.com.core.server.http.ServerBG_Http;
import server.config.CMD_ONEHIT;
import server.config.PATH;
import server.onehit.BaseOnehit_AiO;
import web_admin.API;
import web_admin.HttpDocumentation;
import web_admin.HttpTree;

public class Server_BackendGame {
	public static ServerBG_EncryptingOnehit serverOnehit;
	public static ServerBG_Http serverWebAdmin;
	
	

	
	
	public static void main(String[] args) {long l = System.currentTimeMillis();
		System.out.println("BackendTemplate will improve the performance in each next version.");
		try {BGUtility.traceIpAdress();}catch (Exception e) {e.printStackTrace();}
		System.out.println("Begin Server at "+TimeManager.gI().getStringTime());
		ThreadPool.gI();
		
		serverOnehit = new ServerBG_EncryptingOnehit("Onehit", "GameServer", 1989);
//		serverOnehit.setProcess(OnehitTest.class);
		serverOnehit.set_all_process_in_packagename(Server_BackendGame.class,"server.onehit");
		BaseOnehit_AiO.setup();
		serverOnehit.set_CMD_NotFound(new ILogOnehit_CMD_NotExist() {
			@Override public void showLog(Socket _socket, short _CMD, int lengthReceive) {
				String strCMD = "CMD("+_CMD+") ";
				Field[] fields=CMD_ONEHIT.class.getFields();
				for(Field f:fields)
					try {
						if(_CMD==f.getShort(null)) {
							strCMD = f.getName();
							break;
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				System.err.println(_socket.getInetAddress().getHostAddress() + " ➝ " + serverOnehit.getServerName() + " " + strCMD + " not exist ➝ " + lengthReceive + " byte");				
			}
		});
		
		serverOnehit.setOnMessage(new ILogOnehit_OnMessage() {
			@Override public void showLog(Socket _socket, OneHitProcessing process, int lengthClient, int lengthServer) {
				String strCMD = "CMD("+process.getClass().getAnnotation(OnehitApi.class).CMD()+")";
				Field[] fields = CMD_ONEHIT.class.getFields();
				for(Field f:fields)
					try {
						if(process.getClass().getAnnotation(OnehitApi.class).CMD()==f.getShort(null)) {
							strCMD = f.getName();
							break;
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				System.out.println("Onehit(" + process.getClass().getSimpleName() + ")	"+strCMD+" : request(" + lengthClient + "b) - respone(" + lengthServer + "b) ➝ " + _socket.getInetAddress().getHostAddress() + "➝" + serverOnehit.getServerName());				
			}
		});
		serverOnehit.start();
		
		serverWebAdmin = new ServerBG_Http("aaa", "Web Admin", 8096);//Web Admin API run at port : 8096		if(System.getProperty("os.name").toLowerCase().contains("win"))
		serverWebAdmin.setFrontEnd(PATH.getFrontendPath(), HttpTree.class);
		
		
//		serverWebAdmin.debug(true);
//		serverWebAdmin.server.onConnecting=new ILog_Connecting() {@Override public void showLog(Socket _socket) {System.out.println("************************************* → New connection : "+_socket.getRemoteSocketAddress());}};
		serverWebAdmin.setDescrip("onehit", serverOnehit).start();
		serverWebAdmin.setDocumentation("doc",HttpDocumentation.class);
		API.setupApi();
		serverWebAdmin.set_all_process_in_packagename(Server_BackendGame.class,"web_admin");
		
		ArrayList<String> listIp=BGUtility.getIp();
		for(String ip:listIp)
			if(ip.contains(".")) {
				System.out.println("Document url → http://"+ip+":"+serverWebAdmin.getPort()+"/"+serverWebAdmin.urlDoc);
			    try {
			    	if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
			    		Desktop.getDesktop().browse(new URI("http://localhost:"+serverWebAdmin.getPort()+"/"+serverWebAdmin.urlDoc));
			    	break;
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
					break;
				}
			}
	    
	    System.out.println("Time setup : "+(System.currentTimeMillis()-l)+" ms\n");
	}
	
	
//	public static final String getName() {
//		try {
//			String s = null;
//			while (s == null || s.equals("null"))
////				s = Jsoup.connect("https://www.behindthename.com/random/random.php?number=4&sets=1&gender=both&surname=&usage_eng=1").get().getElementsByClass("random-results").get(0).text().replaceAll(" ", "").replaceAll("'", "").replaceAll("`", "");
//				s = Jsoup.connect("https://www.behindthename.com/random/random.php?number=4&sets=1&gender=both&surname=&usage_eng=1").get().getElementsByClass("random-results").get(0).text();
//			return s;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
