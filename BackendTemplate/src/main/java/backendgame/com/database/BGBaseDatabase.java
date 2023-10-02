package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;

import backendgame.com.core.BGCastObject;
import backendgame.com.core.DBDefine_DataType;

public abstract class BGBaseDatabase {
	public static final String DATA_EXTENSION = ".data";
	public static final String INDEX_EXTENSION = ".index";
	
	public static final byte DBTYPE_1Primary		 	= 1;
	public static final byte DBTYPE_2Primary 			= 2;
	public static final byte DBTYPE_Row 				= 3;
	public static final byte DBTYPE_LineNode			= 5;
	public static final byte DBTYPE_LeaderboardLink		= 6;
	public static final byte DBTYPE_LeaderboardCommit	= 8;
	public static final byte DBTYPE_Log					= 9;
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final long Offset_DatabaseType		 	= 0;//1 byte
	public static final long Offset_TimeCreate		 		= 1;//8 byte
	public static final long Offset_TimeLock				= 9;//8 byte
	public static final long Offset_TimeProcessing			= 17;//8 byte
	
	public static final long Offset_Version					= 92;//8 byte

	public static final long Offset_AccessKey			 	= 100;//8 byte
	public static final long Offset_ReadKey				 	= 108;//8 byte
	public static final long Offset_WriteKey			 	= 116;//8 byte
	
	public static final long Offset_Permission	 			= 124;//1 byte
	public static final long Offset_LogoutId	 			= 125;//1 byte
	
	public static final long Offset_Tracking_AllTime 		= 488;//8 byte
	public static final long Offset_Tracking_Yesterday 		= 496;//8 byte
	public static final long Offset_Tracking_ThisMonth 		= 504;//8 byte
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final long HEADER= 1024;//Another data
	
	public String pathDB;
	protected RandomAccessFile rfData;
	
	
	public byte readByte(long offset) throws IOException {rfData.seek(offset);return rfData.readByte();}
	public void writeByte(long offset,byte value) throws IOException {rfData.seek(offset);rfData.write(value);}
	public long readLong(long offset) throws IOException {rfData.seek(offset);return rfData.readLong();}
	public void writeLong(long offset,long value) throws IOException {rfData.seek(offset);rfData.writeLong(value);}
	public String readUTF(long offset) throws IOException {rfData.seek(offset);return rfData.readUTF();}
	public void writeUTF(long offset,String value) throws IOException {rfData.seek(offset);rfData.writeUTF(value);}
	
	
    public void readBytes(long _offset,byte[] _data) throws IOException {
    	rfData.seek(_offset);
		rfData.read(_data);
    }
    public byte[] readBytes(long _offset,int length) throws IOException {
		byte[] _data=new byte[length];
		readBytes(_offset, _data);
		return _data;
	}
    public void writeBytes(long _offset,byte[] _data) throws IOException {
    	rfData.seek(_offset);
		rfData.write(_data);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Object readObject(byte Type) throws IOException {
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
    	else if(99<Type && Type<120) {
    		int size = rfData.readInt();
    		if(size>0) {
    			byte[] result = new byte[size];
    			rfData.read(result);
    			return result;
    		}else
    			return null;
    	}else if(Type==DBDefine_DataType.STRING)
    		return rfData.readUTF();
    	else if(Type==DBDefine_DataType.IPV6) {
    		byte[] ipv6 = new byte[16];
    		rfData.read(ipv6);
    		return ipv6;
    	}else
    		throw new IOException("Database error DBProcess_Describe → readData(byte Type) " + DBDefine_DataType.getTypeName(Type));
    }
    public Object readObject(long offset, byte Type) throws IOException {
    	rfData.seek(offset);
    	return readObject(Type);
    }
    public byte[] readType(long offset, byte Type) throws IOException {
    	byte[] result=null;
    	rfData.seek(offset);
		if(0<Type && Type<10) {
			result=new byte[1];
			rfData.read(result);
		}else if(9<Type && Type<20) {
			result=new byte[1];
			rfData.read(result);
		}else if(19<Type && Type<40) {
			result=new byte[2];
			rfData.read(result);
		}else if(39<Type && Type<60) {
			result=new byte[4];
			rfData.read(result);
		}else if(59<Type && Type<80) {
			result=new byte[4];
			rfData.read(result);
		}else if(79<Type && Type<90) {
			result=new byte[8];
			rfData.read(result);
		}else if(89<Type && Type<100) {
			result=new byte[8];
			rfData.read(result);
		}else if(99<Type && Type<120) {
            int size = rfData.readInt();
            if(size>0) {
                result = new byte[size];
                rfData.read(result);
            }
		}else if(Type==DBDefine_DataType.STRING) {
			int size = rfData.readUnsignedShort();
			if(size>0) {
                result = new byte[size];
                rfData.read(result);
            }
		}else if(Type==DBDefine_DataType.IPV6) {
			result = new byte[16];
             rfData.read(result);
		}else
			throw new IOException("Database error DBProcess_Describe → readData(long offset, byte Type) " + DBDefine_DataType.getTypeName(Type));
		
		return result;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void baseWriting(long offset, byte Type, Object value) throws IOException {
		rfData.seek(offset);
		baseWriting(Type, value);
	}
	public void baseWriting(byte Type, Object value) throws IOException {
		if(0<Type && Type<10) {
			if(value==null)throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : value is null");
			Class<?>c = value.getClass();
			if(c==Boolean.class)
				rfData.writeBoolean((boolean) value);
			else if(c==Double.class)//Dùng cho RestAPI
				rfData.writeByte(((Double)value).byteValue());
			else if(c==String.class)
				rfData.writeBoolean(Boolean.parseBoolean((String) value));
			else if(c==Byte.class)
				rfData.writeByte((byte)value);
			else if(c==Short.class)
				rfData.writeByte((short)value);
			else if(c==Integer.class)
				rfData.writeByte((int)value);
			else if(c==Float.class)
				rfData.writeByte(((Float)value).byteValue());
			else if(c==Long.class)
				rfData.writeByte(((Long)value).byteValue());
			else
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : invalid value : "+c.getName());
//			rfData.writeBoolean(BGCastObject.toBoolean(value));
		}else if(9<Type && Type<20) {
			if(value==null)throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : value is null");
			Class<?>c = value.getClass();
			if(c==Byte.class)
				rfData.writeByte((byte)value);
			else if(c==Double.class)//Dùng cho RestAPI
				rfData.writeByte(((Double)value).byteValue());
			else if(c==String.class)
				rfData.writeByte(Byte.parseByte((String) value));
			else if(c==Short.class)
				rfData.writeByte((short)value);
			else if(c==Integer.class)
				rfData.writeByte((int)value);
			else if(c==Float.class)
				rfData.writeByte(((Float)value).byteValue());
			else if(c==Long.class)
				rfData.writeByte(((Long)value).byteValue());
			else
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : invalid value : "+c.getName());
//			rfData.writeByte(BGCastObject.toByte(value));
		}else if(19<Type && Type<40) {
			if(value==null)throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : value is null");
			Class<?>c = value.getClass();
			if(c==Short.class)
				rfData.writeShort((short) value);
			else if(c==byte[].class) {
				byte[] data=(byte[]) value;
				if(data.length==2)
					rfData.write(data);
				else
					throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : type Short 2 byte != "+data.length);
			}else if(c==Double.class)//Dùng cho RestAPI
				rfData.writeShort(((Double)value).shortValue());
			else if(c==String.class)
				rfData.writeShort(Short.parseShort((String) value));
			else if(c==Byte.class)
				rfData.writeShort((byte)value);
			else if(c==Integer.class)
				rfData.writeShort((int)value);
			else if(c==Float.class)
				rfData.writeShort(((Float)value).shortValue());
			else if(c==Long.class)
				rfData.writeShort(((Long)value).shortValue());
			else
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : invalid value : "+c.getName());
//			rfData.writeShort(BGCastObject.toShort(value));
		}else if(39<Type && Type<60) {
			if(value==null)throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : value is null");
			Class<?>c = value.getClass();
			if(c==Integer.class)
				rfData.writeInt((int) value);
			else if(c==byte[].class) {
				byte[] data=(byte[]) value;
				if(data.length==4)
					rfData.write(data);
				else
					throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : type Integer 4 byte != "+data.length);
			}else if(c==Double.class)//Dùng cho RestAPI
				rfData.writeInt(((Double)value).intValue());
			else if(c==String.class)
				rfData.writeInt(Integer.parseInt((String) value));
			else if(c==Byte.class)
				rfData.writeInt((byte)value);
			else if(c==Short.class)
				rfData.writeInt((short)value);
			else if(c==Float.class)
				rfData.writeInt(((Float)value).intValue());
			else if(c==Long.class)
				rfData.writeInt(((Long)value).intValue());
			else
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : invalid value : "+c.getName());
//			rfData.writeInt(BGCastObject.toInteger(value));
		}else if(59<Type && Type<80) {
			if(value==null)throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : value is null");
			Class<?>c = value.getClass();
			if(c==Float.class)
				rfData.writeFloat((float) value);
			else if(c==byte[].class) {
				byte[] data=(byte[]) value;
				if(data.length==4)
					rfData.write(data);
				else
					throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : type Float 4 byte != "+data.length);
			}else if(c==Double.class)//Dùng cho RestAPI
				rfData.writeFloat(((Double)value).floatValue());
			else if(c==String.class)
				rfData.writeFloat(Float.parseFloat((String) value));
			else if(c==Byte.class)
				rfData.writeFloat((byte)value);
			else if(c==Short.class)
				rfData.writeFloat((short)value);
			else if(c==Integer.class)
				rfData.writeFloat((int)value);
			else if(c==Long.class)
				rfData.writeFloat(((Long)value).floatValue());
			else
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : invalid value : "+c.getName());
//			rfData.writeFloat(BGCastObject.toFloat(value));
		}else if(79<Type && Type<90) {
			if(value==null)throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : value is null");
			Class<?>c = value.getClass();
			if(c==Long.class)
				rfData.writeLong((long) value);
			else if(c==byte[].class) {
				byte[] data=(byte[]) value;
				if(data.length==8)
					rfData.write(data);
				else
					throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : type Long 8 byte != "+data.length);
			}else if(c==Double.class)//Dùng cho RestAPI
				rfData.writeLong(((Double)value).longValue());
			else if(c==String.class)
				rfData.writeLong(Long.parseLong((String) value));
			else if(c==Byte.class)
				rfData.writeLong((byte)value);
			else if(c==Short.class)
				rfData.writeLong((short)value);
			else if(c==Integer.class)
				rfData.writeLong((int)value);
			else if(c==Float.class)
				rfData.writeLong(((Float)value).longValue());
//			rfData.writeLong(BGCastObject.toLong(value));
		}else if(89<Type && Type<100) {
			if(value==null)throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : value is null");
			Class<?>c = value.getClass();
			if(c==Double.class)//Dùng cho RestAPI
				rfData.writeDouble((double) value);
			else if(c==byte[].class) {
				byte[] data=(byte[]) value;
				if(data.length==8)
					rfData.write(data);
				else
					throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : type Double 8 byte != "+data.length);
			}else if(c==String.class)
				rfData.writeDouble(Double.parseDouble((String) value));
			else if(c==Byte.class)
				rfData.writeDouble((byte)value);
			else if(c==Short.class)
				rfData.writeDouble((short)value);
			else if(c==Integer.class)
				rfData.writeDouble((int)value);
			else if(c==Float.class)
				rfData.writeDouble((float)value);
			else if(c==Long.class)
				rfData.writeDouble((long)value);
			else
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : invalid value : "+c.getName());
//			rfData.writeDouble(BGCastObject.toDouble(value));
		}else if(99<Type && Type<120) {
			if(value==null)
				rfData.writeInt(0);
			else if(value.getClass()==byte[].class) {
				byte[] _data = (byte[]) value;
				rfData.writeInt(_data.length);
				rfData.write(_data);
			}else {
				ArrayList<Object> list=(ArrayList<Object>) value;
				int length=list.size();
				rfData.writeInt(length);
				for(int i=0;i<length;i++)
					rfData.writeByte(BGCastObject.toByte(list.get(i)));
			}
		}else if(Type==DBDefine_DataType.STRING) {
			if (value == null)
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : value is null");
			else if(value.getClass()==String.class)
				rfData.writeUTF((String) value);
			else if(value.getClass()==byte[].class)
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : can't write byte[] to UTF");
			else
				rfData.writeUTF(value+"");
		}else if(Type==DBDefine_DataType.IPV6) {
			if (value == null)
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : value is null");
			
			Class<?>c = value.getClass();
			if(c==byte[].class) {
				byte[] _data = (byte[]) value;
				if(_data.length!=16)
					throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : Type IPV6.length 16 != "+((byte[])value).length);
				rfData.write(_data);
			}else if(c==ArrayList.class) {
				ArrayList<?> list = (ArrayList<?>) value;
				if(list.size()!=16)
					throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : Type IPV6.length 16 != "+((byte[])value).length);
				for(byte i=0;i<16;i++)
					rfData.writeByte(((Double)list.get(i)).byteValue());
			}else
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) : Type IPV6 must is byte[] → "+value.getClass().getSimpleName());
		}else
			throw new IOException("Database error DBProcess_Describe → writeData(long offset, byte Type, Object value) " + DBDefine_DataType.getTypeName(Type) + " : " + value);
	}
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void skipType(byte Type) throws IOException {if (0 < Type && Type < 10) {rfData.skipBytes(1);} else if (9 < Type && Type < 20) {rfData.skipBytes(1);} else if (19 < Type && Type < 40) {rfData.skipBytes(2);} else if (39 < Type && Type < 60) {rfData.skipBytes(4);} else if (59 < Type && Type < 80) {rfData.skipBytes(4);} else if (79 < Type && Type < 90) {rfData.skipBytes(8);} else if (89 < Type && Type < 100) {rfData.skipBytes(8);} else if (99 < Type && Type < 120) {rfData.skipBytes(rfData.readInt());} else if (Type == DBDefine_DataType.STRING) {rfData.skipBytes(rfData.readUnsignedShort());} else if (Type == DBDefine_DataType.IPV6) {rfData.skipBytes(16);} else throw new IOException("Type was not set → call method skipType(byte Type)");}
	
	public boolean islocked() throws IOException {return System.currentTimeMillis()<readLong(Offset_TimeLock);}
	public void unlockDatabase() throws IOException {writeLong(Offset_TimeLock,0);}
	public void lockDatabase(long timeLock) throws IOException {writeLong(Offset_TimeLock,System.currentTimeMillis()+timeLock);}
	public RandomAccessFile getRandomAccessFile() {return rfData;}
	
	public long length() throws IOException {return rfData.length();}
	
	
	public void closeDatabase() {if(rfData!=null)try {rfData.close();} catch (IOException e) {}}
	public void deleteAndClose() {
		closeDatabase();
		try {
			Files.deleteIfExists(FileSystems.getDefault().getPath(pathDB));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
