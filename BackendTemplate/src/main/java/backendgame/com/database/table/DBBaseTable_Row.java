package backendgame.com.database.table;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.database.indexing.Indexing1Primary;
import backendgame.com.database.BGBTree;
import backendgame.com.database.BGBaseDatabaseTable;
import backendgame.com.database.entity.BGAttribute;

public abstract class DBBaseTable_Row extends BGBaseDatabaseTable{

//	public DBProcess_Describe_Rowloop(RandomAccessFile _rfData, long _offsetDescribe) {
//		super(_rfData, _offsetDescribe);
//	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*Dùng abstract để tạo ra 3 loại table
	 * -Loại 1 : row không có header
	 * -Loại 2 : header chứa 1 số byte ở đầu (VD : UserData chứa 8 byte đầu là Offset Credential
	 * -Loại 3 : Header là PrimaryKey và file RowId nằm riêng
	 */
//	public abstract long countRow() throws IOException;
//	public abstract long offsetOfRow(long rowId) throws IOException ;
	public abstract long offsetOfData(long rowId,int offsetData) throws IOException ;
	public abstract int rowLength() throws IOException ;
	
	
	@Override public long countRow() throws IOException {
		int rowLength = rowLength();//Luôn khác 0
		if(rowLength==0)
			return 0;
		long sumData = rfData.length() - getOffset_BeginData();
		if(sumData%rowLength==0)
			return sumData/rowLength;
		else
			return sumData/rowLength+1;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long offsetOfDescribe(long rowId, int indexDescribe) throws IOException{
		BGColumn describe=readBGColumnByIndex(indexDescribe);
		if(describe==null)
			throw new IOException("DBBaseRowloop → offsetOfDescribe(long rowId, int indexDescribe) : indexDescribe invalid ➨"+indexDescribe);
		return offsetOfData(rowId, describe.OffsetRow);
	}
	public void seekTo_OffsetOfDescribe(long rowId, int indexDescribe) throws IOException {
		rfData.seek(offsetOfDescribe(rowId, indexDescribe));
	}
	
	
	
	public Object readValue(long rowId,int indexDescribe) throws IOException {
		BGColumn describe=readBGColumnByIndex(indexDescribe);
		if(describe==null)
			throw new IOException("DBBaseRowloop → readData(long rowId,int indexDescribe) : indexDescribe invalid ➨"+indexDescribe);
		return readValue(rowId, describe);
	}
	public Object readValue(long rowId,BGColumn describe) throws IOException {
		return readValue(rowId, describe.OffsetRow, describe.Type);
	}
	public Object readValue(long rowId,int offsetData, byte Type) throws IOException {
		return readObject(offsetOfData(rowId, offsetData), Type);
	}
	public byte[] readBytes(long rowId, BGColumn describe, int length) throws IOException {
		return readBytes(offsetOfData(rowId, describe.OffsetRow), length);
	}
	
	

	public void writeData(long rowId, BGColumn describe,Object value ) throws IOException {
		baseWriting(offsetOfData(rowId, describe.OffsetRow), describe.Type, value);
	}
	public void writeData(long rowId,int offsetData, byte Type, Object value) throws IOException {
		baseWriting(offsetOfData(rowId, offsetData), Type, value);
	}
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Object process(long rowId, int indexDescribe, byte operator, byte Type, Object object) throws IOException {//Low prfomance
		if(Type==getColumn_DataType(indexDescribe))
			return process(offsetOfDescribe(rowId, indexDescribe), operator, Type, object);
		else
			throw new IOException("Database error "+DBDefine_DataType.getTypeName(getColumn_DataType(indexDescribe))+"!="+Type);
	}	
	
    ///Boolean////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean processBoolean(long rowId, int indexDescribe, byte operator,boolean value) throws IOException {
		byte Type = getColumn_DataType(indexDescribe);
		if(Type<1 || 9>Type)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(Type));
		return processBoolean(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public boolean processBoolean(long rowId, String columnName, byte operator,boolean value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processBoolean(long rowId, String columnName, byte operator,boolean value) : indexDescribe invalid ➨"+indexDescribe);
		return processBoolean(rowId, indexDescribe, operator, value);
	}
	
	///Byte///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte processByte(long rowId, int indexDescribe, byte operator,byte value) throws IOException {
		byte Type = getColumn_DataType(indexDescribe);
		if(Type<10 || 19>Type)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processByte(long rowId, int indexDescribe, byte operator,byte value) : "+DBDefine_DataType.getTypeName(Type));
		return processByte(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public byte processByte(long rowId, String columnName, byte operator,byte value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processByte(long rowId, String columnName, byte operator,byte value) : indexDescribe invalid ➨"+indexDescribe);
		return processByte(rowId, indexDescribe, operator, value);
	}
	
	///Short//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public short processShort(long rowId, int indexDescribe, byte operator,short value) throws IOException {
		byte Type = getColumn_DataType(indexDescribe);
		if(Type<20 || 39>Type)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processShort(long rowId, int indexDescribe, byte operator,short value) : "+DBDefine_DataType.getTypeName(Type));
		return processShort(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public short processShort(long rowId, String columnName, byte operator,short value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processShort(long rowId, String columnName, byte operator,short value) : indexDescribe invalid ➨"+indexDescribe);
		return processShort(rowId, indexDescribe, operator, value);
	}
	
	///Integer////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int processInt(long rowId, int indexDescribe, byte operator,int value) throws IOException {
		byte Type = getColumn_DataType(indexDescribe);
		if(Type<40 || 59>Type)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processInt(long rowId, int indexDescribe, byte operator,int value) : "+DBDefine_DataType.getTypeName(Type));
		return processInt(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public int processInt(long rowId, String columnName, byte operator,int value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processInt(long rowId, String columnName, byte operator,int value) : indexDescribe invalid ➨"+indexDescribe);
		return processInt(rowId, indexDescribe, operator, value);
	}
	
	///Float//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public float processFloat(long rowId, int indexDescribe, byte operator,float value) throws IOException {
		byte Type = getColumn_DataType(indexDescribe);
		if(Type<60 || 79>Type)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processFloat(long rowId, int indexDescribe, byte operator,float value) : "+DBDefine_DataType.getTypeName(Type));
		return processFloat(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public float processFloat(long rowId, String columnName, byte operator,float value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processFloat(long rowId, String columnName, byte operator,float value) : indexDescribe invalid ➨"+indexDescribe);
		return processFloat(rowId, indexDescribe, operator, value);
	}
	
	///Long///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long processLong(long rowId, int indexDescribe, byte operator,long value) throws IOException {
		byte Type = getColumn_DataType(indexDescribe);
		if(Type<80 || 89>Type)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processLong(long rowId, int indexDescribe, byte operator,long value) : "+DBDefine_DataType.getTypeName(Type));
		return processLong(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public long processLong(long rowId, String columnName, byte operator,long value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processLong(long rowId, String columnName, byte operator,long value) : indexDescribe invalid ➨"+indexDescribe);
		return processLong(rowId, indexDescribe, operator, value);
	}
	
	///Double/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public double processDouble(long rowId, int indexDescribe, byte operator,double value) throws IOException {
		byte Type = getColumn_DataType(indexDescribe);
		if(Type<90 || 99>Type)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processDouble(long rowId, int indexDescribe, byte operator,double value) : "+DBDefine_DataType.getTypeName(Type));
		return processDouble(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public double processDouble(long rowId, String columnName, byte operator,double value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processDouble(long rowId, String columnName, byte operator,double value) : indexDescribe invalid ➨"+indexDescribe);
		return processDouble(rowId, indexDescribe, operator, value);
	}
	
	///ByteArray//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] processByteArray(long rowId, int indexDescribe, byte operator,byte[] value) throws IOException {
		byte Type = getColumn_DataType(indexDescribe);
		if(Type<100 || 119>Type)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processByteArray(long rowId, int indexDescribe, byte operator,byte[] value) : "+DBDefine_DataType.getTypeName(getColumn_DataType(indexDescribe)));
		return processByteArray(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public byte[] processByteArray(long rowId, String columnName, byte operator,byte[] value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processByteArray(long rowId, String columnName, byte operator,byte[] value) : indexDescribe invalid ➨"+indexDescribe);
		return processByteArray(rowId, indexDescribe, operator, value);
	}
//	///List///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public byte[] processList(long rowId, int indexDescribe, byte operator,byte[] value) throws IOException {
//		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.List)
//			throw new IOException("Database error "+getClass().getSimpleName()+" → processList(long rowId, int indexDescribe, byte operator,byte[] value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
//		return processList(offsetOfDescribe(rowId, indexDescribe), operator, value);
//	}
//	public byte[] processList(long rowId, String columnName, byte operator,byte[] value) throws IOException {
//		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
//		if(indexDescribe==-1)
//			throw new IOException("DBBaseRowloop → processList(long rowId, String columnName, byte operator,byte[] value) : indexDescribe invalid ➨"+indexDescribe);
//		return processList(rowId, indexDescribe, operator, value);
//	}
	///String/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String processString(long rowId, int indexDescribe, byte operator,String value) throws IOException {
		if(getColumn_DataType(indexDescribe)!=DBDefine_DataType.STRING)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processString(long rowId, int indexDescribe, byte operator,String value) : "+DBDefine_DataType.getTypeName(getColumn_DataType(indexDescribe)));
		return processString(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public String processString(long rowId, String columnName, byte operator,String value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processString(long rowId, String columnName, byte operator,String value) : indexDescribe invalid ➨"+indexDescribe);
		return processString(rowId, indexDescribe, operator, value);
	}
	///IpV6///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] processIpV6(long rowId, int indexDescribe, byte operator,byte[] value) throws IOException {
		if(getColumn_DataType(indexDescribe)!=DBDefine_DataType.IPV6)
			throw new IOException("Database error "+getClass().getSimpleName()+" → processIpV6(long rowId, int indexDescribe, byte operator,byte[] value) : "+DBDefine_DataType.getTypeName(getColumn_DataType(indexDescribe)));
		return processIpV6(offsetOfDescribe(rowId, indexDescribe), operator, value);
	}
	public byte[] processIpV6(long rowId, String columnName, byte operator,byte[] value) throws IOException {
		int indexDescribe = findColumnIndex(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("DBBaseRowloop → processIpV6(long rowId, String columnName, byte operator,byte[] value) : indexDescribe invalid ➨"+indexDescribe);
		return processIpV6(rowId, indexDescribe, operator, value);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long getRowIdAt(long offset) throws IOException {return (offset - getOffset_BeginData())/rowLength();}
//	public void updateIndexingData(long rowId,DBDescribe des, Object value, RandomAccessFile rfBtree) throws IOException {
////		long offset = offsetOfData(rowId, des.OffsetRow);
////		Indexing1Primary indexing = new Indexing1Primary(rfData,rfBtree);
////		indexing.indexingStep1_SetupType(des.Type);
////		indexing.removeIndexAtOffset(offset,readData(offset, des.Type));
////		writeData(offset, des.Type, value);
////		indexing.indexingStep2_WriteBtree(offset, DBDataType.toByteArray(des.Type, value));
//		indexingUpdate(offsetOfData(rowId, des.OffsetRow), des.Type, value, rfBtree);
//	}
	public void indexingColumn(int indexDescribe) throws IOException {
		long numberRow = countRow();
		Indexing1Primary indexing = initIndexing(indexDescribe);
		
		byte Type = getColumn_DataType(indexDescribe);
		int OffsetRow = getCloumn_DataOffset(indexDescribe);
		if((0<Type && Type<100)|| Type==DBDefine_DataType.IPV6) {
			byte[] _buffer=null;
			if(0<Type && Type<20) {
				_buffer=new byte[1];
			}else if(19<Type && Type<40) {
				_buffer=new byte[2];
			}else if(39<Type && Type<80) {
				_buffer=new byte[4];
			}else if(79<Type && Type<100) {
				_buffer=new byte[8];
			}else if(Type==DBDefine_DataType.IPV6) {
				_buffer=new byte[16];
			}
			
			long _off;
			for(int i=0;i<numberRow;i++) {
				_off=offsetOfData(i, OffsetRow);
				rfData.seek(_off);
				rfData.read(_buffer);
				indexing.indexingBtree(_off, _buffer);
			}
		}else {
			long _off;
			for(int i=0;i<numberRow;i++) {
				_off=offsetOfData(i, OffsetRow);
				indexing.indexingBtree(_off, readType(_off, Type));
			}
		}
	}
	
	public void updateAttributesAtRowId(long rowId,BGAttribute... listUpdate) throws IOException {
		updateAttributeWithBeginDataRow(offsetOfData(rowId, 0), listUpdate);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void changingStructureColumn(BGColumn[] newDes,DBBaseTable_Row dbTmp) throws IOException {
		BGColumn[] oldDes = readBGColumn();
		BGBTree.copyAllData(rfData, dbTmp.rfData);
		rfData.setLength(HEADER);
		writeBGColumn(newDes);
		
		if(getDataLength()==0)
			return;
		
		int numberColNew = newDes.length;
		int[] idDuplication=new int[numberColNew];
		byte[][] dataRow=new byte[numberColNew][];
		for(int i=0;i<numberColNew;i++) {
			idDuplication[i] = dbTmp.findColumnIndex(newDes[i].ColumnName);
			if(idDuplication[i]==-1)
				dataRow[i]=readBytes(getOffset_DefaultData()+getCloumn_DataOffset(i), getCloumn_DataSize(i));
		}
		
		long numberRow=dbTmp.countRow();
		Object attribute;
		for(long row=0;row<numberRow;row++)
			for(int col=0;col<numberColNew;col++) {
				if(idDuplication[col]==-1)
					attribute = dataRow[col];
				else 
					attribute = dbTmp.readObject(dbTmp.offsetOfData(row, oldDes[idDuplication[col]].OffsetRow), newDes[col].Type);
				baseWriting(offsetOfData(row, newDes[col].OffsetRow), newDes[col].Type, attribute);
			}
		
		if(listIndexing!=null)
			for(int i=0;i<listIndexing.length;i++)
				if(listIndexing[i]!=null) {
					if(listIndexing[i].rfBTree!=null)
						listIndexing[i].rfBTree.close();
					new File(getPathIndexing(i)).delete();
				}
		
		for(int i=0;i<numberColNew;i++)
			if(newDes[i].Indexing!=0)
				indexingColumn(i);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@Override public void trace() throws IOException {
		System.out.println("  |=====================================Describe Database=====================================");
		System.out.println("  |TotalRow : "+countRow());
		System.out.println("  |File size : "+BGUtility.getMemory(rfData.length()));
		System.out.println("  |OffsetDescribe : "+offsetDescribe);
		System.out.println("  |DatalLength : "+getDataLength());
		System.out.println("  |OffsetBeginData : "+getOffset_BeginData());
		System.out.println("  |OffsetDefaultData : "+getOffset_DefaultData());
		System.out.println("  |-------------------------------------------------------------------------------------------");
		
		BGColumn[] list = readBGColumn();
		int maxSpace = 0;
		for(BGColumn describe:list)
			if(describe.ColumnName.length()>maxSpace)
				maxSpace = describe.ColumnName.length();
		if(maxSpace>20)
			maxSpace=20;
		
		System.out.printf("  |%"+(maxSpace+20)+"."+(maxSpace+20)+"s%12.12s%8.8s%12.12s   DefaultValue\n","Type","OffsetRow","ViewId","Permission");
		
		String strType;
		for(BGColumn describe:list) {
			if(describe.ColumnName.length()>maxSpace) {
				System.out.print("  |"+describe.ColumnName.substring(0, maxSpace-3)+"...");
			}else {
				System.out.print("  |"+describe.ColumnName);
				for(int i=describe.ColumnName.length();i<maxSpace;i++)
					System.out.print(" ");
			}
			
			strType = DBDefine_DataType.getTypeName(describe.Type);
			if(99<describe.Type && describe.Type<120)
				strType = strType+"("+(describe.Size-4)+")";//4 byte : length
			else if(describe.Type==DBDefine_DataType.STRING)
				strType = strType+"("+(describe.Size-2)+")";//2 byte : length
			
			if(describe.BinaryData==null)
				System.out.printf("%20.20s%12.12s%8.8s   Null\n",strType,describe.OffsetRow,describe.ViewId);
			else
				if((99<describe.Type && describe.Type<120) || describe.Type==DBDefine_DataType.IPV6)
					System.out.printf("%20.20s%12.12s%8.8s   "+Arrays.toString((byte[])describe.getDefaultData()).replace(" ", "")+"\n",strType,describe.OffsetRow,describe.ViewId);
				else
					System.out.printf("%20.20s%12.12s%8.8s   "+describe.getDefaultData()+"\n",strType,describe.OffsetRow,describe.ViewId);
		}
		System.out.println("  |===========================================================================================\n\n");
	}
	
	public void traceRows(long... listRow) throws IOException {
		BGColumn[] list = readBGColumn();
		if(list==null) {
			System.out.println("**************************No Data**************************");
			return;
		}
		
		int countCharRowId=0;
		if(listRow!=null)
			for(long rowId:listRow)
				if(countCharRowId<(rowId+"").length())
					countCharRowId=(rowId+"").length();
		for(int i=0;i<countCharRowId;i++)System.out.print(" ");//RowId
		
		int numberColumn=list.length;
		BGColumn describe;
		for(int i=0;i<numberColumn;i++) {
			describe=list[i];
			System.out.printf("  %"+describe.traceLength()+"."+describe.traceLength()+"s", describe.ColumnName);
		}
		System.out.println("");
		
		if(listRow==null)
			return;
		for(long rowId:listRow) {
			System.out.printf("%"+countCharRowId+"."+countCharRowId+"s", rowId);

			for (int i = 0; i < numberColumn; i++) {
				describe=list[i];
				if((99<describe.Type && describe.Type<120) || describe.Type==DBDefine_DataType.IPV6)
					System.out.printf("  %"+describe.traceLength()+"."+describe.traceLength()+"s", Arrays.toString((byte[])readValue(rowId, describe)).replace(" ", ""));
				else
					System.out.printf("  %"+describe.traceLength()+"."+describe.traceLength()+"s", readValue(rowId, describe));
			}
			System.out.println();
		}
	}
}
