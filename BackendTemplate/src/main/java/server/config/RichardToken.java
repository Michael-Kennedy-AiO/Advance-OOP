package server.config;

import java.util.Base64;

public class RichardToken {
	public static CipherBinary cipherBinary;
	public long userId;
	public long timeOut;
	
	public String toHashString() {
		byte[] data = new byte[24];
		
		
		int currentWriter=0;
		long v = userId;
		data[currentWriter]   = (byte)(v >>> 56);
		data[currentWriter+1] = (byte)(v >>> 48);
		data[currentWriter+2] = (byte)(v >>> 40);
		data[currentWriter+3] = (byte)(v >>> 32);
		data[currentWriter+4] = (byte)(v >>> 24);
		data[currentWriter+5] = (byte)(v >>> 16);
		data[currentWriter+6] = (byte)(v >>>  8);
		data[currentWriter+7] = (byte)(v >>>  0);
		
		currentWriter=8;
		v = timeOut;
		data[currentWriter]   = (byte)(v >>> 56);
		data[currentWriter+1] = (byte)(v >>> 48);
		data[currentWriter+2] = (byte)(v >>> 40);
		data[currentWriter+3] = (byte)(v >>> 32);
		data[currentWriter+4] = (byte)(v >>> 24);
		data[currentWriter+5] = (byte)(v >>> 16);
		data[currentWriter+6] = (byte)(v >>>  8);
		data[currentWriter+7] = (byte)(v >>>  0);
		
		currentWriter=16;
		v = userId^timeOut;
		data[currentWriter]   = (byte)(v >>> 56);
		data[currentWriter+1] = (byte)(v >>> 48);
		data[currentWriter+2] = (byte)(v >>> 40);
		data[currentWriter+3] = (byte)(v >>> 32);
		data[currentWriter+4] = (byte)(v >>> 24);
		data[currentWriter+5] = (byte)(v >>> 16);
		data[currentWriter+6] = (byte)(v >>>  8);
		data[currentWriter+7] = (byte)(v >>>  0);
		
		return Base64.getEncoder().encodeToString(cipherBinary.insert2ByteEndCode(data, 24));
	}
	
	public boolean decode(String encodeString) {
		byte[] dataBase64 = Base64.getDecoder().decode(encodeString);
		if(dataBase64.length!=26)
			return false;
		
		byte[] dataDecode = cipherBinary.remove2ByteDecode(dataBase64, 26);
		
		int currentReading=0;
		long l0 = dataDecode[currentReading] & 0xFF;
		long l1 = dataDecode[currentReading+1] & 0xFF;
		long l2 = dataDecode[currentReading+2] & 0xFF;
		long l3 = dataDecode[currentReading+3] & 0xFF;
		long l4 = dataDecode[currentReading+4] & 0xFF;
		long l5 = dataDecode[currentReading+5] & 0xFF;
		long l6 = dataDecode[currentReading+6] & 0xFF;
		long l7 = dataDecode[currentReading+7] & 0xFF;
		
		long r0 = l0 << 56;
		long r1 = l1 << 48;
		long r2 = l2 << 40;
		long r3 = l3 << 32;
		long r4 = l4 << 24;
		long r5 = l5 << 16;
		long r6 = l6 << 8;
		long r7 = l7;
		userId = r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7;
		//////////////////////////////////////////////////////////////////////////
		currentReading=8;
		l0 = dataDecode[currentReading] & 0xFF;
		l1 = dataDecode[currentReading+1] & 0xFF;
		l2 = dataDecode[currentReading+2] & 0xFF;
		l3 = dataDecode[currentReading+3] & 0xFF;
		l4 = dataDecode[currentReading+4] & 0xFF;
		l5 = dataDecode[currentReading+5] & 0xFF;
		l6 = dataDecode[currentReading+6] & 0xFF;
		l7 = dataDecode[currentReading+7] & 0xFF;
		
		r0 = l0 << 56;
		r1 = l1 << 48;
		r2 = l2 << 40;
		r3 = l3 << 32;
		r4 = l4 << 24;
		r5 = l5 << 16;
		r6 = l6 << 8;
		r7 = l7;
		timeOut = r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7;
		//////////////////////////////////////////////////////////////////////////
		currentReading=16;
		l0 = dataDecode[currentReading] & 0xFF;
		l1 = dataDecode[currentReading+1] & 0xFF;
		l2 = dataDecode[currentReading+2] & 0xFF;
		l3 = dataDecode[currentReading+3] & 0xFF;
		l4 = dataDecode[currentReading+4] & 0xFF;
		l5 = dataDecode[currentReading+5] & 0xFF;
		l6 = dataDecode[currentReading+6] & 0xFF;
		l7 = dataDecode[currentReading+7] & 0xFF;
		
		r0 = l0 << 56;
		r1 = l1 << 48;
		r2 = l2 << 40;
		r3 = l3 << 32;
		r4 = l4 << 24;
		r5 = l5 << 16;
		r6 = l6 << 8;
		r7 = l7;
		long verify = r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7;
		
		return verify == (userId^timeOut);
	}
}
