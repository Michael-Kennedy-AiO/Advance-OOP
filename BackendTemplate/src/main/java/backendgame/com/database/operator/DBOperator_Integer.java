package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Integer extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_Integer(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public int process(long offset, int operator, int value) throws IOException {
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

	public int add(long offset, int value) throws IOException {
		rfData.seek(offset);
		value = (int) (value + rfData.readInt());

		rfData.seek(offset);
		rfData.writeInt(value);
		return value;
	}
	public int addPositive(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue + value);
		if(result<0)
			if(oldValue>0 && value>0)
				result=Integer.MAX_VALUE;
			else
				result=0;

		rfData.seek(offset);
		rfData.writeInt(result);
		return result;
	}


	public int add_InBound(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue + value);

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeInt(result);
		return result;
	}
	public int multiplication(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue * value);

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeInt(result);
		return result;
	}
	public int division(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue / value);

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeInt(result);
		return result;
	}
	public int modulus(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue % value);

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeInt(result);
		return result;
	}

	public int and(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue & value);

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public int or(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue | value);

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public int not(long offset, int value) throws IOException {
		rfData.seek(offset);
		int result = (int) (~value);

		if(value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public int xor(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue ^ value);

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public int nand(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (~(oldValue & value));

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public int leftShift(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue << value);

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public int rightShift(long offset, int value) throws IOException {
		rfData.seek(offset);
		int oldValue = rfData.readInt();
		int result = (int) (oldValue >> value);

		if(oldValue>0 && value>0 && result<=0)
			result=Integer.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Integer.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
}
