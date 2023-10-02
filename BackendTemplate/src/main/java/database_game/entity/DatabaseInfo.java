package database_game.entity;

import java.util.ArrayList;

public class DatabaseInfo {
	public short DBId;
	public int ViewId;
	public String DatabaseName;
	public String Description;
	
	public boolean AccountLogin;
	public ArrayList<TableInfo> listTable;
}
