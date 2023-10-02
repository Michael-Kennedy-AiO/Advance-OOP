package web_admin.http.header3.Table;

import java.io.IOException;

import backendgame.com.core.BGUtility;
import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.table.DBTable_1Primary;
import web_admin.API;
import web_admin.DES;
import web_admin.http.dto.DTO_Column;

@Http(api = API.DBTable_Create_Primary1)
@HttpDocument(id = 3021, Header = API.H3_Database, Description = DES.Table_Create)
public class HttpProcessingTable021_CreateTable_1Primary extends BaseCreateTable {
	public String PrimaryName;
	public byte PrimaryType;
	public DTO_Column[] Describes;
	
	private transient DBTable_1Primary database;
	@Override public byte onTableType() {return BGBaseDatabase.DBTYPE_1Primary;}
	@Override public BGBaseDatabase onCreateTable(String path) throws IOException {
		if(PrimaryName==null || PrimaryName.getBytes().length>500)
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
		database=new DBTable_1Primary(path);
		database.initNewDatabase(PrimaryType, PrimaryName, list);
		return database;
	}
	
	@Override public void onDestroy() {
		super.onDestroy();
		if(database!=null)
			database.closeDatabase();
	}
	
	@Override public void test_api_in_document() {
		super.test_api_in_document();
		DBId = 1;
		TableName = "Primary1";
		Description = "Primary1";
		
		PrimaryName = "Khóa chính";
		PrimaryType=DBDefine_DataType.STRING;
		
		ViewId = random.nextInt(100);
		
        int count=0;
        BGColumn describe;
        BGColumn[] listRandom = new BGColumn[9];
        /////////////////////////////////////////////////////////////////////////////////////////
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BOOLEAN;
        describe.Size = 1;
        describe.ColumnName = "Cột một";
        describe.loadDefaultData(true);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BYTE;
        describe.Size = 1;
        describe.ColumnName = "Cộ Số 2";
        describe.loadDefaultData((byte)3);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.SHORT;
        describe.Size = 2;
        describe.ColumnName = "Column2";
        describe.loadDefaultData((short)12);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.FLOAT;
        describe.Size = 4;
        describe.ColumnName = "Column3";
        describe.loadDefaultData(12.34f);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.INTEGER;
        describe.Size = 4;
        describe.ColumnName = "Column4";
        describe.loadDefaultData(4567);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.DOUBLE;
        describe.Size = 8;
        describe.ColumnName = "Column5";
        describe.loadDefaultData(1234.5678d);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.LONG;
        describe.Size = 8;
        describe.ColumnName = "Column6";
        describe.loadDefaultData(9999l);
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.STRING;
        describe.ColumnName = "Column7";
        describe.loadDefaultData("Đức Trí");
        listRandom[count++]=describe;
        
        describe=new BGColumn();
        describe.Type = DBDefine_DataType.BYTE_ARRAY;
        describe.Size = 8;
        describe.ColumnName = "Column8";
        byte[] dataTmp = new byte[describe.Size];
        BGUtility.random.nextBytes(dataTmp);
        describe.loadDefaultData(dataTmp);
        listRandom[count++]=describe;
        
        Describes=new DTO_Column[count];
        for(int i=0;i<count;i++)
        	Describes[i]=new DTO_Column(listRandom[i]);
	}







}
