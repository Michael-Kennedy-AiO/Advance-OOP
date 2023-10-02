package database_game.table;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.core.database.BGColumn;
import server.config.PATH;
import utility.Utility;

class DBGame_UserDataTest {
    private long timeBegin;
    private short DBId=2002;
    public DBGame_UserData databaseUserData;
    public Random random;

    
    
    @Test void test() throws IOException {
        timeBegin=System.currentTimeMillis();
        if(new File(PATH.DATABASE_FOLDER+"/"+DBId).exists()==false)
            new File(PATH.DATABASE_FOLDER+"/"+DBId).mkdirs();
        random=new Random();
        databaseUserData=new DBGame_UserData(DBId);
        
        
        BGColumn[] listRandom = new BGColumn[5+Utility.random.nextInt(8)];
        for(int i=0;i<listRandom.length;i++)
        	listRandom[i]=Utility.randomDescribe();
        
        databaseUserData.initNewDatabase(listRandom);
        databaseUserData.trace();
        
        
        for(int i=0;i<100;i++)
            databaseUserData.insertRow(i, random.nextLong());
        
//        databaseUserData.writeData(15, 0, false);
////        databaseUserData.writeData(15, 1, (byte)99);
//        databaseUserData.process(15, 1, DBOperator_Byte.Addition, DBDefine_DataType.BYTE, (byte)100);
//        databaseUserData.writeData(15, 2, (short)32678);
//        databaseUserData.writeData(15, 3, 88.99f);
//        databaseUserData.writeData(15, 4, (int)1111);
//        databaseUserData.writeData(15, 5, 11.22d);
//        databaseUserData.writeData(15, 6, 123l);
//        databaseUserData.writeData(15, 7, "backendgame");
//        databaseUserData.writeData(15, 8, new byte[] {1,2,3,4,5});
        
        //////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////
        //Bắt đầu code Operators chỗ này
//        databaseUserData.processShort(16, 2, DBOperator_Short.Addition, (short) 20);
        
        //////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////
        databaseUserData.trace();
        System.out.println("**************************************************");
        System.out.println("**************************************************");
        System.out.println("**************************************************");
        databaseUserData.traceRows(1,2,3,4,5,6,7,8,9,10,12,13,14,15,16,17,18,19,20);
    }
    
    @AfterEach void tearDown() throws Exception {
        databaseUserData.deleteAndClose();
        System.out.println("DBGame_UserDataTest : "+(System.currentTimeMillis()-timeBegin)+" ms");
    }
}