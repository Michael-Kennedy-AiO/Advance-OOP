package web_admin;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.TimeManager;
import backendgame.com.database.table.DBString;
import database_game.AdminLoginManager;
import database_game.DatabaseManager;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.Server_BackendGame;
import server.config.CipherBinary;
import server.config.PATH;
import server.config.RichardToken;
import web_admin.http.BaseHttpAdminWeb_SignedIn;
import web_admin.http.header1.login.HttpProcessing_Login;

public class API {
	public static final String LoginScreen_Login 						= "/admin/login";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String Lobby_Create_Database					= "/admin/Lobby/Create-Database";
	public static final String Lobby_Setting_Database					= "/admin/Lobby/Setting-Database";
	public static final String Lobby_Get_DatabaseInfo					= "/admin/Lobby/Get-Database-Info";
	public static final String Lobby_Delete_Database					= "/admin/Lobby/Delete-Database";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String Database_AccountLogin_Enable				= "/admin/Database/AccountLogin/Enable";
	public static final String Database_AccountLogin_Remove				= "/admin/Database/AccountLogin/Remove";
	
	public static final String DBTable_Create_Primary1					= "/admin/Table/Create/1Primary";
	public static final String DBTable_Create_Primary2					= "/admin/Table/Create/2Primary";
	public static final String DBTable_Create_Row						= "/admin/Table/Create/Row";
	public static final String DBTable_Create_LineNode					= "/admin/Table/Create/LineNode";
	public static final String DBTable_Create_Leaderboard_Link			= "/admin/Table/Create/LeaderboardLink";
	public static final String DBTable_Create_Leaderboard_Commit		= "/admin/Table/Create/LeaderboardCommit";
	
	public static final String DBTable_Get_TableInfo					= "/admin/Table/Get-TableInfo";
	public static final String DBTable_Setting_TableInfo				= "/admin/Table/Setting-TableInfo";
	
	public static final String DBTable_Config_AccessToken				= "/admin/Table/Config/AccessToken";
	public static final String DBTable_Config_ReadKey					= "/admin/Table/Config/ReadKey";
	public static final String DBTable_Config_WriteKey					= "/admin/Table/Config/WriteKey";
//	public static final String SubTable_Describes_Change				= "/admin/Table/Describe-Change";
	public static final String SubTable_Delete_Table					= "/admin/Table/Delete";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String TableAccount_Get_TableInfo				= "/admin/TableAccount/Config/TableInfo";
	
	public static final String TableAccount_Config_AccessToken			= "/admin/TableAccount/Config/AccessToken";
	public static final String TableAccount_Config_ReadKey				= "/admin/TableAccount/Config/ReadKey";
	public static final String TableAccount_Config_WriteKey				= "/admin/TableAccount/Config/WriteKey";
	public static final String TableAccount_Config_TokenLifeTime		= "/admin/TableAccount/Config/TokenLifeTime";
	public static final String TableAccount_Config_LoginRule			= "/admin/TableAccount/Config/LoginRule";
	public static final String TableAccount_Config_MailService			= "/admin/TableAccount/Config/MailService";
	public static final String TableAccount_Describes_Get				= "/admin/TableAccount/Describes/Get";
	public static final String TableAccount_Describes_Update_ViewId		= "/admin/TableAccount/Describes/Update-ViewId";
	public static final String TableAccount_Describes_Change			= "/admin/TableAccount/Describes/Change";
	public static final String TableAccount_Describes_Indexing			= "/admin/TableAccount/Describes/Indexing";
	public static final String TableAccount_Config_LogoutAllAccount		= "/admin/TableAccount/Config/LogoutAllAccount";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String TABLERow_Create_Account						= "/admin/TableAccount/Create-Account";
	public static final String TABLERow_QuerryAccountData_ByCredential		= "/admin/AccountQuerry/Credential";
	public static final String TABLERow_QuerryAccountData_ByListUserId		= "/admin/AccountQuerry/ListUserId";
	public static final String TABLERow_QuerryAccountData_ByRange			= "/admin/AccountQuerry/RangeUserId";
	public static final String TABLERow_QuerryAccountData_ByLatest			= "/admin/AccountQuerry/LatestCreate";
	
	public static final String TABLERow_QuerryIndexing_ComparisonOperators	= "/admin/AccountQuerry/Indexing/ComparisonOperators";
	public static final String TABLERow_QuerryIndexing_Between				= "/admin/AccountQuerry/Indexing/Between";
	
	public static final String ParsingRow_Update_UserData					= "/admin/TableAccount/Update-UserData";
	public static final String ParsingRow_Update_AccountStatus				= "/admin/TableAccount/Update-AccountStatus";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String SubTable_1Primary_KeyName							= "/admin/Table1Primary/key-name";
	public static final String SubTable_1Primary_insert								= "/admin/Table1Primary/insert";
	public static final String SubTable_1Primary_update								= "/admin/Table1Primary/update";
	public static final String SubTable_1Primary_querry_primary						= "/admin/Table1Primary/querry-primary";
	public static final String SubTable_1Primary_querry_random						= "/admin/Table1Primary/querry-random";
	public static final String SubTable_1Primary_QuerryIndexing_ComparisonOperators	= "/admin/Table1Primary/Indexing/ComparisonOperators";
	public static final String SubTable_1Primary_QuerryIndexing_Between				= "/admin/Table1Primary/Indexing/Between";
	public static final String SubTable_1Primary_Describes_Change					= "/admin/Table1Primary/Describes/Change";
	public static final String SubTable_1Primary_Describes_Setting					= "/admin/Table1Primary/Describes/Setting";
	public static final String SubTable_1Primary_Describes_Indexing					= "/admin/Table1Primary/Describes/Indexing";
	public static final String SubTable_1Primary_delete								= "/admin/Table1Primary/Delete";
	
	public static final String SubTable_2Primary_KeyName							= "/admin/Table2Primary/key-name";
	public static final String SubTable_2Primary_insert								= "/admin/Table2Primary/insert";
	public static final String SubTable_2Primary_querry_primary1Key					= "/admin/Table2Primary/querry-1key";
	public static final String SubTable_2Primary_querry_primary2Key					= "/admin/Table2Primary/querry-2key";
	public static final String SubTable_2Primary_querry_random						= "/admin/Table2Primary/querry-random";
	public static final String SubTable_2Primary_QuerryIndexing_ComparisonOperators	= "/admin/Table2Primary/Indexing/ComparisonOperators";
	public static final String SubTable_2Primary_QuerryIndexing_Between				= "/admin/Table2Primary/Indexing/Between";
	public static final String SubTable_2Primary_Describes_Change					= "/admin/Table2Primary/Describes/Change";
	public static final String SubTable_2Primary_Describes_Setting					= "/admin/Table2Primary/Describes/Setting";
	public static final String SubTable_2Primary_Describes_Indexing					= "/admin/Table2Primary/Describes/Indexing";
	public static final String SubTable_2Primary_update								= "/admin/Table2Primary/update";
	public static final String SubTable_2Primary_delete								= "/admin/Table2Primary/delete";
	
	public static final String SubTable_Line_insert						= "/admin/TableLineNode/insert";
	public static final String SubTable_Line_read_Latest				= "/admin/TableLineNode/read-latest";
	public static final String SubTable_Line_read_Range					= "/admin/TableLineNode/read-range";
	public static final String SubTable_Line_read_ListId				= "/admin/TableLineNode/read-list-id";
	public static final String SubTable_Line_update_Description			= "/admin/TableLineNode/update-description";
	public static final String SubTable_Line_update_Data				= "/admin/TableLineNode/update-data";
	public static final String SubTable_Line_delete_LineNode			= "/admin/TableLineNode/delete-LineNode";
	
	public static final String SubTable_Row_insert								= "/admin/TableRow/insert";
	public static final String SubTable_Row_init								= "/admin/TableRow/init";
	public static final String SubTable_Row_querry_Latest						= "/admin/TableRow/querry-latest";
	public static final String SubTable_Row_querry_Range						= "/admin/TableRow/querry-range";
	public static final String SubTable_Row_querry_ListId						= "/admin/TableRow/querry-list-rowid";
	public static final String SubTable_Row_QuerryIndexing_ComparisonOperators	= "/admin/TableRow/Indexing/ComparisonOperators";
	public static final String SubTable_Row_QuerryIndexing_Between				= "/admin/TableRow/Indexing/Between";
	public static final String SubTable_Row_update								= "/admin/TableRow/update";
	public static final String SubTable_Row_delete								= "/admin/TableRow/delete";
	
	public static final String SubTable_Log_insert								= "/admin/TableLog/insert";
	public static final String SubTable_Log_update								= "/admin/TableLog/update";
	public static final String SubTable_Log_querry_Latest						= "/admin/TableLog/querry-latest";
	public static final String SubTable_Log_querry_Range						= "/admin/TableLog/querry-range";
	public static final String SubTable_Log_querry_ListId						= "/admin/TableLog/querry-list-rowid";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String TEST_InsertAccount						= "/admin/Test/InsertAccount";
	public static final String TEST_Table1Primary						= "/admin/Test/Table1Primary";
	public static final String TEST_Table2Primary						= "/admin/Test/Table2Primary";
	public static final String TEST_TableRow							= "/admin/Test/TableRow";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String H1_Login						= "1. Login";
	public static final String H2_Lobby						= "2. Lobby";
	public static final String H3_Database					= "3. Database";
	public static final String H3a1_TableAccount			= "3.a1. AccountConfig";
	public static final String H3a2_TableAccount_Querry		= "3.a2. AccountQuerry";
	public static final String H3b1_Primary1				= "➀ Primary1Key ";
	public static final String H3b2_Primary2				= "➁ Primary2Key ";
	public static final String H3b3_Row						= "📊 Row ";
	public static final String H3b4_Line					= "📌 LineNode";
	public static final String H3b5_Leaderboard				= "🏆 Leaderboard";
	public static final String H3b9_Log						= "📜 Log";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static MyRespone resSuccess, resFailure, resBadRequest, resUnauthorized, resForbidden, resNotFound, resInternalServerError, resInvalid, resInvalidParameters, resDatabaseError, resDatabaseNotExist, resTableNotExist, resTableInvalid, resWrongPassword;
	public static MyRespone resTokenNotExist,resTokenTimeout,resTokenError;
	public static MyRespone resExisted;
	public static MyRespone resSuspend,resPending;
	public static MyRespone resDeveloping;
	public static MyRespone resEmpty;
	public static MyRespone resOutOfRange;
    public static void setupApi() {
    	if(new File(PATH.DATABASE_FOLDER).exists()==false)
    		new File(PATH.DATABASE_FOLDER).mkdirs(); 
    	
    	CipherBinary cipherBinary = new CipherBinary();
    	RichardToken.cipherBinary=cipherBinary;
    	AdminToken.cipherBinary=cipherBinary;
    	
    	HttpProcessing_Login.adminLoginManager=AdminLoginManager.gI();
    	HttpProcessing_Login.databaseManager=DatabaseManager.gI();
    	BaseHttpAdminWeb_SignedIn.managingDatabase=DatabaseManager.gI();
    	DBGame_AccountLogin.databaseManager=DatabaseManager.gI();
    	DBGame_UserData.databaseManager=DatabaseManager.gI();
    	
    	DBGame_AccountLogin.timeManager = TimeManager.gI();
    	BaseHttpAdminWeb_SignedIn.timeManager = TimeManager.gI();
    	
    	Server_BackendGame.serverWebAdmin.setHeader(H1_Login, 1);
    	Server_BackendGame.serverWebAdmin.setHeader(H2_Lobby, 2);
    	Server_BackendGame.serverWebAdmin.setHeader(H3_Database, 3);
    	Server_BackendGame.serverWebAdmin.setHeader(H3a1_TableAccount, 4);
    	Server_BackendGame.serverWebAdmin.setHeader(H3a2_TableAccount_Querry, 5);
    	Server_BackendGame.serverWebAdmin.setHeader(H3b1_Primary1, 6);
    	Server_BackendGame.serverWebAdmin.setHeader(H3b2_Primary2, 7);
    	Server_BackendGame.serverWebAdmin.setHeader(H3b4_Line, 9);
    	Server_BackendGame.serverWebAdmin.setHeader(H3b3_Row, 8);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		resInvalidParameters = new MyRespone(MyRespone.STATUS_InvalidParameters);
		resInvalid = new MyRespone(MyRespone.STATUS_Invalid);
		resSuspend = new MyRespone(MyRespone.STATUS_Suspend);
		resPending = new MyRespone(MyRespone.STATUS_Pending);
		resEmpty = new MyRespone(MyRespone.STATUS_Empty);
		resDatabaseError = new MyRespone(MyRespone.STATUS_DatabaseError);
		resDatabaseNotExist = new MyRespone(MyRespone.STATUS_DatabaseNotExist);
		resTableNotExist = new MyRespone(MyRespone.STATUS_TableNotExist);
		resTableInvalid = new MyRespone(MyRespone.STATUS_TableInvalid);
		resWrongPassword = new MyRespone(MyRespone.STATUS_WrongPassword);
		
//		resOk = new MyRespone(MyRespone.STATUS_Ok);
		resSuccess = new MyRespone(MyRespone.STATUS_Success);
		resFailure = new MyRespone(MyRespone.STATUS_Failure);
		resBadRequest = new MyRespone(MyRespone.STATUS_BadRequest);
		resUnauthorized = new MyRespone(MyRespone.STATUS_Unauthorized);
		resForbidden = new MyRespone(MyRespone.STATUS_Forbidden);
		resNotFound = new MyRespone(MyRespone.STATUS_NotFound);
		resInternalServerError = new MyRespone(MyRespone.STATUS_InternalServerError);
		
		resTokenNotExist = new MyRespone(MyRespone.STATUS_TokenNotExist);
		resTokenTimeout = new MyRespone(MyRespone.STATUS_TokenTimeout);
		resTokenError = new MyRespone(MyRespone.STATUS_TokenError);
		resExisted = new MyRespone(MyRespone.STATUS_Existed);
		resOutOfRange = new MyRespone(MyRespone.STATUS_OutOfRange);
		resDeveloping = new MyRespone(MyRespone.STATUS_Developing);
		
		
		
		
		try {BaseHttpAdminWeb_SignedIn.dbString = new DBString(PATH.DATABASE_FOLDER+"/String");} catch (IOException e) {e.printStackTrace();}
    }
	
	
	
	

//	//////////////////////////////
//	public static final String TABLE_Create_Account		= "Row-CreateAccount";
//	
//	public static final String BinaryRowQuerry_By_ListUserId	= "BinaryRowQuerry-ListUserId";
//	public static final String BinaryRowQuerry_By_UserIdRange	= "BinaryRowQuerry-UserIdRange";
//	public static final String BinaryRowQuerry_By_LatestAccount	= "BinaryRowQuerry-LatestAccount";
//	public static final String BinaryRowQuerry_By_Credential	= "BinaryRowQuerry-Credential";
//	
//	
////	public static final String TABLERow_QuerryAccount_ByRange		= "RowQuerryAccount-RangeUserId";
////	public static final String TABLERow_QuerryAccount_ByLatest		= "RowQuerryAccount-LatestCreate";
//	
//	
//	public static final String TABLERow_Update_Row		= "Row-Update";
//	//////////////////////////////
//	public static final String SubTable_Create_Leaderboard		= "Create-Leaderboard";
//	public static final String SubTable_Create_SubUserData		= "Create-SubUserData";
//	public static final String SubTable_Create_TileBinary		= "Create-TileBinary";
//	public static final String SubTable_Create_TileRow			= "Create-TileRow";
//	public static final String SubTable_Rename_SubTable			= "SubTable-Rename";
//	
//	public static final String Leaderboard_Config				= "Config";
//	public static final String Leaderboard_QuerryIndex			= "QuerryIndex";
//	public static final String Leaderboard_QuerryFull_Index		= "QuerryFull-Index";
//	public static final String Leaderboard_QuerryFull_Range		= "QuerryFull-Range";
//	public static final String Leaderboard_QuerryFull_Latest	= "QuerryFull-Latest";
//	public static final String Leaderboard_UpdateData			= "UpdateData";
//	
//	public static final String SubTable_Config_Tile_Custom			= "SubTable-config-tile-custom";
//	public static final String SubTable_Config_Tile_Row			= "SubTable-config-tile-row";
//	public static final String SubTable_Insert		= "SubTable-insert";
//	public static final String SubTable_Querry		= "SubTable-querry";
//	public static final String SubTable_Update		= "SubTable-update";
//	public static final String SubTable_Delete		= "SubTable-delete";
//	//////////////////////////////
//	public static final String Client_LoginScreen_Device			= "Login-Device";
//	public static final String Client_LoginScreen_SystemAccount		= "Login-SystemAccount";
//	public static final String Client_LoginScreen_Facebook			= "Login-Facebook";
//	public static final String Client_LoginScreen_Google			= "Login-Google";
//	public static final String Client_LoginScreen_AdsId				= "Login-AdsId";
//	public static final String Client_LoginScreen_Apple				= "Login-Apple";
//	public static final String Client_LoginScreen_EmailCode			= "Login-EmailCode";
//	
//	public static final String TILE_Create		= "Tile-Create";
//	public static final String TILE_Config		= "Tile-Config";
//	public static final String TILE_Querry		= "Tile-Querry";
//	public static final String TILE_Update		= "Tile-Update";
//	public static final String TILE_Delete		= "Tile-Delete";

	////////////////////////////// Paypal payment
//	public static final String PAYPAL_CHECKOUT_TRANSACTION		= "Checkout-Payment";
//
//	public static final String PAYPAL_REVIEW_TRANSACTION		= "Review-Payment";
//	public static final String PAYPAL_EXECUTE_TRANSACTION		= "Execute-Payment";
}
