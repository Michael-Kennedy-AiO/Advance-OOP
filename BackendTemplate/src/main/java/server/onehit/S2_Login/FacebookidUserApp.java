package server.onehit.S2_Login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class FacebookidUserApp {
//	https://graph.facebook.com/698126327276619/picture
//	https://graph.facebook.com/{facebookId}/picture?type=large&width=720&height=720
	private String token;
	public String name;
	public long facebookid;
	public String token_for_business;
	
//	public PlayerInfo2_Facebook player_info;
	public FacebookidUserApp(String TOKEN) {
		this.token = TOKEN;
		name=null;
		facebookid=0;
		token_for_business=null;
		long l=System.currentTimeMillis();
		BufferedReader reader = null;
		try {
//            URL url = new URL("https://graph.facebook.com/me?access_token="+TOKEN);
            URL url = new URL("https://graph.facebook.com/me?fields=id,name,token_for_business&access_token="+TOKEN);
//            URL url = new URL("https://graph.facebook.com/me?fields=id,name&access_token="+TOKEN);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1 && System.currentTimeMillis()-l<2686)
                buffer.append(chars, 0, read);
            
            
            JsonObject json = new Gson().fromJson(buffer.toString(), JsonObject.class);
            facebookid = json.get("id").getAsLong();
            name = json.get("name").getAsString();
            token_for_business = json.get("token_for_business").getAsString();

        } catch (Exception e) {
        	e.printStackTrace();
		} finally {
            if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
	}
	
	public final void trace() {
		if(facebookid==0)
			System.out.println("Không lấy được facebookid cho token : "+token);
		else
			System.out.println(token_for_business+"	➜ "+facebookid+"	➜ "+name);
	}
}
