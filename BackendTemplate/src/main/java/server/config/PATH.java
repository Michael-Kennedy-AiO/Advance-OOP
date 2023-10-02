package server.config;

import java.io.File;

public class PATH {
	public static final String DATABASE_FOLDER = "./BackendGame";
	public static final String TEST_FOLDER = "./target";
	
	public static final String UserData = "User.data";
	public static final String StringData = "String.data";
	public static final String StringIndex = "String.index";
	
	
	
//	public static final String SubTable = "SubTable";
	
	public static String dbStringName(short DBId) {return PATH.DATABASE_FOLDER+"/"+DBId+"/string";}
	
	
	
	
	
	private static final String PATH_TAM = "/Users/vohoangtam/Documents/GitHub/RichardLibrray/BackendTemplate/FrontEnd";
	private static final String PATH_Richard = "/private/var/Devtools/Github/RichardLibrray/BackendTemplate/FrontEnd";

	public static String getFrontendPath() {
		if (System.getProperty("os.name").toLowerCase().contains("win"))
			return "C:\\Devtools\\Github\\RichardLibrray\\BackendTemplate\\FrontEnd";
		if (new File(PATH_TAM).exists())
			return PATH_TAM;
		if (new File(PATH_Richard).exists())
			return PATH_Richard;
		return null;
	}
}
