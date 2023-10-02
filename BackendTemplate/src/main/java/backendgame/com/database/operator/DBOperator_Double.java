package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Double extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_Double(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public double process(long offset, int operator, double value) throws IOException {
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


	public double add(long offset, double value) throws IOException {
		rfData.seek(offset);
		value = (double) (value + rfData.readDouble());

		rfData.seek(offset);
		rfData.writeDouble(value);
		return value;
	}
	public double addPositive(long offset, double value) throws IOException {
		rfData.seek(offset);
		double oldValue = rfData.readDouble();
		double result = (double) (oldValue + value);
		if(result<0)
			if(oldValue>0 && value>0)
				result=Double.MAX_VALUE;
			else
				result=0;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}


	public double add_InBound(long offset, double value) throws IOException {
		rfData.seek(offset);
		double oldValue = rfData.readDouble();
		double result = (double) (oldValue + value);

		if(oldValue>0 && value>0 && result<=0)
			result=Double.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Double.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}
	public double multiplication(long offset, double value) throws IOException {
		rfData.seek(offset);
		double oldValue = rfData.readDouble();
		double result = (double) (oldValue * value);

		if(oldValue>0 && value>0 && result<=0)
			result=Double.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Double.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}
	public double division(long offset, double value) throws IOException {
		rfData.seek(offset);
		double oldValue = rfData.readDouble();
		double result = (double) (oldValue / value);

		if(oldValue>0 && value>0 && result<=0)
			result=Double.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Double.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}
	public double modulus(long offset, double value) throws IOException {
		rfData.seek(offset);
		double oldValue = rfData.readDouble();
		double result = (double) (oldValue % value);

		if(oldValue>0 && value>0 && result<=0)
			result=Double.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Double.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeDouble(result);
		return result;
	}
}
