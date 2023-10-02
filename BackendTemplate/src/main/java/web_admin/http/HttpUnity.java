package web_admin.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.BGHttp_Get;
import backendgame.com.core.server.http.Http;
import database_game.DatabaseManager;
import database_game.entity.DatabaseInfo;
import database_game.table.DBGame_AccountLogin;
import server.config.CMD_ONEHIT;
import server.config.CMD_REALTIME;
import server.config.PATH;
import server.config.StatusOnehit;

@Http(api = "/Unity.zip")
public class HttpUnity extends BGHttp_Get {
	
	public ArrayList<String> getCMD(Class<?> cls) {
		ArrayList<String> list=new ArrayList<>();
		list.add("public class "+cls.getSimpleName()+"{");
		Field[] listField = cls.getDeclaredFields();
		for(Field f:listField)
			try {
				list.add("	public const short "+f.getName() + "	= "+f.get(null)+";");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		list.add("	/////////////////////////////////////////////////");
		list.add("	/////////////////////////////////////////////////");
		list.add("	/////////////////////////////////////////////////");
		list.add("	public static string getName(MessageSending messageSending){return getName(messageSending.getCMD());}");
		list.add("	public static string getName(MessageReceiving messageSending){return getName(messageSending.getCMD());}");
		list.add("	public static string getName(short cmd){");
		list.add("		switch(cmd){");
		for(Field f:listField)
			try {
				list.add("			case "+f.get(null)+":return \""+f.getName()+"\";");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		list.add("			default:return \"CMD(\"+cmd+\")\";");
		list.add("		}");
		list.add("	}");
		list.add("}");
		return list;
	}
	public ArrayList<String> getOnehitStatus(Class<?> cls) {
		ArrayList<String> list=new ArrayList<>();
		list.add("public class "+cls.getSimpleName()+"{");
		
		Field[] listField = cls.getDeclaredFields();
		for(Field f:listField)
			try {
				list.add("	public const sbyte "+f.getName() + "	= "+f.get(null)+";");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		list.add("	/////////////////////////////////////////////////");
		list.add("	/////////////////////////////////////////////////");
		list.add("	/////////////////////////////////////////////////");
		list.add("	public static string getString(sbyte status){");
		list.add("		switch(status){");
		for(Field f:listField)
			try {
				list.add("			case "+f.get(null)+":return \""+f.getName()+"\";");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		list.add("			default:return \"Status(\"+status+\")\";");
		list.add("		}");
		list.add("	}");
		
		list.add("}");
		return list;
	}
	
	
	
	public ArrayList<String> getDBTable() throws IOException{
		RandomAccessFile rf;
		long AccessKey;
		long ReadKey;
		long WriteKey;
		
		ArrayList<String> list=new ArrayList<>();
		list.add("using System.Collections.Generic;");
		list.add("public class Database{");
		list.add("	public List<BGDB_Database> listDatabase;");
		list.add("	private Database(){");
		list.add("		BGDB_Database database;");
		list.add("		BGDB_Table1Primary table1Primary;");
		list.add("		BGDB_Table2Primary table2Primary;");
		list.add("		BGDB_TableRow tableRow;");
		list.add("		BGDB_TableLineNode tableLineNode;");
		list.add("		BGDB_TableLeaderboard tableLeaderboard;");
		list.add("		listDatabase=new List<BGDB_Database>();");
		ArrayList<DatabaseInfo> listDatabase = DatabaseManager.gI().readListDatabaseInfo();
		for(DatabaseInfo dbInfo:listDatabase) {
			list.add("");list.add("");
			list.add("		database=new BGDB_Database("+dbInfo.DBId+",\""+dbInfo.DatabaseName+"\",\""+dbInfo.Description+"\","+dbInfo.ViewId+");");
			if(dbInfo.AccountLogin){
				rf = new RandomAccessFile(PATH.DATABASE_FOLDER + "/" + dbInfo.DBId + "/UserData.data", "r");listRF.add(rf);
				rf.seek(DBGame_AccountLogin.Offset_AccessKey);AccessKey = rf.readLong();
				rf.seek(DBGame_AccountLogin.Offset_ReadKey);ReadKey = rf.readLong();
				rf.seek(DBGame_AccountLogin.Offset_WriteKey);WriteKey = rf.readLong();
				list.add("		database.tableAccountLogin = new BGDB_TableAccountLogin("+dbInfo.DBId+","+AccessKey+","+ReadKey+","+WriteKey+");");
				// database.tableAccountLogin = new BGDB_TableAccountLogin(database.DBId,123,4456,789);
				
//				DBProcess_Describe dbDes = new DBProcess_Describe() {@Override public long countRow() throws IOException {return 0;}@Override public String getPathIndexing(int indexDescribe) throws IOException {return null;}};
//				dbDes.rfData=rf;
//				dbDes.offsetDescribe=DBProcess_Describe.HEADER;
//				DBDescribe[] listDes = dbDes.readDescribes();
//				if(listDes!=null)
//					for(DBDescribe des:listDes)
//						if(0<des.Type && des.Type<99)
//							list.add("		database.tableAccountLogin.AddDescribe(\""+des.ColumnName+"\","+des.ViewId+","+des.Indexing+","+des.Type+","+des.getDefaultData()+");");
//						else if(des.Type==DBDefine_DataType.STRING)
//							list.add("		database.tableAccountLogin.AddDescribe(\""+des.ColumnName+"\","+des.ViewId+","+des.Indexing+","+des.Type+",\""+des.getDefaultData()+"\");");
//						else
//							list.add("		database.tableAccountLogin.AddDescribe(\""+des.ColumnName+"\","+des.ViewId+","+des.Indexing+","+des.Size+","+des.Type+",null);");
			}
				
			list.add("		listDatabase.Add(database);");
		}
		list.add("	}");
		list.add("	////////////////////////////////////////////////////////////////////////");
		list.add("	private static Database ins = null;");
		list.add("	public static Database instance{");
		list.add("		get{");
		list.add("			if (ins == null)");
		list.add("				ins = new Database();");
		list.add("			return ins;");
		list.add("		}");
		list.add("	}");
		list.add("}");
		return list;
	}
	
//using System.Collections.Generic;
//public class Database{
//	public List<BGDB_Database> listDatabase;
//	private Database(){
	
//	}
//	private static Database ins = null;
//	public static Database instance{
//      get{
//          if (ins == null){
//              ins = new Database();
//          }
//          return ins;
//      }
//  }
//}	
	
	
	
	
	
	
	
	public void addToZip(ZipOutputStream zos,String fileName,ArrayList<String> list) throws IOException {
		String s=list.get(0);
		for(int i=1;i<list.size();i++)
			s=s+"\n"+list.get(i);
		byte[] data = s.getBytes();
		zos.putNextEntry(new ZipEntry(fileName));
		zos.write(data);
	}
	
	
	@Override public Object onHttp(BackendSession arg0) {
		listRF=new ArrayList<>();
		bos = new ByteArrayOutputStream();
		ZipOutputStream zos =new ZipOutputStream(bos);
		
		try {
			addToZip(zos, "CMD_ONEHIT.cs", getCMD(CMD_ONEHIT.class));
			addToZip(zos, "CMD_REALTIME.cs", getCMD(CMD_REALTIME.class));
			addToZip(zos, "StatusOnehit.cs", getOnehitStatus(StatusOnehit.class));
			addToZip(zos, "Database.cs", getDBTable());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {zos.close();} catch (IOException e) {e.printStackTrace();}//**Very important : close before to byte arrays
		return bos.toByteArray();
	}
	
	private ByteArrayOutputStream bos;
	private ArrayList<RandomAccessFile> listRF;
	@Override public void onDestroy() {
		if(listRF!=null)
			for(RandomAccessFile rf:listRF)
				try {rf.close();} catch (IOException e) {e.printStackTrace();}
		if(bos!=null)
			try {bos.close();} catch (IOException e) {e.printStackTrace();}
	}
}
