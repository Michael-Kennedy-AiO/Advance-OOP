package web_admin;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import server.config.CipherBinary;

public class AdminToken {
	public static CipherBinary cipherBinary;
	public String username;
	public long timeOut;
	
	
	
	public String toHashString() {
		byte[] dataString = username.getBytes(StandardCharsets.UTF_8);
		int length = dataString.length;
		byte[] buffer = new byte[length+16];
		
		for(int i=0;i<length;i++) {
			buffer[i%8] += dataString[i];//8 byte đầu là Validate
			buffer[i+8] = dataString[i];//giữa là username
		}
		
		buffer[length+8]	= (byte)(timeOut >>> 56);
		buffer[length+9]	= (byte)(timeOut >>> 48);
		buffer[length+10]	= (byte)(timeOut >>> 40);
		buffer[length+11]	= (byte)(timeOut >>> 32);
		buffer[length+12]	= (byte)(timeOut >>> 24);
		buffer[length+13]	= (byte)(timeOut >>> 16);
		buffer[length+14]	= (byte)(timeOut >>>  8);
		buffer[length+15]	= (byte)(timeOut >>>  0);
		
		for(int i=0;i<8;i++)
			buffer[i] = (byte) (buffer[i]^buffer[length + i]);
		
		return Base64.getEncoder().encodeToString(cipherBinary.insert2ByteEndCode(buffer, length+16));
	}
	
	public boolean decode(String encodeString) {
		return decode(encodeString,true);
	}
	public boolean decode(String encodeString, boolean loadUsername) {
		byte[] dataBase64 = Base64.getDecoder().decode(encodeString);
		if(dataBase64.length<18)
			return false;
		byte[] dataDecode = cipherBinary.remove2ByteDecode(dataBase64, dataBase64.length);
		
		int length = dataDecode.length - 16;
		if(loadUsername) {
			byte[] data=new byte[length];
			for(int i=0;i<length;i++)
				data[i]=dataDecode[i+8];
			username=new String(data,StandardCharsets.UTF_8);
		}
		
		dataBase64[0]=0;//Sử dụng tạm 8 byte này
		dataBase64[1]=0;
		dataBase64[2]=0;
		dataBase64[3]=0;
		dataBase64[4]=0;
		dataBase64[5]=0;
		dataBase64[6]=0;
		dataBase64[7]=0;
		for(int i=0;i<length;i++)
			dataBase64[i%8] += dataDecode[i+8];
		
		for(int i=0;i<8;i++)
			dataBase64[i] = (byte) (dataBase64[i]^dataDecode[length + i]);
		
		long l0 = dataDecode[length+8] & 0xFF;
		long l1 = dataDecode[length+9] & 0xFF;
		long l2 = dataDecode[length+10] & 0xFF;
		long l3 = dataDecode[length+11] & 0xFF;
		long l4 = dataDecode[length+12] & 0xFF;
		long l5 = dataDecode[length+13] & 0xFF;
		long l6 = dataDecode[length+14] & 0xFF;
		long l7 = dataDecode[length+15] & 0xFF;
		
		long r0 = l0 << 56;
		long r1 = l1 << 48;
		long r2 = l2 << 40;
		long r3 = l3 << 32;
		long r4 = l4 << 24;
		long r5 = l5 << 16;
		long r6 = l6 << 8;
		long r7 = l7;
		timeOut = r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7;
//		//////////////////////////////////////////////////////////////////////////
		return dataBase64[0]==dataDecode[0] || dataBase64[1]==dataDecode[1] || dataBase64[2]==dataDecode[2] || dataBase64[3]==dataDecode[3] || dataBase64[4]==dataDecode[4] || dataBase64[5]==dataDecode[5] || dataBase64[6]==dataDecode[6] || dataBase64[7]==dataDecode[7];
	}
	
}
