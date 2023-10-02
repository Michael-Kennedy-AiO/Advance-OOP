package backendgame.com.database.table;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.database.indexing.Indexing1Primary;
import backendgame.com.core.database.indexing.Indexing2Primary;
import backendgame.com.database.BGBTree;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.entity.BGDTO_Att;
import backendgame.com.database.entity.DB2PrimaryKey;
import backendgame.com.database.entity.DB2String;

public class DBTable_2Primary extends DBBaseTable_Primary{
	public byte TypeHash,TypeRange;
	
	public DBTable_2Primary(String _path) throws IOException {
		pathDB=_path;
		rfData = new RandomAccessFile(_path+DATA_EXTENSION, "rw");
		rfBTree = new RandomAccessFile(_path+INDEX_EXTENSION, "rw");
		offsetDescribe = HEADER;
		if (rfData.length() >= HEADER) {
			rfData.seek(Offset_Primary_Type);
			TypeHash = rfData.readByte();
			TypeRange = rfData.readByte();
		}
	}
	
	public void initNewDatabase(byte _TypeHash,String _HashName,byte _TypeRange,String _RangeName,BGColumn[] listColumn) throws IOException {
		TypeHash=_TypeHash;
		TypeRange=_TypeRange;
		
		rfData.seek(Offset_DatabaseType);
		rfData.writeByte(DBTYPE_2Primary);
		
		rfData.seek(Offset_Primary_Type);
		rfData.writeByte(_TypeHash);
		rfData.writeByte(_TypeRange);
		
		rfData.seek(Offset_Primary_Name);
		rfData.writeUTF(_HashName);
		rfData.writeUTF(_RangeName);
		
		rfData.setLength(offsetDescribe);
		writeBGColumn(listColumn);
	}
	
	private Indexing2Primary btree;
	private void initBtree() {if(btree==null)btree=new Indexing2Primary(rfData,rfBTree);}
	
	@Override public void skipPrimary() throws IOException {skipType(TypeHash);if(rfData.readBoolean())skipType(TypeRange);}
	
	public long querryOffset(Object hashKey,Object rangeKey) throws IOException {
//		if(validateType(TypeHash,hashKey)==false || validateType(TypeRange,rangeKey)==false)
//			throw new IOException("DBTable_2Primary → querryOffset(Object hashKey,Object rangeKey) " + DBDefine_DataType.getTypeName(TypeHash) + "("+hashKey+")	" + DBDefine_DataType.getTypeName(TypeRange)+"("+rangeKey+")");
		initBtree();
		return btree.querryOffset(TypeHash,hashKey,TypeRange, rangeKey);
	}
	public long[] querryOffset(Object hashValue) throws IOException {
//		if(validateType(TypeHash,hashValue)==false)
//			throw new IOException("DBTable_2Primary → querryOffset(Object hashKey,Object rangeKey) " + DBDefine_DataType.getTypeName(TypeHash) + "("+hashValue+")");
		initBtree();
		return btree.querryOffset(TypeHash,hashValue);
	}
	
	public long[] querryOffsetLike(String hashValue, int limit) throws IOException {
		if(validateType(TypeHash,hashValue)==false)
			throw new IOException("DBTable_2Primary → querryOffset(Object hashKey,Object rangeKey) " + DBDefine_DataType.getTypeName(TypeHash) + "("+hashValue+")");
		
		Indexing1Primary querry = new Indexing1Primary(TypeHash,rfData,rfBTree);
		long[] result = querry.querryIndexLike(hashValue,limit);
		if(result!=null) {
			int size=result.length;
			for(int i=0;i<size;i++) {
				rfBTree.seek(result[i]*8);
				result[i]=rfBTree.readLong();
			}
		}
		return result;
		
	}
	
//	public long insert(Object hashKey,Object rangeKey,ObjectWriter... listWriter) throws IOException {//Return offset
//		if(validateType(TypeHash,hashKey)==false || validateType(TypeRange,rangeKey)==false)
//			throw new IOException("DBTable_2Primary → insert(Object hashKey,Object rangeKey) " + DBDefine_DataType.getTypeName(TypeHash) + "("+hashKey.getClass().getSimpleName()+")	" + DBDefine_DataType.getTypeName(TypeRange)+"("+rangeKey.getClass().getSimpleName()+")");
//		
//		initBtree();
//		long offsetInsert=rfData.length();
//		if(btree.insertIndex_UNIQUE(offsetInsert, TypeHash, hashKey, TypeRange, rangeKey)==false)
//			return -1;
//		
//		if(getDataLength()>0 && getOffset_BeginData()>0) {//Đã bị seek sang vị trí khác do dùng chung rfData
//			byte[] dataDefault = getDefaultData();
//			rfData.seek(rfData.length());//Đã bị seek sang vị trí khác do dùng chung rfData
//			rfData.write(dataDefault);
//		}
//		
//		rfData.seek(rfData.length());//Đã bị seek sang vị trí khác do dùng chung rfData
//		rfData.writeLong(offsetInsert);//offset của primary → index tìm primary
//		return offsetInsert;
//	}
//
	public ArrayList<Object> loadRow(long offset, BGColumn[] listDes) throws IOException {
		rfData.seek(offset);
		ArrayList<Object> list=new ArrayList<>();
		list.add(readObject(TypeHash));
		list.add(rfData.readBoolean()?readObject(TypeRange):null);
		
		long _offsetBeginData=rfData.getFilePointer();
		for(BGColumn des:listDes)
			if(des.Type==DBDefine_DataType.IPV4) {
				list.add(InetAddress.getByAddress((byte[]) readBytes(_offsetBeginData+des.OffsetRow,4)));
			}else if(des.Type==DBDefine_DataType.IPV6) {
				list.add(Inet6Address.getByAddress((byte[]) readBytes(_offsetBeginData+des.OffsetRow,16)));
			}else
				list.add(readObject(_offsetBeginData+des.OffsetRow,des.Type));
		return list;
	}
	public long insert(Object hashKey,Object rangeKey,BGAttribute... listObWriter) throws IOException {//Return offset
		if (validateType(TypeHash,hashKey)==false || validateType(TypeRange,rangeKey)==false) {
			System.err.println("DBTable_2Primary → insert(Object hashKey,Object rangeKey) " + DBDefine_DataType.getTypeName(TypeHash) + "("+hashKey.getClass().getSimpleName()+")	" + DBDefine_DataType.getTypeName(TypeRange)+"("+rangeKey.getClass().getSimpleName()+")");
			return -1;
		}
		int numberColumn=0;
		long offsetInsert=0;
		try {numberColumn=numberColumn();offsetInsert = rfData.length();} catch (IOException e) {e.printStackTrace();return -1;}
		
		
		initBtree();
		//////////////////////////////////////////////////////////////////////////////////Trường hợp không có dataLength
		if(numberColumn==0) {
			if(btree.insertIndex_UNIQUE(offsetInsert, TypeHash, hashKey, TypeRange, rangeKey)==false)
				return -1;
			rfData.writeLong(offsetInsert);//offset của primary → index tìm primary
			return offsetInsert;
		}
		
		//////////////////////////////////////////////////////////////////////////////////Trường hợp row có data
		insertStep1_InitListObjectAndValidate(listObWriter);
		
		if(btree.insertIndex_UNIQUE(offsetInsert, TypeHash, hashKey, TypeRange, rangeKey)==false)
			return -1;
		
		long offsetBegin = rfData.length();
		insertStep2_WriteAttribute(offsetBegin);
		
		rfData.seek(offsetBegin + getDataLength());
		rfData.writeLong(offsetInsert);//offset của primary → index tìm primary
		return offsetInsert;
	}
	public void updateAttribute(Object hashValue,Object rangeValue,BGAttribute... listAttributes) throws IOException {
		long offsetPrimary = querryOffset(hashValue,rangeValue);
		if(offsetPrimary==-1)
			throw new IOException(hashValue+"("+rangeValue + ") not exist in "+pathDB);
		
		skipPrimary(offsetPrimary);
		offsetPrimary = rfData.getFilePointer();
		
		updateAttributeWithBeginDataRow(offsetPrimary, listAttributes);
	}
	
	
	public void delete(Object hashValue, Object rangeValue) throws IOException {
		initBtree();
		long indexPrimary = btree.querryIndex(TypeHash,hashValue,TypeRange, rangeValue);
		
		if(indexPrimary==-1)
			throw new IOException("HashValue("+hashValue+")-RangeValue("+rangeValue+") is not exist in "+pathDB);			
		long offsetBeginData = rfData.getFilePointer();
		btree.removeIndex(indexPrimary);
//		rfBTree.seek(indexPrimary*8);
//		skipPrimary(rfBTree.readLong());
		deleteIndexingAtBeginDataRow(offsetBeginData);
	}
	
	
	public void changingStructureColumn(BGColumn[] newDes, DBTable_2Primary dbTmp) throws IOException {
		BGColumn[] oldDes=readBGColumn();
		BGBTree.copyAllData(rfData, dbTmp.rfData);
		BGBTree.copyAllData(rfBTree, dbTmp.rfBTree);
		rfData.setLength(HEADER);
		rfBTree.setLength(0);
		writeBGColumn(newDes);
		
		//////////////////////////////////////////////////////////////////////////////////////
		BGDTO_Att[] attDuplication = BGBTree.getDuplicationNewOld(newDes, oldDes);
		///////////////////////////////////////////////////////////////////////////////////////
		long numberRow = dbTmp.countRow();
		long beginOld;
		Object hashValue,rangeValue;
		for(long i=0;i<numberRow;i++) {
			dbTmp.rfData.seek(dbTmp.getOffsetInBtree(i));
			hashValue=dbTmp.readObject(dbTmp.TypeHash);
			if(dbTmp.rfData.readBoolean())
				rangeValue=dbTmp.readObject(dbTmp.TypeRange);
			else
				rangeValue=null;
			
			beginOld=dbTmp.rfData.getFilePointer();
			for(BGDTO_Att dto:attDuplication)
				dto.Value = dbTmp.readObject(beginOld + dto.oldOffsetRow, dto.oldType);
			
			insert(hashValue,rangeValue, attDuplication);
		}
		
		int numberNew=newDes.length;
		int numberOld=oldDes.length;
		for(int i=0;i<numberNew;i++)
			if(newDes[i].Indexing==0)
				new File(getPathIndexing(i)).delete();
			else {
				Indexing1Primary index = initIndexing(i);
				index.rfBTree.setLength(0);
				indexingColumn(i);
			}
		
		for(int i=numberNew;i<numberOld;i++)
			new File(getPathIndexing(i)).delete();		
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public void trace() throws IOException {
//		long offset;
//		Object hash;
//		Object range;
//		long numberRow = countRow();
//		for(long i=0;i<numberRow;i++) {
//			offset = getOffsetInBtree(i);
//			rfData.seek(offset);
//			hash=readValue(TypeHash);
//			if(rfData.readBoolean())
//				range=readValue(TypeRange);
//			else
//				range=null;
//			System.out.println(i+"	"+offset+" "+hash+" - "+range);
//		}
//	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void readPrimary(DB2PrimaryKey primary) throws IOException {
		primary.hashKey = readObject(TypeHash);
		if(rfData.readBoolean())
			primary.rangeKey = readObject(TypeRange);
	}
	public DB2PrimaryKey readPrimaryAtOffset(long offsetPrimary) throws IOException {
		DB2PrimaryKey primary = new DB2PrimaryKey();
		rfData.seek(offsetPrimary);
		readPrimary(primary);
		return primary;
	}
	
	public void traceRows(long... listOffset) throws IOException {
		BGColumn[] list = readBGColumn();
		if (list == null) {
			System.out.println("**************************No Data**************************");
			return;
		}

		int RowIdLength = "Offset".length();
		for (long offset : listOffset)
			if(RowIdLength<(offset+"").length())
				RowIdLength=(offset+"").length();
		
		int STT = (listOffset.length+"").length();

		DB2String primary;
		int countHash = 0;
		int countRange = 0;
		if (listOffset != null)
			for (long rowId : listOffset) {
				primary=readPrimaryAtOffset(rowId).to2String();
				if(countHash<primary.string1.length())
					countHash=primary.string1.length();
				if(countRange<primary.string2.length())
					countRange=primary.string2.length();
			}
		for (int i = 0; i < STT+3 + countHash+countRange+2; i++)
			System.out.print(" ");// RowId

		int numberColumn = list.length;
		BGColumn column;
		System.out.printf("  %"+RowIdLength+"."+RowIdLength+"s", "Offset");
		for (int i = 0; i < numberColumn; i++) {
			column = list[i];
			System.out.printf("  %" + column.traceLength() + "." + column.traceLength() + "s", column.ColumnName);
		}
		System.out.println("");

		int countSTT=1;
		long offsetBeginRow;
		for (long offset : listOffset) {
			primary=readPrimaryAtOffset(offset).to2String();
			offsetBeginRow=rfData.getFilePointer();
			System.out.printf("%" + STT + "." + STT + "s.  %"+countHash+"."+countHash+"s  %"+countRange+"."+countRange+"s  %"+RowIdLength+"."+RowIdLength+"s", countSTT++, primary.string1,primary.string2,offset);
	
			rfData.skipBytes(8);//UserId
			
			for (int i = 0; i < numberColumn; i++) {
				column = list[i];
				if ((99<column.Type && column.Type<120) || column.Type == DBDefine_DataType.IPV6)
					System.out.printf("  %" + column.traceLength() + "." + column.traceLength() + "s", Arrays.toString((byte[]) readObject(offsetBeginRow+column.OffsetRow, column.Type)).replace(" ", ""));
				else
					System.out.printf("  %" + column.traceLength() + "." + column.traceLength() + "s", readObject(offsetBeginRow+column.OffsetRow, column.Type));
			}
			System.out.println();
		}
	}

	





}
