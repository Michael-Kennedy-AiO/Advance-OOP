package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.database.indexing.Indexing1Primary;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.operator.DBOperator_Boolean;
import backendgame.com.database.operator.DBOperator_Byte;
import backendgame.com.database.operator.DBOperator_ByteArrays;
import backendgame.com.database.operator.DBOperator_Double;
import backendgame.com.database.operator.DBOperator_Float;
import backendgame.com.database.operator.DBOperator_IPV6;
import backendgame.com.database.operator.DBOperator_Integer;
import backendgame.com.database.operator.DBOperator_Long;
import backendgame.com.database.operator.DBOperator_Short;
import backendgame.com.database.operator.DBOperator_String;



/*
 * 
 * Describes nằm riêng với dataDefault vì:
 * 1/ Dễ clone DataDefault
 * 2/ Dễ tìm kiếm và update Describe
 * 
 * */
public abstract class BGBaseDatabaseTable extends BGBaseDatabaseHeader{
	public Indexing1Primary[] listIndexing;
	protected Object[] listObjectRow;
	
	protected Indexing1Primary initIndexing(int indexDescribe) throws IOException {
		if(getCloumn_Indexing(indexDescribe)==0)
			return null;
		if(listIndexing==null)
			listIndexing=new Indexing1Primary[numberColumn()];
		Indexing1Primary _indexing = listIndexing[indexDescribe];
		if(_indexing==null) {
			_indexing=new Indexing1Primary(getColumn_DataType(indexDescribe), rfData, new RandomAccessFile(getPathIndexing(indexDescribe), "rw"));
			listIndexing[indexDescribe]=_indexing;
		}
		return _indexing;
	}
	
	public abstract long countRow()throws IOException;
	public abstract String getPathIndexing(int indexDescribe)throws IOException;
	
	@Override public void deleteAndClose() {
		try {
			int numberColumn=numberColumn();
			for(int i=0;i<numberColumn;i++)
				Files.deleteIfExists(FileSystems.getDefault().getPath(getPathIndexing(i)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeDatabase();
		try {
			Files.deleteIfExists(FileSystems.getDefault().getPath(pathDB));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public void indexingOffset(long offset,byte Type,Object value,RandomAccessFile rfBtree) throws IOException {
//		Indexing1Primary indexing = new Indexing1Primary(rfData, rfBtree);//Tạo ra indexing
//		indexing.indexingStep1_SetupType(Type);
//		indexing.indexingStep2_WriteBtree(offset, value);
//	}
//	public void indexingRemove(byte Type,Object value,long offset,RandomAccessFile rfBtree) throws IOException {
//		Indexing1Primary indexing = new Indexing1Primary(rfData, rfBtree);//Tạo ra indexing
//		indexing.indexingStep1_SetupType(Type);
//		indexing.removeIndexAtOffset(offset, value);
//	}
//	public void indexingUpdate(long offset,byte Type,Object value,RandomAccessFile rfBtree) throws IOException {
//		Indexing1Primary indexing = new Indexing1Primary(rfData, rfBtree);//Tạo ra indexing
//		indexing.indexingStep1_SetupType(Type);
//		indexing.removeIndexAtOffset(offset, value);
//		baseWriting(offset, Type, value);
//		indexing.indexingStep2_WriteBtree(offset, value);
//	}
	
	public long[] querryIndexing_EqualTo(int indexDescribe,Object value,int limit) throws IOException {
		Indexing1Primary indexing = initIndexing(indexDescribe);
		long[] list=indexing.querryIndexing_EqualTo(value, limit);
		if(list!=null)
			indexing.indexToOffset(list);
		return list;
	}
	public long[] querryIndexing_LessThanOrEqualTo(int indexDescribe,Object value,int limit) throws IOException {
		Indexing1Primary indexing = initIndexing(indexDescribe);
		long[] list=indexing.querryIndexing_LessThanOrEqualTo(value, limit);
		if(list!=null)
			indexing.indexToOffset(list);
		return list;
	}
	public long[] querryIndexing_LessThan(int indexDescribe,Object value,int limit) throws IOException {
		Indexing1Primary indexing = initIndexing(indexDescribe);
		long[] list=indexing.querryIndexing_LessThan(value, limit);
		if(list!=null)
			indexing.indexToOffset(list);
		return list;
	}
	public long[] querryIndexing_GreaterThan(int indexDescribe,Object value,int limit) throws IOException {
		Indexing1Primary indexing = initIndexing(indexDescribe);
		long[] list=indexing.querryIndexing_GreaterThan(value, limit);
		if(list!=null)
			indexing.indexToOffset(list);
		return list;
	}
	public long[] querryIndexing_GreaterThanOrEqualTo(int indexDescribe,Object value,int limit) throws IOException {
		Indexing1Primary indexing = initIndexing(indexDescribe);
		long[] list=indexing.querryIndexing_GreaterThanOrEqualTo(value, limit);
		if(list!=null)
			indexing.indexToOffset(list);
		return list;
	}
	public long[] querryIndexing_Between(int indexDescribe,Object valueLeft,Object valueRight,int limit) throws IOException {
		Indexing1Primary indexing = initIndexing(indexDescribe);
		long[] list=indexing.querryIndexing_Between(valueLeft, valueRight, limit);
		if(list!=null)
			indexing.indexToOffset(list);
		return list;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean processBoolean(long offsetData, byte operator,boolean value) throws IOException {return new DBOperator_Boolean(rfData).process(offsetData, operator, value);}
	public byte processByte(long offsetData, byte operator,byte value) throws IOException {return new DBOperator_Byte(rfData).process(offsetData, operator, value);}
	public short processShort(long offsetData, byte operator,short value) throws IOException {return new DBOperator_Short(rfData).process(offsetData, operator, value);}
	public float processFloat(long offsetData, byte operator,float value) throws IOException {return new DBOperator_Float(rfData).process(offsetData, operator, value);}
	public int processInt(long offsetData, byte operator,int value) throws IOException {return new DBOperator_Integer(rfData).process(offsetData, operator, value);}
	public double processDouble(long offsetData, byte operator,double value) throws IOException {return new DBOperator_Double(rfData).process(offsetData, operator, value);}
	public long processLong(long offsetData, byte operator,long value) throws IOException {return new DBOperator_Long(rfData).process(offsetData, operator, value);}
	public byte[] processByteArray(long offsetData, byte operator,byte[] value) throws IOException {return new DBOperator_ByteArrays(rfData).process(offsetData, operator, value);}
	public String processString(long offsetData, byte operator,String value) throws IOException {return new DBOperator_String(rfData).process(offsetData, operator, value);}
	public byte[] processIpV6(long offsetData, byte operator,byte[] value) throws IOException {return new DBOperator_IPV6(rfData).process(offsetData, operator, value);}
	
	public Object process(long offsetData, byte operator, byte Type, Object object) throws IOException {
		rfData.seek(offsetData);
		if(0<Type && Type<10)
			return new DBOperator_Boolean(rfData).process(offsetData, operator, (boolean) object);
		else if(9<Type && Type<20)
			return new DBOperator_Byte(rfData).process(offsetData, operator, (byte) object);
		else if(19<Type && Type<40)
			return new DBOperator_Short(rfData).process(offsetData, operator, (short) object);
		else if(39<Type && Type<60)
			return new DBOperator_Integer(rfData).process(offsetData, operator, (int) object);
		else if(59<Type && Type<80)
			return new DBOperator_Float(rfData).process(offsetData, operator, (float) object);
		else if(79<Type && Type<90)
			return new DBOperator_Long(rfData).process(offsetData, operator, (long) object);
		else if(89<Type && Type<100)
			return new DBOperator_Double(rfData).process(offsetData, operator, (double) object);
		else if(99<Type && Type<120)
			return new DBOperator_ByteArrays(rfData).process(offsetData, operator, (byte[]) object);
		else if(Type==DBDefine_DataType.STRING)
			return new DBOperator_String(rfData).process(offsetData, operator, (String) object);
		else if(Type==DBDefine_DataType.IPV6)
			return new DBOperator_IPV6(rfData).process(offsetData, operator, (byte[]) object);
		else
			throw new IOException("Database error DBProcess_Describe → process(long offsetData, byte pperator, byte Type, Object object) " + DBDefine_DataType.getTypeName(Type));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected void insertStep1_InitListObjectAndValidate(BGAttribute... writers) throws IOException{
		int numberColumn=numberColumn();
		//Init listObjectRow with Null
		if(listObjectRow==null)
			listObjectRow=new Object[numberColumn];
		else
			for(int i=0;i<numberColumn;i++)
				listObjectRow[i]=null;
		
		//Fill in  listObject
		for(BGAttribute att:writers)
			listObjectRow[att.indexDescribe]=att.Value;
		
		if(listIndexing==null)
			listIndexing=new Indexing1Primary[numberColumn];
		long offsetDefaultData=getOffset_DefaultData();
		byte Type;
		byte indexType;
		for(int i=0;i<numberColumn;i++) {
			Type=getColumn_DataType(i);
			if(listObjectRow[i]==null)
				listObjectRow[i]=readObject(offsetDefaultData+getCloumn_DataOffset(i), Type);
			
			indexType = getCloumn_Indexing(i);
			if(indexType!=0) {
				listIndexing[i] = new Indexing1Primary(Type, rfData, new RandomAccessFile(getPathIndexing(i), "rw"));
				
				if(indexType==BGColumn.INDEXING_UNIQUE && listIndexing[i].querryIndex(listObjectRow[i])!=-1)
					throw new IOException("Duplicate values("+(listObjectRow[i].getClass()==byte[].class?Arrays.toString((byte[])listObjectRow[i]):listObjectRow[i])+") in column "+getCloumnName(i));
			}
			
		}
		
	}
	protected void insertStep2_WriteAttribute(long offsetBegin) throws IOException{
		int numberColumn=numberColumn();
		int offsetData;
		byte Type;
		
		for(int i=0;i<numberColumn;i++) {
			offsetData = getCloumn_DataOffset(i);
			Type = getColumn_DataType(i);
			baseWriting(offsetBegin+offsetData, Type, listObjectRow[i]);
			
			if(listIndexing[i]!=null)
				listIndexing[i].indexingBtree(offsetBegin+offsetData, listObjectRow[i]);
		}
	}
	
	protected void updateAttributeWithBeginDataRow(long offsetBeginDataRow,BGAttribute... listAttributes) throws IOException {
		int numberColumn=numberColumn();
		byte[] listIndexValue=new byte[numberColumn];
		for(BGAttribute attribute:listAttributes) {
			listIndexValue[attribute.indexDescribe]=getCloumn_Indexing(attribute.indexDescribe);
			if(listIndexValue[attribute.indexDescribe]==BGColumn.INDEXING_UNIQUE && initIndexing(attribute.indexDescribe).querryIndex(attribute.Value)!=-1)
				throw new IOException("updateAttributeAtOffset error : Duplicate "+(attribute.Value.getClass()==byte[].class?Arrays.toString((byte[])attribute.Value):attribute.Value)+" in "+getCloumnName(attribute.indexDescribe));
		}
		
		Indexing1Primary indexing;
		for(BGAttribute attribute:listAttributes)
			if(listIndexValue[attribute.indexDescribe]==0)
				baseWriting(offsetBeginDataRow+getCloumn_DataOffset(attribute.indexDescribe), getColumn_DataType(attribute.indexDescribe), attribute.Value);
			else {
				indexing = initIndexing(attribute.indexDescribe);
				indexing.removeIndexAtOffset(attribute.indexDescribe, attribute.Value);
				indexing.indexingBtree(offsetBeginDataRow+getCloumn_DataOffset(attribute.indexDescribe), attribute.Value);
			}
	}
	
	protected void deleteIndexingAtBeginDataRow(long offsetBeginDataRow) throws IOException {
		int numberColumn=numberColumn();
		Indexing1Primary indexing;
		for(int i=0;i<numberColumn;i++) {
			indexing=initIndexing(i);
			if(indexing!=null)
				indexing.removeIndexAtOffset(offsetBeginDataRow+getCloumn_DataOffset(i), readObject(offsetBeginDataRow, getColumn_DataType(i)));
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override public void closeDatabase() {
		super.closeDatabase();
		if(listIndexing!=null) {
			int numberColumn = listIndexing.length;
			Indexing1Primary _indexing;
			for(int i=0;i<numberColumn;i++) {
				_indexing = listIndexing[i];
				if(_indexing!=null && _indexing.rfBTree!=null)
					try {
						_indexing.rfBTree.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void traceDefaultData() throws IOException {
		BGColumn[] list = readBGColumn();
		BGColumn describe;
		for(int i=0;i<list.length;i++) {
			describe=list[i];
			System.out.printf("%"+describe.traceLength()+"."+describe.traceLength()+"s  ", describe.ColumnName);
		}
		System.out.println("");

		for (int i = 0; i < list.length; i++) {
			describe=list[i];
			if((99<describe.Type && describe.Type<120) || describe.Type==DBDefine_DataType.IPV6)
				System.out.printf("%"+describe.traceLength()+"."+describe.traceLength()+"s  ", Arrays.toString((byte[])describe.getDefaultData()).replace(" ", ""));
			else
				System.out.printf("%"+describe.traceLength()+"."+describe.traceLength()+"s  ", describe.getDefaultData());
		}
		System.out.println();
	}
	public void trace() throws IOException {
		System.out.println("  |================================Describe Database================================");
		System.out.println("  |File size : "+BGUtility.getMemory(rfData.length()));
		System.out.println("  |OffsetDescribe : "+offsetDescribe);
		System.out.println("  |DatalLength : "+getDataLength());
		System.out.println("  |OffsetBeginData : "+getOffset_BeginData());
		System.out.println("  |OffsetDefaultData : "+getOffset_DefaultData());
		System.out.println("  |---------------------------------------------------------------------------------");
		
		BGColumn[] list = readBGColumn();
		if(list==null)
			return;
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
		System.out.println("  |=================================================================================");
	}
}
