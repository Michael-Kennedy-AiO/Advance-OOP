package database_game.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import backendgame.com.core.database.BGColumn;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.table.DBBaseTable_Row;
import database_game.DatabaseManager;
import server.config.PATH;

public class DBGame_UserData extends DBBaseTable_Row{//lengthData = des.getDataLength()+9
	public static DatabaseManager databaseManager=null;
	public short DBId;
	
	public DBGame_UserData(short _DBId,String _path) throws FileNotFoundException {
		pathDB=_path;
		DBId=_DBId;
		rfData = new RandomAccessFile(pathDB, "rw");
		offsetDescribe = HEADER;
	}
	public DBGame_UserData(short _DBId) throws FileNotFoundException {
		this(_DBId, PATH.DATABASE_FOLDER + "/" + _DBId + "/UserData.data");
	}
	
	public void initNewDatabase(BGColumn[] listColumn) throws IOException {
		rfData.seek(Offset_DatabaseType);
		rfData.writeByte(DBTYPE_Row);
		
		rfData.setLength(offsetDescribe);
		writeBGColumn(listColumn);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Loại 2 : header chứa 9 byte ở đầu → offset Credential + AccountStatus
	@Override public int rowLength() throws IOException {
		return 9+getDataLength();
	}
	@Override public long offsetOfData(long userId, int offsetData) throws IOException {
		return getOffset_BeginData() + userId*rowLength() + 9 + offsetData;
	}
	private long offsetAtRow(long userId) throws IOException {
		return getOffset_BeginData() + userId*rowLength();
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override public void changingStructureColumn(BGColumn[] newDes, DBBaseTable_Row dbTmp) throws IOException {
		super.changingStructureColumn(newDes, dbTmp);
		DBGame_UserData database = (DBGame_UserData) dbTmp;
		long numberRow = countRow();
		byte[] _data=new byte[9];//offsetCredential + AccountStatus
		for(long i=0;i<numberRow;i++) {
			database.readBytes(database.offsetAtRow(i), _data);
			writeBytes(offsetAtRow(i), _data);
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void insertRow(long userId,long offsetCredential,BGAttribute... listObWriter) throws IOException {
		int dataLength = getDataLength();
		if(dataLength==0) {
			rfData.seek(offsetAtRow(userId));
			rfData.writeLong(offsetCredential);
			rfData.writeByte(1);//AccountStatus
			return;
		}
		
		insertStep1_InitListObjectAndValidate(listObWriter);
		insertStep2_WriteAttribute(offsetAtRow(userId)+9);
		rfData.seek(offsetAtRow(userId));
		rfData.writeLong(offsetCredential);
		rfData.writeByte(1);//AccountStatus
	}
	public long getOffsetOfCredential(long userId) throws IOException {
		rfData.seek(offsetAtRow(userId));
		return rfData.readLong();
	}
	public void reNewUser(long userId,long offsetCredential, byte[] defaultData) throws IOException {
		rfData.seek(offsetAtRow(userId));
		rfData.writeLong(offsetCredential);
		rfData.writeByte(1);//AccountStatus
		rfData.write(defaultData);
	}
	public void setCredentialOffset(long userId,long offsetCredential) throws IOException {
		rfData.seek(offsetAtRow(userId));
		rfData.writeLong(offsetCredential);
	}
	
	
	public byte getStatus(long userId) throws IOException {
		rfData.seek(offsetAtRow(userId)+8);
		return rfData.readByte();
	}
	public void updateStatus(long userId, byte status) throws IOException {
		rfData.seek(offsetAtRow(userId)+8);
		rfData.writeByte(status);
	}
	
	public byte[] readRow(long userId) throws IOException {
		int length = getDataLength();
		if(length==0)
			return null;
		
		byte[] data=new byte[length];
		rfData.seek(offsetOfData(userId, 0));
		rfData.read(data);
		return data;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getPath() {return pathDB;}
	@Override public String getPathIndexing(int indexDescribe) throws IOException {return pathDB+indexDescribe;}
}
