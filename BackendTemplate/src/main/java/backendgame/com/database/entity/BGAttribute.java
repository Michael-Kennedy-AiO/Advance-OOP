package backendgame.com.database.entity;

import java.io.IOException;
import java.net.Socket;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.OneHitProcessing;
import backendgame.com.database.BGBaseDatabaseTable;

public class BGAttribute {
    public static final byte Init_AutoIncrement	= 1;
    public static final byte Init_IP			= 2;
    public static final byte Init_TimeMili		= 3;
    public static final byte Init_Random		= 4;
	
    public String columnName;
	public int indexDescribe;
	public byte InitType;
    public Object Value;
    
    public Object initValue(BGBaseDatabaseTable database,Socket socket) throws IOException {
    	byte Type = database.getColumn_DataType(indexDescribe);
    	if(InitType == Init_AutoIncrement)
    		Value = database.countRow();
    	else if(InitType == Init_IP) {
    		if(socket==null) {
    			if(Type==DBDefine_DataType.IPV4)
    				Value=new byte[4];
    			else if(Type==DBDefine_DataType.IPV6)
    				Value=new byte[16];
    		}else {
    			byte[] ipBytes = socket.getInetAddress().getAddress();
    			if(Type==DBDefine_DataType.IPV4) {
    				if(ipBytes.length==4)
    					Value=ipBytes;
    				else
    					Value=new byte[4];
    			}else if(Type==DBDefine_DataType.IPV6) {
    				if(ipBytes.length==16)
    					Value=ipBytes;
    				else
    					Value=new byte[16];
    			}else
    				throw new IOException("Database error ObjectWriter → loadValueWriting(DBProcess_Describe database,BackendSession session) Init_IP : "+DBDefine_DataType.getTypeName(Type));
    		}
    	}else if(InitType == Init_TimeMili)
    		Value = System.currentTimeMillis();
		else if(InitType == Init_Random) {
			if(0<Type && Type<10)
				Value = OneHitProcessing.random.nextBoolean();
			else if(9<Type && Type<20)
				Value = (byte)OneHitProcessing.random.nextInt();
			else if(19<Type && Type<40)
				Value = (short)OneHitProcessing.random.nextInt();
			else if(39<Type && Type<60)
				Value = OneHitProcessing.random.nextInt();
			else if(59<Type && Type<80)
				Value = OneHitProcessing.random.nextFloat();
			else if(79<Type && Type<90)
				Value = OneHitProcessing.random.nextLong();
			else if(89<Type && Type<100)
				Value = OneHitProcessing.random.nextDouble();
			else if(99<Type && Type<120) {
				Value=new byte[database.getCloumn_DataSize(indexDescribe)-4];
				OneHitProcessing.random.nextBytes((byte[])Value);
			}else if(Type==DBDefine_DataType.STRING)
				Value = BGUtility.alphanumeric(database.getCloumn_DataSize(indexDescribe)/2-2);
			else if(Type==DBDefine_DataType.IPV6) {
				Value=new byte[16];
				OneHitProcessing.random.nextBytes((byte[])Value);
			}else
				throw new IOException("Database error ObjectWriter → loadValueWriting(DBProcess_Describe database,BackendSession session) : "+DBDefine_DataType.getTypeName(Type));
		}
    	return Value;
	}
}
