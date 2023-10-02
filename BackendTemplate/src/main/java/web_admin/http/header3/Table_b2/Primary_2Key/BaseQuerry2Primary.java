package web_admin.http.header3.Table_b2.Primary_2Key;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import backendgame.com.core.BGUtility;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBBaseTable_Primary;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.StringConstant;
import web_admin.http.BaseHttpAdminWeb_SignedIn;
import web_admin.http.dto.DTO_Column;

public abstract class BaseQuerry2Primary extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	
	public abstract long[] onGetOffset(DBTable_2Primary database2Primary) throws IOException;
	
	private transient DBTable_2Primary database2Primary;
	@Override public MyRespone onPOST(BackendSession session) {
		try {
			if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.INDEX_EXTENSION).exists()==false)
				return API.resTableNotExist;
				
			database2Primary=new DBTable_2Primary(managingDatabase.pathTable(DBId, TableId));
			if(database2Primary.length()<BGBaseDatabase.HEADER)
				return new MyRespone(MyRespone.STATUS_TableNotExist).setMessage("Table is not init");
			if(database2Primary.readByte(BGBaseDatabase.Offset_DatabaseType)!=BGBaseDatabase.DBTYPE_2Primary)
				return new MyRespone(MyRespone.STATUS_DatabaseNotExist).setMessage("Database invalid type");
			if(database2Primary.islocked())
				return API.resPending;
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			HashMap<String, Object> mapData=new HashMap<>();
			
			database2Primary.getRandomAccessFile().seek(DBBaseTable_Primary.Offset_Primary_Name);
			mapData.put(StringConstant.HashName, database2Primary.getRandomAccessFile().readUTF());
			mapData.put(StringConstant.RangeName, database2Primary.getRandomAccessFile().readUTF());
			mapData.put(StringConstant.TotalRow, database2Primary.countRow());
			
			BGColumn[] list=database2Primary.readBGColumn();
			DTO_Column[] listDescribes=null;
			if(list!=null) {
				int number=list.length;
				listDescribes=new DTO_Column[number];
				for(int i=0;i<number;i++)
					listDescribes[i]=new DTO_Column(list[i]);
			}
			mapData.put(StringConstant.Describes, listDescribes);
			
			if(database2Primary.countRow()==0)
				mapData.put(StringConstant.DataRow, null);
			else{
				long[] onlistOffset=onGetOffset(database2Primary);
				if(onlistOffset==null)
					return API.resInvalid;
				if(onlistOffset.length==0)
					return API.resNotFound;
				
				ArrayList<ArrayList<Object>> listDataRow = new ArrayList<>();
				for(long offset:onlistOffset)
					listDataRow.add(database2Primary.loadRow(offset, list));
				mapData.put(StringConstant.DataRow, listDataRow);
			}
			
			return new MyRespone(MyRespone.STATUS_Success).setData(mapData);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception).setMessage(BGUtility.getStringException(e));
		}
	}

	@Override public void onDestroy() {
		if(database2Primary!=null)
			database2Primary.closeDatabase();
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		TableId=2;
	}
}
