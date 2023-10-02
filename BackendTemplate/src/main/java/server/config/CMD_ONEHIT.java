package server.config;
/** writer : richard */

public class CMD_ONEHIT {

    public static final short SplashGlobal_GetList_OneHit   =1;
    public static final short SplashGlobal_GetList_ServerGame_Name=2;
    public static final short SplashGlobal_GetList_ServerGame_IP=3;
    public static final short SplashGlobal_GetList_ServerGame_Data=4;

    public static final short SplashAiO_InfoGame_Android    =40;
    public static final short SplashAiO_InfoGame_IOS        =41;

    public static final short SplashAiO_IAPGold_Android     =50;
    public static final short SplashAiO_IAPGold_IOS         =51;
    public static final short SplashAiO_IAPDiamond_Android  =52;
    public static final short SplashAiO_IAPDiamond_IOS      =53;

    public static final short SplashAiO_IAPInfo_Android     =80;
    public static final short SplashAiO_IAPInfo_IOS         =81;

    public static final short NGOCMANH_Forward_SET_LIST_DATA=150;
    public static final short NGOCMANH_GET_LIST_DATA        =151;
    public static final short NGOCMANH_Forward_Commit_Click =152;

    public static final short NGOCMANH_Insert_Row           =160;
    public static final short NGOCMANH_Update_Row           =161;
    public static final short NGOCMANH_Delete_Row           =162;
    public static final short NGOCMANH_Get_List_Rows        =163;
    public static final short NGOCMANH_Get_Row_By_Id        =164;

    public static final short LGScreen_LoginAccount_0_Device=200;
    public static final short LGScreen_LoginAccount_1_System=201;
    public static final short LGScreen_LoginAccount_2_Facebook=202;
    public static final short LGScreen_LoginAccount_3_Google=203;
    public static final short LGScreen_LoginAccount_4_Email =204;
    public static final short LGScreen_LoginAccount_5_Apple =205;
    public static final short LGScreen_LoginAccount_6_PhoneNumber=206;

    public static final short LGScreen_RegisterAccount      =220;

    public static final short LGScreen_ResetPassword        =222;

    public static final short LGScreen_LoginToken_0_Device  =300;
    public static final short LGScreen_LoginToken_1_System  =301;
    public static final short LGScreen_LoginToken_2_Facebook=302;
    public static final short LGScreen_LoginToken_3_Google  =303;
    public static final short LGScreen_LoginToken_4_Email   =304;
    public static final short LGScreen_LoginToken_5_Apple   =305;
    public static final short LGScreen_LoginToken_6_PhoneNumber=306;

    public static final short HomeDB_ChangePassword         =500;
    public static final short HomeDB_GetEmailSecurity       =501;
    public static final short HomeDB_SetEmailSecurity       =502;

    public static final short HomeDB_Change_Avatarid        =505;

    public static final short HomeDB_LoginToken             =598;
    public static final short HomeDB_RefreshToken           =599;

    public static final short HomeLeaderboard_TopGold       =800;
    public static final short HomeLeaderboard_TopDiamond    =801;
    public static final short HomeLeaderboard_TopGoldDiamond=802;

    public static final short HomeShop_InappPurchase_Android=900;

    public static final short HomeShop_InappPurchase_IOS_test=904;
    public static final short HomeShop_InappPurchase_IOS    =905;

    public static final short AiOHome_Get_Gold_By_AiOid     =1020;

    public static final short AiOHome_Get_Subsidy           =1100;
    public static final short AiOHome_Get_DailyBonus        =1101;

    public static final short AiOHome_Get_Header_By_aioId   =1150;

    public static final short AiOHome_Purchase_Android      =1190;

    public static final short AiOHome_Purchase_IOS_test     =1194;
    public static final short AiOHome_Purchase_IOS          =1195;

    public static final short AiOHome__GetPurchaseGoldPrice =1250;

    public static final short AiORoomTable_GetListTable_By_gameid=2901;
    public static final short AiORoomTable_GetSmartTable_By_gameid=2902;

    public static final short AiORoomTable_GetListViewer_in_table=2905;

    public static final short Game_House_Get_Version        =2950;

    public static final short GameTowerDefense_Get_Data     =3001;
    public static final short GameTowerDefense_Update_Data  =3002;

    public static final short BoomHome_FirstLogin_HeaderGold_FullData=3200;

    public static final short BoomHome_Update_Data          =3205;

    public static final short BoomHome_Unlock_ThanhTuu      =3220;
    public static final short BoomHome_Commit_NhiemVu       =3221;

    public static final short BoomHome_Get_DailyBonus       =3250;
    public static final short BoomHome_Get_Free_By_VideoReward=3251;

    public static final short BoomHome_Get_Shop_Data        =3280;
    public static final short BoomHome_Shop_Character       =3281;
    public static final short BoomHome_Shop_Item            =3282;
    public static final short BoomHome_Shop_Skill           =3283;

    public static final short VIN_Update_Android            =6000;
    public static final short VIN_Update_IOS                =6001;
    public static final short VIN_IAP_Android               =6002;
    public static final short VIN_IAP_IOS                   =6003;

    public static final short VinHome_FirstLogin_FullData   =6100;
    public static final short VinHome_Add_GOLD              =6101;
    public static final short VinHome_Add_DIAMOND           =6102;
    public static final short VinHome_Add_Egg               =6103;
    public static final short VinHome_Add_Pet               =6104;
    public static final short VinHome_Add_Spin              =6105;

    public static final short VinHome_Add_Medal             =6120;
    public static final short VinHome_Remove_Medal          =6121;

    public static final short VinHome_Bonus_Daily           =6200;

    public static final short VinHome_Use_Spin              =6202;
    public static final short VinHome_Break_Egg             =6203;

    public static final short VinHome_Create_PrivateRoom    =6800;

    public static final short VinHome_Bet_Casino            =6999;

    public static final short Game_Global_Get_BasePlayerInfo_BySessionid=9002;
    public static final short Game_Global_Get_BasePlayerInfo_And_1_Achievement_ByUserid=9003;

    public static final short Game_Global_Get_BasePlayerInfo_And_FullAchievement_ByUserid=9005;

    public static final short Game_BetToWin_NoAchievement   =9200;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final short BBWeb_Table_Create_Table              =10110;
    public static final short BBWeb_Table_Get_Info                  =10120;
    
    public static final short BBWeb_Table_Config_AccessKey          =10151;
    public static final short BBWeb_Table_Config_ReadKey            =10153;
    public static final short BBWeb_Table_Config_WriteKey           =10155;
    
    public static final short BBWeb_Table_Config_TokenLifeTime      =10157;
    public static final short BBWeb_Table_Config_LoginRule          =10158;
    public static final short BBWeb_Table_Config_MailService        =10159;
    public static final short BBWeb_Table_Config_DescribeTables     =10160;
    
    public static final short BBWeb_Table_Logout_All_Client         =10170;
    
    public static final short BBWeb_Table_Delete                    =10190;
    
    
    ///////////////////////////////////////////////
    public static final short BBWeb_BinaryRow_Querry_By_Credential          =10200;
    public static final short BBWeb_BinaryRow_Querry_By_ListUserId          =10201;
    public static final short BBWeb_BinaryRow_Querry_By_UserIdRange         =10202;
    public static final short BBWeb_BinaryRow_Querry_By_LatestCreate        =10203;
    public static final short BBWeb_BinaryRow_Update_Row                    =10235;
    
    
    public static final short BBWeb_ParsingRow_Querry_By_Credential         =10250;
    public static final short BBWeb_ParsingRow_Querry_By_ListUserId         =10251;
    public static final short BBWeb_ParsingRow_Querry_By_UserIdRange        =10252;
    public static final short BBWeb_ParsingRow_Querry_By_LatestCreate       =10253;
    public static final short BBWeb_ParsingRow_Update_UserData              =10285;
    
    public static final short BBWeb_Row_Update_AccountStatus                =10290;
    public static final short BBWeb_Row_Insert_Account                      =10299;
    ///////////////////////////////////////////////
    public static final short BBWeb_SubTable_Create_Leaderboard                 =10300;
    
    public static final short BBWeb_SubTable_Create_Tile_Binary                 =10305;
    public static final short BBWeb_SubTable_Create_Tile_PrimaryKey             =10308;
    public static final short BBWeb_SubTable_Create_TileRow_Without_PrimaryKey  =10309;
    
    
    public static final short BBWeb_SubTable_Config_AccessKey               =10350;
    public static final short BBWeb_SubTable_Config_ReadKey                 =10351;
    public static final short BBWeb_SubTable_Config_WriteKey                =10352;
    
    
    public static final short BBWeb_SubTable_Delete                         =10399;
    
    public static final short BBWeb_Leaderboard_Config                      =10400;
    public static final short BBWeb_Leaderboard_Querry_Id                   =10420;
    public static final short BBWeb_Leaderboard_FullQuerry_Index            =10421;
    public static final short BBWeb_Leaderboard_FullQuerry_Range            =10422;
    public static final short BBWeb_Leaderboard_FullQuerry_Latest           =10423;
    public static final short BBWeb_Leaderboard_UpdateData                  =10460;
    public static final short BBWeb_Leaderboard_LoadConfig                  =10490;
    
    public static final short BBWeb_SubTable_Config_DescribeTables          =10500;
    public static final short BBWeb_SubTable_Querry_Index                   =10520;
    public static final short BBWeb_SubTable_Querry_Range                   =10521;
    public static final short BBWeb_SubTable_Querry_Latest                  =10522;
    public static final short BBWeb_SubTable_FullQuerry_Index               =10540;
    public static final short BBWeb_SubTable_FullQuerry_Range               =10541;
    public static final short BBWeb_SubTable_FullQuerry_Latest              =10542;
    public static final short BBWeb_SubTable_Data_Update                    =10580;
    
    public static final short BBWeb_SubTileBinary_Config                    =10600;
    public static final short BBWeb_SubTileBinary_Querry_Index              =10620;
    public static final short BBWeb_SubTileBinary_Querry_Range              =10621;
    public static final short BBWeb_SubTileBinary_Querry_Latest             =10622;
    public static final short BBWeb_SubTileBinary_Data_Insert               =10650;
    public static final short BBWeb_SubTileBinary_Data_Update               =10680;
    
    public static final short BBWeb_SubTileRow_Config                       =10700;
    public static final short BBWeb_SubTileRow_Querry_Index                 =10720;
    public static final short BBWeb_SubTileRow_Querry_Range                 =10721;
    public static final short BBWeb_SubTileRow_Querry_Latest                =10722;
    public static final short BBWeb_SubTileRow_Data_Insert                  =10750;
    public static final short BBWeb_SubTileRow_Data_Update                  =10780;
    ///////////////////////////////////////////////
    public static final short BBWeb_Tile_Create                             =10800;
    public static final short BBWeb_Tile_Config                             =10801;
    public static final short BBWeb_Tile_Delete                             =10802;
    
    public static final short BBWeb_TileData_Querry                         =10820;
    public static final short BBWeb_TileData_Insert                         =10840;
    public static final short BBWeb_TileData_Update                         =10860;
    public static final short BBWeb_TileData_Delete                         =10890;
    
    public static final short BBWeb_Sync_GetVersion                         =10900;
    public static final short BBWeb_Sync_BeginDownload                      =10905;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final short BBClient_Table_Login_Without_Password             =11000;
    public static final short BBClient_Table_Login_With_Password                =11001;
    
    public static final short BBClient_Table_SignUpAccount_CreateAccount        =11005;
    
    public static final short BBClient_Table_SignUpEmail_Request_EmailCode      =11007;
    public static final short BBClient_Table_SignUpEmail_Complete_VerifyCode    =11008;
    public static final short BBClient_Table_SignUpEmail_Forgot_Password        =11009;
    
    public static final short BBClient_Home_Change_Password                     =11020;
    
    
    
    
    
    
    public static final short BBClient_Lobby_Get_UserData                       =11029;
    public static final short BBClient_TableRow_Update_Data                     =11030;
    public static final short BBClient_TableRow_Update_DataType                 =11031;
    
//  public static final short BBClient_TableRow_Operator_Addition           =11031;//Cộng
//  public static final short BBClient_TableRow_Operator_Subtraction        =11031;//Trừ
//  public static final short BBClient_TableRow_Operator_AdditiveInverse    =11031;
//  public static final short BBClient_TableRow_Operator_Multiplication     =11031;
//  public static final short BBClient_TableRow_Operator_Division           =11031;
//  public static final short BBClient_TableRow_Operator_Modulo             =11031;
//  public static final short BBClient_TableRow_Operator_LogicalNegation    =11031;
//  public static final short BBClient_TableRow_Operator_LogicalAND         =11031;
//  
//  public static final short BBClient_TableRow_Operator_BitwiseNOT         =11031;
//  public static final short BBClient_TableRow_Operator_BitwiseAND         =11031;
//  public static final short BBClient_TableRow_Operator_BitwiseOR          =11031;
//  public static final short BBClient_TableRow_Operator_BitwiseXOR         =11031;
//  public static final short BBClient_TableRow_Operator_BitwiseLeftShift   =11031;
//  public static final short BBClient_TableRow_Operator_BitwiseRightShift  =11031;
    
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  public static final short BBAdmin_Reader_Querry_Account_By_UserIdIndex          =12000;
//  public static final short BBAdmin_Reader_Querry_Account_By_UserIdRange          =12000;
//  public static final short BBAdmin_Reader_Querry_Account_By_LatestCreate         =12000;
//  
//  public static final short BBAdmin_Reader_Querry_UserData_By_UserIdIndex         =12000;
//  public static final short BBAdmin_Reader_Querry_UserData_By_UserIdRange         =12000;
//  public static final short BBAdmin_Reader_Querry_UserData_By_LatestCreate        =12000;
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final short BBWeb_Test_Time_Avaiable                      =19998; 
    public static final short BBWeb_Row_Random_Account                      =19999; 
    
    
    
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final short MySQL_DataCache_NgocManhBanner=20100;

    public static final short Admin_ReloadDatabase          =30000;



    public static final String API(short cmd) {
        switch (cmd) {
            case 1: return "SplashGlobal_GetList_OneHit";
            case 2: return "SplashGlobal_GetList_ServerGame_Name";
            case 3: return "SplashGlobal_GetList_ServerGame_IP";
            case 4: return "SplashGlobal_GetList_ServerGame_Data";
            case 40: return "SplashAiO_InfoGame_Android";
            case 41: return "SplashAiO_InfoGame_IOS";
            case 50: return "SplashAiO_IAPGold_Android";
            case 51: return "SplashAiO_IAPGold_IOS";
            case 52: return "SplashAiO_IAPDiamond_Android";
            case 53: return "SplashAiO_IAPDiamond_IOS";
            case 80: return "SplashAiO_IAPInfo_Android";
            case 81: return "SplashAiO_IAPInfo_IOS";
            case 150: return "NGOCMANH_Forward_SET_LIST_DATA";
            case 151: return "NGOCMANH_GET_LIST_DATA";
            case 152: return "NGOCMANH_Forward_Commit_Click";
            case 160: return "NGOCMANH_Insert_Row";
            case 161: return "NGOCMANH_Update_Row";
            case 162: return "NGOCMANH_Delete_Row";
            case 163: return "NGOCMANH_Get_List_Rows";
            case 164: return "NGOCMANH_Get_Row_By_Id";
            case 200: return "LGScreen_LoginAccount_0_Device";
            case 201: return "LGScreen_LoginAccount_1_System";
            case 202: return "LGScreen_LoginAccount_2_Facebook";
            case 203: return "LGScreen_LoginAccount_3_Google";
            case 204: return "LGScreen_LoginAccount_4_Email";
            case 205: return "LGScreen_LoginAccount_5_Apple";
            case 206: return "LGScreen_LoginAccount_6_PhoneNumber";
            case 220: return "LGScreen_RegisterAccount";
            case 222: return "LGScreen_ResetPassword";
            case 300: return "LGScreen_LoginToken_0_Device";
            case 301: return "LGScreen_LoginToken_1_System";
            case 302: return "LGScreen_LoginToken_2_Facebook";
            case 303: return "LGScreen_LoginToken_3_Google";
            case 304: return "LGScreen_LoginToken_4_Email";
            case 305: return "LGScreen_LoginToken_5_Apple";
            case 306: return "LGScreen_LoginToken_6_PhoneNumber";
            case 500: return "HomeDB_ChangePassword";
            case 501: return "HomeDB_GetEmailSecurity";
            case 502: return "HomeDB_SetEmailSecurity";
            case 505: return "HomeDB_Change_Avatarid";
            case 598: return "HomeDB_LoginToken";
            case 599: return "HomeDB_RefreshToken";
            case 800: return "HomeLeaderboard_TopGold";
            case 801: return "HomeLeaderboard_TopDiamond";
            case 802: return "HomeLeaderboard_TopGoldDiamond";
            case 900: return "HomeShop_InappPurchase_Android";
            case 904: return "HomeShop_InappPurchase_IOS_test";
            case 905: return "HomeShop_InappPurchase_IOS";
            case 1020: return "AiOHome_Get_Gold_By_AiOid";
            case 1100: return "AiOHome_Get_Subsidy";
            case 1101: return "AiOHome_Get_DailyBonus";
            case 1150: return "AiOHome_Get_Header_By_aioId";
            case 1190: return "AiOHome_Purchase_Android";
            case 1194: return "AiOHome_Purchase_IOS_test";
            case 1195: return "AiOHome_Purchase_IOS";
            case 1250: return "AiOHome__GetPurchaseGoldPrice";
            case 2901: return "AiORoomTable_GetListTable_By_gameid";
            case 2902: return "AiORoomTable_GetSmartTable_By_gameid";
            case 2905: return "AiORoomTable_GetListViewer_in_table";
            case 2950: return "Game_House_Get_Version";
            case 3001: return "GameTowerDefense_Get_Data";
            case 3002: return "GameTowerDefense_Update_Data";
            case 3200: return "BoomHome_FirstLogin_HeaderGold_FullData";
            case 3205: return "BoomHome_Update_Data";
            case 3220: return "BoomHome_Unlock_ThanhTuu";
            case 3221: return "BoomHome_Commit_NhiemVu";
            case 3250: return "BoomHome_Get_DailyBonus";
            case 3251: return "BoomHome_Get_Free_By_VideoReward";
            case 3280: return "BoomHome_Get_Shop_Data";
            case 3281: return "BoomHome_Shop_Character";
            case 3282: return "BoomHome_Shop_Item";
            case 3283: return "BoomHome_Shop_Skill";
            case 6000: return "VIN_Update_Android";
            case 6001: return "VIN_Update_IOS";
            case 6002: return "VIN_IAP_Android";
            case 6003: return "VIN_IAP_IOS";
            case 6100: return "VinHome_FirstLogin_FullData";
            case 6101: return "VinHome_Add_GOLD";
            case 6102: return "VinHome_Add_DIAMOND";
            case 6103: return "VinHome_Add_Egg";
            case 6104: return "VinHome_Add_Pet";
            case 6105: return "VinHome_Add_Spin";
            case 6120: return "VinHome_Add_Medal";
            case 6121: return "VinHome_Remove_Medal";
            case 6200: return "VinHome_Bonus_Daily";
            case 6202: return "VinHome_Use_Spin";
            case 6203: return "VinHome_Break_Egg";
            case 6800: return "VinHome_Create_PrivateRoom";
            case 6999: return "VinHome_Bet_Casino";
            case 9002: return "Game_Global_Get_BasePlayerInfo_BySessionid";
            case 9003: return "Game_Global_Get_BasePlayerInfo_And_1_Achievement_ByUserid";
            case 9005: return "Game_Global_Get_BasePlayerInfo_And_FullAchievement_ByUserid";
            case 9200: return "Game_BetToWin_NoAchievement";
            case 20100: return "MySQL_DataCache_NgocManhBanner";
            case 30000: return "Admin_ReloadDatabase";
            default:return "CMD("+cmd+")";
        }
    }


    public static final String getCmdName(int _id) {
        switch (_id) {
            case 1: return "SplashGlobal_GetList_OneHit(1)";
            case 2: return "SplashGlobal_GetList_ServerGame_Name(2)";
            case 3: return "SplashGlobal_GetList_ServerGame_IP(3)";
            case 4: return "SplashGlobal_GetList_ServerGame_Data(4)";
            case 40: return "SplashAiO_InfoGame_Android(40)";
            case 41: return "SplashAiO_InfoGame_IOS(41)";
            case 50: return "SplashAiO_IAPGold_Android(50)";
            case 51: return "SplashAiO_IAPGold_IOS(51)";
            case 52: return "SplashAiO_IAPDiamond_Android(52)";
            case 53: return "SplashAiO_IAPDiamond_IOS(53)";
            case 80: return "SplashAiO_IAPInfo_Android(80)";
            case 81: return "SplashAiO_IAPInfo_IOS(81)";
            case 150: return "NGOCMANH_Forward_SET_LIST_DATA(150)";
            case 151: return "NGOCMANH_GET_LIST_DATA(151)";
            case 152: return "NGOCMANH_Forward_Commit_Click(152)";
            case 160: return "NGOCMANH_Insert_Row(160)";
            case 161: return "NGOCMANH_Update_Row(161)";
            case 162: return "NGOCMANH_Delete_Row(162)";
            case 163: return "NGOCMANH_Get_List_Rows(163)";
            case 164: return "NGOCMANH_Get_Row_By_Id(164)";
            case 200: return "LGScreen_LoginAccount_0_Device(200)";
            case 201: return "LGScreen_LoginAccount_1_System(201)";
            case 202: return "LGScreen_LoginAccount_2_Facebook(202)";
            case 203: return "LGScreen_LoginAccount_3_Google(203)";
            case 204: return "LGScreen_LoginAccount_4_Email(204)";
            case 205: return "LGScreen_LoginAccount_5_Apple(205)";
            case 206: return "LGScreen_LoginAccount_6_PhoneNumber(206)";
            case 220: return "LGScreen_RegisterAccount(220)";
            case 222: return "LGScreen_ResetPassword(222)";
            case 300: return "LGScreen_LoginToken_0_Device(300)";
            case 301: return "LGScreen_LoginToken_1_System(301)";
            case 302: return "LGScreen_LoginToken_2_Facebook(302)";
            case 303: return "LGScreen_LoginToken_3_Google(303)";
            case 304: return "LGScreen_LoginToken_4_Email(304)";
            case 305: return "LGScreen_LoginToken_5_Apple(305)";
            case 306: return "LGScreen_LoginToken_6_PhoneNumber(306)";
            case 500: return "HomeDB_ChangePassword(500)";
            case 501: return "HomeDB_GetEmailSecurity(501)";
            case 502: return "HomeDB_SetEmailSecurity(502)";
            case 505: return "HomeDB_Change_Avatarid(505)";
            case 598: return "HomeDB_LoginToken(598)";
            case 599: return "HomeDB_RefreshToken(599)";
            case 800: return "HomeLeaderboard_TopGold(800)";
            case 801: return "HomeLeaderboard_TopDiamond(801)";
            case 802: return "HomeLeaderboard_TopGoldDiamond(802)";
            case 900: return "HomeShop_InappPurchase_Android(900)";
            case 904: return "HomeShop_InappPurchase_IOS_test(904)";
            case 905: return "HomeShop_InappPurchase_IOS(905)";
            case 1020: return "AiOHome_Get_Gold_By_AiOid(1020)";
            case 1100: return "AiOHome_Get_Subsidy(1100)";
            case 1101: return "AiOHome_Get_DailyBonus(1101)";
            case 1150: return "AiOHome_Get_Header_By_aioId(1150)";
            case 1190: return "AiOHome_Purchase_Android(1190)";
            case 1194: return "AiOHome_Purchase_IOS_test(1194)";
            case 1195: return "AiOHome_Purchase_IOS(1195)";
            case 1250: return "AiOHome__GetPurchaseGoldPrice(1250)";
            case 2901: return "AiORoomTable_GetListTable_By_gameid(2901)";
            case 2902: return "AiORoomTable_GetSmartTable_By_gameid(2902)";
            case 2905: return "AiORoomTable_GetListViewer_in_table(2905)";
            case 2950: return "Game_House_Get_Version(2950)";
            case 3001: return "GameTowerDefense_Get_Data(3001)";
            case 3002: return "GameTowerDefense_Update_Data(3002)";
            case 3200: return "BoomHome_FirstLogin_HeaderGold_FullData(3200)";
            case 3205: return "BoomHome_Update_Data(3205)";
            case 3220: return "BoomHome_Unlock_ThanhTuu(3220)";
            case 3221: return "BoomHome_Commit_NhiemVu(3221)";
            case 3250: return "BoomHome_Get_DailyBonus(3250)";
            case 3251: return "BoomHome_Get_Free_By_VideoReward(3251)";
            case 3280: return "BoomHome_Get_Shop_Data(3280)";
            case 3281: return "BoomHome_Shop_Character(3281)";
            case 3282: return "BoomHome_Shop_Item(3282)";
            case 3283: return "BoomHome_Shop_Skill(3283)";
            case 6000: return "VIN_Update_Android(6000)";
            case 6001: return "VIN_Update_IOS(6001)";
            case 6002: return "VIN_IAP_Android(6002)";
            case 6003: return "VIN_IAP_IOS(6003)";
            case 6100: return "VinHome_FirstLogin_FullData(6100)";
            case 6101: return "VinHome_Add_GOLD(6101)";
            case 6102: return "VinHome_Add_DIAMOND(6102)";
            case 6103: return "VinHome_Add_Egg(6103)";
            case 6104: return "VinHome_Add_Pet(6104)";
            case 6105: return "VinHome_Add_Spin(6105)";
            case 6120: return "VinHome_Add_Medal(6120)";
            case 6121: return "VinHome_Remove_Medal(6121)";
            case 6200: return "VinHome_Bonus_Daily(6200)";
            case 6202: return "VinHome_Use_Spin(6202)";
            case 6203: return "VinHome_Break_Egg(6203)";
            case 6800: return "VinHome_Create_PrivateRoom(6800)";
            case 6999: return "VinHome_Bet_Casino(6999)";
            case 9002: return "Game_Global_Get_BasePlayerInfo_BySessionid(9002)";
            case 9003: return "Game_Global_Get_BasePlayerInfo_And_1_Achievement_ByUserid(9003)";
            case 9005: return "Game_Global_Get_BasePlayerInfo_And_FullAchievement_ByUserid(9005)";
            case 9200: return "Game_BetToWin_NoAchievement(9200)";
            case 20100: return "MySQL_DataCache_NgocManhBanner(20100)";
            case 30000: return "Admin_ReloadDatabase(30000)";
            default:return "CMD("+_id+")";
        }
    }
}
