package backendgame.com.database;

import java.io.IOException;

import backendgame.com.core.database.BGColumn;

public abstract class BGBaseDatabaseHeader extends BGBaseDatabase {
	protected long offsetDescribe;
	
	public BGColumn readBGColumnByIndex(int index) throws IOException {
		BGColumn describe=new BGColumn();
		
		rfData.seek(offsetDescribe + 25 + index*BGColumn.DES_SIZE);
		describe.OffsetRow = rfData.readInt();			//4
		describe.ViewId = rfData.readInt();				//4
		describe.Indexing = rfData.readByte();			//1
		rfData.readByte();								//1 UnUse
		rfData.readByte();								//1 UnUse
		rfData.readByte();								//1 UnUse
		describe.Size = rfData.readInt();				//4
		describe.Type = rfData.readByte();				//1
		describe.ColumnName = rfData.readUTF();
		
		if(describe.Size>0) {
			describe.BinaryData=new byte[describe.Size];
			rfData.seek(getOffset_DefaultData() + describe.OffsetRow);
			rfData.read(describe.BinaryData);
		}
		return describe;
	}
	
	protected void writeBGColumn(BGColumn[] list) throws IOException {//describe.Offset của row chứ không phải offset của Describe
		if(list==null) {
			rfData.seek(offsetDescribe);
			rfData.writeLong(offsetDescribe+25);//offsetBeginData
			rfData.writeLong(offsetDescribe+25);//offsetDefaultData
			rfData.writeByte(0);//DatabaseType
			rfData.writeInt(0);//DataLength
			rfData.writeInt(0);//number Column
			rfData.setLength(offsetDescribe+25);
		}else {
			int numberColumn=list.length;
			rfData.seek(offsetDescribe+21);
			rfData.writeInt(numberColumn);
			int dataLength=0;
			/////////////////////////////////////////////////////
			//Write mô tả ///////////////////////////////////////
			/////////////////////////////////////////////////////
			BGColumn describe;
			for(int i=0;i<numberColumn;i++) {
				describe=list[i];
				
				if(describe.Size<describe.BinaryData.length)
					describe.Size = describe.BinaryData.length;
				
				describe.OffsetRow = dataLength;//Vị trí đúng của offset trước khi cộng Size
				dataLength+=describe.Size;
				
				rfData.seek(offsetDescribe + 25 + i*BGColumn.DES_SIZE);
				rfData.writeInt(describe.OffsetRow);		//4
				rfData.writeInt(describe.ViewId);			//4
				rfData.writeByte(describe.Indexing);		//1
				rfData.writeByte(0);						//1 UnUse
				rfData.writeByte(0);						//1 UnUse
				rfData.writeByte(0);						//1 UnUse
				rfData.writeInt(describe.Size);				//4
				rfData.writeByte(describe.Type);			//1
				rfData.writeUTF(describe.ColumnName);
			}
			
			/////////////////////////////////////////////////////
			//Write header///////////////////////////////////////
			/////////////////////////////////////////////////////
			long offsetDefaultData = offsetDescribe + 25 + numberColumn*BGColumn.DES_SIZE;
			long offsetBeginData = offsetDefaultData + dataLength;
			rfData.seek(offsetDescribe);
			rfData.writeLong(offsetBeginData);
			rfData.writeLong(offsetDefaultData);
			rfData.writeByte(0);//DatabaseType
			rfData.writeInt(dataLength);//DataLength
			
			/////////////////////////////////////////////////////
			//Write Default data/////////////////////////////////
			/////////////////////////////////////////////////////
			for(int i=0;i<numberColumn;i++) {
				describe=list[i];
				if(describe.BinaryData!=null) {
					rfData.seek(offsetDefaultData + describe.OffsetRow);//Vị trí của DefaultData
					rfData.write(describe.BinaryData);
				}
			}
			
			if(rfData.length() < offsetBeginData)
				rfData.setLength(offsetBeginData);//Trường hợp String và Array init không đủ số byte → countRow bị sai
		}
	}
	
	
	public String getCloumnName(int indexDescribe) throws IOException {rfData.seek(offsetDescribe + 25 + indexDescribe*BGColumn.DES_SIZE + 17);return rfData.readUTF();}
	public void setCloumnName(int indexDescribe, String ColumnName) throws IOException {rfData.seek(offsetDescribe + 25 + indexDescribe*BGColumn.DES_SIZE + 17);rfData.writeUTF(ColumnName);}
	public void setCloumnViewId(int indexDescribe, int viewId) throws IOException {rfData.seek(offsetDescribe + 25 + indexDescribe*BGColumn.DES_SIZE + 4);rfData.writeInt(viewId);}

	public int getCloumn_DataOffset(int indexDescribe) throws IOException {rfData.seek(offsetDescribe + 25 + indexDescribe*BGColumn.DES_SIZE);return rfData.readInt();}
	public byte getCloumn_Indexing(int indexDescribe) throws IOException {rfData.seek(offsetDescribe + 25 + indexDescribe*BGColumn.DES_SIZE + 8);return rfData.readByte();}
	public void setCloumn_Indexing(int indexDescribe, byte value) throws IOException {rfData.seek(offsetDescribe + 25 + indexDescribe*BGColumn.DES_SIZE + 8);rfData.writeByte(value);}
	public int getCloumn_DataSize(int indexDescribe) throws IOException {rfData.seek(offsetDescribe + 25 + indexDescribe*BGColumn.DES_SIZE + 12);return rfData.readInt();}
	public byte getColumn_DataType(int indexDescribe) throws IOException {rfData.seek(offsetDescribe + 25 + indexDescribe*BGColumn.DES_SIZE + 16);return rfData.readByte();}
	
	

	
	public BGColumn findColumn(String ColumnName) throws IOException {
		int index = findColumnIndex(ColumnName);
		if(index==-1)
			return null;
		return readBGColumnByIndex(index);
	}
	public int findColumnIndex(String ColumnName) throws IOException {
		if(ColumnName==null)
			ColumnName="";
		int numberColumn = numberColumn();
		for(int i=0;i<numberColumn;i++) {
			rfData.seek(offsetDescribe + 25 + i*BGColumn.DES_SIZE + 17);
			if(ColumnName.equals(rfData.readUTF()))
				return i;
		}
		return -1;
	}
	
	public long getOffset_BeginData() throws IOException {rfData.seek(offsetDescribe);return rfData.readLong();}
	protected long getOffset_DefaultData() throws IOException {rfData.seek(offsetDescribe+8);return rfData.readLong();}
	
	public int getDataLength() throws IOException {rfData.seek(offsetDescribe+17);return rfData.readInt();}
	public int numberColumn() throws IOException {rfData.seek(offsetDescribe+21);return rfData.readInt();}
	
	public BGColumn[] readBGColumn() throws IOException {
		int numberColumn=numberColumn();
		if(numberColumn==0)
			return null;
		
		BGColumn[] list=new BGColumn[numberColumn];
		for(int i=0;i<numberColumn;i++)
			list[i] = readBGColumnByIndex(i);
		return list;
	}
	
	public byte[] getDefaultData() throws IOException {
		int dataLength = getDataLength();
		if (dataLength == 0)
			return null;
		byte[] data = new byte[dataLength];
		rfData.seek(getOffset_DefaultData());
		rfData.read(data);
		return data;
	}
}
