package web_admin.http.header3.Table;

import java.io.IOException;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBTable_2Primary;
import web_admin.API;
import web_admin.DES;
import web_admin.http.dto.DTO_Column;

@Http(api = API.DBTable_Create_Primary2)
@HttpDocument(id = 3022, Header = API.H3_Database, Description = DES.Table_Create)
public class HttpProcessingTable022_CreateTable_2Primary extends BaseCreateTable {
	public String HashName;
	public byte TypeHash;
	public String RangeName;
	public byte TypeRange;
	public DTO_Column[] Describes;
	
	private transient DBTable_2Primary database;
	@Override public byte onTableType() {return BGBaseDatabase.DBTYPE_2Primary;}
	@Override public BGBaseDatabase onCreateTable(String path) throws IOException {
		if(HashName==null || RangeName==null || (HashName.getBytes().length+RangeName.getBytes().length)>500)
			throw new IOException("Invalid params");
		
		///////////////////////////////////////////////////////////////////////////////////////////
		BGColumn[] list = null;
		if (Describes != null && Describes.length > 0) {
			int numberDes = Describes.length;
			list = new BGColumn[numberDes];
			for (int i = 0; i < numberDes; i++) {
				list[i] = Describes[i].toDescribe();
				if(list[i].validate() == false) {
					list[i].trace();
					throw new IOException("Describe invalid");
				}
			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////
		database=new DBTable_2Primary(path);
		database.initNewDatabase(TypeHash, HashName, TypeRange, RangeName, list);
		return database;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(database!=null)
			database.closeDatabase();
	}
	
	
	
	
	
	
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		
		TableName = "Primary2";
		Description = "Primary2";
		
		HashName = "Khóa thứ I";
		TypeHash=DBDefine_DataType.STRING;
		
		RangeName = "Khóa thứ II";
		TypeRange = DBDefine_DataType.INTEGER;
		
		ViewId = random.nextInt(100);
		
		
		
        int count=0;
        BGColumn describe;
        BGColumn[] listRandom = new BGColumn[3];
        /////////////////////////////////////////////////////////////////////////////////////////
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.IPV4;
        describe.Size = 4;
        describe.ColumnName = "IPV4";
        describe.loadDefaultData(123456);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.IPV6;
        describe.Size = 16;
        describe.ColumnName = "IPV6";
        describe.loadDefaultData(null);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.LONG;
        describe.Size = 8;
        describe.ColumnName = "Gold";
        describe.loadDefaultData((long)0);
        listRandom[count++]=describe;
        
        Describes=new DTO_Column[count];
        for(int i=0;i<count;i++)
        	Describes[i]=new DTO_Column(listRandom[i]);
	}

}