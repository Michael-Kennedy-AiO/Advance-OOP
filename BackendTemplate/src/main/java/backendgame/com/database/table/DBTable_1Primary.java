package backendgame.com.database.table;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.database.indexing.Indexing1Primary;
import backendgame.com.database.BGBTree;
import backendgame.com.database.entity.BGAttribute;
import backendgame.com.database.entity.BGDTO_Att;

public class DBTable_1Primary extends DBBaseTable_Primary{
	public byte primaryType;
	public DBTable_1Primary(String _path) throws IOException {
		pathDB=_path;
		rfData = new RandomAccessFile(_path+DATA_EXTENSION, "rw");
		rfBTree = new RandomAccessFile(_path+INDEX_EXTENSION, "rw");
		offsetDescribe = HEADER;
		if(rfData.length()>=HEADER) {
			rfData.seek(Offset_Primary_Type);
			primaryType=rfData.readByte();
		}
	}
	
	public void initNewDatabase(byte _primaryType,String _primaryName,BGColumn[] listColumn) throws IOException {
		primaryType=_primaryType;
		rfData.seek(Offset_DatabaseType);
		rfData.writeByte(DBTYPE_1Primary);
		
		rfData.seek(Offset_Primary_Type);
		rfData.writeByte(_primaryType);
		
		rfData.seek(Offset_Primary_Name);
		rfData.writeUTF(_primaryName);
		
		rfData.setLength(offsetDescribe);
		writeBGColumn(listColumn);
	}
	
	private Indexing1Primary btree;
	public void initBtree() throws IOException {if(btree==null)btree=new Indexing1Primary(primaryType,rfData,rfBTree);}

	public long querryOffset(Object primaryKey) throws IOException {
		if(validateType(primaryType,primaryKey)==false)
			throw new IOException("DBTable_1Primary → querryOffset(Object primaryKey) " + DBDefine_DataType.getTypeName(primaryType) + " : " + primaryKey);
		
		initBtree();
		return btree.querryOffset(primaryKey);
	}
	
//	public long querryIndex(Object primaryKey) throws IOException {
//		if(validateType(primaryType,primaryKey)==false)
//			throw new IOException("DBTable_1Primary → querryOffset(Object primaryKey) " + DBDefine_DataType.getTypeName(primaryType) + " : " + primaryKey);
//		
//		initBtree();
//		return btree.querryIndex(primaryKey);
//	}
	
	
	public void skipPrimary() throws IOException {skipType(primaryType);}
	
	/*
	 * 1. Validate : None/Indexing/Unique → listIndexingType + Indexing1Primary + listValue
	 * 2. write value
	 * 3. indexing
	 * */
	public long insert(Object primaryValue,BGAttribute... listObWriter) throws IOException {//Return offset
		if (validateType(primaryType, primaryValue) == false) {
			System.err.println("DBTable_1Primary → insert(Object primaryKey) " + DBDefine_DataType.getTypeName(primaryType) + " : " + primaryValue.getClass().getSimpleName());
			return -1;
		}
		int numberColumn=0;
		long offsetInsert=0;
		try {numberColumn=numberColumn();offsetInsert = rfData.length();} catch (IOException e) {e.printStackTrace();return -1;}
		
		
		initBtree();
		//////////////////////////////////////////////////////////////////////////////////Trường hợp không có dataLength
		if(numberColumn==0) {
			if(btree.insertIndex_UNIQUE(offsetInsert, primaryType, primaryValue)==false)
				return -1;
			rfData.writeLong(offsetInsert);//offset của primary → index tìm primary
			return offsetInsert;
		}
		
		//////////////////////////////////////////////////////////////////////////////////Trường hợp row có data
		insertStep1_InitListObjectAndValidate(listObWriter);
		
		if(btree.insertIndex_UNIQUE(offsetInsert, primaryType, primaryValue)==false)
			return -1;
		
		long offsetBegin = rfData.length();
		insertStep2_WriteAttribute(offsetBegin);
		
		rfData.seek(offsetBegin + getDataLength());
		rfData.writeLong(offsetInsert);//offset của primary → index tìm primary
		return offsetInsert;
	}
	public void updateAttribute(Object primaryValue,BGAttribute... listAttributes) throws IOException {
		long offsetPrimary = querryOffset(primaryValue);
		if(offsetPrimary==-1)
			throw new IOException(primaryValue + " not exist in "+pathDB);
		
		skipPrimary(offsetPrimary);
		offsetPrimary = rfData.getFilePointer();
		
		updateAttributeWithBeginDataRow(offsetPrimary, listAttributes);
	}
	public void delete(Object primaryValue) throws IOException {
		initBtree();
		long indexPrimary = btree.querryIndex(primaryValue);
		if(indexPrimary==-1)
			throw new IOException("PrimaryValue("+primaryValue+") is not exist in "+pathDB);
		
		long offsetBeginData = rfData.getFilePointer();
		btree.removeIndex(indexPrimary);
//		rfBTree.seek(indexPrimary*8);
//		skipPrimary(rfBTree.readLong());
		deleteIndexingAtBeginDataRow(offsetBeginData);
	}
	
	
	public void changingStructureColumn(BGColumn[] newDes,DBTable_1Primary dbTmp) throws IOException {
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
		Object primary;
		for(long i=0;i<numberRow;i++) {
			dbTmp.rfData.seek(dbTmp.getOffsetInBtree(i));
			primary=dbTmp.readObject(dbTmp.getOffsetInBtree(i), primaryType);
			beginOld=dbTmp.rfData.getFilePointer();
			for(BGDTO_Att dto:attDuplication)
				dto.Value = dbTmp.readObject(beginOld + dto.oldOffsetRow, dto.oldType);
			
			insert(primary, attDuplication);
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
	public ArrayList<Object> loadRow(long offset, BGColumn[] listDes) throws IOException {
		ArrayList<Object> list=new ArrayList<>();
		rfData.seek(offset);
		list.add(readObject(primaryType));
		
		long _offsetBeginData = rfData.getFilePointer();
		if(listDes!=null) {
//			int numberDes=listDes.length;
//			for(int i=0;i<numberDes;i++)
//				list.add(readValue(_offsetBeginData+listDes[i].OffsetRow,listDes[i].Type));
			for(BGColumn des:listDes)
				if(des.Type==DBDefine_DataType.IPV4) {
					list.add(InetAddress.getByAddress(readBytes(_offsetBeginData+des.OffsetRow,4)));
				}else if(des.Type==DBDefine_DataType.IPV6) {
					list.add(InetAddress.getByAddress(readBytes(_offsetBeginData+des.OffsetRow,16)));
				}else
					list.add(readObject(_offsetBeginData+des.OffsetRow,des.Type));
		}
		return list;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public Object readPrimaryAtIndex(long index) throws IOException {
//		rfBTree.seek(index * 8);
//		return readValue(rfBTree.readLong(), Type);
//	}
	public Object readPrimaryAtOffset(long _offset) throws IOException {
		return readObject(_offset, primaryType);
	}
	
	private String readPrimaryAsString(long offsetPrimary) throws IOException {
		Object primary = readPrimaryAtOffset(offsetPrimary);
		if((99<primaryType && primaryType<120) || primaryType == DBDefine_DataType.IPV6)
			return Arrays.toString((byte[])primary).replace(" ", "");
		else
			return primary.toString();
	}
	
	public void traceRows(long... listOffset) throws IOException {
		BGColumn[] list = readBGColumn();
		if (list == null || listOffset==null) {
			System.out.println("**************************No Data**************************");
			return;
		}

		int STT = (listOffset.length+"").length();
		
		String primary;
		int countCharRowId = 0;
		if (listOffset != null)
			for (long offset : listOffset) {
				primary=readPrimaryAsString(offset);
				if (countCharRowId < primary.length())
					countCharRowId = primary.length();
			}
		for (int i = 0; i < countCharRowId; i++)
			System.out.print(" ");// RowId

		int numberColumn = list.length;
		BGColumn describe;
		System.out.printf("%" + STT + "." + STT + "s   ", "");
		for (int i = 0; i < numberColumn; i++) {
			describe = list[i];
			System.out.printf("  %" + describe.traceLength() + "." + describe.traceLength() + "s", describe.ColumnName);
		}
		System.out.println("");

		int countSTT=1;
		long offsetBeginData;
		for (long offset : listOffset) {
			primary=readPrimaryAsString(offset);
			offsetBeginData=rfData.getFilePointer();
			System.out.printf("%" + STT + "." + STT + "s.  %" + countCharRowId + "." + countCharRowId + "s",countSTT++, primary);
			
			for (int i = 0; i < numberColumn; i++) {
				describe = list[i];
				if ((99<describe.Type && describe.Type<120) || describe.Type == DBDefine_DataType.IPV6)
					System.out.printf("  %" + describe.traceLength() + "." + describe.traceLength() + "s", Arrays.toString((byte[]) readObject(offsetBeginData+describe.OffsetRow, describe.Type)).replace(" ", ""));
				else
					System.out.printf("  %" + describe.traceLength() + "." + describe.traceLength() + "s", readObject(offsetBeginData+describe.OffsetRow, describe.Type));
			}
			System.out.println();
		}
	}

	
}
