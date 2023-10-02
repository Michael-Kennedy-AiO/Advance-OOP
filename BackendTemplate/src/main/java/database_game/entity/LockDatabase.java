package database_game.entity;

public class LockDatabase {
	protected LockTable[] listTable;
	protected Object[] listIndexing;
	public LockDatabase() {
		listTable=new LockTable[Short.MAX_VALUE];
		listIndexing=new Object[Short.MAX_VALUE];
	}
	
	public Object getLockIndex(int lockId) {
		if(listIndexing[lockId]==null)
			listIndexing[lockId]=new Object();
		return listIndexing[lockId];
	}
	
	public LockTable getLockTable(int TableId) {
		if(listTable[TableId]==null)
			listTable[TableId]=new LockTable();
		return listTable[TableId];
	}
}
