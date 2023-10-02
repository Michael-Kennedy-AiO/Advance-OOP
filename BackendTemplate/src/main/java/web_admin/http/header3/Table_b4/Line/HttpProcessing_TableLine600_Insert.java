package web_admin.http.header3.Table_b4.Line;

import java.io.IOException;

import backendgame.com.core.BGCastObject;
import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGBytesConverter;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.table.DBLine;
import web_admin.API;
import web_admin.DES;
import web_admin.MyRespone;

@Http(api = API.SubTable_Line_insert)
@HttpDocument(id = 3800,Header = API.H3b4_Line, Description = DES.LineNode_Insert)
public class HttpProcessing_TableLine600_Insert extends BaseProcess_DatabaseLine {
	public String Description;
	public byte Type;
	public int Size;
	public Object value;
	
	@Override public MyRespone onProcess(DBLine databaseLine) throws IOException {
		if(0<Type && Type<10)
			databaseLine.insert(dbString, Description, Type, BGCastObject.toBoolean(value));
		else if(9<Type && Type<20)
			databaseLine.insert(dbString, Description, Type, BGCastObject.toByte(value));
		else if(19<Type && Type<40)
			databaseLine.insert(dbString, Description, Type, BGCastObject.toShort(value));
		else if(39<Type && Type<60)
			databaseLine.insert(dbString, Description, Type, BGCastObject.toInteger(value));
		else if(59<Type && Type<80)
			databaseLine.insert(dbString, Description, Type, BGCastObject.toFloat(value));
		else if(79<Type && Type<90)
			databaseLine.insert(dbString, Description, Type, BGCastObject.toLong(value));
		else if(89<Type && Type<100)
			databaseLine.insert(dbString, Description, Type, BGCastObject.toDouble(value));
		else if(99<Type && Type<120)
			databaseLine.insert(dbString, Description, Type, Size, BGBytesConverter.toByteArray(value));
		else if(Type==DBDefine_DataType.STRING)
			databaseLine.insert(dbString, Description, Type, Size, (String)value);
		else if(Type==DBDefine_DataType.IPV6)
			databaseLine.insert(dbString, Description, Type, 16, BGBytesConverter.toByteArray(value));
		else
			throw new IOException("DBDescribe → loadDefaultDataByObject(Object object) " + DBDefine_DataType.getTypeName(Type));
		return API.resSuccess;
	}
	

	@Override public void test_api_in_document() {
		super.test_api_in_document();
		switch (random.nextInt(19)) {
			case 0:
				Description="BOOLEAN";
				Type = DBDefine_DataType.BOOLEAN;
				Size = 1;
				value = random.nextBoolean();
				break;
				
			case 1:
				Description="BYTE";
				Type = DBDefine_DataType.BYTE;
				Size = 1;
				value = random.nextInt();
				break;
			case 2:
				Description="STATUS";
				Type = DBDefine_DataType.STATUS;
				Size = 1;
				value = random.nextInt();
				break;
			case 3:
				Description="PERMISSION";
				Type = DBDefine_DataType.PERMISSION;
				Size = 1;
				value = random.nextInt();
				break;
			case 4:
				Description="AVARTAR";
				Type = DBDefine_DataType.AVARTAR;
				Size = 1;
				value = random.nextInt();
				break;
			case 5:
				Description="Gender";
				Type = DBDefine_DataType.Gender;
				Size = 1;
				value = random.nextInt();
				break;
				
			case 6:
				Description="SHORT";
				Type = DBDefine_DataType.SHORT;
				Size = 2;
				value = random.nextInt();
				break;
			case 7:
				Description="CountryCode";
				Type = DBDefine_DataType.CountryCode;
				Size = 2;
				value = random.nextInt();
				break;
				
			case 8:
				Description="INTEGER";
				Type = DBDefine_DataType.INTEGER;
				Size = 4;
				value = random.nextInt();
				break;
			case 9:
				Description="IPV4";
				Type = DBDefine_DataType.IPV4;
				Size = 4;
				value = random.nextInt();
				break;
			case 10:
				Description="FLOAT";
				Type = DBDefine_DataType.FLOAT;
				Size = 4;
				value = random.nextFloat();
				break;
				
			case 11:
				Description="LONG";
				Type = DBDefine_DataType.LONG;
				Size = 8;
				value = random.nextLong();
				break;
			case 12:
				Description="USER_ID";
				Type = DBDefine_DataType.USER_ID;
				Size = 8;
				value = BGUtility.randomPositiveLong(Long.MAX_VALUE);
				break;
			case 13:
				Description="TIMEMILI";
				Type = DBDefine_DataType.TIMEMILI;
				Size = 8;
				value = BGUtility.randomPositiveLong(System.currentTimeMillis());
				break;
			case 14:
				Description="DOUBLE";
				Type = DBDefine_DataType.DOUBLE;
				Size = 8;
				value = random.nextDouble();
				break;
				
			case 15:
				Description="ByteArray";
				Type = DBDefine_DataType.BYTE_ARRAY;
				Size = 3+random.nextInt(5);
				byte[] tmpArr = new byte[Size];
				random.nextBytes(tmpArr);
				value = tmpArr;
				break;
			case 16:
				Description="LIST";
				Type = DBDefine_DataType.List;
				Size = 3+random.nextInt(5);
				byte[] tmpList = new byte[Size];
				random.nextBytes(tmpList);
				value = tmpList;
				break;
				
			case 17:
				Description="STRING";
				Type = DBDefine_DataType.STRING;
				Size = 4+random.nextInt(5);
				value = BGUtility.alphabetic(2+random.nextInt(Size-3));
				break;	
				
			case 18:
				Description="IPV6";
				Type = DBDefine_DataType.IPV6;
				Size = 16;
				byte[] tmpIPV6 = new byte[Size];
				random.nextBytes(tmpIPV6);
				value = tmpIPV6;
				break;
			default:break;
		}
	}



}
