package web_admin.http.dto;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;

public class DTO_UpdateUserData {
	public long UserId;
	public String ColumnName;
	public int IndexDescribe;
	public byte Type;
	public Object value;
	
//	public Object castValueByType() throws IOException{
//		if(0<Type && Type<10)
//			return BGCastObject.toBoolean(value);
//		else if(9<Type && Type<20)
//			return BGCastObject.toByte(value);
//		else if(19<Type && Type<40)
//			return BGCastObject.toShort(value);
//		else if(39<Type && Type<60)
//			return BGCastObject.toInteger(value);
//		else if(59<Type && Type<80)
//			return BGCastObject.toFloat(value);
//		else if(79<Type && Type<90)
//			return BGCastObject.toLong(value);
//		else if(89<Type && Type<100)
//			return BGCastObject.toDouble(value);
//		else if(99<Type && Type<120)
//			return BGCastObject.toByteArray(value);
//		else if(Type==DBDefine_DataType.STRING)
//			return value;
//		else if(Type==DBDefine_DataType.IPV6)
//			return BGCastObject.toByteArray(value);
//		else 
//			throw new IOException("DTO_UpdateUserData → castValueByType() : "+DBDefine_DataType.getTypeName(Type) + " to "+value.getClass().getSimpleName());
//	}
	
	public boolean validate(BGColumn des) {
		if(0<Type && Type<10) {
			if(des.Type<1 || 9<des.Type)
				return false;
		}else if(9<Type && Type<20) {
			if(des.Type<10 || 19<des.Type)
				return false;
		}else if(19<Type && Type<40) {
			if(des.Type<20 || 39<des.Type)
				return false;
		}else if(39<Type && Type<60) {
			if(des.Type<40 || 59<des.Type)
				return false;
		}else if(59<Type && Type<80) {
			if(des.Type<60 || 79<des.Type)
				return false;
		}else if(79<Type && Type<90) {
			if(des.Type<80 || 89<des.Type)
				return false;
		}else if(89<Type && Type<100) {
			if(des.Type<90 || 99<des.Type)
				return false;
		}else if(99<Type && Type<120) {
			if(des.Type<100 || 119<des.Type)
				return false;
		}else if(Type==DBDefine_DataType.STRING) {
			if(des.Type!=DBDefine_DataType.STRING)
				return false;
		}else if(Type==DBDefine_DataType.IPV6) {
			if(des.Type!=DBDefine_DataType.IPV6)
				return false;
		}else
			return false;
		return true;
	}
}
