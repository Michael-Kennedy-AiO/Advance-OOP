package database_game.entity;

public class LockTable {
	protected Object[] listIndexing;
	
	public LockTable() {
		listIndexing=new Object[Short.MAX_VALUE];
	
	}
	
	public Object getLockIndex(short lockId) {
		if(listIndexing[lockId]==null)
			listIndexing[lockId]=new Object();
		return listIndexing[lockId];
	}
}
