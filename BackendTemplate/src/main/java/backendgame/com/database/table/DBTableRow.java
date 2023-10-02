package backendgame.com.database.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.database.entity.BGAttribute;

public class DBTableRow extends DBBaseTable_Row{
	public DBTableRow(String _path) throws FileNotFoundException {
		pathDB=_path;
		rfData = new RandomAccessFile(pathDB, "rw");
		offsetDescribe = HEADER;
	}
	
	public void initNewDatabase(BGColumn[] listColumn) throws IOException {
		rfData.seek(Offset_DatabaseType);
		rfData.writeByte(DBTYPE_Row);
		
		rfData.setLength(offsetDescribe);
		writeBGColumn(listColumn);
	}
	
	

	
	
	public boolean rowIsExist(long rowId) throws IOException {
		rfData.seek(getOffset_BeginData() + rowId*(getDataLength()+1));
		return rfData.readBoolean();
	}

	
	public long offsetStartDataRow(long rowId) throws IOException {
		return getOffset_BeginData() + rowId*(getDataLength()+1) + 1;
	}
	@Override public int rowLength() throws IOException {return getDataLength()+1;}
	@Override public long offsetOfData(long rowId, int offsetData) throws IOException {
		return getOffset_BeginData() + rowId*(getDataLength()+1) + 1 + offsetData;
	}



	public boolean deleteRow(long rowId) throws IOException {
		if(rowIsExist(rowId)) {
			deleteIndexingAtBeginDataRow(offsetStartDataRow(rowId));
			writeByte(getOffset_BeginData() + rowId*(getDataLength()+1), (byte) 0);
			return true;
		}else
			return false;
	}
	


	

	public long insert(BGAttribute... listObWriter) throws IOException {
		int dataLength = getDataLength();
		if(dataLength==0)
			return 0;
		
		long rowId = countRow();
		insertStep1_InitListObjectAndValidate(listObWriter);
		insertStep2_WriteAttribute(offsetStartDataRow(rowId));
		writeByte(getOffset_BeginData() + rowId*(getDataLength()+1), (byte) 1);
		return rowId;
	}
	public boolean initRow(long rowId,BGAttribute... listObWriter) throws IOException {
		if(rowIsExist(rowId))
			deleteRow(rowId);
		insertStep1_InitListObjectAndValidate(listObWriter);
		insertStep2_WriteAttribute(offsetStartDataRow(rowId));
		writeByte(getOffset_BeginData() + rowId*(getDataLength()+1), (byte) 1);
		return true;
	}
	
	
	public ArrayList<Object> loadRow(long rowId, BGColumn[] listDes) throws IOException {
		if(rowIsExist(rowId)==false)
			return null;
		
		long _offsetBeginData = offsetStartDataRow(rowId);
		ArrayList<Object> list=new ArrayList<>();
		for(BGColumn des:listDes)
			if(des.Type==DBDefine_DataType.IPV4) {
				list.add(InetAddress.getByAddress((byte[]) readBytes(_offsetBeginData+des.OffsetRow,4)));
			}else if(des.Type==DBDefine_DataType.IPV6) {
				list.add(Inet6Address.getByAddress((byte[]) readBytes(_offsetBeginData+des.OffsetRow,16)));
			}else
				list.add(readObject(_offsetBeginData+des.OffsetRow,des.Type));
		return list;
	}
	
	@Override public String getPathIndexing(int indexDescribe) throws IOException {
		return pathDB+indexDescribe;
	}
	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void trace(long...rowIds) throws IOException {
		BGColumn[] listColumn=readBGColumn();
		int numberColumn=numberColumn();
		System.out.println("  ______________________________________________________________________________________________");
		BGColumn column;
		for (int i = 0; i < numberColumn; i++) {
			column = listColumn[i];
			System.out.printf("  %" + column.traceLength() + "." + column.traceLength() + "s", column.ColumnName);
		}
		
		
		for(long rowID:rowIds) {
			System.out.println();
			for (int i = 0; i < numberColumn; i++) {
				column = listColumn[i];
				if((99<column.Type && column.Type<120) || column.Type==DBDefine_DataType.IPV6)
					System.out.printf("  %" + column.traceLength() + "." + column.traceLength() + "s", Arrays.toString(readType(offsetOfData(rowID, column.OffsetRow), column.Type)));
				else
					System.out.printf("  %" + column.traceLength() + "." + column.traceLength() + "s", readObject(offsetOfData(rowID, column.OffsetRow), column.Type));
			}
		}
		System.out.println("\n  ______________________________________________________________________________________________");
		
		
		
	}


}
