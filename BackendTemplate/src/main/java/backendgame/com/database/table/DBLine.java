package backendgame.com.database.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import backendgame.com.core.BGCastObject;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGBytesConverter;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.entity.LineNode;

public class DBLine extends BGBaseDatabase{
	protected RandomAccessFile rfDes;
	public DBLine(String _path) throws FileNotFoundException {
		pathDB=_path;
		rfDes=new RandomAccessFile(pathDB+INDEX_EXTENSION, "rw");
		rfData=new RandomAccessFile(pathDB+DATA_EXTENSION, "rw");
	}
	
	public void initNewDatabase() throws IOException {
		rfData.setLength(0);
		rfData.setLength(HEADER);
	}
	
	/*Des : 21 byte
	 * 
	 * long OffsetData
	 * byte Type
	 * int Size
	 * long Description
	 * */
	public static final int DES_SIZE = 21;
	private long insertNode(DBString dbString, String Description,byte Type,int Size, long offsetData) throws IOException {
		long id = count();
		rfDes.seek(id*DES_SIZE);
		rfDes.writeLong(offsetData);
		rfDes.writeByte(Type);
		rfDes.writeInt(Size);
		rfDes.writeLong(dbString.getOffset(Description));
		return id;
	}
	public LineNode getLineNode(long id, DBString dbString) throws IOException {
		LineNode lineNode=new LineNode();
		rfDes.seek(id*DES_SIZE);
		rfData.seek(rfDes.readLong());
		lineNode.Type = rfDes.readByte();
		lineNode.Size = rfDes.readInt();
		lineNode.Description = dbString.readStringByOffset(rfDes.readLong());
		
		byte Type = lineNode.Type;
		if(0<Type && Type<10)
			lineNode.value = rfData.readBoolean();
		else if(9<Type && Type<20)
			lineNode.value =  rfData.readByte();
		else if(19<Type && Type<40)
			lineNode.value = rfData.readShort();
		else if(39<Type && Type<60)
			lineNode.value = rfData.readInt();
		else if(59<Type && Type<80)
			lineNode.value = rfData.readFloat();
		else if(79<Type && Type<90)
			lineNode.value = rfData.readLong();
		else if(89<Type && Type<100)
			lineNode.value = rfData.readDouble();
		else if((99<Type && Type<120) || Type==DBDefine_DataType.IPV6) {
			int _len = rfData.readInt();
			if(_len==0)
				lineNode.value=null;
			else {
				lineNode.value = new byte[_len];
				rfData.read((byte[])lineNode.value);
			}
		}else if(Type==DBDefine_DataType.STRING)
			lineNode.value = rfData.readUTF();
		else
			throw new IOException("invalid Type "+DBDefine_DataType.getTypeName(Type)+" : DatabaseLine → getLineNode(long id, DBString dbString)");
		
		return lineNode;
	}
	public void removeLineNode(long id) throws IOException {
		rfDes.seek(id*DES_SIZE);
		rfDes.writeLong(-1);
	}
	public void updateNodeDescription(DBString dbString, long id, String Description) throws IOException {
		rfDes.seek(id*DES_SIZE + 13);//offsetData(8) + Type(1) + Size(4)
		rfDes.writeLong(dbString.getOffset(Description));
	}
	
	public boolean validateTypeSize(long id, byte Type, int Size) throws IOException {
		rfDes.seek(id*DES_SIZE + 8);//offsetData(8)
		return Type==rfDes.readByte() && Size<=rfDes.readInt();
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long insert(DBString dbString, String Description,byte Type, boolean value) throws IOException {
		if(!(0<Type && Type<10))
			throw new IOException("invalid value "+DBDefine_DataType.getTypeName(Type)+" with BOOLEAN : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, boolean value)");
		long offsetData = rfData.length();
		rfData.seek(offsetData);
		rfData.writeBoolean(value);
		return insertNode(dbString, Description, Type, 1, offsetData);
	}
	public long insert(DBString dbString, String Description,byte Type, byte value) throws IOException {
		if(!(9<Type && Type<20))
			throw new IOException("invalid value "+DBDefine_DataType.getTypeName(Type)+" with BYTE : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, byte value)");
		long offsetData = rfData.length();
		rfData.seek(offsetData);
		rfData.writeByte(value);
		return insertNode(dbString, Description, Type, 1, offsetData);
	}
	public long insert(DBString dbString, String Description,byte Type, short value) throws IOException {
		if(!(19<Type && Type<40))
			throw new IOException("invalid value "+DBDefine_DataType.getTypeName(Type)+" with SHORT : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, short value)");
		long offsetData = rfData.length();
		rfData.seek(offsetData);
		rfData.writeShort(value);
		return insertNode(dbString, Description, Type, 2, offsetData);
	}
	public long insert(DBString dbString, String Description,byte Type, int value) throws IOException {
		if(!(39<Type && Type<60))
			throw new IOException("invalid value "+DBDefine_DataType.getTypeName(Type)+" with INTEGER : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, int value)");
		long offsetData = rfData.length();
		rfData.seek(offsetData);
		rfData.writeInt(value);
		return insertNode(dbString, Description, Type, 4, offsetData);
	}
	public long insert(DBString dbString, String Description,byte Type, float value) throws IOException {
		if(!(59<Type && Type<80))
			throw new IOException("invalid value "+DBDefine_DataType.getTypeName(Type)+" with FLOAT : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, float value)");
		long offsetData = rfData.length();
		rfData.seek(offsetData);
		rfData.writeFloat(value);
		return insertNode(dbString, Description, Type, 4, offsetData);
	}
	public long insert(DBString dbString, String Description,byte Type, long value) throws IOException {
		if(!(79<Type && Type<90))
			throw new IOException("invalid value "+DBDefine_DataType.getTypeName(Type)+" with LONG : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, long value)");
		long offsetData = rfData.length();
		rfData.seek(offsetData);
		rfData.writeLong(value);
		return insertNode(dbString, Description, Type, 8, offsetData);
	}
	public long insert(DBString dbString, String Description,byte Type, double value) throws IOException {
		if(!(89<Type && Type<100))
			throw new IOException("invalid value "+DBDefine_DataType.getTypeName(Type)+" with DOUBLE : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, double value)");
		long offsetData = rfData.length();
		rfData.seek(offsetData);
		rfData.writeDouble(value);
		return insertNode(dbString, Description, Type, 8, offsetData);
	}
	public long insert(DBString dbString, String Description,byte Type,int Size, String value) throws IOException {
		if(Type!=DBDefine_DataType.STRING)
			throw new IOException("invalid value "+DBDefine_DataType.getTypeName(Type)+" with String : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, String value)");
		if(value.length()>Size)
			throw new IOException("invalid value.length()="+value.length()+" > "+Size+" : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, String value)");
		
		long offsetData = rfData.length();
		rfData.seek(offsetData);
		rfData.writeUTF(value);
		return insertNode(dbString, Description, Type, Size, offsetData);
	}
	public long insert(DBString dbString, String Description,byte Type,int Size, byte[] value) throws IOException {
		if((Type<100 || 119<Type) && Type!=DBDefine_DataType.IPV6)
			throw new IOException("invalid value "+DBDefine_DataType.getTypeName(Type)+" with String : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, byte[] value)");
		
		long offsetData = rfData.length();
		rfData.seek(offsetData);
		if(value==null)
			rfData.writeInt(0);
		else{
			int _len=value.length;
			if(_len>Size)
				throw new IOException("invalid value.length="+_len+" > "+Size+" : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, byte[] value)");
			rfData.writeInt(value.length);
			rfData.write(value);
		}
		return insertNode(dbString, Description, Type, Size, offsetData);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void updateValue(long id, boolean value) throws IOException {
		rfDes.seek(id*DES_SIZE);
		long offsetData = rfDes.readLong();
		byte Type = rfDes.readByte();
		if(0<Type && Type<10) {
			rfData.seek(offsetData);
			rfData.writeBoolean(value);
		}else
			throw new IOException("invalid "+DBDefine_DataType.getTypeName(Type)+" with Boolean : DatabaseLine → updateValue(long id, boolean value)");
	}
	public void updateValue(long id, byte value) throws IOException {
		rfDes.seek(id*DES_SIZE);
		long offsetData = rfDes.readLong();
		byte Type = rfDes.readByte();
		if(9<Type && Type<20) {
			rfData.seek(offsetData);
			rfData.writeByte(value);
		}else
			throw new IOException("invalid "+DBDefine_DataType.getTypeName(Type)+" with Byte : DatabaseLine → updateValue(long id, byte value)");
	}
	public void updateValue(long id, short value) throws IOException {
		rfDes.seek(id*DES_SIZE);
		long offsetData = rfDes.readLong();
		byte Type = rfDes.readByte();
		if(19<Type && Type<40) {
			rfData.seek(offsetData);
			rfData.writeShort(value);
		}else
			throw new IOException("invalid "+DBDefine_DataType.getTypeName(Type)+" with Short : DatabaseLine → updateValue(long id, short value)");
	}
	public void updateValue(long id, int value) throws IOException {
		rfDes.seek(id*DES_SIZE);
		long offsetData = rfDes.readLong();
		byte Type = rfDes.readByte();
		if(39<Type && Type<60) {
			rfData.seek(offsetData);
			rfData.writeInt(value);
		}else
			throw new IOException("invalid "+DBDefine_DataType.getTypeName(Type)+" with Integer : DatabaseLine → updateValue(long id, int value)");
	}
	public void updateValue(long id, float value) throws IOException {
		rfDes.seek(id*DES_SIZE);
		long offsetData = rfDes.readLong();
		byte Type = rfDes.readByte();
		if(59<Type && Type<80) {
			rfData.seek(offsetData);
			rfData.writeFloat(value);
		}else
			throw new IOException("invalid "+DBDefine_DataType.getTypeName(Type)+" with Float : DatabaseLine → updateValue(long id, float value)");
	}
	public void updateValue(long id, long value) throws IOException {
		rfDes.seek(id*DES_SIZE);
		long offsetData = rfDes.readLong();
		byte Type = rfDes.readByte();
		if(79<Type && Type<90) {
			rfData.seek(offsetData);
			rfData.writeLong(value);
		}else
			throw new IOException("invalid "+DBDefine_DataType.getTypeName(Type)+" with Long : DatabaseLine → updateValue(long id, long value)");
	}
	public void updateValue(long id, double value) throws IOException {
		rfDes.seek(id*DES_SIZE);
		long offsetData = rfDes.readLong();
		byte Type = rfDes.readByte();
		if(89<Type && Type<100) {
			rfData.seek(offsetData);
			rfData.writeDouble(value);
		}else
			throw new IOException("invalid "+DBDefine_DataType.getTypeName(Type)+" with Double : DatabaseLine → updateValue(long id, double value)");
	}
	public void updateValue(long id, byte[] value) throws IOException {
		rfDes.seek(id*DES_SIZE);
		long offsetData = rfDes.readLong();
		byte Type = rfDes.readByte();
		if((99<Type && Type<120) || Type==DBDefine_DataType.IPV6) {
			rfData.seek(offsetData);
			if(value==null)
				rfData.writeInt(0);
			else {
				rfData.writeInt(value.length);
				rfData.write(value);
			}
		}else
			throw new IOException("invalid "+DBDefine_DataType.getTypeName(Type)+" with ByteArray : DatabaseLine → updateValue(long id, byte[] value)");
	}
	
	public void updateValue(long id, Object value) throws IOException {
		rfDes.seek(id*DES_SIZE);
		rfData.seek(rfDes.readLong());
		byte Type = rfDes.readByte();
		if(0<Type && Type<10)
			rfData.writeBoolean(BGCastObject.toBoolean(value));
		else if(9<Type && Type<20)
			rfData.writeByte(BGCastObject.toByte(value));
		else if(19<Type && Type<40)
			rfData.writeShort(BGCastObject.toShort(value));
		else if(39<Type && Type<60)
			rfData.writeInt(BGCastObject.toInteger(value));
		else if(59<Type && Type<80)
			rfData.writeFloat(BGCastObject.toFloat(value));
		else if(79<Type && Type<90)
			rfData.writeLong(BGCastObject.toLong(value));
		else if(89<Type && Type<100)
			rfData.writeDouble(BGCastObject.toDouble(value));
		else if((99<Type && Type<120) || Type==DBDefine_DataType.IPV6) {
			byte[] tmp=BGBytesConverter.toByteArray(value);
			if(value==null)
				rfData.writeInt(0);
			else {
				rfData.writeInt(tmp.length);
				rfData.write(tmp);
			}
		}else if(Type==DBDefine_DataType.STRING)
			rfData.writeUTF((String) value);
		else
			throw new IOException("invalid "+DBDefine_DataType.getTypeName(Type)+" with "+value.getClass().getSimpleName()+" : DatabaseLine → updateValue(long id, Object value)");
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Object readValue(long id) throws IOException {
		rfDes.seek(id*DES_SIZE);
		long offsetData = rfDes.readLong();
		if(offsetData==-1)
			return null;
		
		rfData.seek(offsetData);
		byte Type = rfDes.readByte();
		if(0<Type && Type<10)
			return rfData.readBoolean();
		else if(9<Type && Type<20)
			return rfData.readByte();
		else if(19<Type && Type<40)
			return rfData.readShort();
		else if(39<Type && Type<60)
			return rfData.readInt();
		else if(59<Type && Type<80)
			return rfData.readFloat();
		else if(79<Type && Type<90)
			return rfData.readLong();
		else if(89<Type && Type<100)
			return rfData.readDouble();
		else if((99<Type && Type<120) || Type==DBDefine_DataType.IPV6) {
			int _len = rfData.readInt();
			if(_len==0)
				return null;
			byte[] _data = new byte[_len];
			rfData.read(_data);
			return _data;
		}else if(Type==DBDefine_DataType.STRING)
			return rfData.readUTF();
		
		throw new IOException("invalid Type("+DBDefine_DataType.getTypeName(Type)+") : DatabaseLine → insert(DBString dbString, String Description,byte Type,int Size, String value)");
	}
	
	
	public RandomAccessFile getRandomAccessFile() {return rfData;}
	public long count() throws IOException {return rfDes.length()/DES_SIZE;}
	@Override public void closeDatabase() {
		super.closeDatabase();
		if(rfDes!=null)try {rfDes.close();} catch (IOException e) {}
	}

	@Override public void deleteAndClose() {
		try {
			Files.deleteIfExists(FileSystems.getDefault().getPath(pathDB + DATA_EXTENSION));
			Files.deleteIfExists(FileSystems.getDefault().getPath(pathDB + INDEX_EXTENSION));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
