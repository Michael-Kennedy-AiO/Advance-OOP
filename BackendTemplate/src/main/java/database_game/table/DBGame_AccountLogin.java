package database_game.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.TimeManager;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.database.indexing.DBBaseType;
import backendgame.com.core.database.indexing.Indexing1Primary;
import backendgame.com.core.database.indexing.IndexingBase;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.entity.BGAttribute;
import database_game.AccountType;
import database_game.DatabaseManager;

public class DBGame_AccountLogin extends IndexingBase{//Cập nhật UserData thường chỉ dùng đến UserId = RowId của UserData
	public static final long HEADER_LENGTH					= 2048;
	
	public static final long Offset_DatabaseType		 	= 0;
	public static final long Offset_TimeAvaiable		 	= 1;
	public static final long Offset_AdminId					= 9;//UserId-DynamoDB
	public static final long Offset_Permission	 			= 17;//1 byte
	public static final long Offset_LogoutId	 			= 18;//1 byte
	public static final long Offset_AccessKey			 	= 19;
	public static final long Offset_ReadKey				 	= 27;
	public static final long Offset_WriteKey			 	= 35;
	
	public static final long Offset_TimeCreate		 		= 43;
	public static final long Offset_Version					= 51;
	
	public static final long Offset_Token_LifeTime			= 62;
	
	public static final long Offset_Permission_Device 			= 100;
	public static final long Offset_Permission_SystemAccount 	= 101;
	public static final long Offset_Permission_Facebook 		= 102;
	public static final long Offset_Permission_Google 			= 103;
	public static final long Offset_Permission_AdsId 			= 104;
	public static final long Offset_Permission_Apple 			= 105;
	public static final long Offset_Permission_EmailCode		= 106;
	
	public static final long Offset_TrackingCreate_Today 		= 200;
	public static final long Offset_TrackingCreate_Yesterday 	= 204;
	public static final long Offset_TrackingCreate_ThisMonth 	= 208;
	public static final long Offset_TrackingCreate_AllTime 		= 212;//Kiểu Long
	
	public static final long Offset_TrackingLogin_Today 		= 220;
	public static final long Offset_TrackingLogin_Yesterday 	= 224;
	public static final long Offset_TrackingLogin_ThisMonth 	= 228;
	public static final long Offset_TrackingLogin_AllTime 		= 232;//Kiểu Long
	
	public static final long Offset_MailService			 		= 512;
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final int PASSWORD_LENGTH=20;
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static TimeManager timeManager;
	public static DatabaseManager databaseManager;
	public short DBId;
	public String path;
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public DBGame_AccountLogin(short _DBId) throws FileNotFoundException {
		DBId=_DBId;
		path=databaseManager.pathAccountLogin(_DBId);
		rfData = new RandomAccessFile(path+BGBaseDatabase.DATA_EXTENSION, "rw");
		rfBTree = new RandomAccessFile(path+BGBaseDatabase.INDEX_EXTENSION, "rw");
	}
	
	private DBBaseType hashKey;
	private DBBaseType rangeKey;
	private void initBtree() throws IOException {
		if (hashKey == null || rangeKey == null) {
			hashKey = getDBType(DBDefine_DataType.STRING);
			rangeKey = getDBType(DBDefine_DataType.BYTE);
		}
	}
	
	public void initNewDatabase() throws IOException {
		writeByte(Offset_LogoutId, (byte) 1);
		writeLong(Offset_AccessKey, 100000 + BGUtility.random.nextInt(900000));
		writeLong(Offset_ReadKey, 100000 + BGUtility.random.nextInt(900000));
		writeLong(Offset_WriteKey, 100000 + BGUtility.random.nextInt(900000));
		writeLong(Offset_TimeCreate, System.currentTimeMillis());
		writeLong(Offset_Version, 1);
		
		writeLong(Offset_Token_LifeTime, 100*TimeManager.A_YEAR_MILLISECOND);
		
		writeByte(Offset_Permission_Device, (byte) 1);
		writeByte(Offset_Permission_SystemAccount, (byte) 1);
		writeByte(Offset_Permission_Facebook, (byte) 1);
		writeByte(Offset_Permission_Google, (byte) 1);
		writeByte(Offset_Permission_AdsId, (byte) 1);
		writeByte(Offset_Permission_Apple, (byte) 1);
		writeByte(Offset_Permission_EmailCode, (byte) 0);//Need setup Mail(Google)
		
		rfData.setLength(HEADER_LENGTH);
	}
	
	public void writeMailService(HashMap<String, String> listEmail) throws IOException {
		Set<String> set = listEmail.keySet();
		rfData.seek(Offset_MailService);
		rfData.writeByte(set.size());
		for(String email:set){
			rfData.writeUTF(email);
			rfData.writeUTF(listEmail.get(email));
		}
	}
	public HashMap<String, String> readMailService() throws IOException {
		HashMap<String, String> listEmail=new HashMap<>();
		rfData.seek(Offset_MailService);
		byte numberEmail=rfData.readByte();
		for(byte i=0;i<numberEmail;i++)
			listEmail.put(rfData.readUTF(), rfData.readUTF());
		return listEmail;
	}
	
	public void skipPrimary() throws IOException {rfData.skipBytes(rfData.readUnsignedShort()+2);}
	
	public void writeByte(long offset,byte _value) throws IOException {rfData.seek(offset);rfData.writeByte(_value);;}
	public void writeData(long offset,byte[] _data) throws IOException {rfData.seek(offset);rfData.write(_data);}
	public void writeLong(long offset,long _value) throws IOException {rfData.seek(offset);rfData.writeLong(_value);;}
	public byte readByte(long offset) throws IOException {rfData.seek(offset);return rfData.readByte();}
	public int readInt(long offset) throws IOException {rfData.seek(offset);return rfData.readInt();}
	public long readLong(long offset) throws IOException {rfData.seek(offset);return rfData.readLong();}
	
	public long countRow() throws IOException {return rfBTree.length()/8;}
	public boolean isExisted() throws IOException {return !(rfData.length()<HEADER_LENGTH);}
	
	public void closeDatabase() {if(rfBTree!=null)try {rfBTree.close();} catch (IOException e) {}if(rfData!=null)try {rfData.close();} catch (IOException e) {}}
	public void deleteAndClose(){try {Files.deleteIfExists(FileSystems.getDefault().getPath(path+BGBaseDatabase.DATA_EXTENSION));} catch (IOException e) {e.printStackTrace();}try {Files.deleteIfExists(FileSystems.getDefault().getPath(path+BGBaseDatabase.INDEX_EXTENSION));} catch (IOException e) {e.printStackTrace();}}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long readUserId(long offsetCredential) throws IOException {
		rfData.seek(offsetCredential);
		rfData.skipBytes(rfData.readUnsignedShort()+2);
		return rfData.readLong();
	}
	public long readTimeCreate(long offsetCredential) throws IOException {
		rfData.seek(offsetCredential);
		rfData.skipBytes(rfData.readUnsignedShort()+10);
		return rfData.readLong();
	}
	public String readPassword(long offsetCredential) throws IOException {
		rfData.seek(offsetCredential);
		rfData.skipBytes(rfData.readUnsignedShort()+18);
		return rfData.readUTF();
	}
	public long readEmailCode(long offsetCredential) throws IOException {
		rfData.seek(offsetCredential);
		rfData.skipBytes(rfData.readUnsignedShort()+18);
		return rfData.readLong();
	}
	public long querryOffset(String credential, byte accountType) throws IOException {
		long index=querryIndex(credential,accountType);
		if(index==-1)
			return -1;
		rfBTree.seek(index*8);
		return rfBTree.readLong();
	}
	
	public ArrayList<Object> loadRow(long userId, DBGame_UserData databaseUserData, BGColumn[] listDescribes) throws IOException {
		ArrayList<Object> dataRow = new ArrayList<>();

		rfData.seek(databaseUserData.getOffsetOfCredential(userId));
		dataRow.add(rfData.readUTF());// Credential
		byte accountType = rfData.readBoolean()?rfData.readByte():-1;
		dataRow.add(accountType);// AccountType
		dataRow.add(rfData.readLong());//UserId
		dataRow.add(timeManager.getStringDayMonthYear(rfData.readLong()));// TimeCreateAccount
		
		dataRow.add(databaseUserData.getStatus(userId));
		if(listDescribes!=null)
			for(BGColumn describe:listDescribes)
				if(describe.Type==DBDefine_DataType.IPV4)
					dataRow.add(InetAddress.getByAddress(databaseUserData.readBytes(userId, describe,4)));
				else if(describe.Type==DBDefine_DataType.IPV6)
					dataRow.add(InetAddress.getByAddress(databaseUserData.readBytes(userId, describe,16)));
				else
					dataRow.add(databaseUserData.readValue(userId, describe));
		return dataRow;
	}
	/*
	 * String credential
	 * byte accountType
	 * long userId
	 * long timeCreate
	 * String password-EmailCode
	 * */
	public long insertAccount(String credential, byte accountType, String password, DBGame_UserData databaseUserData,BGAttribute... listObWriter) throws IOException {
		int strBytesLent = password==null?0:password.getBytes().length;
		if(strBytesLent>20)
			throw new IOException("Can't insert Account : password too long  : "+strBytesLent+">20 byte");
		
		synchronized (databaseManager.getLockDatabase(DBId)) {
			if(querryIndex(credential,accountType)!=-1)
				return -1;
			
			long offsetInsert = rfData.length();
			long newUserId = databaseUserData.countRow();
			
			indexing(offsetInsert);
			rfData.seek(offsetInsert);
			hashKey.writeData(credential);
			rfData.writeBoolean(true);
			rangeKey.writeData(accountType);
			rfData.writeLong(newUserId);//UserId
			rfData.writeLong(System.currentTimeMillis());//TimeCreateAccount
			//////////////////////////////////////////////////////////////////////////////
			if(accountType==AccountType.SystemAccount) {
				rfData.writeUTF(password);
				for(int i=strBytesLent;i<20;i++)
					rfData.writeByte(0);
			}else if(accountType==AccountType.EmailCode)
				rfData.writeLong(0);//EmailCode
			//////////////////////////////////////////////////////////////////////////////
			databaseUserData.insertRow(newUserId, offsetInsert,listObWriter);
			return newUserId;
		}
	}
	public long querryIndex(String credential, byte accountType) throws IOException {
		initBtree();
		hashKey.setDataIndex(credential);
		rangeKey.setDataIndex(accountType);
		return querry_index();
	}
	
	public long[] querryIndexLike(String credential,int limit) throws IOException {
		Indexing1Primary querry = new Indexing1Primary(DBDefine_DataType.STRING,rfData,rfBTree);
		return querry.querryIndexLike(credential, limit);
	}
	
	

	@Override protected void compareQuerry() throws IOException {
		compare=hashKey.compareQuerry();
		if(compare==0)
			if(rfData.readBoolean())////////////////////////////////////////////////////
				if(rangeKey==null)
					compare=1;
				else
					compare=rangeKey.compareQuerry();
			else
				compare = (byte) (rangeKey==null?0:-1);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long[] querryUserId(String credential) throws IOException {
		long[] listOffset = querryOffset(credential);
		if(listOffset!=null) {
			int numberOffset = listOffset.length;
			for(int i=0;i<numberOffset;i++)
				listOffset[i] = readUserId(listOffset[i]);
		}
		return listOffset;
	}
	private void seekToIndex(long index) throws IOException {
		rfBTree.seek(index*8);
		rfData.seek(rfBTree.readLong());
	}
	public long[] querryIndex(String credential) throws IOException {
		Indexing1Primary querry = new Indexing1Primary(DBDefine_DataType.STRING,rfData,rfBTree);
		long btree = querry.querryIndex(credential);
		
		if(btree==-1)
			return null;
		
		long left = btree;
		while(left>0) {
			seekToIndex(left-1);
			querry.compareQuerry();
			if(querry.compare==0)
				left--;
			else
				break;
		}
		
		long countAccount = rfBTree.length()/8;
		long right = btree;

		while(right+1<countAccount) {
			seekToIndex(right+1);
			querry.compareQuerry();
			if(querry.compare==0)
				right++;
			else
				break;
		}
		
		int size = (int) (right-left+1);
		long[] result = new long[size];
		for(int i=0;i<size;i++)
			result[i] = left+i;
		
		return result;
	}
	public long[] querryOffset(String credential) throws IOException {
		initBtree();
		long[] result=querryIndex(credential);
		if(result!=null) {
			int length = result.length;
			for(int i=0;i<length;i++) {
				rfBTree.seek(result[i]*8);
				result[i]=rfBTree.readLong();
			}
		}
		return result;
	}
	
	
	public long getOffsetInBtree(long index) throws IOException {rfBTree.seek(index*8);return rfBTree.readLong();}
	public void trace() throws IOException {
		long offset;
		Object hash;
		Object range;
		long numberRow = countRow();
		for(long i=0;i<numberRow;i++) {
			offset = getOffsetInBtree(i);
			rfData.seek(offset);
			hash=rfData.readUTF();
			if(rfData.readBoolean())
				range=rfData.readByte();
			else
				range=null;
			System.out.println(i+"	"+" "+hash+" - "+range+"	=>"+offset);
		}
	}
	public void traceInfo(String credential) throws IOException {
		initBtree();
		long[] listOffset = querryOffset(credential);
		if(listOffset==null || listOffset.length==0)
			return;
		
		int maxCre = "Credential".length()>credential.length()?"Credential".length():credential.length();
		
		String tmpUid;
		int maxUid=6;
		for(long offset:listOffset) {
			rfData.seek(offset);
			rfData.skipBytes(rfData.readUnsignedShort());
			if(rfData.readBoolean())
				rfData.skipBytes(1);
			tmpUid=rfData.readLong()+"";
			if(maxUid<tmpUid.length())
				maxUid=tmpUid.length();
		}
		
		System.out.printf("%"+maxCre+"."+maxCre+"s%15.15s  %"+maxUid+"."+maxUid+"s	TimeCreate\n","Credential", "AccountType","UserId");
		for(long offset:listOffset) {
			rfData.seek(offset);
			credential = rfData.readUTF();
			byte accountType = rfData.readBoolean()?rfData.readByte():-1;
			long userId = rfData.readLong();
			long timeCreate = rfData.readLong();
			if(accountType==AccountType.SystemAccount)
				rfData.readUTF();
			else if(accountType==AccountType.EmailCode)
				rfData.readLong();
			
			System.out.printf("%"+maxCre+"."+maxCre+"s%15.15s  %"+maxUid+"."+maxUid+"s	"+TimeManager.gI().getStringTime(timeCreate)+"\n",credential,AccountType.getName(accountType),userId);
		}
	}
	public void traceInfo(String credential, DBGame_UserData databaseUserData) throws IOException {
		long[] listUserId = querryUserId(credential);
		if(listUserId==null)
			System.out.println("No User");
		else {
			int maxCre = "Credential".length()+5>credential.length()?"Credential".length()+5:credential.length();
			int maxUid=6;
			for(long userId:listUserId) {
				if(maxUid<(userId+"").length())
					maxUid=(userId+"").length();
			}
			
			System.out.printf("%"+maxCre+"."+maxCre+"s%15.15s  %"+maxUid+"."+maxUid+"s  %21.21s","Credential", "AccountType","UserId","TimeCreate");
			
			BGColumn describe;
			BGColumn[] list=databaseUserData.readBGColumn();
			for (int i = 0; i < list.length; i++) {
				describe = list[i];
				System.out.printf("  %" + describe.traceLength() + "." + describe.traceLength() + "s", describe.ColumnName);
			}
			System.out.println();
			for(long userId:listUserId) {
				traceInfo(userId, databaseUserData, list, maxUid);
			}
		}
	}
	public void traceInfo(DBGame_UserData databaseUserData,long... listUserId) throws IOException {
		int maxCre = "Credential".length()+5;
		int maxUid=6;
		for(long userId:listUserId) {
			if(maxUid<(userId+"").length())
				maxUid=(userId+"").length();
		}
		
		System.out.printf("%"+maxCre+"."+maxCre+"s%15.15s  %"+maxUid+"."+maxUid+"s  %21.21s","Credential", "AccountType","UserId","TimeCreate");
		
		BGColumn describe;
		BGColumn[] list=databaseUserData.readBGColumn();
		for (int i = 0; i < list.length; i++) {
			describe = list[i];
			System.out.printf("  %" + describe.traceLength() + "." + describe.traceLength() + "s", describe.ColumnName);
		}
		System.out.println();
		for(long userId:listUserId) {
			traceInfo(userId, databaseUserData, list, maxUid);
		}
	}
	
	private void traceInfo(long userId, DBGame_UserData databaseUserData,BGColumn[] list,int userIdSpace) throws IOException {
		long offsetCredential = databaseUserData.getOffsetOfCredential(userId);
		rfData.seek(offsetCredential);
		String credential=rfData.readUTF();
		byte accountType = rfData.readBoolean()?rfData.readByte():-1;
		rfData.skipBytes(8);//UserId
		long timeCreateAccount = rfData.readLong();
		if(accountType==AccountType.SystemAccount)
			rfData.skipBytes(rfData.readUnsignedShort());
		else if(accountType==AccountType.EmailCode)
			rfData.skipBytes(8);
		////////////////////////////////////////////////////
		int maxCre = "Credential".length()+5>credential.length()?"Credential".length()+5:credential.length();
		System.out.printf("%"+maxCre+"."+maxCre+"s%15.15s  %"+userIdSpace+"."+userIdSpace+"s  %21.21s",credential, AccountType.getName(accountType),userId,TimeManager.gI().getStringTime(timeCreateAccount));
		
//		System.out.print(dbid+"	status("+status+")	"+TimeManager.gI().getStringTime(timeCreateAccount)+"	Credential("+credential+")	userid("+userid+")	password("+password+")");
//		int numberColumn=databaseUserData.getNumberDescribe();
//		for(int i=0;i<numberColumn;i++)
//			System.out.print("	"+list[i].ColumnName+"("+databaseUserData.readData(userid, i)+")");
		
		BGColumn describe;
		if(list!=null)
			for (int i = 0; i < list.length; i++) {
				describe=list[i];
				if((99<describe.Type && describe.Type<120) || describe.Type==DBDefine_DataType.IPV6)
					System.out.printf("  %"+describe.traceLength()+"."+describe.traceLength()+"s", Arrays.toString((byte[])databaseUserData.readValue(userId, describe)));
				else
					System.out.printf("  %"+describe.traceLength()+"."+describe.traceLength()+"s", databaseUserData.readValue(userId, describe));
			}
		
		
		System.out.println();
	}
	
}
