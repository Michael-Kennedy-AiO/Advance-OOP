package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Byte extends DBOperator{
	private RandomAccessFile rfData;
	public DBOperator_Byte(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	
	public byte process(long offset, byte operator, byte value) throws IOException {
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
	
	public byte add(long offset, byte value) throws IOException {
		rfData.seek(offset);
		value = (byte) (value + rfData.readByte());
		
		rfData.seek(offset);
		rfData.writeByte(value);
		return value;
	}
	public byte addPositive(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue + value);
		if(result<0)
			if(oldValue>0 && value>0)
				result=Byte.MAX_VALUE;
			else
				result=0;
		
		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	
	
	public byte add_InBound(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue + value);
		
		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;
		
		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;
		
		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte multiplication(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue * value);

		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte division(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue / value);

		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte modulus(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue % value);

		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte and(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue & value);

		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte or(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue | value);

		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte not(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte result = (byte) (~value);

		if(value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte xor(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue ^ value);

		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte nand(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (~(oldValue & value));

		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte leftShift(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue << value);

		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	public byte rightShift(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue >> value);

		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}

}
