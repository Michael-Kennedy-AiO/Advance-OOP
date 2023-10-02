package web_admin.http.header3.Table_b3.Row;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import backendgame.com.core.BGUtility;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBTableRow;
import web_admin.API;
import web_admin.MyRespone;
import web_admin.StringConstant;
import web_admin.http.BaseHttpAdminWeb_SignedIn;
import web_admin.http.dto.DTO_Column;

public abstract class BaseQuerryTableRow extends BaseHttpAdminWeb_SignedIn {
	public short DBId;
	public short TableId;
	
	public abstract long[] onListRowIds(DBTableRow database,BackendSession session) throws IOException;
	
	private transient DBTableRow database;
	@Override public MyRespone onPOST(BackendSession session) {
		try {
			if(new File(managingDatabase.pathTable(DBId, TableId)+BGBaseDatabase.INDEX_EXTENSION).getParentFile().exists()==false)
				return API.resTableNotExist;
				
			database=new DBTableRow(managingDatabase.pathTable(DBId, TableId));
			if(database.length()<BGBaseDatabase.HEADER)
				return new MyRespone(MyRespone.STATUS_TableNotExist).setMessage("Table is not init");
			if(database.readByte(BGBaseDatabase.Offset_DatabaseType)!=BGBaseDatabase.DBTYPE_Row)
				return new MyRespone(MyRespone.STATUS_DatabaseNotExist).setMessage("Database invalid type");
			if(database.islocked())
				return API.resPending;
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			HashMap<String, Object> mapData = new HashMap<>();

			BGColumn[] listColumn = database.readBGColumn();
			DTO_Column[] listDescribes = null;
			if (listColumn != null) {
				int number = listColumn.length;
				listDescribes = new DTO_Column[number];
				for (int i = 0; i < number; i++)
					listDescribes[i] = new DTO_Column(listColumn[i]);
			}
			mapData.put(StringConstant.Describes, listDescribes);

			if (database.countRow() == 0)
				mapData.put(StringConstant.DataRow, null);
			else {
				long[] listRowIds = onListRowIds(database, session);
				if (listRowIds == null)
					return API.resInvalid;
				if (listRowIds.length == 0)
					return API.resNotFound;

				ArrayList<ArrayList<Object>> listDataRow = new ArrayList<>();
				for (long rowId : listRowIds)
					listDataRow.add(database.loadRow(rowId, listColumn));
				mapData.put(StringConstant.DataRow, listDataRow);
			}

			return new MyRespone(MyRespone.STATUS_Success).setData(mapData);
		} catch (IOException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_Exception).setMessage(BGUtility.getStringException(e));
		}
	}

	@Override public void onDestroy() {
		if(database!=null)
			database.closeDatabase();
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId=1;
		TableId=3;
	}
}
