package web_admin.http.dto;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;

public class DTO_UpdatePrimary2Key {
	public Object HashKey;
	public Object RangeKey;
	public String ColumnName;
	public int IndexDescribe;
	public byte Type;
	public Object value;
	
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
