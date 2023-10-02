package server.config;

public class StatusOnehit {
	public static final byte Success=1;
	
	
	
	
	
	
	
	
	
	
	public static final byte AccountNotExist = 2;
	public static final byte AccountExisted = 2;
	public static final byte PasswordIncorrect = 3;
	public static final byte EmailCodeIncorrect = 4;
	public static final byte TokenError = 5;
	
	public static final byte AccessKey = 10;
	public static final byte ReadKey = 11;
	public static final byte WriteKey = 12;
	
	public static final byte Pending = -3;
	
	
	
	public static final byte EXCEPTION = -5;
	public static final byte INVALID = -6;
	public static final byte VARIABLE_INVALID = -7;
	public static final byte PARAMS_INVALID = -8;
	public static final byte Table_Not_Exist = -9;
	public static final byte Database_Not_Exist = -10;
}
