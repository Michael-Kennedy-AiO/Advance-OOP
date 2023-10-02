package backendgame.com.database.table;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.indexing.Indexing1Primary;
import backendgame.com.database.BGBaseDatabaseTable;

public abstract class DBBaseTable_Primary extends BGBaseDatabaseTable {
	public static final long Offset_Primary_Type 			= 512;//2 byte 
	public static final long Offset_Primary_Name			= 514;//End : 512 byte
	
	public RandomAccessFile rfBTree;
	
	
	public void skipPrimary(long offset) throws IOException {rfData.seek(offset);skipPrimary();}
	public abstract void skipPrimary() throws IOException;
	
	public Object readValue(long offsetPrimary,int OffsetRow,byte Type) throws IOException {
		rfData.seek(offsetPrimary);
		skipPrimary();
		rfData.skipBytes(OffsetRow);
		return readObject(Type);
	}
	
	public long getOffsetInBtree(long index) throws IOException {rfBTree.seek(index*8);return rfBTree.readLong();}
//	public void updateIndexingData(long offset,byte Type, Object value, RandomAccessFile rfBtree) throws IOException {
//		Indexing1Primary indexing = new Indexing1Primary(rfData,rfBtree);
//		indexing.indexingStep1_SetupType(Type);
//		indexing.removeIndexAtOffset(offset,readType(offset, Type));
//		baseWriting(offset, Type, value);
//		indexing.indexingStep2_WriteBtree(offset, BGCastObject.toByteArray(Type, value));
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
				skipPrimary(getOffsetInBtree(i));
				rfData.skipBytes(OffsetRow);
				_off = rfData.getFilePointer();
				rfData.read(_buffer);
				indexing.indexingBtree(_off, _buffer);
			}
		}else {
			long _off;
			for(int i=0;i<numberRow;i++) {
				skipPrimary(getOffsetInBtree(i));
				_off = rfData.getFilePointer()+OffsetRow;
				indexing.indexingBtree(_off, readType(_off, Type));
			}
		}
	}
//	
//	public void indexing(long offset,byte Type, Object value,RandomAccessFile rfBtree) throws IOException {
//		Indexing1Primary indexing = new Indexing1Primary(rfData, rfBtree);//Tạo ra indexing
//		indexing.indexingStep1_SetupType(Type);
//		indexing.indexingStep2_WriteBtree(offset, value);
//	}
	
	
	
	

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean validateType(byte Type,Object object) {
		Class<?> c = object.getClass();
		if(0<Type && Type<10)
			return c==Boolean.class;
		else if(9<Type && Type<20)
			return c==Byte.class;
		else if(19<Type && Type<40)
			return c==Short.class;
		else if(39<Type && Type<60)
			return c==Integer.class;
		else if(59<Type && Type<80)
			return c==Float.class;
		else if(79<Type && Type<90)
			return c==Long.class;
		else if(89<Type && Type<100)
			return c==Double.class;
		else if(99<Type && Type<120) {
			return c==byte[].class;
		}else if(Type==DBDefine_DataType.STRING)
			return c==String.class;
		else if(Type==DBDefine_DataType.IPV6) {
			return c==byte[].class;
		}else
			return false;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override public String getPathIndexing(int indexDescribe) throws IOException {
		return pathDB+indexDescribe;
	}
	
	@Override public long countRow() throws IOException {return rfBTree.length()/8;}
	@Override public void closeDatabase() {if (rfBTree != null)try {rfBTree.close();rfBTree = null;} catch (IOException e) {e.printStackTrace();}super.closeDatabase();}
	
	public String getPath() {return pathDB;}
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
			Files.deleteIfExists(FileSystems.getDefault().getPath(pathDB + DATA_EXTENSION));
			Files.deleteIfExists(FileSystems.getDefault().getPath(pathDB + INDEX_EXTENSION));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
