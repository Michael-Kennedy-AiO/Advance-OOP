package server.onehit.S2_Login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonGoogle {
	public String email;
	public String name;
	public String linkIcon;
	
	private static final String const_sub="sub";
	private static final String const_email="email";
	private static final String const_picture="picture";
	private static final String const_name="name";
	public JsonGoogle(String TOKEN) {
		long l=System.currentTimeMillis();
		BufferedReader reader = null;
		email=null;
		name=null;
		linkIcon=null;
		try {
            URL url = new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token="+TOKEN);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1 && System.currentTimeMillis()-l<2686)
                buffer.append(chars, 0, read);
            
            JsonObject json = new Gson().fromJson(buffer.toString(), JsonObject.class);
            if(json.has(const_email))
            	email = json.get(const_email).getAsString();
            else if(json.has(const_sub))
            	email = json.get(const_sub).getAsString();
            
            if(json.has(const_picture))
            	linkIcon = json.get(const_picture).getAsString();
            
            if(json.has(const_name))
            	name = json.get(const_name).getAsString();
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
		System.out.println("***********************************************************************");
		System.out.println("gmail : "+email);
		System.out.println("nameShow : "+name);
		System.out.println("linkIcon : "+linkIcon);
	}
}
