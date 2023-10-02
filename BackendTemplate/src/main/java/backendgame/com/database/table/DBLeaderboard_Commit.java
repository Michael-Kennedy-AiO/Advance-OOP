package backendgame.com.database.table;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.database.BGBaseDatabase;

public class DBLeaderboard_Commit extends BGBaseDatabase {
	public static final long Offset_numberTop 		= 512;//4 byte
	public static final long Offset_DataType 		= 900;//1 byte
	public static final long Offset_MinValue 		= 901;//8 byte
	//////////////////////////////////////////////////////////////////////////
	public DBLeaderboard_Commit(String _path) throws IOException {
		pathDB=_path;
		rfData = new RandomAccessFile(_path+DATA_EXTENSION, "rw");
	}

	public void initNewDatabase(byte dataType,int numberTop) throws IOException {
		rfData.seek(Offset_numberTop);
		rfData.writeInt(numberTop);
		
		rfData.seek(Offset_DataType);
		rfData.writeByte(dataType);
		
		rfData.setLength(HEADER);
	}

	
	
	
	

}
