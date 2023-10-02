package database_game;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import backendgame.com.core.BGUtility;
import backendgame.com.database.BGBaseDatabase;
import database_game.entity.DatabaseInfo;
import database_game.entity.LockDatabase;
import database_game.entity.TableInfo;
import database_game.table.DBGame_AccountLogin;
import server.config.PATH;

public class DatabaseManager {
	public static final String TableInfoFileName = "TableInfo";
	
	
	private Object lockDatabase;
	public LockDatabase[] lockDB;
	public static final int ROW_LENGTH=200;
	private DatabaseManager() {
		lockDatabase = new Object();
		lockDB=new LockDatabase[Short.MAX_VALUE];
	}
	
	public LockDatabase getLockDatabase(short DBId) {if(lockDB[DBId]==null)lockDB[DBId]=new LockDatabase();return lockDB[DBId];}
	public Object getLockTable(short DBId,short TableId) {return getLockDatabase(DBId).getLockTable(TableId);}
	
	public void deleteDatabase(short DBId) {
		RandomAccessFile rf=null;
		try {
			rf=new RandomAccessFile(pathDatabaseInfo(), "rw");
			rf.seek(DBId*ROW_LENGTH);
			rf.writeBoolean(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
	}
	
	public void updateDatabaseInfo(short DBId, int viewId, String databaseName, String description) {
		RandomAccessFile rf=null;
		try {
			rf=new RandomAccessFile(pathDatabaseInfo(), "rw");
			rf.seek(DBId*ROW_LENGTH);
			rf.writeBoolean(true);
			rf.writeInt(viewId);
			rf.writeUTF(databaseName);
			rf.writeUTF(description);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
	}
	
	public short createDatabase(String databaseName, String description, int viewId) {
		short DBId=countDatabase();
		RandomAccessFile rf=null;
		synchronized (lockDatabase) {
			try {
				rf=new RandomAccessFile(pathDatabaseInfo(), "rw");
				if(DBId==Short.MAX_VALUE){
					DBId=-1;
					for(short i=0;i<Short.MAX_VALUE;i++) {
						rf.seek(i*ROW_LENGTH);
						if(rf.readBoolean()==false) {
							DBId = i;
							rf.seek(DBId*ROW_LENGTH);
							rf.writeBoolean(true);
							rf.writeInt(viewId);
							rf.writeUTF(databaseName);
							rf.writeUTF(description);
							break;
						}
					}
				}else {
					rf.seek(DBId*ROW_LENGTH);
					rf.writeBoolean(true);
					rf.writeInt(viewId);
					rf.writeUTF(databaseName);
					rf.writeUTF(description);
				}
				
				File file = new File(PATH.DATABASE_FOLDER+"/"+DBId);
				if(file.exists())
					BGUtility.deleteFolder(file);
				file.mkdir();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
		return DBId;
	}
	public short countDatabase() {
		File file=new File(pathDatabaseInfo());
		if(file.exists())
			if(file.length()%ROW_LENGTH==0)
				return (short) (file.length()/ROW_LENGTH);
			else
				return (short) (file.length()/ROW_LENGTH + 1);
		else
			return 0;
	}
	
	public DatabaseInfo readDatabaseInfo(short DBId) {
		DatabaseInfo dBinfo=null;
		RandomAccessFile rf=null;

		try {
			rf=new RandomAccessFile(pathDatabaseInfo(), "rw");
			rf.seek(DBId*ROW_LENGTH);
			if(rf.readBoolean()) {
				dBinfo=new DatabaseInfo();
				dBinfo.DBId = DBId;
				dBinfo.ViewId = rf.readInt();
				dBinfo.DatabaseName=rf.readUTF();
				dBinfo.Description=rf.readUTF();
				
				dBinfo.AccountLogin = new File(pathAccountLogin(DBId)+BGBaseDatabase.DATA_EXTENSION).exists();
				dBinfo.listTable=readListTableInfo(dBinfo.DBId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
		
		return dBinfo;
	}
	public ArrayList<DatabaseInfo> readListDatabaseInfo() {
		ArrayList<DatabaseInfo> list=new ArrayList<>();
		RandomAccessFile rf=null;

		try {
			rf=new RandomAccessFile(pathDatabaseInfo(), "rw");
			DatabaseInfo dBinfo;
			short numberRow = countDatabase();
			for(short i=0;i<numberRow;i++) {
				rf.seek(i*ROW_LENGTH);
				if(rf.readBoolean()) {
					dBinfo=new DatabaseInfo();
					dBinfo.DBId = i;
					dBinfo.ViewId = rf.readInt();
					dBinfo.DatabaseName=rf.readUTF();
					dBinfo.Description=rf.readUTF();
					list.add(dBinfo);
					
					dBinfo.AccountLogin = checkAccountLogin(dBinfo.DBId);
					dBinfo.listTable=readListTableInfo(dBinfo.DBId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
		
		return list;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean checkAccountLogin(short DBId) {return new File(pathAccountLogin(DBId)+BGBaseDatabase.DATA_EXTENSION).length()>=DBGame_AccountLogin.HEADER_LENGTH;}
	public boolean checkDatabase(short DBId) {
		boolean result=false;
		RandomAccessFile rf=null;

		try {
			rf=new RandomAccessFile(pathDatabaseInfo(), "rw");
			rf.seek(DBId*ROW_LENGTH);
			result=rf.readBoolean();
		} catch (IOException e) {
//			e.printStackTrace();
		}
		
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
		return result;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public short countTable(short DBId) {
		File file=new File(pathTableInfo(DBId));
		if(file.exists())
			if(file.length()%ROW_LENGTH==0)
				return (short) (file.length()/ROW_LENGTH);
			else
				return (short) (file.length()/ROW_LENGTH + 1);
		else
			return 0;
	}
	public short createTable(short DBId, byte TableType,String databaseName, String description, int viewId) {
		File file=new File(PATH.DATABASE_FOLDER+"/"+DBId);
		if(file.exists()==false)
			file.mkdirs();
		
		if(DBId<0 || DBId==Short.MAX_VALUE)
			return -1;
		
		RandomAccessFile rf=null;
		short tableId=countTable(DBId);
		synchronized (getLockDatabase(DBId)) {
			try {
				rf=new RandomAccessFile(pathTableInfo(DBId), "rw");
				if(tableId==Short.MAX_VALUE){
					tableId=-1;
					for(short i=0;i<Short.MAX_VALUE;i++) {
						rf.seek(i*ROW_LENGTH);
						if(rf.readBoolean()==false) {
							tableId = i;
							rf.seek(tableId*ROW_LENGTH);
							rf.writeBoolean(true);
							rf.writeByte(TableType);
							rf.writeInt(viewId);
							rf.writeUTF(databaseName);
							rf.writeUTF(description);
							break;
						}
					}
				}else {
					rf.seek(tableId*ROW_LENGTH);
					rf.writeBoolean(true);
					rf.writeByte(TableType);
					rf.writeInt(viewId);
					rf.writeUTF(databaseName);
					rf.writeUTF(description);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
		return tableId;
	}
	
	
	public void deleteTable(short DBId, short TableId) {
		if(DBId<0 || DBId==Short.MAX_VALUE)
			return;
		
		RandomAccessFile rf=null;
		try {
			rf=new RandomAccessFile(pathTableInfo(DBId), "rw");
			rf.seek(TableId*ROW_LENGTH);
			rf.writeBoolean(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
		
		BGUtility.deleteFolder(new File(pathTable(DBId,TableId)).getParentFile());
	}
	
	public void updateTableInfo(short DBId, short TableId, int viewId, String databaseName, String description) {
		if(DBId<0 || DBId==Short.MAX_VALUE)
			return;
		
		RandomAccessFile rf=null;
		try {
			rf=new RandomAccessFile(pathTableInfo(DBId), "rw");
			rf.seek(TableId*ROW_LENGTH + 2);//Boolean + TableType
			rf.writeInt(viewId);
			rf.writeUTF(databaseName);
			rf.writeUTF(description);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
	}
	
	public ArrayList<TableInfo> readListTableInfo(short DBId) {
		ArrayList<TableInfo> list=new ArrayList<>();
		RandomAccessFile rf=null;

		try {
			rf=new RandomAccessFile(pathTableInfo(DBId), "rw");
			TableInfo dBinfo;
			short numberRow = countTable(DBId);
			for(short i=0;i<numberRow;i++) {
				rf.seek(i*ROW_LENGTH);
				if(rf.readBoolean()) {
					dBinfo=new TableInfo();
					dBinfo.TableId = i;
					dBinfo.TableType = rf.readByte();
					dBinfo.ViewId = rf.readInt();
					dBinfo.TableName=rf.readUTF();
					dBinfo.Description=rf.readUTF();
					list.add(dBinfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
		
		return list;
	}
	
	
	private String pathDatabaseInfo() {return PATH.DATABASE_FOLDER+"/DatabaseInfo";}
	public String pathAccountLogin(short DBId) {return PATH.DATABASE_FOLDER+"/"+DBId+"/AccountLogin";}
	private String pathTableInfo(short DBId) {return PATH.DATABASE_FOLDER+"/"+DBId+"/"+TableInfoFileName;}
	public String pathTable(short DBId,short TableId) {return PATH.DATABASE_FOLDER+"/"+DBId+"/"+TableId+"/Table";}
	
	
	
	private static DatabaseManager instance=null;
	public static DatabaseManager gI() {
		if(instance==null) {
			instance=new DatabaseManager();
//			try {
//				instance = new Gson().fromJson(new String(Files.readAllBytes(instance.path),StandardCharsets.UTF_8), DatabaseManager.class);
//			} catch (JsonSyntaxException | IOException e) {
////				e.printStackTrace();
//				instance.maxDBId=0;
//				instance.listDatabase=new DatabaseInfo[0];
//				instance.writeDatabaseInfo();
//			}
		}
		return instance;
	}
//
//	
//	public void trace() {
//		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(getHashMap()));
//	}







}
