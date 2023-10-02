package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Boolean extends DBOperator{
	private RandomAccessFile rfData;
	public DBOperator_Boolean(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public boolean process(long offsetData, byte operator, boolean value) throws IOException {
		rfData.seek(offsetData);
		switch (operator) {
			case AND:
				boolean andVal = value && rfData.readBoolean();
				rfData.seek(offsetData);
				rfData.writeBoolean(andVal);
				return andVal;
			case OR:
				boolean orVal = value || rfData.readBoolean();
				rfData.seek(offsetData);
				rfData.writeBoolean(orVal);
				return orVal;
			case NOT:
				value = !rfData.readBoolean();
				rfData.seek(offsetData);
				rfData.writeBoolean(value);
				return value;
			case XOR:
				boolean xorVal = value ^ rfData.readBoolean();
				rfData.seek(offsetData);
				rfData.writeBoolean(xorVal);
				return xorVal;
			case NAND:
				boolean nandVal = !(value && rfData.readBoolean());
				rfData.seek(offsetData);
				rfData.writeBoolean(nandVal);
				return nandVal;
			default:
				return value;
		}
	}
}
