package backendgame.com.database.table;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.database.BGBaseDatabase;

public class DBLeaderboard_Link extends BGBaseDatabase {
	public static final long Offset_numberTop 			= 512;//4 byte
	
	public static final long OffsetPrimary_DatabaseId 	= 600;//2 byte
	public static final long OffsetPrimary_TableId 		= 602;//2 byte
	public static final long OffsetPrimary_ColumnId 	= 604;//4 byte
	
	public static final long OffsetValue_DatabaseId 	= 800;//2 byte
	public static final long OffsetValue_TableId 		= 802;//2 byte
	public static final long OffsetValue_ColumnId 		= 804;//4 byte
	
	public static final long Offset_DataType 		= 900;//1 byte
	public static final long Offset_MinValue 		= 901;//8 byte
	//////////////////////////////////////////////////////////////////////////
	public DBLeaderboard_Link(String _path) throws IOException {
		pathDB=_path;
		rfData = new RandomAccessFile(_path+DATA_EXTENSION, "rw");
	}

	public void initNewDatabase(short dbIdPrimary,short tableIdPrimary,int columnIdPrimary,short dbIdValue,short tableIdValue,int columnIdValue,int numberTop) throws IOException {
		rfData.seek(Offset_numberTop);
		rfData.writeInt(numberTop);
		
		rfData.seek(OffsetPrimary_DatabaseId);
		rfData.writeShort(dbIdPrimary);
		
		rfData.seek(OffsetPrimary_TableId);
		rfData.writeShort(tableIdPrimary);
		
		rfData.seek(OffsetPrimary_ColumnId);
		rfData.writeInt(columnIdPrimary);
		
		rfData.seek(OffsetValue_DatabaseId);
		rfData.writeShort(dbIdValue);
		
		rfData.seek(OffsetValue_TableId);
		rfData.writeShort(tableIdValue);
		
		rfData.seek(OffsetValue_ColumnId);
		rfData.writeInt(columnIdValue);
		
		rfData.setLength(HEADER);
	}
	
	
	
}
