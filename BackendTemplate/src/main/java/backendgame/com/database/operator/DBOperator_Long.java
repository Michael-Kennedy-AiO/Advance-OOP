package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Long extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_Long(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public long process(long offset, byte operator, long value) throws IOException {
		switch (operator) {
			case Addition:return add(offset, value);
			case AddPositive:return addPositive(offset, value);
			case AddInBound:return add_InBound(offset, value);
			case Multiplication: return multiplication(offset, value);
			case Division: return division(offset, value);
			case Modulus: return modulus(offset, value);
			case AND: return and(offset, value);
			case OR: return or(offset, value);
			case NOT: return not(offset, value);
			case XOR: return xor(offset, value);
			case NAND: return nand(offset, value);
			case LeftShift: return leftShift(offset, value);
			case RightShift: return rightShift(offset, value);
			default:
				break;
		}
		return 0;
	}


	public long add(long offset, long value) throws IOException {
		rfData.seek(offset);
		value = (long) (value + rfData.readLong());

		rfData.seek(offset);
		rfData.writeDouble(value);
		return value;
	}
	public long addPositive(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue + value);
		if(result<0)
			if(oldValue>0 && value>0)
				result=Long.MAX_VALUE;
			else
				result=0;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}


	public long add_InBound(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue + value);

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}
	public long multiplication(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue * value);

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}
	public long division(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue / value);

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}
	public long modulus(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue % value);

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}

	public long and(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue & value);

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public long or(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue | value);

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public long not(long offset, long value) throws IOException {
		rfData.seek(offset);
		long result = (long) (~value);

		if(value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public long xor(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue ^ value);

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public long nand(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (~(oldValue & value));

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public long leftShift(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue << value);

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public long rightShift(long offset, long value) throws IOException {
		rfData.seek(offset);
		long oldValue = rfData.readLong();
		long result = (long) (oldValue >> value);

		if(oldValue>0 && value>0 && result<=0)
			result=Long.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Long.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
}
