package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_ByteArrays extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_ByteArrays(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	
	public boolean compare(long offset, byte[] data) throws IOException {
		rfData.seek(offset);
		if(data==null)
			return rfData.readInt()==0;
		
		int length = rfData.readInt();
		if(length!=data.length)
			return false;
		
		for(int i=0;i<length;i++)
			if(data[i]!=rfData.readByte())
				return false;
		
		return true;
	}

    public byte[] process(long get_OffsetData, byte operator, byte[] object) {
        
        return null;
    }
}
