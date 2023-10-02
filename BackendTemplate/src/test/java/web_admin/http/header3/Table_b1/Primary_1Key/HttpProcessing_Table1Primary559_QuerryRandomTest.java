package web_admin.http.header3.Table_b1.Primary_1Key;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.google.gson.GsonBuilder;

import backendgame.com.core.database.BGColumn;
import backendgame.com.database.table.DBTable_1Primary;
import database_game.DatabaseManager;
import web_admin.StringConstant;
import web_admin.http.dto.DTO_Column;

class HttpProcessing_Table1Primary559_QuerryRandomTest {
	public short DBId=1;
	public short TableId=1;
	public DBTable_1Primary database1Primary;

	@Test void test() throws IOException {
		Random random=new Random();
		database1Primary=new DBTable_1Primary(DatabaseManager.gI().pathTable(DBId, TableId));
		
		HashMap<String, Object> mapData=new HashMap<>();
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
			long countRow=database1Primary.countRow();
			long[] onlistOffset=new long[100];
			for(int i=0;i<onlistOffset.length;i++) {
				database1Primary.rfBTree.seek(random.nextInt((int) countRow)*8);
				onlistOffset[i]=database1Primary.rfBTree.readLong();
			}
			
			ArrayList<ArrayList<Object>> listDataRow = new ArrayList<>();
			for(long offset:onlistOffset)
				listDataRow.add(database1Primary.loadRow(offset, list));
			mapData.put(StringConstant.DataRow, listDataRow);
		}
		
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(mapData));
	}

	@AfterEach void tearDown() throws Exception {
	}
}
