package utility;

import java.util.Random;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;

public class Utility {
	public static final Random random = new Random();
	public static BGColumn randomDescribe() {
		return randomDescribe(null);
	}
	public static BGColumn randomDescribe(String columnName) {
		BGColumn describe=new BGColumn();
		switch (random.nextInt(19)) {
			case 0:
				describe.ColumnName="BOOLEAN";
				describe.Type = DBDefine_DataType.BOOLEAN;
				describe.Size = 1;
				describe.loadDefaultData(random.nextBoolean());
				break;
				
			case 1:
				describe.ColumnName="BYTE";
				describe.Type = DBDefine_DataType.BYTE;
				describe.Size = 1;
				describe.loadDefaultData((byte)random.nextInt());
				break;
			case 2:
				describe.ColumnName="STATUS";
				describe.Type = DBDefine_DataType.STATUS;
				describe.Size = 1;
				describe.loadDefaultData((byte)random.nextInt());
				break;
			case 3:
				describe.ColumnName="PERMISSION";
				describe.Type = DBDefine_DataType.PERMISSION;
				describe.Size = 1;
				describe.loadDefaultData((byte)random.nextInt());
				break;
			case 4:
				describe.ColumnName="AVARTAR";
				describe.Type = DBDefine_DataType.AVARTAR;
				describe.Size = 1;
				describe.loadDefaultData((byte)random.nextInt());
				break;
			case 5:
				describe.ColumnName="Gender";
				describe.Type = DBDefine_DataType.Gender;
				describe.Size = 1;
				describe.loadDefaultData((byte)random.nextInt());
				break;
				
			case 6:
				describe.ColumnName="SHORT";
				describe.Type = DBDefine_DataType.SHORT;
				describe.Size = 2;
				describe.loadDefaultData((short)random.nextInt());
				break;
			case 7:
				describe.ColumnName="CountryCode";
				describe.Type = DBDefine_DataType.CountryCode;
				describe.Size = 2;
				describe.loadDefaultData((short)random.nextInt());
				break;
				
			case 8:
				describe.ColumnName="INTEGER";
				describe.Type = DBDefine_DataType.INTEGER;
				describe.Size = 4;
				describe.loadDefaultData(random.nextInt());
				break;
			case 9:
				describe.ColumnName="IPV4";
				describe.Type = DBDefine_DataType.IPV4;
				describe.Size = 4;
				describe.loadDefaultData(random.nextInt());
				break;
			case 10:
				describe.ColumnName="FLOAT";
				describe.Type = DBDefine_DataType.FLOAT;
				describe.Size = 4;
				describe.loadDefaultData(random.nextFloat());
				break;
				
			case 11:
				describe.ColumnName="LONG";
				describe.Type = DBDefine_DataType.LONG;
				describe.Size = 8;
				describe.loadDefaultData(random.nextLong());
				break;
			case 12:
				describe.ColumnName="USER_ID";
				describe.Type = DBDefine_DataType.USER_ID;
				describe.Size = 8;
				describe.loadDefaultData(BGUtility.randomPositiveLong(Long.MAX_VALUE));
				break;
			case 13:
				describe.ColumnName="TIMEMILI";
				describe.Type = DBDefine_DataType.TIMEMILI;
				describe.Size = 8;
				describe.loadDefaultData(BGUtility.randomPositiveLong(System.currentTimeMillis()));
				break;
			case 14:
				describe.ColumnName="DOUBLE";
				describe.Type = DBDefine_DataType.DOUBLE;
				describe.Size = 8;
				describe.loadDefaultData(random.nextDouble());
				break;
				
			case 15:
				describe.ColumnName="ByteArray";
				describe.Type = DBDefine_DataType.BYTE_ARRAY;
				describe.Size = 3+random.nextInt(5);
				byte[] tmpArr = new byte[describe.Size];
				random.nextBytes(tmpArr);
				describe.loadDefaultData(tmpArr);
				break;
			case 16:
				describe.ColumnName="LIST";
				describe.Type = DBDefine_DataType.List;
				describe.Size = 3+random.nextInt(5);
				byte[] tmpList = new byte[describe.Size];
				random.nextBytes(tmpList);
				describe.loadDefaultData(tmpList);
				break;
				
			case 17:
				describe.ColumnName="STRING";
				describe.Type = DBDefine_DataType.STRING;
				describe.Size = 4+random.nextInt(5);
				describe.loadDefaultData(BGUtility.alphabetic(2+random.nextInt(describe.Size-3)));
				break;	
				
			case 18:
				describe.ColumnName="IPV6";
				describe.Type = DBDefine_DataType.IPV6;
				describe.Size = 16;
				byte[] tmpIPV6 = new byte[describe.Size];
				random.nextBytes(tmpIPV6);
				describe.loadDefaultData(tmpIPV6);
				break;
			default:break;
		}
		if(columnName!=null)
			describe.ColumnName = columnName;
		
		return describe;
	}
}
