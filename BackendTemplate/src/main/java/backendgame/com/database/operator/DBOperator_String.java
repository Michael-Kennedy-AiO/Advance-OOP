package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_String extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_String(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	
	
	
	public boolean compare(long offset, String str) throws IOException {
		rfData.seek(offset);
		if(str==null)
			return rfData.readUTF().equals("");
		return str.equals(rfData.readUTF());
	}



	public String process(long offsetData, byte operator, String object) {
		return null;
	}
}
