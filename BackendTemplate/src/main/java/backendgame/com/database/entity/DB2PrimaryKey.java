package backendgame.com.database.entity;

import java.util.Arrays;

public class DB2PrimaryKey {
	public Object hashKey;
	public Object rangeKey;
	
	public DB2String to2String() {
		DB2String db2String = new DB2String();
		if(hashKey.getClass()==byte[].class)
			db2String.string1=Arrays.toString((byte[])hashKey);
		else
			db2String.string1=hashKey.toString();
		
		if(rangeKey==null)
			db2String.string2="Null";
		else if(rangeKey.getClass()==byte[].class)
			db2String.string2=Arrays.toString((byte[])rangeKey);
		else
			db2String.string2=rangeKey.toString();
		
		return db2String;
	}
	
	public String toString() {
		return hashKey+" "+rangeKey;
	}
	
	public void trace() {
		System.out.println("hashKey("+hashKey+")	rangeKey("+rangeKey+")");
	}
}
