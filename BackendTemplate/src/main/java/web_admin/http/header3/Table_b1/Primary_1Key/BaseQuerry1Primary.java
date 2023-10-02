package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import backendgame.com.core.BGUtility;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBBaseTable_Primary;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.StringConstant;
import web_admin.http.BaseHttpAdminWeb_SignedIn;
import web_admin.http.dto.DTO_Column;

public abstract class BaseQuerry1Primary extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	
	public abstract long[] onGetOffset(DBTable_1Primary database1Primary) throws IOException;
	
	private transient DBTable_1Primary database1Primary;
	@Override public MyRespone onPOST(BackendSession session) {
		try {
			if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.INDEX_EXTENSION).exists()==false)
				return API.resTableNotExist;
				
			database1Primary=new DBTable_1Primary(managingDatabase.pathTable(DBId, TableId));
			if(database1Primary.length()<BGBaseDatabase.HEADER)
				return new MyRespone(MyRespone.STATUS_TableNotExist).setMessage("Table is not init");
			if(database1Primary.readByte(BGBaseDatabase.Offset_DatabaseType)!=BGBaseDatabase.DBTYPE_1Primary)
				return new MyRespone(MyRespone.STATUS_DatabaseNotExist).setMessage("Database invalid type");
			if(database1Primary.islocked())
				return API.resPending;
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			HashMap<String, Object> mapData=new HashMap<>();
			mapData.put(StringConstant.PrimaryType, database1Primary.primaryType);
			mapData.put(StringConstant.PrimaryName, database1Primary.readUTF(DBBaseTable_Primary.Offset_Primary_Name));
			mapData.put(StringConstant.TotalRow, database1Primary.countRow());
			
			BGColumn[] list=database1Primary.readBGColumn();
			DTO_Column[] listDescribes=null;
			if(list!=null) {
				int number=list.length;
				listDescribes=new DTO_Column[number];
				for(int i=0;i<number;i++)
					listDescribes[i]=new DTO_Column(list[i]);
			}
			mapData.put(StringConstant.Describes, listDescribes);
			
			if(database1Primary.countRow()==0)
				mapData.put(StringConstant.DataRow, null);
			else{
				long[] onlistOffset=onGetOffset(database1Primary);
				if(onlistOffset==null)
					return API.resInvalid;
				if(onlistOffset.length==0)
					return API.resNotFound;
				
				
				ArrayList<ArrayList<Object>> listDataRow = new ArrayList<>();
				for(long offset:onlistOffset)
					listDataRow.add(database1Primary.loadRow(offset, list));
				
				mapData.put(StringConstant.DataRow, listDataRow);
			}
			
			return new MyRespone(MyRespone.STATUS_Success).setData(mapData);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception).setMessage(BGUtility.getStringException(e));
		}
	}

	@Override public void onDestroy() {
		if(database1Primary!=null)
			database1Primary.closeDatabase();
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		TableId=1;
	}
}
