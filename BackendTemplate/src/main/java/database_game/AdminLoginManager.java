package database_game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import server.config.PATH;

public class AdminLoginManager {
	public static final String FILE = "/AdminAccount.json";
	
	public HashMap<String, String> mapAccount;
	private Path path;
	private AdminLoginManager() {
		path = Paths.get(PATH.DATABASE_FOLDER + FILE);
		mapAccount = new HashMap<>();
	}
	
	
	public void updateAdminFile() {
		try {
			Files.write(path, new GsonBuilder().setPrettyPrinting().create().toJson(mapAccount).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	}
	
//	public String getPasswordAccount(String username) {
//		Map<String, Object> mapAccount = loadAdminAccount();
//		if(mapAccount.containsKey(username))
//			return (String) mapAccount.get(username);
//		else
//			return null;
//	}
//	public void deleteAccount(String username) {
//		Map<String, Object> mapAccount = loadAdminAccount();
//		mapAccount.remove(username);
//		writeAdminFile(mapAccount);
//	}
//	public void setAccount(String username,String password) {
//		Map<String, Object> mapAccount = loadAdminAccount();
//		mapAccount.put(username, password);
//		writeAdminFile(mapAccount);
//	}
	
	
	
	
	private static AdminLoginManager instance=null;
	public static final AdminLoginManager gI() {
		if(instance==null) {
			instance=new AdminLoginManager();
			
			try {
				instance.mapAccount = new Gson().fromJson(new String(Files.readAllBytes(instance.path),StandardCharsets.UTF_8), new TypeToken<HashMap<String, String>>(){}.getType());
			} catch (JsonSyntaxException | IOException e) {
//				e.printStackTrace();
				instance.mapAccount=new HashMap<>();
				instance.mapAccount.put("admin", "admin");
				instance.updateAdminFile();
			}
			
		}
		return instance;
	}
}
