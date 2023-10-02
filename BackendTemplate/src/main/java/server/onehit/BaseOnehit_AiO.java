package server.onehit;

import java.io.File;
import java.util.regex.Pattern;

import backendgame.com.core.MessageSending;
import backendgame.com.core.OneHitProcessing;
import database_game.DatabaseManager;
import server.config.CipherBinary;
import server.config.PATH;
import server.config.RichardToken;
import server.config.StatusOnehit;

public abstract class BaseOnehit_AiO extends OneHitProcessing {
	public static DatabaseManager managerTable;
	
	protected static MessageSending mgSuccess;
	protected static MessageSending mgPending;
	protected static MessageSending mgAccountNotExist;
	protected static MessageSending mgAccountExisted;
	protected static MessageSending mgPasswordIncorrect;
	protected static MessageSending mgEmailCodeIncorrect;
//	protected static MessageSending mgValueNull;
//	protected static MessageSending mgNotEnought;
//	protected static MessageSending mgOutOfRange;
//	protected static MessageSending mgPasswordError;
	protected static MessageSending mgDatabaseNotExist;
//	protected static MessageSending mgExpired;
	protected static MessageSending mgTokenError;
//	protected static MessageSending mgSyntaxError;
//	protected static MessageSending mgTimeout;
	protected static MessageSending mgTableNotExist;
	protected static MessageSending mgParamsInvalid;
	protected static MessageSending mgVariableInvalid;
	protected static MessageSending mgInvalid;
	protected static MessageSending mgException;
//	protected static MessageSending mgExist;
//	protected static MessageSending mgNotExist;
//	protected static MessageSending mgPlayerWrong;
//	protected static MessageSending mgPlayerNotFound;
//	protected static MessageSending mgPlayerInvalidData;
	protected static MessageSending mgAccessKey;
	protected static MessageSending mgReadKey;
	protected static MessageSending mgWriteKey;

	public static Pattern patternUsername;
	public static Pattern patternEmail;
	
	public static final void setup() {
		if(new File(PATH.DATABASE_FOLDER).exists()==false)
    		new File(PATH.DATABASE_FOLDER).mkdirs(); 
		managerTable = DatabaseManager.gI();
//		dataType = BB_DataType.gI();
//		
//		randomCache=RandomCache.gI();
//		ClientToken.randomCache=randomCache;
		RichardToken.cipherBinary=new CipherBinary();
		
		mgSuccess=new MessageSending((short) 0,StatusOnehit.Success);
		mgPending=new MessageSending((short) 0,StatusOnehit.Pending);
		mgAccountNotExist=new MessageSending((short) 0,StatusOnehit.AccountNotExist);
		mgAccountExisted=new MessageSending((short) 0,StatusOnehit.AccountExisted);
		mgPasswordIncorrect=new MessageSending((short) 0,StatusOnehit.PasswordIncorrect);
		mgEmailCodeIncorrect=new MessageSending((short) 0,StatusOnehit.EmailCodeIncorrect);
//		mgValueNull=new MessageSending((short) 0,CaseCheck.VALUE_NULL);
//		mgNotEnought=new MessageSending((short) 0,CaseCheck.NOT_ENOUGHT);
//		mgOutOfRange=new MessageSending((short) 0,CaseCheck.OUT_OF_RANGE);
//		mgPasswordError=new MessageSending((short) 0,CaseCheck.PASSWORD_ERROR);
		mgDatabaseNotExist=new MessageSending((short) 0,StatusOnehit.Database_Not_Exist);
//		mgExpired=new MessageSending((short) 0,CaseCheck.EXPIRED);
		mgTokenError=new MessageSending((short) 0,StatusOnehit.TokenError);
//		mgSyntaxError=new MessageSending((short) 0,CaseCheck.SYNTAX_ERROR);
//		mgTimeout=new MessageSending((short) 0,CaseCheck.TIMEOUT);
		mgTableNotExist=new MessageSending((short) 0,StatusOnehit.Table_Not_Exist);
		mgParamsInvalid=new MessageSending((short) 0,StatusOnehit.PARAMS_INVALID);
		mgVariableInvalid=new MessageSending((short) 0,StatusOnehit.VARIABLE_INVALID);
		mgInvalid=new MessageSending((short) 0,StatusOnehit.INVALID);
		mgException=new MessageSending((short) 0,StatusOnehit.EXCEPTION);
//		mgExist=new MessageSending((short) 0,CaseCheck.EXIST);
//		mgNotExist=new MessageSending((short) 0,CaseCheck.NOT_EXIST);
//		mgPlayerWrong=new MessageSending((short) 0,CaseCheck.PLAYER_WRONG);
//		mgPlayerNotFound=new MessageSending((short) 0,CaseCheck.PLAYER_NOT_FOUND);
//		mgPlayerInvalidData=new MessageSending((short) 0,CaseCheck.PLAYER_INVALIDATE_DATA);
		mgAccessKey=new MessageSending((short) 0,StatusOnehit.AccessKey);
		mgReadKey=new MessageSending((short) 0,StatusOnehit.ReadKey);
		mgWriteKey=new MessageSending((short) 0,StatusOnehit.WriteKey);
		
		patternUsername=Pattern.compile("^[A-Za-z]\\w{5,29}$");
		patternEmail=Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$");
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
	}
	
	
	
	
	public long[] rangeToArray(long begin,long end) {
		if(begin>end) {
			int length = (int) (begin-end+1);
			long[] result=new long[length];
			for(int i=0;i<length;i++)
				result[i] = begin - i;
			return result;
		}else {
			int length = (int) (end-begin+1);
			long[] result=new long[length];
			for(int i=0;i<length;i++)
				result[i] = begin + i;
			return result;
		}
	}
	
	
}
