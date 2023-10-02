package backendgame.com.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import backendgame.com.database.BGBytesConverter;

public class BGCastObject {
	
	public Object castValueByType(byte Type,Object value) throws IOException{
		if(0<Type && Type<10)
			return toBoolean(value);
		else if(9<Type && Type<20)
			return toByte(value);
		else if(19<Type && Type<40)
			return toShort(value);
		else if(39<Type && Type<60)
			return toInteger(value);
		else if(59<Type && Type<80)
			return toFloat(value);
		else if(79<Type && Type<90)
			return toLong(value);
		else if(89<Type && Type<100)
			return toDouble(value);
		else if(99<Type && Type<120)
			return toByteArray(value);
		else if(Type==DBDefine_DataType.STRING) {
			return value;
		}else if(Type==DBDefine_DataType.IPV6)
			return toByteArray(value);
		else 
			throw new IOException("BGCastObject → castValueByType(byte Type,Object value) : "+DBDefine_DataType.getTypeName(Type) + " to "+value.getClass().getSimpleName());
	}
	
	
	public static final byte[] toByteArray(Object DefaultValue) {
		if(DefaultValue instanceof ArrayList) {
			ArrayList<?> test = (ArrayList<?>)DefaultValue;
			int numberArray = test.size();
			byte[] result = new byte[numberArray];
			for(int i=0;i<numberArray;i++)
				result[i]=toByte(test.get(i));
			return result;
		}else
			return (byte[]) DefaultValue;
//		ArrayList
	}
	
	
	
	public static final double toDouble(Object DefaultValue) {
		if(DefaultValue instanceof Double)
			return (double) DefaultValue;
		
		if(DefaultValue instanceof Long)
			return ((Long)DefaultValue).doubleValue();
		
		if(DefaultValue instanceof Float)
			return ((Float)DefaultValue).doubleValue();
		
		if(DefaultValue instanceof Integer)
			return ((Integer)DefaultValue).doubleValue();
		
		if(DefaultValue instanceof Short)
			return ((Short)DefaultValue).doubleValue();
		
		if(DefaultValue instanceof Byte)
			return ((Byte)DefaultValue).doubleValue();
		
		if(DefaultValue instanceof Boolean)
			return (double) (((boolean) DefaultValue)?1:0);
		
		return 0;
	}
	public static final long toLong(Object DefaultValue) {
		if(DefaultValue instanceof Double)
			return ((Double)DefaultValue).longValue();
		
		if(DefaultValue instanceof Long)
			return (long) DefaultValue;
		
		if(DefaultValue instanceof Float)
			return ((Float)DefaultValue).longValue();
		
		if(DefaultValue instanceof Integer)
			return ((Integer)DefaultValue).longValue();
		
		if(DefaultValue instanceof Short)
			return ((Short)DefaultValue).longValue();
		
		if(DefaultValue instanceof Byte)
			return ((Byte)DefaultValue).longValue();
		
		if(DefaultValue instanceof Boolean)
			return (long) (((boolean) DefaultValue)?1:0);
		
		return 0;
	}
	public static final float toFloat(Object DefaultValue) {
		if(DefaultValue instanceof Double)
			return ((Double)DefaultValue).floatValue();
		
		if(DefaultValue instanceof Long)
			return ((Long)DefaultValue).floatValue();
		
		if(DefaultValue instanceof Float)
			return (float) DefaultValue;
		
		if(DefaultValue instanceof Integer)
			return ((Integer)DefaultValue).floatValue();
		
		if(DefaultValue instanceof Short)
			return ((Short)DefaultValue).floatValue();
		
		if(DefaultValue instanceof Byte)
			return ((Byte)DefaultValue).floatValue();
		
		if(DefaultValue instanceof Boolean)
			return (float) (((boolean) DefaultValue)?1:0);
		
		return 0;
	}
	public static final int toInteger(Object DefaultValue) {
		if(DefaultValue instanceof Double)
			return ((Double)DefaultValue).intValue();
		
		if(DefaultValue instanceof Long)
			return ((Long)DefaultValue).intValue();
		
		if(DefaultValue instanceof Float)
			return ((Float)DefaultValue).intValue();
		
		if(DefaultValue instanceof Integer)
			return (int) DefaultValue;
		
		if(DefaultValue instanceof Short)
			return ((Short)DefaultValue).intValue();
		
		if(DefaultValue instanceof Byte)
			return ((Byte)DefaultValue).shortValue();
		
		if(DefaultValue instanceof Boolean)
			return (int) (((boolean) DefaultValue)?1:0);
		
		return 0;
	}
	public static final short toShort(Object DefaultValue) {
		if(DefaultValue instanceof Double)
			return ((Double)DefaultValue).shortValue();
		
		if(DefaultValue instanceof Long)
			return ((Long)DefaultValue).shortValue();
		
		if(DefaultValue instanceof Float)
			return ((Float)DefaultValue).shortValue();
		
		if(DefaultValue instanceof Integer)
			return ((Integer)DefaultValue).shortValue();
		
		if(DefaultValue instanceof Short)
			return (Short)DefaultValue;
		
		if(DefaultValue instanceof Byte)
			return ((Byte)DefaultValue).shortValue();
		
		if(DefaultValue instanceof Boolean)
			return (short) (((boolean) DefaultValue)?1:0);
		
		return 0;
	}
	public static final byte toByte(Object DefaultValue) {
		if(DefaultValue instanceof Double)
			return ((Double)DefaultValue).byteValue();
		
		if(DefaultValue instanceof Long)
			return ((Long)DefaultValue).byteValue();
		
		if(DefaultValue instanceof Float)
			return ((Float)DefaultValue).byteValue();
		
		if(DefaultValue instanceof Integer)
			return ((Integer)DefaultValue).byteValue();
		
		if(DefaultValue instanceof Short)
			return ((Short)DefaultValue).byteValue();
		
		if(DefaultValue instanceof Byte)
			return (byte)DefaultValue;
		
		if(DefaultValue instanceof Boolean)
			return (byte) (((boolean) DefaultValue)?1:0);
		
		return 0;
	}
	public static final boolean toBoolean(Object DefaultValue) {
		if(DefaultValue instanceof Double)
			return ((Double)DefaultValue).byteValue()!=0;
		
		if(DefaultValue instanceof Long)
			return ((Long)DefaultValue).byteValue()!=0;
		
		if(DefaultValue instanceof Float)
			return ((Float)DefaultValue).byteValue()!=0;
		
		if(DefaultValue instanceof Integer)
			return ((Integer)DefaultValue).byteValue()!=0;
		
		if(DefaultValue instanceof Short)
			return ((Short)DefaultValue).byteValue()!=0;
		
		if(DefaultValue instanceof Byte)
			return (byte)DefaultValue!=0;
		
		if(DefaultValue instanceof Boolean)
			return (boolean) DefaultValue;
		
		return false;
	}
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Object convertToType(Object object,byte Type) throws IOException{
		if (0 < Type && Type < 10) {
			if (object.getClass() == Boolean.class)
				return object;
			else if (object.getClass() == Byte.class)
				return ((byte) object)!=0;
			else if (object.getClass() == Short.class)
				return ((short) object)!=0;
			else if (object.getClass() == Integer.class)
				return ((int) object)!=0;
			else if (object.getClass() == Float.class)
				return ((float) object)!=0;
			else if (object.getClass() == Long.class)
				return ((long) object)!=0;
			else if (object.getClass() == Double.class)
				return ((double) object)!=0;
			else if (object.getClass() == String.class)
				return Boolean.parseBoolean((String) object);
			else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 1)
					return result[0]!=0;
				else
					throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			}else
				throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (9 < Type && Type < 20) {
			if (object.getClass() == Boolean.class)
				return (byte) ((boolean) object ? 1 : 0);
			else if (object.getClass() == Byte.class)
				return object;
			else if (object.getClass() == Short.class)
				return ((Short) object).byteValue();
			else if (object.getClass() == Integer.class)
				return ((Integer) object).byteValue();
			else if (object.getClass() == Float.class)
				return ((Float) object).byteValue();
			else if (object.getClass() == Long.class)
				return ((Long) object).byteValue();
			else if (object.getClass() == Double.class)
				return ((Double) object).byteValue();
			else if (object.getClass() == String.class)
				return Byte.parseByte((String) object);
			else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 1)
					return result[0];
				else
					throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			}else
				throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (19 < Type && Type < 40) {
			if (object.getClass() == Boolean.class)
				return (short) ((boolean) object ? 1 : 0);
			else if (object.getClass() == Byte.class)
				return ((Byte) object).shortValue();
			else if (object.getClass() == Short.class) {
				return object;
			} else if (object.getClass() == Integer.class) {
				return ((Integer) object).shortValue();
			} else if (object.getClass() == Float.class) {
				return ((Float) object).shortValue();
			} else if (object.getClass() == Long.class) {
				return ((Long) object).shortValue();
			} else if (object.getClass() == Double.class) {
				return ((Double) object).shortValue();
			} else if (object.getClass() == String.class) {
				return Short.parseShort((String) object);
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 2)
					return BGBytesConverter.bytesToShort(result);
				else
					throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (39 < Type && Type < 60) {
			if (object.getClass() == Boolean.class)
				return (boolean) object ? 1 : 0;
			else if (object.getClass() == Byte.class)
				return ((Byte) object).intValue();
			else if (object.getClass() == Short.class) {
				return ((Short) object).intValue();
			} else if (object.getClass() == Integer.class) {
				return object;
			} else if (object.getClass() == Float.class) {
				return ((Float) object).intValue();
			} else if (object.getClass() == Long.class) {
				return ((Long) object).intValue();
			} else if (object.getClass() == Double.class) {
				return ((Double) object).intValue();
			} else if (object.getClass() == String.class) {
				return Integer.parseInt((String) object);
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 4)
					return BGBytesConverter.bytesToInt(result);
				else
					throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (59 < Type && Type < 80) {
			if (object.getClass() == Boolean.class) {
				return (float)((boolean) object ? 1 : 0);
			} else if (object.getClass() == Byte.class)
				return ((Byte) object).floatValue();
			else if (object.getClass() == Short.class) {
				return ((Short) object).floatValue();
			} else if (object.getClass() == Integer.class) {
				return ((Integer) object).floatValue();
			} else if (object.getClass() == Float.class) {
				return object;
			} else if (object.getClass() == Long.class) {
				return ((Long) object).floatValue();
			} else if (object.getClass() == Double.class) {
				return ((Double) object).floatValue();
			} else if (object.getClass() == String.class) {
				return Float.parseFloat((String) object);
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 4)
					return BGBytesConverter.bytesToFloat(result);
				else
					throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (79 < Type && Type < 90) {
			if (object.getClass() == Boolean.class)
				return (long)((boolean) object ? 1 : 0);
			else if (object.getClass() == Byte.class)
				return ((Byte) object).longValue();
			else if (object.getClass() == Short.class) {
				return ((Short) object).longValue();
			} else if (object.getClass() == Integer.class) {
				return ((Integer) object).longValue();
			} else if (object.getClass() == Float.class) {
				return ((Float) object).longValue();
			} else if (object.getClass() == Long.class) {
				return object;
			} else if (object.getClass() == Double.class) {
				return ((Double) object).longValue();
			} else if (object.getClass() == String.class) {
				return Long.parseLong((String) object);
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 8)
					return BGBytesConverter.bytesToLong(result);
				else
					throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (89 < Type && Type < 100) {
			if (object.getClass() == Boolean.class) {
				return (double)((boolean) object ? 1 : 0);
			} else if (object.getClass() == Byte.class)
				return ((Byte) object).doubleValue();
			else if (object.getClass() == Short.class) {
				return ((Short) object).doubleValue();
			} else if (object.getClass() == Integer.class) {
				return ((Integer) object).doubleValue();
			} else if (object.getClass() == Float.class) {
				return ((Float) object).doubleValue();
			} else if (object.getClass() == Long.class) {
				return ((Long) object).doubleValue();
			} else if (object.getClass() == Double.class) {
				return object;
			} else if (object.getClass() == String.class) {
				return Double.parseDouble((String) object);
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 8)
					return BGBytesConverter.bytesToDouble(result);
				else
					throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (99 < Type && Type < 120) {
			if (object.getClass() == byte[].class)
				return object;
			else if (object.getClass() == String.class)
				return new Gson().fromJson((String) object, byte[].class);
			else
				throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (Type == DBDefine_DataType.STRING) {
			if (object.getClass() == String.class)
				return object;
			else
				return object+"";
		} else if (Type == DBDefine_DataType.IPV6) {
			if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 16)
					return result;
				else
					throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else
			throw new IOException("DBDefine_DataType → convertToType(Object object,byte Type) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
	}
	
	
	
	
	
	
	
	
	public static byte[] toByteArray(byte Type, Object object) throws IOException {
		if (0 < Type && Type < 10) {
			if (object.getClass() == Boolean.class)
				return new byte[] { (byte) ((boolean) object ? 1 : 0) };
			else if (object.getClass() == Byte.class)
				return new byte[] { (byte) object };
			else if (object.getClass() == Short.class)
				return new byte[] { ((Short) object).byteValue() };
			else if (object.getClass() == Integer.class)
				return new byte[] { ((Integer) object).byteValue() };
			else if (object.getClass() == Float.class)
				return new byte[] { ((Float) object).byteValue() };
			else if (object.getClass() == Long.class)
				return new byte[] { ((Long) object).byteValue() };
			else if (object.getClass() == Double.class)
				return new byte[] { ((Double) object).byteValue() };
			else if (object.getClass() == String.class)
				return new byte[] { (byte) (Boolean.parseBoolean((String) object) ? 1 : 0) };
			else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 1)
					return result;
				else
					throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			}else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (9 < Type && Type < 20) {
			if (object.getClass() == Boolean.class)
				return new byte[] { (byte) ((boolean) object ? 1 : 0) };
			else if (object.getClass() == Byte.class)
				return new byte[] { (byte) object };
			else if (object.getClass() == Short.class)
				return new byte[] { ((Short) object).byteValue() };
			else if (object.getClass() == Integer.class)
				return new byte[] { ((Integer) object).byteValue() };
			else if (object.getClass() == Float.class)
				return new byte[] { ((Float) object).byteValue() };
			else if (object.getClass() == Long.class)
				return new byte[] { ((Long) object).byteValue() };
			else if (object.getClass() == Double.class)
				return new byte[] { ((Double) object).byteValue() };
			else if (object.getClass() == String.class)
				return new byte[] { Byte.parseByte((String) object) };
			else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 1)
					return result;
				else
					throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			}else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (19 < Type && Type < 40) {
			if (object.getClass() == Boolean.class)
				return new byte[] { 0, (byte) ((boolean) object ? 1 : 0) };
			else if (object.getClass() == Byte.class)
				return new byte[] { 0, (byte) object };
			else if (object.getClass() == Short.class) {
				short value = (short) object;
				return new byte[] { (byte) (value >>> 8), (byte) value };
			} else if (object.getClass() == Integer.class) {
				int value = (int) object;
				return new byte[] { (byte) (value >>> 8), (byte) value };
			} else if (object.getClass() == Float.class) {
				short value = ((Float) object).shortValue();
				return new byte[] { (byte) (value >>> 8), (byte) value };
			} else if (object.getClass() == Long.class) {
				short value = ((Long) object).shortValue();
				return new byte[] { (byte) (value >>> 8), (byte) value };
			} else if (object.getClass() == Double.class) {
				short value = ((Double) object).shortValue();
				return new byte[] { (byte) (value >>> 8), (byte) value };
			} else if (object.getClass() == String.class) {
				short value = Short.parseShort((String) object);
				return new byte[] { (byte) (value >>> 8), (byte) value };
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 2)
					return result;
				else
					throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (39 < Type && Type < 60) {
			if (object.getClass() == Boolean.class)
				return new byte[] {0,0,0,(byte) ((boolean)object?1:0)};
			else if (object.getClass() == Byte.class)
				return new byte[] {0,0,0,(byte) object};
			else if (object.getClass() == Short.class) {
				return BGBytesConverter.intToBytes(((Short)object).intValue());
			} else if (object.getClass() == Integer.class) {
				return BGBytesConverter.intToBytes((int) object);
			} else if (object.getClass() == Float.class) {
				return BGBytesConverter.intToBytes(((Float)object).intValue());
			} else if (object.getClass() == Long.class) {
				return BGBytesConverter.intToBytes(((Long)object).intValue());
			} else if (object.getClass() == Double.class) {
				return BGBytesConverter.intToBytes(((Double)object).intValue());
			} else if (object.getClass() == String.class) {
				return BGBytesConverter.intToBytes(Integer.parseInt((String) object));
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 4)
					return result;
				else
					throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (59 < Type && Type < 80) {
			if (object.getClass() == Boolean.class) {
				return new byte[] {0,0,0,(byte) ((boolean)object?1:0)};
			} else if (object.getClass() == Byte.class)
				return new byte[] {0,0,0,(byte) object};
			else if (object.getClass() == Short.class) {
				return BGBytesConverter.floatToBytes(((Short)object).floatValue());
			} else if (object.getClass() == Integer.class) {
				return BGBytesConverter.floatToBytes(((Integer)object).floatValue());
			} else if (object.getClass() == Float.class) {
				return BGBytesConverter.floatToBytes((float) object);
			} else if (object.getClass() == Long.class) {
				return BGBytesConverter.floatToBytes(((Long)object).floatValue());
			} else if (object.getClass() == Double.class) {
				return BGBytesConverter.floatToBytes(((Double)object).floatValue());
			} else if (object.getClass() == String.class) {
				return BGBytesConverter.floatToBytes(Float.parseFloat((String) object));
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 4)
					return result;
				else
					throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (79 < Type && Type < 90) {
			if (object.getClass() == Boolean.class)
				return new byte[] {0,0,0,0,0,0,0,(byte) ((boolean)object?1:0)};
			else if (object.getClass() == Byte.class)
				return new byte[] {0,0,0,0,0,0,0,(byte) object};
			else if (object.getClass() == Short.class) {
				return BGBytesConverter.longToBytes(((Short)object).longValue());
			} else if (object.getClass() == Integer.class) {
				return BGBytesConverter.longToBytes(((Integer)object).longValue());
			} else if (object.getClass() == Float.class) {
				return BGBytesConverter.longToBytes(((Float)object).longValue());
			} else if (object.getClass() == Long.class) {
				return BGBytesConverter.longToBytes((long) object);
			} else if (object.getClass() == Double.class) {
				return BGBytesConverter.longToBytes(((Double)object).longValue());
			} else if (object.getClass() == String.class) {
				return BGBytesConverter.longToBytes(Long.parseLong((String) object));
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 8)
					return result;
				else
					throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (89 < Type && Type < 100) {
			if (object.getClass() == Boolean.class) {
				return new byte[] {0,0,0,0,0,0,0,(byte) ((boolean)object?1:0)};
			} else if (object.getClass() == Byte.class)
				return new byte[] {0,0,0,0,0,0,0,(byte) object};
			else if (object.getClass() == Short.class) {
				return BGBytesConverter.doubleToBytes(((Short)object).doubleValue());
			} else if (object.getClass() == Integer.class) {
				return BGBytesConverter.doubleToBytes(((Integer)object).doubleValue());
			} else if (object.getClass() == Float.class) {
				return BGBytesConverter.doubleToBytes(((Float)object).doubleValue());
			} else if (object.getClass() == Long.class) {
				return BGBytesConverter.doubleToBytes(((Long)object).doubleValue());
			} else if (object.getClass() == Double.class) {
				return BGBytesConverter.doubleToBytes((double) object);
			} else if (object.getClass() == String.class) {
				return BGBytesConverter.doubleToBytes(Double.parseDouble((String) object));
			} else if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 8)
					return result;
				else
					throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (99 < Type && Type < 120) {
			if (object.getClass() == byte[].class)
				return (byte[]) object;
			else if (object.getClass() == String.class)
				return new Gson().fromJson((String) object, byte[].class);
			else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (Type == DBDefine_DataType.STRING) {
			if (object.getClass() == String.class)
				return ((String) object).getBytes();
			else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else if (Type == DBDefine_DataType.IPV6) {
			if (object.getClass() == byte[].class) {
				byte[] result = (byte[]) object;
				if (result.length == 16)
					return result;
				else
					throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + Arrays.toString(result));
			} else
				throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
		} else
			throw new IOException("DBDefine_DataType → toByteArray(byte Type,Object object) " +DBDefine_DataType.getTypeName(Type) + " : " + object.getClass().getSimpleName() + "(" + object + ")");
	}
}
