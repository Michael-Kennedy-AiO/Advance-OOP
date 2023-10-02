package web_admin.http.dto;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.MessageSending;
import backendgame.com.core.database.BGColumn;

public class DTO_Column {
	public String ColumnName;		//Max
	public int ViewId;				//4
	public byte Indexing;			//1
	public int Size;				//4
	public byte Type;				//1
	public Object DefaultValue;
	
	public DTO_Column() {}
	public DTO_Column(BGColumn describe) {
		ColumnName=describe.ColumnName;
		ViewId=describe.ViewId;
		Indexing=describe.Indexing;
		Size=describe.Size;
		Type=describe.Type;
		DefaultValue=describe.getDefaultData();
		
		if(Type == DBDefine_DataType.STRING)
			Size-=2;
		else if(99<Type && Type<120)
			Size-=4;
	}
	
	public void writeMessage(MessageSending messageSending) {
		messageSending.writeString(ColumnName);
		messageSending.writeInt(ViewId);
		messageSending.writeByte(Indexing);
		messageSending.writeInt(Size);
		messageSending.writeByte(Type);
		if(0<Type && Type<10)
			messageSending.writeBoolean((boolean) DefaultValue);
		else if(9<Type && Type<20)
			messageSending.writeByte((byte) DefaultValue);
		else if(19<Type && Type<40)
			messageSending.writeShort((short) DefaultValue);
		else if(39<Type && Type<60)
			messageSending.writeInt((int) DefaultValue);
		else if(59<Type && Type<80)
			messageSending.writeFloat((float) DefaultValue);
		else if(79<Type && Type<90)
			messageSending.writeLong((long) DefaultValue);
		else if(89<Type && Type<100)
			messageSending.writeDouble((double) DefaultValue);
		else if(99<Type && Type<120)
			messageSending.writeByteArray((byte[]) DefaultValue);
		else if(Type==DBDefine_DataType.STRING) {
			messageSending.writeString((String) DefaultValue);
		}else if(Type==DBDefine_DataType.IPV6)
			messageSending.writeSpecialArray_WithoutLength((byte[]) DefaultValue);
	}
	
	
	public BGColumn toDescribe() {
		BGColumn describe = new BGColumn();
		describe.ColumnName = ColumnName;
		describe.ViewId = ViewId;
		describe.Indexing = Indexing;
		describe.Size = Size;
		describe.Type = Type;
		describe.loadDefaultData(DefaultValue);
		if(0<Type && Type<10) {
			describe.Size = 1;
		}else if(9<Type && Type<20) {
			describe.Size = 1;
		}else if(19<Type && Type<40) {
			describe.Size = 2;
		}else if(39<Type && Type<60) {
			describe.Size = 4;
		}else if(59<Type && Type<80) {
			describe.Size = 4;
		}else if(79<Type && Type<90) {
			describe.Size = 8;
		}else if(89<Type && Type<100) {
			describe.Size = 8;
		}else if(99<Type && Type<120) {
			describe.Size+=4;
		}else if(Type==DBDefine_DataType.STRING) {
			describe.Size+=2;
		}else if(Type==DBDefine_DataType.IPV6) {
			describe.Size = 16;
		}else {
			System.err.println("DTO_Describe → toDescribe() " + DBDefine_DataType.getTypeName(Type));
			DefaultValue=null;
		}
		
		return describe;
	}
	


	
	
	
	
	
	
	
	
	
	
	
	
	public void trace() {
		System.out.println("ColumnName("+ColumnName+")	ViewId("+ViewId+")	Indexing("+Indexing+")	Size("+Size+")	 Type("+Type+")	DefaultValue : "+DefaultValue);
	}
}
