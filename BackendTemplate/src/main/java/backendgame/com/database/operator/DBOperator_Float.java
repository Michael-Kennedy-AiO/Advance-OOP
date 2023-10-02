package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Float extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_Float(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public float process(long offset, byte operator, float value) throws IOException {
		switch (operator) {
			case Addition:return add(offset, value);
			case AddPositive:return addPositive(offset, value);
			case AddInBound:return add_InBound(offset, value);
			case Multiplication: return multiplication(offset, value);
			case Division: return division(offset, value);
			case Modulus: return modulus(offset, value);
			default:
				break;
		}
		return 0;
	}

	public float add(long offset, float value) throws IOException {
		rfData.seek(offset);
		value = (float) (value + rfData.readFloat());

		rfData.seek(offset);
		rfData.writeFloat(value);
		return value;
	}
	public float addPositive(long offset, float value) throws IOException {
		rfData.seek(offset);
		float oldValue = rfData.readFloat();
		float result = (float) (oldValue + value);
		if(result<0)
			if(oldValue>0 && value>0)
				result=Float.MAX_VALUE;
			else
				result=0;

		rfData.seek(offset);
		rfData.writeFloat(result);
		return result;
	}


	public float add_InBound(long offset, float value) throws IOException {
		rfData.seek(offset);
		float oldValue = rfData.readFloat();
		float result = (float) (oldValue + value);

		if(oldValue>0 && value>0 && result<=0)
			result=Float.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Float.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeFloat(result);
		return result;
	}
	public float multiplication(long offset, float value) throws IOException {
		rfData.seek(offset);
		float oldValue = rfData.readFloat();
		float result = (float) (oldValue * value);

		if(oldValue>0 && value>0 && result<=0)
			result=Float.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Float.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeFloat(result);
		return result;
	}
	public float division(long offset, float value) throws IOException {
		rfData.seek(offset);
		float oldValue = rfData.readFloat();
		float result = (float) (oldValue / value);

		if(oldValue>0 && value>0 && result<=0)
			result=Float.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Float.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeFloat(result);
		return result;
	}
	public float modulus(long offset, float value) throws IOException {
		rfData.seek(offset);
		float oldValue = rfData.readFloat();
		float result = (float) (oldValue % value);

		if(oldValue>0 && value>0 && result<=0)
			result=Float.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Float.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeFloat(result);
		return result;
	}
}
