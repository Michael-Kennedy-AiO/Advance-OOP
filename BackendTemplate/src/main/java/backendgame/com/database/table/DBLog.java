package backendgame.com.database.table;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.database.BGBaseDatabaseHeader;
import backendgame.com.database.entity.BGAttribute;

public class DBLog extends BGBaseDatabaseHeader {
	public DBLog(String _path) throws IOException {
		pathDB=_path;
		rfData = new RandomAccessFile(_path, "rw");
	}
	
	public long insert(BGAttribute... listObWriter) throws IOException {
		long rowId = countRow();
		long beginData = getOffset_BeginData();
		int rowLength = getDataLength();
		for(BGAttribute attribute:listObWriter)
			baseWriting(beginData + rowLength*rowId +getCloumn_DataOffset(attribute.indexDescribe), getColumn_DataType(attribute.indexDescribe), attribute.Value);
		
		return rowId;
	}
	
	public void update(long rowId,BGAttribute... listObWriter) throws IOException {
		long countROW = countRow();
		if(-1<rowId && rowId<countROW) {
//			for(BGAttribute attribute:listObWriter)
//				baseWriting(getOffset_BeginData()+getCloumn_DataOffset(attribute.indexDescribe), getColumn_DataType(attribute.indexDescribe), attribute.Value);
			long beginData = getOffset_BeginData();
			int rowLength = getDataLength();
			for(BGAttribute attribute:listObWriter)
				baseWriting(beginData + rowLength*rowId +getCloumn_DataOffset(attribute.indexDescribe), getColumn_DataType(attribute.indexDescribe), attribute.Value);
		}else
			throw new IOException("rowId "+rowId+" not in [0,"+(countROW-1)+"]");
	}
	
	public long countRow() throws IOException {
		int rowLength = getDataLength();//Luôn khác 0
		if(rowLength==0)
			return 0;
		long sumData = rfData.length() - getOffset_BeginData();
		if(sumData%rowLength==0)
			return sumData/rowLength;
		else
			return sumData/rowLength+1;
	}

}
